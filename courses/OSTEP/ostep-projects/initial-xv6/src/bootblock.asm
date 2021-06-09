
bootblock.o:     file format elf32-i386


Disassembly of section .text:

00007c00 <start>:
# with %cs=0 %ip=7c00.

.code16                       # Assemble for 16-bit mode
.globl start
start:
  cli                         # BIOS enabled interrupts; disable
    7c00:	fa                   	cli    

  # Zero data segment registers DS, ES, and SS.
  xorw    %ax,%ax             # Set %ax to zero
    7c01:	31 c0                	xor    %eax,%eax
  movw    %ax,%ds             # -> Data Segment
    7c03:	8e d8                	mov    %eax,%ds
  movw    %ax,%es             # -> Extra Segment
    7c05:	8e c0                	mov    %eax,%es
  movw    %ax,%ss             # -> Stack Segment
    7c07:	8e d0                	mov    %eax,%ss

00007c09 <seta20.1>:

  # Physical address line A20 is tied to zero so that the first PCs 
  # with 2 MB would run software that assumed 1 MB.  Undo that.
seta20.1:
  inb     $0x64,%al               # Wait for not busy
    7c09:	e4 64                	in     $0x64,%al
  testb   $0x2,%al
    7c0b:	a8 02                	test   $0x2,%al
  jnz     seta20.1
    7c0d:	75 fa                	jne    7c09 <seta20.1>

  movb    $0xd1,%al               # 0xd1 -> port 0x64
    7c0f:	b0 d1                	mov    $0xd1,%al
  outb    %al,$0x64
    7c11:	e6 64                	out    %al,$0x64

00007c13 <seta20.2>:

seta20.2:
  inb     $0x64,%al               # Wait for not busy
    7c13:	e4 64                	in     $0x64,%al
  testb   $0x2,%al
    7c15:	a8 02                	test   $0x2,%al
  jnz     seta20.2
    7c17:	75 fa                	jne    7c13 <seta20.2>

  movb    $0xdf,%al               # 0xdf -> port 0x60
    7c19:	b0 df                	mov    $0xdf,%al
  outb    %al,$0x60
    7c1b:	e6 60                	out    %al,$0x60

  # Switch from real to protected mode.  Use a bootstrap GDT that makes
  # virtual addresses map directly to physical addresses so that the
  # effective memory map doesn't change during the transition.
  lgdt    gdtdesc
    7c1d:	0f 01 16             	lgdtl  (%esi)
    7c20:	78 7c                	js     7c9e <readsect+0xe>
  movl    %cr0, %eax
    7c22:	0f 20 c0             	mov    %cr0,%eax
  orl     $CR0_PE, %eax
    7c25:	66 83 c8 01          	or     $0x1,%ax
  movl    %eax, %cr0
    7c29:	0f 22 c0             	mov    %eax,%cr0

//PAGEBREAK!
  # Complete the transition to 32-bit protected mode by using a long jmp
  # to reload %cs and %eip.  The segment descriptors are set up with no
  # translation, so that the mapping is still the identity mapping.
  ljmp    $(SEG_KCODE<<3), $start32
    7c2c:	ea                   	.byte 0xea
    7c2d:	31 7c 08 00          	xor    %edi,0x0(%eax,%ecx,1)

00007c31 <start32>:

