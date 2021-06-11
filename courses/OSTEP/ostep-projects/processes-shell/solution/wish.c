// Thanks to @palladian on Discord for solution, I added comments
#include "wish.h"


// main function that runs the wish shell.
int main(int argc, char *argv[])
{
    // If batch mode, set input file; otherwise read from stdin
    FILE *infile;
    if (argc == 2) {                     // command is like ./wish somescript.sh
        infile = fopen(argv[1], "r");                // argv[1] is somescript.sh
        if (infile == NULL) {
            wish_error();                                       // defined below
            exit(EXIT_FAILURE);                            // as usual, return 1
        }
    }

    else if (argc == 1) {     // command is just ./wish so it runs interactively
        infile = stdin;                     // read interactive input from stdin
    }

    else {                                // no more than one batch file allowed
        wish_error();                                           // defined below
        exit(EXIT_FAILURE);                                // as usual, return 1
    }

    char *line = NULL;                                   // needed for getline()
    size_t n = 0;                                        // needed for getline()

    char **path = create_path();  // Initialize path with "/bin" (defined below)
    if (path == NULL) {                               // calloc or malloc failed
        fclose(infile);                                            // close file
        wish_error();                                           // display error
        exit(EXIT_FAILURE);                   // as usual, return failure status
    }

    while (1) {      // keep displaying the prompt and running commands, forever
        if (argc == 1) {            // no script provided, just interactive mode
            printf("wish> ");                                  // display prompt
        }

        // Read interactive input commands, or lines from script file
        if (getline(&line, &n, infile) > 0) {

            // multiple commands? run each in its own thread concurrently
            cmdresult res = parallel_cmds(line, &path);         // defined below

            switch (res) {
                case CONTINUE:
                    continue;
                case QUIT:
                    handle_exit(infile, line, path, EXIT_SUCCESS);      // below
            }
        }

        else if (feof(infile)) {                                  // Reached EOF
            handle_exit(infile, line, path, EXIT_SUCCESS);      // defined below
        }

        else {                         // getline() failed for some other reason
            wish_error();
            handle_exit(infile, line, path, EXIT_FAILURE);      // defined below
        }
    }
}


// exit/cleanup code that is repeated many times above, refactored out here
void handle_exit(FILE *infile, char *line, char **path, int exit_code) {
    fclose(infile);
    free(line);
    destroy_path(path);
    exit(exit_code);
}


// runs multiple commands each in its own thread concurrently
cmdresult parallel_cmds(char *line, char ***path)         // cmdresult is 0 or 1
{
    char *cmd = strsep(&line, "&");               // Check for parallel commands
    if (line == NULL) {                 // If none found, process single command
        return get_cmd(cmd, path);                     // process single command
    }

    cmdresult res = CONTINUE;    // Otherwise, fork to execute the first command

    int rc = fork();   // returns 2 values: one to parent (>0), one to child (0)
    if (rc < 0) {                                                 // Fork failed
        wish_error();                                           // defined below
        return QUIT;
    }

    else if (rc == 0) {                            // created Child successfully
        get_cmd(cmd, path);                      // Child: execute first command
        return QUIT; // child must return QUIT to avoid spawning parallel shells
    }

    else {               // Parent: check second half for more parallel commands
        strip(&line);  // remove leading and trailing whitespace (defined below)
        res = parallel_cmds(line, path);    // execute rest of the parallel cmds
        wait(NULL);                                     // wait for child to die
        return res;                         // there might be more commands left
    }
}


cmdresult get_cmd(char *buf, char ***path) {
    // buf can come from a strsep() in parallel_cmd so it could be NULL
    // example: buf = '   ls     -a          -l   \n'
    if (buf == NULL) {     // Check for empty buffer (strip will dereference it)
        return CONTINUE;                              // move on to next command
    }

    strip(&buf);           // remove leading and trailing whitespace and newline
    if (*buf == '\0')                                // empty string, no command
        return CONTINUE;                              // move on to next command

    char *cmd = strsep(&buf, " \t\n\r");      // Separate command from arguments
    // example: now cmd = 'ls' and buf becomes = '     -a          -l'

    // example: buf = "exit\n" (OK) or "exit       5\n" (should raise error)
    if (strcmp(cmd, "exit") == 0) {                       // Handle exit command
        if (buf != NULL) {     // exit should not be followed by another command
            wish_error();
        }
        return QUIT;                                    // quit after exit error
    }

    if (strcmp(cmd, "cd") == 0) {                           // Handle cd command
        if (chdir(buf) != 0) {     // try to change directory to the path in buf
            wish_error();       // chdir returns 0 on success, nonzero otherwise
        }
        return CONTINUE;                              // move on to next command
    }

    if (strcmp(cmd, "path") == 0) {                       // Handle path command
        *path = update_path(*path, buf);               // try to update the path
        if (*path == NULL) {            // update_path uses realloc. if it fails
            wish_error();                               // report error and quit
            return QUIT;   // why not exit(1)? we can only free memory in main()
        }
        return CONTINUE;                    // otherwise move on to next command
    }

    // Check for a redirect; redir will be either the output filename or NULL;
    char *redir = process_redirect(&buf);  // line will be everything before '>'

    char **argv = create_argv(cmd, buf, *path);    // Create arg array for execv
    if (argv == NULL) {                      // if argument array creation fails
        wish_error();                                            // report error
        return QUIT;                                                 // and quit
    }

    run_cmd(argv, redir);    // Run the command with the given arguments (below)
    destroy_argv(argv);                                    // clean up arguments
    return CONTINUE;                                  // move on to next command
}


