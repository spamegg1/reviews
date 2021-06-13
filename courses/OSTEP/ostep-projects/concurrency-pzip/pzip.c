// Thanks to Shubham Aggarwal https://github.com/Saggarwal9/Parallel-ZIP
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/mman.h>                                        // Library for mmap
#include <pthread.h>
#include <sys/stat.h>                                 // Library for struct stat
#include <sys/sysinfo.h>
#include <unistd.h>

////////////////////// NOTES /////////////////////////////
/*  void *mmap(void *addr, size_t length, int prot, int flags,
                    int fd, off_t offset);
Struct stat contains:
dev_t     st_dev     Device ID of device containing file.
ino_t     st_ino     File serial number.
mode_t    st_mode    Mode of file (see below).
nlink_t   st_nlink   Number of hard links to the file.
uid_t     st_uid     User ID of file.
gid_t     st_gid     Group ID of file.
[XSI][Option Start]
dev_t     st_rdev    Device ID (if file is character or block special).
[Option End]
off_t     st_size    For regular files, the file size in bytes.
                     For symbolic links, the length in bytes of the
                     pathname contained in the symbolic link.

Queue code and logic referenced from Professor Remzi's OS Book:
http://pages.cs.wisc.edu/~remzi/Classes/537/Spring2018/Book/threads-cv.pdf
*/

///////////////// GLOBAL VARIABLES ////////////////////////
int total_threads;      // Total number of threads that are created for consumer
int page_size;          // Page size = 4096 Bytes
int output_size;        // Output size
int num_files;          // Total number of the files passed as the arguments
int isComplete = 0;     // Flag to wake any sleeping threads at end of program
int total_pages;        // required for the compressed output
int q_head = 0;         // Circular queue head.
int q_tail = 0;         // Circular queue tail.
#define q_capacity 10   // Circular queue current size. Cannot have static array
// for buf which size is given as a variable, so use define. (Stackoverflow)
int q_size = 0;        // Circular queue capacity.
pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t filelock = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t empty = PTHREAD_COND_INITIALIZER;
pthread_cond_t fill = PTHREAD_COND_INITIALIZER;
int* pages_per_file;


///////////////// STRUCTURES ///////////////////////////////
// Contains the compressed output
struct output {
    char* data;
    int* count;
    int size;
} *out;

// Contains page specific data of a specific file.
struct buffer {
    char* address;     // Mapping address of file_number file + page_number page
    int file_number;   // File Number
    int page_number;   // Page number
    int last_page_size;// Page sized or (size_of_file) % page_size
} buf[q_capacity];

struct fd {                            // Contains file specific data for munmap
    char* addr;
    int size;
} *files;


///////////////// QUEUE Functions ////////////////////////
// buf is the Buffer queue. Queue capacity by default = 10
// Add at q_head index of the circular queue.
void put(struct buffer b) {
    buf[q_head] = b;                                       // Enqueue the buffer
    q_head = (q_head + 1) % q_capacity;
    q_size++;
}

// Remove from q_tail index of the circular queue.
struct buffer get() {
    struct buffer b = buf[q_tail];                         // Dequeue the buffer
    q_tail = (q_tail + 1) % q_capacity;
    q_size--;
    return b;
}


