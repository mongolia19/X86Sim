package com.nine.viewpaperdemo;

public  class cpu8086 {

	private static final int MEMSIZE = 250;

	public static int cpu_get_mem_u8( CPU_STATE_S cpu, int segment, int offset )
	{
		// @TODO: change these to support segmented memory when that comes
		//uint32_t addr = EAADDR(segment, offset);
		return cpu.memory.mem[ offset ];
	}

	public static void cpu_set_mem_u8( CPU_STATE_S cpu, int segment, int offset, int value )
	{
		//uint32_t addr = EAADDR(segment, offset);
		cpu.memory.mem[ offset ] = value;
	}

	public static int cpu_get_mem_u16( CPU_STATE_S cpu, int segment, int offset )
	{
		//uint32_t addr = EAADDR(segment, offset);
		return (cpu.memory.mem[offset]);
	}

	public static void cpu_set_mem_u16( CPU_STATE_S cpu, int segment, int offset, int value )
	{
		//uint32_t addr = EAADDR(segment, offset);
		cpu.memory.mem[ offset ] = value;
	}

	public static void cpu_push( CPU_STATE_S cpu, int value )
	{
		cpu.SP -= 2;
		cpu_set_mem_u16( cpu, cpu.SS, cpu.SP, value );
	}

	public static int cpu_pop( CPU_STATE_S cpu )
	{
		int sp = cpu_get_mem_u16( cpu, cpu.SS, cpu.SP );
		cpu.SP += 2;
		return sp;
	}

	public static	void cpu_reset( CPU_STATE_S cpu )
	{
		//memset( cpu, 0, sizeof( cpu_state_s ) );
		
		cpu.memory.mem = new int[MEMSIZE];
		cpu.mp.mp= cpu.memory.mem;
		//memset( cpu.memory, 0, MEMSIZE );
		
		///video reset not implimented
		//memset( &cpu->memory[MEMOFFSET_VIDEO], ' ', 80 * 25 );
		
		cpu.SP = 0x100;
		cpu.flags(CPU_STATE_S.SET_BIT, CPU_STATE_S.IOPL1);
		cpu.flags(CPU_STATE_S.SET_BIT, CPU_STATE_S.IOPL0);
		cpu.flags(CPU_STATE_S.SET_BIT, CPU_STATE_S.NT);
		cpu.flags(CPU_STATE_S.SET_BIT,CPU_STATE_S.flag15_reserved);
		
		
		
	}

	public static int cpu_execute( CPU_STATE_S cpu )
	{
		cpu.cnt = 0;
		return opcodes.op_execute( cpu );
	}

	public static	void cpu_create( CPU_STATE_S cpu )
	{
		
		cpu_reset( cpu );
	}

	public static	void cpu_destroy( CPU_STATE_S cpu )
	{
		 cpu.memory =null;
		cpu=null ;
		//*cpu = NULL;
	}
	
	
}
