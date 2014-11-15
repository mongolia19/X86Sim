package com.nine.viewpaperdemo;

import android.provider.Settings.System;
import android.widget.Toast;

public class opcodes {
	
	public static final int MEMSIZE =0x10000;
	public static final int MEMOFFSET_VIDEO =0x8000;
	
	// 16bit reg
	public static final int REG_AX=0;
	public static final int REG_CX=1;
	public static final int REG_DX=2;
	public static final int REG_BX=3;
	public static final int REG_SP=4;
	public static final int REG_BP=5;
	public static final int REG_SI=6;
	public static final int REG_DI=7;

	// 8bit reg
	public static final int REG_AL=0;
	public static final int REG_CL=1;
	public static final int REG_DL=2;
	public static final int REG_BL=3;
	public static final int REG_AH=4;
	public static final int REG_CH=5;
	public static final int REG_DH=6;
	public static final int REG_BH=7;

	// seg
	public static final int REG_ES=0;
	public static final int REG_CS=1;
	public static final int REG_SS=2;
	public static final int REG_DS=3;
	
	public static final int UNHANDLED_OPCODE = 0xff;
	
public static final int BCNT_OPCODE =(1<< 0);
private static final int BCNT_1 = (1<< 1);
private static final int BCNT_2	=( 1 << 2 );
private static final int BCNT_3=( 1 << 3 );
private static final int BCNT_4=( 1 << 4 );
private static final int BCNT_5	=( 1 << 5 );
private static final int BCNT_6=( 1 << 6 );



public static int  op_execute( CPU_STATE_S cpu )
	{
	
		int bit_index, bit_counter;
		int opcode = cpu.mp.mp[0];
		int skip = 0;
		int preIP = cpu.IP;
		cpu.cnt = BCNT_OPCODE; // start off the count with the one byte opcode
		cpu.modrm = cpu.mp.mp[1];
		bit_counter = op86_opcodes( opcode, cpu );
		if( bit_counter == UNHANDLED_OPCODE )
		{
			return UNHANDLED_OPCODE;
		// @TODO: convert to a lookup table with 256 entries
		}
		for( bit_index = 0; bit_index < 8; ++bit_index )
		{
			skip += ( bit_counter >> ( 7 - bit_index ) ) & 1;
		}
		//Toast.makeText(, text, duration)	("skip for opcode %x is %d [ addr %04x -> %04x ]\n", opcode, skip, preIP, cpu->IP + skip);
		
		return skip;



	}

public static int op86_opcodes(int opcode, CPU_STATE_S cpu) 
	{
		int res; 
		// TODO Auto-generated method stub
		switch (opcode) {
		case 0x01:
				res=op01(cpu);
			break;
			
		default:
				res=opxx(cpu);
			break;
		}
	
	
	
		return res;
	}

private static int opxx(CPU_STATE_S cpu) {
	// TODO Auto-generated method stub
	return UNHANDLED_OPCODE;
}

private static int op01(CPU_STATE_S cpu) {
	// TODO Auto-generated method stub
	int src1, src2;
	cpu.cnt |= BCNT_1; // modrm
	//printf("ADD rm16, reg16\n");
	src1 = get_ea_val_16( cpu );
	src2 = get_reg_val_16( cpu, cpu.modrm(CPU_STATE_S.GetBit,CPU_STATE_S.reg) );
	set_ea_val_16( cpu, src1 + src2 );
	set_flag_add_word( cpu, src1, src2 );
	return cpu.cnt;
	
}

private static int get_ea_val_16(CPU_STATE_S cpu) {
	// TODO Auto-generated method stub
	int disp16 = MAKE16( cpu.mp.mp[3], cpu.mp.mp[2] );
	int disp8 = cpu.mp.mp[2];

	
	int val=0;
	
	
	switch (key) {
	case value:
		
		break;

	default:
		break;
	}
	return 0;
}


private static int MAKE16(int h, int l) {
	return (l | h << 8);
}

}