//////////////////////// PRODUCER /////////////////////////
// Reference for MMAP code: http://man7.org/linux/man-pages/man2/mmap.2.html
// Producer function to memory map the files passed as arguments.
void* producer(void *arg) {
    char** filenames = (char **) arg;                    // Step 1: Get the file
    struct stat sb;
    char* map;                                                   // mmap address
    int file;

    for (int i = 0; i < num_files; i++) {               // Step 2: Open the file
        // printf("filename %s\n",filenames[i]);
        file = open(filenames[i], O_RDONLY);

        // Calculates the number of pages in the file.
        // Number of pages = Size of file / Page size.
        int pages_in_file = 0;

        // Variable required if the file is not page-alligned
        // i.e. Size of File % Page size !=0
        int last_page_size = 0;

        if (file == -1){              // yikes , possible due to file not found?
            printf("Error: File didn't open\n");
            exit(1);
        }

        // Step 3: Get the file info.
        if (fstat(file, &sb) == -1){                                 // yikes #2
            close(file);
            printf("Error: Couldn't retrieve file stats");
            exit(1);
        }

        if (sb.st_size == 0) {                           // Empty files - Test 5
            continue;
        }

        // Step 4: Calculate the number of pages and last page size.
        // st_size contains the size offset.
        pages_in_file = sb.st_size / page_size;

        // In case file is not page alligned, we'll need to assign an extra page
        if (((double) sb.st_size / page_size) > pages_in_file) {
            pages_in_file += 1;
            last_page_size = sb.st_size % page_size;
        }

        else{                                                    // Page aligned
            last_page_size = page_size;
        }

        total_pages += pages_in_file;
        pages_per_file[i] = pages_in_file;

        // Step 5: Map the entire file.
        // If addr is NULL, then the kernel chooses the (page-aligned) address
        map = mmap(NULL, sb.st_size, PROT_READ, MAP_SHARED, file, 0);

        // at which to create the mapping. Source: man pages
        // yikes #3, possibly due to no memory? --unmap needed then?
        if (map == MAP_FAILED) {
            close(file);
            printf("Error mmapping the file\n");
            exit(1);
        }

        // Step 6: For all the pages in file, create a Buffer type data
        // with the relevant information for the consumer.
        for (int j = 0; j < pages_in_file; j++) {
            pthread_mutex_lock(&lock);
            while (q_size == q_capacity) {
                // Wake-up all the sleeping consumer threads.
                pthread_cond_broadcast(&fill);

                // Call the consumer to start working on the queue.
                pthread_cond_wait(&empty,&lock);
            }
            pthread_mutex_unlock(&lock);

            struct buffer temp;
            if (j == pages_in_file - 1) { // Last page might not be page-aligned
                temp.last_page_size = last_page_size;
            }
            else {
                temp.last_page_size = page_size;
            }

            temp.address = map;
            temp.page_number = j;
            temp.file_number = i;
            map += page_size;                   // Go to next page in the memory

            // possible race condition while changing size
            pthread_mutex_lock(&lock);
            put(temp);
            pthread_mutex_unlock(&lock);
            pthread_cond_signal(&fill);
        }

        close(file);                                   // Step 7: Close the file
    }

    // Possible race condition at isComplete?
    isComplete = 1;                             // When producer is done mapping

    // Debugging step: Program wasn't ending during some runtimes
    // as consumer threads were waiting for work.
    pthread_cond_broadcast(&fill);  // Wake-up all the sleeping consumer threads

    return 0;
}


/////////////////////////// CONSUMER /////////////////////////////////////
struct output RLECompress(struct buffer temp) {  // Compresses the buffer object
    struct output compressed;
    compressed.count = malloc(temp.last_page_size * sizeof(int));
    char* tempString = malloc(temp.last_page_size);
    int countIndex = 0;

    for (int i = 0; i < temp.last_page_size; i++) {
        tempString[countIndex] = temp.address[i];
        compressed.count[countIndex] = 1;

        while (i + 1 < temp.last_page_size
            && temp.address[i] == temp.address[i+1]) {
            compressed.count[countIndex]++;
            i++;
        }

        countIndex++;
    }

    compressed.size = countIndex;
    compressed.data = realloc(tempString, countIndex);
    return compressed;
}

/* https://piazza.com/class/jcwd4786vss6ky?cid=571
You'll need some sort of variable to figure out where in the buffer you are
in terms of bytes (i.e. where to add the next int or char).

Each time you add an int to the buffer,
you'll have to update that variable by 4 since an int is 4 bytes.

Each time you add a char, you'll have to update the variable by 1 byte.
At the end, the variable will also equal the size of the buffer.

To use fwrite, you just need to pass in the buffer plus the size of the buffer,
which is that variable. A simple example:

    char buffer[1000];
    int *p = &buffer[0];
    *p = 100;
    char *c = &buffer[4];
    *c = 'x';
*/

