package com.nine.viewpaperdemo;

public class CPU_STATE_S 
{
	public static final int SET_BIT=1;
	public static final int CLEAR_BIT=0;
	
	
	public static final int CF=0; // carry flag - set if there was a carry from or borrow to the most significant bit during the last result calculation
	public static final int flag1_reserved=1;
	public static final int PF=2; // parity flag - set if parity ( the number of "1" bits ) in the lwo-order byte of the result is even
	public static final int flag3_reserved=3;
	public static final int AF=4; // adjust flag - set if there was a carry from or borrow to bits 0-3 in the AL register
	public static final int flag5_reserved=5;
	public static final int ZF=6; // zero flag - set if the result is zero
	public static final int SF=7; // sign flag - set if the most significant bit of the result is set
	public static final int TF=8; // trap flag - if set then single-step interrupt will occur after the next instruction
	public static final int IF=9; // interrupt enable flag - setting this bit enables maskable interupts
	public static final int DF=10; // direction flag - if set then string manipulation instructions will auto-descrement index registers. if cleared then the index registers will be auto-incremented
	public static final int OF=11; // overflow flag - set if the result is too large positive number, or is to small negative number to fit into destination operand
	public static final int IOPL0=12; // I/O privilege level (always 1,1 on 8086 and 80186)
	public static final int IOPL1=13;
	public static final int NT=14; // nested task flag ( always 1 on 8086 and 80186)
	public static final int flag15_reserved=15; // always 1 on 8086 and 8086. should be 0 above
	// general registers
	int AX;// Accumulator register for operands and results data.
	int BX;// Base resigster. pointer to data in the DS segment.
	int CX;// Count register for string and loop operations.
	int DX;// Data register. I/O pointer.
	
	// index registers
	int SI; // Pointer to data in the segment pointed to by the DS register; source pointer for string operations. (Source index register. Used for string and memory array copying)
	int DI; // Pointer to data (or destination) in the segment pointed to by the ES register; destination pointer for string operations. (Destination index register. Used for string, memory array copying and setting and for far pointer addressing with ES)
		
	// pointer registers
	int BP; // Pointer to data on the stack (in the SS segment). (Stack Base pointer register. Holds the base address of the stack)
	int SP; // Stack pointer (in the SS segment). (Stack pointer register.Holds the top address of the stack)
		
	// flags register
	int IP; // Index Pointer. Holds the offset of the next instruction. It can only be read
		
	
	int flags;
	public void flags(int SetOrClear,int bit)
	{
		int Mask=0;
		if(SetOrClear==0)
		{
			Mask=~(1<<bit);
			this.flags&=Mask;
			
		}
		else
		{
			Mask=(1<<bit);	
			this.flags|=Mask;
		}
		
		
		
		
	}
	// 16-bit segment registers
	int CS; // code segment (Holds the Code segment in which your program runs. Changing its value might make the computer hang.)
	int DS; // data segment (Holds the Data segment that your program accesses. Changing its value might give erroneous data.)
	int SS; // stack segment (Holds the Stack segment your program uses. Sometimes has the same value as DS.  Changing its value can give unpredictable results, mostly data related.)
	
	// These are extra segment registers available for far pointer addressing like video memory and such.
	int ES; // data segment
	int FS; // data segment
	int GS; // data segment
	
	Memory memory;
	MP mp;
	int cnt;
	
	int modrm;
	public static final int rm=0x7;
	public static final int reg=0x38;//0011 1000
	public static final int mod=0xc0;//1100 0000
	public static final int SetBit=1;
	public static final int GetBit=0;
	public int modrm(int SetOrGet,int bit)
	{
		 if(SetOrGet==GetBit)//get bits
		 {
			 if(bit==rm)
			 {
				 return ((this.modrm)&rm);
			 }
			 else if(bit==reg)
			 {
				 return (((this.modrm)&reg)>>3);
				 
				 
				 
			 }
			 else
			 {
				 return (((this.modrm)&mod)>>6);
				 
				 
			 }
		 }
		 else
		 {
			 this.modrm&=bit;
			 
			 return this.modrm;
			 
		 }
		 
	}
	public int  cpu_get_mem_u8(CPU_STATE_S cpu, int segment, int offset)
	{
		// @TODO: change these to support segmented memory when that comes
		//uint32_t addr = EAADDR(segment, offset);
		return cpu.memory.mem[offset];
		
	}
	public void cpu_set_mem_u8( CPU_STATE_S cpu, int segment, int offset, int value )
	{
		//uint32_t addr = EAADDR(segment, offset);
		cpu.memory.mem[ offset ] = value;
	}
	
	public int cpu_get_mem_u16( CPU_STATE_S cpu, int segment, int offset )
	{
		//uint32_t addr = EAADDR(segment, offset);
		return (cpu.memory.mem[offset]);
	}
	
	public void cpu_set_mem_u16( CPU_STATE_S cpu, int segment, int offset, int value )
	{
		//uint32_t addr = EAADDR(segment, offset);
		cpu.memory.mem[ offset ] = value;
	}
	
	public void cpu_push( CPU_STATE_S cpu, int value )
	{
		cpu.SP -= 2;
		cpu_set_mem_u16( cpu, cpu.SS, cpu.SP, value );
	}

	int cpu_pop( CPU_STATE_S cpu )
	{
		int sp = cpu_get_mem_u16( cpu, cpu.SS, cpu.SP );
		cpu.SP += 2;
		return sp;
	}
	
 	public void cpu_reset( CPU_STATE_S cpu )
	{
//		memset( cpu, 0, sizeof( cpu_state_s ) );
		
		cpu.memory = new Memory();
		cpu.mp = new MP();
		//memset( cpu->memory, 0, MEMSIZE );
		
		//memset( &cpu->memory[MEMOFFSET_VIDEO], ' ', 80 * 25 );
		
		cpu.SP = 0x100;
		cpu.flags(CPU_STATE_S.SET_BIT,IOPL0);// IOPL = 0x3;
		cpu.flags(CPU_STATE_S.SET_BIT,IOPL1);// IOPL = 0x3;
		
		cpu.flags(CPU_STATE_S.SET_BIT,NT);// NT = 1;
		cpu.flags(CPU_STATE_S.SET_BIT,flag15_reserved);// flag15_reserved = 1;
		
		
	}
	
	public int cpu_execute( CPU_STATE_S cpu )
	{
		cpu.cnt = 0;
		return opcodes.op_execute(cpu);
	}
	
	void cpu_create( CPU_STATE_S cpu )
	{
		//*cpu = malloc( sizeof( cpu_state_s ) );
		cpu=new CPU_STATE_S();
		cpu_reset(cpu );
	}
	public static void cpu_destroy( CPU_STATE_S cpu )
	{
		//free( (*cpu)->memory );
		//free( *cpu );
		cpu = null;
	}
	
	
	
}
