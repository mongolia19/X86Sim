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
		case 0x04:
				res=op04(cpu);
			break;
		case 0x09:
			res=op09(cpu);
		break;
		case 0x0a:
			res=op0a(cpu);
		break;
		
		default:
				res=opxx(cpu);
			break;
		}
	
	
	
		return res;
	}

private static int op0a(CPU_STATE_S cpu) {
	int src1, src2, dst;
	cpu.cnt |= BCNT_1;
	src1 = get_reg_val_8(cpu, cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg));
	src2 = get_ea_val_8(cpu);
	dst = src1 | src2;
	set_ea_val_8(cpu, dst);
	flags.set_flag_log_byte(cpu, dst);
	//printf( "OR %s, %s\n", byteRegName[ cpu->modrm.bit.rm ], byteRegName[ cpu->modrm.bit.reg ] );
	return cpu.cnt;
}

private static void set_ea_val_8(CPU_STATE_S cpu, int val) {
	// TODO Auto-generated method stub
	int disp16 = MAKE16( cpu.mp.mp[3], cpu.mp.mp[2] );
	int disp8 = cpu.mp.mp[2];
	
	switch( cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.mod))
	{
		case 0x0:
		case 0x1:
		case 0x2:
		{
			int addr = 0;
			switch( cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.rm ) )
			{
				case 0:
					addr += cpu.BX;
					addr += cpu.SI;
					break;
				case 1:
					addr += cpu.BX;
					addr += cpu.DI;
					break;
				case 2:
					addr += cpu.BP;
					addr += cpu.SI;
					break;
				case 3:
					addr += cpu.BP;
					addr += cpu.DI;
					break;
				case 4:
					addr += cpu.SI;
					break;
				case 5:
					addr += cpu.DI;
					break;
				case 6:
					if( 0x0 == cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.mod) )
					{
						addr += disp16;
						cpu.cnt |= BCNT_2 | BCNT_3;
					}
					else
					{
						addr += cpu.BP;
					}
					break;
				case 7:
					addr += cpu.BX;
					break;
			}
			
			if( cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.mod)== 0x1 )
			{
				addr += disp8;
				cpu.cnt |= BCNT_2;
			}
			else if( cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.mod) == 0x2 )
			{
				addr += disp16;
				cpu.cnt |= BCNT_2 | BCNT_3;
			}
			
			cpu.memory.mem[ addr ] = val;
		}
			break;
			
		case 0x3:
		{
			set_reg_val_8( cpu, cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.rm), val );
		}
			break;
	}
	
	return;
}

static int get_ea_val_8( CPU_STATE_S cpu )
{
	int disp16 = MAKE16( cpu.mp.mp[3], cpu.mp.mp[2] );
	int disp8 = cpu.mp.mp[2];
	
	switch( cpu.modrm(CPU_STATE_S.GetBit,CPU_STATE_S.mod) )
	{
		case 0x0:
		case 0x1:
		case 0x2:
		{
			int addr = 0;
			switch( cpu.modrm(CPU_STATE_S.GetBit,CPU_STATE_S.rm ))
			{
				case 0:
					addr += cpu.BX;
					addr += cpu.SI;
					break;
				case 1:
					addr += cpu.BX;
					addr += cpu.DI;
					break;
				case 2:
					addr += cpu.BP;
					addr += cpu.SI;
					break;
				case 3:
					addr += cpu.BP;
					addr += cpu.DI;
					break;
				case 4:
					addr += cpu.SI;
					break;
				case 5:
					addr += cpu.DI;
					break;
				case 6:
					if( 0x0 == cpu.modrm(CPU_STATE_S.GetBit,CPU_STATE_S.mod))
					{
						addr += disp16;
						cpu.cnt |= BCNT_2 | BCNT_3;
					}
					else
					{
						addr += cpu.BP;
					}
					break;
				case 7:
					addr += cpu.BX;
					break;
			}
			
			if( cpu.modrm(CPU_STATE_S.GetBit,CPU_STATE_S.mod) == 0x1 )
			{
				addr += disp8;
				cpu.cnt |= BCNT_2;
			}
			else if( cpu.modrm(CPU_STATE_S.GetBit,CPU_STATE_S.mod) == 0x2 )
			{
				addr += disp16;
				cpu.cnt |= BCNT_2 | BCNT_3;
			}
			
			return cpu.memory.mem[ addr ];
		}
			
			
		case 0x3:
		{
			return get_reg_val_8( cpu, cpu.modrm(CPU_STATE_S.GetBit,CPU_STATE_S.rm) );
		}
			
	}
		
	return 0;
}

