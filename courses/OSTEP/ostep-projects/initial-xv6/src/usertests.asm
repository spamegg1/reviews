
_usertests:     file format elf32-i386


Disassembly of section .text:

00000000 <main>:
  return randstate;
}

int
main(int argc, char *argv[])
{
       0:	f3 0f 1e fb          	endbr32 
       4:	8d 4c 24 04          	lea    0x4(%esp),%ecx
       8:	83 e4 f0             	and    $0xfffffff0,%esp
       b:	ff 71 fc             	pushl  -0x4(%ecx)
       e:	55                   	push   %ebp
       f:	89 e5                	mov    %esp,%ebp
      11:	51                   	push   %ecx
      12:	83 ec 0c             	sub    $0xc,%esp
  printf(1, "usertests starting\n");
      15:	68 36 4e 00 00       	push   $0x4e36
      1a:	6a 01                	push   $0x1
      1c:	e8 bf 3a 00 00       	call   3ae0 <printf>

  if(open("usertests.ran", 0) >= 0){
      21:	59                   	pop    %ecx
      22:	58                   	pop    %eax
      23:	6a 00                	push   $0x0
      25:	68 4a 4e 00 00       	push   $0x4e4a
      2a:	e8 94 39 00 00       	call   39c3 <open>
      2f:	83 c4 10             	add    $0x10,%esp
      32:	85 c0                	test   %eax,%eax
      34:	78 13                	js     49 <main+0x49>
    printf(1, "already ran user tests -- rebuild fs.img\n");
      36:	52                   	push   %edx
      37:	52                   	push   %edx
      38:	68 b4 55 00 00       	push   $0x55b4
      3d:	6a 01                	push   $0x1
      3f:	e8 9c 3a 00 00       	call   3ae0 <printf>
    exit();
      44:	e8 3a 39 00 00       	call   3983 <exit>
  }
  close(open("usertests.ran", O_CREATE));
      49:	50                   	push   %eax
      4a:	50                   	push   %eax
      4b:	68 00 02 00 00       	push   $0x200
      50:	68 4a 4e 00 00       	push   $0x4e4a
      55:	e8 69 39 00 00       	call   39c3 <open>
      5a:	89 04 24             	mov    %eax,(%esp)
      5d:	e8 49 39 00 00       	call   39ab <close>

  argptest();
      62:	e8 29 36 00 00       	call   3690 <argptest>
  createdelete();
      67:	e8 04 12 00 00       	call   1270 <createdelete>
  linkunlink();
      6c:	e8 df 1a 00 00       	call   1b50 <linkunlink>
  concreate();
      71:	e8 da 17 00 00       	call   1850 <concreate>
  fourfiles();
      76:	e8 f5 0f 00 00       	call   1070 <fourfiles>
  sharedfd();
      7b:	e8 30 0e 00 00       	call   eb0 <sharedfd>

  bigargtest();
      80:	e8 ab 32 00 00       	call   3330 <bigargtest>
  bigwrite();
      85:	e8 06 24 00 00       	call   2490 <bigwrite>
  bigargtest();
      8a:	e8 a1 32 00 00       	call   3330 <bigargtest>
  bsstest();
      8f:	e8 2c 32 00 00       	call   32c0 <bsstest>
  sbrktest();
      94:	e8 37 2d 00 00       	call   2dd0 <sbrktest>
  validatetest();
      99:	e8 62 31 00 00       	call   3200 <validatetest>

  opentest();
      9e:	e8 6d 03 00 00       	call   410 <opentest>
  writetest();
      a3:	e8 08 04 00 00       	call   4b0 <writetest>
  writetest1();
      a8:	e8 e3 05 00 00       	call   690 <writetest1>
  createtest();
      ad:	e8 ae 07 00 00       	call   860 <createtest>

  openiputtest();
      b2:	e8 59 02 00 00       	call   310 <openiputtest>
  exitiputtest();
      b7:	e8 54 01 00 00       	call   210 <exitiputtest>
  iputtest();
      bc:	e8 5f 00 00 00       	call   120 <iputtest>

  mem();
      c1:	e8 1a 0d 00 00       	call   de0 <mem>
  pipe1();
      c6:	e8 95 09 00 00       	call   a60 <pipe1>
  preempt();
      cb:	e8 30 0b 00 00       	call   c00 <preempt>
  exitwait();
      d0:	e8 8b 0c 00 00       	call   d60 <exitwait>

  rmdot();
      d5:	e8 a6 27 00 00       	call   2880 <rmdot>
  fourteen();
      da:	e8 61 26 00 00       	call   2740 <fourteen>
  bigfile();
      df:	e8 8c 24 00 00       	call   2570 <bigfile>
  subdir();
      e4:	e8 b7 1c 00 00       	call   1da0 <subdir>
  linktest();
      e9:	e8 42 15 00 00       	call   1630 <linktest>
  unlinkread();
      ee:	e8 ad 13 00 00       	call   14a0 <unlinkread>
  dirfile();
      f3:	e8 08 29 00 00       	call   2a00 <dirfile>
  iref();
      f8:	e8 03 2b 00 00       	call   2c00 <iref>
  forktest();
      fd:	e8 1e 2c 00 00       	call   2d20 <forktest>
  bigdir(); // slow
     102:	e8 59 1b 00 00       	call   1c60 <bigdir>

  uio();
     107:	e8 04 35 00 00       	call   3610 <uio>

  exectest();
     10c:	e8 ff 08 00 00       	call   a10 <exectest>

  exit();
     111:	e8 6d 38 00 00       	call   3983 <exit>
     116:	66 90                	xchg   %ax,%ax
     118:	66 90                	xchg   %ax,%ax
     11a:	66 90                	xchg   %ax,%ax
     11c:	66 90                	xchg   %ax,%ax
     11e:	66 90                	xchg   %ax,%ax

00000120 <iputtest>:
{
     120:	f3 0f 1e fb          	endbr32 
     124:	55                   	push   %ebp
     125:	89 e5                	mov    %esp,%ebp
     127:	83 ec 10             	sub    $0x10,%esp
  printf(stdout, "iput test\n");
     12a:	68 dc 3e 00 00       	push   $0x3edc
     12f:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     135:	e8 a6 39 00 00       	call   3ae0 <printf>
  if(mkdir("iputdir") < 0){
     13a:	c7 04 24 6f 3e 00 00 	movl   $0x3e6f,(%esp)
     141:	e8 a5 38 00 00       	call   39eb <mkdir>
     146:	83 c4 10             	add    $0x10,%esp
     149:	85 c0                	test   %eax,%eax
     14b:	78 58                	js     1a5 <iputtest+0x85>
  if(chdir("iputdir") < 0){
     14d:	83 ec 0c             	sub    $0xc,%esp
     150:	68 6f 3e 00 00       	push   $0x3e6f
     155:	e8 99 38 00 00       	call   39f3 <chdir>
     15a:	83 c4 10             	add    $0x10,%esp
     15d:	85 c0                	test   %eax,%eax
     15f:	0f 88 85 00 00 00    	js     1ea <iputtest+0xca>
  if(unlink("../iputdir") < 0){
     165:	83 ec 0c             	sub    $0xc,%esp
     168:	68 6c 3e 00 00       	push   $0x3e6c
     16d:	e8 61 38 00 00       	call   39d3 <unlink>
     172:	83 c4 10             	add    $0x10,%esp
     175:	85 c0                	test   %eax,%eax
     177:	78 5a                	js     1d3 <iputtest+0xb3>
  if(chdir("/") < 0){
     179:	83 ec 0c             	sub    $0xc,%esp
     17c:	68 91 3e 00 00       	push   $0x3e91
     181:	e8 6d 38 00 00       	call   39f3 <chdir>
     186:	83 c4 10             	add    $0x10,%esp
     189:	85 c0                	test   %eax,%eax
     18b:	78 2f                	js     1bc <iputtest+0x9c>
  printf(stdout, "iput test ok\n");
     18d:	83 ec 08             	sub    $0x8,%esp
     190:	68 14 3f 00 00       	push   $0x3f14
     195:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     19b:	e8 40 39 00 00       	call   3ae0 <printf>
}
     1a0:	83 c4 10             	add    $0x10,%esp
     1a3:	c9                   	leave  
     1a4:	c3                   	ret    
    printf(stdout, "mkdir failed\n");
     1a5:	50                   	push   %eax
     1a6:	50                   	push   %eax
     1a7:	68 48 3e 00 00       	push   $0x3e48
     1ac:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     1b2:	e8 29 39 00 00       	call   3ae0 <printf>
    exit();
     1b7:	e8 c7 37 00 00       	call   3983 <exit>
    printf(stdout, "chdir / failed\n");
     1bc:	50                   	push   %eax
     1bd:	50                   	push   %eax
     1be:	68 93 3e 00 00       	push   $0x3e93
     1c3:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     1c9:	e8 12 39 00 00       	call   3ae0 <printf>
    exit();
     1ce:	e8 b0 37 00 00       	call   3983 <exit>
    printf(stdout, "unlink ../iputdir failed\n");
     1d3:	52                   	push   %edx
     1d4:	52                   	push   %edx
     1d5:	68 77 3e 00 00       	push   $0x3e77
     1da:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     1e0:	e8 fb 38 00 00       	call   3ae0 <printf>
    exit();
     1e5:	e8 99 37 00 00       	call   3983 <exit>
    printf(stdout, "chdir iputdir failed\n");
     1ea:	51                   	push   %ecx
     1eb:	51                   	push   %ecx
     1ec:	68 56 3e 00 00       	push   $0x3e56
     1f1:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     1f7:	e8 e4 38 00 00       	call   3ae0 <printf>
    exit();
     1fc:	e8 82 37 00 00       	call   3983 <exit>
     201:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
     208:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
     20f:	90                   	nop

00000210 <exitiputtest>:
{
     210:	f3 0f 1e fb          	endbr32 
     214:	55                   	push   %ebp
     215:	89 e5                	mov    %esp,%ebp
     217:	83 ec 10             	sub    $0x10,%esp
  printf(stdout, "exitiput test\n");
     21a:	68 a3 3e 00 00       	push   $0x3ea3
     21f:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     225:	e8 b6 38 00 00       	call   3ae0 <printf>
  pid = fork();
     22a:	e8 4c 37 00 00       	call   397b <fork>
  if(pid < 0){
     22f:	83 c4 10             	add    $0x10,%esp
     232:	85 c0                	test   %eax,%eax
     234:	0f 88 86 00 00 00    	js     2c0 <exitiputtest+0xb0>
  if(pid == 0){
     23a:	75 4c                	jne    288 <exitiputtest+0x78>
    if(mkdir("iputdir") < 0){
     23c:	83 ec 0c             	sub    $0xc,%esp
     23f:	68 6f 3e 00 00       	push   $0x3e6f
     244:	e8 a2 37 00 00       	call   39eb <mkdir>
     249:	83 c4 10             	add    $0x10,%esp
     24c:	85 c0                	test   %eax,%eax
     24e:	0f 88 83 00 00 00    	js     2d7 <exitiputtest+0xc7>
    if(chdir("iputdir") < 0){
     254:	83 ec 0c             	sub    $0xc,%esp
     257:	68 6f 3e 00 00       	push   $0x3e6f
     25c:	e8 92 37 00 00       	call   39f3 <chdir>
     261:	83 c4 10             	add    $0x10,%esp
     264:	85 c0                	test   %eax,%eax
     266:	0f 88 82 00 00 00    	js     2ee <exitiputtest+0xde>
    if(unlink("../iputdir") < 0){
     26c:	83 ec 0c             	sub    $0xc,%esp
     26f:	68 6c 3e 00 00       	push   $0x3e6c
     274:	e8 5a 37 00 00       	call   39d3 <unlink>
     279:	83 c4 10             	add    $0x10,%esp
     27c:	85 c0                	test   %eax,%eax
     27e:	78 28                	js     2a8 <exitiputtest+0x98>
    exit();
     280:	e8 fe 36 00 00       	call   3983 <exit>
     285:	8d 76 00             	lea    0x0(%esi),%esi
  wait();
     288:	e8 fe 36 00 00       	call   398b <wait>
  printf(stdout, "exitiput test ok\n");
     28d:	83 ec 08             	sub    $0x8,%esp
     290:	68 c6 3e 00 00       	push   $0x3ec6
     295:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     29b:	e8 40 38 00 00       	call   3ae0 <printf>
}
     2a0:	83 c4 10             	add    $0x10,%esp
     2a3:	c9                   	leave  
     2a4:	c3                   	ret    
     2a5:	8d 76 00             	lea    0x0(%esi),%esi
      printf(stdout, "unlink ../iputdir failed\n");
     2a8:	83 ec 08             	sub    $0x8,%esp
     2ab:	68 77 3e 00 00       	push   $0x3e77
     2b0:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     2b6:	e8 25 38 00 00       	call   3ae0 <printf>
      exit();
     2bb:	e8 c3 36 00 00       	call   3983 <exit>
    printf(stdout, "fork failed\n");
     2c0:	51                   	push   %ecx
     2c1:	51                   	push   %ecx
     2c2:	68 89 4d 00 00       	push   $0x4d89
     2c7:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     2cd:	e8 0e 38 00 00       	call   3ae0 <printf>
    exit();
     2d2:	e8 ac 36 00 00       	call   3983 <exit>
      printf(stdout, "mkdir failed\n");
     2d7:	52                   	push   %edx
     2d8:	52                   	push   %edx
     2d9:	68 48 3e 00 00       	push   $0x3e48
     2de:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     2e4:	e8 f7 37 00 00       	call   3ae0 <printf>
      exit();
     2e9:	e8 95 36 00 00       	call   3983 <exit>
      printf(stdout, "child chdir failed\n");
     2ee:	50                   	push   %eax
     2ef:	50                   	push   %eax
     2f0:	68 b2 3e 00 00       	push   $0x3eb2
     2f5:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     2fb:	e8 e0 37 00 00       	call   3ae0 <printf>
      exit();
     300:	e8 7e 36 00 00       	call   3983 <exit>
     305:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
     30c:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi

00000310 <openiputtest>:
{
     310:	f3 0f 1e fb          	endbr32 
     314:	55                   	push   %ebp
     315:	89 e5                	mov    %esp,%ebp
     317:	83 ec 10             	sub    $0x10,%esp
  printf(stdout, "openiput test\n");
     31a:	68 d8 3e 00 00       	push   $0x3ed8
     31f:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     325:	e8 b6 37 00 00       	call   3ae0 <printf>
  if(mkdir("oidir") < 0){
     32a:	c7 04 24 e7 3e 00 00 	movl   $0x3ee7,(%esp)
     331:	e8 b5 36 00 00       	call   39eb <mkdir>
     336:	83 c4 10             	add    $0x10,%esp
     339:	85 c0                	test   %eax,%eax
     33b:	0f 88 9b 00 00 00    	js     3dc <openiputtest+0xcc>
  pid = fork();
     341:	e8 35 36 00 00       	call   397b <fork>
  if(pid < 0){
     346:	85 c0                	test   %eax,%eax
     348:	78 7b                	js     3c5 <openiputtest+0xb5>
  if(pid == 0){
     34a:	75 34                	jne    380 <openiputtest+0x70>
    int fd = open("oidir", O_RDWR);
     34c:	83 ec 08             	sub    $0x8,%esp
     34f:	6a 02                	push   $0x2
     351:	68 e7 3e 00 00       	push   $0x3ee7
     356:	e8 68 36 00 00       	call   39c3 <open>
    if(fd >= 0){
     35b:	83 c4 10             	add    $0x10,%esp
     35e:	85 c0                	test   %eax,%eax
     360:	78 5e                	js     3c0 <openiputtest+0xb0>
      printf(stdout, "open directory for write succeeded\n");
     362:	83 ec 08             	sub    $0x8,%esp
     365:	68 6c 4e 00 00       	push   $0x4e6c
     36a:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     370:	e8 6b 37 00 00       	call   3ae0 <printf>
      exit();
     375:	e8 09 36 00 00       	call   3983 <exit>
     37a:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi
  sleep(1);
     380:	83 ec 0c             	sub    $0xc,%esp
     383:	6a 01                	push   $0x1
     385:	e8 89 36 00 00       	call   3a13 <sleep>
  if(unlink("oidir") != 0){
     38a:	c7 04 24 e7 3e 00 00 	movl   $0x3ee7,(%esp)
     391:	e8 3d 36 00 00       	call   39d3 <unlink>
     396:	83 c4 10             	add    $0x10,%esp
     399:	85 c0                	test   %eax,%eax
     39b:	75 56                	jne    3f3 <openiputtest+0xe3>
  wait();
     39d:	e8 e9 35 00 00       	call   398b <wait>
  printf(stdout, "openiput test ok\n");
     3a2:	83 ec 08             	sub    $0x8,%esp
     3a5:	68 10 3f 00 00       	push   $0x3f10
     3aa:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     3b0:	e8 2b 37 00 00       	call   3ae0 <printf>
     3b5:	83 c4 10             	add    $0x10,%esp
}
     3b8:	c9                   	leave  
     3b9:	c3                   	ret    
     3ba:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi
    exit();
     3c0:	e8 be 35 00 00       	call   3983 <exit>
    printf(stdout, "fork failed\n");
     3c5:	52                   	push   %edx
     3c6:	52                   	push   %edx
     3c7:	68 89 4d 00 00       	push   $0x4d89
     3cc:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     3d2:	e8 09 37 00 00       	call   3ae0 <printf>
    exit();
     3d7:	e8 a7 35 00 00       	call   3983 <exit>
    printf(stdout, "mkdir oidir failed\n");
     3dc:	51                   	push   %ecx
     3dd:	51                   	push   %ecx
     3de:	68 ed 3e 00 00       	push   $0x3eed
     3e3:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     3e9:	e8 f2 36 00 00       	call   3ae0 <printf>
    exit();
     3ee:	e8 90 35 00 00       	call   3983 <exit>
    printf(stdout, "unlink failed\n");
     3f3:	50                   	push   %eax
     3f4:	50                   	push   %eax
     3f5:	68 01 3f 00 00       	push   $0x3f01
     3fa:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     400:	e8 db 36 00 00       	call   3ae0 <printf>
    exit();
     405:	e8 79 35 00 00       	call   3983 <exit>
     40a:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi

00000410 <opentest>:
{
     410:	f3 0f 1e fb          	endbr32 
     414:	55                   	push   %ebp
     415:	89 e5                	mov    %esp,%ebp
     417:	83 ec 10             	sub    $0x10,%esp
  printf(stdout, "open test\n");
     41a:	68 22 3f 00 00       	push   $0x3f22
     41f:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     425:	e8 b6 36 00 00       	call   3ae0 <printf>
  fd = open("echo", 0);
     42a:	58                   	pop    %eax
     42b:	5a                   	pop    %edx
     42c:	6a 00                	push   $0x0
     42e:	68 2d 3f 00 00       	push   $0x3f2d
     433:	e8 8b 35 00 00       	call   39c3 <open>
  if(fd < 0){
     438:	83 c4 10             	add    $0x10,%esp
     43b:	85 c0                	test   %eax,%eax
     43d:	78 36                	js     475 <opentest+0x65>
  close(fd);
     43f:	83 ec 0c             	sub    $0xc,%esp
     442:	50                   	push   %eax
     443:	e8 63 35 00 00       	call   39ab <close>
  fd = open("doesnotexist", 0);
     448:	5a                   	pop    %edx
     449:	59                   	pop    %ecx
     44a:	6a 00                	push   $0x0
     44c:	68 45 3f 00 00       	push   $0x3f45
     451:	e8 6d 35 00 00       	call   39c3 <open>
  if(fd >= 0){
     456:	83 c4 10             	add    $0x10,%esp
     459:	85 c0                	test   %eax,%eax
     45b:	79 2f                	jns    48c <opentest+0x7c>
  printf(stdout, "open test ok\n");
     45d:	83 ec 08             	sub    $0x8,%esp
     460:	68 70 3f 00 00       	push   $0x3f70
     465:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     46b:	e8 70 36 00 00       	call   3ae0 <printf>
}
     470:	83 c4 10             	add    $0x10,%esp
     473:	c9                   	leave  
     474:	c3                   	ret    
    printf(stdout, "open echo failed!\n");
     475:	50                   	push   %eax
     476:	50                   	push   %eax
     477:	68 32 3f 00 00       	push   $0x3f32
     47c:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     482:	e8 59 36 00 00       	call   3ae0 <printf>
    exit();
     487:	e8 f7 34 00 00       	call   3983 <exit>
    printf(stdout, "open doesnotexist succeeded!\n");
     48c:	50                   	push   %eax
     48d:	50                   	push   %eax
     48e:	68 52 3f 00 00       	push   $0x3f52
     493:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     499:	e8 42 36 00 00       	call   3ae0 <printf>
    exit();
     49e:	e8 e0 34 00 00       	call   3983 <exit>
     4a3:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
     4aa:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi

000004b0 <writetest>:
{
     4b0:	f3 0f 1e fb          	endbr32 
     4b4:	55                   	push   %ebp
     4b5:	89 e5                	mov    %esp,%ebp
     4b7:	56                   	push   %esi
     4b8:	53                   	push   %ebx
  printf(stdout, "small file test\n");
     4b9:	83 ec 08             	sub    $0x8,%esp
     4bc:	68 7e 3f 00 00       	push   $0x3f7e
     4c1:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     4c7:	e8 14 36 00 00       	call   3ae0 <printf>
  fd = open("small", O_CREATE|O_RDWR);
     4cc:	58                   	pop    %eax
     4cd:	5a                   	pop    %edx
     4ce:	68 02 02 00 00       	push   $0x202
     4d3:	68 8f 3f 00 00       	push   $0x3f8f
     4d8:	e8 e6 34 00 00       	call   39c3 <open>
  if(fd >= 0){
     4dd:	83 c4 10             	add    $0x10,%esp
     4e0:	85 c0                	test   %eax,%eax
     4e2:	0f 88 8c 01 00 00    	js     674 <writetest+0x1c4>
    printf(stdout, "creat small succeeded; ok\n");
     4e8:	83 ec 08             	sub    $0x8,%esp
     4eb:	89 c6                	mov    %eax,%esi
  for(i = 0; i < 100; i++){
     4ed:	31 db                	xor    %ebx,%ebx
    printf(stdout, "creat small succeeded; ok\n");
     4ef:	68 95 3f 00 00       	push   $0x3f95
     4f4:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     4fa:	e8 e1 35 00 00       	call   3ae0 <printf>
     4ff:	83 c4 10             	add    $0x10,%esp
     502:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi
    if(write(fd, "aaaaaaaaaa", 10) != 10){
     508:	83 ec 04             	sub    $0x4,%esp
     50b:	6a 0a                	push   $0xa
     50d:	68 cc 3f 00 00       	push   $0x3fcc
     512:	56                   	push   %esi
     513:	e8 8b 34 00 00       	call   39a3 <write>
     518:	83 c4 10             	add    $0x10,%esp
     51b:	83 f8 0a             	cmp    $0xa,%eax
     51e:	0f 85 d9 00 00 00    	jne    5fd <writetest+0x14d>
    if(write(fd, "bbbbbbbbbb", 10) != 10){
     524:	83 ec 04             	sub    $0x4,%esp
     527:	6a 0a                	push   $0xa
     529:	68 d7 3f 00 00       	push   $0x3fd7
     52e:	56                   	push   %esi
     52f:	e8 6f 34 00 00       	call   39a3 <write>
     534:	83 c4 10             	add    $0x10,%esp
     537:	83 f8 0a             	cmp    $0xa,%eax
     53a:	0f 85 d6 00 00 00    	jne    616 <writetest+0x166>
  for(i = 0; i < 100; i++){
     540:	83 c3 01             	add    $0x1,%ebx
     543:	83 fb 64             	cmp    $0x64,%ebx
     546:	75 c0                	jne    508 <writetest+0x58>
  printf(stdout, "writes ok\n");
     548:	83 ec 08             	sub    $0x8,%esp
     54b:	68 e2 3f 00 00       	push   $0x3fe2
     550:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     556:	e8 85 35 00 00       	call   3ae0 <printf>
  close(fd);
     55b:	89 34 24             	mov    %esi,(%esp)
     55e:	e8 48 34 00 00       	call   39ab <close>
  fd = open("small", O_RDONLY);
     563:	5b                   	pop    %ebx
     564:	5e                   	pop    %esi
     565:	6a 00                	push   $0x0
     567:	68 8f 3f 00 00       	push   $0x3f8f
     56c:	e8 52 34 00 00       	call   39c3 <open>
  if(fd >= 0){
     571:	83 c4 10             	add    $0x10,%esp
  fd = open("small", O_RDONLY);
     574:	89 c3                	mov    %eax,%ebx
  if(fd >= 0){
     576:	85 c0                	test   %eax,%eax
     578:	0f 88 b1 00 00 00    	js     62f <writetest+0x17f>
    printf(stdout, "open small succeeded ok\n");
     57e:	83 ec 08             	sub    $0x8,%esp
     581:	68 ed 3f 00 00       	push   $0x3fed
     586:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     58c:	e8 4f 35 00 00       	call   3ae0 <printf>
  i = read(fd, buf, 2000);
     591:	83 c4 0c             	add    $0xc,%esp
     594:	68 d0 07 00 00       	push   $0x7d0
     599:	68 c0 86 00 00       	push   $0x86c0
     59e:	53                   	push   %ebx
     59f:	e8 f7 33 00 00       	call   399b <read>
  if(i == 2000){
     5a4:	83 c4 10             	add    $0x10,%esp
     5a7:	3d d0 07 00 00       	cmp    $0x7d0,%eax
     5ac:	0f 85 94 00 00 00    	jne    646 <writetest+0x196>
    printf(stdout, "read succeeded ok\n");
     5b2:	83 ec 08             	sub    $0x8,%esp
     5b5:	68 21 40 00 00       	push   $0x4021
     5ba:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     5c0:	e8 1b 35 00 00       	call   3ae0 <printf>
  close(fd);
     5c5:	89 1c 24             	mov    %ebx,(%esp)
     5c8:	e8 de 33 00 00       	call   39ab <close>
  if(unlink("small") < 0){
     5cd:	c7 04 24 8f 3f 00 00 	movl   $0x3f8f,(%esp)
     5d4:	e8 fa 33 00 00       	call   39d3 <unlink>
     5d9:	83 c4 10             	add    $0x10,%esp
     5dc:	85 c0                	test   %eax,%eax
     5de:	78 7d                	js     65d <writetest+0x1ad>
  printf(stdout, "small file test ok\n");
     5e0:	83 ec 08             	sub    $0x8,%esp
     5e3:	68 49 40 00 00       	push   $0x4049
     5e8:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     5ee:	e8 ed 34 00 00       	call   3ae0 <printf>
}
     5f3:	83 c4 10             	add    $0x10,%esp
     5f6:	8d 65 f8             	lea    -0x8(%ebp),%esp
     5f9:	5b                   	pop    %ebx
     5fa:	5e                   	pop    %esi
     5fb:	5d                   	pop    %ebp
     5fc:	c3                   	ret    
      printf(stdout, "error: write aa %d new file failed\n", i);
     5fd:	83 ec 04             	sub    $0x4,%esp
     600:	53                   	push   %ebx
     601:	68 90 4e 00 00       	push   $0x4e90
     606:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     60c:	e8 cf 34 00 00       	call   3ae0 <printf>
      exit();
     611:	e8 6d 33 00 00       	call   3983 <exit>
      printf(stdout, "error: write bb %d new file failed\n", i);
     616:	83 ec 04             	sub    $0x4,%esp
     619:	53                   	push   %ebx
     61a:	68 b4 4e 00 00       	push   $0x4eb4
     61f:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     625:	e8 b6 34 00 00       	call   3ae0 <printf>
      exit();
     62a:	e8 54 33 00 00       	call   3983 <exit>
    printf(stdout, "error: open small failed!\n");
     62f:	51                   	push   %ecx
     630:	51                   	push   %ecx
     631:	68 06 40 00 00       	push   $0x4006
     636:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     63c:	e8 9f 34 00 00       	call   3ae0 <printf>
    exit();
     641:	e8 3d 33 00 00       	call   3983 <exit>
    printf(stdout, "read failed\n");
     646:	52                   	push   %edx
     647:	52                   	push   %edx
     648:	68 4d 43 00 00       	push   $0x434d
     64d:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     653:	e8 88 34 00 00       	call   3ae0 <printf>
    exit();
     658:	e8 26 33 00 00       	call   3983 <exit>
    printf(stdout, "unlink small failed\n");
     65d:	50                   	push   %eax
     65e:	50                   	push   %eax
     65f:	68 34 40 00 00       	push   $0x4034
     664:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     66a:	e8 71 34 00 00       	call   3ae0 <printf>
    exit();
     66f:	e8 0f 33 00 00       	call   3983 <exit>
    printf(stdout, "error: creat small failed!\n");
     674:	50                   	push   %eax
     675:	50                   	push   %eax
     676:	68 b0 3f 00 00       	push   $0x3fb0
     67b:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     681:	e8 5a 34 00 00       	call   3ae0 <printf>
    exit();
     686:	e8 f8 32 00 00       	call   3983 <exit>
     68b:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
     68f:	90                   	nop

00000690 <writetest1>:
{
     690:	f3 0f 1e fb          	endbr32 
     694:	55                   	push   %ebp
     695:	89 e5                	mov    %esp,%ebp
     697:	56                   	push   %esi
     698:	53                   	push   %ebx
  printf(stdout, "big files test\n");
     699:	83 ec 08             	sub    $0x8,%esp
     69c:	68 5d 40 00 00       	push   $0x405d
     6a1:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     6a7:	e8 34 34 00 00       	call   3ae0 <printf>
  fd = open("big", O_CREATE|O_RDWR);
     6ac:	58                   	pop    %eax
     6ad:	5a                   	pop    %edx
     6ae:	68 02 02 00 00       	push   $0x202
     6b3:	68 d7 40 00 00       	push   $0x40d7
     6b8:	e8 06 33 00 00       	call   39c3 <open>
  if(fd < 0){
     6bd:	83 c4 10             	add    $0x10,%esp
     6c0:	85 c0                	test   %eax,%eax
     6c2:	0f 88 5d 01 00 00    	js     825 <writetest1+0x195>
     6c8:	89 c6                	mov    %eax,%esi
  for(i = 0; i < MAXFILE; i++){
     6ca:	31 db                	xor    %ebx,%ebx
     6cc:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
    if(write(fd, buf, 512) != 512){
     6d0:	83 ec 04             	sub    $0x4,%esp
    ((int*)buf)[0] = i;
     6d3:	89 1d c0 86 00 00    	mov    %ebx,0x86c0
    if(write(fd, buf, 512) != 512){
     6d9:	68 00 02 00 00       	push   $0x200
     6de:	68 c0 86 00 00       	push   $0x86c0
     6e3:	56                   	push   %esi
     6e4:	e8 ba 32 00 00       	call   39a3 <write>
     6e9:	83 c4 10             	add    $0x10,%esp
     6ec:	3d 00 02 00 00       	cmp    $0x200,%eax
     6f1:	0f 85 b3 00 00 00    	jne    7aa <writetest1+0x11a>
  for(i = 0; i < MAXFILE; i++){
     6f7:	83 c3 01             	add    $0x1,%ebx
     6fa:	81 fb 8c 00 00 00    	cmp    $0x8c,%ebx
     700:	75 ce                	jne    6d0 <writetest1+0x40>
  close(fd);
     702:	83 ec 0c             	sub    $0xc,%esp
     705:	56                   	push   %esi
     706:	e8 a0 32 00 00       	call   39ab <close>
  fd = open("big", O_RDONLY);
     70b:	5b                   	pop    %ebx
     70c:	5e                   	pop    %esi
     70d:	6a 00                	push   $0x0
     70f:	68 d7 40 00 00       	push   $0x40d7
     714:	e8 aa 32 00 00       	call   39c3 <open>
  if(fd < 0){
     719:	83 c4 10             	add    $0x10,%esp
  fd = open("big", O_RDONLY);
     71c:	89 c3                	mov    %eax,%ebx
  if(fd < 0){
     71e:	85 c0                	test   %eax,%eax
     720:	0f 88 e8 00 00 00    	js     80e <writetest1+0x17e>
  n = 0;
     726:	31 f6                	xor    %esi,%esi
     728:	eb 1d                	jmp    747 <writetest1+0xb7>
     72a:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi
    } else if(i != 512){
     730:	3d 00 02 00 00       	cmp    $0x200,%eax
     735:	0f 85 9f 00 00 00    	jne    7da <writetest1+0x14a>
    if(((int*)buf)[0] != n){
     73b:	a1 c0 86 00 00       	mov    0x86c0,%eax
     740:	39 f0                	cmp    %esi,%eax
     742:	75 7f                	jne    7c3 <writetest1+0x133>
    n++;
     744:	83 c6 01             	add    $0x1,%esi
    i = read(fd, buf, 512);
     747:	83 ec 04             	sub    $0x4,%esp
     74a:	68 00 02 00 00       	push   $0x200
     74f:	68 c0 86 00 00       	push   $0x86c0
     754:	53                   	push   %ebx
     755:	e8 41 32 00 00       	call   399b <read>
    if(i == 0){
     75a:	83 c4 10             	add    $0x10,%esp
     75d:	85 c0                	test   %eax,%eax
     75f:	75 cf                	jne    730 <writetest1+0xa0>
      if(n == MAXFILE - 1){
     761:	81 fe 8b 00 00 00    	cmp    $0x8b,%esi
     767:	0f 84 86 00 00 00    	je     7f3 <writetest1+0x163>
  close(fd);
     76d:	83 ec 0c             	sub    $0xc,%esp
     770:	53                   	push   %ebx
     771:	e8 35 32 00 00       	call   39ab <close>
  if(unlink("big") < 0){
     776:	c7 04 24 d7 40 00 00 	movl   $0x40d7,(%esp)
     77d:	e8 51 32 00 00       	call   39d3 <unlink>
     782:	83 c4 10             	add    $0x10,%esp
     785:	85 c0                	test   %eax,%eax
     787:	0f 88 af 00 00 00    	js     83c <writetest1+0x1ac>
  printf(stdout, "big files ok\n");
     78d:	83 ec 08             	sub    $0x8,%esp
     790:	68 fe 40 00 00       	push   $0x40fe
     795:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     79b:	e8 40 33 00 00       	call   3ae0 <printf>
}
     7a0:	83 c4 10             	add    $0x10,%esp
     7a3:	8d 65 f8             	lea    -0x8(%ebp),%esp
     7a6:	5b                   	pop    %ebx
     7a7:	5e                   	pop    %esi
     7a8:	5d                   	pop    %ebp
     7a9:	c3                   	ret    
      printf(stdout, "error: write big file failed\n", i);
     7aa:	83 ec 04             	sub    $0x4,%esp
     7ad:	53                   	push   %ebx
     7ae:	68 87 40 00 00       	push   $0x4087
     7b3:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     7b9:	e8 22 33 00 00       	call   3ae0 <printf>
      exit();
     7be:	e8 c0 31 00 00       	call   3983 <exit>
      printf(stdout, "read content of block %d is %d\n",
     7c3:	50                   	push   %eax
     7c4:	56                   	push   %esi
     7c5:	68 d8 4e 00 00       	push   $0x4ed8
     7ca:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     7d0:	e8 0b 33 00 00       	call   3ae0 <printf>
      exit();
     7d5:	e8 a9 31 00 00       	call   3983 <exit>
      printf(stdout, "read failed %d\n", i);
     7da:	83 ec 04             	sub    $0x4,%esp
     7dd:	50                   	push   %eax
     7de:	68 db 40 00 00       	push   $0x40db
     7e3:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     7e9:	e8 f2 32 00 00       	call   3ae0 <printf>
      exit();
     7ee:	e8 90 31 00 00       	call   3983 <exit>
        printf(stdout, "read only %d blocks from big", n);
     7f3:	52                   	push   %edx
     7f4:	68 8b 00 00 00       	push   $0x8b
     7f9:	68 be 40 00 00       	push   $0x40be
     7fe:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     804:	e8 d7 32 00 00       	call   3ae0 <printf>
        exit();
     809:	e8 75 31 00 00       	call   3983 <exit>
    printf(stdout, "error: open big failed!\n");
     80e:	51                   	push   %ecx
     80f:	51                   	push   %ecx
     810:	68 a5 40 00 00       	push   $0x40a5
     815:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     81b:	e8 c0 32 00 00       	call   3ae0 <printf>
    exit();
     820:	e8 5e 31 00 00       	call   3983 <exit>
    printf(stdout, "error: creat big failed!\n");
     825:	50                   	push   %eax
     826:	50                   	push   %eax
     827:	68 6d 40 00 00       	push   $0x406d
     82c:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     832:	e8 a9 32 00 00       	call   3ae0 <printf>
    exit();
     837:	e8 47 31 00 00       	call   3983 <exit>
    printf(stdout, "unlink big failed\n");
     83c:	50                   	push   %eax
     83d:	50                   	push   %eax
     83e:	68 eb 40 00 00       	push   $0x40eb
     843:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     849:	e8 92 32 00 00       	call   3ae0 <printf>
    exit();
     84e:	e8 30 31 00 00       	call   3983 <exit>
     853:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
     85a:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi

00000860 <createtest>:
{
     860:	f3 0f 1e fb          	endbr32 
     864:	55                   	push   %ebp
     865:	89 e5                	mov    %esp,%ebp
     867:	53                   	push   %ebx
  name[2] = '\0';
     868:	bb 30 00 00 00       	mov    $0x30,%ebx
{
     86d:	83 ec 0c             	sub    $0xc,%esp
  printf(stdout, "many creates, followed by unlink test\n");
     870:	68 f8 4e 00 00       	push   $0x4ef8
     875:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     87b:	e8 60 32 00 00       	call   3ae0 <printf>
  name[0] = 'a';
     880:	c6 05 c0 a6 00 00 61 	movb   $0x61,0xa6c0
  name[2] = '\0';
     887:	83 c4 10             	add    $0x10,%esp
     88a:	c6 05 c2 a6 00 00 00 	movb   $0x0,0xa6c2
  for(i = 0; i < 52; i++){
     891:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    fd = open(name, O_CREATE|O_RDWR);
     898:	83 ec 08             	sub    $0x8,%esp
    name[1] = '0' + i;
     89b:	88 1d c1 a6 00 00    	mov    %bl,0xa6c1
    fd = open(name, O_CREATE|O_RDWR);
     8a1:	83 c3 01             	add    $0x1,%ebx
     8a4:	68 02 02 00 00       	push   $0x202
     8a9:	68 c0 a6 00 00       	push   $0xa6c0
     8ae:	e8 10 31 00 00       	call   39c3 <open>
    close(fd);
     8b3:	89 04 24             	mov    %eax,(%esp)
     8b6:	e8 f0 30 00 00       	call   39ab <close>
  for(i = 0; i < 52; i++){
     8bb:	83 c4 10             	add    $0x10,%esp
     8be:	80 fb 64             	cmp    $0x64,%bl
     8c1:	75 d5                	jne    898 <createtest+0x38>
  name[0] = 'a';
     8c3:	c6 05 c0 a6 00 00 61 	movb   $0x61,0xa6c0
  name[2] = '\0';
     8ca:	bb 30 00 00 00       	mov    $0x30,%ebx
     8cf:	c6 05 c2 a6 00 00 00 	movb   $0x0,0xa6c2
  for(i = 0; i < 52; i++){
     8d6:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
     8dd:	8d 76 00             	lea    0x0(%esi),%esi
    unlink(name);
     8e0:	83 ec 0c             	sub    $0xc,%esp
    name[1] = '0' + i;
     8e3:	88 1d c1 a6 00 00    	mov    %bl,0xa6c1
    unlink(name);
     8e9:	83 c3 01             	add    $0x1,%ebx
     8ec:	68 c0 a6 00 00       	push   $0xa6c0
     8f1:	e8 dd 30 00 00       	call   39d3 <unlink>
  for(i = 0; i < 52; i++){
     8f6:	83 c4 10             	add    $0x10,%esp
     8f9:	80 fb 64             	cmp    $0x64,%bl
     8fc:	75 e2                	jne    8e0 <createtest+0x80>
  printf(stdout, "many creates, followed by unlink; ok\n");
     8fe:	83 ec 08             	sub    $0x8,%esp
     901:	68 20 4f 00 00       	push   $0x4f20
     906:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     90c:	e8 cf 31 00 00       	call   3ae0 <printf>
}
     911:	8b 5d fc             	mov    -0x4(%ebp),%ebx
     914:	83 c4 10             	add    $0x10,%esp
     917:	c9                   	leave  
     918:	c3                   	ret    
     919:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi

00000920 <dirtest>:
{
     920:	f3 0f 1e fb          	endbr32 
     924:	55                   	push   %ebp
     925:	89 e5                	mov    %esp,%ebp
     927:	83 ec 10             	sub    $0x10,%esp
  printf(stdout, "mkdir test\n");
     92a:	68 0c 41 00 00       	push   $0x410c
     92f:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     935:	e8 a6 31 00 00       	call   3ae0 <printf>
  if(mkdir("dir0") < 0){
     93a:	c7 04 24 18 41 00 00 	movl   $0x4118,(%esp)
     941:	e8 a5 30 00 00       	call   39eb <mkdir>
     946:	83 c4 10             	add    $0x10,%esp
     949:	85 c0                	test   %eax,%eax
     94b:	78 58                	js     9a5 <dirtest+0x85>
  if(chdir("dir0") < 0){
     94d:	83 ec 0c             	sub    $0xc,%esp
     950:	68 18 41 00 00       	push   $0x4118
     955:	e8 99 30 00 00       	call   39f3 <chdir>
     95a:	83 c4 10             	add    $0x10,%esp
     95d:	85 c0                	test   %eax,%eax
     95f:	0f 88 85 00 00 00    	js     9ea <dirtest+0xca>
  if(chdir("..") < 0){
     965:	83 ec 0c             	sub    $0xc,%esp
     968:	68 bd 46 00 00       	push   $0x46bd
     96d:	e8 81 30 00 00       	call   39f3 <chdir>
     972:	83 c4 10             	add    $0x10,%esp
     975:	85 c0                	test   %eax,%eax
     977:	78 5a                	js     9d3 <dirtest+0xb3>
  if(unlink("dir0") < 0){
     979:	83 ec 0c             	sub    $0xc,%esp
     97c:	68 18 41 00 00       	push   $0x4118
     981:	e8 4d 30 00 00       	call   39d3 <unlink>
     986:	83 c4 10             	add    $0x10,%esp
     989:	85 c0                	test   %eax,%eax
     98b:	78 2f                	js     9bc <dirtest+0x9c>
  printf(stdout, "mkdir test ok\n");
     98d:	83 ec 08             	sub    $0x8,%esp
     990:	68 55 41 00 00       	push   $0x4155
     995:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     99b:	e8 40 31 00 00       	call   3ae0 <printf>
}
     9a0:	83 c4 10             	add    $0x10,%esp
     9a3:	c9                   	leave  
     9a4:	c3                   	ret    
    printf(stdout, "mkdir failed\n");
     9a5:	50                   	push   %eax
     9a6:	50                   	push   %eax
     9a7:	68 48 3e 00 00       	push   $0x3e48
     9ac:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     9b2:	e8 29 31 00 00       	call   3ae0 <printf>
    exit();
     9b7:	e8 c7 2f 00 00       	call   3983 <exit>
    printf(stdout, "unlink dir0 failed\n");
     9bc:	50                   	push   %eax
     9bd:	50                   	push   %eax
     9be:	68 41 41 00 00       	push   $0x4141
     9c3:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     9c9:	e8 12 31 00 00       	call   3ae0 <printf>
    exit();
     9ce:	e8 b0 2f 00 00       	call   3983 <exit>
    printf(stdout, "chdir .. failed\n");
     9d3:	52                   	push   %edx
     9d4:	52                   	push   %edx
     9d5:	68 30 41 00 00       	push   $0x4130
     9da:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     9e0:	e8 fb 30 00 00       	call   3ae0 <printf>
    exit();
     9e5:	e8 99 2f 00 00       	call   3983 <exit>
    printf(stdout, "chdir dir0 failed\n");
     9ea:	51                   	push   %ecx
     9eb:	51                   	push   %ecx
     9ec:	68 1d 41 00 00       	push   $0x411d
     9f1:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     9f7:	e8 e4 30 00 00       	call   3ae0 <printf>
    exit();
     9fc:	e8 82 2f 00 00       	call   3983 <exit>
     a01:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
     a08:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
     a0f:	90                   	nop

00000a10 <exectest>:
{
     a10:	f3 0f 1e fb          	endbr32 
     a14:	55                   	push   %ebp
     a15:	89 e5                	mov    %esp,%ebp
     a17:	83 ec 10             	sub    $0x10,%esp
  printf(stdout, "exec test\n");
     a1a:	68 64 41 00 00       	push   $0x4164
     a1f:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     a25:	e8 b6 30 00 00       	call   3ae0 <printf>
  if(exec("echo", echoargv) < 0){
     a2a:	5a                   	pop    %edx
     a2b:	59                   	pop    %ecx
     a2c:	68 e4 5e 00 00       	push   $0x5ee4
     a31:	68 2d 3f 00 00       	push   $0x3f2d
     a36:	e8 80 2f 00 00       	call   39bb <exec>
     a3b:	83 c4 10             	add    $0x10,%esp
     a3e:	85 c0                	test   %eax,%eax
     a40:	78 02                	js     a44 <exectest+0x34>
}
     a42:	c9                   	leave  
     a43:	c3                   	ret    
    printf(stdout, "exec echo failed\n");
     a44:	50                   	push   %eax
     a45:	50                   	push   %eax
     a46:	68 6f 41 00 00       	push   $0x416f
     a4b:	ff 35 e0 5e 00 00    	pushl  0x5ee0
     a51:	e8 8a 30 00 00       	call   3ae0 <printf>
    exit();
     a56:	e8 28 2f 00 00       	call   3983 <exit>
     a5b:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
     a5f:	90                   	nop

00000a60 <pipe1>:
{
     a60:	f3 0f 1e fb          	endbr32 
     a64:	55                   	push   %ebp
     a65:	89 e5                	mov    %esp,%ebp
     a67:	57                   	push   %edi
     a68:	56                   	push   %esi
  if(pipe(fds) != 0){
     a69:	8d 45 e0             	lea    -0x20(%ebp),%eax
{
     a6c:	53                   	push   %ebx
     a6d:	83 ec 38             	sub    $0x38,%esp
  if(pipe(fds) != 0){
     a70:	50                   	push   %eax
     a71:	e8 1d 2f 00 00       	call   3993 <pipe>
     a76:	83 c4 10             	add    $0x10,%esp
     a79:	85 c0                	test   %eax,%eax
     a7b:	0f 85 38 01 00 00    	jne    bb9 <pipe1+0x159>
  pid = fork();
     a81:	e8 f5 2e 00 00       	call   397b <fork>
  if(pid == 0){
     a86:	85 c0                	test   %eax,%eax
     a88:	0f 84 8d 00 00 00    	je     b1b <pipe1+0xbb>
  } else if(pid > 0){
     a8e:	0f 8e 38 01 00 00    	jle    bcc <pipe1+0x16c>
    close(fds[1]);
     a94:	83 ec 0c             	sub    $0xc,%esp
     a97:	ff 75 e4             	pushl  -0x1c(%ebp)
  seq = 0;
     a9a:	31 db                	xor    %ebx,%ebx
    cc = 1;
     a9c:	be 01 00 00 00       	mov    $0x1,%esi
    close(fds[1]);
     aa1:	e8 05 2f 00 00       	call   39ab <close>
    total = 0;
     aa6:	c7 45 d4 00 00 00 00 	movl   $0x0,-0x2c(%ebp)
    while((n = read(fds[0], buf, cc)) > 0){
     aad:	83 c4 10             	add    $0x10,%esp
     ab0:	83 ec 04             	sub    $0x4,%esp
     ab3:	56                   	push   %esi
     ab4:	68 c0 86 00 00       	push   $0x86c0
     ab9:	ff 75 e0             	pushl  -0x20(%ebp)
     abc:	e8 da 2e 00 00       	call   399b <read>
     ac1:	83 c4 10             	add    $0x10,%esp
     ac4:	89 c7                	mov    %eax,%edi
     ac6:	85 c0                	test   %eax,%eax
     ac8:	0f 8e a7 00 00 00    	jle    b75 <pipe1+0x115>
     ace:	8d 0c 3b             	lea    (%ebx,%edi,1),%ecx
      for(i = 0; i < n; i++){
     ad1:	31 c0                	xor    %eax,%eax
     ad3:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
     ad7:	90                   	nop
        if((buf[i] & 0xff) != (seq++ & 0xff)){
     ad8:	89 da                	mov    %ebx,%edx
     ada:	83 c3 01             	add    $0x1,%ebx
     add:	38 90 c0 86 00 00    	cmp    %dl,0x86c0(%eax)
     ae3:	75 1c                	jne    b01 <pipe1+0xa1>
      for(i = 0; i < n; i++){
     ae5:	83 c0 01             	add    $0x1,%eax
     ae8:	39 d9                	cmp    %ebx,%ecx
     aea:	75 ec                	jne    ad8 <pipe1+0x78>
      cc = cc * 2;
     aec:	01 f6                	add    %esi,%esi
      total += n;
     aee:	01 7d d4             	add    %edi,-0x2c(%ebp)
     af1:	b8 00 20 00 00       	mov    $0x2000,%eax
     af6:	81 fe 00 20 00 00    	cmp    $0x2000,%esi
     afc:	0f 4f f0             	cmovg  %eax,%esi
     aff:	eb af                	jmp    ab0 <pipe1+0x50>
          printf(1, "pipe1 oops 2\n");
     b01:	83 ec 08             	sub    $0x8,%esp
     b04:	68 9e 41 00 00       	push   $0x419e
     b09:	6a 01                	push   $0x1
     b0b:	e8 d0 2f 00 00       	call   3ae0 <printf>
          return;
     b10:	83 c4 10             	add    $0x10,%esp
}
     b13:	8d 65 f4             	lea    -0xc(%ebp),%esp
     b16:	5b                   	pop    %ebx
     b17:	5e                   	pop    %esi
     b18:	5f                   	pop    %edi
     b19:	5d                   	pop    %ebp
     b1a:	c3                   	ret    
    close(fds[0]);
     b1b:	83 ec 0c             	sub    $0xc,%esp
     b1e:	ff 75 e0             	pushl  -0x20(%ebp)
  seq = 0;
     b21:	31 db                	xor    %ebx,%ebx
    close(fds[0]);
     b23:	e8 83 2e 00 00       	call   39ab <close>
     b28:	83 c4 10             	add    $0x10,%esp
      for(i = 0; i < 1033; i++)
     b2b:	31 c0                	xor    %eax,%eax
     b2d:	8d 76 00             	lea    0x0(%esi),%esi
        buf[i] = seq++;
     b30:	8d 14 18             	lea    (%eax,%ebx,1),%edx
      for(i = 0; i < 1033; i++)
     b33:	83 c0 01             	add    $0x1,%eax
        buf[i] = seq++;
     b36:	88 90 bf 86 00 00    	mov    %dl,0x86bf(%eax)
      for(i = 0; i < 1033; i++)
     b3c:	3d 09 04 00 00       	cmp    $0x409,%eax
     b41:	75 ed                	jne    b30 <pipe1+0xd0>
      if(write(fds[1], buf, 1033) != 1033){
     b43:	83 ec 04             	sub    $0x4,%esp
     b46:	81 c3 09 04 00 00    	add    $0x409,%ebx
     b4c:	68 09 04 00 00       	push   $0x409
     b51:	68 c0 86 00 00       	push   $0x86c0
     b56:	ff 75 e4             	pushl  -0x1c(%ebp)
     b59:	e8 45 2e 00 00       	call   39a3 <write>
     b5e:	83 c4 10             	add    $0x10,%esp
     b61:	3d 09 04 00 00       	cmp    $0x409,%eax
     b66:	75 77                	jne    bdf <pipe1+0x17f>
    for(n = 0; n < 5; n++){
     b68:	81 fb 2d 14 00 00    	cmp    $0x142d,%ebx
     b6e:	75 bb                	jne    b2b <pipe1+0xcb>
    exit();
     b70:	e8 0e 2e 00 00       	call   3983 <exit>
    if(total != 5 * 1033){
     b75:	81 7d d4 2d 14 00 00 	cmpl   $0x142d,-0x2c(%ebp)
     b7c:	75 26                	jne    ba4 <pipe1+0x144>
    close(fds[0]);
     b7e:	83 ec 0c             	sub    $0xc,%esp
     b81:	ff 75 e0             	pushl  -0x20(%ebp)
     b84:	e8 22 2e 00 00       	call   39ab <close>
    wait();
     b89:	e8 fd 2d 00 00       	call   398b <wait>
  printf(1, "pipe1 ok\n");
     b8e:	5a                   	pop    %edx
     b8f:	59                   	pop    %ecx
     b90:	68 c3 41 00 00       	push   $0x41c3
     b95:	6a 01                	push   $0x1
     b97:	e8 44 2f 00 00       	call   3ae0 <printf>
     b9c:	83 c4 10             	add    $0x10,%esp
     b9f:	e9 6f ff ff ff       	jmp    b13 <pipe1+0xb3>
      printf(1, "pipe1 oops 3 total %d\n", total);
     ba4:	53                   	push   %ebx
     ba5:	ff 75 d4             	pushl  -0x2c(%ebp)
     ba8:	68 ac 41 00 00       	push   $0x41ac
     bad:	6a 01                	push   $0x1
     baf:	e8 2c 2f 00 00       	call   3ae0 <printf>
      exit();
     bb4:	e8 ca 2d 00 00       	call   3983 <exit>
    printf(1, "pipe() failed\n");
     bb9:	57                   	push   %edi
     bba:	57                   	push   %edi
     bbb:	68 81 41 00 00       	push   $0x4181
     bc0:	6a 01                	push   $0x1
     bc2:	e8 19 2f 00 00       	call   3ae0 <printf>
    exit();
     bc7:	e8 b7 2d 00 00       	call   3983 <exit>
    printf(1, "fork() failed\n");
     bcc:	50                   	push   %eax
     bcd:	50                   	push   %eax
     bce:	68 cd 41 00 00       	push   $0x41cd
     bd3:	6a 01                	push   $0x1
     bd5:	e8 06 2f 00 00       	call   3ae0 <printf>
    exit();
     bda:	e8 a4 2d 00 00       	call   3983 <exit>
        printf(1, "pipe1 oops 1\n");
     bdf:	56                   	push   %esi
     be0:	56                   	push   %esi
     be1:	68 90 41 00 00       	push   $0x4190
     be6:	6a 01                	push   $0x1
     be8:	e8 f3 2e 00 00       	call   3ae0 <printf>
        exit();
     bed:	e8 91 2d 00 00       	call   3983 <exit>
     bf2:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
     bf9:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi

00000c00 <preempt>:
{
     c00:	f3 0f 1e fb          	endbr32 
     c04:	55                   	push   %ebp
     c05:	89 e5                	mov    %esp,%ebp
     c07:	57                   	push   %edi
     c08:	56                   	push   %esi
     c09:	53                   	push   %ebx
     c0a:	83 ec 24             	sub    $0x24,%esp
  printf(1, "preempt: ");
     c0d:	68 dc 41 00 00       	push   $0x41dc
     c12:	6a 01                	push   $0x1
     c14:	e8 c7 2e 00 00       	call   3ae0 <printf>
  pid1 = fork();
     c19:	e8 5d 2d 00 00       	call   397b <fork>
  if(pid1 == 0)
     c1e:	83 c4 10             	add    $0x10,%esp
     c21:	85 c0                	test   %eax,%eax
     c23:	75 0b                	jne    c30 <preempt+0x30>
    for(;;)
     c25:	eb fe                	jmp    c25 <preempt+0x25>
     c27:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
     c2e:	66 90                	xchg   %ax,%ax
     c30:	89 c7                	mov    %eax,%edi
  pid2 = fork();
     c32:	e8 44 2d 00 00       	call   397b <fork>
     c37:	89 c6                	mov    %eax,%esi
  if(pid2 == 0)
     c39:	85 c0                	test   %eax,%eax
     c3b:	75 03                	jne    c40 <preempt+0x40>
    for(;;)
     c3d:	eb fe                	jmp    c3d <preempt+0x3d>
     c3f:	90                   	nop
  pipe(pfds);
     c40:	83 ec 0c             	sub    $0xc,%esp
     c43:	8d 45 e0             	lea    -0x20(%ebp),%eax
     c46:	50                   	push   %eax
     c47:	e8 47 2d 00 00       	call   3993 <pipe>
  pid3 = fork();
     c4c:	e8 2a 2d 00 00       	call   397b <fork>
  if(pid3 == 0){
     c51:	83 c4 10             	add    $0x10,%esp
  pid3 = fork();
     c54:	89 c3                	mov    %eax,%ebx
  if(pid3 == 0){
     c56:	85 c0                	test   %eax,%eax
     c58:	75 3e                	jne    c98 <preempt+0x98>
    close(pfds[0]);
     c5a:	83 ec 0c             	sub    $0xc,%esp
     c5d:	ff 75 e0             	pushl  -0x20(%ebp)
     c60:	e8 46 2d 00 00       	call   39ab <close>
    if(write(pfds[1], "x", 1) != 1)
     c65:	83 c4 0c             	add    $0xc,%esp
     c68:	6a 01                	push   $0x1
     c6a:	68 a1 47 00 00       	push   $0x47a1
     c6f:	ff 75 e4             	pushl  -0x1c(%ebp)
     c72:	e8 2c 2d 00 00       	call   39a3 <write>
     c77:	83 c4 10             	add    $0x10,%esp
     c7a:	83 f8 01             	cmp    $0x1,%eax
     c7d:	0f 85 a4 00 00 00    	jne    d27 <preempt+0x127>
    close(pfds[1]);
     c83:	83 ec 0c             	sub    $0xc,%esp
     c86:	ff 75 e4             	pushl  -0x1c(%ebp)
     c89:	e8 1d 2d 00 00       	call   39ab <close>
     c8e:	83 c4 10             	add    $0x10,%esp
    for(;;)
     c91:	eb fe                	jmp    c91 <preempt+0x91>
     c93:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
     c97:	90                   	nop
  close(pfds[1]);
     c98:	83 ec 0c             	sub    $0xc,%esp
     c9b:	ff 75 e4             	pushl  -0x1c(%ebp)
     c9e:	e8 08 2d 00 00       	call   39ab <close>
  if(read(pfds[0], buf, sizeof(buf)) != 1){
     ca3:	83 c4 0c             	add    $0xc,%esp
     ca6:	68 00 20 00 00       	push   $0x2000
     cab:	68 c0 86 00 00       	push   $0x86c0
     cb0:	ff 75 e0             	pushl  -0x20(%ebp)
     cb3:	e8 e3 2c 00 00       	call   399b <read>
     cb8:	83 c4 10             	add    $0x10,%esp
     cbb:	83 f8 01             	cmp    $0x1,%eax
     cbe:	75 7e                	jne    d3e <preempt+0x13e>
  close(pfds[0]);
     cc0:	83 ec 0c             	sub    $0xc,%esp
     cc3:	ff 75 e0             	pushl  -0x20(%ebp)
     cc6:	e8 e0 2c 00 00       	call   39ab <close>
  printf(1, "kill... ");
     ccb:	58                   	pop    %eax
     ccc:	5a                   	pop    %edx
     ccd:	68 0d 42 00 00       	push   $0x420d
     cd2:	6a 01                	push   $0x1
     cd4:	e8 07 2e 00 00       	call   3ae0 <printf>
  kill(pid1);
     cd9:	89 3c 24             	mov    %edi,(%esp)
     cdc:	e8 d2 2c 00 00       	call   39b3 <kill>
  kill(pid2);
     ce1:	89 34 24             	mov    %esi,(%esp)
     ce4:	e8 ca 2c 00 00       	call   39b3 <kill>
  kill(pid3);
     ce9:	89 1c 24             	mov    %ebx,(%esp)
     cec:	e8 c2 2c 00 00       	call   39b3 <kill>
  printf(1, "wait... ");
     cf1:	59                   	pop    %ecx
     cf2:	5b                   	pop    %ebx
     cf3:	68 16 42 00 00       	push   $0x4216
     cf8:	6a 01                	push   $0x1
     cfa:	e8 e1 2d 00 00       	call   3ae0 <printf>
  wait();
     cff:	e8 87 2c 00 00       	call   398b <wait>
  wait();
     d04:	e8 82 2c 00 00       	call   398b <wait>
  wait();
     d09:	e8 7d 2c 00 00       	call   398b <wait>
  printf(1, "preempt ok\n");
     d0e:	5e                   	pop    %esi
     d0f:	5f                   	pop    %edi
     d10:	68 1f 42 00 00       	push   $0x421f
     d15:	6a 01                	push   $0x1
     d17:	e8 c4 2d 00 00       	call   3ae0 <printf>
     d1c:	83 c4 10             	add    $0x10,%esp
}
     d1f:	8d 65 f4             	lea    -0xc(%ebp),%esp
     d22:	5b                   	pop    %ebx
     d23:	5e                   	pop    %esi
     d24:	5f                   	pop    %edi
     d25:	5d                   	pop    %ebp
     d26:	c3                   	ret    
      printf(1, "preempt write error");
     d27:	83 ec 08             	sub    $0x8,%esp
     d2a:	68 e6 41 00 00       	push   $0x41e6
     d2f:	6a 01                	push   $0x1
     d31:	e8 aa 2d 00 00       	call   3ae0 <printf>
     d36:	83 c4 10             	add    $0x10,%esp
     d39:	e9 45 ff ff ff       	jmp    c83 <preempt+0x83>
    printf(1, "preempt read error");
     d3e:	83 ec 08             	sub    $0x8,%esp
     d41:	68 fa 41 00 00       	push   $0x41fa
     d46:	6a 01                	push   $0x1
     d48:	e8 93 2d 00 00       	call   3ae0 <printf>
    return;
     d4d:	83 c4 10             	add    $0x10,%esp
     d50:	eb cd                	jmp    d1f <preempt+0x11f>
     d52:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
     d59:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi

00000d60 <exitwait>:
{
     d60:	f3 0f 1e fb          	endbr32 
     d64:	55                   	push   %ebp
     d65:	89 e5                	mov    %esp,%ebp
     d67:	56                   	push   %esi
     d68:	be 64 00 00 00       	mov    $0x64,%esi
     d6d:	53                   	push   %ebx
     d6e:	eb 10                	jmp    d80 <exitwait+0x20>
    if(pid){
     d70:	74 68                	je     dda <exitwait+0x7a>
      if(wait() != pid){
     d72:	e8 14 2c 00 00       	call   398b <wait>
     d77:	39 d8                	cmp    %ebx,%eax
     d79:	75 2d                	jne    da8 <exitwait+0x48>
  for(i = 0; i < 100; i++){
     d7b:	83 ee 01             	sub    $0x1,%esi
     d7e:	74 41                	je     dc1 <exitwait+0x61>
    pid = fork();
     d80:	e8 f6 2b 00 00       	call   397b <fork>
     d85:	89 c3                	mov    %eax,%ebx
    if(pid < 0){
     d87:	85 c0                	test   %eax,%eax
     d89:	79 e5                	jns    d70 <exitwait+0x10>
      printf(1, "fork failed\n");
     d8b:	83 ec 08             	sub    $0x8,%esp
     d8e:	68 89 4d 00 00       	push   $0x4d89
     d93:	6a 01                	push   $0x1
     d95:	e8 46 2d 00 00       	call   3ae0 <printf>
      return;
     d9a:	83 c4 10             	add    $0x10,%esp
}
     d9d:	8d 65 f8             	lea    -0x8(%ebp),%esp
     da0:	5b                   	pop    %ebx
     da1:	5e                   	pop    %esi
     da2:	5d                   	pop    %ebp
     da3:	c3                   	ret    
     da4:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
        printf(1, "wait wrong pid\n");
     da8:	83 ec 08             	sub    $0x8,%esp
     dab:	68 2b 42 00 00       	push   $0x422b
     db0:	6a 01                	push   $0x1
     db2:	e8 29 2d 00 00       	call   3ae0 <printf>
        return;
     db7:	83 c4 10             	add    $0x10,%esp
}
     dba:	8d 65 f8             	lea    -0x8(%ebp),%esp
     dbd:	5b                   	pop    %ebx
     dbe:	5e                   	pop    %esi
     dbf:	5d                   	pop    %ebp
     dc0:	c3                   	ret    
  printf(1, "exitwait ok\n");
     dc1:	83 ec 08             	sub    $0x8,%esp
     dc4:	68 3b 42 00 00       	push   $0x423b
     dc9:	6a 01                	push   $0x1
     dcb:	e8 10 2d 00 00       	call   3ae0 <printf>
     dd0:	83 c4 10             	add    $0x10,%esp
}
     dd3:	8d 65 f8             	lea    -0x8(%ebp),%esp
     dd6:	5b                   	pop    %ebx
     dd7:	5e                   	pop    %esi
     dd8:	5d                   	pop    %ebp
     dd9:	c3                   	ret    
      exit();
     dda:	e8 a4 2b 00 00       	call   3983 <exit>
     ddf:	90                   	nop

00000de0 <mem>:
{
     de0:	f3 0f 1e fb          	endbr32 
     de4:	55                   	push   %ebp
     de5:	89 e5                	mov    %esp,%ebp
     de7:	56                   	push   %esi
     de8:	31 f6                	xor    %esi,%esi
     dea:	53                   	push   %ebx
  printf(1, "mem test\n");
     deb:	83 ec 08             	sub    $0x8,%esp
     dee:	68 48 42 00 00       	push   $0x4248
     df3:	6a 01                	push   $0x1
     df5:	e8 e6 2c 00 00       	call   3ae0 <printf>
  ppid = getpid();
     dfa:	e8 04 2c 00 00       	call   3a03 <getpid>
     dff:	89 c3                	mov    %eax,%ebx
  if((pid = fork()) == 0){
     e01:	e8 75 2b 00 00       	call   397b <fork>
     e06:	83 c4 10             	add    $0x10,%esp
     e09:	85 c0                	test   %eax,%eax
     e0b:	74 0f                	je     e1c <mem+0x3c>
     e0d:	e9 8e 00 00 00       	jmp    ea0 <mem+0xc0>
     e12:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi
      *(char**)m2 = m1;
     e18:	89 30                	mov    %esi,(%eax)
     e1a:	89 c6                	mov    %eax,%esi
    while((m2 = malloc(10001)) != 0){
     e1c:	83 ec 0c             	sub    $0xc,%esp
     e1f:	68 11 27 00 00       	push   $0x2711
     e24:	e8 17 2f 00 00       	call   3d40 <malloc>
     e29:	83 c4 10             	add    $0x10,%esp
     e2c:	85 c0                	test   %eax,%eax
     e2e:	75 e8                	jne    e18 <mem+0x38>
    while(m1){
     e30:	85 f6                	test   %esi,%esi
     e32:	74 18                	je     e4c <mem+0x6c>
     e34:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
      m2 = *(char**)m1;
     e38:	89 f0                	mov    %esi,%eax
      free(m1);
     e3a:	83 ec 0c             	sub    $0xc,%esp
      m2 = *(char**)m1;
     e3d:	8b 36                	mov    (%esi),%esi
      free(m1);
     e3f:	50                   	push   %eax
     e40:	e8 6b 2e 00 00       	call   3cb0 <free>
    while(m1){
     e45:	83 c4 10             	add    $0x10,%esp
     e48:	85 f6                	test   %esi,%esi
     e4a:	75 ec                	jne    e38 <mem+0x58>
    m1 = malloc(1024*20);
     e4c:	83 ec 0c             	sub    $0xc,%esp
     e4f:	68 00 50 00 00       	push   $0x5000
     e54:	e8 e7 2e 00 00       	call   3d40 <malloc>
    if(m1 == 0){
     e59:	83 c4 10             	add    $0x10,%esp
     e5c:	85 c0                	test   %eax,%eax
     e5e:	74 20                	je     e80 <mem+0xa0>
    free(m1);
     e60:	83 ec 0c             	sub    $0xc,%esp
     e63:	50                   	push   %eax
     e64:	e8 47 2e 00 00       	call   3cb0 <free>
    printf(1, "mem ok\n");
     e69:	58                   	pop    %eax
     e6a:	5a                   	pop    %edx
     e6b:	68 6c 42 00 00       	push   $0x426c
     e70:	6a 01                	push   $0x1
     e72:	e8 69 2c 00 00       	call   3ae0 <printf>
    exit();
     e77:	e8 07 2b 00 00       	call   3983 <exit>
     e7c:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
      printf(1, "couldn't allocate mem?!!\n");
     e80:	83 ec 08             	sub    $0x8,%esp
     e83:	68 52 42 00 00       	push   $0x4252
     e88:	6a 01                	push   $0x1
     e8a:	e8 51 2c 00 00       	call   3ae0 <printf>
      kill(ppid);
     e8f:	89 1c 24             	mov    %ebx,(%esp)
     e92:	e8 1c 2b 00 00       	call   39b3 <kill>
      exit();
     e97:	e8 e7 2a 00 00       	call   3983 <exit>
     e9c:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
}
     ea0:	8d 65 f8             	lea    -0x8(%ebp),%esp
     ea3:	5b                   	pop    %ebx
     ea4:	5e                   	pop    %esi
     ea5:	5d                   	pop    %ebp
    wait();
     ea6:	e9 e0 2a 00 00       	jmp    398b <wait>
     eab:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
     eaf:	90                   	nop

00000eb0 <sharedfd>:
{
     eb0:	f3 0f 1e fb          	endbr32 
     eb4:	55                   	push   %ebp
     eb5:	89 e5                	mov    %esp,%ebp
     eb7:	57                   	push   %edi
     eb8:	56                   	push   %esi
     eb9:	53                   	push   %ebx
     eba:	83 ec 34             	sub    $0x34,%esp
  printf(1, "sharedfd test\n");
     ebd:	68 74 42 00 00       	push   $0x4274
     ec2:	6a 01                	push   $0x1
     ec4:	e8 17 2c 00 00       	call   3ae0 <printf>
  unlink("sharedfd");
     ec9:	c7 04 24 83 42 00 00 	movl   $0x4283,(%esp)
     ed0:	e8 fe 2a 00 00       	call   39d3 <unlink>
  fd = open("sharedfd", O_CREATE|O_RDWR);
     ed5:	5b                   	pop    %ebx
     ed6:	5e                   	pop    %esi
     ed7:	68 02 02 00 00       	push   $0x202
     edc:	68 83 42 00 00       	push   $0x4283
     ee1:	e8 dd 2a 00 00       	call   39c3 <open>
  if(fd < 0){
     ee6:	83 c4 10             	add    $0x10,%esp
     ee9:	85 c0                	test   %eax,%eax
     eeb:	0f 88 26 01 00 00    	js     1017 <sharedfd+0x167>
     ef1:	89 c7                	mov    %eax,%edi
  memset(buf, pid==0?'c':'p', sizeof(buf));
     ef3:	8d 75 de             	lea    -0x22(%ebp),%esi
     ef6:	bb e8 03 00 00       	mov    $0x3e8,%ebx
  pid = fork();
     efb:	e8 7b 2a 00 00       	call   397b <fork>
  memset(buf, pid==0?'c':'p', sizeof(buf));
     f00:	83 f8 01             	cmp    $0x1,%eax
  pid = fork();
     f03:	89 45 d4             	mov    %eax,-0x2c(%ebp)
  memset(buf, pid==0?'c':'p', sizeof(buf));
     f06:	19 c0                	sbb    %eax,%eax
     f08:	83 ec 04             	sub    $0x4,%esp
     f0b:	83 e0 f3             	and    $0xfffffff3,%eax
     f0e:	6a 0a                	push   $0xa
     f10:	83 c0 70             	add    $0x70,%eax
     f13:	50                   	push   %eax
     f14:	56                   	push   %esi
     f15:	e8 c6 28 00 00       	call   37e0 <memset>
     f1a:	83 c4 10             	add    $0x10,%esp
     f1d:	eb 06                	jmp    f25 <sharedfd+0x75>
     f1f:	90                   	nop
  for(i = 0; i < 1000; i++){
     f20:	83 eb 01             	sub    $0x1,%ebx
     f23:	74 26                	je     f4b <sharedfd+0x9b>
    if(write(fd, buf, sizeof(buf)) != sizeof(buf)){
     f25:	83 ec 04             	sub    $0x4,%esp
     f28:	6a 0a                	push   $0xa
     f2a:	56                   	push   %esi
     f2b:	57                   	push   %edi
     f2c:	e8 72 2a 00 00       	call   39a3 <write>
     f31:	83 c4 10             	add    $0x10,%esp
     f34:	83 f8 0a             	cmp    $0xa,%eax
     f37:	74 e7                	je     f20 <sharedfd+0x70>
      printf(1, "fstests: write sharedfd failed\n");
     f39:	83 ec 08             	sub    $0x8,%esp
     f3c:	68 74 4f 00 00       	push   $0x4f74
     f41:	6a 01                	push   $0x1
     f43:	e8 98 2b 00 00       	call   3ae0 <printf>
      break;
     f48:	83 c4 10             	add    $0x10,%esp
  if(pid == 0)
     f4b:	8b 4d d4             	mov    -0x2c(%ebp),%ecx
     f4e:	85 c9                	test   %ecx,%ecx
     f50:	0f 84 f5 00 00 00    	je     104b <sharedfd+0x19b>
    wait();
     f56:	e8 30 2a 00 00       	call   398b <wait>
  close(fd);
     f5b:	83 ec 0c             	sub    $0xc,%esp
  nc = np = 0;
     f5e:	31 db                	xor    %ebx,%ebx
  close(fd);
     f60:	57                   	push   %edi
     f61:	8d 7d e8             	lea    -0x18(%ebp),%edi
     f64:	e8 42 2a 00 00       	call   39ab <close>
  fd = open("sharedfd", 0);
     f69:	58                   	pop    %eax
     f6a:	5a                   	pop    %edx
     f6b:	6a 00                	push   $0x0
     f6d:	68 83 42 00 00       	push   $0x4283
     f72:	e8 4c 2a 00 00       	call   39c3 <open>
  if(fd < 0){
     f77:	83 c4 10             	add    $0x10,%esp
  nc = np = 0;
     f7a:	31 d2                	xor    %edx,%edx
  fd = open("sharedfd", 0);
     f7c:	89 45 d0             	mov    %eax,-0x30(%ebp)
  if(fd < 0){
     f7f:	85 c0                	test   %eax,%eax
     f81:	0f 88 aa 00 00 00    	js     1031 <sharedfd+0x181>
     f87:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
     f8e:	66 90                	xchg   %ax,%ax
  while((n = read(fd, buf, sizeof(buf))) > 0){
     f90:	83 ec 04             	sub    $0x4,%esp
     f93:	89 55 d4             	mov    %edx,-0x2c(%ebp)
     f96:	6a 0a                	push   $0xa
     f98:	56                   	push   %esi
     f99:	ff 75 d0             	pushl  -0x30(%ebp)
     f9c:	e8 fa 29 00 00       	call   399b <read>
     fa1:	83 c4 10             	add    $0x10,%esp
     fa4:	85 c0                	test   %eax,%eax
     fa6:	7e 28                	jle    fd0 <sharedfd+0x120>
    for(i = 0; i < sizeof(buf); i++){
     fa8:	8b 55 d4             	mov    -0x2c(%ebp),%edx
     fab:	89 f0                	mov    %esi,%eax
     fad:	eb 13                	jmp    fc2 <sharedfd+0x112>
     faf:	90                   	nop
        np++;
     fb0:	80 f9 70             	cmp    $0x70,%cl
     fb3:	0f 94 c1             	sete   %cl
     fb6:	0f b6 c9             	movzbl %cl,%ecx
     fb9:	01 cb                	add    %ecx,%ebx
    for(i = 0; i < sizeof(buf); i++){
     fbb:	83 c0 01             	add    $0x1,%eax
     fbe:	39 c7                	cmp    %eax,%edi
     fc0:	74 ce                	je     f90 <sharedfd+0xe0>
      if(buf[i] == 'c')
     fc2:	0f b6 08             	movzbl (%eax),%ecx
     fc5:	80 f9 63             	cmp    $0x63,%cl
     fc8:	75 e6                	jne    fb0 <sharedfd+0x100>
        nc++;
     fca:	83 c2 01             	add    $0x1,%edx
      if(buf[i] == 'p')
     fcd:	eb ec                	jmp    fbb <sharedfd+0x10b>
     fcf:	90                   	nop
  close(fd);
     fd0:	83 ec 0c             	sub    $0xc,%esp
     fd3:	ff 75 d0             	pushl  -0x30(%ebp)
     fd6:	e8 d0 29 00 00       	call   39ab <close>
  unlink("sharedfd");
     fdb:	c7 04 24 83 42 00 00 	movl   $0x4283,(%esp)
     fe2:	e8 ec 29 00 00       	call   39d3 <unlink>
  if(nc == 10000 && np == 10000){
     fe7:	8b 55 d4             	mov    -0x2c(%ebp),%edx
     fea:	83 c4 10             	add    $0x10,%esp
     fed:	81 fa 10 27 00 00    	cmp    $0x2710,%edx
     ff3:	75 5b                	jne    1050 <sharedfd+0x1a0>
     ff5:	81 fb 10 27 00 00    	cmp    $0x2710,%ebx
     ffb:	75 53                	jne    1050 <sharedfd+0x1a0>
    printf(1, "sharedfd ok\n");
     ffd:	83 ec 08             	sub    $0x8,%esp
    1000:	68 8c 42 00 00       	push   $0x428c
    1005:	6a 01                	push   $0x1
    1007:	e8 d4 2a 00 00       	call   3ae0 <printf>
    100c:	83 c4 10             	add    $0x10,%esp
}
    100f:	8d 65 f4             	lea    -0xc(%ebp),%esp
    1012:	5b                   	pop    %ebx
    1013:	5e                   	pop    %esi
    1014:	5f                   	pop    %edi
    1015:	5d                   	pop    %ebp
    1016:	c3                   	ret    
    printf(1, "fstests: cannot open sharedfd for writing");
    1017:	83 ec 08             	sub    $0x8,%esp
    101a:	68 48 4f 00 00       	push   $0x4f48
    101f:	6a 01                	push   $0x1
    1021:	e8 ba 2a 00 00       	call   3ae0 <printf>
    return;
    1026:	83 c4 10             	add    $0x10,%esp
}
    1029:	8d 65 f4             	lea    -0xc(%ebp),%esp
    102c:	5b                   	pop    %ebx
    102d:	5e                   	pop    %esi
    102e:	5f                   	pop    %edi
    102f:	5d                   	pop    %ebp
    1030:	c3                   	ret    
    printf(1, "fstests: cannot open sharedfd for reading\n");
    1031:	83 ec 08             	sub    $0x8,%esp
    1034:	68 94 4f 00 00       	push   $0x4f94
    1039:	6a 01                	push   $0x1
    103b:	e8 a0 2a 00 00       	call   3ae0 <printf>
    return;
    1040:	83 c4 10             	add    $0x10,%esp
}
    1043:	8d 65 f4             	lea    -0xc(%ebp),%esp
    1046:	5b                   	pop    %ebx
    1047:	5e                   	pop    %esi
    1048:	5f                   	pop    %edi
    1049:	5d                   	pop    %ebp
    104a:	c3                   	ret    
    exit();
    104b:	e8 33 29 00 00       	call   3983 <exit>
    printf(1, "sharedfd oops %d %d\n", nc, np);
    1050:	53                   	push   %ebx
    1051:	52                   	push   %edx
    1052:	68 99 42 00 00       	push   $0x4299
    1057:	6a 01                	push   $0x1
    1059:	e8 82 2a 00 00       	call   3ae0 <printf>
    exit();
    105e:	e8 20 29 00 00       	call   3983 <exit>
    1063:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    106a:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi

00001070 <fourfiles>:
{
    1070:	f3 0f 1e fb          	endbr32 
    1074:	55                   	push   %ebp
    1075:	89 e5                	mov    %esp,%ebp
    1077:	57                   	push   %edi
    1078:	56                   	push   %esi
  printf(1, "fourfiles test\n");
    1079:	be ae 42 00 00       	mov    $0x42ae,%esi
{
    107e:	53                   	push   %ebx
  for(pi = 0; pi < 4; pi++){
    107f:	31 db                	xor    %ebx,%ebx
{
    1081:	83 ec 34             	sub    $0x34,%esp
  char *names[] = { "f0", "f1", "f2", "f3" };
    1084:	c7 45 d8 ae 42 00 00 	movl   $0x42ae,-0x28(%ebp)
  printf(1, "fourfiles test\n");
    108b:	68 b4 42 00 00       	push   $0x42b4
    1090:	6a 01                	push   $0x1
  char *names[] = { "f0", "f1", "f2", "f3" };
    1092:	c7 45 dc f7 43 00 00 	movl   $0x43f7,-0x24(%ebp)
    1099:	c7 45 e0 fb 43 00 00 	movl   $0x43fb,-0x20(%ebp)
    10a0:	c7 45 e4 b1 42 00 00 	movl   $0x42b1,-0x1c(%ebp)
  printf(1, "fourfiles test\n");
    10a7:	e8 34 2a 00 00       	call   3ae0 <printf>
    10ac:	83 c4 10             	add    $0x10,%esp
    unlink(fname);
    10af:	83 ec 0c             	sub    $0xc,%esp
    10b2:	56                   	push   %esi
    10b3:	e8 1b 29 00 00       	call   39d3 <unlink>
    pid = fork();
    10b8:	e8 be 28 00 00       	call   397b <fork>
    if(pid < 0){
    10bd:	83 c4 10             	add    $0x10,%esp
    10c0:	85 c0                	test   %eax,%eax
    10c2:	0f 88 60 01 00 00    	js     1228 <fourfiles+0x1b8>
    if(pid == 0){
    10c8:	0f 84 e5 00 00 00    	je     11b3 <fourfiles+0x143>
  for(pi = 0; pi < 4; pi++){
    10ce:	83 c3 01             	add    $0x1,%ebx
    10d1:	83 fb 04             	cmp    $0x4,%ebx
    10d4:	74 06                	je     10dc <fourfiles+0x6c>
    10d6:	8b 74 9d d8          	mov    -0x28(%ebp,%ebx,4),%esi
    10da:	eb d3                	jmp    10af <fourfiles+0x3f>
    wait();
    10dc:	e8 aa 28 00 00       	call   398b <wait>
  for(i = 0; i < 2; i++){
    10e1:	31 f6                	xor    %esi,%esi
    wait();
    10e3:	e8 a3 28 00 00       	call   398b <wait>
    10e8:	e8 9e 28 00 00       	call   398b <wait>
    10ed:	e8 99 28 00 00       	call   398b <wait>
    fname = names[i];
    10f2:	8b 44 b5 d8          	mov    -0x28(%ebp,%esi,4),%eax
    fd = open(fname, 0);
    10f6:	83 ec 08             	sub    $0x8,%esp
    total = 0;
    10f9:	31 db                	xor    %ebx,%ebx
    fd = open(fname, 0);
    10fb:	6a 00                	push   $0x0
    10fd:	50                   	push   %eax
    fname = names[i];
    10fe:	89 45 d0             	mov    %eax,-0x30(%ebp)
    fd = open(fname, 0);
    1101:	e8 bd 28 00 00       	call   39c3 <open>
    while((n = read(fd, buf, sizeof(buf))) > 0){
    1106:	83 c4 10             	add    $0x10,%esp
    fd = open(fname, 0);
    1109:	89 45 d4             	mov    %eax,-0x2c(%ebp)
    while((n = read(fd, buf, sizeof(buf))) > 0){
    110c:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
    1110:	83 ec 04             	sub    $0x4,%esp
    1113:	68 00 20 00 00       	push   $0x2000
    1118:	68 c0 86 00 00       	push   $0x86c0
    111d:	ff 75 d4             	pushl  -0x2c(%ebp)
    1120:	e8 76 28 00 00       	call   399b <read>
    1125:	83 c4 10             	add    $0x10,%esp
    1128:	85 c0                	test   %eax,%eax
    112a:	7e 22                	jle    114e <fourfiles+0xde>
      for(j = 0; j < n; j++){
    112c:	31 d2                	xor    %edx,%edx
    112e:	66 90                	xchg   %ax,%ax
        if(buf[j] != '0'+i){
    1130:	83 fe 01             	cmp    $0x1,%esi
    1133:	0f be ba c0 86 00 00 	movsbl 0x86c0(%edx),%edi
    113a:	19 c9                	sbb    %ecx,%ecx
    113c:	83 c1 31             	add    $0x31,%ecx
    113f:	39 cf                	cmp    %ecx,%edi
    1141:	75 5c                	jne    119f <fourfiles+0x12f>
      for(j = 0; j < n; j++){
    1143:	83 c2 01             	add    $0x1,%edx
    1146:	39 d0                	cmp    %edx,%eax
    1148:	75 e6                	jne    1130 <fourfiles+0xc0>
      total += n;
    114a:	01 c3                	add    %eax,%ebx
    114c:	eb c2                	jmp    1110 <fourfiles+0xa0>
    close(fd);
    114e:	83 ec 0c             	sub    $0xc,%esp
    1151:	ff 75 d4             	pushl  -0x2c(%ebp)
    1154:	e8 52 28 00 00       	call   39ab <close>
    if(total != 12*500){
    1159:	83 c4 10             	add    $0x10,%esp
    115c:	81 fb 70 17 00 00    	cmp    $0x1770,%ebx
    1162:	0f 85 d4 00 00 00    	jne    123c <fourfiles+0x1cc>
    unlink(fname);
    1168:	83 ec 0c             	sub    $0xc,%esp
    116b:	ff 75 d0             	pushl  -0x30(%ebp)
    116e:	e8 60 28 00 00       	call   39d3 <unlink>
  for(i = 0; i < 2; i++){
    1173:	83 c4 10             	add    $0x10,%esp
    1176:	83 fe 01             	cmp    $0x1,%esi
    1179:	75 1a                	jne    1195 <fourfiles+0x125>
  printf(1, "fourfiles ok\n");
    117b:	83 ec 08             	sub    $0x8,%esp
    117e:	68 f2 42 00 00       	push   $0x42f2
    1183:	6a 01                	push   $0x1
    1185:	e8 56 29 00 00       	call   3ae0 <printf>
}
    118a:	83 c4 10             	add    $0x10,%esp
    118d:	8d 65 f4             	lea    -0xc(%ebp),%esp
    1190:	5b                   	pop    %ebx
    1191:	5e                   	pop    %esi
    1192:	5f                   	pop    %edi
    1193:	5d                   	pop    %ebp
    1194:	c3                   	ret    
    1195:	be 01 00 00 00       	mov    $0x1,%esi
    119a:	e9 53 ff ff ff       	jmp    10f2 <fourfiles+0x82>
          printf(1, "wrong char\n");
    119f:	83 ec 08             	sub    $0x8,%esp
    11a2:	68 d5 42 00 00       	push   $0x42d5
    11a7:	6a 01                	push   $0x1
    11a9:	e8 32 29 00 00       	call   3ae0 <printf>
          exit();
    11ae:	e8 d0 27 00 00       	call   3983 <exit>
      fd = open(fname, O_CREATE | O_RDWR);
    11b3:	83 ec 08             	sub    $0x8,%esp
    11b6:	68 02 02 00 00       	push   $0x202
    11bb:	56                   	push   %esi
    11bc:	e8 02 28 00 00       	call   39c3 <open>
      if(fd < 0){
    11c1:	83 c4 10             	add    $0x10,%esp
      fd = open(fname, O_CREATE | O_RDWR);
    11c4:	89 c6                	mov    %eax,%esi
      if(fd < 0){
    11c6:	85 c0                	test   %eax,%eax
    11c8:	78 45                	js     120f <fourfiles+0x19f>
      memset(buf, '0'+pi, 512);
    11ca:	83 ec 04             	sub    $0x4,%esp
    11cd:	83 c3 30             	add    $0x30,%ebx
    11d0:	68 00 02 00 00       	push   $0x200
    11d5:	53                   	push   %ebx
    11d6:	bb 0c 00 00 00       	mov    $0xc,%ebx
    11db:	68 c0 86 00 00       	push   $0x86c0
    11e0:	e8 fb 25 00 00       	call   37e0 <memset>
    11e5:	83 c4 10             	add    $0x10,%esp
        if((n = write(fd, buf, 500)) != 500){
    11e8:	83 ec 04             	sub    $0x4,%esp
    11eb:	68 f4 01 00 00       	push   $0x1f4
    11f0:	68 c0 86 00 00       	push   $0x86c0
    11f5:	56                   	push   %esi
    11f6:	e8 a8 27 00 00       	call   39a3 <write>
    11fb:	83 c4 10             	add    $0x10,%esp
    11fe:	3d f4 01 00 00       	cmp    $0x1f4,%eax
    1203:	75 4a                	jne    124f <fourfiles+0x1df>
      for(i = 0; i < 12; i++){
    1205:	83 eb 01             	sub    $0x1,%ebx
    1208:	75 de                	jne    11e8 <fourfiles+0x178>
      exit();
    120a:	e8 74 27 00 00       	call   3983 <exit>
        printf(1, "create failed\n");
    120f:	51                   	push   %ecx
    1210:	51                   	push   %ecx
    1211:	68 4f 45 00 00       	push   $0x454f
    1216:	6a 01                	push   $0x1
    1218:	e8 c3 28 00 00       	call   3ae0 <printf>
        exit();
    121d:	e8 61 27 00 00       	call   3983 <exit>
    1222:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi
      printf(1, "fork failed\n");
    1228:	83 ec 08             	sub    $0x8,%esp
    122b:	68 89 4d 00 00       	push   $0x4d89
    1230:	6a 01                	push   $0x1
    1232:	e8 a9 28 00 00       	call   3ae0 <printf>
      exit();
    1237:	e8 47 27 00 00       	call   3983 <exit>
      printf(1, "wrong length %d\n", total);
    123c:	50                   	push   %eax
    123d:	53                   	push   %ebx
    123e:	68 e1 42 00 00       	push   $0x42e1
    1243:	6a 01                	push   $0x1
    1245:	e8 96 28 00 00       	call   3ae0 <printf>
      exit();
    124a:	e8 34 27 00 00       	call   3983 <exit>
          printf(1, "write failed %d\n", n);
    124f:	52                   	push   %edx
    1250:	50                   	push   %eax
    1251:	68 c4 42 00 00       	push   $0x42c4
    1256:	6a 01                	push   $0x1
    1258:	e8 83 28 00 00       	call   3ae0 <printf>
          exit();
    125d:	e8 21 27 00 00       	call   3983 <exit>
    1262:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    1269:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi

00001270 <createdelete>:
{
    1270:	f3 0f 1e fb          	endbr32 
    1274:	55                   	push   %ebp
    1275:	89 e5                	mov    %esp,%ebp
    1277:	57                   	push   %edi
    1278:	56                   	push   %esi
    1279:	53                   	push   %ebx
  for(pi = 0; pi < 4; pi++){
    127a:	31 db                	xor    %ebx,%ebx
{
    127c:	83 ec 44             	sub    $0x44,%esp
  printf(1, "createdelete test\n");
    127f:	68 00 43 00 00       	push   $0x4300
    1284:	6a 01                	push   $0x1
    1286:	e8 55 28 00 00       	call   3ae0 <printf>
    128b:	83 c4 10             	add    $0x10,%esp
    pid = fork();
    128e:	e8 e8 26 00 00       	call   397b <fork>
    if(pid < 0){
    1293:	85 c0                	test   %eax,%eax
    1295:	0f 88 ce 01 00 00    	js     1469 <createdelete+0x1f9>
    if(pid == 0){
    129b:	0f 84 17 01 00 00    	je     13b8 <createdelete+0x148>
  for(pi = 0; pi < 4; pi++){
    12a1:	83 c3 01             	add    $0x1,%ebx
    12a4:	83 fb 04             	cmp    $0x4,%ebx
    12a7:	75 e5                	jne    128e <createdelete+0x1e>
    wait();
    12a9:	e8 dd 26 00 00       	call   398b <wait>
    12ae:	8d 7d c8             	lea    -0x38(%ebp),%edi
  name[0] = name[1] = name[2] = 0;
    12b1:	be ff ff ff ff       	mov    $0xffffffff,%esi
    wait();
    12b6:	e8 d0 26 00 00       	call   398b <wait>
    12bb:	e8 cb 26 00 00       	call   398b <wait>
    12c0:	e8 c6 26 00 00       	call   398b <wait>
  name[0] = name[1] = name[2] = 0;
    12c5:	c6 45 ca 00          	movb   $0x0,-0x36(%ebp)
  for(i = 0; i < N; i++){
    12c9:	89 7d c0             	mov    %edi,-0x40(%ebp)
    12cc:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
    for(pi = 0; pi < 4; pi++){
    12d0:	8d 46 31             	lea    0x31(%esi),%eax
    12d3:	89 f7                	mov    %esi,%edi
    12d5:	83 c6 01             	add    $0x1,%esi
    12d8:	83 fe 09             	cmp    $0x9,%esi
    12db:	88 45 c7             	mov    %al,-0x39(%ebp)
    12de:	0f 9f c3             	setg   %bl
    12e1:	85 f6                	test   %esi,%esi
    12e3:	0f 94 c0             	sete   %al
    12e6:	09 c3                	or     %eax,%ebx
    12e8:	88 5d c6             	mov    %bl,-0x3a(%ebp)
      name[2] = '\0';
    12eb:	bb 70 00 00 00       	mov    $0x70,%ebx
      fd = open(name, 0);
    12f0:	83 ec 08             	sub    $0x8,%esp
      name[1] = '0' + i;
    12f3:	0f b6 45 c7          	movzbl -0x39(%ebp),%eax
      name[0] = 'p' + pi;
    12f7:	88 5d c8             	mov    %bl,-0x38(%ebp)
      fd = open(name, 0);
    12fa:	6a 00                	push   $0x0
    12fc:	ff 75 c0             	pushl  -0x40(%ebp)
      name[1] = '0' + i;
    12ff:	88 45 c9             	mov    %al,-0x37(%ebp)
      fd = open(name, 0);
    1302:	e8 bc 26 00 00       	call   39c3 <open>
      if((i == 0 || i >= N/2) && fd < 0){
    1307:	83 c4 10             	add    $0x10,%esp
    130a:	80 7d c6 00          	cmpb   $0x0,-0x3a(%ebp)
    130e:	0f 84 8c 00 00 00    	je     13a0 <createdelete+0x130>
    1314:	85 c0                	test   %eax,%eax
    1316:	0f 88 21 01 00 00    	js     143d <createdelete+0x1cd>
      } else if((i >= 1 && i < N/2) && fd >= 0){
    131c:	83 ff 08             	cmp    $0x8,%edi
    131f:	0f 86 60 01 00 00    	jbe    1485 <createdelete+0x215>
        close(fd);
    1325:	83 ec 0c             	sub    $0xc,%esp
    1328:	50                   	push   %eax
    1329:	e8 7d 26 00 00       	call   39ab <close>
    132e:	83 c4 10             	add    $0x10,%esp
    for(pi = 0; pi < 4; pi++){
    1331:	83 c3 01             	add    $0x1,%ebx
    1334:	80 fb 74             	cmp    $0x74,%bl
    1337:	75 b7                	jne    12f0 <createdelete+0x80>
  for(i = 0; i < N; i++){
    1339:	83 fe 13             	cmp    $0x13,%esi
    133c:	75 92                	jne    12d0 <createdelete+0x60>
    133e:	8b 7d c0             	mov    -0x40(%ebp),%edi
    1341:	be 70 00 00 00       	mov    $0x70,%esi
    1346:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    134d:	8d 76 00             	lea    0x0(%esi),%esi
    for(pi = 0; pi < 4; pi++){
    1350:	8d 46 c0             	lea    -0x40(%esi),%eax
  name[0] = name[1] = name[2] = 0;
    1353:	bb 04 00 00 00       	mov    $0x4,%ebx
    1358:	88 45 c7             	mov    %al,-0x39(%ebp)
      unlink(name);
    135b:	83 ec 0c             	sub    $0xc,%esp
      name[0] = 'p' + i;
    135e:	89 f0                	mov    %esi,%eax
      unlink(name);
    1360:	57                   	push   %edi
      name[0] = 'p' + i;
    1361:	88 45 c8             	mov    %al,-0x38(%ebp)
      name[1] = '0' + i;
    1364:	0f b6 45 c7          	movzbl -0x39(%ebp),%eax
    1368:	88 45 c9             	mov    %al,-0x37(%ebp)
      unlink(name);
    136b:	e8 63 26 00 00       	call   39d3 <unlink>
    for(pi = 0; pi < 4; pi++){
    1370:	83 c4 10             	add    $0x10,%esp
    1373:	83 eb 01             	sub    $0x1,%ebx
    1376:	75 e3                	jne    135b <createdelete+0xeb>
  for(i = 0; i < N; i++){
    1378:	83 c6 01             	add    $0x1,%esi
    137b:	89 f0                	mov    %esi,%eax
    137d:	3c 84                	cmp    $0x84,%al
    137f:	75 cf                	jne    1350 <createdelete+0xe0>
  printf(1, "createdelete ok\n");
    1381:	83 ec 08             	sub    $0x8,%esp
    1384:	68 13 43 00 00       	push   $0x4313
    1389:	6a 01                	push   $0x1
    138b:	e8 50 27 00 00       	call   3ae0 <printf>
}
    1390:	8d 65 f4             	lea    -0xc(%ebp),%esp
    1393:	5b                   	pop    %ebx
    1394:	5e                   	pop    %esi
    1395:	5f                   	pop    %edi
    1396:	5d                   	pop    %ebp
    1397:	c3                   	ret    
    1398:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    139f:	90                   	nop
      } else if((i >= 1 && i < N/2) && fd >= 0){
    13a0:	83 ff 08             	cmp    $0x8,%edi
    13a3:	0f 86 d4 00 00 00    	jbe    147d <createdelete+0x20d>
      if(fd >= 0)
    13a9:	85 c0                	test   %eax,%eax
    13ab:	78 84                	js     1331 <createdelete+0xc1>
    13ad:	e9 73 ff ff ff       	jmp    1325 <createdelete+0xb5>
    13b2:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi
      name[0] = 'p' + pi;
    13b8:	83 c3 70             	add    $0x70,%ebx
      name[2] = '\0';
    13bb:	c6 45 ca 00          	movb   $0x0,-0x36(%ebp)
    13bf:	8d 7d c8             	lea    -0x38(%ebp),%edi
      name[0] = 'p' + pi;
    13c2:	88 5d c8             	mov    %bl,-0x38(%ebp)
      name[2] = '\0';
    13c5:	31 db                	xor    %ebx,%ebx
    13c7:	eb 0f                	jmp    13d8 <createdelete+0x168>
    13c9:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
      for(i = 0; i < N; i++){
    13d0:	83 fb 13             	cmp    $0x13,%ebx
    13d3:	74 63                	je     1438 <createdelete+0x1c8>
    13d5:	83 c3 01             	add    $0x1,%ebx
        fd = open(name, O_CREATE | O_RDWR);
    13d8:	83 ec 08             	sub    $0x8,%esp
        name[1] = '0' + i;
    13db:	8d 43 30             	lea    0x30(%ebx),%eax
        fd = open(name, O_CREATE | O_RDWR);
    13de:	68 02 02 00 00       	push   $0x202
    13e3:	57                   	push   %edi
        name[1] = '0' + i;
    13e4:	88 45 c9             	mov    %al,-0x37(%ebp)
        fd = open(name, O_CREATE | O_RDWR);
    13e7:	e8 d7 25 00 00       	call   39c3 <open>
        if(fd < 0){
    13ec:	83 c4 10             	add    $0x10,%esp
    13ef:	85 c0                	test   %eax,%eax
    13f1:	78 62                	js     1455 <createdelete+0x1e5>
        close(fd);
    13f3:	83 ec 0c             	sub    $0xc,%esp
    13f6:	50                   	push   %eax
    13f7:	e8 af 25 00 00       	call   39ab <close>
        if(i > 0 && (i % 2 ) == 0){
    13fc:	83 c4 10             	add    $0x10,%esp
    13ff:	85 db                	test   %ebx,%ebx
    1401:	74 d2                	je     13d5 <createdelete+0x165>
    1403:	f6 c3 01             	test   $0x1,%bl
    1406:	75 c8                	jne    13d0 <createdelete+0x160>
          if(unlink(name) < 0){
    1408:	83 ec 0c             	sub    $0xc,%esp
          name[1] = '0' + (i / 2);
    140b:	89 d8                	mov    %ebx,%eax
          if(unlink(name) < 0){
    140d:	57                   	push   %edi
          name[1] = '0' + (i / 2);
    140e:	d1 f8                	sar    %eax
    1410:	83 c0 30             	add    $0x30,%eax
    1413:	88 45 c9             	mov    %al,-0x37(%ebp)
          if(unlink(name) < 0){
    1416:	e8 b8 25 00 00       	call   39d3 <unlink>
    141b:	83 c4 10             	add    $0x10,%esp
    141e:	85 c0                	test   %eax,%eax
    1420:	79 ae                	jns    13d0 <createdelete+0x160>
            printf(1, "unlink failed\n");
    1422:	52                   	push   %edx
    1423:	52                   	push   %edx
    1424:	68 01 3f 00 00       	push   $0x3f01
    1429:	6a 01                	push   $0x1
    142b:	e8 b0 26 00 00       	call   3ae0 <printf>
            exit();
    1430:	e8 4e 25 00 00       	call   3983 <exit>
    1435:	8d 76 00             	lea    0x0(%esi),%esi
      exit();
    1438:	e8 46 25 00 00       	call   3983 <exit>
    143d:	8b 7d c0             	mov    -0x40(%ebp),%edi
        printf(1, "oops createdelete %s didn't exist\n", name);
    1440:	83 ec 04             	sub    $0x4,%esp
    1443:	57                   	push   %edi
    1444:	68 c0 4f 00 00       	push   $0x4fc0
    1449:	6a 01                	push   $0x1
    144b:	e8 90 26 00 00       	call   3ae0 <printf>
        exit();
    1450:	e8 2e 25 00 00       	call   3983 <exit>
          printf(1, "create failed\n");
    1455:	83 ec 08             	sub    $0x8,%esp
    1458:	68 4f 45 00 00       	push   $0x454f
    145d:	6a 01                	push   $0x1
    145f:	e8 7c 26 00 00       	call   3ae0 <printf>
          exit();
    1464:	e8 1a 25 00 00       	call   3983 <exit>
      printf(1, "fork failed\n");
    1469:	83 ec 08             	sub    $0x8,%esp
    146c:	68 89 4d 00 00       	push   $0x4d89
    1471:	6a 01                	push   $0x1
    1473:	e8 68 26 00 00       	call   3ae0 <printf>
      exit();
    1478:	e8 06 25 00 00       	call   3983 <exit>
      } else if((i >= 1 && i < N/2) && fd >= 0){
    147d:	85 c0                	test   %eax,%eax
    147f:	0f 88 ac fe ff ff    	js     1331 <createdelete+0xc1>
    1485:	8b 7d c0             	mov    -0x40(%ebp),%edi
        printf(1, "oops createdelete %s did exist\n", name);
    1488:	50                   	push   %eax
    1489:	57                   	push   %edi
    148a:	68 e4 4f 00 00       	push   $0x4fe4
    148f:	6a 01                	push   $0x1
    1491:	e8 4a 26 00 00       	call   3ae0 <printf>
        exit();
    1496:	e8 e8 24 00 00       	call   3983 <exit>
    149b:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
    149f:	90                   	nop

000014a0 <unlinkread>:
{
    14a0:	f3 0f 1e fb          	endbr32 
    14a4:	55                   	push   %ebp
    14a5:	89 e5                	mov    %esp,%ebp
    14a7:	56                   	push   %esi
    14a8:	53                   	push   %ebx
  printf(1, "unlinkread test\n");
    14a9:	83 ec 08             	sub    $0x8,%esp
    14ac:	68 24 43 00 00       	push   $0x4324
    14b1:	6a 01                	push   $0x1
    14b3:	e8 28 26 00 00       	call   3ae0 <printf>
  fd = open("unlinkread", O_CREATE | O_RDWR);
    14b8:	5b                   	pop    %ebx
    14b9:	5e                   	pop    %esi
    14ba:	68 02 02 00 00       	push   $0x202
    14bf:	68 35 43 00 00       	push   $0x4335
    14c4:	e8 fa 24 00 00       	call   39c3 <open>
  if(fd < 0){
    14c9:	83 c4 10             	add    $0x10,%esp
    14cc:	85 c0                	test   %eax,%eax
    14ce:	0f 88 e6 00 00 00    	js     15ba <unlinkread+0x11a>
  write(fd, "hello", 5);
    14d4:	83 ec 04             	sub    $0x4,%esp
    14d7:	89 c3                	mov    %eax,%ebx
    14d9:	6a 05                	push   $0x5
    14db:	68 5a 43 00 00       	push   $0x435a
    14e0:	50                   	push   %eax
    14e1:	e8 bd 24 00 00       	call   39a3 <write>
  close(fd);
    14e6:	89 1c 24             	mov    %ebx,(%esp)
    14e9:	e8 bd 24 00 00       	call   39ab <close>
  fd = open("unlinkread", O_RDWR);
    14ee:	58                   	pop    %eax
    14ef:	5a                   	pop    %edx
    14f0:	6a 02                	push   $0x2
    14f2:	68 35 43 00 00       	push   $0x4335
    14f7:	e8 c7 24 00 00       	call   39c3 <open>
  if(fd < 0){
    14fc:	83 c4 10             	add    $0x10,%esp
  fd = open("unlinkread", O_RDWR);
    14ff:	89 c3                	mov    %eax,%ebx
  if(fd < 0){
    1501:	85 c0                	test   %eax,%eax
    1503:	0f 88 10 01 00 00    	js     1619 <unlinkread+0x179>
  if(unlink("unlinkread") != 0){
    1509:	83 ec 0c             	sub    $0xc,%esp
    150c:	68 35 43 00 00       	push   $0x4335
    1511:	e8 bd 24 00 00       	call   39d3 <unlink>
    1516:	83 c4 10             	add    $0x10,%esp
    1519:	85 c0                	test   %eax,%eax
    151b:	0f 85 e5 00 00 00    	jne    1606 <unlinkread+0x166>
  fd1 = open("unlinkread", O_CREATE | O_RDWR);
    1521:	83 ec 08             	sub    $0x8,%esp
    1524:	68 02 02 00 00       	push   $0x202
    1529:	68 35 43 00 00       	push   $0x4335
    152e:	e8 90 24 00 00       	call   39c3 <open>
  write(fd1, "yyy", 3);
    1533:	83 c4 0c             	add    $0xc,%esp
    1536:	6a 03                	push   $0x3
  fd1 = open("unlinkread", O_CREATE | O_RDWR);
    1538:	89 c6                	mov    %eax,%esi
  write(fd1, "yyy", 3);
    153a:	68 92 43 00 00       	push   $0x4392
    153f:	50                   	push   %eax
    1540:	e8 5e 24 00 00       	call   39a3 <write>
  close(fd1);
    1545:	89 34 24             	mov    %esi,(%esp)
    1548:	e8 5e 24 00 00       	call   39ab <close>
  if(read(fd, buf, sizeof(buf)) != 5){
    154d:	83 c4 0c             	add    $0xc,%esp
    1550:	68 00 20 00 00       	push   $0x2000
    1555:	68 c0 86 00 00       	push   $0x86c0
    155a:	53                   	push   %ebx
    155b:	e8 3b 24 00 00       	call   399b <read>
    1560:	83 c4 10             	add    $0x10,%esp
    1563:	83 f8 05             	cmp    $0x5,%eax
    1566:	0f 85 87 00 00 00    	jne    15f3 <unlinkread+0x153>
  if(buf[0] != 'h'){
    156c:	80 3d c0 86 00 00 68 	cmpb   $0x68,0x86c0
    1573:	75 6b                	jne    15e0 <unlinkread+0x140>
  if(write(fd, buf, 10) != 10){
    1575:	83 ec 04             	sub    $0x4,%esp
    1578:	6a 0a                	push   $0xa
    157a:	68 c0 86 00 00       	push   $0x86c0
    157f:	53                   	push   %ebx
    1580:	e8 1e 24 00 00       	call   39a3 <write>
    1585:	83 c4 10             	add    $0x10,%esp
    1588:	83 f8 0a             	cmp    $0xa,%eax
    158b:	75 40                	jne    15cd <unlinkread+0x12d>
  close(fd);
    158d:	83 ec 0c             	sub    $0xc,%esp
    1590:	53                   	push   %ebx
    1591:	e8 15 24 00 00       	call   39ab <close>
  unlink("unlinkread");
    1596:	c7 04 24 35 43 00 00 	movl   $0x4335,(%esp)
    159d:	e8 31 24 00 00       	call   39d3 <unlink>
  printf(1, "unlinkread ok\n");
    15a2:	58                   	pop    %eax
    15a3:	5a                   	pop    %edx
    15a4:	68 dd 43 00 00       	push   $0x43dd
    15a9:	6a 01                	push   $0x1
    15ab:	e8 30 25 00 00       	call   3ae0 <printf>
}
    15b0:	83 c4 10             	add    $0x10,%esp
    15b3:	8d 65 f8             	lea    -0x8(%ebp),%esp
    15b6:	5b                   	pop    %ebx
    15b7:	5e                   	pop    %esi
    15b8:	5d                   	pop    %ebp
    15b9:	c3                   	ret    
    printf(1, "create unlinkread failed\n");
    15ba:	51                   	push   %ecx
    15bb:	51                   	push   %ecx
    15bc:	68 40 43 00 00       	push   $0x4340
    15c1:	6a 01                	push   $0x1
    15c3:	e8 18 25 00 00       	call   3ae0 <printf>
    exit();
    15c8:	e8 b6 23 00 00       	call   3983 <exit>
    printf(1, "unlinkread write failed\n");
    15cd:	51                   	push   %ecx
    15ce:	51                   	push   %ecx
    15cf:	68 c4 43 00 00       	push   $0x43c4
    15d4:	6a 01                	push   $0x1
    15d6:	e8 05 25 00 00       	call   3ae0 <printf>
    exit();
    15db:	e8 a3 23 00 00       	call   3983 <exit>
    printf(1, "unlinkread wrong data\n");
    15e0:	53                   	push   %ebx
    15e1:	53                   	push   %ebx
    15e2:	68 ad 43 00 00       	push   $0x43ad
    15e7:	6a 01                	push   $0x1
    15e9:	e8 f2 24 00 00       	call   3ae0 <printf>
    exit();
    15ee:	e8 90 23 00 00       	call   3983 <exit>
    printf(1, "unlinkread read failed");
    15f3:	56                   	push   %esi
    15f4:	56                   	push   %esi
    15f5:	68 96 43 00 00       	push   $0x4396
    15fa:	6a 01                	push   $0x1
    15fc:	e8 df 24 00 00       	call   3ae0 <printf>
    exit();
    1601:	e8 7d 23 00 00       	call   3983 <exit>
    printf(1, "unlink unlinkread failed\n");
    1606:	50                   	push   %eax
    1607:	50                   	push   %eax
    1608:	68 78 43 00 00       	push   $0x4378
    160d:	6a 01                	push   $0x1
    160f:	e8 cc 24 00 00       	call   3ae0 <printf>
    exit();
    1614:	e8 6a 23 00 00       	call   3983 <exit>
    printf(1, "open unlinkread failed\n");
    1619:	50                   	push   %eax
    161a:	50                   	push   %eax
    161b:	68 60 43 00 00       	push   $0x4360
    1620:	6a 01                	push   $0x1
    1622:	e8 b9 24 00 00       	call   3ae0 <printf>
    exit();
    1627:	e8 57 23 00 00       	call   3983 <exit>
    162c:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi

00001630 <linktest>:
{
    1630:	f3 0f 1e fb          	endbr32 
    1634:	55                   	push   %ebp
    1635:	89 e5                	mov    %esp,%ebp
    1637:	53                   	push   %ebx
    1638:	83 ec 0c             	sub    $0xc,%esp
  printf(1, "linktest\n");
    163b:	68 ec 43 00 00       	push   $0x43ec
    1640:	6a 01                	push   $0x1
    1642:	e8 99 24 00 00       	call   3ae0 <printf>
  unlink("lf1");
    1647:	c7 04 24 f6 43 00 00 	movl   $0x43f6,(%esp)
    164e:	e8 80 23 00 00       	call   39d3 <unlink>
  unlink("lf2");
    1653:	c7 04 24 fa 43 00 00 	movl   $0x43fa,(%esp)
    165a:	e8 74 23 00 00       	call   39d3 <unlink>
  fd = open("lf1", O_CREATE|O_RDWR);
    165f:	58                   	pop    %eax
    1660:	5a                   	pop    %edx
    1661:	68 02 02 00 00       	push   $0x202
    1666:	68 f6 43 00 00       	push   $0x43f6
    166b:	e8 53 23 00 00       	call   39c3 <open>
  if(fd < 0){
    1670:	83 c4 10             	add    $0x10,%esp
    1673:	85 c0                	test   %eax,%eax
    1675:	0f 88 1e 01 00 00    	js     1799 <linktest+0x169>
  if(write(fd, "hello", 5) != 5){
    167b:	83 ec 04             	sub    $0x4,%esp
    167e:	89 c3                	mov    %eax,%ebx
    1680:	6a 05                	push   $0x5
    1682:	68 5a 43 00 00       	push   $0x435a
    1687:	50                   	push   %eax
    1688:	e8 16 23 00 00       	call   39a3 <write>
    168d:	83 c4 10             	add    $0x10,%esp
    1690:	83 f8 05             	cmp    $0x5,%eax
    1693:	0f 85 98 01 00 00    	jne    1831 <linktest+0x201>
  close(fd);
    1699:	83 ec 0c             	sub    $0xc,%esp
    169c:	53                   	push   %ebx
    169d:	e8 09 23 00 00       	call   39ab <close>
  if(link("lf1", "lf2") < 0){
    16a2:	5b                   	pop    %ebx
    16a3:	58                   	pop    %eax
    16a4:	68 fa 43 00 00       	push   $0x43fa
    16a9:	68 f6 43 00 00       	push   $0x43f6
    16ae:	e8 30 23 00 00       	call   39e3 <link>
    16b3:	83 c4 10             	add    $0x10,%esp
    16b6:	85 c0                	test   %eax,%eax
    16b8:	0f 88 60 01 00 00    	js     181e <linktest+0x1ee>
  unlink("lf1");
    16be:	83 ec 0c             	sub    $0xc,%esp
    16c1:	68 f6 43 00 00       	push   $0x43f6
    16c6:	e8 08 23 00 00       	call   39d3 <unlink>
  if(open("lf1", 0) >= 0){
    16cb:	58                   	pop    %eax
    16cc:	5a                   	pop    %edx
    16cd:	6a 00                	push   $0x0
    16cf:	68 f6 43 00 00       	push   $0x43f6
    16d4:	e8 ea 22 00 00       	call   39c3 <open>
    16d9:	83 c4 10             	add    $0x10,%esp
    16dc:	85 c0                	test   %eax,%eax
    16de:	0f 89 27 01 00 00    	jns    180b <linktest+0x1db>
  fd = open("lf2", 0);
    16e4:	83 ec 08             	sub    $0x8,%esp
    16e7:	6a 00                	push   $0x0
    16e9:	68 fa 43 00 00       	push   $0x43fa
    16ee:	e8 d0 22 00 00       	call   39c3 <open>
  if(fd < 0){
    16f3:	83 c4 10             	add    $0x10,%esp
  fd = open("lf2", 0);
    16f6:	89 c3                	mov    %eax,%ebx
  if(fd < 0){
    16f8:	85 c0                	test   %eax,%eax
    16fa:	0f 88 f8 00 00 00    	js     17f8 <linktest+0x1c8>
  if(read(fd, buf, sizeof(buf)) != 5){
    1700:	83 ec 04             	sub    $0x4,%esp
    1703:	68 00 20 00 00       	push   $0x2000
    1708:	68 c0 86 00 00       	push   $0x86c0
    170d:	50                   	push   %eax
    170e:	e8 88 22 00 00       	call   399b <read>
    1713:	83 c4 10             	add    $0x10,%esp
    1716:	83 f8 05             	cmp    $0x5,%eax
    1719:	0f 85 c6 00 00 00    	jne    17e5 <linktest+0x1b5>
  close(fd);
    171f:	83 ec 0c             	sub    $0xc,%esp
    1722:	53                   	push   %ebx
    1723:	e8 83 22 00 00       	call   39ab <close>
  if(link("lf2", "lf2") >= 0){
    1728:	58                   	pop    %eax
    1729:	5a                   	pop    %edx
    172a:	68 fa 43 00 00       	push   $0x43fa
    172f:	68 fa 43 00 00       	push   $0x43fa
    1734:	e8 aa 22 00 00       	call   39e3 <link>
    1739:	83 c4 10             	add    $0x10,%esp
    173c:	85 c0                	test   %eax,%eax
    173e:	0f 89 8e 00 00 00    	jns    17d2 <linktest+0x1a2>
  unlink("lf2");
    1744:	83 ec 0c             	sub    $0xc,%esp
    1747:	68 fa 43 00 00       	push   $0x43fa
    174c:	e8 82 22 00 00       	call   39d3 <unlink>
  if(link("lf2", "lf1") >= 0){
    1751:	59                   	pop    %ecx
    1752:	5b                   	pop    %ebx
    1753:	68 f6 43 00 00       	push   $0x43f6
    1758:	68 fa 43 00 00       	push   $0x43fa
    175d:	e8 81 22 00 00       	call   39e3 <link>
    1762:	83 c4 10             	add    $0x10,%esp
    1765:	85 c0                	test   %eax,%eax
    1767:	79 56                	jns    17bf <linktest+0x18f>
  if(link(".", "lf1") >= 0){
    1769:	83 ec 08             	sub    $0x8,%esp
    176c:	68 f6 43 00 00       	push   $0x43f6
    1771:	68 be 46 00 00       	push   $0x46be
    1776:	e8 68 22 00 00       	call   39e3 <link>
    177b:	83 c4 10             	add    $0x10,%esp
    177e:	85 c0                	test   %eax,%eax
    1780:	79 2a                	jns    17ac <linktest+0x17c>
  printf(1, "linktest ok\n");
    1782:	83 ec 08             	sub    $0x8,%esp
    1785:	68 94 44 00 00       	push   $0x4494
    178a:	6a 01                	push   $0x1
    178c:	e8 4f 23 00 00       	call   3ae0 <printf>
}
    1791:	8b 5d fc             	mov    -0x4(%ebp),%ebx
    1794:	83 c4 10             	add    $0x10,%esp
    1797:	c9                   	leave  
    1798:	c3                   	ret    
    printf(1, "create lf1 failed\n");
    1799:	50                   	push   %eax
    179a:	50                   	push   %eax
    179b:	68 fe 43 00 00       	push   $0x43fe
    17a0:	6a 01                	push   $0x1
    17a2:	e8 39 23 00 00       	call   3ae0 <printf>
    exit();
    17a7:	e8 d7 21 00 00       	call   3983 <exit>
    printf(1, "link . lf1 succeeded! oops\n");
    17ac:	50                   	push   %eax
    17ad:	50                   	push   %eax
    17ae:	68 78 44 00 00       	push   $0x4478
    17b3:	6a 01                	push   $0x1
    17b5:	e8 26 23 00 00       	call   3ae0 <printf>
    exit();
    17ba:	e8 c4 21 00 00       	call   3983 <exit>
    printf(1, "link non-existant succeeded! oops\n");
    17bf:	52                   	push   %edx
    17c0:	52                   	push   %edx
    17c1:	68 2c 50 00 00       	push   $0x502c
    17c6:	6a 01                	push   $0x1
    17c8:	e8 13 23 00 00       	call   3ae0 <printf>
    exit();
    17cd:	e8 b1 21 00 00       	call   3983 <exit>
    printf(1, "link lf2 lf2 succeeded! oops\n");
    17d2:	50                   	push   %eax
    17d3:	50                   	push   %eax
    17d4:	68 5a 44 00 00       	push   $0x445a
    17d9:	6a 01                	push   $0x1
    17db:	e8 00 23 00 00       	call   3ae0 <printf>
    exit();
    17e0:	e8 9e 21 00 00       	call   3983 <exit>
    printf(1, "read lf2 failed\n");
    17e5:	51                   	push   %ecx
    17e6:	51                   	push   %ecx
    17e7:	68 49 44 00 00       	push   $0x4449
    17ec:	6a 01                	push   $0x1
    17ee:	e8 ed 22 00 00       	call   3ae0 <printf>
    exit();
    17f3:	e8 8b 21 00 00       	call   3983 <exit>
    printf(1, "open lf2 failed\n");
    17f8:	53                   	push   %ebx
    17f9:	53                   	push   %ebx
    17fa:	68 38 44 00 00       	push   $0x4438
    17ff:	6a 01                	push   $0x1
    1801:	e8 da 22 00 00       	call   3ae0 <printf>
    exit();
    1806:	e8 78 21 00 00       	call   3983 <exit>
    printf(1, "unlinked lf1 but it is still there!\n");
    180b:	50                   	push   %eax
    180c:	50                   	push   %eax
    180d:	68 04 50 00 00       	push   $0x5004
    1812:	6a 01                	push   $0x1
    1814:	e8 c7 22 00 00       	call   3ae0 <printf>
    exit();
    1819:	e8 65 21 00 00       	call   3983 <exit>
    printf(1, "link lf1 lf2 failed\n");
    181e:	51                   	push   %ecx
    181f:	51                   	push   %ecx
    1820:	68 23 44 00 00       	push   $0x4423
    1825:	6a 01                	push   $0x1
    1827:	e8 b4 22 00 00       	call   3ae0 <printf>
    exit();
    182c:	e8 52 21 00 00       	call   3983 <exit>
    printf(1, "write lf1 failed\n");
    1831:	50                   	push   %eax
    1832:	50                   	push   %eax
    1833:	68 11 44 00 00       	push   $0x4411
    1838:	6a 01                	push   $0x1
    183a:	e8 a1 22 00 00       	call   3ae0 <printf>
    exit();
    183f:	e8 3f 21 00 00       	call   3983 <exit>
    1844:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    184b:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
    184f:	90                   	nop

00001850 <concreate>:
{
    1850:	f3 0f 1e fb          	endbr32 
    1854:	55                   	push   %ebp
    1855:	89 e5                	mov    %esp,%ebp
    1857:	57                   	push   %edi
    1858:	56                   	push   %esi
  for(i = 0; i < 40; i++){
    1859:	31 f6                	xor    %esi,%esi
{
    185b:	53                   	push   %ebx
    185c:	8d 5d ad             	lea    -0x53(%ebp),%ebx
    185f:	83 ec 64             	sub    $0x64,%esp
  printf(1, "concreate test\n");
    1862:	68 a1 44 00 00       	push   $0x44a1
    1867:	6a 01                	push   $0x1
    1869:	e8 72 22 00 00       	call   3ae0 <printf>
  file[0] = 'C';
    186e:	c6 45 ad 43          	movb   $0x43,-0x53(%ebp)
  file[2] = '\0';
    1872:	83 c4 10             	add    $0x10,%esp
    1875:	c6 45 af 00          	movb   $0x0,-0x51(%ebp)
  for(i = 0; i < 40; i++){
    1879:	eb 48                	jmp    18c3 <concreate+0x73>
    187b:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
    187f:	90                   	nop
    1880:	69 c6 ab aa aa aa    	imul   $0xaaaaaaab,%esi,%eax
    if(pid && (i % 3) == 1){
    1886:	3d ab aa aa aa       	cmp    $0xaaaaaaab,%eax
    188b:	0f 83 af 00 00 00    	jae    1940 <concreate+0xf0>
      fd = open(file, O_CREATE | O_RDWR);
    1891:	83 ec 08             	sub    $0x8,%esp
    1894:	68 02 02 00 00       	push   $0x202
    1899:	53                   	push   %ebx
    189a:	e8 24 21 00 00       	call   39c3 <open>
      if(fd < 0){
    189f:	83 c4 10             	add    $0x10,%esp
    18a2:	85 c0                	test   %eax,%eax
    18a4:	78 5f                	js     1905 <concreate+0xb5>
      close(fd);
    18a6:	83 ec 0c             	sub    $0xc,%esp
  for(i = 0; i < 40; i++){
    18a9:	83 c6 01             	add    $0x1,%esi
      close(fd);
    18ac:	50                   	push   %eax
    18ad:	e8 f9 20 00 00       	call   39ab <close>
    18b2:	83 c4 10             	add    $0x10,%esp
      wait();
    18b5:	e8 d1 20 00 00       	call   398b <wait>
  for(i = 0; i < 40; i++){
    18ba:	83 fe 28             	cmp    $0x28,%esi
    18bd:	0f 84 9f 00 00 00    	je     1962 <concreate+0x112>
    unlink(file);
    18c3:	83 ec 0c             	sub    $0xc,%esp
    file[1] = '0' + i;
    18c6:	8d 46 30             	lea    0x30(%esi),%eax
    unlink(file);
    18c9:	53                   	push   %ebx
    file[1] = '0' + i;
    18ca:	88 45 ae             	mov    %al,-0x52(%ebp)
    unlink(file);
    18cd:	e8 01 21 00 00       	call   39d3 <unlink>
    pid = fork();
    18d2:	e8 a4 20 00 00       	call   397b <fork>
    if(pid && (i % 3) == 1){
    18d7:	83 c4 10             	add    $0x10,%esp
    18da:	85 c0                	test   %eax,%eax
    18dc:	75 a2                	jne    1880 <concreate+0x30>
      link("C0", file);
    18de:	69 f6 cd cc cc cc    	imul   $0xcccccccd,%esi,%esi
    } else if(pid == 0 && (i % 5) == 1){
    18e4:	81 fe cd cc cc cc    	cmp    $0xcccccccd,%esi
    18ea:	73 34                	jae    1920 <concreate+0xd0>
      fd = open(file, O_CREATE | O_RDWR);
    18ec:	83 ec 08             	sub    $0x8,%esp
    18ef:	68 02 02 00 00       	push   $0x202
    18f4:	53                   	push   %ebx
    18f5:	e8 c9 20 00 00       	call   39c3 <open>
      if(fd < 0){
    18fa:	83 c4 10             	add    $0x10,%esp
    18fd:	85 c0                	test   %eax,%eax
    18ff:	0f 89 39 02 00 00    	jns    1b3e <concreate+0x2ee>
        printf(1, "concreate create %s failed\n", file);
    1905:	83 ec 04             	sub    $0x4,%esp
    1908:	53                   	push   %ebx
    1909:	68 b4 44 00 00       	push   $0x44b4
    190e:	6a 01                	push   $0x1
    1910:	e8 cb 21 00 00       	call   3ae0 <printf>
        exit();
    1915:	e8 69 20 00 00       	call   3983 <exit>
    191a:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi
      link("C0", file);
    1920:	83 ec 08             	sub    $0x8,%esp
    1923:	53                   	push   %ebx
    1924:	68 b1 44 00 00       	push   $0x44b1
    1929:	e8 b5 20 00 00       	call   39e3 <link>
    192e:	83 c4 10             	add    $0x10,%esp
      exit();
    1931:	e8 4d 20 00 00       	call   3983 <exit>
    1936:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    193d:	8d 76 00             	lea    0x0(%esi),%esi
      link("C0", file);
    1940:	83 ec 08             	sub    $0x8,%esp
  for(i = 0; i < 40; i++){
    1943:	83 c6 01             	add    $0x1,%esi
      link("C0", file);
    1946:	53                   	push   %ebx
    1947:	68 b1 44 00 00       	push   $0x44b1
    194c:	e8 92 20 00 00       	call   39e3 <link>
    1951:	83 c4 10             	add    $0x10,%esp
      wait();
    1954:	e8 32 20 00 00       	call   398b <wait>
  for(i = 0; i < 40; i++){
    1959:	83 fe 28             	cmp    $0x28,%esi
    195c:	0f 85 61 ff ff ff    	jne    18c3 <concreate+0x73>
  memset(fa, 0, sizeof(fa));
    1962:	83 ec 04             	sub    $0x4,%esp
    1965:	8d 45 c0             	lea    -0x40(%ebp),%eax
    1968:	6a 28                	push   $0x28
    196a:	6a 00                	push   $0x0
    196c:	50                   	push   %eax
    196d:	e8 6e 1e 00 00       	call   37e0 <memset>
  fd = open(".", 0);
    1972:	5e                   	pop    %esi
    1973:	5f                   	pop    %edi
    1974:	6a 00                	push   $0x0
    1976:	68 be 46 00 00       	push   $0x46be
    197b:	8d 7d b0             	lea    -0x50(%ebp),%edi
    197e:	e8 40 20 00 00       	call   39c3 <open>
  n = 0;
    1983:	c7 45 a4 00 00 00 00 	movl   $0x0,-0x5c(%ebp)
  while(read(fd, &de, sizeof(de)) > 0){
    198a:	83 c4 10             	add    $0x10,%esp
  fd = open(".", 0);
    198d:	89 c6                	mov    %eax,%esi
  while(read(fd, &de, sizeof(de)) > 0){
    198f:	90                   	nop
    1990:	83 ec 04             	sub    $0x4,%esp
    1993:	6a 10                	push   $0x10
    1995:	57                   	push   %edi
    1996:	56                   	push   %esi
    1997:	e8 ff 1f 00 00       	call   399b <read>
    199c:	83 c4 10             	add    $0x10,%esp
    199f:	85 c0                	test   %eax,%eax
    19a1:	7e 3d                	jle    19e0 <concreate+0x190>
    if(de.inum == 0)
    19a3:	66 83 7d b0 00       	cmpw   $0x0,-0x50(%ebp)
    19a8:	74 e6                	je     1990 <concreate+0x140>
    if(de.name[0] == 'C' && de.name[2] == '\0'){
    19aa:	80 7d b2 43          	cmpb   $0x43,-0x4e(%ebp)
    19ae:	75 e0                	jne    1990 <concreate+0x140>
    19b0:	80 7d b4 00          	cmpb   $0x0,-0x4c(%ebp)
    19b4:	75 da                	jne    1990 <concreate+0x140>
      i = de.name[1] - '0';
    19b6:	0f be 45 b3          	movsbl -0x4d(%ebp),%eax
    19ba:	83 e8 30             	sub    $0x30,%eax
      if(i < 0 || i >= sizeof(fa)){
    19bd:	83 f8 27             	cmp    $0x27,%eax
    19c0:	0f 87 60 01 00 00    	ja     1b26 <concreate+0x2d6>
      if(fa[i]){
    19c6:	80 7c 05 c0 00       	cmpb   $0x0,-0x40(%ebp,%eax,1)
    19cb:	0f 85 3d 01 00 00    	jne    1b0e <concreate+0x2be>
      n++;
    19d1:	83 45 a4 01          	addl   $0x1,-0x5c(%ebp)
      fa[i] = 1;
    19d5:	c6 44 05 c0 01       	movb   $0x1,-0x40(%ebp,%eax,1)
      n++;
    19da:	eb b4                	jmp    1990 <concreate+0x140>
    19dc:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
  close(fd);
    19e0:	83 ec 0c             	sub    $0xc,%esp
    19e3:	56                   	push   %esi
    19e4:	e8 c2 1f 00 00       	call   39ab <close>
  if(n != 40){
    19e9:	83 c4 10             	add    $0x10,%esp
    19ec:	83 7d a4 28          	cmpl   $0x28,-0x5c(%ebp)
    19f0:	0f 85 05 01 00 00    	jne    1afb <concreate+0x2ab>
  for(i = 0; i < 40; i++){
    19f6:	31 f6                	xor    %esi,%esi
    19f8:	eb 4c                	jmp    1a46 <concreate+0x1f6>
    19fa:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi
       ((i % 3) == 1 && pid != 0)){
    1a00:	85 ff                	test   %edi,%edi
    1a02:	74 05                	je     1a09 <concreate+0x1b9>
    1a04:	83 f8 01             	cmp    $0x1,%eax
    1a07:	74 6c                	je     1a75 <concreate+0x225>
      unlink(file);
    1a09:	83 ec 0c             	sub    $0xc,%esp
    1a0c:	53                   	push   %ebx
    1a0d:	e8 c1 1f 00 00       	call   39d3 <unlink>
      unlink(file);
    1a12:	89 1c 24             	mov    %ebx,(%esp)
    1a15:	e8 b9 1f 00 00       	call   39d3 <unlink>
      unlink(file);
    1a1a:	89 1c 24             	mov    %ebx,(%esp)
    1a1d:	e8 b1 1f 00 00       	call   39d3 <unlink>
      unlink(file);
    1a22:	89 1c 24             	mov    %ebx,(%esp)
    1a25:	e8 a9 1f 00 00       	call   39d3 <unlink>
    1a2a:	83 c4 10             	add    $0x10,%esp
    if(pid == 0)
    1a2d:	85 ff                	test   %edi,%edi
    1a2f:	0f 84 fc fe ff ff    	je     1931 <concreate+0xe1>
      wait();
    1a35:	e8 51 1f 00 00       	call   398b <wait>
  for(i = 0; i < 40; i++){
    1a3a:	83 c6 01             	add    $0x1,%esi
    1a3d:	83 fe 28             	cmp    $0x28,%esi
    1a40:	0f 84 8a 00 00 00    	je     1ad0 <concreate+0x280>
    file[1] = '0' + i;
    1a46:	8d 46 30             	lea    0x30(%esi),%eax
    1a49:	88 45 ae             	mov    %al,-0x52(%ebp)
    pid = fork();
    1a4c:	e8 2a 1f 00 00       	call   397b <fork>
    1a51:	89 c7                	mov    %eax,%edi
    if(pid < 0){
    1a53:	85 c0                	test   %eax,%eax
    1a55:	0f 88 8c 00 00 00    	js     1ae7 <concreate+0x297>
    if(((i % 3) == 0 && pid == 0) ||
    1a5b:	b8 ab aa aa aa       	mov    $0xaaaaaaab,%eax
    1a60:	f7 e6                	mul    %esi
    1a62:	89 d0                	mov    %edx,%eax
    1a64:	83 e2 fe             	and    $0xfffffffe,%edx
    1a67:	d1 e8                	shr    %eax
    1a69:	01 c2                	add    %eax,%edx
    1a6b:	89 f0                	mov    %esi,%eax
    1a6d:	29 d0                	sub    %edx,%eax
    1a6f:	89 c1                	mov    %eax,%ecx
    1a71:	09 f9                	or     %edi,%ecx
    1a73:	75 8b                	jne    1a00 <concreate+0x1b0>
      close(open(file, 0));
    1a75:	83 ec 08             	sub    $0x8,%esp
    1a78:	6a 00                	push   $0x0
    1a7a:	53                   	push   %ebx
    1a7b:	e8 43 1f 00 00       	call   39c3 <open>
    1a80:	89 04 24             	mov    %eax,(%esp)
    1a83:	e8 23 1f 00 00       	call   39ab <close>
      close(open(file, 0));
    1a88:	58                   	pop    %eax
    1a89:	5a                   	pop    %edx
    1a8a:	6a 00                	push   $0x0
    1a8c:	53                   	push   %ebx
    1a8d:	e8 31 1f 00 00       	call   39c3 <open>
    1a92:	89 04 24             	mov    %eax,(%esp)
    1a95:	e8 11 1f 00 00       	call   39ab <close>
      close(open(file, 0));
    1a9a:	59                   	pop    %ecx
    1a9b:	58                   	pop    %eax
    1a9c:	6a 00                	push   $0x0
    1a9e:	53                   	push   %ebx
    1a9f:	e8 1f 1f 00 00       	call   39c3 <open>
    1aa4:	89 04 24             	mov    %eax,(%esp)
    1aa7:	e8 ff 1e 00 00       	call   39ab <close>
      close(open(file, 0));
    1aac:	58                   	pop    %eax
    1aad:	5a                   	pop    %edx
    1aae:	6a 00                	push   $0x0
    1ab0:	53                   	push   %ebx
    1ab1:	e8 0d 1f 00 00       	call   39c3 <open>
    1ab6:	89 04 24             	mov    %eax,(%esp)
    1ab9:	e8 ed 1e 00 00       	call   39ab <close>
    1abe:	83 c4 10             	add    $0x10,%esp
    1ac1:	e9 67 ff ff ff       	jmp    1a2d <concreate+0x1dd>
    1ac6:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    1acd:	8d 76 00             	lea    0x0(%esi),%esi
  printf(1, "concreate ok\n");
    1ad0:	83 ec 08             	sub    $0x8,%esp
    1ad3:	68 06 45 00 00       	push   $0x4506
    1ad8:	6a 01                	push   $0x1
    1ada:	e8 01 20 00 00       	call   3ae0 <printf>
}
    1adf:	8d 65 f4             	lea    -0xc(%ebp),%esp
    1ae2:	5b                   	pop    %ebx
    1ae3:	5e                   	pop    %esi
    1ae4:	5f                   	pop    %edi
    1ae5:	5d                   	pop    %ebp
    1ae6:	c3                   	ret    
      printf(1, "fork failed\n");
    1ae7:	83 ec 08             	sub    $0x8,%esp
    1aea:	68 89 4d 00 00       	push   $0x4d89
    1aef:	6a 01                	push   $0x1
    1af1:	e8 ea 1f 00 00       	call   3ae0 <printf>
      exit();
    1af6:	e8 88 1e 00 00       	call   3983 <exit>
    printf(1, "concreate not enough files in directory listing\n");
    1afb:	51                   	push   %ecx
    1afc:	51                   	push   %ecx
    1afd:	68 50 50 00 00       	push   $0x5050
    1b02:	6a 01                	push   $0x1
    1b04:	e8 d7 1f 00 00       	call   3ae0 <printf>
    exit();
    1b09:	e8 75 1e 00 00       	call   3983 <exit>
        printf(1, "concreate duplicate file %s\n", de.name);
    1b0e:	83 ec 04             	sub    $0x4,%esp
    1b11:	8d 45 b2             	lea    -0x4e(%ebp),%eax
    1b14:	50                   	push   %eax
    1b15:	68 e9 44 00 00       	push   $0x44e9
    1b1a:	6a 01                	push   $0x1
    1b1c:	e8 bf 1f 00 00       	call   3ae0 <printf>
        exit();
    1b21:	e8 5d 1e 00 00       	call   3983 <exit>
        printf(1, "concreate weird file %s\n", de.name);
    1b26:	83 ec 04             	sub    $0x4,%esp
    1b29:	8d 45 b2             	lea    -0x4e(%ebp),%eax
    1b2c:	50                   	push   %eax
    1b2d:	68 d0 44 00 00       	push   $0x44d0
    1b32:	6a 01                	push   $0x1
    1b34:	e8 a7 1f 00 00       	call   3ae0 <printf>
        exit();
    1b39:	e8 45 1e 00 00       	call   3983 <exit>
      close(fd);
    1b3e:	83 ec 0c             	sub    $0xc,%esp
    1b41:	50                   	push   %eax
    1b42:	e8 64 1e 00 00       	call   39ab <close>
    1b47:	83 c4 10             	add    $0x10,%esp
    1b4a:	e9 e2 fd ff ff       	jmp    1931 <concreate+0xe1>
    1b4f:	90                   	nop

00001b50 <linkunlink>:
{
    1b50:	f3 0f 1e fb          	endbr32 
    1b54:	55                   	push   %ebp
    1b55:	89 e5                	mov    %esp,%ebp
    1b57:	57                   	push   %edi
    1b58:	56                   	push   %esi
    1b59:	53                   	push   %ebx
    1b5a:	83 ec 24             	sub    $0x24,%esp
  printf(1, "linkunlink test\n");
    1b5d:	68 14 45 00 00       	push   $0x4514
    1b62:	6a 01                	push   $0x1
    1b64:	e8 77 1f 00 00       	call   3ae0 <printf>
  unlink("x");
    1b69:	c7 04 24 a1 47 00 00 	movl   $0x47a1,(%esp)
    1b70:	e8 5e 1e 00 00       	call   39d3 <unlink>
  pid = fork();
    1b75:	e8 01 1e 00 00       	call   397b <fork>
  if(pid < 0){
    1b7a:	83 c4 10             	add    $0x10,%esp
  pid = fork();
    1b7d:	89 45 e4             	mov    %eax,-0x1c(%ebp)
  if(pid < 0){
    1b80:	85 c0                	test   %eax,%eax
    1b82:	0f 88 b2 00 00 00    	js     1c3a <linkunlink+0xea>
  unsigned int x = (pid ? 1 : 97);
    1b88:	83 7d e4 01          	cmpl   $0x1,-0x1c(%ebp)
    1b8c:	bb 64 00 00 00       	mov    $0x64,%ebx
    if((x % 3) == 0){
    1b91:	be ab aa aa aa       	mov    $0xaaaaaaab,%esi
  unsigned int x = (pid ? 1 : 97);
    1b96:	19 ff                	sbb    %edi,%edi
    1b98:	83 e7 60             	and    $0x60,%edi
    1b9b:	83 c7 01             	add    $0x1,%edi
    1b9e:	eb 1a                	jmp    1bba <linkunlink+0x6a>
    } else if((x % 3) == 1){
    1ba0:	83 f8 01             	cmp    $0x1,%eax
    1ba3:	74 7b                	je     1c20 <linkunlink+0xd0>
      unlink("x");
    1ba5:	83 ec 0c             	sub    $0xc,%esp
    1ba8:	68 a1 47 00 00       	push   $0x47a1
    1bad:	e8 21 1e 00 00       	call   39d3 <unlink>
    1bb2:	83 c4 10             	add    $0x10,%esp
  for(i = 0; i < 100; i++){
    1bb5:	83 eb 01             	sub    $0x1,%ebx
    1bb8:	74 41                	je     1bfb <linkunlink+0xab>
    x = x * 1103515245 + 12345;
    1bba:	69 cf 6d 4e c6 41    	imul   $0x41c64e6d,%edi,%ecx
    1bc0:	8d b9 39 30 00 00    	lea    0x3039(%ecx),%edi
    if((x % 3) == 0){
    1bc6:	89 f8                	mov    %edi,%eax
    1bc8:	f7 e6                	mul    %esi
    1bca:	89 d0                	mov    %edx,%eax
    1bcc:	83 e2 fe             	and    $0xfffffffe,%edx
    1bcf:	d1 e8                	shr    %eax
    1bd1:	01 c2                	add    %eax,%edx
    1bd3:	89 f8                	mov    %edi,%eax
    1bd5:	29 d0                	sub    %edx,%eax
    1bd7:	75 c7                	jne    1ba0 <linkunlink+0x50>
      close(open("x", O_RDWR | O_CREATE));
    1bd9:	83 ec 08             	sub    $0x8,%esp
    1bdc:	68 02 02 00 00       	push   $0x202
    1be1:	68 a1 47 00 00       	push   $0x47a1
    1be6:	e8 d8 1d 00 00       	call   39c3 <open>
    1beb:	89 04 24             	mov    %eax,(%esp)
    1bee:	e8 b8 1d 00 00       	call   39ab <close>
    1bf3:	83 c4 10             	add    $0x10,%esp
  for(i = 0; i < 100; i++){
    1bf6:	83 eb 01             	sub    $0x1,%ebx
    1bf9:	75 bf                	jne    1bba <linkunlink+0x6a>
  if(pid)
    1bfb:	8b 45 e4             	mov    -0x1c(%ebp),%eax
    1bfe:	85 c0                	test   %eax,%eax
    1c00:	74 4b                	je     1c4d <linkunlink+0xfd>
    wait();
    1c02:	e8 84 1d 00 00       	call   398b <wait>
  printf(1, "linkunlink ok\n");
    1c07:	83 ec 08             	sub    $0x8,%esp
    1c0a:	68 29 45 00 00       	push   $0x4529
    1c0f:	6a 01                	push   $0x1
    1c11:	e8 ca 1e 00 00       	call   3ae0 <printf>
}
    1c16:	8d 65 f4             	lea    -0xc(%ebp),%esp
    1c19:	5b                   	pop    %ebx
    1c1a:	5e                   	pop    %esi
    1c1b:	5f                   	pop    %edi
    1c1c:	5d                   	pop    %ebp
    1c1d:	c3                   	ret    
    1c1e:	66 90                	xchg   %ax,%ax
      link("cat", "x");
    1c20:	83 ec 08             	sub    $0x8,%esp
    1c23:	68 a1 47 00 00       	push   $0x47a1
    1c28:	68 25 45 00 00       	push   $0x4525
    1c2d:	e8 b1 1d 00 00       	call   39e3 <link>
    1c32:	83 c4 10             	add    $0x10,%esp
    1c35:	e9 7b ff ff ff       	jmp    1bb5 <linkunlink+0x65>
    printf(1, "fork failed\n");
    1c3a:	52                   	push   %edx
    1c3b:	52                   	push   %edx
    1c3c:	68 89 4d 00 00       	push   $0x4d89
    1c41:	6a 01                	push   $0x1
    1c43:	e8 98 1e 00 00       	call   3ae0 <printf>
    exit();
    1c48:	e8 36 1d 00 00       	call   3983 <exit>
    exit();
    1c4d:	e8 31 1d 00 00       	call   3983 <exit>
    1c52:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    1c59:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi

00001c60 <bigdir>:
{
    1c60:	f3 0f 1e fb          	endbr32 
    1c64:	55                   	push   %ebp
    1c65:	89 e5                	mov    %esp,%ebp
    1c67:	57                   	push   %edi
    1c68:	56                   	push   %esi
    1c69:	53                   	push   %ebx
    1c6a:	83 ec 24             	sub    $0x24,%esp
  printf(1, "bigdir test\n");
    1c6d:	68 38 45 00 00       	push   $0x4538
    1c72:	6a 01                	push   $0x1
    1c74:	e8 67 1e 00 00       	call   3ae0 <printf>
  unlink("bd");
    1c79:	c7 04 24 45 45 00 00 	movl   $0x4545,(%esp)
    1c80:	e8 4e 1d 00 00       	call   39d3 <unlink>
  fd = open("bd", O_CREATE);
    1c85:	5a                   	pop    %edx
    1c86:	59                   	pop    %ecx
    1c87:	68 00 02 00 00       	push   $0x200
    1c8c:	68 45 45 00 00       	push   $0x4545
    1c91:	e8 2d 1d 00 00       	call   39c3 <open>
  if(fd < 0){
    1c96:	83 c4 10             	add    $0x10,%esp
    1c99:	85 c0                	test   %eax,%eax
    1c9b:	0f 88 ea 00 00 00    	js     1d8b <bigdir+0x12b>
  close(fd);
    1ca1:	83 ec 0c             	sub    $0xc,%esp
  for(i = 0; i < 500; i++){
    1ca4:	31 f6                	xor    %esi,%esi
    1ca6:	8d 7d de             	lea    -0x22(%ebp),%edi
  close(fd);
    1ca9:	50                   	push   %eax
    1caa:	e8 fc 1c 00 00       	call   39ab <close>
    1caf:	83 c4 10             	add    $0x10,%esp
    1cb2:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi
    name[1] = '0' + (i / 64);
    1cb8:	89 f0                	mov    %esi,%eax
    if(link("bd", name) != 0){
    1cba:	83 ec 08             	sub    $0x8,%esp
    name[0] = 'x';
    1cbd:	c6 45 de 78          	movb   $0x78,-0x22(%ebp)
    name[1] = '0' + (i / 64);
    1cc1:	c1 f8 06             	sar    $0x6,%eax
    if(link("bd", name) != 0){
    1cc4:	57                   	push   %edi
    name[1] = '0' + (i / 64);
    1cc5:	83 c0 30             	add    $0x30,%eax
    if(link("bd", name) != 0){
    1cc8:	68 45 45 00 00       	push   $0x4545
    name[1] = '0' + (i / 64);
    1ccd:	88 45 df             	mov    %al,-0x21(%ebp)
    name[2] = '0' + (i % 64);
    1cd0:	89 f0                	mov    %esi,%eax
    1cd2:	83 e0 3f             	and    $0x3f,%eax
    name[3] = '\0';
    1cd5:	c6 45 e1 00          	movb   $0x0,-0x1f(%ebp)
    name[2] = '0' + (i % 64);
    1cd9:	83 c0 30             	add    $0x30,%eax
    1cdc:	88 45 e0             	mov    %al,-0x20(%ebp)
    if(link("bd", name) != 0){
    1cdf:	e8 ff 1c 00 00       	call   39e3 <link>
    1ce4:	83 c4 10             	add    $0x10,%esp
    1ce7:	89 c3                	mov    %eax,%ebx
    1ce9:	85 c0                	test   %eax,%eax
    1ceb:	75 76                	jne    1d63 <bigdir+0x103>
  for(i = 0; i < 500; i++){
    1ced:	83 c6 01             	add    $0x1,%esi
    1cf0:	81 fe f4 01 00 00    	cmp    $0x1f4,%esi
    1cf6:	75 c0                	jne    1cb8 <bigdir+0x58>
  unlink("bd");
    1cf8:	83 ec 0c             	sub    $0xc,%esp
    1cfb:	68 45 45 00 00       	push   $0x4545
    1d00:	e8 ce 1c 00 00       	call   39d3 <unlink>
    1d05:	83 c4 10             	add    $0x10,%esp
    1d08:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    1d0f:	90                   	nop
    name[1] = '0' + (i / 64);
    1d10:	89 d8                	mov    %ebx,%eax
    if(unlink(name) != 0){
    1d12:	83 ec 0c             	sub    $0xc,%esp
    name[0] = 'x';
    1d15:	c6 45 de 78          	movb   $0x78,-0x22(%ebp)
    name[1] = '0' + (i / 64);
    1d19:	c1 f8 06             	sar    $0x6,%eax
    if(unlink(name) != 0){
    1d1c:	57                   	push   %edi
    name[1] = '0' + (i / 64);
    1d1d:	83 c0 30             	add    $0x30,%eax
    name[3] = '\0';
    1d20:	c6 45 e1 00          	movb   $0x0,-0x1f(%ebp)
    name[1] = '0' + (i / 64);
    1d24:	88 45 df             	mov    %al,-0x21(%ebp)
    name[2] = '0' + (i % 64);
    1d27:	89 d8                	mov    %ebx,%eax
    1d29:	83 e0 3f             	and    $0x3f,%eax
    1d2c:	83 c0 30             	add    $0x30,%eax
    1d2f:	88 45 e0             	mov    %al,-0x20(%ebp)
    if(unlink(name) != 0){
    1d32:	e8 9c 1c 00 00       	call   39d3 <unlink>
    1d37:	83 c4 10             	add    $0x10,%esp
    1d3a:	85 c0                	test   %eax,%eax
    1d3c:	75 39                	jne    1d77 <bigdir+0x117>
  for(i = 0; i < 500; i++){
    1d3e:	83 c3 01             	add    $0x1,%ebx
    1d41:	81 fb f4 01 00 00    	cmp    $0x1f4,%ebx
    1d47:	75 c7                	jne    1d10 <bigdir+0xb0>
  printf(1, "bigdir ok\n");
    1d49:	83 ec 08             	sub    $0x8,%esp
    1d4c:	68 87 45 00 00       	push   $0x4587
    1d51:	6a 01                	push   $0x1
    1d53:	e8 88 1d 00 00       	call   3ae0 <printf>
    1d58:	83 c4 10             	add    $0x10,%esp
}
    1d5b:	8d 65 f4             	lea    -0xc(%ebp),%esp
    1d5e:	5b                   	pop    %ebx
    1d5f:	5e                   	pop    %esi
    1d60:	5f                   	pop    %edi
    1d61:	5d                   	pop    %ebp
    1d62:	c3                   	ret    
      printf(1, "bigdir link failed\n");
    1d63:	83 ec 08             	sub    $0x8,%esp
    1d66:	68 5e 45 00 00       	push   $0x455e
    1d6b:	6a 01                	push   $0x1
    1d6d:	e8 6e 1d 00 00       	call   3ae0 <printf>
      exit();
    1d72:	e8 0c 1c 00 00       	call   3983 <exit>
      printf(1, "bigdir unlink failed");
    1d77:	83 ec 08             	sub    $0x8,%esp
    1d7a:	68 72 45 00 00       	push   $0x4572
    1d7f:	6a 01                	push   $0x1
    1d81:	e8 5a 1d 00 00       	call   3ae0 <printf>
      exit();
    1d86:	e8 f8 1b 00 00       	call   3983 <exit>
    printf(1, "bigdir create failed\n");
    1d8b:	50                   	push   %eax
    1d8c:	50                   	push   %eax
    1d8d:	68 48 45 00 00       	push   $0x4548
    1d92:	6a 01                	push   $0x1
    1d94:	e8 47 1d 00 00       	call   3ae0 <printf>
    exit();
    1d99:	e8 e5 1b 00 00       	call   3983 <exit>
    1d9e:	66 90                	xchg   %ax,%ax

00001da0 <subdir>:
{
    1da0:	f3 0f 1e fb          	endbr32 
    1da4:	55                   	push   %ebp
    1da5:	89 e5                	mov    %esp,%ebp
    1da7:	53                   	push   %ebx
    1da8:	83 ec 0c             	sub    $0xc,%esp
  printf(1, "subdir test\n");
    1dab:	68 92 45 00 00       	push   $0x4592
    1db0:	6a 01                	push   $0x1
    1db2:	e8 29 1d 00 00       	call   3ae0 <printf>
  unlink("ff");
    1db7:	c7 04 24 1b 46 00 00 	movl   $0x461b,(%esp)
    1dbe:	e8 10 1c 00 00       	call   39d3 <unlink>
  if(mkdir("dd") != 0){
    1dc3:	c7 04 24 b8 46 00 00 	movl   $0x46b8,(%esp)
    1dca:	e8 1c 1c 00 00       	call   39eb <mkdir>
    1dcf:	83 c4 10             	add    $0x10,%esp
    1dd2:	85 c0                	test   %eax,%eax
    1dd4:	0f 85 b3 05 00 00    	jne    238d <subdir+0x5ed>
  fd = open("dd/ff", O_CREATE | O_RDWR);
    1dda:	83 ec 08             	sub    $0x8,%esp
    1ddd:	68 02 02 00 00       	push   $0x202
    1de2:	68 f1 45 00 00       	push   $0x45f1
    1de7:	e8 d7 1b 00 00       	call   39c3 <open>
  if(fd < 0){
    1dec:	83 c4 10             	add    $0x10,%esp
  fd = open("dd/ff", O_CREATE | O_RDWR);
    1def:	89 c3                	mov    %eax,%ebx
  if(fd < 0){
    1df1:	85 c0                	test   %eax,%eax
    1df3:	0f 88 81 05 00 00    	js     237a <subdir+0x5da>
  write(fd, "ff", 2);
    1df9:	83 ec 04             	sub    $0x4,%esp
    1dfc:	6a 02                	push   $0x2
    1dfe:	68 1b 46 00 00       	push   $0x461b
    1e03:	50                   	push   %eax
    1e04:	e8 9a 1b 00 00       	call   39a3 <write>
  close(fd);
    1e09:	89 1c 24             	mov    %ebx,(%esp)
    1e0c:	e8 9a 1b 00 00       	call   39ab <close>
  if(unlink("dd") >= 0){
    1e11:	c7 04 24 b8 46 00 00 	movl   $0x46b8,(%esp)
    1e18:	e8 b6 1b 00 00       	call   39d3 <unlink>
    1e1d:	83 c4 10             	add    $0x10,%esp
    1e20:	85 c0                	test   %eax,%eax
    1e22:	0f 89 3f 05 00 00    	jns    2367 <subdir+0x5c7>
  if(mkdir("/dd/dd") != 0){
    1e28:	83 ec 0c             	sub    $0xc,%esp
    1e2b:	68 cc 45 00 00       	push   $0x45cc
    1e30:	e8 b6 1b 00 00       	call   39eb <mkdir>
    1e35:	83 c4 10             	add    $0x10,%esp
    1e38:	85 c0                	test   %eax,%eax
    1e3a:	0f 85 14 05 00 00    	jne    2354 <subdir+0x5b4>
  fd = open("dd/dd/ff", O_CREATE | O_RDWR);
    1e40:	83 ec 08             	sub    $0x8,%esp
    1e43:	68 02 02 00 00       	push   $0x202
    1e48:	68 ee 45 00 00       	push   $0x45ee
    1e4d:	e8 71 1b 00 00       	call   39c3 <open>
  if(fd < 0){
    1e52:	83 c4 10             	add    $0x10,%esp
  fd = open("dd/dd/ff", O_CREATE | O_RDWR);
    1e55:	89 c3                	mov    %eax,%ebx
  if(fd < 0){
    1e57:	85 c0                	test   %eax,%eax
    1e59:	0f 88 24 04 00 00    	js     2283 <subdir+0x4e3>
  write(fd, "FF", 2);
    1e5f:	83 ec 04             	sub    $0x4,%esp
    1e62:	6a 02                	push   $0x2
    1e64:	68 0f 46 00 00       	push   $0x460f
    1e69:	50                   	push   %eax
    1e6a:	e8 34 1b 00 00       	call   39a3 <write>
  close(fd);
    1e6f:	89 1c 24             	mov    %ebx,(%esp)
    1e72:	e8 34 1b 00 00       	call   39ab <close>
  fd = open("dd/dd/../ff", 0);
    1e77:	58                   	pop    %eax
    1e78:	5a                   	pop    %edx
    1e79:	6a 00                	push   $0x0
    1e7b:	68 12 46 00 00       	push   $0x4612
    1e80:	e8 3e 1b 00 00       	call   39c3 <open>
  if(fd < 0){
    1e85:	83 c4 10             	add    $0x10,%esp
  fd = open("dd/dd/../ff", 0);
    1e88:	89 c3                	mov    %eax,%ebx
  if(fd < 0){
    1e8a:	85 c0                	test   %eax,%eax
    1e8c:	0f 88 de 03 00 00    	js     2270 <subdir+0x4d0>
  cc = read(fd, buf, sizeof(buf));
    1e92:	83 ec 04             	sub    $0x4,%esp
    1e95:	68 00 20 00 00       	push   $0x2000
    1e9a:	68 c0 86 00 00       	push   $0x86c0
    1e9f:	50                   	push   %eax
    1ea0:	e8 f6 1a 00 00       	call   399b <read>
  if(cc != 2 || buf[0] != 'f'){
    1ea5:	83 c4 10             	add    $0x10,%esp
    1ea8:	83 f8 02             	cmp    $0x2,%eax
    1eab:	0f 85 3a 03 00 00    	jne    21eb <subdir+0x44b>
    1eb1:	80 3d c0 86 00 00 66 	cmpb   $0x66,0x86c0
    1eb8:	0f 85 2d 03 00 00    	jne    21eb <subdir+0x44b>
  close(fd);
    1ebe:	83 ec 0c             	sub    $0xc,%esp
    1ec1:	53                   	push   %ebx
    1ec2:	e8 e4 1a 00 00       	call   39ab <close>
  if(link("dd/dd/ff", "dd/dd/ffff") != 0){
    1ec7:	59                   	pop    %ecx
    1ec8:	5b                   	pop    %ebx
    1ec9:	68 52 46 00 00       	push   $0x4652
    1ece:	68 ee 45 00 00       	push   $0x45ee
    1ed3:	e8 0b 1b 00 00       	call   39e3 <link>
    1ed8:	83 c4 10             	add    $0x10,%esp
    1edb:	85 c0                	test   %eax,%eax
    1edd:	0f 85 c6 03 00 00    	jne    22a9 <subdir+0x509>
  if(unlink("dd/dd/ff") != 0){
    1ee3:	83 ec 0c             	sub    $0xc,%esp
    1ee6:	68 ee 45 00 00       	push   $0x45ee
    1eeb:	e8 e3 1a 00 00       	call   39d3 <unlink>
    1ef0:	83 c4 10             	add    $0x10,%esp
    1ef3:	85 c0                	test   %eax,%eax
    1ef5:	0f 85 16 03 00 00    	jne    2211 <subdir+0x471>
  if(open("dd/dd/ff", O_RDONLY) >= 0){
    1efb:	83 ec 08             	sub    $0x8,%esp
    1efe:	6a 00                	push   $0x0
    1f00:	68 ee 45 00 00       	push   $0x45ee
    1f05:	e8 b9 1a 00 00       	call   39c3 <open>
    1f0a:	83 c4 10             	add    $0x10,%esp
    1f0d:	85 c0                	test   %eax,%eax
    1f0f:	0f 89 2c 04 00 00    	jns    2341 <subdir+0x5a1>
  if(chdir("dd") != 0){
    1f15:	83 ec 0c             	sub    $0xc,%esp
    1f18:	68 b8 46 00 00       	push   $0x46b8
    1f1d:	e8 d1 1a 00 00       	call   39f3 <chdir>
    1f22:	83 c4 10             	add    $0x10,%esp
    1f25:	85 c0                	test   %eax,%eax
    1f27:	0f 85 01 04 00 00    	jne    232e <subdir+0x58e>
  if(chdir("dd/../../dd") != 0){
    1f2d:	83 ec 0c             	sub    $0xc,%esp
    1f30:	68 86 46 00 00       	push   $0x4686
    1f35:	e8 b9 1a 00 00       	call   39f3 <chdir>
    1f3a:	83 c4 10             	add    $0x10,%esp
    1f3d:	85 c0                	test   %eax,%eax
    1f3f:	0f 85 b9 02 00 00    	jne    21fe <subdir+0x45e>
  if(chdir("dd/../../../dd") != 0){
    1f45:	83 ec 0c             	sub    $0xc,%esp
    1f48:	68 ac 46 00 00       	push   $0x46ac
    1f4d:	e8 a1 1a 00 00       	call   39f3 <chdir>
    1f52:	83 c4 10             	add    $0x10,%esp
    1f55:	85 c0                	test   %eax,%eax
    1f57:	0f 85 a1 02 00 00    	jne    21fe <subdir+0x45e>
  if(chdir("./..") != 0){
    1f5d:	83 ec 0c             	sub    $0xc,%esp
    1f60:	68 bb 46 00 00       	push   $0x46bb
    1f65:	e8 89 1a 00 00       	call   39f3 <chdir>
    1f6a:	83 c4 10             	add    $0x10,%esp
    1f6d:	85 c0                	test   %eax,%eax
    1f6f:	0f 85 21 03 00 00    	jne    2296 <subdir+0x4f6>
  fd = open("dd/dd/ffff", 0);
    1f75:	83 ec 08             	sub    $0x8,%esp
    1f78:	6a 00                	push   $0x0
    1f7a:	68 52 46 00 00       	push   $0x4652
    1f7f:	e8 3f 1a 00 00       	call   39c3 <open>
  if(fd < 0){
    1f84:	83 c4 10             	add    $0x10,%esp
  fd = open("dd/dd/ffff", 0);
    1f87:	89 c3                	mov    %eax,%ebx
  if(fd < 0){
    1f89:	85 c0                	test   %eax,%eax
    1f8b:	0f 88 e0 04 00 00    	js     2471 <subdir+0x6d1>
  if(read(fd, buf, sizeof(buf)) != 2){
    1f91:	83 ec 04             	sub    $0x4,%esp
    1f94:	68 00 20 00 00       	push   $0x2000
    1f99:	68 c0 86 00 00       	push   $0x86c0
    1f9e:	50                   	push   %eax
    1f9f:	e8 f7 19 00 00       	call   399b <read>
    1fa4:	83 c4 10             	add    $0x10,%esp
    1fa7:	83 f8 02             	cmp    $0x2,%eax
    1faa:	0f 85 ae 04 00 00    	jne    245e <subdir+0x6be>
  close(fd);
    1fb0:	83 ec 0c             	sub    $0xc,%esp
    1fb3:	53                   	push   %ebx
    1fb4:	e8 f2 19 00 00       	call   39ab <close>
  if(open("dd/dd/ff", O_RDONLY) >= 0){
    1fb9:	58                   	pop    %eax
    1fba:	5a                   	pop    %edx
    1fbb:	6a 00                	push   $0x0
    1fbd:	68 ee 45 00 00       	push   $0x45ee
    1fc2:	e8 fc 19 00 00       	call   39c3 <open>
    1fc7:	83 c4 10             	add    $0x10,%esp
    1fca:	85 c0                	test   %eax,%eax
    1fcc:	0f 89 65 02 00 00    	jns    2237 <subdir+0x497>
  if(open("dd/ff/ff", O_CREATE|O_RDWR) >= 0){
    1fd2:	83 ec 08             	sub    $0x8,%esp
    1fd5:	68 02 02 00 00       	push   $0x202
    1fda:	68 06 47 00 00       	push   $0x4706
    1fdf:	e8 df 19 00 00       	call   39c3 <open>
    1fe4:	83 c4 10             	add    $0x10,%esp
    1fe7:	85 c0                	test   %eax,%eax
    1fe9:	0f 89 35 02 00 00    	jns    2224 <subdir+0x484>
  if(open("dd/xx/ff", O_CREATE|O_RDWR) >= 0){
    1fef:	83 ec 08             	sub    $0x8,%esp
    1ff2:	68 02 02 00 00       	push   $0x202
    1ff7:	68 2b 47 00 00       	push   $0x472b
    1ffc:	e8 c2 19 00 00       	call   39c3 <open>
    2001:	83 c4 10             	add    $0x10,%esp
    2004:	85 c0                	test   %eax,%eax
    2006:	0f 89 0f 03 00 00    	jns    231b <subdir+0x57b>
  if(open("dd", O_CREATE) >= 0){
    200c:	83 ec 08             	sub    $0x8,%esp
    200f:	68 00 02 00 00       	push   $0x200
    2014:	68 b8 46 00 00       	push   $0x46b8
    2019:	e8 a5 19 00 00       	call   39c3 <open>
    201e:	83 c4 10             	add    $0x10,%esp
    2021:	85 c0                	test   %eax,%eax
    2023:	0f 89 df 02 00 00    	jns    2308 <subdir+0x568>
  if(open("dd", O_RDWR) >= 0){
    2029:	83 ec 08             	sub    $0x8,%esp
    202c:	6a 02                	push   $0x2
    202e:	68 b8 46 00 00       	push   $0x46b8
    2033:	e8 8b 19 00 00       	call   39c3 <open>
    2038:	83 c4 10             	add    $0x10,%esp
    203b:	85 c0                	test   %eax,%eax
    203d:	0f 89 b2 02 00 00    	jns    22f5 <subdir+0x555>
  if(open("dd", O_WRONLY) >= 0){
    2043:	83 ec 08             	sub    $0x8,%esp
    2046:	6a 01                	push   $0x1
    2048:	68 b8 46 00 00       	push   $0x46b8
    204d:	e8 71 19 00 00       	call   39c3 <open>
    2052:	83 c4 10             	add    $0x10,%esp
    2055:	85 c0                	test   %eax,%eax
    2057:	0f 89 85 02 00 00    	jns    22e2 <subdir+0x542>
  if(link("dd/ff/ff", "dd/dd/xx") == 0){
    205d:	83 ec 08             	sub    $0x8,%esp
    2060:	68 9a 47 00 00       	push   $0x479a
    2065:	68 06 47 00 00       	push   $0x4706
    206a:	e8 74 19 00 00       	call   39e3 <link>
    206f:	83 c4 10             	add    $0x10,%esp
    2072:	85 c0                	test   %eax,%eax
    2074:	0f 84 55 02 00 00    	je     22cf <subdir+0x52f>
  if(link("dd/xx/ff", "dd/dd/xx") == 0){
    207a:	83 ec 08             	sub    $0x8,%esp
    207d:	68 9a 47 00 00       	push   $0x479a
    2082:	68 2b 47 00 00       	push   $0x472b
    2087:	e8 57 19 00 00       	call   39e3 <link>
    208c:	83 c4 10             	add    $0x10,%esp
    208f:	85 c0                	test   %eax,%eax
    2091:	0f 84 25 02 00 00    	je     22bc <subdir+0x51c>
  if(link("dd/ff", "dd/dd/ffff") == 0){
    2097:	83 ec 08             	sub    $0x8,%esp
    209a:	68 52 46 00 00       	push   $0x4652
    209f:	68 f1 45 00 00       	push   $0x45f1
    20a4:	e8 3a 19 00 00       	call   39e3 <link>
    20a9:	83 c4 10             	add    $0x10,%esp
    20ac:	85 c0                	test   %eax,%eax
    20ae:	0f 84 a9 01 00 00    	je     225d <subdir+0x4bd>
  if(mkdir("dd/ff/ff") == 0){
    20b4:	83 ec 0c             	sub    $0xc,%esp
    20b7:	68 06 47 00 00       	push   $0x4706
    20bc:	e8 2a 19 00 00       	call   39eb <mkdir>
    20c1:	83 c4 10             	add    $0x10,%esp
    20c4:	85 c0                	test   %eax,%eax
    20c6:	0f 84 7e 01 00 00    	je     224a <subdir+0x4aa>
  if(mkdir("dd/xx/ff") == 0){
    20cc:	83 ec 0c             	sub    $0xc,%esp
    20cf:	68 2b 47 00 00       	push   $0x472b
    20d4:	e8 12 19 00 00       	call   39eb <mkdir>
    20d9:	83 c4 10             	add    $0x10,%esp
    20dc:	85 c0                	test   %eax,%eax
    20de:	0f 84 67 03 00 00    	je     244b <subdir+0x6ab>
  if(mkdir("dd/dd/ffff") == 0){
    20e4:	83 ec 0c             	sub    $0xc,%esp
    20e7:	68 52 46 00 00       	push   $0x4652
    20ec:	e8 fa 18 00 00       	call   39eb <mkdir>
    20f1:	83 c4 10             	add    $0x10,%esp
    20f4:	85 c0                	test   %eax,%eax
    20f6:	0f 84 3c 03 00 00    	je     2438 <subdir+0x698>
  if(unlink("dd/xx/ff") == 0){
    20fc:	83 ec 0c             	sub    $0xc,%esp
    20ff:	68 2b 47 00 00       	push   $0x472b
    2104:	e8 ca 18 00 00       	call   39d3 <unlink>
    2109:	83 c4 10             	add    $0x10,%esp
    210c:	85 c0                	test   %eax,%eax
    210e:	0f 84 11 03 00 00    	je     2425 <subdir+0x685>
  if(unlink("dd/ff/ff") == 0){
    2114:	83 ec 0c             	sub    $0xc,%esp
    2117:	68 06 47 00 00       	push   $0x4706
    211c:	e8 b2 18 00 00       	call   39d3 <unlink>
    2121:	83 c4 10             	add    $0x10,%esp
    2124:	85 c0                	test   %eax,%eax
    2126:	0f 84 e6 02 00 00    	je     2412 <subdir+0x672>
  if(chdir("dd/ff") == 0){
    212c:	83 ec 0c             	sub    $0xc,%esp
    212f:	68 f1 45 00 00       	push   $0x45f1
    2134:	e8 ba 18 00 00       	call   39f3 <chdir>
    2139:	83 c4 10             	add    $0x10,%esp
    213c:	85 c0                	test   %eax,%eax
    213e:	0f 84 bb 02 00 00    	je     23ff <subdir+0x65f>
  if(chdir("dd/xx") == 0){
    2144:	83 ec 0c             	sub    $0xc,%esp
    2147:	68 9d 47 00 00       	push   $0x479d
    214c:	e8 a2 18 00 00       	call   39f3 <chdir>
    2151:	83 c4 10             	add    $0x10,%esp
    2154:	85 c0                	test   %eax,%eax
    2156:	0f 84 90 02 00 00    	je     23ec <subdir+0x64c>
  if(unlink("dd/dd/ffff") != 0){
    215c:	83 ec 0c             	sub    $0xc,%esp
    215f:	68 52 46 00 00       	push   $0x4652
    2164:	e8 6a 18 00 00       	call   39d3 <unlink>
    2169:	83 c4 10             	add    $0x10,%esp
    216c:	85 c0                	test   %eax,%eax
    216e:	0f 85 9d 00 00 00    	jne    2211 <subdir+0x471>
  if(unlink("dd/ff") != 0){
    2174:	83 ec 0c             	sub    $0xc,%esp
    2177:	68 f1 45 00 00       	push   $0x45f1
    217c:	e8 52 18 00 00       	call   39d3 <unlink>
    2181:	83 c4 10             	add    $0x10,%esp
    2184:	85 c0                	test   %eax,%eax
    2186:	0f 85 4d 02 00 00    	jne    23d9 <subdir+0x639>
  if(unlink("dd") == 0){
    218c:	83 ec 0c             	sub    $0xc,%esp
    218f:	68 b8 46 00 00       	push   $0x46b8
    2194:	e8 3a 18 00 00       	call   39d3 <unlink>
    2199:	83 c4 10             	add    $0x10,%esp
    219c:	85 c0                	test   %eax,%eax
    219e:	0f 84 22 02 00 00    	je     23c6 <subdir+0x626>
  if(unlink("dd/dd") < 0){
    21a4:	83 ec 0c             	sub    $0xc,%esp
    21a7:	68 cd 45 00 00       	push   $0x45cd
    21ac:	e8 22 18 00 00       	call   39d3 <unlink>
    21b1:	83 c4 10             	add    $0x10,%esp
    21b4:	85 c0                	test   %eax,%eax
    21b6:	0f 88 f7 01 00 00    	js     23b3 <subdir+0x613>
  if(unlink("dd") < 0){
    21bc:	83 ec 0c             	sub    $0xc,%esp
    21bf:	68 b8 46 00 00       	push   $0x46b8
    21c4:	e8 0a 18 00 00       	call   39d3 <unlink>
    21c9:	83 c4 10             	add    $0x10,%esp
    21cc:	85 c0                	test   %eax,%eax
    21ce:	0f 88 cc 01 00 00    	js     23a0 <subdir+0x600>
  printf(1, "subdir ok\n");
    21d4:	83 ec 08             	sub    $0x8,%esp
    21d7:	68 9a 48 00 00       	push   $0x489a
    21dc:	6a 01                	push   $0x1
    21de:	e8 fd 18 00 00       	call   3ae0 <printf>
}
    21e3:	8b 5d fc             	mov    -0x4(%ebp),%ebx
    21e6:	83 c4 10             	add    $0x10,%esp
    21e9:	c9                   	leave  
    21ea:	c3                   	ret    
    printf(1, "dd/dd/../ff wrong content\n");
    21eb:	50                   	push   %eax
    21ec:	50                   	push   %eax
    21ed:	68 37 46 00 00       	push   $0x4637
    21f2:	6a 01                	push   $0x1
    21f4:	e8 e7 18 00 00       	call   3ae0 <printf>
    exit();
    21f9:	e8 85 17 00 00       	call   3983 <exit>
    printf(1, "chdir dd/../../dd failed\n");
    21fe:	50                   	push   %eax
    21ff:	50                   	push   %eax
    2200:	68 92 46 00 00       	push   $0x4692
    2205:	6a 01                	push   $0x1
    2207:	e8 d4 18 00 00       	call   3ae0 <printf>
    exit();
    220c:	e8 72 17 00 00       	call   3983 <exit>
    printf(1, "unlink dd/dd/ff failed\n");
    2211:	50                   	push   %eax
    2212:	50                   	push   %eax
    2213:	68 5d 46 00 00       	push   $0x465d
    2218:	6a 01                	push   $0x1
    221a:	e8 c1 18 00 00       	call   3ae0 <printf>
    exit();
    221f:	e8 5f 17 00 00       	call   3983 <exit>
    printf(1, "create dd/ff/ff succeeded!\n");
    2224:	51                   	push   %ecx
    2225:	51                   	push   %ecx
    2226:	68 0f 47 00 00       	push   $0x470f
    222b:	6a 01                	push   $0x1
    222d:	e8 ae 18 00 00       	call   3ae0 <printf>
    exit();
    2232:	e8 4c 17 00 00       	call   3983 <exit>
    printf(1, "open (unlinked) dd/dd/ff succeeded!\n");
    2237:	53                   	push   %ebx
    2238:	53                   	push   %ebx
    2239:	68 f4 50 00 00       	push   $0x50f4
    223e:	6a 01                	push   $0x1
    2240:	e8 9b 18 00 00       	call   3ae0 <printf>
    exit();
    2245:	e8 39 17 00 00       	call   3983 <exit>
    printf(1, "mkdir dd/ff/ff succeeded!\n");
    224a:	51                   	push   %ecx
    224b:	51                   	push   %ecx
    224c:	68 a3 47 00 00       	push   $0x47a3
    2251:	6a 01                	push   $0x1
    2253:	e8 88 18 00 00       	call   3ae0 <printf>
    exit();
    2258:	e8 26 17 00 00       	call   3983 <exit>
    printf(1, "link dd/ff dd/dd/ffff succeeded!\n");
    225d:	53                   	push   %ebx
    225e:	53                   	push   %ebx
    225f:	68 64 51 00 00       	push   $0x5164
    2264:	6a 01                	push   $0x1
    2266:	e8 75 18 00 00       	call   3ae0 <printf>
    exit();
    226b:	e8 13 17 00 00       	call   3983 <exit>
    printf(1, "open dd/dd/../ff failed\n");
    2270:	50                   	push   %eax
    2271:	50                   	push   %eax
    2272:	68 1e 46 00 00       	push   $0x461e
    2277:	6a 01                	push   $0x1
    2279:	e8 62 18 00 00       	call   3ae0 <printf>
    exit();
    227e:	e8 00 17 00 00       	call   3983 <exit>
    printf(1, "create dd/dd/ff failed\n");
    2283:	51                   	push   %ecx
    2284:	51                   	push   %ecx
    2285:	68 f7 45 00 00       	push   $0x45f7
    228a:	6a 01                	push   $0x1
    228c:	e8 4f 18 00 00       	call   3ae0 <printf>
    exit();
    2291:	e8 ed 16 00 00       	call   3983 <exit>
    printf(1, "chdir ./.. failed\n");
    2296:	50                   	push   %eax
    2297:	50                   	push   %eax
    2298:	68 c0 46 00 00       	push   $0x46c0
    229d:	6a 01                	push   $0x1
    229f:	e8 3c 18 00 00       	call   3ae0 <printf>
    exit();
    22a4:	e8 da 16 00 00       	call   3983 <exit>
    printf(1, "link dd/dd/ff dd/dd/ffff failed\n");
    22a9:	52                   	push   %edx
    22aa:	52                   	push   %edx
    22ab:	68 ac 50 00 00       	push   $0x50ac
    22b0:	6a 01                	push   $0x1
    22b2:	e8 29 18 00 00       	call   3ae0 <printf>
    exit();
    22b7:	e8 c7 16 00 00       	call   3983 <exit>
    printf(1, "link dd/xx/ff dd/dd/xx succeeded!\n");
    22bc:	50                   	push   %eax
    22bd:	50                   	push   %eax
    22be:	68 40 51 00 00       	push   $0x5140
    22c3:	6a 01                	push   $0x1
    22c5:	e8 16 18 00 00       	call   3ae0 <printf>
    exit();
    22ca:	e8 b4 16 00 00       	call   3983 <exit>
    printf(1, "link dd/ff/ff dd/dd/xx succeeded!\n");
    22cf:	50                   	push   %eax
    22d0:	50                   	push   %eax
    22d1:	68 1c 51 00 00       	push   $0x511c
    22d6:	6a 01                	push   $0x1
    22d8:	e8 03 18 00 00       	call   3ae0 <printf>
    exit();
    22dd:	e8 a1 16 00 00       	call   3983 <exit>
    printf(1, "open dd wronly succeeded!\n");
    22e2:	50                   	push   %eax
    22e3:	50                   	push   %eax
    22e4:	68 7f 47 00 00       	push   $0x477f
    22e9:	6a 01                	push   $0x1
    22eb:	e8 f0 17 00 00       	call   3ae0 <printf>
    exit();
    22f0:	e8 8e 16 00 00       	call   3983 <exit>
    printf(1, "open dd rdwr succeeded!\n");
    22f5:	50                   	push   %eax
    22f6:	50                   	push   %eax
    22f7:	68 66 47 00 00       	push   $0x4766
    22fc:	6a 01                	push   $0x1
    22fe:	e8 dd 17 00 00       	call   3ae0 <printf>
    exit();
    2303:	e8 7b 16 00 00       	call   3983 <exit>
    printf(1, "create dd succeeded!\n");
    2308:	50                   	push   %eax
    2309:	50                   	push   %eax
    230a:	68 50 47 00 00       	push   $0x4750
    230f:	6a 01                	push   $0x1
    2311:	e8 ca 17 00 00       	call   3ae0 <printf>
    exit();
    2316:	e8 68 16 00 00       	call   3983 <exit>
    printf(1, "create dd/xx/ff succeeded!\n");
    231b:	52                   	push   %edx
    231c:	52                   	push   %edx
    231d:	68 34 47 00 00       	push   $0x4734
    2322:	6a 01                	push   $0x1
    2324:	e8 b7 17 00 00       	call   3ae0 <printf>
    exit();
    2329:	e8 55 16 00 00       	call   3983 <exit>
    printf(1, "chdir dd failed\n");
    232e:	50                   	push   %eax
    232f:	50                   	push   %eax
    2330:	68 75 46 00 00       	push   $0x4675
    2335:	6a 01                	push   $0x1
    2337:	e8 a4 17 00 00       	call   3ae0 <printf>
    exit();
    233c:	e8 42 16 00 00       	call   3983 <exit>
    printf(1, "open (unlinked) dd/dd/ff succeeded\n");
    2341:	50                   	push   %eax
    2342:	50                   	push   %eax
    2343:	68 d0 50 00 00       	push   $0x50d0
    2348:	6a 01                	push   $0x1
    234a:	e8 91 17 00 00       	call   3ae0 <printf>
    exit();
    234f:	e8 2f 16 00 00       	call   3983 <exit>
    printf(1, "subdir mkdir dd/dd failed\n");
    2354:	53                   	push   %ebx
    2355:	53                   	push   %ebx
    2356:	68 d3 45 00 00       	push   $0x45d3
    235b:	6a 01                	push   $0x1
    235d:	e8 7e 17 00 00       	call   3ae0 <printf>
    exit();
    2362:	e8 1c 16 00 00       	call   3983 <exit>
    printf(1, "unlink dd (non-empty dir) succeeded!\n");
    2367:	50                   	push   %eax
    2368:	50                   	push   %eax
    2369:	68 84 50 00 00       	push   $0x5084
    236e:	6a 01                	push   $0x1
    2370:	e8 6b 17 00 00       	call   3ae0 <printf>
    exit();
    2375:	e8 09 16 00 00       	call   3983 <exit>
    printf(1, "create dd/ff failed\n");
    237a:	50                   	push   %eax
    237b:	50                   	push   %eax
    237c:	68 b7 45 00 00       	push   $0x45b7
    2381:	6a 01                	push   $0x1
    2383:	e8 58 17 00 00       	call   3ae0 <printf>
    exit();
    2388:	e8 f6 15 00 00       	call   3983 <exit>
    printf(1, "subdir mkdir dd failed\n");
    238d:	50                   	push   %eax
    238e:	50                   	push   %eax
    238f:	68 9f 45 00 00       	push   $0x459f
    2394:	6a 01                	push   $0x1
    2396:	e8 45 17 00 00       	call   3ae0 <printf>
    exit();
    239b:	e8 e3 15 00 00       	call   3983 <exit>
    printf(1, "unlink dd failed\n");
    23a0:	50                   	push   %eax
    23a1:	50                   	push   %eax
    23a2:	68 88 48 00 00       	push   $0x4888
    23a7:	6a 01                	push   $0x1
    23a9:	e8 32 17 00 00       	call   3ae0 <printf>
    exit();
    23ae:	e8 d0 15 00 00       	call   3983 <exit>
    printf(1, "unlink dd/dd failed\n");
    23b3:	52                   	push   %edx
    23b4:	52                   	push   %edx
    23b5:	68 73 48 00 00       	push   $0x4873
    23ba:	6a 01                	push   $0x1
    23bc:	e8 1f 17 00 00       	call   3ae0 <printf>
    exit();
    23c1:	e8 bd 15 00 00       	call   3983 <exit>
    printf(1, "unlink non-empty dd succeeded!\n");
    23c6:	51                   	push   %ecx
    23c7:	51                   	push   %ecx
    23c8:	68 88 51 00 00       	push   $0x5188
    23cd:	6a 01                	push   $0x1
    23cf:	e8 0c 17 00 00       	call   3ae0 <printf>
    exit();
    23d4:	e8 aa 15 00 00       	call   3983 <exit>
    printf(1, "unlink dd/ff failed\n");
    23d9:	53                   	push   %ebx
    23da:	53                   	push   %ebx
    23db:	68 5e 48 00 00       	push   $0x485e
    23e0:	6a 01                	push   $0x1
    23e2:	e8 f9 16 00 00       	call   3ae0 <printf>
    exit();
    23e7:	e8 97 15 00 00       	call   3983 <exit>
    printf(1, "chdir dd/xx succeeded!\n");
    23ec:	50                   	push   %eax
    23ed:	50                   	push   %eax
    23ee:	68 46 48 00 00       	push   $0x4846
    23f3:	6a 01                	push   $0x1
    23f5:	e8 e6 16 00 00       	call   3ae0 <printf>
    exit();
    23fa:	e8 84 15 00 00       	call   3983 <exit>
    printf(1, "chdir dd/ff succeeded!\n");
    23ff:	50                   	push   %eax
    2400:	50                   	push   %eax
    2401:	68 2e 48 00 00       	push   $0x482e
    2406:	6a 01                	push   $0x1
    2408:	e8 d3 16 00 00       	call   3ae0 <printf>
    exit();
    240d:	e8 71 15 00 00       	call   3983 <exit>
    printf(1, "unlink dd/ff/ff succeeded!\n");
    2412:	50                   	push   %eax
    2413:	50                   	push   %eax
    2414:	68 12 48 00 00       	push   $0x4812
    2419:	6a 01                	push   $0x1
    241b:	e8 c0 16 00 00       	call   3ae0 <printf>
    exit();
    2420:	e8 5e 15 00 00       	call   3983 <exit>
    printf(1, "unlink dd/xx/ff succeeded!\n");
    2425:	50                   	push   %eax
    2426:	50                   	push   %eax
    2427:	68 f6 47 00 00       	push   $0x47f6
    242c:	6a 01                	push   $0x1
    242e:	e8 ad 16 00 00       	call   3ae0 <printf>
    exit();
    2433:	e8 4b 15 00 00       	call   3983 <exit>
    printf(1, "mkdir dd/dd/ffff succeeded!\n");
    2438:	50                   	push   %eax
    2439:	50                   	push   %eax
    243a:	68 d9 47 00 00       	push   $0x47d9
    243f:	6a 01                	push   $0x1
    2441:	e8 9a 16 00 00       	call   3ae0 <printf>
    exit();
    2446:	e8 38 15 00 00       	call   3983 <exit>
    printf(1, "mkdir dd/xx/ff succeeded!\n");
    244b:	52                   	push   %edx
    244c:	52                   	push   %edx
    244d:	68 be 47 00 00       	push   $0x47be
    2452:	6a 01                	push   $0x1
    2454:	e8 87 16 00 00       	call   3ae0 <printf>
    exit();
    2459:	e8 25 15 00 00       	call   3983 <exit>
    printf(1, "read dd/dd/ffff wrong len\n");
    245e:	51                   	push   %ecx
    245f:	51                   	push   %ecx
    2460:	68 eb 46 00 00       	push   $0x46eb
    2465:	6a 01                	push   $0x1
    2467:	e8 74 16 00 00       	call   3ae0 <printf>
    exit();
    246c:	e8 12 15 00 00       	call   3983 <exit>
    printf(1, "open dd/dd/ffff failed\n");
    2471:	53                   	push   %ebx
    2472:	53                   	push   %ebx
    2473:	68 d3 46 00 00       	push   $0x46d3
    2478:	6a 01                	push   $0x1
    247a:	e8 61 16 00 00       	call   3ae0 <printf>
    exit();
    247f:	e8 ff 14 00 00       	call   3983 <exit>
    2484:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    248b:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
    248f:	90                   	nop

00002490 <bigwrite>:
{
    2490:	f3 0f 1e fb          	endbr32 
    2494:	55                   	push   %ebp
    2495:	89 e5                	mov    %esp,%ebp
    2497:	56                   	push   %esi
    2498:	53                   	push   %ebx
  for(sz = 499; sz < 12*512; sz += 471){
    2499:	bb f3 01 00 00       	mov    $0x1f3,%ebx
  printf(1, "bigwrite test\n");
    249e:	83 ec 08             	sub    $0x8,%esp
    24a1:	68 a5 48 00 00       	push   $0x48a5
    24a6:	6a 01                	push   $0x1
    24a8:	e8 33 16 00 00       	call   3ae0 <printf>
  unlink("bigwrite");
    24ad:	c7 04 24 b4 48 00 00 	movl   $0x48b4,(%esp)
    24b4:	e8 1a 15 00 00       	call   39d3 <unlink>
    24b9:	83 c4 10             	add    $0x10,%esp
    24bc:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
    fd = open("bigwrite", O_CREATE | O_RDWR);
    24c0:	83 ec 08             	sub    $0x8,%esp
    24c3:	68 02 02 00 00       	push   $0x202
    24c8:	68 b4 48 00 00       	push   $0x48b4
    24cd:	e8 f1 14 00 00       	call   39c3 <open>
    if(fd < 0){
    24d2:	83 c4 10             	add    $0x10,%esp
    fd = open("bigwrite", O_CREATE | O_RDWR);
    24d5:	89 c6                	mov    %eax,%esi
    if(fd < 0){
    24d7:	85 c0                	test   %eax,%eax
    24d9:	78 7e                	js     2559 <bigwrite+0xc9>
      int cc = write(fd, buf, sz);
    24db:	83 ec 04             	sub    $0x4,%esp
    24de:	53                   	push   %ebx
    24df:	68 c0 86 00 00       	push   $0x86c0
    24e4:	50                   	push   %eax
    24e5:	e8 b9 14 00 00       	call   39a3 <write>
      if(cc != sz){
    24ea:	83 c4 10             	add    $0x10,%esp
    24ed:	39 d8                	cmp    %ebx,%eax
    24ef:	75 55                	jne    2546 <bigwrite+0xb6>
      int cc = write(fd, buf, sz);
    24f1:	83 ec 04             	sub    $0x4,%esp
    24f4:	53                   	push   %ebx
    24f5:	68 c0 86 00 00       	push   $0x86c0
    24fa:	56                   	push   %esi
    24fb:	e8 a3 14 00 00       	call   39a3 <write>
      if(cc != sz){
    2500:	83 c4 10             	add    $0x10,%esp
    2503:	39 d8                	cmp    %ebx,%eax
    2505:	75 3f                	jne    2546 <bigwrite+0xb6>
    close(fd);
    2507:	83 ec 0c             	sub    $0xc,%esp
  for(sz = 499; sz < 12*512; sz += 471){
    250a:	81 c3 d7 01 00 00    	add    $0x1d7,%ebx
    close(fd);
    2510:	56                   	push   %esi
    2511:	e8 95 14 00 00       	call   39ab <close>
    unlink("bigwrite");
    2516:	c7 04 24 b4 48 00 00 	movl   $0x48b4,(%esp)
    251d:	e8 b1 14 00 00       	call   39d3 <unlink>
  for(sz = 499; sz < 12*512; sz += 471){
    2522:	83 c4 10             	add    $0x10,%esp
    2525:	81 fb 07 18 00 00    	cmp    $0x1807,%ebx
    252b:	75 93                	jne    24c0 <bigwrite+0x30>
  printf(1, "bigwrite ok\n");
    252d:	83 ec 08             	sub    $0x8,%esp
    2530:	68 e7 48 00 00       	push   $0x48e7
    2535:	6a 01                	push   $0x1
    2537:	e8 a4 15 00 00       	call   3ae0 <printf>
}
    253c:	83 c4 10             	add    $0x10,%esp
    253f:	8d 65 f8             	lea    -0x8(%ebp),%esp
    2542:	5b                   	pop    %ebx
    2543:	5e                   	pop    %esi
    2544:	5d                   	pop    %ebp
    2545:	c3                   	ret    
        printf(1, "write(%d) ret %d\n", sz, cc);
    2546:	50                   	push   %eax
    2547:	53                   	push   %ebx
    2548:	68 d5 48 00 00       	push   $0x48d5
    254d:	6a 01                	push   $0x1
    254f:	e8 8c 15 00 00       	call   3ae0 <printf>
        exit();
    2554:	e8 2a 14 00 00       	call   3983 <exit>
      printf(1, "cannot create bigwrite\n");
    2559:	83 ec 08             	sub    $0x8,%esp
    255c:	68 bd 48 00 00       	push   $0x48bd
    2561:	6a 01                	push   $0x1
    2563:	e8 78 15 00 00       	call   3ae0 <printf>
      exit();
    2568:	e8 16 14 00 00       	call   3983 <exit>
    256d:	8d 76 00             	lea    0x0(%esi),%esi

00002570 <bigfile>:
{
    2570:	f3 0f 1e fb          	endbr32 
    2574:	55                   	push   %ebp
    2575:	89 e5                	mov    %esp,%ebp
    2577:	57                   	push   %edi
    2578:	56                   	push   %esi
    2579:	53                   	push   %ebx
    257a:	83 ec 14             	sub    $0x14,%esp
  printf(1, "bigfile test\n");
    257d:	68 f4 48 00 00       	push   $0x48f4
    2582:	6a 01                	push   $0x1
    2584:	e8 57 15 00 00       	call   3ae0 <printf>
  unlink("bigfile");
    2589:	c7 04 24 10 49 00 00 	movl   $0x4910,(%esp)
    2590:	e8 3e 14 00 00       	call   39d3 <unlink>
  fd = open("bigfile", O_CREATE | O_RDWR);
    2595:	58                   	pop    %eax
    2596:	5a                   	pop    %edx
    2597:	68 02 02 00 00       	push   $0x202
    259c:	68 10 49 00 00       	push   $0x4910
    25a1:	e8 1d 14 00 00       	call   39c3 <open>
  if(fd < 0){
    25a6:	83 c4 10             	add    $0x10,%esp
    25a9:	85 c0                	test   %eax,%eax
    25ab:	0f 88 5a 01 00 00    	js     270b <bigfile+0x19b>
    25b1:	89 c6                	mov    %eax,%esi
  for(i = 0; i < 20; i++){
    25b3:	31 db                	xor    %ebx,%ebx
    25b5:	8d 76 00             	lea    0x0(%esi),%esi
    memset(buf, i, 600);
    25b8:	83 ec 04             	sub    $0x4,%esp
    25bb:	68 58 02 00 00       	push   $0x258
    25c0:	53                   	push   %ebx
    25c1:	68 c0 86 00 00       	push   $0x86c0
    25c6:	e8 15 12 00 00       	call   37e0 <memset>
    if(write(fd, buf, 600) != 600){
    25cb:	83 c4 0c             	add    $0xc,%esp
    25ce:	68 58 02 00 00       	push   $0x258
    25d3:	68 c0 86 00 00       	push   $0x86c0
    25d8:	56                   	push   %esi
    25d9:	e8 c5 13 00 00       	call   39a3 <write>
    25de:	83 c4 10             	add    $0x10,%esp
    25e1:	3d 58 02 00 00       	cmp    $0x258,%eax
    25e6:	0f 85 f8 00 00 00    	jne    26e4 <bigfile+0x174>
  for(i = 0; i < 20; i++){
    25ec:	83 c3 01             	add    $0x1,%ebx
    25ef:	83 fb 14             	cmp    $0x14,%ebx
    25f2:	75 c4                	jne    25b8 <bigfile+0x48>
  close(fd);
    25f4:	83 ec 0c             	sub    $0xc,%esp
    25f7:	56                   	push   %esi
    25f8:	e8 ae 13 00 00       	call   39ab <close>
  fd = open("bigfile", 0);
    25fd:	5e                   	pop    %esi
    25fe:	5f                   	pop    %edi
    25ff:	6a 00                	push   $0x0
    2601:	68 10 49 00 00       	push   $0x4910
    2606:	e8 b8 13 00 00       	call   39c3 <open>
  if(fd < 0){
    260b:	83 c4 10             	add    $0x10,%esp
  fd = open("bigfile", 0);
    260e:	89 c6                	mov    %eax,%esi
  if(fd < 0){
    2610:	85 c0                	test   %eax,%eax
    2612:	0f 88 e0 00 00 00    	js     26f8 <bigfile+0x188>
  total = 0;
    2618:	31 db                	xor    %ebx,%ebx
  for(i = 0; ; i++){
    261a:	31 ff                	xor    %edi,%edi
    261c:	eb 30                	jmp    264e <bigfile+0xde>
    261e:	66 90                	xchg   %ax,%ax
    if(cc != 300){
    2620:	3d 2c 01 00 00       	cmp    $0x12c,%eax
    2625:	0f 85 91 00 00 00    	jne    26bc <bigfile+0x14c>
    if(buf[0] != i/2 || buf[299] != i/2){
    262b:	89 fa                	mov    %edi,%edx
    262d:	0f be 05 c0 86 00 00 	movsbl 0x86c0,%eax
    2634:	d1 fa                	sar    %edx
    2636:	39 d0                	cmp    %edx,%eax
    2638:	75 6e                	jne    26a8 <bigfile+0x138>
    263a:	0f be 15 eb 87 00 00 	movsbl 0x87eb,%edx
    2641:	39 d0                	cmp    %edx,%eax
    2643:	75 63                	jne    26a8 <bigfile+0x138>
    total += cc;
    2645:	81 c3 2c 01 00 00    	add    $0x12c,%ebx
  for(i = 0; ; i++){
    264b:	83 c7 01             	add    $0x1,%edi
    cc = read(fd, buf, 300);
    264e:	83 ec 04             	sub    $0x4,%esp
    2651:	68 2c 01 00 00       	push   $0x12c
    2656:	68 c0 86 00 00       	push   $0x86c0
    265b:	56                   	push   %esi
    265c:	e8 3a 13 00 00       	call   399b <read>
    if(cc < 0){
    2661:	83 c4 10             	add    $0x10,%esp
    2664:	85 c0                	test   %eax,%eax
    2666:	78 68                	js     26d0 <bigfile+0x160>
    if(cc == 0)
    2668:	75 b6                	jne    2620 <bigfile+0xb0>
  close(fd);
    266a:	83 ec 0c             	sub    $0xc,%esp
    266d:	56                   	push   %esi
    266e:	e8 38 13 00 00       	call   39ab <close>
  if(total != 20*600){
    2673:	83 c4 10             	add    $0x10,%esp
    2676:	81 fb e0 2e 00 00    	cmp    $0x2ee0,%ebx
    267c:	0f 85 9c 00 00 00    	jne    271e <bigfile+0x1ae>
  unlink("bigfile");
    2682:	83 ec 0c             	sub    $0xc,%esp
    2685:	68 10 49 00 00       	push   $0x4910
    268a:	e8 44 13 00 00       	call   39d3 <unlink>
  printf(1, "bigfile test ok\n");
    268f:	58                   	pop    %eax
    2690:	5a                   	pop    %edx
    2691:	68 9f 49 00 00       	push   $0x499f
    2696:	6a 01                	push   $0x1
    2698:	e8 43 14 00 00       	call   3ae0 <printf>
}
    269d:	83 c4 10             	add    $0x10,%esp
    26a0:	8d 65 f4             	lea    -0xc(%ebp),%esp
    26a3:	5b                   	pop    %ebx
    26a4:	5e                   	pop    %esi
    26a5:	5f                   	pop    %edi
    26a6:	5d                   	pop    %ebp
    26a7:	c3                   	ret    
      printf(1, "read bigfile wrong data\n");
    26a8:	83 ec 08             	sub    $0x8,%esp
    26ab:	68 6c 49 00 00       	push   $0x496c
    26b0:	6a 01                	push   $0x1
    26b2:	e8 29 14 00 00       	call   3ae0 <printf>
      exit();
    26b7:	e8 c7 12 00 00       	call   3983 <exit>
      printf(1, "short read bigfile\n");
    26bc:	83 ec 08             	sub    $0x8,%esp
    26bf:	68 58 49 00 00       	push   $0x4958
    26c4:	6a 01                	push   $0x1
    26c6:	e8 15 14 00 00       	call   3ae0 <printf>
      exit();
    26cb:	e8 b3 12 00 00       	call   3983 <exit>
      printf(1, "read bigfile failed\n");
    26d0:	83 ec 08             	sub    $0x8,%esp
    26d3:	68 43 49 00 00       	push   $0x4943
    26d8:	6a 01                	push   $0x1
    26da:	e8 01 14 00 00       	call   3ae0 <printf>
      exit();
    26df:	e8 9f 12 00 00       	call   3983 <exit>
      printf(1, "write bigfile failed\n");
    26e4:	83 ec 08             	sub    $0x8,%esp
    26e7:	68 18 49 00 00       	push   $0x4918
    26ec:	6a 01                	push   $0x1
    26ee:	e8 ed 13 00 00       	call   3ae0 <printf>
      exit();
    26f3:	e8 8b 12 00 00       	call   3983 <exit>
    printf(1, "cannot open bigfile\n");
    26f8:	53                   	push   %ebx
    26f9:	53                   	push   %ebx
    26fa:	68 2e 49 00 00       	push   $0x492e
    26ff:	6a 01                	push   $0x1
    2701:	e8 da 13 00 00       	call   3ae0 <printf>
    exit();
    2706:	e8 78 12 00 00       	call   3983 <exit>
    printf(1, "cannot create bigfile");
    270b:	50                   	push   %eax
    270c:	50                   	push   %eax
    270d:	68 02 49 00 00       	push   $0x4902
    2712:	6a 01                	push   $0x1
    2714:	e8 c7 13 00 00       	call   3ae0 <printf>
    exit();
    2719:	e8 65 12 00 00       	call   3983 <exit>
    printf(1, "read bigfile wrong total\n");
    271e:	51                   	push   %ecx
    271f:	51                   	push   %ecx
    2720:	68 85 49 00 00       	push   $0x4985
    2725:	6a 01                	push   $0x1
    2727:	e8 b4 13 00 00       	call   3ae0 <printf>
    exit();
    272c:	e8 52 12 00 00       	call   3983 <exit>
    2731:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    2738:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    273f:	90                   	nop

00002740 <fourteen>:
{
    2740:	f3 0f 1e fb          	endbr32 
    2744:	55                   	push   %ebp
    2745:	89 e5                	mov    %esp,%ebp
    2747:	83 ec 10             	sub    $0x10,%esp
  printf(1, "fourteen test\n");
    274a:	68 b0 49 00 00       	push   $0x49b0
    274f:	6a 01                	push   $0x1
    2751:	e8 8a 13 00 00       	call   3ae0 <printf>
  if(mkdir("12345678901234") != 0){
    2756:	c7 04 24 eb 49 00 00 	movl   $0x49eb,(%esp)
    275d:	e8 89 12 00 00       	call   39eb <mkdir>
    2762:	83 c4 10             	add    $0x10,%esp
    2765:	85 c0                	test   %eax,%eax
    2767:	0f 85 97 00 00 00    	jne    2804 <fourteen+0xc4>
  if(mkdir("12345678901234/123456789012345") != 0){
    276d:	83 ec 0c             	sub    $0xc,%esp
    2770:	68 a8 51 00 00       	push   $0x51a8
    2775:	e8 71 12 00 00       	call   39eb <mkdir>
    277a:	83 c4 10             	add    $0x10,%esp
    277d:	85 c0                	test   %eax,%eax
    277f:	0f 85 de 00 00 00    	jne    2863 <fourteen+0x123>
  fd = open("123456789012345/123456789012345/123456789012345", O_CREATE);
    2785:	83 ec 08             	sub    $0x8,%esp
    2788:	68 00 02 00 00       	push   $0x200
    278d:	68 f8 51 00 00       	push   $0x51f8
    2792:	e8 2c 12 00 00       	call   39c3 <open>
  if(fd < 0){
    2797:	83 c4 10             	add    $0x10,%esp
    279a:	85 c0                	test   %eax,%eax
    279c:	0f 88 ae 00 00 00    	js     2850 <fourteen+0x110>
  close(fd);
    27a2:	83 ec 0c             	sub    $0xc,%esp
    27a5:	50                   	push   %eax
    27a6:	e8 00 12 00 00       	call   39ab <close>
  fd = open("12345678901234/12345678901234/12345678901234", 0);
    27ab:	58                   	pop    %eax
    27ac:	5a                   	pop    %edx
    27ad:	6a 00                	push   $0x0
    27af:	68 68 52 00 00       	push   $0x5268
    27b4:	e8 0a 12 00 00       	call   39c3 <open>
  if(fd < 0){
    27b9:	83 c4 10             	add    $0x10,%esp
    27bc:	85 c0                	test   %eax,%eax
    27be:	78 7d                	js     283d <fourteen+0xfd>
  close(fd);
    27c0:	83 ec 0c             	sub    $0xc,%esp
    27c3:	50                   	push   %eax
    27c4:	e8 e2 11 00 00       	call   39ab <close>
  if(mkdir("12345678901234/12345678901234") == 0){
    27c9:	c7 04 24 dc 49 00 00 	movl   $0x49dc,(%esp)
    27d0:	e8 16 12 00 00       	call   39eb <mkdir>
    27d5:	83 c4 10             	add    $0x10,%esp
    27d8:	85 c0                	test   %eax,%eax
    27da:	74 4e                	je     282a <fourteen+0xea>
  if(mkdir("123456789012345/12345678901234") == 0){
    27dc:	83 ec 0c             	sub    $0xc,%esp
    27df:	68 04 53 00 00       	push   $0x5304
    27e4:	e8 02 12 00 00       	call   39eb <mkdir>
    27e9:	83 c4 10             	add    $0x10,%esp
    27ec:	85 c0                	test   %eax,%eax
    27ee:	74 27                	je     2817 <fourteen+0xd7>
  printf(1, "fourteen ok\n");
    27f0:	83 ec 08             	sub    $0x8,%esp
    27f3:	68 fa 49 00 00       	push   $0x49fa
    27f8:	6a 01                	push   $0x1
    27fa:	e8 e1 12 00 00       	call   3ae0 <printf>
}
    27ff:	83 c4 10             	add    $0x10,%esp
    2802:	c9                   	leave  
    2803:	c3                   	ret    
    printf(1, "mkdir 12345678901234 failed\n");
    2804:	50                   	push   %eax
    2805:	50                   	push   %eax
    2806:	68 bf 49 00 00       	push   $0x49bf
    280b:	6a 01                	push   $0x1
    280d:	e8 ce 12 00 00       	call   3ae0 <printf>
    exit();
    2812:	e8 6c 11 00 00       	call   3983 <exit>
    printf(1, "mkdir 12345678901234/123456789012345 succeeded!\n");
    2817:	50                   	push   %eax
    2818:	50                   	push   %eax
    2819:	68 24 53 00 00       	push   $0x5324
    281e:	6a 01                	push   $0x1
    2820:	e8 bb 12 00 00       	call   3ae0 <printf>
    exit();
    2825:	e8 59 11 00 00       	call   3983 <exit>
    printf(1, "mkdir 12345678901234/12345678901234 succeeded!\n");
    282a:	52                   	push   %edx
    282b:	52                   	push   %edx
    282c:	68 d4 52 00 00       	push   $0x52d4
    2831:	6a 01                	push   $0x1
    2833:	e8 a8 12 00 00       	call   3ae0 <printf>
    exit();
    2838:	e8 46 11 00 00       	call   3983 <exit>
    printf(1, "open 12345678901234/12345678901234/12345678901234 failed\n");
    283d:	51                   	push   %ecx
    283e:	51                   	push   %ecx
    283f:	68 98 52 00 00       	push   $0x5298
    2844:	6a 01                	push   $0x1
    2846:	e8 95 12 00 00       	call   3ae0 <printf>
    exit();
    284b:	e8 33 11 00 00       	call   3983 <exit>
    printf(1, "create 123456789012345/123456789012345/123456789012345 failed\n");
    2850:	51                   	push   %ecx
    2851:	51                   	push   %ecx
    2852:	68 28 52 00 00       	push   $0x5228
    2857:	6a 01                	push   $0x1
    2859:	e8 82 12 00 00       	call   3ae0 <printf>
    exit();
    285e:	e8 20 11 00 00       	call   3983 <exit>
    printf(1, "mkdir 12345678901234/123456789012345 failed\n");
    2863:	50                   	push   %eax
    2864:	50                   	push   %eax
    2865:	68 c8 51 00 00       	push   $0x51c8
    286a:	6a 01                	push   $0x1
    286c:	e8 6f 12 00 00       	call   3ae0 <printf>
    exit();
    2871:	e8 0d 11 00 00       	call   3983 <exit>
    2876:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    287d:	8d 76 00             	lea    0x0(%esi),%esi

00002880 <rmdot>:
{
    2880:	f3 0f 1e fb          	endbr32 
    2884:	55                   	push   %ebp
    2885:	89 e5                	mov    %esp,%ebp
    2887:	83 ec 10             	sub    $0x10,%esp
  printf(1, "rmdot test\n");
    288a:	68 07 4a 00 00       	push   $0x4a07
    288f:	6a 01                	push   $0x1
    2891:	e8 4a 12 00 00       	call   3ae0 <printf>
  if(mkdir("dots") != 0){
    2896:	c7 04 24 13 4a 00 00 	movl   $0x4a13,(%esp)
    289d:	e8 49 11 00 00       	call   39eb <mkdir>
    28a2:	83 c4 10             	add    $0x10,%esp
    28a5:	85 c0                	test   %eax,%eax
    28a7:	0f 85 b0 00 00 00    	jne    295d <rmdot+0xdd>
  if(chdir("dots") != 0){
    28ad:	83 ec 0c             	sub    $0xc,%esp
    28b0:	68 13 4a 00 00       	push   $0x4a13
    28b5:	e8 39 11 00 00       	call   39f3 <chdir>
    28ba:	83 c4 10             	add    $0x10,%esp
    28bd:	85 c0                	test   %eax,%eax
    28bf:	0f 85 1d 01 00 00    	jne    29e2 <rmdot+0x162>
  if(unlink(".") == 0){
    28c5:	83 ec 0c             	sub    $0xc,%esp
    28c8:	68 be 46 00 00       	push   $0x46be
    28cd:	e8 01 11 00 00       	call   39d3 <unlink>
    28d2:	83 c4 10             	add    $0x10,%esp
    28d5:	85 c0                	test   %eax,%eax
    28d7:	0f 84 f2 00 00 00    	je     29cf <rmdot+0x14f>
  if(unlink("..") == 0){
    28dd:	83 ec 0c             	sub    $0xc,%esp
    28e0:	68 bd 46 00 00       	push   $0x46bd
    28e5:	e8 e9 10 00 00       	call   39d3 <unlink>
    28ea:	83 c4 10             	add    $0x10,%esp
    28ed:	85 c0                	test   %eax,%eax
    28ef:	0f 84 c7 00 00 00    	je     29bc <rmdot+0x13c>
  if(chdir("/") != 0){
    28f5:	83 ec 0c             	sub    $0xc,%esp
    28f8:	68 91 3e 00 00       	push   $0x3e91
    28fd:	e8 f1 10 00 00       	call   39f3 <chdir>
    2902:	83 c4 10             	add    $0x10,%esp
    2905:	85 c0                	test   %eax,%eax
    2907:	0f 85 9c 00 00 00    	jne    29a9 <rmdot+0x129>
  if(unlink("dots/.") == 0){
    290d:	83 ec 0c             	sub    $0xc,%esp
    2910:	68 5b 4a 00 00       	push   $0x4a5b
    2915:	e8 b9 10 00 00       	call   39d3 <unlink>
    291a:	83 c4 10             	add    $0x10,%esp
    291d:	85 c0                	test   %eax,%eax
    291f:	74 75                	je     2996 <rmdot+0x116>
  if(unlink("dots/..") == 0){
    2921:	83 ec 0c             	sub    $0xc,%esp
    2924:	68 79 4a 00 00       	push   $0x4a79
    2929:	e8 a5 10 00 00       	call   39d3 <unlink>
    292e:	83 c4 10             	add    $0x10,%esp
    2931:	85 c0                	test   %eax,%eax
    2933:	74 4e                	je     2983 <rmdot+0x103>
  if(unlink("dots") != 0){
    2935:	83 ec 0c             	sub    $0xc,%esp
    2938:	68 13 4a 00 00       	push   $0x4a13
    293d:	e8 91 10 00 00       	call   39d3 <unlink>
    2942:	83 c4 10             	add    $0x10,%esp
    2945:	85 c0                	test   %eax,%eax
    2947:	75 27                	jne    2970 <rmdot+0xf0>
  printf(1, "rmdot ok\n");
    2949:	83 ec 08             	sub    $0x8,%esp
    294c:	68 ae 4a 00 00       	push   $0x4aae
    2951:	6a 01                	push   $0x1
    2953:	e8 88 11 00 00       	call   3ae0 <printf>
}
    2958:	83 c4 10             	add    $0x10,%esp
    295b:	c9                   	leave  
    295c:	c3                   	ret    
    printf(1, "mkdir dots failed\n");
    295d:	50                   	push   %eax
    295e:	50                   	push   %eax
    295f:	68 18 4a 00 00       	push   $0x4a18
    2964:	6a 01                	push   $0x1
    2966:	e8 75 11 00 00       	call   3ae0 <printf>
    exit();
    296b:	e8 13 10 00 00       	call   3983 <exit>
    printf(1, "unlink dots failed!\n");
    2970:	50                   	push   %eax
    2971:	50                   	push   %eax
    2972:	68 99 4a 00 00       	push   $0x4a99
    2977:	6a 01                	push   $0x1
    2979:	e8 62 11 00 00       	call   3ae0 <printf>
    exit();
    297e:	e8 00 10 00 00       	call   3983 <exit>
    printf(1, "unlink dots/.. worked!\n");
    2983:	52                   	push   %edx
    2984:	52                   	push   %edx
    2985:	68 81 4a 00 00       	push   $0x4a81
    298a:	6a 01                	push   $0x1
    298c:	e8 4f 11 00 00       	call   3ae0 <printf>
    exit();
    2991:	e8 ed 0f 00 00       	call   3983 <exit>
    printf(1, "unlink dots/. worked!\n");
    2996:	51                   	push   %ecx
    2997:	51                   	push   %ecx
    2998:	68 62 4a 00 00       	push   $0x4a62
    299d:	6a 01                	push   $0x1
    299f:	e8 3c 11 00 00       	call   3ae0 <printf>
    exit();
    29a4:	e8 da 0f 00 00       	call   3983 <exit>
    printf(1, "chdir / failed\n");
    29a9:	50                   	push   %eax
    29aa:	50                   	push   %eax
    29ab:	68 93 3e 00 00       	push   $0x3e93
    29b0:	6a 01                	push   $0x1
    29b2:	e8 29 11 00 00       	call   3ae0 <printf>
    exit();
    29b7:	e8 c7 0f 00 00       	call   3983 <exit>
    printf(1, "rm .. worked!\n");
    29bc:	50                   	push   %eax
    29bd:	50                   	push   %eax
    29be:	68 4c 4a 00 00       	push   $0x4a4c
    29c3:	6a 01                	push   $0x1
    29c5:	e8 16 11 00 00       	call   3ae0 <printf>
    exit();
    29ca:	e8 b4 0f 00 00       	call   3983 <exit>
    printf(1, "rm . worked!\n");
    29cf:	50                   	push   %eax
    29d0:	50                   	push   %eax
    29d1:	68 3e 4a 00 00       	push   $0x4a3e
    29d6:	6a 01                	push   $0x1
    29d8:	e8 03 11 00 00       	call   3ae0 <printf>
    exit();
    29dd:	e8 a1 0f 00 00       	call   3983 <exit>
    printf(1, "chdir dots failed\n");
    29e2:	50                   	push   %eax
    29e3:	50                   	push   %eax
    29e4:	68 2b 4a 00 00       	push   $0x4a2b
    29e9:	6a 01                	push   $0x1
    29eb:	e8 f0 10 00 00       	call   3ae0 <printf>
    exit();
    29f0:	e8 8e 0f 00 00       	call   3983 <exit>
    29f5:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    29fc:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi

00002a00 <dirfile>:
{
    2a00:	f3 0f 1e fb          	endbr32 
    2a04:	55                   	push   %ebp
    2a05:	89 e5                	mov    %esp,%ebp
    2a07:	53                   	push   %ebx
    2a08:	83 ec 0c             	sub    $0xc,%esp
  printf(1, "dir vs file\n");
    2a0b:	68 b8 4a 00 00       	push   $0x4ab8
    2a10:	6a 01                	push   $0x1
    2a12:	e8 c9 10 00 00       	call   3ae0 <printf>
  fd = open("dirfile", O_CREATE);
    2a17:	5b                   	pop    %ebx
    2a18:	58                   	pop    %eax
    2a19:	68 00 02 00 00       	push   $0x200
    2a1e:	68 c5 4a 00 00       	push   $0x4ac5
    2a23:	e8 9b 0f 00 00       	call   39c3 <open>
  if(fd < 0){
    2a28:	83 c4 10             	add    $0x10,%esp
    2a2b:	85 c0                	test   %eax,%eax
    2a2d:	0f 88 43 01 00 00    	js     2b76 <dirfile+0x176>
  close(fd);
    2a33:	83 ec 0c             	sub    $0xc,%esp
    2a36:	50                   	push   %eax
    2a37:	e8 6f 0f 00 00       	call   39ab <close>
  if(chdir("dirfile") == 0){
    2a3c:	c7 04 24 c5 4a 00 00 	movl   $0x4ac5,(%esp)
    2a43:	e8 ab 0f 00 00       	call   39f3 <chdir>
    2a48:	83 c4 10             	add    $0x10,%esp
    2a4b:	85 c0                	test   %eax,%eax
    2a4d:	0f 84 10 01 00 00    	je     2b63 <dirfile+0x163>
  fd = open("dirfile/xx", 0);
    2a53:	83 ec 08             	sub    $0x8,%esp
    2a56:	6a 00                	push   $0x0
    2a58:	68 fe 4a 00 00       	push   $0x4afe
    2a5d:	e8 61 0f 00 00       	call   39c3 <open>
  if(fd >= 0){
    2a62:	83 c4 10             	add    $0x10,%esp
    2a65:	85 c0                	test   %eax,%eax
    2a67:	0f 89 e3 00 00 00    	jns    2b50 <dirfile+0x150>
  fd = open("dirfile/xx", O_CREATE);
    2a6d:	83 ec 08             	sub    $0x8,%esp
    2a70:	68 00 02 00 00       	push   $0x200
    2a75:	68 fe 4a 00 00       	push   $0x4afe
    2a7a:	e8 44 0f 00 00       	call   39c3 <open>
  if(fd >= 0){
    2a7f:	83 c4 10             	add    $0x10,%esp
    2a82:	85 c0                	test   %eax,%eax
    2a84:	0f 89 c6 00 00 00    	jns    2b50 <dirfile+0x150>
  if(mkdir("dirfile/xx") == 0){
    2a8a:	83 ec 0c             	sub    $0xc,%esp
    2a8d:	68 fe 4a 00 00       	push   $0x4afe
    2a92:	e8 54 0f 00 00       	call   39eb <mkdir>
    2a97:	83 c4 10             	add    $0x10,%esp
    2a9a:	85 c0                	test   %eax,%eax
    2a9c:	0f 84 46 01 00 00    	je     2be8 <dirfile+0x1e8>
  if(unlink("dirfile/xx") == 0){
    2aa2:	83 ec 0c             	sub    $0xc,%esp
    2aa5:	68 fe 4a 00 00       	push   $0x4afe
    2aaa:	e8 24 0f 00 00       	call   39d3 <unlink>
    2aaf:	83 c4 10             	add    $0x10,%esp
    2ab2:	85 c0                	test   %eax,%eax
    2ab4:	0f 84 1b 01 00 00    	je     2bd5 <dirfile+0x1d5>
  if(link("README", "dirfile/xx") == 0){
    2aba:	83 ec 08             	sub    $0x8,%esp
    2abd:	68 fe 4a 00 00       	push   $0x4afe
    2ac2:	68 62 4b 00 00       	push   $0x4b62
    2ac7:	e8 17 0f 00 00       	call   39e3 <link>
    2acc:	83 c4 10             	add    $0x10,%esp
    2acf:	85 c0                	test   %eax,%eax
    2ad1:	0f 84 eb 00 00 00    	je     2bc2 <dirfile+0x1c2>
  if(unlink("dirfile") != 0){
    2ad7:	83 ec 0c             	sub    $0xc,%esp
    2ada:	68 c5 4a 00 00       	push   $0x4ac5
    2adf:	e8 ef 0e 00 00       	call   39d3 <unlink>
    2ae4:	83 c4 10             	add    $0x10,%esp
    2ae7:	85 c0                	test   %eax,%eax
    2ae9:	0f 85 c0 00 00 00    	jne    2baf <dirfile+0x1af>
  fd = open(".", O_RDWR);
    2aef:	83 ec 08             	sub    $0x8,%esp
    2af2:	6a 02                	push   $0x2
    2af4:	68 be 46 00 00       	push   $0x46be
    2af9:	e8 c5 0e 00 00       	call   39c3 <open>
  if(fd >= 0){
    2afe:	83 c4 10             	add    $0x10,%esp
    2b01:	85 c0                	test   %eax,%eax
    2b03:	0f 89 93 00 00 00    	jns    2b9c <dirfile+0x19c>
  fd = open(".", 0);
    2b09:	83 ec 08             	sub    $0x8,%esp
    2b0c:	6a 00                	push   $0x0
    2b0e:	68 be 46 00 00       	push   $0x46be
    2b13:	e8 ab 0e 00 00       	call   39c3 <open>
  if(write(fd, "x", 1) > 0){
    2b18:	83 c4 0c             	add    $0xc,%esp
    2b1b:	6a 01                	push   $0x1
  fd = open(".", 0);
    2b1d:	89 c3                	mov    %eax,%ebx
  if(write(fd, "x", 1) > 0){
    2b1f:	68 a1 47 00 00       	push   $0x47a1
    2b24:	50                   	push   %eax
    2b25:	e8 79 0e 00 00       	call   39a3 <write>
    2b2a:	83 c4 10             	add    $0x10,%esp
    2b2d:	85 c0                	test   %eax,%eax
    2b2f:	7f 58                	jg     2b89 <dirfile+0x189>
  close(fd);
    2b31:	83 ec 0c             	sub    $0xc,%esp
    2b34:	53                   	push   %ebx
    2b35:	e8 71 0e 00 00       	call   39ab <close>
  printf(1, "dir vs file OK\n");
    2b3a:	58                   	pop    %eax
    2b3b:	5a                   	pop    %edx
    2b3c:	68 95 4b 00 00       	push   $0x4b95
    2b41:	6a 01                	push   $0x1
    2b43:	e8 98 0f 00 00       	call   3ae0 <printf>
}
    2b48:	8b 5d fc             	mov    -0x4(%ebp),%ebx
    2b4b:	83 c4 10             	add    $0x10,%esp
    2b4e:	c9                   	leave  
    2b4f:	c3                   	ret    
    printf(1, "create dirfile/xx succeeded!\n");
    2b50:	50                   	push   %eax
    2b51:	50                   	push   %eax
    2b52:	68 09 4b 00 00       	push   $0x4b09
    2b57:	6a 01                	push   $0x1
    2b59:	e8 82 0f 00 00       	call   3ae0 <printf>
    exit();
    2b5e:	e8 20 0e 00 00       	call   3983 <exit>
    printf(1, "chdir dirfile succeeded!\n");
    2b63:	52                   	push   %edx
    2b64:	52                   	push   %edx
    2b65:	68 e4 4a 00 00       	push   $0x4ae4
    2b6a:	6a 01                	push   $0x1
    2b6c:	e8 6f 0f 00 00       	call   3ae0 <printf>
    exit();
    2b71:	e8 0d 0e 00 00       	call   3983 <exit>
    printf(1, "create dirfile failed\n");
    2b76:	51                   	push   %ecx
    2b77:	51                   	push   %ecx
    2b78:	68 cd 4a 00 00       	push   $0x4acd
    2b7d:	6a 01                	push   $0x1
    2b7f:	e8 5c 0f 00 00       	call   3ae0 <printf>
    exit();
    2b84:	e8 fa 0d 00 00       	call   3983 <exit>
    printf(1, "write . succeeded!\n");
    2b89:	51                   	push   %ecx
    2b8a:	51                   	push   %ecx
    2b8b:	68 81 4b 00 00       	push   $0x4b81
    2b90:	6a 01                	push   $0x1
    2b92:	e8 49 0f 00 00       	call   3ae0 <printf>
    exit();
    2b97:	e8 e7 0d 00 00       	call   3983 <exit>
    printf(1, "open . for writing succeeded!\n");
    2b9c:	53                   	push   %ebx
    2b9d:	53                   	push   %ebx
    2b9e:	68 78 53 00 00       	push   $0x5378
    2ba3:	6a 01                	push   $0x1
    2ba5:	e8 36 0f 00 00       	call   3ae0 <printf>
    exit();
    2baa:	e8 d4 0d 00 00       	call   3983 <exit>
    printf(1, "unlink dirfile failed!\n");
    2baf:	50                   	push   %eax
    2bb0:	50                   	push   %eax
    2bb1:	68 69 4b 00 00       	push   $0x4b69
    2bb6:	6a 01                	push   $0x1
    2bb8:	e8 23 0f 00 00       	call   3ae0 <printf>
    exit();
    2bbd:	e8 c1 0d 00 00       	call   3983 <exit>
    printf(1, "link to dirfile/xx succeeded!\n");
    2bc2:	50                   	push   %eax
    2bc3:	50                   	push   %eax
    2bc4:	68 58 53 00 00       	push   $0x5358
    2bc9:	6a 01                	push   $0x1
    2bcb:	e8 10 0f 00 00       	call   3ae0 <printf>
    exit();
    2bd0:	e8 ae 0d 00 00       	call   3983 <exit>
    printf(1, "unlink dirfile/xx succeeded!\n");
    2bd5:	50                   	push   %eax
    2bd6:	50                   	push   %eax
    2bd7:	68 44 4b 00 00       	push   $0x4b44
    2bdc:	6a 01                	push   $0x1
    2bde:	e8 fd 0e 00 00       	call   3ae0 <printf>
    exit();
    2be3:	e8 9b 0d 00 00       	call   3983 <exit>
    printf(1, "mkdir dirfile/xx succeeded!\n");
    2be8:	50                   	push   %eax
    2be9:	50                   	push   %eax
    2bea:	68 27 4b 00 00       	push   $0x4b27
    2bef:	6a 01                	push   $0x1
    2bf1:	e8 ea 0e 00 00       	call   3ae0 <printf>
    exit();
    2bf6:	e8 88 0d 00 00       	call   3983 <exit>
    2bfb:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
    2bff:	90                   	nop

00002c00 <iref>:
{
    2c00:	f3 0f 1e fb          	endbr32 
    2c04:	55                   	push   %ebp
    2c05:	89 e5                	mov    %esp,%ebp
    2c07:	53                   	push   %ebx
  printf(1, "empty file name\n");
    2c08:	bb 33 00 00 00       	mov    $0x33,%ebx
{
    2c0d:	83 ec 0c             	sub    $0xc,%esp
  printf(1, "empty file name\n");
    2c10:	68 a5 4b 00 00       	push   $0x4ba5
    2c15:	6a 01                	push   $0x1
    2c17:	e8 c4 0e 00 00       	call   3ae0 <printf>
    2c1c:	83 c4 10             	add    $0x10,%esp
    2c1f:	90                   	nop
    if(mkdir("irefd") != 0){
    2c20:	83 ec 0c             	sub    $0xc,%esp
    2c23:	68 b6 4b 00 00       	push   $0x4bb6
    2c28:	e8 be 0d 00 00       	call   39eb <mkdir>
    2c2d:	83 c4 10             	add    $0x10,%esp
    2c30:	85 c0                	test   %eax,%eax
    2c32:	0f 85 bb 00 00 00    	jne    2cf3 <iref+0xf3>
    if(chdir("irefd") != 0){
    2c38:	83 ec 0c             	sub    $0xc,%esp
    2c3b:	68 b6 4b 00 00       	push   $0x4bb6
    2c40:	e8 ae 0d 00 00       	call   39f3 <chdir>
    2c45:	83 c4 10             	add    $0x10,%esp
    2c48:	85 c0                	test   %eax,%eax
    2c4a:	0f 85 b7 00 00 00    	jne    2d07 <iref+0x107>
    mkdir("");
    2c50:	83 ec 0c             	sub    $0xc,%esp
    2c53:	68 6b 42 00 00       	push   $0x426b
    2c58:	e8 8e 0d 00 00       	call   39eb <mkdir>
    link("README", "");
    2c5d:	59                   	pop    %ecx
    2c5e:	58                   	pop    %eax
    2c5f:	68 6b 42 00 00       	push   $0x426b
    2c64:	68 62 4b 00 00       	push   $0x4b62
    2c69:	e8 75 0d 00 00       	call   39e3 <link>
    fd = open("", O_CREATE);
    2c6e:	58                   	pop    %eax
    2c6f:	5a                   	pop    %edx
    2c70:	68 00 02 00 00       	push   $0x200
    2c75:	68 6b 42 00 00       	push   $0x426b
    2c7a:	e8 44 0d 00 00       	call   39c3 <open>
    if(fd >= 0)
    2c7f:	83 c4 10             	add    $0x10,%esp
    2c82:	85 c0                	test   %eax,%eax
    2c84:	78 0c                	js     2c92 <iref+0x92>
      close(fd);
    2c86:	83 ec 0c             	sub    $0xc,%esp
    2c89:	50                   	push   %eax
    2c8a:	e8 1c 0d 00 00       	call   39ab <close>
    2c8f:	83 c4 10             	add    $0x10,%esp
    fd = open("xx", O_CREATE);
    2c92:	83 ec 08             	sub    $0x8,%esp
    2c95:	68 00 02 00 00       	push   $0x200
    2c9a:	68 a0 47 00 00       	push   $0x47a0
    2c9f:	e8 1f 0d 00 00       	call   39c3 <open>
    if(fd >= 0)
    2ca4:	83 c4 10             	add    $0x10,%esp
    2ca7:	85 c0                	test   %eax,%eax
    2ca9:	78 0c                	js     2cb7 <iref+0xb7>
      close(fd);
    2cab:	83 ec 0c             	sub    $0xc,%esp
    2cae:	50                   	push   %eax
    2caf:	e8 f7 0c 00 00       	call   39ab <close>
    2cb4:	83 c4 10             	add    $0x10,%esp
    unlink("xx");
    2cb7:	83 ec 0c             	sub    $0xc,%esp
    2cba:	68 a0 47 00 00       	push   $0x47a0
    2cbf:	e8 0f 0d 00 00       	call   39d3 <unlink>
  for(i = 0; i < 50 + 1; i++){
    2cc4:	83 c4 10             	add    $0x10,%esp
    2cc7:	83 eb 01             	sub    $0x1,%ebx
    2cca:	0f 85 50 ff ff ff    	jne    2c20 <iref+0x20>
  chdir("/");
    2cd0:	83 ec 0c             	sub    $0xc,%esp
    2cd3:	68 91 3e 00 00       	push   $0x3e91
    2cd8:	e8 16 0d 00 00       	call   39f3 <chdir>
  printf(1, "empty file name OK\n");
    2cdd:	58                   	pop    %eax
    2cde:	5a                   	pop    %edx
    2cdf:	68 e4 4b 00 00       	push   $0x4be4
    2ce4:	6a 01                	push   $0x1
    2ce6:	e8 f5 0d 00 00       	call   3ae0 <printf>
}
    2ceb:	8b 5d fc             	mov    -0x4(%ebp),%ebx
    2cee:	83 c4 10             	add    $0x10,%esp
    2cf1:	c9                   	leave  
    2cf2:	c3                   	ret    
      printf(1, "mkdir irefd failed\n");
    2cf3:	83 ec 08             	sub    $0x8,%esp
    2cf6:	68 bc 4b 00 00       	push   $0x4bbc
    2cfb:	6a 01                	push   $0x1
    2cfd:	e8 de 0d 00 00       	call   3ae0 <printf>
      exit();
    2d02:	e8 7c 0c 00 00       	call   3983 <exit>
      printf(1, "chdir irefd failed\n");
    2d07:	83 ec 08             	sub    $0x8,%esp
    2d0a:	68 d0 4b 00 00       	push   $0x4bd0
    2d0f:	6a 01                	push   $0x1
    2d11:	e8 ca 0d 00 00       	call   3ae0 <printf>
      exit();
    2d16:	e8 68 0c 00 00       	call   3983 <exit>
    2d1b:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
    2d1f:	90                   	nop

00002d20 <forktest>:
{
    2d20:	f3 0f 1e fb          	endbr32 
    2d24:	55                   	push   %ebp
    2d25:	89 e5                	mov    %esp,%ebp
    2d27:	53                   	push   %ebx
  for(n=0; n<1000; n++){
    2d28:	31 db                	xor    %ebx,%ebx
{
    2d2a:	83 ec 0c             	sub    $0xc,%esp
  printf(1, "fork test\n");
    2d2d:	68 f8 4b 00 00       	push   $0x4bf8
    2d32:	6a 01                	push   $0x1
    2d34:	e8 a7 0d 00 00       	call   3ae0 <printf>
    2d39:	83 c4 10             	add    $0x10,%esp
    2d3c:	eb 0f                	jmp    2d4d <forktest+0x2d>
    2d3e:	66 90                	xchg   %ax,%ax
    if(pid == 0)
    2d40:	74 4a                	je     2d8c <forktest+0x6c>
  for(n=0; n<1000; n++){
    2d42:	83 c3 01             	add    $0x1,%ebx
    2d45:	81 fb e8 03 00 00    	cmp    $0x3e8,%ebx
    2d4b:	74 6b                	je     2db8 <forktest+0x98>
    pid = fork();
    2d4d:	e8 29 0c 00 00       	call   397b <fork>
    if(pid < 0)
    2d52:	85 c0                	test   %eax,%eax
    2d54:	79 ea                	jns    2d40 <forktest+0x20>
  for(; n > 0; n--){
    2d56:	85 db                	test   %ebx,%ebx
    2d58:	74 14                	je     2d6e <forktest+0x4e>
    2d5a:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi
    if(wait() < 0){
    2d60:	e8 26 0c 00 00       	call   398b <wait>
    2d65:	85 c0                	test   %eax,%eax
    2d67:	78 28                	js     2d91 <forktest+0x71>
  for(; n > 0; n--){
    2d69:	83 eb 01             	sub    $0x1,%ebx
    2d6c:	75 f2                	jne    2d60 <forktest+0x40>
  if(wait() != -1){
    2d6e:	e8 18 0c 00 00       	call   398b <wait>
    2d73:	83 f8 ff             	cmp    $0xffffffff,%eax
    2d76:	75 2d                	jne    2da5 <forktest+0x85>
  printf(1, "fork test OK\n");
    2d78:	83 ec 08             	sub    $0x8,%esp
    2d7b:	68 2a 4c 00 00       	push   $0x4c2a
    2d80:	6a 01                	push   $0x1
    2d82:	e8 59 0d 00 00       	call   3ae0 <printf>
}
    2d87:	8b 5d fc             	mov    -0x4(%ebp),%ebx
    2d8a:	c9                   	leave  
    2d8b:	c3                   	ret    
      exit();
    2d8c:	e8 f2 0b 00 00       	call   3983 <exit>
      printf(1, "wait stopped early\n");
    2d91:	83 ec 08             	sub    $0x8,%esp
    2d94:	68 03 4c 00 00       	push   $0x4c03
    2d99:	6a 01                	push   $0x1
    2d9b:	e8 40 0d 00 00       	call   3ae0 <printf>
      exit();
    2da0:	e8 de 0b 00 00       	call   3983 <exit>
    printf(1, "wait got too many\n");
    2da5:	52                   	push   %edx
    2da6:	52                   	push   %edx
    2da7:	68 17 4c 00 00       	push   $0x4c17
    2dac:	6a 01                	push   $0x1
    2dae:	e8 2d 0d 00 00       	call   3ae0 <printf>
    exit();
    2db3:	e8 cb 0b 00 00       	call   3983 <exit>
    printf(1, "fork claimed to work 1000 times!\n");
    2db8:	50                   	push   %eax
    2db9:	50                   	push   %eax
    2dba:	68 98 53 00 00       	push   $0x5398
    2dbf:	6a 01                	push   $0x1
    2dc1:	e8 1a 0d 00 00       	call   3ae0 <printf>
    exit();
    2dc6:	e8 b8 0b 00 00       	call   3983 <exit>
    2dcb:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
    2dcf:	90                   	nop

00002dd0 <sbrktest>:
{
    2dd0:	f3 0f 1e fb          	endbr32 
    2dd4:	55                   	push   %ebp
    2dd5:	89 e5                	mov    %esp,%ebp
    2dd7:	57                   	push   %edi
  for(i = 0; i < 5000; i++){
    2dd8:	31 ff                	xor    %edi,%edi
{
    2dda:	56                   	push   %esi
    2ddb:	53                   	push   %ebx
    2ddc:	83 ec 54             	sub    $0x54,%esp
  printf(stdout, "sbrk test\n");
    2ddf:	68 38 4c 00 00       	push   $0x4c38
    2de4:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    2dea:	e8 f1 0c 00 00       	call   3ae0 <printf>
  oldbrk = sbrk(0);
    2def:	c7 04 24 00 00 00 00 	movl   $0x0,(%esp)
    2df6:	e8 10 0c 00 00       	call   3a0b <sbrk>
  a = sbrk(0);
    2dfb:	c7 04 24 00 00 00 00 	movl   $0x0,(%esp)
  oldbrk = sbrk(0);
    2e02:	89 c3                	mov    %eax,%ebx
  a = sbrk(0);
    2e04:	e8 02 0c 00 00       	call   3a0b <sbrk>
    2e09:	83 c4 10             	add    $0x10,%esp
    2e0c:	89 c6                	mov    %eax,%esi
  for(i = 0; i < 5000; i++){
    2e0e:	eb 02                	jmp    2e12 <sbrktest+0x42>
    a = b + 1;
    2e10:	89 c6                	mov    %eax,%esi
    b = sbrk(1);
    2e12:	83 ec 0c             	sub    $0xc,%esp
    2e15:	6a 01                	push   $0x1
    2e17:	e8 ef 0b 00 00       	call   3a0b <sbrk>
    if(b != a){
    2e1c:	83 c4 10             	add    $0x10,%esp
    2e1f:	39 f0                	cmp    %esi,%eax
    2e21:	0f 85 84 02 00 00    	jne    30ab <sbrktest+0x2db>
  for(i = 0; i < 5000; i++){
    2e27:	83 c7 01             	add    $0x1,%edi
    *b = 1;
    2e2a:	c6 06 01             	movb   $0x1,(%esi)
    a = b + 1;
    2e2d:	8d 46 01             	lea    0x1(%esi),%eax
  for(i = 0; i < 5000; i++){
    2e30:	81 ff 88 13 00 00    	cmp    $0x1388,%edi
    2e36:	75 d8                	jne    2e10 <sbrktest+0x40>
  pid = fork();
    2e38:	e8 3e 0b 00 00       	call   397b <fork>
    2e3d:	89 c7                	mov    %eax,%edi
  if(pid < 0){
    2e3f:	85 c0                	test   %eax,%eax
    2e41:	0f 88 91 03 00 00    	js     31d8 <sbrktest+0x408>
  c = sbrk(1);
    2e47:	83 ec 0c             	sub    $0xc,%esp
  if(c != a + 1){
    2e4a:	83 c6 02             	add    $0x2,%esi
  c = sbrk(1);
    2e4d:	6a 01                	push   $0x1
    2e4f:	e8 b7 0b 00 00       	call   3a0b <sbrk>
  c = sbrk(1);
    2e54:	c7 04 24 01 00 00 00 	movl   $0x1,(%esp)
    2e5b:	e8 ab 0b 00 00       	call   3a0b <sbrk>
  if(c != a + 1){
    2e60:	83 c4 10             	add    $0x10,%esp
    2e63:	39 c6                	cmp    %eax,%esi
    2e65:	0f 85 56 03 00 00    	jne    31c1 <sbrktest+0x3f1>
  if(pid == 0)
    2e6b:	85 ff                	test   %edi,%edi
    2e6d:	0f 84 49 03 00 00    	je     31bc <sbrktest+0x3ec>
  wait();
    2e73:	e8 13 0b 00 00       	call   398b <wait>
  a = sbrk(0);
    2e78:	83 ec 0c             	sub    $0xc,%esp
    2e7b:	6a 00                	push   $0x0
    2e7d:	e8 89 0b 00 00       	call   3a0b <sbrk>
    2e82:	89 c6                	mov    %eax,%esi
  amt = (BIG) - (uint)a;
    2e84:	b8 00 00 40 06       	mov    $0x6400000,%eax
    2e89:	29 f0                	sub    %esi,%eax
  p = sbrk(amt);
    2e8b:	89 04 24             	mov    %eax,(%esp)
    2e8e:	e8 78 0b 00 00       	call   3a0b <sbrk>
  if (p != a) {
    2e93:	83 c4 10             	add    $0x10,%esp
    2e96:	39 c6                	cmp    %eax,%esi
    2e98:	0f 85 07 03 00 00    	jne    31a5 <sbrktest+0x3d5>
  a = sbrk(0);
    2e9e:	83 ec 0c             	sub    $0xc,%esp
  *lastaddr = 99;
    2ea1:	c6 05 ff ff 3f 06 63 	movb   $0x63,0x63fffff
  a = sbrk(0);
    2ea8:	6a 00                	push   $0x0
    2eaa:	e8 5c 0b 00 00       	call   3a0b <sbrk>
  c = sbrk(-4096);
    2eaf:	c7 04 24 00 f0 ff ff 	movl   $0xfffff000,(%esp)
  a = sbrk(0);
    2eb6:	89 c6                	mov    %eax,%esi
  c = sbrk(-4096);
    2eb8:	e8 4e 0b 00 00       	call   3a0b <sbrk>
  if(c == (char*)0xffffffff){
    2ebd:	83 c4 10             	add    $0x10,%esp
    2ec0:	83 f8 ff             	cmp    $0xffffffff,%eax
    2ec3:	0f 84 c5 02 00 00    	je     318e <sbrktest+0x3be>
  c = sbrk(0);
    2ec9:	83 ec 0c             	sub    $0xc,%esp
    2ecc:	6a 00                	push   $0x0
    2ece:	e8 38 0b 00 00       	call   3a0b <sbrk>
  if(c != a - 4096){
    2ed3:	8d 96 00 f0 ff ff    	lea    -0x1000(%esi),%edx
    2ed9:	83 c4 10             	add    $0x10,%esp
    2edc:	39 d0                	cmp    %edx,%eax
    2ede:	0f 85 93 02 00 00    	jne    3177 <sbrktest+0x3a7>
  a = sbrk(0);
    2ee4:	83 ec 0c             	sub    $0xc,%esp
    2ee7:	6a 00                	push   $0x0
    2ee9:	e8 1d 0b 00 00       	call   3a0b <sbrk>
  c = sbrk(4096);
    2eee:	c7 04 24 00 10 00 00 	movl   $0x1000,(%esp)
  a = sbrk(0);
    2ef5:	89 c6                	mov    %eax,%esi
  c = sbrk(4096);
    2ef7:	e8 0f 0b 00 00       	call   3a0b <sbrk>
  if(c != a || sbrk(0) != a + 4096){
    2efc:	83 c4 10             	add    $0x10,%esp
  c = sbrk(4096);
    2eff:	89 c7                	mov    %eax,%edi
  if(c != a || sbrk(0) != a + 4096){
    2f01:	39 c6                	cmp    %eax,%esi
    2f03:	0f 85 57 02 00 00    	jne    3160 <sbrktest+0x390>
    2f09:	83 ec 0c             	sub    $0xc,%esp
    2f0c:	6a 00                	push   $0x0
    2f0e:	e8 f8 0a 00 00       	call   3a0b <sbrk>
    2f13:	8d 96 00 10 00 00    	lea    0x1000(%esi),%edx
    2f19:	83 c4 10             	add    $0x10,%esp
    2f1c:	39 c2                	cmp    %eax,%edx
    2f1e:	0f 85 3c 02 00 00    	jne    3160 <sbrktest+0x390>
  if(*lastaddr == 99){
    2f24:	80 3d ff ff 3f 06 63 	cmpb   $0x63,0x63fffff
    2f2b:	0f 84 18 02 00 00    	je     3149 <sbrktest+0x379>
  a = sbrk(0);
    2f31:	83 ec 0c             	sub    $0xc,%esp
    2f34:	6a 00                	push   $0x0
    2f36:	e8 d0 0a 00 00       	call   3a0b <sbrk>
  c = sbrk(-(sbrk(0) - oldbrk));
    2f3b:	c7 04 24 00 00 00 00 	movl   $0x0,(%esp)
  a = sbrk(0);
    2f42:	89 c6                	mov    %eax,%esi
  c = sbrk(-(sbrk(0) - oldbrk));
    2f44:	e8 c2 0a 00 00       	call   3a0b <sbrk>
    2f49:	89 d9                	mov    %ebx,%ecx
    2f4b:	29 c1                	sub    %eax,%ecx
    2f4d:	89 0c 24             	mov    %ecx,(%esp)
    2f50:	e8 b6 0a 00 00       	call   3a0b <sbrk>
  if(c != a){
    2f55:	83 c4 10             	add    $0x10,%esp
    2f58:	39 c6                	cmp    %eax,%esi
    2f5a:	0f 85 d2 01 00 00    	jne    3132 <sbrktest+0x362>
  for(a = (char*)(KERNBASE); a < (char*) (KERNBASE+2000000); a += 50000){
    2f60:	be 00 00 00 80       	mov    $0x80000000,%esi
    2f65:	8d 76 00             	lea    0x0(%esi),%esi
    ppid = getpid();
    2f68:	e8 96 0a 00 00       	call   3a03 <getpid>
    2f6d:	89 c7                	mov    %eax,%edi
    pid = fork();
    2f6f:	e8 07 0a 00 00       	call   397b <fork>
    if(pid < 0){
    2f74:	85 c0                	test   %eax,%eax
    2f76:	0f 88 9e 01 00 00    	js     311a <sbrktest+0x34a>
    if(pid == 0){
    2f7c:	0f 84 76 01 00 00    	je     30f8 <sbrktest+0x328>
    wait();
    2f82:	e8 04 0a 00 00       	call   398b <wait>
  for(a = (char*)(KERNBASE); a < (char*) (KERNBASE+2000000); a += 50000){
    2f87:	81 c6 50 c3 00 00    	add    $0xc350,%esi
    2f8d:	81 fe 80 84 1e 80    	cmp    $0x801e8480,%esi
    2f93:	75 d3                	jne    2f68 <sbrktest+0x198>
  if(pipe(fds) != 0){
    2f95:	83 ec 0c             	sub    $0xc,%esp
    2f98:	8d 45 b8             	lea    -0x48(%ebp),%eax
    2f9b:	50                   	push   %eax
    2f9c:	e8 f2 09 00 00       	call   3993 <pipe>
    2fa1:	83 c4 10             	add    $0x10,%esp
    2fa4:	85 c0                	test   %eax,%eax
    2fa6:	0f 85 34 01 00 00    	jne    30e0 <sbrktest+0x310>
    2fac:	8d 75 c0             	lea    -0x40(%ebp),%esi
    2faf:	89 f7                	mov    %esi,%edi
    if((pids[i] = fork()) == 0){
    2fb1:	e8 c5 09 00 00       	call   397b <fork>
    2fb6:	89 07                	mov    %eax,(%edi)
    2fb8:	85 c0                	test   %eax,%eax
    2fba:	0f 84 8f 00 00 00    	je     304f <sbrktest+0x27f>
    if(pids[i] != -1)
    2fc0:	83 f8 ff             	cmp    $0xffffffff,%eax
    2fc3:	74 14                	je     2fd9 <sbrktest+0x209>
      read(fds[0], &scratch, 1);
    2fc5:	83 ec 04             	sub    $0x4,%esp
    2fc8:	8d 45 b7             	lea    -0x49(%ebp),%eax
    2fcb:	6a 01                	push   $0x1
    2fcd:	50                   	push   %eax
    2fce:	ff 75 b8             	pushl  -0x48(%ebp)
    2fd1:	e8 c5 09 00 00       	call   399b <read>
    2fd6:	83 c4 10             	add    $0x10,%esp
  for(i = 0; i < sizeof(pids)/sizeof(pids[0]); i++){
    2fd9:	83 c7 04             	add    $0x4,%edi
    2fdc:	8d 45 e8             	lea    -0x18(%ebp),%eax
    2fdf:	39 c7                	cmp    %eax,%edi
    2fe1:	75 ce                	jne    2fb1 <sbrktest+0x1e1>
  c = sbrk(4096);
    2fe3:	83 ec 0c             	sub    $0xc,%esp
    2fe6:	68 00 10 00 00       	push   $0x1000
    2feb:	e8 1b 0a 00 00       	call   3a0b <sbrk>
    2ff0:	83 c4 10             	add    $0x10,%esp
    2ff3:	89 c7                	mov    %eax,%edi
    if(pids[i] == -1)
    2ff5:	8b 06                	mov    (%esi),%eax
    2ff7:	83 f8 ff             	cmp    $0xffffffff,%eax
    2ffa:	74 11                	je     300d <sbrktest+0x23d>
    kill(pids[i]);
    2ffc:	83 ec 0c             	sub    $0xc,%esp
    2fff:	50                   	push   %eax
    3000:	e8 ae 09 00 00       	call   39b3 <kill>
    wait();
    3005:	e8 81 09 00 00       	call   398b <wait>
    300a:	83 c4 10             	add    $0x10,%esp
  for(i = 0; i < sizeof(pids)/sizeof(pids[0]); i++){
    300d:	83 c6 04             	add    $0x4,%esi
    3010:	8d 45 e8             	lea    -0x18(%ebp),%eax
    3013:	39 f0                	cmp    %esi,%eax
    3015:	75 de                	jne    2ff5 <sbrktest+0x225>
  if(c == (char*)0xffffffff){
    3017:	83 ff ff             	cmp    $0xffffffff,%edi
    301a:	0f 84 a9 00 00 00    	je     30c9 <sbrktest+0x2f9>
  if(sbrk(0) > oldbrk)
    3020:	83 ec 0c             	sub    $0xc,%esp
    3023:	6a 00                	push   $0x0
    3025:	e8 e1 09 00 00       	call   3a0b <sbrk>
    302a:	83 c4 10             	add    $0x10,%esp
    302d:	39 c3                	cmp    %eax,%ebx
    302f:	72 61                	jb     3092 <sbrktest+0x2c2>
  printf(stdout, "sbrk test OK\n");
    3031:	83 ec 08             	sub    $0x8,%esp
    3034:	68 e0 4c 00 00       	push   $0x4ce0
    3039:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    303f:	e8 9c 0a 00 00       	call   3ae0 <printf>
}
    3044:	83 c4 10             	add    $0x10,%esp
    3047:	8d 65 f4             	lea    -0xc(%ebp),%esp
    304a:	5b                   	pop    %ebx
    304b:	5e                   	pop    %esi
    304c:	5f                   	pop    %edi
    304d:	5d                   	pop    %ebp
    304e:	c3                   	ret    
      sbrk(BIG - (uint)sbrk(0));
    304f:	83 ec 0c             	sub    $0xc,%esp
    3052:	6a 00                	push   $0x0
    3054:	e8 b2 09 00 00       	call   3a0b <sbrk>
    3059:	89 c2                	mov    %eax,%edx
    305b:	b8 00 00 40 06       	mov    $0x6400000,%eax
    3060:	29 d0                	sub    %edx,%eax
    3062:	89 04 24             	mov    %eax,(%esp)
    3065:	e8 a1 09 00 00       	call   3a0b <sbrk>
      write(fds[1], "x", 1);
    306a:	83 c4 0c             	add    $0xc,%esp
    306d:	6a 01                	push   $0x1
    306f:	68 a1 47 00 00       	push   $0x47a1
    3074:	ff 75 bc             	pushl  -0x44(%ebp)
    3077:	e8 27 09 00 00       	call   39a3 <write>
    307c:	83 c4 10             	add    $0x10,%esp
    307f:	90                   	nop
      for(;;) sleep(1000);
    3080:	83 ec 0c             	sub    $0xc,%esp
    3083:	68 e8 03 00 00       	push   $0x3e8
    3088:	e8 86 09 00 00       	call   3a13 <sleep>
    308d:	83 c4 10             	add    $0x10,%esp
    3090:	eb ee                	jmp    3080 <sbrktest+0x2b0>
    sbrk(-(sbrk(0) - oldbrk));
    3092:	83 ec 0c             	sub    $0xc,%esp
    3095:	6a 00                	push   $0x0
    3097:	e8 6f 09 00 00       	call   3a0b <sbrk>
    309c:	29 c3                	sub    %eax,%ebx
    309e:	89 1c 24             	mov    %ebx,(%esp)
    30a1:	e8 65 09 00 00       	call   3a0b <sbrk>
    30a6:	83 c4 10             	add    $0x10,%esp
    30a9:	eb 86                	jmp    3031 <sbrktest+0x261>
      printf(stdout, "sbrk test failed %d %x %x\n", i, a, b);
    30ab:	83 ec 0c             	sub    $0xc,%esp
    30ae:	50                   	push   %eax
    30af:	56                   	push   %esi
    30b0:	57                   	push   %edi
    30b1:	68 43 4c 00 00       	push   $0x4c43
    30b6:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    30bc:	e8 1f 0a 00 00       	call   3ae0 <printf>
      exit();
    30c1:	83 c4 20             	add    $0x20,%esp
    30c4:	e8 ba 08 00 00       	call   3983 <exit>
    printf(stdout, "failed sbrk leaked memory\n");
    30c9:	50                   	push   %eax
    30ca:	50                   	push   %eax
    30cb:	68 c5 4c 00 00       	push   $0x4cc5
    30d0:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    30d6:	e8 05 0a 00 00       	call   3ae0 <printf>
    exit();
    30db:	e8 a3 08 00 00       	call   3983 <exit>
    printf(1, "pipe() failed\n");
    30e0:	52                   	push   %edx
    30e1:	52                   	push   %edx
    30e2:	68 81 41 00 00       	push   $0x4181
    30e7:	6a 01                	push   $0x1
    30e9:	e8 f2 09 00 00       	call   3ae0 <printf>
    exit();
    30ee:	e8 90 08 00 00       	call   3983 <exit>
    30f3:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
    30f7:	90                   	nop
      printf(stdout, "oops could read %x = %x\n", a, *a);
    30f8:	0f be 06             	movsbl (%esi),%eax
    30fb:	50                   	push   %eax
    30fc:	56                   	push   %esi
    30fd:	68 ac 4c 00 00       	push   $0x4cac
    3102:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    3108:	e8 d3 09 00 00       	call   3ae0 <printf>
      kill(ppid);
    310d:	89 3c 24             	mov    %edi,(%esp)
    3110:	e8 9e 08 00 00       	call   39b3 <kill>
      exit();
    3115:	e8 69 08 00 00       	call   3983 <exit>
      printf(stdout, "fork failed\n");
    311a:	83 ec 08             	sub    $0x8,%esp
    311d:	68 89 4d 00 00       	push   $0x4d89
    3122:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    3128:	e8 b3 09 00 00       	call   3ae0 <printf>
      exit();
    312d:	e8 51 08 00 00       	call   3983 <exit>
    printf(stdout, "sbrk downsize failed, a %x c %x\n", a, c);
    3132:	50                   	push   %eax
    3133:	56                   	push   %esi
    3134:	68 8c 54 00 00       	push   $0x548c
    3139:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    313f:	e8 9c 09 00 00       	call   3ae0 <printf>
    exit();
    3144:	e8 3a 08 00 00       	call   3983 <exit>
    printf(stdout, "sbrk de-allocation didn't really deallocate\n");
    3149:	51                   	push   %ecx
    314a:	51                   	push   %ecx
    314b:	68 5c 54 00 00       	push   $0x545c
    3150:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    3156:	e8 85 09 00 00       	call   3ae0 <printf>
    exit();
    315b:	e8 23 08 00 00       	call   3983 <exit>
    printf(stdout, "sbrk re-allocation failed, a %x c %x\n", a, c);
    3160:	57                   	push   %edi
    3161:	56                   	push   %esi
    3162:	68 34 54 00 00       	push   $0x5434
    3167:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    316d:	e8 6e 09 00 00       	call   3ae0 <printf>
    exit();
    3172:	e8 0c 08 00 00       	call   3983 <exit>
    printf(stdout, "sbrk deallocation produced wrong address, a %x c %x\n", a, c);
    3177:	50                   	push   %eax
    3178:	56                   	push   %esi
    3179:	68 fc 53 00 00       	push   $0x53fc
    317e:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    3184:	e8 57 09 00 00       	call   3ae0 <printf>
    exit();
    3189:	e8 f5 07 00 00       	call   3983 <exit>
    printf(stdout, "sbrk could not deallocate\n");
    318e:	53                   	push   %ebx
    318f:	53                   	push   %ebx
    3190:	68 91 4c 00 00       	push   $0x4c91
    3195:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    319b:	e8 40 09 00 00       	call   3ae0 <printf>
    exit();
    31a0:	e8 de 07 00 00       	call   3983 <exit>
    printf(stdout, "sbrk test failed to grow big address space; enough phys mem?\n");
    31a5:	56                   	push   %esi
    31a6:	56                   	push   %esi
    31a7:	68 bc 53 00 00       	push   $0x53bc
    31ac:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    31b2:	e8 29 09 00 00       	call   3ae0 <printf>
    exit();
    31b7:	e8 c7 07 00 00       	call   3983 <exit>
    exit();
    31bc:	e8 c2 07 00 00       	call   3983 <exit>
    printf(stdout, "sbrk test failed post-fork\n");
    31c1:	57                   	push   %edi
    31c2:	57                   	push   %edi
    31c3:	68 75 4c 00 00       	push   $0x4c75
    31c8:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    31ce:	e8 0d 09 00 00       	call   3ae0 <printf>
    exit();
    31d3:	e8 ab 07 00 00       	call   3983 <exit>
    printf(stdout, "sbrk test fork failed\n");
    31d8:	50                   	push   %eax
    31d9:	50                   	push   %eax
    31da:	68 5e 4c 00 00       	push   $0x4c5e
    31df:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    31e5:	e8 f6 08 00 00       	call   3ae0 <printf>
    exit();
    31ea:	e8 94 07 00 00       	call   3983 <exit>
    31ef:	90                   	nop

000031f0 <validateint>:
{
    31f0:	f3 0f 1e fb          	endbr32 
}
    31f4:	c3                   	ret    
    31f5:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    31fc:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi

00003200 <validatetest>:
{
    3200:	f3 0f 1e fb          	endbr32 
    3204:	55                   	push   %ebp
    3205:	89 e5                	mov    %esp,%ebp
    3207:	56                   	push   %esi
  for(p = 0; p <= (uint)hi; p += 4096){
    3208:	31 f6                	xor    %esi,%esi
{
    320a:	53                   	push   %ebx
  printf(stdout, "validate test\n");
    320b:	83 ec 08             	sub    $0x8,%esp
    320e:	68 ee 4c 00 00       	push   $0x4cee
    3213:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    3219:	e8 c2 08 00 00       	call   3ae0 <printf>
    321e:	83 c4 10             	add    $0x10,%esp
    3221:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    if((pid = fork()) == 0){
    3228:	e8 4e 07 00 00       	call   397b <fork>
    322d:	89 c3                	mov    %eax,%ebx
    322f:	85 c0                	test   %eax,%eax
    3231:	74 63                	je     3296 <validatetest+0x96>
    sleep(0);
    3233:	83 ec 0c             	sub    $0xc,%esp
    3236:	6a 00                	push   $0x0
    3238:	e8 d6 07 00 00       	call   3a13 <sleep>
    sleep(0);
    323d:	c7 04 24 00 00 00 00 	movl   $0x0,(%esp)
    3244:	e8 ca 07 00 00       	call   3a13 <sleep>
    kill(pid);
    3249:	89 1c 24             	mov    %ebx,(%esp)
    324c:	e8 62 07 00 00       	call   39b3 <kill>
    wait();
    3251:	e8 35 07 00 00       	call   398b <wait>
    if(link("nosuchfile", (char*)p) != -1){
    3256:	58                   	pop    %eax
    3257:	5a                   	pop    %edx
    3258:	56                   	push   %esi
    3259:	68 fd 4c 00 00       	push   $0x4cfd
    325e:	e8 80 07 00 00       	call   39e3 <link>
    3263:	83 c4 10             	add    $0x10,%esp
    3266:	83 f8 ff             	cmp    $0xffffffff,%eax
    3269:	75 30                	jne    329b <validatetest+0x9b>
  for(p = 0; p <= (uint)hi; p += 4096){
    326b:	81 c6 00 10 00 00    	add    $0x1000,%esi
    3271:	81 fe 00 40 11 00    	cmp    $0x114000,%esi
    3277:	75 af                	jne    3228 <validatetest+0x28>
  printf(stdout, "validate ok\n");
    3279:	83 ec 08             	sub    $0x8,%esp
    327c:	68 21 4d 00 00       	push   $0x4d21
    3281:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    3287:	e8 54 08 00 00       	call   3ae0 <printf>
}
    328c:	83 c4 10             	add    $0x10,%esp
    328f:	8d 65 f8             	lea    -0x8(%ebp),%esp
    3292:	5b                   	pop    %ebx
    3293:	5e                   	pop    %esi
    3294:	5d                   	pop    %ebp
    3295:	c3                   	ret    
      exit();
    3296:	e8 e8 06 00 00       	call   3983 <exit>
      printf(stdout, "link should not succeed\n");
    329b:	83 ec 08             	sub    $0x8,%esp
    329e:	68 08 4d 00 00       	push   $0x4d08
    32a3:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    32a9:	e8 32 08 00 00       	call   3ae0 <printf>
      exit();
    32ae:	e8 d0 06 00 00       	call   3983 <exit>
    32b3:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    32ba:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi

000032c0 <bsstest>:
{
    32c0:	f3 0f 1e fb          	endbr32 
    32c4:	55                   	push   %ebp
    32c5:	89 e5                	mov    %esp,%ebp
    32c7:	83 ec 10             	sub    $0x10,%esp
  printf(stdout, "bss test\n");
    32ca:	68 2e 4d 00 00       	push   $0x4d2e
    32cf:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    32d5:	e8 06 08 00 00       	call   3ae0 <printf>
    32da:	83 c4 10             	add    $0x10,%esp
  for(i = 0; i < sizeof(uninit); i++){
    32dd:	31 c0                	xor    %eax,%eax
    32df:	90                   	nop
    if(uninit[i] != '\0'){
    32e0:	80 b8 a0 5f 00 00 00 	cmpb   $0x0,0x5fa0(%eax)
    32e7:	75 22                	jne    330b <bsstest+0x4b>
  for(i = 0; i < sizeof(uninit); i++){
    32e9:	83 c0 01             	add    $0x1,%eax
    32ec:	3d 10 27 00 00       	cmp    $0x2710,%eax
    32f1:	75 ed                	jne    32e0 <bsstest+0x20>
  printf(stdout, "bss test ok\n");
    32f3:	83 ec 08             	sub    $0x8,%esp
    32f6:	68 49 4d 00 00       	push   $0x4d49
    32fb:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    3301:	e8 da 07 00 00       	call   3ae0 <printf>
}
    3306:	83 c4 10             	add    $0x10,%esp
    3309:	c9                   	leave  
    330a:	c3                   	ret    
      printf(stdout, "bss test failed\n");
    330b:	83 ec 08             	sub    $0x8,%esp
    330e:	68 38 4d 00 00       	push   $0x4d38
    3313:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    3319:	e8 c2 07 00 00       	call   3ae0 <printf>
      exit();
    331e:	e8 60 06 00 00       	call   3983 <exit>
    3323:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    332a:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi

00003330 <bigargtest>:
{
    3330:	f3 0f 1e fb          	endbr32 
    3334:	55                   	push   %ebp
    3335:	89 e5                	mov    %esp,%ebp
    3337:	83 ec 14             	sub    $0x14,%esp
  unlink("bigarg-ok");
    333a:	68 56 4d 00 00       	push   $0x4d56
    333f:	e8 8f 06 00 00       	call   39d3 <unlink>
  pid = fork();
    3344:	e8 32 06 00 00       	call   397b <fork>
  if(pid == 0){
    3349:	83 c4 10             	add    $0x10,%esp
    334c:	85 c0                	test   %eax,%eax
    334e:	74 40                	je     3390 <bigargtest+0x60>
  } else if(pid < 0){
    3350:	0f 88 c1 00 00 00    	js     3417 <bigargtest+0xe7>
  wait();
    3356:	e8 30 06 00 00       	call   398b <wait>
  fd = open("bigarg-ok", 0);
    335b:	83 ec 08             	sub    $0x8,%esp
    335e:	6a 00                	push   $0x0
    3360:	68 56 4d 00 00       	push   $0x4d56
    3365:	e8 59 06 00 00       	call   39c3 <open>
  if(fd < 0){
    336a:	83 c4 10             	add    $0x10,%esp
    336d:	85 c0                	test   %eax,%eax
    336f:	0f 88 8b 00 00 00    	js     3400 <bigargtest+0xd0>
  close(fd);
    3375:	83 ec 0c             	sub    $0xc,%esp
    3378:	50                   	push   %eax
    3379:	e8 2d 06 00 00       	call   39ab <close>
  unlink("bigarg-ok");
    337e:	c7 04 24 56 4d 00 00 	movl   $0x4d56,(%esp)
    3385:	e8 49 06 00 00       	call   39d3 <unlink>
}
    338a:	83 c4 10             	add    $0x10,%esp
    338d:	c9                   	leave  
    338e:	c3                   	ret    
    338f:	90                   	nop
      args[i] = "bigargs test: failed\n                                                                                                                                                                                                       ";
    3390:	c7 04 85 00 5f 00 00 	movl   $0x54b0,0x5f00(,%eax,4)
    3397:	b0 54 00 00 
    for(i = 0; i < MAXARG-1; i++)
    339b:	83 c0 01             	add    $0x1,%eax
    339e:	83 f8 1f             	cmp    $0x1f,%eax
    33a1:	75 ed                	jne    3390 <bigargtest+0x60>
    printf(stdout, "bigarg test\n");
    33a3:	51                   	push   %ecx
    33a4:	51                   	push   %ecx
    33a5:	68 60 4d 00 00       	push   $0x4d60
    33aa:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    args[MAXARG-1] = 0;
    33b0:	c7 05 7c 5f 00 00 00 	movl   $0x0,0x5f7c
    33b7:	00 00 00 
    printf(stdout, "bigarg test\n");
    33ba:	e8 21 07 00 00       	call   3ae0 <printf>
    exec("echo", args);
    33bf:	58                   	pop    %eax
    33c0:	5a                   	pop    %edx
    33c1:	68 00 5f 00 00       	push   $0x5f00
    33c6:	68 2d 3f 00 00       	push   $0x3f2d
    33cb:	e8 eb 05 00 00       	call   39bb <exec>
    printf(stdout, "bigarg test ok\n");
    33d0:	59                   	pop    %ecx
    33d1:	58                   	pop    %eax
    33d2:	68 6d 4d 00 00       	push   $0x4d6d
    33d7:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    33dd:	e8 fe 06 00 00       	call   3ae0 <printf>
    fd = open("bigarg-ok", O_CREATE);
    33e2:	58                   	pop    %eax
    33e3:	5a                   	pop    %edx
    33e4:	68 00 02 00 00       	push   $0x200
    33e9:	68 56 4d 00 00       	push   $0x4d56
    33ee:	e8 d0 05 00 00       	call   39c3 <open>
    close(fd);
    33f3:	89 04 24             	mov    %eax,(%esp)
    33f6:	e8 b0 05 00 00       	call   39ab <close>
    exit();
    33fb:	e8 83 05 00 00       	call   3983 <exit>
    printf(stdout, "bigarg test failed!\n");
    3400:	50                   	push   %eax
    3401:	50                   	push   %eax
    3402:	68 96 4d 00 00       	push   $0x4d96
    3407:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    340d:	e8 ce 06 00 00       	call   3ae0 <printf>
    exit();
    3412:	e8 6c 05 00 00       	call   3983 <exit>
    printf(stdout, "bigargtest: fork failed\n");
    3417:	52                   	push   %edx
    3418:	52                   	push   %edx
    3419:	68 7d 4d 00 00       	push   $0x4d7d
    341e:	ff 35 e0 5e 00 00    	pushl  0x5ee0
    3424:	e8 b7 06 00 00       	call   3ae0 <printf>
    exit();
    3429:	e8 55 05 00 00       	call   3983 <exit>
    342e:	66 90                	xchg   %ax,%ax

00003430 <fsfull>:
{
    3430:	f3 0f 1e fb          	endbr32 
    3434:	55                   	push   %ebp
    3435:	89 e5                	mov    %esp,%ebp
    3437:	57                   	push   %edi
    3438:	56                   	push   %esi
  for(nfiles = 0; ; nfiles++){
    3439:	31 f6                	xor    %esi,%esi
{
    343b:	53                   	push   %ebx
    343c:	83 ec 54             	sub    $0x54,%esp
  printf(1, "fsfull test\n");
    343f:	68 ab 4d 00 00       	push   $0x4dab
    3444:	6a 01                	push   $0x1
    3446:	e8 95 06 00 00       	call   3ae0 <printf>
    344b:	83 c4 10             	add    $0x10,%esp
    344e:	66 90                	xchg   %ax,%ax
    name[1] = '0' + nfiles / 1000;
    3450:	b8 d3 4d 62 10       	mov    $0x10624dd3,%eax
    name[3] = '0' + (nfiles % 100) / 10;
    3455:	b9 cd cc cc cc       	mov    $0xcccccccd,%ecx
    printf(1, "writing %s\n", name);
    345a:	83 ec 04             	sub    $0x4,%esp
    name[0] = 'f';
    345d:	c6 45 a8 66          	movb   $0x66,-0x58(%ebp)
    name[1] = '0' + nfiles / 1000;
    3461:	f7 e6                	mul    %esi
    name[5] = '\0';
    3463:	c6 45 ad 00          	movb   $0x0,-0x53(%ebp)
    name[1] = '0' + nfiles / 1000;
    3467:	c1 ea 06             	shr    $0x6,%edx
    346a:	8d 42 30             	lea    0x30(%edx),%eax
    name[2] = '0' + (nfiles % 1000) / 100;
    346d:	69 d2 e8 03 00 00    	imul   $0x3e8,%edx,%edx
    name[1] = '0' + nfiles / 1000;
    3473:	88 45 a9             	mov    %al,-0x57(%ebp)
    name[2] = '0' + (nfiles % 1000) / 100;
    3476:	89 f0                	mov    %esi,%eax
    3478:	29 d0                	sub    %edx,%eax
    347a:	89 c2                	mov    %eax,%edx
    347c:	b8 1f 85 eb 51       	mov    $0x51eb851f,%eax
    3481:	f7 e2                	mul    %edx
    name[3] = '0' + (nfiles % 100) / 10;
    3483:	b8 1f 85 eb 51       	mov    $0x51eb851f,%eax
    name[2] = '0' + (nfiles % 1000) / 100;
    3488:	c1 ea 05             	shr    $0x5,%edx
    348b:	83 c2 30             	add    $0x30,%edx
    348e:	88 55 aa             	mov    %dl,-0x56(%ebp)
    name[3] = '0' + (nfiles % 100) / 10;
    3491:	f7 e6                	mul    %esi
    3493:	89 f0                	mov    %esi,%eax
    3495:	c1 ea 05             	shr    $0x5,%edx
    3498:	6b d2 64             	imul   $0x64,%edx,%edx
    349b:	29 d0                	sub    %edx,%eax
    349d:	f7 e1                	mul    %ecx
    name[4] = '0' + (nfiles % 10);
    349f:	89 f0                	mov    %esi,%eax
    name[3] = '0' + (nfiles % 100) / 10;
    34a1:	c1 ea 03             	shr    $0x3,%edx
    34a4:	83 c2 30             	add    $0x30,%edx
    34a7:	88 55 ab             	mov    %dl,-0x55(%ebp)
    name[4] = '0' + (nfiles % 10);
    34aa:	f7 e1                	mul    %ecx
    34ac:	89 f1                	mov    %esi,%ecx
    34ae:	c1 ea 03             	shr    $0x3,%edx
    34b1:	8d 04 92             	lea    (%edx,%edx,4),%eax
    34b4:	01 c0                	add    %eax,%eax
    34b6:	29 c1                	sub    %eax,%ecx
    34b8:	89 c8                	mov    %ecx,%eax
    34ba:	83 c0 30             	add    $0x30,%eax
    34bd:	88 45 ac             	mov    %al,-0x54(%ebp)
    printf(1, "writing %s\n", name);
    34c0:	8d 45 a8             	lea    -0x58(%ebp),%eax
    34c3:	50                   	push   %eax
    34c4:	68 b8 4d 00 00       	push   $0x4db8
    34c9:	6a 01                	push   $0x1
    34cb:	e8 10 06 00 00       	call   3ae0 <printf>
    int fd = open(name, O_CREATE|O_RDWR);
    34d0:	58                   	pop    %eax
    34d1:	8d 45 a8             	lea    -0x58(%ebp),%eax
    34d4:	5a                   	pop    %edx
    34d5:	68 02 02 00 00       	push   $0x202
    34da:	50                   	push   %eax
    34db:	e8 e3 04 00 00       	call   39c3 <open>
    if(fd < 0){
    34e0:	83 c4 10             	add    $0x10,%esp
    int fd = open(name, O_CREATE|O_RDWR);
    34e3:	89 c7                	mov    %eax,%edi
    if(fd < 0){
    34e5:	85 c0                	test   %eax,%eax
    34e7:	78 4d                	js     3536 <fsfull+0x106>
    int total = 0;
    34e9:	31 db                	xor    %ebx,%ebx
    34eb:	eb 05                	jmp    34f2 <fsfull+0xc2>
    34ed:	8d 76 00             	lea    0x0(%esi),%esi
      total += cc;
    34f0:	01 c3                	add    %eax,%ebx
      int cc = write(fd, buf, 512);
    34f2:	83 ec 04             	sub    $0x4,%esp
    34f5:	68 00 02 00 00       	push   $0x200
    34fa:	68 c0 86 00 00       	push   $0x86c0
    34ff:	57                   	push   %edi
    3500:	e8 9e 04 00 00       	call   39a3 <write>
      if(cc < 512)
    3505:	83 c4 10             	add    $0x10,%esp
    3508:	3d ff 01 00 00       	cmp    $0x1ff,%eax
    350d:	7f e1                	jg     34f0 <fsfull+0xc0>
    printf(1, "wrote %d bytes\n", total);
    350f:	83 ec 04             	sub    $0x4,%esp
    3512:	53                   	push   %ebx
    3513:	68 d4 4d 00 00       	push   $0x4dd4
    3518:	6a 01                	push   $0x1
    351a:	e8 c1 05 00 00       	call   3ae0 <printf>
    close(fd);
    351f:	89 3c 24             	mov    %edi,(%esp)
    3522:	e8 84 04 00 00       	call   39ab <close>
    if(total == 0)
    3527:	83 c4 10             	add    $0x10,%esp
    352a:	85 db                	test   %ebx,%ebx
    352c:	74 1e                	je     354c <fsfull+0x11c>
  for(nfiles = 0; ; nfiles++){
    352e:	83 c6 01             	add    $0x1,%esi
    3531:	e9 1a ff ff ff       	jmp    3450 <fsfull+0x20>
      printf(1, "open %s failed\n", name);
    3536:	83 ec 04             	sub    $0x4,%esp
    3539:	8d 45 a8             	lea    -0x58(%ebp),%eax
    353c:	50                   	push   %eax
    353d:	68 c4 4d 00 00       	push   $0x4dc4
    3542:	6a 01                	push   $0x1
    3544:	e8 97 05 00 00       	call   3ae0 <printf>
      break;
    3549:	83 c4 10             	add    $0x10,%esp
    name[1] = '0' + nfiles / 1000;
    354c:	bf d3 4d 62 10       	mov    $0x10624dd3,%edi
    name[2] = '0' + (nfiles % 1000) / 100;
    3551:	bb 1f 85 eb 51       	mov    $0x51eb851f,%ebx
    3556:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    355d:	8d 76 00             	lea    0x0(%esi),%esi
    name[1] = '0' + nfiles / 1000;
    3560:	89 f0                	mov    %esi,%eax
    3562:	89 f1                	mov    %esi,%ecx
    unlink(name);
    3564:	83 ec 0c             	sub    $0xc,%esp
    name[0] = 'f';
    3567:	c6 45 a8 66          	movb   $0x66,-0x58(%ebp)
    name[1] = '0' + nfiles / 1000;
    356b:	f7 ef                	imul   %edi
    356d:	c1 f9 1f             	sar    $0x1f,%ecx
    name[5] = '\0';
    3570:	c6 45 ad 00          	movb   $0x0,-0x53(%ebp)
    name[1] = '0' + nfiles / 1000;
    3574:	c1 fa 06             	sar    $0x6,%edx
    3577:	29 ca                	sub    %ecx,%edx
    3579:	8d 42 30             	lea    0x30(%edx),%eax
    name[2] = '0' + (nfiles % 1000) / 100;
    357c:	69 d2 e8 03 00 00    	imul   $0x3e8,%edx,%edx
    name[1] = '0' + nfiles / 1000;
    3582:	88 45 a9             	mov    %al,-0x57(%ebp)
    name[2] = '0' + (nfiles % 1000) / 100;
    3585:	89 f0                	mov    %esi,%eax
    3587:	29 d0                	sub    %edx,%eax
    3589:	f7 e3                	mul    %ebx
    name[3] = '0' + (nfiles % 100) / 10;
    358b:	89 f0                	mov    %esi,%eax
    name[2] = '0' + (nfiles % 1000) / 100;
    358d:	c1 ea 05             	shr    $0x5,%edx
    3590:	83 c2 30             	add    $0x30,%edx
    3593:	88 55 aa             	mov    %dl,-0x56(%ebp)
    name[3] = '0' + (nfiles % 100) / 10;
    3596:	f7 eb                	imul   %ebx
    3598:	89 f0                	mov    %esi,%eax
    359a:	c1 fa 05             	sar    $0x5,%edx
    359d:	29 ca                	sub    %ecx,%edx
    359f:	6b d2 64             	imul   $0x64,%edx,%edx
    35a2:	29 d0                	sub    %edx,%eax
    35a4:	ba cd cc cc cc       	mov    $0xcccccccd,%edx
    35a9:	f7 e2                	mul    %edx
    name[4] = '0' + (nfiles % 10);
    35ab:	89 f0                	mov    %esi,%eax
    name[3] = '0' + (nfiles % 100) / 10;
    35ad:	c1 ea 03             	shr    $0x3,%edx
    35b0:	83 c2 30             	add    $0x30,%edx
    35b3:	88 55 ab             	mov    %dl,-0x55(%ebp)
    name[4] = '0' + (nfiles % 10);
    35b6:	ba 67 66 66 66       	mov    $0x66666667,%edx
    35bb:	f7 ea                	imul   %edx
    35bd:	c1 fa 02             	sar    $0x2,%edx
    35c0:	29 ca                	sub    %ecx,%edx
    35c2:	89 f1                	mov    %esi,%ecx
    nfiles--;
    35c4:	83 ee 01             	sub    $0x1,%esi
    name[4] = '0' + (nfiles % 10);
    35c7:	8d 04 92             	lea    (%edx,%edx,4),%eax
    35ca:	01 c0                	add    %eax,%eax
    35cc:	29 c1                	sub    %eax,%ecx
    35ce:	89 c8                	mov    %ecx,%eax
    35d0:	83 c0 30             	add    $0x30,%eax
    35d3:	88 45 ac             	mov    %al,-0x54(%ebp)
    unlink(name);
    35d6:	8d 45 a8             	lea    -0x58(%ebp),%eax
    35d9:	50                   	push   %eax
    35da:	e8 f4 03 00 00       	call   39d3 <unlink>
  while(nfiles >= 0){
    35df:	83 c4 10             	add    $0x10,%esp
    35e2:	83 fe ff             	cmp    $0xffffffff,%esi
    35e5:	0f 85 75 ff ff ff    	jne    3560 <fsfull+0x130>
  printf(1, "fsfull test finished\n");
    35eb:	83 ec 08             	sub    $0x8,%esp
    35ee:	68 e4 4d 00 00       	push   $0x4de4
    35f3:	6a 01                	push   $0x1
    35f5:	e8 e6 04 00 00       	call   3ae0 <printf>
}
    35fa:	83 c4 10             	add    $0x10,%esp
    35fd:	8d 65 f4             	lea    -0xc(%ebp),%esp
    3600:	5b                   	pop    %ebx
    3601:	5e                   	pop    %esi
    3602:	5f                   	pop    %edi
    3603:	5d                   	pop    %ebp
    3604:	c3                   	ret    
    3605:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    360c:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi

00003610 <uio>:
{
    3610:	f3 0f 1e fb          	endbr32 
    3614:	55                   	push   %ebp
    3615:	89 e5                	mov    %esp,%ebp
    3617:	83 ec 10             	sub    $0x10,%esp
  printf(1, "uio test\n");
    361a:	68 fa 4d 00 00       	push   $0x4dfa
    361f:	6a 01                	push   $0x1
    3621:	e8 ba 04 00 00       	call   3ae0 <printf>
  pid = fork();
    3626:	e8 50 03 00 00       	call   397b <fork>
  if(pid == 0){
    362b:	83 c4 10             	add    $0x10,%esp
    362e:	85 c0                	test   %eax,%eax
    3630:	74 1b                	je     364d <uio+0x3d>
  } else if(pid < 0){
    3632:	78 3d                	js     3671 <uio+0x61>
  wait();
    3634:	e8 52 03 00 00       	call   398b <wait>
  printf(1, "uio test done\n");
    3639:	83 ec 08             	sub    $0x8,%esp
    363c:	68 04 4e 00 00       	push   $0x4e04
    3641:	6a 01                	push   $0x1
    3643:	e8 98 04 00 00       	call   3ae0 <printf>
}
    3648:	83 c4 10             	add    $0x10,%esp
    364b:	c9                   	leave  
    364c:	c3                   	ret    
    asm volatile("outb %0,%1"::"a"(val), "d" (port));
    364d:	b8 09 00 00 00       	mov    $0x9,%eax
    3652:	ba 70 00 00 00       	mov    $0x70,%edx
    3657:	ee                   	out    %al,(%dx)
    asm volatile("inb %1,%0" : "=a" (val) : "d" (port));
    3658:	ba 71 00 00 00       	mov    $0x71,%edx
    365d:	ec                   	in     (%dx),%al
    printf(1, "uio: uio succeeded; test FAILED\n");
    365e:	52                   	push   %edx
    365f:	52                   	push   %edx
    3660:	68 90 55 00 00       	push   $0x5590
    3665:	6a 01                	push   $0x1
    3667:	e8 74 04 00 00       	call   3ae0 <printf>
    exit();
    366c:	e8 12 03 00 00       	call   3983 <exit>
    printf (1, "fork failed\n");
    3671:	50                   	push   %eax
    3672:	50                   	push   %eax
    3673:	68 89 4d 00 00       	push   $0x4d89
    3678:	6a 01                	push   $0x1
    367a:	e8 61 04 00 00       	call   3ae0 <printf>
    exit();
    367f:	e8 ff 02 00 00       	call   3983 <exit>
    3684:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    368b:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
    368f:	90                   	nop

00003690 <argptest>:
{
    3690:	f3 0f 1e fb          	endbr32 
    3694:	55                   	push   %ebp
    3695:	89 e5                	mov    %esp,%ebp
    3697:	53                   	push   %ebx
    3698:	83 ec 0c             	sub    $0xc,%esp
  fd = open("init", O_RDONLY);
    369b:	6a 00                	push   $0x0
    369d:	68 13 4e 00 00       	push   $0x4e13
    36a2:	e8 1c 03 00 00       	call   39c3 <open>
  if (fd < 0) {
    36a7:	83 c4 10             	add    $0x10,%esp
    36aa:	85 c0                	test   %eax,%eax
    36ac:	78 39                	js     36e7 <argptest+0x57>
  read(fd, sbrk(0) - 1, -1);
    36ae:	83 ec 0c             	sub    $0xc,%esp
    36b1:	89 c3                	mov    %eax,%ebx
    36b3:	6a 00                	push   $0x0
    36b5:	e8 51 03 00 00       	call   3a0b <sbrk>
    36ba:	83 c4 0c             	add    $0xc,%esp
    36bd:	83 e8 01             	sub    $0x1,%eax
    36c0:	6a ff                	push   $0xffffffff
    36c2:	50                   	push   %eax
    36c3:	53                   	push   %ebx
    36c4:	e8 d2 02 00 00       	call   399b <read>
  close(fd);
    36c9:	89 1c 24             	mov    %ebx,(%esp)
    36cc:	e8 da 02 00 00       	call   39ab <close>
  printf(1, "arg test passed\n");
    36d1:	58                   	pop    %eax
    36d2:	5a                   	pop    %edx
    36d3:	68 25 4e 00 00       	push   $0x4e25
    36d8:	6a 01                	push   $0x1
    36da:	e8 01 04 00 00       	call   3ae0 <printf>
}
    36df:	8b 5d fc             	mov    -0x4(%ebp),%ebx
    36e2:	83 c4 10             	add    $0x10,%esp
    36e5:	c9                   	leave  
    36e6:	c3                   	ret    
    printf(2, "open failed\n");
    36e7:	51                   	push   %ecx
    36e8:	51                   	push   %ecx
    36e9:	68 18 4e 00 00       	push   $0x4e18
    36ee:	6a 02                	push   $0x2
    36f0:	e8 eb 03 00 00       	call   3ae0 <printf>
    exit();
    36f5:	e8 89 02 00 00       	call   3983 <exit>
    36fa:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi

00003700 <rand>:
{
    3700:	f3 0f 1e fb          	endbr32 
  randstate = randstate * 1664525 + 1013904223;
    3704:	69 05 dc 5e 00 00 0d 	imul   $0x19660d,0x5edc,%eax
    370b:	66 19 00 
    370e:	05 5f f3 6e 3c       	add    $0x3c6ef35f,%eax
    3713:	a3 dc 5e 00 00       	mov    %eax,0x5edc
}
    3718:	c3                   	ret    
    3719:	66 90                	xchg   %ax,%ax
    371b:	66 90                	xchg   %ax,%ax
    371d:	66 90                	xchg   %ax,%ax
    371f:	90                   	nop

00003720 <strcpy>:
#include "user.h"
#include "x86.h"

char*
strcpy(char *s, const char *t)
{
    3720:	f3 0f 1e fb          	endbr32 
    3724:	55                   	push   %ebp
  char *os;

  os = s;
  while((*s++ = *t++) != 0)
    3725:	31 c0                	xor    %eax,%eax
{
    3727:	89 e5                	mov    %esp,%ebp
    3729:	53                   	push   %ebx
    372a:	8b 4d 08             	mov    0x8(%ebp),%ecx
    372d:	8b 5d 0c             	mov    0xc(%ebp),%ebx
  while((*s++ = *t++) != 0)
    3730:	0f b6 14 03          	movzbl (%ebx,%eax,1),%edx
    3734:	88 14 01             	mov    %dl,(%ecx,%eax,1)
    3737:	83 c0 01             	add    $0x1,%eax
    373a:	84 d2                	test   %dl,%dl
    373c:	75 f2                	jne    3730 <strcpy+0x10>
    ;
  return os;
}
    373e:	89 c8                	mov    %ecx,%eax
    3740:	5b                   	pop    %ebx
    3741:	5d                   	pop    %ebp
    3742:	c3                   	ret    
    3743:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    374a:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi

00003750 <strcmp>:

int
strcmp(const char *p, const char *q)
{
    3750:	f3 0f 1e fb          	endbr32 
    3754:	55                   	push   %ebp
    3755:	89 e5                	mov    %esp,%ebp
    3757:	53                   	push   %ebx
    3758:	8b 4d 08             	mov    0x8(%ebp),%ecx
    375b:	8b 55 0c             	mov    0xc(%ebp),%edx
  while(*p && *p == *q)
    375e:	0f b6 01             	movzbl (%ecx),%eax
    3761:	0f b6 1a             	movzbl (%edx),%ebx
    3764:	84 c0                	test   %al,%al
    3766:	75 19                	jne    3781 <strcmp+0x31>
    3768:	eb 26                	jmp    3790 <strcmp+0x40>
    376a:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi
    3770:	0f b6 41 01          	movzbl 0x1(%ecx),%eax
    p++, q++;
    3774:	83 c1 01             	add    $0x1,%ecx
    3777:	83 c2 01             	add    $0x1,%edx
  while(*p && *p == *q)
    377a:	0f b6 1a             	movzbl (%edx),%ebx
    377d:	84 c0                	test   %al,%al
    377f:	74 0f                	je     3790 <strcmp+0x40>
    3781:	38 d8                	cmp    %bl,%al
    3783:	74 eb                	je     3770 <strcmp+0x20>
  return (uchar)*p - (uchar)*q;
    3785:	29 d8                	sub    %ebx,%eax
}
    3787:	5b                   	pop    %ebx
    3788:	5d                   	pop    %ebp
    3789:	c3                   	ret    
    378a:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi
    3790:	31 c0                	xor    %eax,%eax
  return (uchar)*p - (uchar)*q;
    3792:	29 d8                	sub    %ebx,%eax
}
    3794:	5b                   	pop    %ebx
    3795:	5d                   	pop    %ebp
    3796:	c3                   	ret    
    3797:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    379e:	66 90                	xchg   %ax,%ax

000037a0 <strlen>:

uint
strlen(const char *s)
{
    37a0:	f3 0f 1e fb          	endbr32 
    37a4:	55                   	push   %ebp
    37a5:	89 e5                	mov    %esp,%ebp
    37a7:	8b 55 08             	mov    0x8(%ebp),%edx
  int n;

  for(n = 0; s[n]; n++)
    37aa:	80 3a 00             	cmpb   $0x0,(%edx)
    37ad:	74 21                	je     37d0 <strlen+0x30>
    37af:	31 c0                	xor    %eax,%eax
    37b1:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    37b8:	83 c0 01             	add    $0x1,%eax
    37bb:	80 3c 02 00          	cmpb   $0x0,(%edx,%eax,1)
    37bf:	89 c1                	mov    %eax,%ecx
    37c1:	75 f5                	jne    37b8 <strlen+0x18>
    ;
  return n;
}
    37c3:	89 c8                	mov    %ecx,%eax
    37c5:	5d                   	pop    %ebp
    37c6:	c3                   	ret    
    37c7:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    37ce:	66 90                	xchg   %ax,%ax
  for(n = 0; s[n]; n++)
    37d0:	31 c9                	xor    %ecx,%ecx
}
    37d2:	5d                   	pop    %ebp
    37d3:	89 c8                	mov    %ecx,%eax
    37d5:	c3                   	ret    
    37d6:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    37dd:	8d 76 00             	lea    0x0(%esi),%esi

000037e0 <memset>:

void*
memset(void *dst, int c, uint n)
{
    37e0:	f3 0f 1e fb          	endbr32 
    37e4:	55                   	push   %ebp
    37e5:	89 e5                	mov    %esp,%ebp
    37e7:	57                   	push   %edi
    37e8:	8b 55 08             	mov    0x8(%ebp),%edx
}

static inline void
stosb(void *addr, int data, int cnt)
{
  asm volatile("cld; rep stosb" :
    37eb:	8b 4d 10             	mov    0x10(%ebp),%ecx
    37ee:	8b 45 0c             	mov    0xc(%ebp),%eax
    37f1:	89 d7                	mov    %edx,%edi
    37f3:	fc                   	cld    
    37f4:	f3 aa                	rep stos %al,%es:(%edi)
  stosb(dst, c, n);
  return dst;
}
    37f6:	89 d0                	mov    %edx,%eax
    37f8:	5f                   	pop    %edi
    37f9:	5d                   	pop    %ebp
    37fa:	c3                   	ret    
    37fb:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
    37ff:	90                   	nop

00003800 <strchr>:

char*
strchr(const char *s, char c)
{
    3800:	f3 0f 1e fb          	endbr32 
    3804:	55                   	push   %ebp
    3805:	89 e5                	mov    %esp,%ebp
    3807:	8b 45 08             	mov    0x8(%ebp),%eax
    380a:	0f b6 4d 0c          	movzbl 0xc(%ebp),%ecx
  for(; *s; s++)
    380e:	0f b6 10             	movzbl (%eax),%edx
    3811:	84 d2                	test   %dl,%dl
    3813:	75 16                	jne    382b <strchr+0x2b>
    3815:	eb 21                	jmp    3838 <strchr+0x38>
    3817:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    381e:	66 90                	xchg   %ax,%ax
    3820:	0f b6 50 01          	movzbl 0x1(%eax),%edx
    3824:	83 c0 01             	add    $0x1,%eax
    3827:	84 d2                	test   %dl,%dl
    3829:	74 0d                	je     3838 <strchr+0x38>
    if(*s == c)
    382b:	38 d1                	cmp    %dl,%cl
    382d:	75 f1                	jne    3820 <strchr+0x20>
      return (char*)s;
  return 0;
}
    382f:	5d                   	pop    %ebp
    3830:	c3                   	ret    
    3831:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
  return 0;
    3838:	31 c0                	xor    %eax,%eax
}
    383a:	5d                   	pop    %ebp
    383b:	c3                   	ret    
    383c:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi

00003840 <gets>:

char*
gets(char *buf, int max)
{
    3840:	f3 0f 1e fb          	endbr32 
    3844:	55                   	push   %ebp
    3845:	89 e5                	mov    %esp,%ebp
    3847:	57                   	push   %edi
    3848:	56                   	push   %esi
  int i, cc;
  char c;

  for(i=0; i+1 < max; ){
    3849:	31 f6                	xor    %esi,%esi
{
    384b:	53                   	push   %ebx
    384c:	89 f3                	mov    %esi,%ebx
    384e:	83 ec 1c             	sub    $0x1c,%esp
    3851:	8b 7d 08             	mov    0x8(%ebp),%edi
  for(i=0; i+1 < max; ){
    3854:	eb 33                	jmp    3889 <gets+0x49>
    3856:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    385d:	8d 76 00             	lea    0x0(%esi),%esi
    cc = read(0, &c, 1);
    3860:	83 ec 04             	sub    $0x4,%esp
    3863:	8d 45 e7             	lea    -0x19(%ebp),%eax
    3866:	6a 01                	push   $0x1
    3868:	50                   	push   %eax
    3869:	6a 00                	push   $0x0
    386b:	e8 2b 01 00 00       	call   399b <read>
    if(cc < 1)
    3870:	83 c4 10             	add    $0x10,%esp
    3873:	85 c0                	test   %eax,%eax
    3875:	7e 1c                	jle    3893 <gets+0x53>
      break;
    buf[i++] = c;
    3877:	0f b6 45 e7          	movzbl -0x19(%ebp),%eax
    387b:	83 c7 01             	add    $0x1,%edi
    387e:	88 47 ff             	mov    %al,-0x1(%edi)
    if(c == '\n' || c == '\r')
    3881:	3c 0a                	cmp    $0xa,%al
    3883:	74 23                	je     38a8 <gets+0x68>
    3885:	3c 0d                	cmp    $0xd,%al
    3887:	74 1f                	je     38a8 <gets+0x68>
  for(i=0; i+1 < max; ){
    3889:	83 c3 01             	add    $0x1,%ebx
    388c:	89 fe                	mov    %edi,%esi
    388e:	3b 5d 0c             	cmp    0xc(%ebp),%ebx
    3891:	7c cd                	jl     3860 <gets+0x20>
    3893:	89 f3                	mov    %esi,%ebx
      break;
  }
  buf[i] = '\0';
  return buf;
}
    3895:	8b 45 08             	mov    0x8(%ebp),%eax
  buf[i] = '\0';
    3898:	c6 03 00             	movb   $0x0,(%ebx)
}
    389b:	8d 65 f4             	lea    -0xc(%ebp),%esp
    389e:	5b                   	pop    %ebx
    389f:	5e                   	pop    %esi
    38a0:	5f                   	pop    %edi
    38a1:	5d                   	pop    %ebp
    38a2:	c3                   	ret    
    38a3:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
    38a7:	90                   	nop
    38a8:	8b 75 08             	mov    0x8(%ebp),%esi
    38ab:	8b 45 08             	mov    0x8(%ebp),%eax
    38ae:	01 de                	add    %ebx,%esi
    38b0:	89 f3                	mov    %esi,%ebx
  buf[i] = '\0';
    38b2:	c6 03 00             	movb   $0x0,(%ebx)
}
    38b5:	8d 65 f4             	lea    -0xc(%ebp),%esp
    38b8:	5b                   	pop    %ebx
    38b9:	5e                   	pop    %esi
    38ba:	5f                   	pop    %edi
    38bb:	5d                   	pop    %ebp
    38bc:	c3                   	ret    
    38bd:	8d 76 00             	lea    0x0(%esi),%esi

000038c0 <stat>:

int
stat(const char *n, struct stat *st)
{
    38c0:	f3 0f 1e fb          	endbr32 
    38c4:	55                   	push   %ebp
    38c5:	89 e5                	mov    %esp,%ebp
    38c7:	56                   	push   %esi
    38c8:	53                   	push   %ebx
  int fd;
  int r;

  fd = open(n, O_RDONLY);
    38c9:	83 ec 08             	sub    $0x8,%esp
    38cc:	6a 00                	push   $0x0
    38ce:	ff 75 08             	pushl  0x8(%ebp)
    38d1:	e8 ed 00 00 00       	call   39c3 <open>
  if(fd < 0)
    38d6:	83 c4 10             	add    $0x10,%esp
    38d9:	85 c0                	test   %eax,%eax
    38db:	78 2b                	js     3908 <stat+0x48>
    return -1;
  r = fstat(fd, st);
    38dd:	83 ec 08             	sub    $0x8,%esp
    38e0:	ff 75 0c             	pushl  0xc(%ebp)
    38e3:	89 c3                	mov    %eax,%ebx
    38e5:	50                   	push   %eax
    38e6:	e8 f0 00 00 00       	call   39db <fstat>
  close(fd);
    38eb:	89 1c 24             	mov    %ebx,(%esp)
  r = fstat(fd, st);
    38ee:	89 c6                	mov    %eax,%esi
  close(fd);
    38f0:	e8 b6 00 00 00       	call   39ab <close>
  return r;
    38f5:	83 c4 10             	add    $0x10,%esp
}
    38f8:	8d 65 f8             	lea    -0x8(%ebp),%esp
    38fb:	89 f0                	mov    %esi,%eax
    38fd:	5b                   	pop    %ebx
    38fe:	5e                   	pop    %esi
    38ff:	5d                   	pop    %ebp
    3900:	c3                   	ret    
    3901:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    return -1;
    3908:	be ff ff ff ff       	mov    $0xffffffff,%esi
    390d:	eb e9                	jmp    38f8 <stat+0x38>
    390f:	90                   	nop

00003910 <atoi>:

int
atoi(const char *s)
{
    3910:	f3 0f 1e fb          	endbr32 
    3914:	55                   	push   %ebp
    3915:	89 e5                	mov    %esp,%ebp
    3917:	53                   	push   %ebx
    3918:	8b 55 08             	mov    0x8(%ebp),%edx
  int n;

  n = 0;
  while('0' <= *s && *s <= '9')
    391b:	0f be 02             	movsbl (%edx),%eax
    391e:	8d 48 d0             	lea    -0x30(%eax),%ecx
    3921:	80 f9 09             	cmp    $0x9,%cl
  n = 0;
    3924:	b9 00 00 00 00       	mov    $0x0,%ecx
  while('0' <= *s && *s <= '9')
    3929:	77 1a                	ja     3945 <atoi+0x35>
    392b:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
    392f:	90                   	nop
    n = n*10 + *s++ - '0';
    3930:	83 c2 01             	add    $0x1,%edx
    3933:	8d 0c 89             	lea    (%ecx,%ecx,4),%ecx
    3936:	8d 4c 48 d0          	lea    -0x30(%eax,%ecx,2),%ecx
  while('0' <= *s && *s <= '9')
    393a:	0f be 02             	movsbl (%edx),%eax
    393d:	8d 58 d0             	lea    -0x30(%eax),%ebx
    3940:	80 fb 09             	cmp    $0x9,%bl
    3943:	76 eb                	jbe    3930 <atoi+0x20>
  return n;
}
    3945:	89 c8                	mov    %ecx,%eax
    3947:	5b                   	pop    %ebx
    3948:	5d                   	pop    %ebp
    3949:	c3                   	ret    
    394a:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi

00003950 <memmove>:

void*
memmove(void *vdst, const void *vsrc, int n)
{
    3950:	f3 0f 1e fb          	endbr32 
    3954:	55                   	push   %ebp
    3955:	89 e5                	mov    %esp,%ebp
    3957:	57                   	push   %edi
    3958:	8b 45 10             	mov    0x10(%ebp),%eax
    395b:	8b 55 08             	mov    0x8(%ebp),%edx
    395e:	56                   	push   %esi
    395f:	8b 75 0c             	mov    0xc(%ebp),%esi
  char *dst;
  const char *src;

  dst = vdst;
  src = vsrc;
  while(n-- > 0)
    3962:	85 c0                	test   %eax,%eax
    3964:	7e 0f                	jle    3975 <memmove+0x25>
    3966:	01 d0                	add    %edx,%eax
  dst = vdst;
    3968:	89 d7                	mov    %edx,%edi
    396a:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi
    *dst++ = *src++;
    3970:	a4                   	movsb  %ds:(%esi),%es:(%edi)
  while(n-- > 0)
    3971:	39 f8                	cmp    %edi,%eax
    3973:	75 fb                	jne    3970 <memmove+0x20>
  return vdst;
}
    3975:	5e                   	pop    %esi
    3976:	89 d0                	mov    %edx,%eax
    3978:	5f                   	pop    %edi
    3979:	5d                   	pop    %ebp
    397a:	c3                   	ret    

0000397b <fork>:
  name: \
    movl $SYS_ ## name, %eax; \
    int $T_SYSCALL; \
    ret

SYSCALL(fork)
    397b:	b8 01 00 00 00       	mov    $0x1,%eax
    3980:	cd 40                	int    $0x40
    3982:	c3                   	ret    

00003983 <exit>:
SYSCALL(exit)
    3983:	b8 02 00 00 00       	mov    $0x2,%eax
    3988:	cd 40                	int    $0x40
    398a:	c3                   	ret    

0000398b <wait>:
SYSCALL(wait)
    398b:	b8 03 00 00 00       	mov    $0x3,%eax
    3990:	cd 40                	int    $0x40
    3992:	c3                   	ret    

00003993 <pipe>:
SYSCALL(pipe)
    3993:	b8 04 00 00 00       	mov    $0x4,%eax
    3998:	cd 40                	int    $0x40
    399a:	c3                   	ret    

0000399b <read>:
SYSCALL(read)
    399b:	b8 05 00 00 00       	mov    $0x5,%eax
    39a0:	cd 40                	int    $0x40
    39a2:	c3                   	ret    

000039a3 <write>:
SYSCALL(write)
    39a3:	b8 10 00 00 00       	mov    $0x10,%eax
    39a8:	cd 40                	int    $0x40
    39aa:	c3                   	ret    

000039ab <close>:
SYSCALL(close)
    39ab:	b8 15 00 00 00       	mov    $0x15,%eax
    39b0:	cd 40                	int    $0x40
    39b2:	c3                   	ret    

000039b3 <kill>:
SYSCALL(kill)
    39b3:	b8 06 00 00 00       	mov    $0x6,%eax
    39b8:	cd 40                	int    $0x40
    39ba:	c3                   	ret    

000039bb <exec>:
SYSCALL(exec)
    39bb:	b8 07 00 00 00       	mov    $0x7,%eax
    39c0:	cd 40                	int    $0x40
    39c2:	c3                   	ret    

000039c3 <open>:
SYSCALL(open)
    39c3:	b8 0f 00 00 00       	mov    $0xf,%eax
    39c8:	cd 40                	int    $0x40
    39ca:	c3                   	ret    

000039cb <mknod>:
SYSCALL(mknod)
    39cb:	b8 11 00 00 00       	mov    $0x11,%eax
    39d0:	cd 40                	int    $0x40
    39d2:	c3                   	ret    

000039d3 <unlink>:
SYSCALL(unlink)
    39d3:	b8 12 00 00 00       	mov    $0x12,%eax
    39d8:	cd 40                	int    $0x40
    39da:	c3                   	ret    

000039db <fstat>:
SYSCALL(fstat)
    39db:	b8 08 00 00 00       	mov    $0x8,%eax
    39e0:	cd 40                	int    $0x40
    39e2:	c3                   	ret    

000039e3 <link>:
SYSCALL(link)
    39e3:	b8 13 00 00 00       	mov    $0x13,%eax
    39e8:	cd 40                	int    $0x40
    39ea:	c3                   	ret    

000039eb <mkdir>:
SYSCALL(mkdir)
    39eb:	b8 14 00 00 00       	mov    $0x14,%eax
    39f0:	cd 40                	int    $0x40
    39f2:	c3                   	ret    

000039f3 <chdir>:
SYSCALL(chdir)
    39f3:	b8 09 00 00 00       	mov    $0x9,%eax
    39f8:	cd 40                	int    $0x40
    39fa:	c3                   	ret    

000039fb <dup>:
SYSCALL(dup)
    39fb:	b8 0a 00 00 00       	mov    $0xa,%eax
    3a00:	cd 40                	int    $0x40
    3a02:	c3                   	ret    

00003a03 <getpid>:
SYSCALL(getpid)
    3a03:	b8 0b 00 00 00       	mov    $0xb,%eax
    3a08:	cd 40                	int    $0x40
    3a0a:	c3                   	ret    

00003a0b <sbrk>:
SYSCALL(sbrk)
    3a0b:	b8 0c 00 00 00       	mov    $0xc,%eax
    3a10:	cd 40                	int    $0x40
    3a12:	c3                   	ret    

00003a13 <sleep>:
SYSCALL(sleep)
    3a13:	b8 0d 00 00 00       	mov    $0xd,%eax
    3a18:	cd 40                	int    $0x40
    3a1a:	c3                   	ret    

00003a1b <uptime>:
SYSCALL(uptime)
    3a1b:	b8 0e 00 00 00       	mov    $0xe,%eax
    3a20:	cd 40                	int    $0x40
    3a22:	c3                   	ret    

00003a23 <getreadcount>:
SYSCALL(getreadcount)  // OSTEP project
    3a23:	b8 16 00 00 00       	mov    $0x16,%eax
    3a28:	cd 40                	int    $0x40
    3a2a:	c3                   	ret    
    3a2b:	66 90                	xchg   %ax,%ax
    3a2d:	66 90                	xchg   %ax,%ax
    3a2f:	90                   	nop

00003a30 <printint>:
  write(fd, &c, 1);
}

static void
printint(int fd, int xx, int base, int sgn)
{
    3a30:	55                   	push   %ebp
    3a31:	89 e5                	mov    %esp,%ebp
    3a33:	57                   	push   %edi
    3a34:	56                   	push   %esi
    3a35:	53                   	push   %ebx
    3a36:	83 ec 3c             	sub    $0x3c,%esp
    3a39:	89 4d c4             	mov    %ecx,-0x3c(%ebp)
  uint x;

  neg = 0;
  if(sgn && xx < 0){
    neg = 1;
    x = -xx;
    3a3c:	89 d1                	mov    %edx,%ecx
{
    3a3e:	89 45 b8             	mov    %eax,-0x48(%ebp)
  if(sgn && xx < 0){
    3a41:	85 d2                	test   %edx,%edx
    3a43:	0f 89 7f 00 00 00    	jns    3ac8 <printint+0x98>
    3a49:	f6 45 08 01          	testb  $0x1,0x8(%ebp)
    3a4d:	74 79                	je     3ac8 <printint+0x98>
    neg = 1;
    3a4f:	c7 45 bc 01 00 00 00 	movl   $0x1,-0x44(%ebp)
    x = -xx;
    3a56:	f7 d9                	neg    %ecx
  } else {
    x = xx;
  }

  i = 0;
    3a58:	31 db                	xor    %ebx,%ebx
    3a5a:	8d 75 d7             	lea    -0x29(%ebp),%esi
    3a5d:	8d 76 00             	lea    0x0(%esi),%esi
  do{
    buf[i++] = digits[x % base];
    3a60:	89 c8                	mov    %ecx,%eax
    3a62:	31 d2                	xor    %edx,%edx
    3a64:	89 cf                	mov    %ecx,%edi
    3a66:	f7 75 c4             	divl   -0x3c(%ebp)
    3a69:	0f b6 92 e8 55 00 00 	movzbl 0x55e8(%edx),%edx
    3a70:	89 45 c0             	mov    %eax,-0x40(%ebp)
    3a73:	89 d8                	mov    %ebx,%eax
    3a75:	8d 5b 01             	lea    0x1(%ebx),%ebx
  }while((x /= base) != 0);
    3a78:	8b 4d c0             	mov    -0x40(%ebp),%ecx
    buf[i++] = digits[x % base];
    3a7b:	88 14 1e             	mov    %dl,(%esi,%ebx,1)
  }while((x /= base) != 0);
    3a7e:	39 7d c4             	cmp    %edi,-0x3c(%ebp)
    3a81:	76 dd                	jbe    3a60 <printint+0x30>
  if(neg)
    3a83:	8b 4d bc             	mov    -0x44(%ebp),%ecx
    3a86:	85 c9                	test   %ecx,%ecx
    3a88:	74 0c                	je     3a96 <printint+0x66>
    buf[i++] = '-';
    3a8a:	c6 44 1d d8 2d       	movb   $0x2d,-0x28(%ebp,%ebx,1)
    buf[i++] = digits[x % base];
    3a8f:	89 d8                	mov    %ebx,%eax
    buf[i++] = '-';
    3a91:	ba 2d 00 00 00       	mov    $0x2d,%edx

  while(--i >= 0)
    3a96:	8b 7d b8             	mov    -0x48(%ebp),%edi
    3a99:	8d 5c 05 d7          	lea    -0x29(%ebp,%eax,1),%ebx
    3a9d:	eb 07                	jmp    3aa6 <printint+0x76>
    3a9f:	90                   	nop
    3aa0:	0f b6 13             	movzbl (%ebx),%edx
    3aa3:	83 eb 01             	sub    $0x1,%ebx
  write(fd, &c, 1);
    3aa6:	83 ec 04             	sub    $0x4,%esp
    3aa9:	88 55 d7             	mov    %dl,-0x29(%ebp)
    3aac:	6a 01                	push   $0x1
    3aae:	56                   	push   %esi
    3aaf:	57                   	push   %edi
    3ab0:	e8 ee fe ff ff       	call   39a3 <write>
  while(--i >= 0)
    3ab5:	83 c4 10             	add    $0x10,%esp
    3ab8:	39 de                	cmp    %ebx,%esi
    3aba:	75 e4                	jne    3aa0 <printint+0x70>
    putc(fd, buf[i]);
}
    3abc:	8d 65 f4             	lea    -0xc(%ebp),%esp
    3abf:	5b                   	pop    %ebx
    3ac0:	5e                   	pop    %esi
    3ac1:	5f                   	pop    %edi
    3ac2:	5d                   	pop    %ebp
    3ac3:	c3                   	ret    
    3ac4:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
  neg = 0;
    3ac8:	c7 45 bc 00 00 00 00 	movl   $0x0,-0x44(%ebp)
    3acf:	eb 87                	jmp    3a58 <printint+0x28>
    3ad1:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    3ad8:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    3adf:	90                   	nop

00003ae0 <printf>:

// Print to the given fd. Only understands %d, %x, %p, %s.
void
printf(int fd, const char *fmt, ...)
{
    3ae0:	f3 0f 1e fb          	endbr32 
    3ae4:	55                   	push   %ebp
    3ae5:	89 e5                	mov    %esp,%ebp
    3ae7:	57                   	push   %edi
    3ae8:	56                   	push   %esi
    3ae9:	53                   	push   %ebx
    3aea:	83 ec 2c             	sub    $0x2c,%esp
  int c, i, state;
  uint *ap;

  state = 0;
  ap = (uint*)(void*)&fmt + 1;
  for(i = 0; fmt[i]; i++){
    3aed:	8b 75 0c             	mov    0xc(%ebp),%esi
    3af0:	0f b6 1e             	movzbl (%esi),%ebx
    3af3:	84 db                	test   %bl,%bl
    3af5:	0f 84 b4 00 00 00    	je     3baf <printf+0xcf>
  ap = (uint*)(void*)&fmt + 1;
    3afb:	8d 45 10             	lea    0x10(%ebp),%eax
    3afe:	83 c6 01             	add    $0x1,%esi
  write(fd, &c, 1);
    3b01:	8d 7d e7             	lea    -0x19(%ebp),%edi
  state = 0;
    3b04:	31 d2                	xor    %edx,%edx
  ap = (uint*)(void*)&fmt + 1;
    3b06:	89 45 d0             	mov    %eax,-0x30(%ebp)
    3b09:	eb 33                	jmp    3b3e <printf+0x5e>
    3b0b:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
    3b0f:	90                   	nop
    3b10:	89 55 d4             	mov    %edx,-0x2c(%ebp)
    c = fmt[i] & 0xff;
    if(state == 0){
      if(c == '%'){
        state = '%';
    3b13:	ba 25 00 00 00       	mov    $0x25,%edx
      if(c == '%'){
    3b18:	83 f8 25             	cmp    $0x25,%eax
    3b1b:	74 17                	je     3b34 <printf+0x54>
  write(fd, &c, 1);
    3b1d:	83 ec 04             	sub    $0x4,%esp
    3b20:	88 5d e7             	mov    %bl,-0x19(%ebp)
    3b23:	6a 01                	push   $0x1
    3b25:	57                   	push   %edi
    3b26:	ff 75 08             	pushl  0x8(%ebp)
    3b29:	e8 75 fe ff ff       	call   39a3 <write>
    3b2e:	8b 55 d4             	mov    -0x2c(%ebp),%edx
      } else {
        putc(fd, c);
    3b31:	83 c4 10             	add    $0x10,%esp
  for(i = 0; fmt[i]; i++){
    3b34:	0f b6 1e             	movzbl (%esi),%ebx
    3b37:	83 c6 01             	add    $0x1,%esi
    3b3a:	84 db                	test   %bl,%bl
    3b3c:	74 71                	je     3baf <printf+0xcf>
    c = fmt[i] & 0xff;
    3b3e:	0f be cb             	movsbl %bl,%ecx
    3b41:	0f b6 c3             	movzbl %bl,%eax
    if(state == 0){
    3b44:	85 d2                	test   %edx,%edx
    3b46:	74 c8                	je     3b10 <printf+0x30>
      }
    } else if(state == '%'){
    3b48:	83 fa 25             	cmp    $0x25,%edx
    3b4b:	75 e7                	jne    3b34 <printf+0x54>
      if(c == 'd'){
    3b4d:	83 f8 64             	cmp    $0x64,%eax
    3b50:	0f 84 9a 00 00 00    	je     3bf0 <printf+0x110>
        printint(fd, *ap, 10, 1);
        ap++;
      } else if(c == 'x' || c == 'p'){
    3b56:	81 e1 f7 00 00 00    	and    $0xf7,%ecx
    3b5c:	83 f9 70             	cmp    $0x70,%ecx
    3b5f:	74 5f                	je     3bc0 <printf+0xe0>
        printint(fd, *ap, 16, 0);
        ap++;
      } else if(c == 's'){
    3b61:	83 f8 73             	cmp    $0x73,%eax
    3b64:	0f 84 d6 00 00 00    	je     3c40 <printf+0x160>
          s = "(null)";
        while(*s != 0){
          putc(fd, *s);
          s++;
        }
      } else if(c == 'c'){
    3b6a:	83 f8 63             	cmp    $0x63,%eax
    3b6d:	0f 84 8d 00 00 00    	je     3c00 <printf+0x120>
        putc(fd, *ap);
        ap++;
      } else if(c == '%'){
    3b73:	83 f8 25             	cmp    $0x25,%eax
    3b76:	0f 84 b4 00 00 00    	je     3c30 <printf+0x150>
  write(fd, &c, 1);
    3b7c:	83 ec 04             	sub    $0x4,%esp
    3b7f:	c6 45 e7 25          	movb   $0x25,-0x19(%ebp)
    3b83:	6a 01                	push   $0x1
    3b85:	57                   	push   %edi
    3b86:	ff 75 08             	pushl  0x8(%ebp)
    3b89:	e8 15 fe ff ff       	call   39a3 <write>
        putc(fd, c);
      } else {
        // Unknown % sequence.  Print it to draw attention.
        putc(fd, '%');
        putc(fd, c);
    3b8e:	88 5d e7             	mov    %bl,-0x19(%ebp)
  write(fd, &c, 1);
    3b91:	83 c4 0c             	add    $0xc,%esp
    3b94:	6a 01                	push   $0x1
    3b96:	83 c6 01             	add    $0x1,%esi
    3b99:	57                   	push   %edi
    3b9a:	ff 75 08             	pushl  0x8(%ebp)
    3b9d:	e8 01 fe ff ff       	call   39a3 <write>
  for(i = 0; fmt[i]; i++){
    3ba2:	0f b6 5e ff          	movzbl -0x1(%esi),%ebx
        putc(fd, c);
    3ba6:	83 c4 10             	add    $0x10,%esp
      }
      state = 0;
    3ba9:	31 d2                	xor    %edx,%edx
  for(i = 0; fmt[i]; i++){
    3bab:	84 db                	test   %bl,%bl
    3bad:	75 8f                	jne    3b3e <printf+0x5e>
    }
  }
}
    3baf:	8d 65 f4             	lea    -0xc(%ebp),%esp
    3bb2:	5b                   	pop    %ebx
    3bb3:	5e                   	pop    %esi
    3bb4:	5f                   	pop    %edi
    3bb5:	5d                   	pop    %ebp
    3bb6:	c3                   	ret    
    3bb7:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    3bbe:	66 90                	xchg   %ax,%ax
        printint(fd, *ap, 16, 0);
    3bc0:	83 ec 0c             	sub    $0xc,%esp
    3bc3:	b9 10 00 00 00       	mov    $0x10,%ecx
    3bc8:	6a 00                	push   $0x0
    3bca:	8b 5d d0             	mov    -0x30(%ebp),%ebx
    3bcd:	8b 45 08             	mov    0x8(%ebp),%eax
    3bd0:	8b 13                	mov    (%ebx),%edx
    3bd2:	e8 59 fe ff ff       	call   3a30 <printint>
        ap++;
    3bd7:	89 d8                	mov    %ebx,%eax
    3bd9:	83 c4 10             	add    $0x10,%esp
      state = 0;
    3bdc:	31 d2                	xor    %edx,%edx
        ap++;
    3bde:	83 c0 04             	add    $0x4,%eax
    3be1:	89 45 d0             	mov    %eax,-0x30(%ebp)
    3be4:	e9 4b ff ff ff       	jmp    3b34 <printf+0x54>
    3be9:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
        printint(fd, *ap, 10, 1);
    3bf0:	83 ec 0c             	sub    $0xc,%esp
    3bf3:	b9 0a 00 00 00       	mov    $0xa,%ecx
    3bf8:	6a 01                	push   $0x1
    3bfa:	eb ce                	jmp    3bca <printf+0xea>
    3bfc:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
        putc(fd, *ap);
    3c00:	8b 5d d0             	mov    -0x30(%ebp),%ebx
  write(fd, &c, 1);
    3c03:	83 ec 04             	sub    $0x4,%esp
        putc(fd, *ap);
    3c06:	8b 03                	mov    (%ebx),%eax
  write(fd, &c, 1);
    3c08:	6a 01                	push   $0x1
        ap++;
    3c0a:	83 c3 04             	add    $0x4,%ebx
  write(fd, &c, 1);
    3c0d:	57                   	push   %edi
    3c0e:	ff 75 08             	pushl  0x8(%ebp)
        putc(fd, *ap);
    3c11:	88 45 e7             	mov    %al,-0x19(%ebp)
  write(fd, &c, 1);
    3c14:	e8 8a fd ff ff       	call   39a3 <write>
        ap++;
    3c19:	89 5d d0             	mov    %ebx,-0x30(%ebp)
    3c1c:	83 c4 10             	add    $0x10,%esp
      state = 0;
    3c1f:	31 d2                	xor    %edx,%edx
    3c21:	e9 0e ff ff ff       	jmp    3b34 <printf+0x54>
    3c26:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    3c2d:	8d 76 00             	lea    0x0(%esi),%esi
        putc(fd, c);
    3c30:	88 5d e7             	mov    %bl,-0x19(%ebp)
  write(fd, &c, 1);
    3c33:	83 ec 04             	sub    $0x4,%esp
    3c36:	e9 59 ff ff ff       	jmp    3b94 <printf+0xb4>
    3c3b:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
    3c3f:	90                   	nop
        s = (char*)*ap;
    3c40:	8b 45 d0             	mov    -0x30(%ebp),%eax
    3c43:	8b 18                	mov    (%eax),%ebx
        ap++;
    3c45:	83 c0 04             	add    $0x4,%eax
    3c48:	89 45 d0             	mov    %eax,-0x30(%ebp)
        if(s == 0)
    3c4b:	85 db                	test   %ebx,%ebx
    3c4d:	74 17                	je     3c66 <printf+0x186>
        while(*s != 0){
    3c4f:	0f b6 03             	movzbl (%ebx),%eax
      state = 0;
    3c52:	31 d2                	xor    %edx,%edx
        while(*s != 0){
    3c54:	84 c0                	test   %al,%al
    3c56:	0f 84 d8 fe ff ff    	je     3b34 <printf+0x54>
    3c5c:	89 75 d4             	mov    %esi,-0x2c(%ebp)
    3c5f:	89 de                	mov    %ebx,%esi
    3c61:	8b 5d 08             	mov    0x8(%ebp),%ebx
    3c64:	eb 1a                	jmp    3c80 <printf+0x1a0>
          s = "(null)";
    3c66:	bb de 55 00 00       	mov    $0x55de,%ebx
        while(*s != 0){
    3c6b:	89 75 d4             	mov    %esi,-0x2c(%ebp)
    3c6e:	b8 28 00 00 00       	mov    $0x28,%eax
    3c73:	89 de                	mov    %ebx,%esi
    3c75:	8b 5d 08             	mov    0x8(%ebp),%ebx
    3c78:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    3c7f:	90                   	nop
  write(fd, &c, 1);
    3c80:	83 ec 04             	sub    $0x4,%esp
          s++;
    3c83:	83 c6 01             	add    $0x1,%esi
    3c86:	88 45 e7             	mov    %al,-0x19(%ebp)
  write(fd, &c, 1);
    3c89:	6a 01                	push   $0x1
    3c8b:	57                   	push   %edi
    3c8c:	53                   	push   %ebx
    3c8d:	e8 11 fd ff ff       	call   39a3 <write>
        while(*s != 0){
    3c92:	0f b6 06             	movzbl (%esi),%eax
    3c95:	83 c4 10             	add    $0x10,%esp
    3c98:	84 c0                	test   %al,%al
    3c9a:	75 e4                	jne    3c80 <printf+0x1a0>
    3c9c:	8b 75 d4             	mov    -0x2c(%ebp),%esi
      state = 0;
    3c9f:	31 d2                	xor    %edx,%edx
    3ca1:	e9 8e fe ff ff       	jmp    3b34 <printf+0x54>
    3ca6:	66 90                	xchg   %ax,%ax
    3ca8:	66 90                	xchg   %ax,%ax
    3caa:	66 90                	xchg   %ax,%ax
    3cac:	66 90                	xchg   %ax,%ax
    3cae:	66 90                	xchg   %ax,%ax

00003cb0 <free>:
static Header base;
static Header *freep;

void
free(void *ap)
{
    3cb0:	f3 0f 1e fb          	endbr32 
    3cb4:	55                   	push   %ebp
  Header *bp, *p;

  bp = (Header*)ap - 1;
  for(p = freep; !(bp > p && bp < p->s.ptr); p = p->s.ptr)
    3cb5:	a1 80 5f 00 00       	mov    0x5f80,%eax
{
    3cba:	89 e5                	mov    %esp,%ebp
    3cbc:	57                   	push   %edi
    3cbd:	56                   	push   %esi
    3cbe:	53                   	push   %ebx
    3cbf:	8b 5d 08             	mov    0x8(%ebp),%ebx
    3cc2:	8b 10                	mov    (%eax),%edx
  bp = (Header*)ap - 1;
    3cc4:	8d 4b f8             	lea    -0x8(%ebx),%ecx
  for(p = freep; !(bp > p && bp < p->s.ptr); p = p->s.ptr)
    3cc7:	39 c8                	cmp    %ecx,%eax
    3cc9:	73 15                	jae    3ce0 <free+0x30>
    3ccb:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
    3ccf:	90                   	nop
    3cd0:	39 d1                	cmp    %edx,%ecx
    3cd2:	72 14                	jb     3ce8 <free+0x38>
    if(p >= p->s.ptr && (bp > p || bp < p->s.ptr))
    3cd4:	39 d0                	cmp    %edx,%eax
    3cd6:	73 10                	jae    3ce8 <free+0x38>
{
    3cd8:	89 d0                	mov    %edx,%eax
  for(p = freep; !(bp > p && bp < p->s.ptr); p = p->s.ptr)
    3cda:	8b 10                	mov    (%eax),%edx
    3cdc:	39 c8                	cmp    %ecx,%eax
    3cde:	72 f0                	jb     3cd0 <free+0x20>
    if(p >= p->s.ptr && (bp > p || bp < p->s.ptr))
    3ce0:	39 d0                	cmp    %edx,%eax
    3ce2:	72 f4                	jb     3cd8 <free+0x28>
    3ce4:	39 d1                	cmp    %edx,%ecx
    3ce6:	73 f0                	jae    3cd8 <free+0x28>
      break;
  if(bp + bp->s.size == p->s.ptr){
    3ce8:	8b 73 fc             	mov    -0x4(%ebx),%esi
    3ceb:	8d 3c f1             	lea    (%ecx,%esi,8),%edi
    3cee:	39 fa                	cmp    %edi,%edx
    3cf0:	74 1e                	je     3d10 <free+0x60>
    bp->s.size += p->s.ptr->s.size;
    bp->s.ptr = p->s.ptr->s.ptr;
  } else
    bp->s.ptr = p->s.ptr;
    3cf2:	89 53 f8             	mov    %edx,-0x8(%ebx)
  if(p + p->s.size == bp){
    3cf5:	8b 50 04             	mov    0x4(%eax),%edx
    3cf8:	8d 34 d0             	lea    (%eax,%edx,8),%esi
    3cfb:	39 f1                	cmp    %esi,%ecx
    3cfd:	74 28                	je     3d27 <free+0x77>
    p->s.size += bp->s.size;
    p->s.ptr = bp->s.ptr;
  } else
    p->s.ptr = bp;
    3cff:	89 08                	mov    %ecx,(%eax)
  freep = p;
}
    3d01:	5b                   	pop    %ebx
  freep = p;
    3d02:	a3 80 5f 00 00       	mov    %eax,0x5f80
}
    3d07:	5e                   	pop    %esi
    3d08:	5f                   	pop    %edi
    3d09:	5d                   	pop    %ebp
    3d0a:	c3                   	ret    
    3d0b:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi
    3d0f:	90                   	nop
    bp->s.size += p->s.ptr->s.size;
    3d10:	03 72 04             	add    0x4(%edx),%esi
    3d13:	89 73 fc             	mov    %esi,-0x4(%ebx)
    bp->s.ptr = p->s.ptr->s.ptr;
    3d16:	8b 10                	mov    (%eax),%edx
    3d18:	8b 12                	mov    (%edx),%edx
    3d1a:	89 53 f8             	mov    %edx,-0x8(%ebx)
  if(p + p->s.size == bp){
    3d1d:	8b 50 04             	mov    0x4(%eax),%edx
    3d20:	8d 34 d0             	lea    (%eax,%edx,8),%esi
    3d23:	39 f1                	cmp    %esi,%ecx
    3d25:	75 d8                	jne    3cff <free+0x4f>
    p->s.size += bp->s.size;
    3d27:	03 53 fc             	add    -0x4(%ebx),%edx
  freep = p;
    3d2a:	a3 80 5f 00 00       	mov    %eax,0x5f80
    p->s.size += bp->s.size;
    3d2f:	89 50 04             	mov    %edx,0x4(%eax)
    p->s.ptr = bp->s.ptr;
    3d32:	8b 53 f8             	mov    -0x8(%ebx),%edx
    3d35:	89 10                	mov    %edx,(%eax)
}
    3d37:	5b                   	pop    %ebx
    3d38:	5e                   	pop    %esi
    3d39:	5f                   	pop    %edi
    3d3a:	5d                   	pop    %ebp
    3d3b:	c3                   	ret    
    3d3c:	8d 74 26 00          	lea    0x0(%esi,%eiz,1),%esi

00003d40 <malloc>:
  return freep;
}

void*
malloc(uint nbytes)
{
    3d40:	f3 0f 1e fb          	endbr32 
    3d44:	55                   	push   %ebp
    3d45:	89 e5                	mov    %esp,%ebp
    3d47:	57                   	push   %edi
    3d48:	56                   	push   %esi
    3d49:	53                   	push   %ebx
    3d4a:	83 ec 1c             	sub    $0x1c,%esp
  Header *p, *prevp;
  uint nunits;

  nunits = (nbytes + sizeof(Header) - 1)/sizeof(Header) + 1;
    3d4d:	8b 45 08             	mov    0x8(%ebp),%eax
  if((prevp = freep) == 0){
    3d50:	8b 3d 80 5f 00 00    	mov    0x5f80,%edi
  nunits = (nbytes + sizeof(Header) - 1)/sizeof(Header) + 1;
    3d56:	8d 70 07             	lea    0x7(%eax),%esi
    3d59:	c1 ee 03             	shr    $0x3,%esi
    3d5c:	83 c6 01             	add    $0x1,%esi
  if((prevp = freep) == 0){
    3d5f:	85 ff                	test   %edi,%edi
    3d61:	0f 84 a9 00 00 00    	je     3e10 <malloc+0xd0>
    base.s.ptr = freep = prevp = &base;
    base.s.size = 0;
  }
  for(p = prevp->s.ptr; ; prevp = p, p = p->s.ptr){
    3d67:	8b 07                	mov    (%edi),%eax
    if(p->s.size >= nunits){
    3d69:	8b 48 04             	mov    0x4(%eax),%ecx
    3d6c:	39 f1                	cmp    %esi,%ecx
    3d6e:	73 6d                	jae    3ddd <malloc+0x9d>
    3d70:	81 fe 00 10 00 00    	cmp    $0x1000,%esi
    3d76:	bb 00 10 00 00       	mov    $0x1000,%ebx
    3d7b:	0f 43 de             	cmovae %esi,%ebx
  p = sbrk(nu * sizeof(Header));
    3d7e:	8d 0c dd 00 00 00 00 	lea    0x0(,%ebx,8),%ecx
    3d85:	89 4d e4             	mov    %ecx,-0x1c(%ebp)
    3d88:	eb 17                	jmp    3da1 <malloc+0x61>
    3d8a:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi
  for(p = prevp->s.ptr; ; prevp = p, p = p->s.ptr){
    3d90:	8b 10                	mov    (%eax),%edx
    if(p->s.size >= nunits){
    3d92:	8b 4a 04             	mov    0x4(%edx),%ecx
    3d95:	39 f1                	cmp    %esi,%ecx
    3d97:	73 4f                	jae    3de8 <malloc+0xa8>
    3d99:	8b 3d 80 5f 00 00    	mov    0x5f80,%edi
    3d9f:	89 d0                	mov    %edx,%eax
        p->s.size = nunits;
      }
      freep = prevp;
      return (void*)(p + 1);
    }
    if(p == freep)
    3da1:	39 c7                	cmp    %eax,%edi
    3da3:	75 eb                	jne    3d90 <malloc+0x50>
  p = sbrk(nu * sizeof(Header));
    3da5:	83 ec 0c             	sub    $0xc,%esp
    3da8:	ff 75 e4             	pushl  -0x1c(%ebp)
    3dab:	e8 5b fc ff ff       	call   3a0b <sbrk>
  if(p == (char*)-1)
    3db0:	83 c4 10             	add    $0x10,%esp
    3db3:	83 f8 ff             	cmp    $0xffffffff,%eax
    3db6:	74 1b                	je     3dd3 <malloc+0x93>
  hp->s.size = nu;
    3db8:	89 58 04             	mov    %ebx,0x4(%eax)
  free((void*)(hp + 1));
    3dbb:	83 ec 0c             	sub    $0xc,%esp
    3dbe:	83 c0 08             	add    $0x8,%eax
    3dc1:	50                   	push   %eax
    3dc2:	e8 e9 fe ff ff       	call   3cb0 <free>
  return freep;
    3dc7:	a1 80 5f 00 00       	mov    0x5f80,%eax
      if((p = morecore(nunits)) == 0)
    3dcc:	83 c4 10             	add    $0x10,%esp
    3dcf:	85 c0                	test   %eax,%eax
    3dd1:	75 bd                	jne    3d90 <malloc+0x50>
        return 0;
  }
}
    3dd3:	8d 65 f4             	lea    -0xc(%ebp),%esp
        return 0;
    3dd6:	31 c0                	xor    %eax,%eax
}
    3dd8:	5b                   	pop    %ebx
    3dd9:	5e                   	pop    %esi
    3dda:	5f                   	pop    %edi
    3ddb:	5d                   	pop    %ebp
    3ddc:	c3                   	ret    
    if(p->s.size >= nunits){
    3ddd:	89 c2                	mov    %eax,%edx
    3ddf:	89 f8                	mov    %edi,%eax
    3de1:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
      if(p->s.size == nunits)
    3de8:	39 ce                	cmp    %ecx,%esi
    3dea:	74 54                	je     3e40 <malloc+0x100>
        p->s.size -= nunits;
    3dec:	29 f1                	sub    %esi,%ecx
    3dee:	89 4a 04             	mov    %ecx,0x4(%edx)
        p += p->s.size;
    3df1:	8d 14 ca             	lea    (%edx,%ecx,8),%edx
        p->s.size = nunits;
    3df4:	89 72 04             	mov    %esi,0x4(%edx)
      freep = prevp;
    3df7:	a3 80 5f 00 00       	mov    %eax,0x5f80
}
    3dfc:	8d 65 f4             	lea    -0xc(%ebp),%esp
      return (void*)(p + 1);
    3dff:	8d 42 08             	lea    0x8(%edx),%eax
}
    3e02:	5b                   	pop    %ebx
    3e03:	5e                   	pop    %esi
    3e04:	5f                   	pop    %edi
    3e05:	5d                   	pop    %ebp
    3e06:	c3                   	ret    
    3e07:	8d b4 26 00 00 00 00 	lea    0x0(%esi,%eiz,1),%esi
    3e0e:	66 90                	xchg   %ax,%ax
    base.s.ptr = freep = prevp = &base;
    3e10:	c7 05 80 5f 00 00 84 	movl   $0x5f84,0x5f80
    3e17:	5f 00 00 
    base.s.size = 0;
    3e1a:	bf 84 5f 00 00       	mov    $0x5f84,%edi
    base.s.ptr = freep = prevp = &base;
    3e1f:	c7 05 84 5f 00 00 84 	movl   $0x5f84,0x5f84
    3e26:	5f 00 00 
  for(p = prevp->s.ptr; ; prevp = p, p = p->s.ptr){
    3e29:	89 f8                	mov    %edi,%eax
    base.s.size = 0;
    3e2b:	c7 05 88 5f 00 00 00 	movl   $0x0,0x5f88
    3e32:	00 00 00 
    if(p->s.size >= nunits){
    3e35:	e9 36 ff ff ff       	jmp    3d70 <malloc+0x30>
    3e3a:	8d b6 00 00 00 00    	lea    0x0(%esi),%esi
        prevp->s.ptr = p->s.ptr;
    3e40:	8b 0a                	mov    (%edx),%ecx
    3e42:	89 08                	mov    %ecx,(%eax)
    3e44:	eb b1                	jmp    3df7 <malloc+0xb7>
