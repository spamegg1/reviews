// Thanks to @palladian on Discord
#include "wish.h"


// main function that runs the shell.
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

        else if (feof(infile)) {  // Reached EOF
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
        return get_cmd(cmd, path);
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
    if (buf == NULL) {                                 // Check for empty buffer
        return CONTINUE;
    }

    strip(&buf);
    if (*buf == '\0')
        return CONTINUE;

    // Separate command from arguments and remove trailing newline
    char *cmd = strsep(&buf, " \t\n\r");
    if (buf != NULL && buf[strlen(buf) - 1] == '\n') {
        buf[strlen(buf) - 1] = '\0';
    }

    // Handle exit command
    if (strcmp(cmd, "exit") == 0) {
        if (buf != NULL && *buf != '\0') {
            wish_error();
        }
        return QUIT;
    }
    // Handle cd command
    if (strcmp(cmd, "cd") == 0) {
        if (chdir(buf) != 0) {
            wish_error();
        }
        return CONTINUE;
    }
    // Handle path command
    if (strcmp(cmd, "path") == 0) {
        *path = update_path(*path, buf);
        if (*path == NULL) {
            wish_error();
            return QUIT;
        }
        return CONTINUE;
    }

    // Check for a redirect; redir will be either the output filename or NULL;
    // line will be everything before '>'
    char *redir = process_redirect(&buf);

    char **argv = create_argv(cmd, buf, *path);     // Create the argument array
    if (argv == NULL) {
        wish_error();
        return QUIT;
    }

    run_cmd(argv, redir);                                     // Run the command
    destroy_argv(argv);
    return CONTINUE;
}


void run_cmd(char **argv, char *redir)
{
    int rc = fork();
    if (rc < 0) {
        // Fork failed
        destroy_argv(argv);
        wish_error();

    } else if (rc == 0) {
        // Child: check for redirection and set it up
        if (redir != NULL) {
            if (redir[0] == '\0') {
                wish_error();
                return;
            }
            close(STDOUT_FILENO);
            open(redir, O_CREAT|O_WRONLY|O_TRUNC, S_IRWXU);
        }
        execv(argv[0], argv);

        // execv() doesn't return, so this executes only if execv fails
        wish_error();
        exit(EXIT_SUCCESS);

    } else {
        // Parent: wait for the child process to terminate
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
    // Replace path array with new one
    destroy_path(path);
    char **new_path = calloc(MAXPATHS, sizeof(char *));
    if (new_path == NULL) {
        return NULL;
    }
    // Add paths from shell arguments to path array
    char *tmp;
    for (size_t i = 0; i < MAXPATHS; i++) {
        tmp = strsep(&buf, " \t\n\r");
        if (tmp != NULL) {
            // Allocate memory for entry and copy path into it
            new_path[i] = calloc(PATHLEN + 1, 1);
            if (new_path[i] == NULL) {
                destroy_path(new_path);
                return NULL;
            }
            if (tmp[0] != '/') {
                // Relative path
                strcpy(new_path[i], "./");
                strncat(new_path[i], tmp, PATHLEN - 3);
            } else {
                // Absolute path
                strncpy(new_path[i], tmp, PATHLEN - 1);
            }
        }
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
    // Remove trailing whitespace
    char *end = *str + strlen(*str) - 1;
    while (end > *str && isspace(*end)) end--;
    end[1] = '\0';

    // Remove leading whitespace
    while (isspace(**str)) (*str)++;
}
