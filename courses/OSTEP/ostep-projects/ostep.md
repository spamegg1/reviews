# Installation

## Getting `xv6` to work

```bash
sudo apt install qemu-system-x86 expect perl gawk build-essential
git clone https://github.com/mit-pdos/xv6-public
cd xv6-public
make qemu
```

## Doing Projects

For example, to do the `initial-xv6 ` project,

```bash
git clone https://github.com/remzi-arpacidusseau/ostep-projects/
cd ostep-projects/initial-xv6/
ls -l
```

and you'll see a folder named `src`. Now copy contents of `xv6-public-master` into the `/src` directory. 
