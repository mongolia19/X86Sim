package com.nine.viewpaperdemo;

public class flags {
public static	void set_flag_add_word( CPU_STATE_S cpu, int v1, int v2 )
	{
		int dst = (int)v1 + (int)v2;

		set_psz_flags_word( cpu, v1 + v2 );

		cpu.flags(( ( dst & 0xffff0000 ) != 0 ),CPU_STATE_S.CF) ;
		cpu.flags(( ( ( dst ^ v1 ) & ( dst ^ v2 ) & 0x8000 ) != 0 ), CPU_STATE_S.OF);
		cpu.flags(( ( ( v1 ^ v2 ^ dst ) & 0x10 ) != 0 ), CPU_STATE_S.AF);
		
	}
public static void set_flag_add_byte( CPU_STATE_S cpu, int v1, int v2 )
{
	int dst = v1 + v2;

	set_psz_flags_byte( cpu, v1 + v2 );
	
	cpu.flags(( ( dst & 0xff00) != 0 ),CPU_STATE_S.CF) ;
	cpu.flags((  ( ( dst ^ v1 ) & ( dst ^ v2 ) & 0x80 ) != 0), CPU_STATE_S.OF);
	cpu.flags((( ( v1 ^ v2 ^ dst ) & 0x10 ) != 0 ), CPU_STATE_S.AF);
	
}
private static void set_psz_flags_byte(CPU_STATE_S cpu, int val) {
	int bit_index, bit_count=0;

	cpu.flags( ( 0 == val ),CPU_STATE_S.ZF);
	cpu.flags(  ( 0 != ( val & 0x80 ) ),CPU_STATE_S.SF);
	
	// TODO: convert to a lookup table with 256 entries
	for( bit_index = 0; bit_index < 8; ++bit_index )
		bit_count = ( val >> ( 7 - bit_index ) ) & 1;
	cpu.flags(( ( bit_count & 1 ) > 0 ), CPU_STATE_S.PF);
	
}
private static void set_psz_flags_word(CPU_STATE_S cpu, int val) {
	
	int bit_index, bit_count=0;
	
	cpu.flags(( 0 == ( val & 0xffff ) ),CPU_STATE_S.ZF) ;
	cpu.flags(( 0 != ( val & 0x8000 ) ),CPU_STATE_S.SF);
	// TODO: convert to a lookup table with 256 entries
	for( bit_index = 0; bit_index < 16; ++bit_index )
		{bit_count = ( val >> ( 15 - bit_index ) ) & 1;}
	
	cpu.flags(( ( bit_count & 1 ) > 0 ), CPU_STATE_S.PF);
	
}

//modify		define		un-define	values
//o..szapc 	o..sz.pc 	.....a.. 	o......c
public static void set_flag_log_word( CPU_STATE_S cpu, int v1 )
{
	set_psz_flags_word( cpu, v1 );
	
	cpu.flags(CPU_STATE_S.CLEAR_BIT,CPU_STATE_S.CF);
	cpu.flags(CPU_STATE_S.CLEAR_BIT, CPU_STATE_S.OF);
}
public static void set_flag_log_byte(CPU_STATE_S cpu, int v1) {
	// TODO Auto-generated method stub
	set_psz_flags_byte( cpu, v1 );
	cpu.flags(CPU_STATE_S.CLEAR_BIT,CPU_STATE_S.CF);
	cpu.flags(CPU_STATE_S.CLEAR_BIT, CPU_STATE_S.OF);
	
}



}