private static int op09(CPU_STATE_S cpu) {
	// TODO Auto-generated method stub
	int src1, src2, dst;
	cpu.cnt |= BCNT_1; // modrm
	src1 = get_ea_val_16( cpu );
	src2 = get_reg_val_16( cpu, cpu.modrm(CPU_STATE_S.GetBit,CPU_STATE_S.reg) );
	dst = src1 | src2;
	set_ea_val_16(cpu, dst);
	flags. set_flag_log_word(cpu, dst);
	//printf( "OR %s, %s\n", wordRegName[ cpu->modrm.bit.reg ], wordRegName[ cpu->modrm.bit.rm ] );
	return cpu.cnt;
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
	flags.set_flag_add_word( cpu, src1, src2 );
	return cpu.cnt;
	
}



//ADD 	AL, data8
static int op04( CPU_STATE_S cpu )
{
	int val = cpu.mp.mp[1];
	int AL = get_reg_val_8( cpu, REG_AL );
	cpu.cnt |= BCNT_1;
	//printf( "ADD AL, 0x%02x\n", val );
	set_reg_val_8( cpu, REG_AL, AL + val );
	flags.set_flag_add_byte( cpu, AL, val);
	return cpu.cnt;
}





private static void set_reg_val_8(CPU_STATE_S cpu, int reg, int val) {
	
	// TODO: convert this to a direct lookup table with enum register indexes
		switch( reg )
		{
		case REG_AL: cpu.AX =  val + ( cpu.AX & 0xff00 ); break;
		case REG_CL: cpu.CX =  val + ( cpu.CX & 0xff00 ); break;
		case REG_DL: cpu.DX =  val + ( cpu.DX & 0xff00 ); break;
		case REG_BL: cpu.BX =  val + ( cpu.BX & 0xff00 ); break;
		case REG_AH: cpu.AX = ( ( val << 8 ) + ( cpu.AX & 0xff ) ); break;
		case REG_CH: cpu.CX = ( ( val << 8 ) + ( cpu.CX & 0xff ) ); break;
		case REG_DH: cpu.DX = ( ( val << 8 ) + ( cpu.DX & 0xff ) ); break;
		case REG_BH: cpu.BX = ( (val << 8 ) + ( cpu.BX & 0xff ) ); break;
		}
}

private static int get_reg_val_8(CPU_STATE_S cpu, int reg) {
	// TODO Auto-generated method stub
	int val = 0;
	// @TODO: convert this to a direct lookup table with enum register indexes
	switch( reg )
	{
	case REG_AL: val = cpu.AX & 0xff; break;
	case REG_CL: val = cpu.CX & 0xff; break;
	case REG_DL: val = cpu.DX & 0xff; break;
	case REG_BL: val = cpu.BX & 0xff; break;
	case REG_AH: val = cpu.AX >> 8; break;
	case REG_CH: val = cpu.CX >> 8; break;
	case REG_DH: val = cpu.DX >> 8; break;
	case REG_BH: val = cpu.BX >> 8; break;
	}

	return val;
}

private static void set_ea_val_16(CPU_STATE_S cpu, int val) {
	// TODO Auto-generated method stub
	int disp16 = MAKE16( cpu.mp.mp[3], cpu.mp.mp[2] );
	int disp8 = cpu.mp.mp[2];

	switch( cpu.modrm(CPU_STATE_S.GetBit,CPU_STATE_S.mod ))
	{
		case 0x0:
		case 0x1:
		case 0x2:
		{
			int addr = 0;
			switch( cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.rm) )
			{
				case 0:
					addr += cpu.BX;
					addr += cpu.SI;
					break;
				case 1:
					addr += cpu.BX;
					addr += cpu.DI;
					break;
				case 2:
					addr += cpu.BP;
					addr += cpu.SI;
					break;
				case 3:
					addr += cpu.BP;
					addr += cpu.DI;
					break;
				case 4:
					addr += cpu.SI;
					break;
				case 5:
					addr += cpu.DI;
					break;
				case 6:
					if( 0x0 == cpu.modrm(CPU_STATE_S.GetBit,CPU_STATE_S.mod ) )
					{
						addr += disp16;
						cpu.cnt |= BCNT_2 | BCNT_3;
					}
					else
					{
						addr += cpu.BP;
					}
					break;
				case 7:
					addr += cpu.BX;
					break;
			}
			
			if( cpu.modrm(CPU_STATE_S.GetBit,CPU_STATE_S.mod ) == 0x1 )
			{
				addr += disp8;
				cpu.cnt |= BCNT_2;
			}
			else if( cpu.modrm(CPU_STATE_S.GetBit,CPU_STATE_S.mod ) == 0x2 )
			{
				addr += disp16;
				cpu.cnt |= BCNT_2 | BCNT_3;
			}
			
			cpu.memory.mem[ addr ] = HI8( val );
			cpu.memory.mem[ addr+1 ] = LO8( val );
			
		}
			break;
			
		case 0x3:
		{
			set_reg_val_16( cpu, cpu.modrm(CPU_STATE_S.GetBit,CPU_STATE_S.rm), val );
		}
			break;
	}
	
	return;
}