void run_cmd(char **argv, char *redir)
{
    int rc = fork();           // create child process to run command with execv
    if (rc < 0) {                               // Fork failed, no child created
        destroy_argv(argv);                                // clean up arguments
        wish_error();                                            // report error
    }

    else if (rc == 0) {            // Child: check for redirection and set it up
        if (redir != NULL) {
            if (redir[0] == '\0') {              // if redir is the empty string
                wish_error();                                    // report error
                return;                                                  // exit
            }

            close(STDOUT_FILENO);         // redirect output: close stdout first
            open(redir, O_CREAT|O_WRONLY|O_TRUNC, S_IRWXU);        // open redir
        }
        execv(argv[0], argv);            // execute command with given arguments

        // execv() doesn't return, so this executes only if execv fails
        wish_error();
        exit(EXIT_SUCCESS);                               // satisfy the tests:)
    }

    else {                    // Parent: wait for the child process to terminate
        wait(NULL);
    }
}


char **create_argv(char *cmd, char *buf, char **path)
{
    char **argv = calloc(MAXARGS + 1, sizeof(char *));
    if (argv == NULL) {
        return NULL;
    }

    // Allocate space for a string to contain the command plus its path
    char *cmdpath = calloc(PATHLEN + 1 + strlen(cmd), 1);
    if (cmdpath == NULL) {
        free(argv);
        return NULL;
    }

    // Check whether command exists at each entry in path and we have
    // permission to execute
    size_t i = 0;
    while (i < MAXPATHS && path[i] != NULL) {
        strncpy(cmdpath, path[i], PATHLEN);
        strcat(cmdpath, "/");
        strcat(cmdpath, cmd);
        if (access(cmdpath, X_OK) == 0) {
            break;
        }
        i++;
    }
    argv[0] = cmdpath;

    // Check for empty arguments list
    if (buf == NULL || *buf == '\0') {
        return argv;
    }

    // Remove leading and trailing whitespace from buf
    strip(&buf);

    // Parse arguments into array
    for (i = 1; i < MAXARGS; i++) {
        argv[i] = strsep(&buf, " \t\n\r");
        if (argv[i] == NULL) {
            break;
        }
    }
    return argv;
}


void destroy_argv(char **argv) {
    free(argv[0]);
    free(argv);
}


char **create_path(void) {
    char **path = calloc(MAXPATHS, sizeof(char *));
    if (path == NULL) {
        return NULL;
    }
    // Initialize path with only "/bin"
    path[0] = malloc(5);
    if (path[0] == NULL) {
        free(path);
        return NULL;
    }
    strcpy(path[0], "/bin");
    return path;
}


void destroy_path(char **path) {
    for (size_t i = 0; i < MAXPATHS; i++) {
        free(path[i]);
    }
    free(path);
}


char **update_path(char **path, char *buf) {
    destroy_path(path);                       // Replace path array with new one
    char **new_path = calloc(MAXPATHS, sizeof(char *));               // new one
    if (new_path == NULL) {
        return NULL;
    }

    char *tmp;                   // Add paths from shell arguments to path array
    size_t i = 0;
    while (tmp != NULL && i < MAXPATHS) {                   // go through tokens
        tmp = strsep(&buf, " \t\n\r");             // get next token from buffer

        if (tmp != NULL) {
            // Allocate memory for entry and copy path into it
            new_path[i] = calloc(PATHLEN + 1, 1);
            if (new_path[i] == NULL) {
                destroy_path(new_path);
                return NULL;
            }

            if (tmp[0] != '/') {                                // Relative path
                strcpy(new_path[i], "./");
                strncat(new_path[i], tmp, PATHLEN - 3);
            }

            else {                                              // Absolute path
                strncpy(new_path[i], tmp, PATHLEN - 1);
            }
        }
        i++;
    }

    return new_path;
}


char *process_redirect(char **buf) {
    // Remove everything after '>' from buf
    char *tmp = *buf;
    *buf = strsep(&tmp, ">");

    // Skip any whitespace after '>'
    if (tmp != NULL) {
        strip(&tmp);
    }

    // Set redir to either output filename or NULL
    char *redir = strsep(&tmp, " \t\n\r");

    // Check for extra filenames after the first one
    if (tmp != NULL) {
        wish_error();
    }
    return redir;
}


ssize_t wish_error(void) {
    static const char error_message[30] = "An error has occurred\n";
    return write(STDERR_FILENO, error_message, strlen(error_message));
}


void strip(char **str) {
    char *end = *str + strlen(*str) - 1;           // Remove trailing whitespace
    while (end > *str && isspace(*end)) end--;// isspace counts \n as whitespace
    end[1] = '\0';

    while (isspace(**str)) (*str)++;                // Remove leading whitespace
}
