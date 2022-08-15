# INSTRUCTIONS TO INSTALL SPECIFIC RUBY VERSION. UBUNTU AND UBUNTU WITH WSL2.

I followed these instructions to install `ruby 2.3` in my Ubuntu 20.04 machine running on wsl2. Native Ubuntu instructions should be the same.

## PRE-REQUISITES.

```
sudo apt-get install software-properties-common
```

We are going to use **Ruby Version Manager**. From their website: _"RVM is a command-line tool which allows you to easily install, manage, and work with multiple ruby environments from interpreters to sets of gems."_

## 1. ADD THE PPA AND INSTALL RVM.

```
sudo apt-add-repository -y ppa:rael-gc/rvm
sudo apt-get update
sudo apt-get install rvm
```

Once installed, we add our user to the `rvm` group:

```
sudo usermod -a -G rvm $USER
```

## 2. CHANGE TO OUR TERMINAL WINDOW.

In order to always load rvm, we change our Gnome Terminal to always perform a login (Ubuntu native terminal). If you use Zsh, check the RVM link in section 8 for troubleshooting.

At terminal window, click `Edit` > `Profile Preferences`, click on `Title and Command` tab and check `Run command as login shell`.

There is no need to do anything in the wsl2 terminal.

## 3. REBOOT

If you are in native Ubuntu, reboot the machine. If you are in wsl2:

- Logout (exit the shell).
- Open a cmd or powershell window and execute `wsl --shutdown`.
- Open terminal again.

At this point, you can check `rvm` installation by running `rvm -v`.

## 4. ENABLE LOCAL GEMSETS.

```
rvm user gemsets
```

This is like creating your local environment.

## 5. INSTALL RUBY 2.3 AND ENABLE IT.

```
rvm install 2.3
rvm use 2.3
```

## 6. CHECK RUBY INSTALLATION.

At this point, you are done. You can run the following commands to ensure everything went fine:

- `ruby -v` shows the ruby version.
- `irb` opens a Ruby REPL (to exit type `exit` or `quit`).

## 7. TO LOAD A FILE INTO THE REPL.

- Navigate within your terminal to the folder containing the `.rb` file.
- Use `irb` command to enter the REPL.
- Inside the REPL, type `load "my_file.rb"` and the REPL will open and eval the file.

Alternatively, you can just run the command from the terminal `ruby my_file.rb` just like with Python files.

## 8. SOURCES AND TROUBLESHOOTING.

[RVM](https://rvm.io/rvm/install)

[RVM github](https://github.com/rvm/ubuntu_rvm)

[Some Ruby instructions within the course](https://www.coursera.org/learn/programming-languages-part-c/supplement/ae1NA/part-c-software-installation-and-use-ruby-and-irb)

## 9. APPENDIX. INSTALLING TK.

Since it was a pain for me, i'm gonna share how i installed tk library:

- Install tk and tcl dev packages
```
sudo apt install tcl8.6-dev tk8.6-dev
```
- Install `tk` gem using this command (if you cannot properly copy and paste, remove the backslashes and paste the entire command without them):
```
gem install tk -- --with-tcltkversion=8.6 \
--with-tcl-lib=/usr/lib/x86_64-linux-gnu \
--with-tk-lib=/usr/lib/x86_64-linux-gnu \
--with-tcl-include=/usr/include/tcl8.6 \
--with-tk-include=/usr/include/tcl8.6 --enable-pthread
```
- If the above doesn't work, and you get this error `make: /usr/bin/mkdir: Command Not Found`,
make this symbolic link and try the installation again:
```
sudo ln -s /bin/mkdir /usr/bin/mkdir
```
Done. 1 gem installed.