private static void set_reg_val_16(CPU_STATE_S cpu, int reg, int val) {
	
	// TODO: convert this to a direct lookup table with enum register indexes
		switch( reg )
		{
			case REG_AX: cpu.AX = val; break;
			case REG_CX: cpu.CX = val; break;
			case REG_DX: cpu.DX = val; break;
			case REG_BX: cpu.BX = val; break;
			case REG_SP: cpu.SP = val; break;
			case REG_BP: cpu.BP = val; break;
			case REG_SI: cpu.SI = val; break;
			case REG_DI: cpu.DI = val; break;
		}
}

private static int LO8(int w) {
	// TODO Auto-generated method stub
	return		((w)&0xFF);
	
}

private static int HI8(int w) {
	// TODO Auto-generated method stub
	
	return ((w >> 8) & 0xFF);
}

private static int get_reg_val_16(CPU_STATE_S cpu, int reg) {
	
	int val = 0;
	// @TODO: convert this to a direct lookup table with enum register indexes
	switch( reg )
	{
	case REG_AX: val = cpu.AX; break;
	case REG_CX: val = cpu.CX; break;
	case REG_DX: val = cpu.DX; break;
	case REG_BX: val = cpu.BX; break;
	case REG_SP: val = cpu.SP; break;
	case REG_BP: val = cpu.BP; break;
	case REG_SI: val = cpu.SI; break;
	case REG_DI: val = cpu.DI; break;
	}

	return val;
}

private static int get_ea_val_16(CPU_STATE_S cpu) {
	// TODO Auto-generated method stub
	int disp16 = MAKE16( cpu.mp.mp[3], cpu.mp.mp[2] );
	int disp8 = cpu.mp.mp[2];

	
	
	
	
	switch (cpu.modrm(CPU_STATE_S.GetBit,CPU_STATE_S.mod)){
	case 0x00:
	case 0x01:
	case 0x02:
	{
		int addr = 0;
		switch( cpu.modrm(CPU_STATE_S.GetBit,CPU_STATE_S.rm) )
		{
			case 0:
				addr += cpu.BX;
				addr += cpu.SI;
				break;
			case 1:
				addr += cpu.BX;
				addr += cpu.DI;
				break;
			case 2:
				addr += cpu.BP;
				addr += cpu.SI;
				break;
			case 3:
				addr += cpu.BP;
				addr += cpu.DI;
				break;
			case 4:
				addr += cpu.SI;
				break;
			case 5:
				addr += cpu.DI;
				break;
			case 6:
				if( 0x0 == cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.mod))
				{
					addr += disp16;
					cpu.cnt |= BCNT_2 | BCNT_3;
				}
				else
				{
					addr += cpu.BP;
				}
				break;
			case 7:
				addr += cpu.BX;
				break;
		}

		if( cpu.modrm(CPU_STATE_S.GetBit,CPU_STATE_S.mod) == 0x1 )
		{
			addr += disp8;
			cpu.cnt |= BCNT_2;
		}
		else if( cpu.modrm(CPU_STATE_S.GetBit,CPU_STATE_S.mod)== 0x2 )
		{
			addr += disp16;
			cpu.cnt |= BCNT_2 | BCNT_3;
		}

		return MAKE16( cpu.memory.mem[ addr ], cpu.memory.mem[ addr + 1 ] );
	}
	case 0x03:
	{
		return get_reg_val_16( cpu, cpu.modrm(CPU_STATE_S.GetBit,CPU_STATE_S.rm ));
	}
	
	}
	return 0;
}


private static int MAKE16(int h, int l) {
	return (l | h << 8);
}

}