// Calculates the relative output position for the buffer object.
int calculateOutputPosition(struct buffer temp) {
    int position = 0;
    // Step 1: Find the relative file position of the buffer object.
    for (int i = 0; i < temp.file_number; i++) {
        position += pages_per_file[i];
    }

    // Step 2: Now we're at the index where the particular file begins,
    // we need to find the page relative position
    position += temp.page_number;
    return position;
}


// Called by consumer threads to start memory compression of files in queue buf
void *consumer() {
    do {
        pthread_mutex_lock(&lock);                                  // Lock here
        while (q_size == 0 && isComplete == 0) {
            pthread_cond_signal(&empty);

            // Call the producer to start filling the queue
            pthread_cond_wait(&fill, &lock);
        }

        // If producer is done mapping and there's nothing left in the queue
        if (isComplete == 1 && q_size == 0) {
            pthread_mutex_unlock(&lock);
            return NULL;
        }

        struct buffer temp = get();

        if(isComplete == 0) {
            pthread_cond_signal(&empty);
        }
        pthread_mutex_unlock(&lock);                              // Unlock here

        int position = calculateOutputPosition(temp);
        out[position] = RLECompress(temp);        // Output position calculation

    } while (!(isComplete == 1 && q_size == 0));

    return NULL;
}


/////////////////////////// Main /////////////////////////////////////////
// https://piazza.com/class/jcwd4786vss6ky?cid=571
void printOutput() {
    char* finalOutput = malloc(
        total_pages * page_size * (sizeof(int) + sizeof(char))
        );
    char* init = finalOutput;  // contains starting point of finalOutput pointer

    for (int i = 0; i < total_pages; i++) {
        if(i < total_pages - 1) {
            // Compare ith output's last char to i+1st output's first char
            if(out[i].data[out[i].size - 1] == out[i + 1].data[0]) {
                out[i+1].count[0] += out[i].count[out[i].size-1];
                out[i].size--;
            }
        }

        for (int j = 0; j < out[i].size; j++) {
            int num = out[i].count[j];
            char character = out[i].data[j];

            *((int*) finalOutput) = num;
            finalOutput += sizeof(int);
            *((char*) finalOutput) = character;
            finalOutput += sizeof(char);
        }
    }

    fwrite(init, finalOutput - init, 1, stdout);
}

void freeMemory() {
    free(pages_per_file);
    for (int i = 0; i < total_pages; i++) {
        free(out[i].data);
        free(out[i].count);
    }
    free(out);
}

int main(int argc, char* argv[]) {
    if (argc < 2) {                          // Check if less than two arguments
        printf("pzip: file1 [file2 ...]\n");
        exit(1);
    }

    // Initialize all the global Variables. I took 4096 as page size.
    // Program was running very slow, started trying out huge random values.
    // Program execution decreased by atleast 1/4.
    page_size = 10000000;                // sysconf(_SC_PAGE_SIZE);   4096 bytes
    num_files = argc - 1;                // Number of files, needed for producer
    total_threads = get_nprocs();        // Number of processes consumer threads
    pages_per_file = malloc(sizeof(int) * num_files);          // Pages per file
    output_size = 1024000;

    // Initially I was re-allocing out everytime to increase the size,
    // but it lead to a race condition where consumer tried to put compressed
    // output into an unallocated space.
    out = malloc(sizeof(struct output) * output_size);

    pthread_t pid;                // Create producer thread to map all the files
    pthread_t cid[total_threads];
    pthread_create(&pid, NULL, producer, argv + 1);  // argv + 1 to skip argv[0]

    // Create consumer thread to compress all the pages per file
    for (int i = 0; i < total_threads; i++) {
        pthread_create(&cid[i], NULL, consumer, NULL);
    }

    // Wait for producer-consumers to finish
    for (int i = 0; i < total_threads; i++) {
        pthread_join(cid[i], NULL);
    }
    pthread_join(pid, NULL);

    printOutput();
    freeMemory();
    return 0;
}
