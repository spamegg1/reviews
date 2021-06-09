#include "wish.h"


int main(int argc, char *argv[])
{
	// If batch mode, set input file; otherwise read from stdin
	FILE *infile;
	if (argc == 2) {
		infile = fopen(argv[1], "r");
		if (infile == NULL) {
			wish_error();
			exit(EXIT_FAILURE);
		}
	} else if (argc == 1) {
		infile = stdin;
	} else {  // no more than one batch file
		wish_error();
		exit(EXIT_FAILURE);
	}

	char *line = NULL;
	size_t n = 0;

	// Initialize path
	char **path = create_path();
	if (path == NULL) {
		fclose(infile);
		wish_error();
		exit(EXIT_FAILURE);
	}

	while (1) {
		if (argc == 1) {
			printf("wish> ");
		}

		// Read and run input commands
		if (getline(&line, &n, infile) > 0) {
			cmdresult res = parallel_cmds(line, &path);
			switch (res) {
				case CONTINUE:
					continue;
				case EXIT:
					fclose(infile);
					free(line);
					destroy_path(path);
					exit(EXIT_SUCCESS);
			}
		} else if (feof(infile)) {  // Reached EOF
			fclose(infile);
			free(line);
			destroy_path(path);
			exit(EXIT_SUCCESS);
		} else {  // getline() failed for some other reason
			fclose(infile);
			free(line);
			destroy_path(path);
			wish_error();
			exit(EXIT_FAILURE);
		}
	}
}


cmdresult parallel_cmds(char *buf, char ***path)
{
	// Check for parallel commands
	char *cmd1 = strsep(&buf, "&");

	// If none found, process single command
	if (buf == NULL) {
		return get_cmd(cmd1, path);
	}

	// Otherwise, fork to execute the first command
	cmdresult res = CONTINUE;
	int rc = fork();

	if (rc < 0) {
		// Fork failed
		wish_error();
		return EXIT;
	} else if (rc == 0) {
		// Child: execute first command
		res *= get_cmd(cmd1, path);
		return EXIT;
	} else {
		// Parent: check second half for more parallel commands
		strip(&buf);
		res *= parallel_cmds(buf, path);
		wait(NULL);
		return res;
	}
}


cmdresult get_cmd(char *buf, char ***path)
{
	// Check for empty buffer
	if (buf == NULL) {
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
		return EXIT;
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
			return EXIT;
		}
		return CONTINUE;
	}

	// Check for a redirect; redir will be either the output filename or NULL;
	// buf will be everything before '>'
	char *redir = process_redirect(&buf);

	// Create the argument array
	char **argv = create_argv(cmd, buf, *path);
	if (argv == NULL) {
		wish_error();
		return EXIT;
	}

	// Run the command
	run_cmd(argv, redir);
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


void destroy_argv(char **argv)
{
	free(argv[0]);
	free(argv);
}


char **create_path(void)
{
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


void destroy_path(char **path)
{
	for (size_t i = 0; i < MAXPATHS; i++) {
		free(path[i]);
	}
	free(path);
}


char **update_path(char **path, char *buf)
{
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


char *process_redirect(char **buf)
{
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


ssize_t wish_error(void)
{
	static const char error_message[30] = "An error has occurred\n";
	return write(STDERR_FILENO, error_message, strlen(error_message));
}


void strip(char **str)
{
	// Remove trailing whitespace
	char *end = *str + strlen(*str) - 1;
	while (end > *str && isspace(*end)) end--;
	end[1] = '\0';
	// Remove leading whitespace
	while (isspace(**str)) (*str)++;
}