.code32  # Tell assembler to generate 32-bit code now.
start32:
  # Set up the protected-mode data segment registers
  movw    $(SEG_KDATA<<3), %ax    # Our data segment selector
    7c31:	66 b8 10 00          	mov    $0x10,%ax
  movw    %ax, %ds                # -> DS: Data Segment
    7c35:	8e d8                	mov    %eax,%ds
  movw    %ax, %es                # -> ES: Extra Segment
    7c37:	8e c0                	mov    %eax,%es
  movw    %ax, %ss                # -> SS: Stack Segment
    7c39:	8e d0                	mov    %eax,%ss
  movw    $0, %ax                 # Zero segments not ready for use
    7c3b:	66 b8 00 00          	mov    $0x0,%ax
  movw    %ax, %fs                # -> FS
    7c3f:	8e e0                	mov    %eax,%fs
  movw    %ax, %gs                # -> GS
    7c41:	8e e8                	mov    %eax,%gs

  # Set up the stack pointer and call into C.
  movl    $start, %esp
    7c43:	bc 00 7c 00 00       	mov    $0x7c00,%esp
  call    bootmain
    7c48:	e8 fc 00 00 00       	call   7d49 <bootmain>

  # If bootmain returns (it shouldn't), trigger a Bochs
  # breakpoint if running under Bochs, then loop.
  movw    $0x8a00, %ax            # 0x8a00 -> port 0x8a00
    7c4d:	66 b8 00 8a          	mov    $0x8a00,%ax
  movw    %ax, %dx
    7c51:	66 89 c2             	mov    %ax,%dx
  outw    %ax, %dx
    7c54:	66 ef                	out    %ax,(%dx)
  movw    $0x8ae0, %ax            # 0x8ae0 -> port 0x8a00
    7c56:	66 b8 e0 8a          	mov    $0x8ae0,%ax
  outw    %ax, %dx
    7c5a:	66 ef                	out    %ax,(%dx)

00007c5c <spin>:
spin:
  jmp     spin
    7c5c:	eb fe                	jmp    7c5c <spin>
    7c5e:	66 90                	xchg   %ax,%ax

00007c60 <gdt>:
	...
    7c68:	ff                   	(bad)  
    7c69:	ff 00                	incl   (%eax)
    7c6b:	00 00                	add    %al,(%eax)
    7c6d:	9a cf 00 ff ff 00 00 	lcall  $0x0,$0xffff00cf
    7c74:	00                   	.byte 0x0
    7c75:	92                   	xchg   %eax,%edx
    7c76:	cf                   	iret   
	...

00007c78 <gdtdesc>:
    7c78:	17                   	pop    %ss
    7c79:	00 60 7c             	add    %ah,0x7c(%eax)
	...

00007c7e <waitdisk>:
  entry();
}

