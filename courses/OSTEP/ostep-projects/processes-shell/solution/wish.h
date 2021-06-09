#ifndef WISH_H
#define WISH_H

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <fcntl.h>

#define MAXARGS 128
#define MAXPATHS 128
#define PATHLEN 128

typedef enum {
	EXIT,
	CONTINUE,
} cmdresult;


cmdresult parallel_cmds(char *buf, char ***path);
/* parallel_cmds: checks a shell command string buf for parallel commands. If
 * any are found, forks to execute the first one and recursively checks the rest
 * of the command string for more parallel commands. Otherwise, passes the
 * lone command to get_cmd(). Returns EXIT if any of the commands did, or
 * CONTINUE otherwise.
 */

cmdresult get_cmd(char *buf, char ***path);
/* get_cmd: takes a shell command buf and checks for calls to built-in commands
 * exit, cd, or path. Otherwise, checks for and handles output redirection
 * before executing the command with run_cmd(). Returns CONTINUE in order to
 * get the next command or EXIT to close the shell.
 */

void run_cmd(char **argv, char *redir);
/* run_cmd: forks to execute command argv[0] with arguments argv; the parent
 * process waits for the child to terminate before returning. If redir is not
 * NULL, redirects the command's output to that file. argv[0] must include the
 * path to the command, and the last entry of argv must be NULL.
 */

char **create_argv(char *cmd, char *buf, char **path);
/* create_argv: creates an array of arguments of size MAXARGS to pass to execv.
 * The first entry in the array is the path to the command to be executed; the
 * rest are option flags, parameters, and arguments; any empty entries are set
 * to NULL. Returns a pointer to the array upon successful construction;
 * otherwise, returns NULL. Note that the array created must be freed using
 * destroy_argv().
 */

void destroy_argv(char **args);
/* destroy_argv: frees an array of arguments created by create_argv().
 */

char **create_path(void);
/* create_path: creates an array of strings of size MAXPATHS where each entry
 * is a path in which to search for executables and binaries. The first entry
 * is "/bin"; all others are NULL. Returns a pointer to the array upon
 * successful construction; otherwise, returns NULL. Note that the array
 * created must be freed using destroy_path().
 */

void destroy_path(char **path);
/* destroy_path: frees an array of path strings created by create_path().
 */

char **update_path(char **path, char *buf);
/* update_path: replaces the current path array with new entries from the
 * whitespace-separated tokens in buf, up to a total of MAXPATHS. Each path
 * should be no longer than PATHLEN.
 */

char *process_redirect(char **buf);
/* process_redirect: checks for presence of a '>' character in *buf. If found,
 * replaces it with '\0' and returns a pointer to the next token, which should
 * be the output filename; otherwise returns NULL.
 */

ssize_t wish_error(void);
/* write_error: writes the string "An error has occurred\n" to STDERR_FILENO
 * before exiting the program with exit code EXIT_FAILURE.
 */

void strip(char **str);
/* strip: removes all leading and trailing whitespace from the string pointed
 * to by str.
 */


#endif