void
waitdisk(void)
{
    7c7e:	f3 0f 1e fb          	endbr32 
static inline uchar
inb(ushort port)
{
  uchar data;

  asm volatile("in %1,%0" : "=a" (data) : "d" (port));
    7c82:	ba f7 01 00 00       	mov    $0x1f7,%edx
    7c87:	ec                   	in     (%dx),%al
  // Wait for disk ready.
  while((inb(0x1F7) & 0xC0) != 0x40)
    7c88:	83 e0 c0             	and    $0xffffffc0,%eax
    7c8b:	3c 40                	cmp    $0x40,%al
    7c8d:	75 f8                	jne    7c87 <waitdisk+0x9>
    ;
}
    7c8f:	c3                   	ret    

00007c90 <readsect>:

// Read a single sector at offset into dst.
void
readsect(void *dst, uint offset)
{
    7c90:	f3 0f 1e fb          	endbr32 
    7c94:	55                   	push   %ebp
    7c95:	89 e5                	mov    %esp,%ebp
    7c97:	57                   	push   %edi
    7c98:	53                   	push   %ebx
    7c99:	8b 5d 0c             	mov    0xc(%ebp),%ebx
  // Issue command.
  waitdisk();
    7c9c:	e8 dd ff ff ff       	call   7c7e <waitdisk>
}

static inline void
outb(ushort port, uchar data)
{
  asm volatile("out %0,%1" : : "a" (data), "d" (port));
    7ca1:	b8 01 00 00 00       	mov    $0x1,%eax
    7ca6:	ba f2 01 00 00       	mov    $0x1f2,%edx
    7cab:	ee                   	out    %al,(%dx)
    7cac:	ba f3 01 00 00       	mov    $0x1f3,%edx
    7cb1:	89 d8                	mov    %ebx,%eax
    7cb3:	ee                   	out    %al,(%dx)
  outb(0x1F2, 1);   // count = 1
  outb(0x1F3, offset);
  outb(0x1F4, offset >> 8);
    7cb4:	89 d8                	mov    %ebx,%eax
    7cb6:	c1 e8 08             	shr    $0x8,%eax
    7cb9:	ba f4 01 00 00       	mov    $0x1f4,%edx
    7cbe:	ee                   	out    %al,(%dx)
  outb(0x1F5, offset >> 16);
    7cbf:	89 d8                	mov    %ebx,%eax
    7cc1:	c1 e8 10             	shr    $0x10,%eax
    7cc4:	ba f5 01 00 00       	mov    $0x1f5,%edx
    7cc9:	ee                   	out    %al,(%dx)
  outb(0x1F6, (offset >> 24) | 0xE0);
    7cca:	89 d8                	mov    %ebx,%eax
    7ccc:	c1 e8 18             	shr    $0x18,%eax
    7ccf:	83 c8 e0             	or     $0xffffffe0,%eax
    7cd2:	ba f6 01 00 00       	mov    $0x1f6,%edx
    7cd7:	ee                   	out    %al,(%dx)
    7cd8:	b8 20 00 00 00       	mov    $0x20,%eax
    7cdd:	ba f7 01 00 00       	mov    $0x1f7,%edx
    7ce2:	ee                   	out    %al,(%dx)
  outb(0x1F7, 0x20);  // cmd 0x20 - read sectors

  // Read data.
  waitdisk();
    7ce3:	e8 96 ff ff ff       	call   7c7e <waitdisk>
  asm volatile("cld; rep insl" :
    7ce8:	8b 7d 08             	mov    0x8(%ebp),%edi
    7ceb:	b9 80 00 00 00       	mov    $0x80,%ecx
    7cf0:	ba f0 01 00 00       	mov    $0x1f0,%edx
    7cf5:	fc                   	cld    
    7cf6:	f3 6d                	rep insl (%dx),%es:(%edi)
  insl(0x1F0, dst, SECTSIZE/4);
}
    7cf8:	5b                   	pop    %ebx
    7cf9:	5f                   	pop    %edi
    7cfa:	5d                   	pop    %ebp
    7cfb:	c3                   	ret    

00007cfc <readseg>:

// Read 'count' bytes at 'offset' from kernel into physical address 'pa'.
// Might copy more than asked.
void
readseg(uchar* pa, uint count, uint offset)
{
    7cfc:	f3 0f 1e fb          	endbr32 
    7d00:	55                   	push   %ebp
    7d01:	89 e5                	mov    %esp,%ebp
    7d03:	57                   	push   %edi
    7d04:	56                   	push   %esi
    7d05:	53                   	push   %ebx
    7d06:	83 ec 0c             	sub    $0xc,%esp
    7d09:	8b 5d 08             	mov    0x8(%ebp),%ebx
    7d0c:	8b 75 10             	mov    0x10(%ebp),%esi
  uchar* epa;

  epa = pa + count;
    7d0f:	89 df                	mov    %ebx,%edi
    7d11:	03 7d 0c             	add    0xc(%ebp),%edi

  // Round down to sector boundary.
  pa -= offset % SECTSIZE;
    7d14:	89 f0                	mov    %esi,%eax
    7d16:	25 ff 01 00 00       	and    $0x1ff,%eax
    7d1b:	29 c3                	sub    %eax,%ebx

  // Translate from bytes to sectors; kernel starts at sector 1.
  offset = (offset / SECTSIZE) + 1;
    7d1d:	c1 ee 09             	shr    $0x9,%esi
    7d20:	83 c6 01             	add    $0x1,%esi

  // If this is too slow, we could read lots of sectors at a time.
  // We'd write more to memory than asked, but it doesn't matter --
  // we load in increasing order.
  for(; pa < epa; pa += SECTSIZE, offset++)
    7d23:	39 df                	cmp    %ebx,%edi
    7d25:	76 1a                	jbe    7d41 <readseg+0x45>
    readsect(pa, offset);
    7d27:	83 ec 08             	sub    $0x8,%esp
    7d2a:	56                   	push   %esi
    7d2b:	53                   	push   %ebx
    7d2c:	e8 5f ff ff ff       	call   7c90 <readsect>
  for(; pa < epa; pa += SECTSIZE, offset++)
    7d31:	81 c3 00 02 00 00    	add    $0x200,%ebx
    7d37:	83 c6 01             	add    $0x1,%esi
    7d3a:	83 c4 10             	add    $0x10,%esp
    7d3d:	39 df                	cmp    %ebx,%edi
    7d3f:	77 e6                	ja     7d27 <readseg+0x2b>
}
    7d41:	8d 65 f4             	lea    -0xc(%ebp),%esp
    7d44:	5b                   	pop    %ebx
    7d45:	5e                   	pop    %esi
    7d46:	5f                   	pop    %edi
    7d47:	5d                   	pop    %ebp
    7d48:	c3                   	ret    

00007d49 <bootmain>:
{
    7d49:	f3 0f 1e fb          	endbr32 
    7d4d:	55                   	push   %ebp
    7d4e:	89 e5                	mov    %esp,%ebp
    7d50:	57                   	push   %edi
    7d51:	56                   	push   %esi
    7d52:	53                   	push   %ebx
    7d53:	83 ec 10             	sub    $0x10,%esp
  readseg((uchar*)elf, 4096, 0);
    7d56:	6a 00                	push   $0x0
    7d58:	68 00 10 00 00       	push   $0x1000
    7d5d:	68 00 00 01 00       	push   $0x10000
    7d62:	e8 95 ff ff ff       	call   7cfc <readseg>
  if(elf->magic != ELF_MAGIC)
    7d67:	83 c4 10             	add    $0x10,%esp
    7d6a:	81 3d 00 00 01 00 7f 	cmpl   $0x464c457f,0x10000
    7d71:	45 4c 46 
    7d74:	75 21                	jne    7d97 <bootmain+0x4e>
  ph = (struct proghdr*)((uchar*)elf + elf->phoff);
    7d76:	a1 1c 00 01 00       	mov    0x1001c,%eax
    7d7b:	8d 98 00 00 01 00    	lea    0x10000(%eax),%ebx
  eph = ph + elf->phnum;
    7d81:	0f b7 35 2c 00 01 00 	movzwl 0x1002c,%esi
    7d88:	c1 e6 05             	shl    $0x5,%esi
    7d8b:	01 de                	add    %ebx,%esi
  for(; ph < eph; ph++){
    7d8d:	39 f3                	cmp    %esi,%ebx
    7d8f:	72 15                	jb     7da6 <bootmain+0x5d>
  entry();
    7d91:	ff 15 18 00 01 00    	call   *0x10018
}
    7d97:	8d 65 f4             	lea    -0xc(%ebp),%esp
    7d9a:	5b                   	pop    %ebx
    7d9b:	5e                   	pop    %esi
    7d9c:	5f                   	pop    %edi
    7d9d:	5d                   	pop    %ebp
    7d9e:	c3                   	ret    
  for(; ph < eph; ph++){
    7d9f:	83 c3 20             	add    $0x20,%ebx
    7da2:	39 de                	cmp    %ebx,%esi
    7da4:	76 eb                	jbe    7d91 <bootmain+0x48>
    pa = (uchar*)ph->paddr;
    7da6:	8b 7b 0c             	mov    0xc(%ebx),%edi
    readseg(pa, ph->filesz, ph->off);
    7da9:	83 ec 04             	sub    $0x4,%esp
    7dac:	ff 73 04             	pushl  0x4(%ebx)
    7daf:	ff 73 10             	pushl  0x10(%ebx)
    7db2:	57                   	push   %edi
    7db3:	e8 44 ff ff ff       	call   7cfc <readseg>
    if(ph->memsz > ph->filesz)
    7db8:	8b 4b 14             	mov    0x14(%ebx),%ecx
    7dbb:	8b 43 10             	mov    0x10(%ebx),%eax
    7dbe:	83 c4 10             	add    $0x10,%esp
    7dc1:	39 c1                	cmp    %eax,%ecx
    7dc3:	76 da                	jbe    7d9f <bootmain+0x56>
      stosb(pa + ph->filesz, 0, ph->memsz - ph->filesz);
    7dc5:	01 c7                	add    %eax,%edi
    7dc7:	29 c1                	sub    %eax,%ecx
}

static inline void
stosb(void *addr, int data, int cnt)
{
  asm volatile("cld; rep stosb" :
    7dc9:	b8 00 00 00 00       	mov    $0x0,%eax
    7dce:	fc                   	cld    
    7dcf:	f3 aa                	rep stos %al,%es:(%edi)
               "=D" (addr), "=c" (cnt) :
               "0" (addr), "1" (cnt), "a" (data) :
               "memory", "cc");
}
    7dd1:	eb cc                	jmp    7d9f <bootmain+0x56>
