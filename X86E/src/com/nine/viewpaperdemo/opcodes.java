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
		//Toast.makeText(, text, duration)	("skip for opcode %x is %d [ addr %04x . %04x ]\n", opcode, skip, preIP, cpu.IP + skip);
		
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
		case 0x19:
			res=op19(cpu);
		break;
		case 0x20:
			res=op20(cpu);
		break;
		case 0x21:
			res=op21(cpu);
		break;
		case 0x29:
			res=op29(cpu);
		break;
		case 0x2c:
			res=op2c(cpu);
		break;
		case 0x31:
			res=op31(cpu);
		break;
		case 0x39:
			res=op39(cpu);
		break;
		case 0x3c:
			res=op3c(cpu);
		break;
		case 0x40:
			res=op40(cpu);
		break;
		case 0x48:
			res=op48(cpu);
		case 0x50:
			res=op50(cpu);
		break;
		case 0x58:
			res=op58(cpu);
		break;
		case 0x72:
			res=op72(cpu);
		break;
		case 0x74:
			res=op74(cpu);
		break;
		case 0x75:
			res=op75(cpu);
		break;
		case 0x76:
			res=op76(cpu);
		break;
		case 0x77:
			res=op77(cpu);
		break;
		case 0x79:
			res=op80(cpu);
		break;
		case 0x81:
			res=op81(cpu);
		break;
		case 0x83:
			res=op83(cpu);
		break;
		case 0x86:
			res=op86(cpu);
		break;
		case 0x87:
			res=op87(cpu);
		break;
		case 0x88:
			res=op88(cpu);
		break;
		case 0x89:
			res=op89(cpu);
		break;
		case 0x8a:
			res=op8a(cpu);
		break;
		case 0x8b:
			res=op8b(cpu);
		break;
		case 0x90:
			res=op90(cpu);
		break;
		case 0xb0:
			res=opb0(cpu);
		break;
		case 0xb8:
			res=opb8(cpu);
		break;
		case 0xc3:
			res=opc3(cpu);
		break;
		case 0xc4:
			res=opc4(cpu);
		break;
		case 0xc7:
			res=opc7(cpu);
		break;
		case 0xd3:
			res=opd3(cpu);
		break;
		case 0xe0:
			res=op87(cpu);
		break;
		case 0xe8:
			res=ope8(cpu);
		break;
		case 0xeb:
			res=opeb(cpu);
		break;
		case 0xf4:
			res=opf4(cpu);
		break;
		case 0xf7:
			res=opf7(cpu);
		break;
		case 0xf8:
			res=opf8(cpu);
		break;
		case 0xf9:
			res=opf9(cpu);
		break;
		case 0xfe:
			res=opfe(cpu);
		break;
		default:
				res=opxx(cpu);
			break;
		}
	
	
	
		return res;
	}


//OR reg8, rm8
private static int op0a(CPU_STATE_S cpu) {
	int src1, src2, dst;
	cpu.cnt |= BCNT_1;
	src1 = get_reg_val_8(cpu, cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg));
	src2 = get_ea_val_8(cpu);
	dst = src1 | src2;
	set_ea_val_8(cpu, dst);
	flags.set_flag_log_byte(cpu, dst);
	//printf( "OR %s, %s\n", byteRegName[ cpu.modrm.bit.rm ], byteRegName[ cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) ] );
	return cpu.cnt;
}

//SBB rm16, reg16
static int op19( CPU_STATE_S cpu )
{
	int src1, src2, src3, dst;
	cpu.cnt |= BCNT_1; // modrm
	src1 = get_ea_val_16( cpu );
	src2 = get_reg_val_16( cpu, cpu.modrm(CPU_STATE_S.GetBit,CPU_STATE_S. reg ));
	src3 = cpu.get_flag_bit(CPU_STATE_S.CF);// bit.CF;
	dst = src1 - src2 - src3;
	set_ea_val_16( cpu, dst );
	flags.set_flag_sbb_word( cpu, src1, src2, src3 );
	//printf( "SBB rm16, reg16\n");
	return cpu.cnt;
}


//AND 	rm8, reg8
static int op20( CPU_STATE_S cpu )
{
	int src1, src2, dst;
	cpu.cnt |= BCNT_1; // modrm
	src1 = get_ea_val_8(cpu);
	src2 = get_reg_val_8(cpu, cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg));
	dst = src1 & src2;
	set_ea_val_8(cpu, dst);
	flags.set_flag_log_byte( cpu, dst );
	//printf("AND %s, %s\n", byteRegName[ cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) ], "???" );
	return cpu.cnt;
}

//AND rm16, reg16
static int op21( CPU_STATE_S cpu )
{
	int src1, src2, dst;
	cpu.cnt |= BCNT_1; // modrm
	src1 = get_ea_val_16(cpu);
	src2 = get_reg_val_16(cpu, cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg));
	dst = src1 & src2;
	set_ea_val_16(cpu, dst);
	flags.set_flag_log_word(cpu, dst );
	//printf("AND %s, %s\n", wordRegName[ cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) ], "???" );
	return cpu.cnt;
}

//SUB 	rm16, reg16
static int op29( CPU_STATE_S cpu )
{
	int src1, src2, dst;
	cpu.cnt |= BCNT_1; // modrm
	src1 = get_ea_val_16(cpu);
	src2 = get_reg_val_16(cpu, cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg));
	dst = src1 - src2;
	set_ea_val_16( cpu, dst );
	flags.set_flag_sub_word( cpu, src1, src2 );
	//printf( "SUB %s %s\n", wordRegName[ cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) ], "???" );
	return cpu.cnt;
}

//SUB 	AL, data8
static int op2c( CPU_STATE_S cpu )
{
	int val = cpu.mp.mp[1];
	int AL = get_reg_val_8( cpu, REG_AL );
	cpu.cnt |= BCNT_1; // src2
	set_reg_val_8(cpu, REG_AL, AL - val);
	flags. set_flag_sub_byte(cpu, AL, val);
	//printf( "ADD AL, 0x%02x\n", val );
	return cpu.cnt;
}
//TODO 2c finished
///
//XOR rm16, reg16
static int op31( CPU_STATE_S cpu )
{
	int src1, src2, dst;
	cpu.cnt |= BCNT_1; // modrm
	src1 = get_ea_val_16( cpu );
	src2 = get_reg_val_16( cpu, cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) );
	dst = src1 ^ src2;
	set_ea_val_16(cpu, dst);
	flags.set_flag_log_word(cpu, dst);
	//printf( "XOR %s, %s\n", wordRegName[ cpu.modrm.bit.rm ], wordRegName[ cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) ] );
	return cpu.cnt;
}

//CMP 	rm16, reg16
static int op39( CPU_STATE_S cpu )
{	
	int src1, src2;
	cpu.cnt |= BCNT_1; // modrm
	src1 = get_ea_val_16( cpu );
	src2 = get_reg_val_16( cpu, cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) );
	flags.set_flag_sub_word( cpu, src1, src2);
	//printf( "CMP %s, %s\n", wordRegName[ cpu.modrm.bit.rm ], wordRegName[ cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) ] );
	return cpu.cnt;
}

//CMP 	AL 	Ib
static int op3c( CPU_STATE_S cpu )
{
	int src1, src2;
	src1 = get_reg_val_8(cpu, REG_AL);
	src2 = cpu.mp.mp[1];
	cpu.cnt |= BCNT_1; // src2
	flags.set_flag_sub_byte(cpu, src1, src2);
	return cpu.cnt;
}

//INC 	reg16
static int op40( CPU_STATE_S cpu )
{
	int rm = MODRM_RM(cpu.mp.mp[0]);
	int src = get_reg_val_16(cpu, rm);
	set_reg_val_16( cpu, rm, src + 1 );
	// we can't modify the CF flag
	int cf =cpu.get_flag_bit(CPU_STATE_S.CF) ;
	flags.set_flag_add_word( cpu, src, 1 );
	cpu.flags(( cf != 0 ), CPU_STATE_S.CF);
	//cpu.flags.bit.CF = ( cf != 0 );
	//printf( "INC %s\n", wordRegName[rm]);
	return cpu.cnt;
}



//DEC 	reg16
static int op48( CPU_STATE_S cpu )
{
	int rm = MODRM_RM(cpu.mp.mp[0]);
	int src = get_reg_val_16(cpu, rm);
	set_reg_val_16( cpu, rm, src - 1 );
	// we can't modify the CF flag
	int cf = cpu.get_flag_bit(CPU_STATE_S.CF) ;
	flags.set_flag_sub_word( cpu, src, 1 );
	cpu.flags(( cf != 0 ), CPU_STATE_S.CF);
	//printf( "DEC %s\n", wordRegName[rm]);
	return cpu.cnt;
}

//PUSH
static int op50( CPU_STATE_S cpu )
{
	int rm = MODRM_RM(cpu.mp.mp[0]);
	//printf( "PUSH %s\n", wordRegName[rm] );	
	if( rm == REG_SP /* && CPU_PUSH_FIRST?? */ )
	{
		cpu8086.cpu_push( cpu, cpu.SP - 2 );
	}
	else
	{
		int value = get_reg_val_16( cpu, rm );
		cpu8086. cpu_push( cpu, value );
	}
	return cpu.cnt;
}

//POP 
static int op58( CPU_STATE_S cpu )
{
	int rm = MODRM_RM(cpu.mp.mp[0]);
	int val = cpu8086.cpu_pop( cpu );
	set_reg_val_16( cpu, rm, val );
	//printf( "POP %s\n", wordRegName[rm] );	
	return cpu.cnt;
}

//JB/JNAE/JC imm8
static int op72( CPU_STATE_S cpu )
{
	int mem = ADD_S8( cpu.IP + 2, cpu.mp.mp[1] );
	cpu.cnt |= BCNT_1;
	//printf("JC %xh\n", mem);
	if( (cpu.get_flag_bit(CPU_STATE_S.CF)>0) )
	{
		cpu.IP = mem;
		cpu.mp.mp[mem] = cpu.memory.mem[ mem ];
		return 0; // jump
	}
	return cpu.cnt; // no jump
}



//JZ/JE imm8
static int op74( CPU_STATE_S cpu )
{
	int mem = ADD_S8( cpu.IP + 2, cpu.mp.mp[1] );
	cpu.cnt |= BCNT_1; // modrm
	//printf("JZ %xh\n", mem);
	
	if(cpu.get_flag_bit(CPU_STATE_S.ZF)>0  )
	{
		cpu.IP = mem;
		cpu.mp.mp [mem]= cpu.memory.mem[ mem ];
		return 0; // jump
	}
	return cpu.cnt; // no jump
}

//JNZ/JNE imm8
static int op75( CPU_STATE_S cpu )
{
	int mem = ADD_S8( cpu.IP + 2, cpu.mp.mp[1] );
	cpu.cnt |= BCNT_1;
	//printf("JNZ %xh\n", mem);
	if( (cpu.get_flag_bit(CPU_STATE_S.ZF)==0) )
	{
		cpu.IP = mem;
		cpu.mp.mp[mem] = cpu.memory.mem[ mem ];
		return 0; // jump
	}
	return cpu.cnt; // no jump	
}

//JBE/JNA imm8
static int op76( CPU_STATE_S cpu )
{
	int mem = ADD_S8( cpu.IP + 2, cpu.mp.mp[1] );
	cpu.cnt |= BCNT_1;
	//printf("JBE 0x%02x\n", mem);
	
	if(cpu.get_flag_bit(CPU_STATE_S.CF)>0 ||cpu.get_flag_bit(CPU_STATE_S.ZF)>0 )// cpu.flags.bit.CF || cpu.flags.bit.ZF )
	{
		cpu.IP = mem;
		cpu.mp.mp[mem] = cpu.memory.mem[ mem ];
		return 0; // jump
	}
	return cpu.cnt; // no jump
}

//JA imm8
static int op77( CPU_STATE_S cpu )
{
	int mem = ADD_S8( cpu.IP + 2, cpu.mp.mp[1] );
	cpu.cnt |= BCNT_1;
	//printf("JA 0x%02x\n", mem);
	//cpu.get_flag_bit(CPU_STATE_S.CF)
	if( !( cpu.get_flag_bit(CPU_STATE_S.CF)>0 ||cpu.get_flag_bit(CPU_STATE_S.ZF)>0 ) )
	{
		cpu.IP = mem;
		cpu.mp.mp[mem] = cpu.memory.mem[ mem ];
		return 0; // jump
	}
	return cpu.cnt; // no jump
}

//JNS imm8
static int op79( CPU_STATE_S cpu )
{
	int mem = ADD_S8( cpu.IP + 2, cpu.mp.mp[1] );
	cpu.cnt |= BCNT_1;
	//printf("JNS 0x%02x\n", mem);
	//(cpu.get_flag_bit(CPU_STATE_S.SF)>0)
	if( !(cpu.get_flag_bit(CPU_STATE_S.SF)>0) )
	{
		cpu.IP = mem;
		cpu.mp.mp[mem] = cpu.memory.mem[ mem ];///Here maybe a problem
		return 0; // jump
	}
	return cpu.cnt; // no jump
}

//Various rm8, imm8
static int op80( CPU_STATE_S cpu )
{
	int src1, src2, dst, offset;
	cpu.cnt |= BCNT_1;
	switch( cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) )
	{
		case 0x1: // OR rm8, imm8
			src1 = get_ea_val_8(cpu);
			offset = bit_count( cpu.cnt );
			src2 = cpu.mp.mp[offset];
			cpu.cnt |= (1<<(offset));
			dst = src1 | src2;
			set_ea_val_8( cpu, dst);
			flags.set_flag_log_byte( cpu, dst );
			//printf("ORB(80) %xh %xh\n", src1, src2);
		break;
		
		default:
			//printf("reg = %x\n", cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) );
			return UNHANDLED_OPCODE;
		
	}
	return cpu.cnt;
}

//Various rm16, imm16
static int op81( CPU_STATE_S cpu )
{
	int src1, src2, offset;
	cpu.cnt |= BCNT_1;
	src1 = get_ea_val_16( cpu );
	offset = bit_count( cpu.cnt );
	src2 = MAKE16( cpu.mp.mp[offset+1], cpu.mp.mp[offset] );;
	cpu.cnt |= (1<<(offset)) | (1<<(offset+1));
	switch( cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) )
	{
		case 0x7: // CMP rm16, imm16
			//printf("CMP(81) (%xh) %xh\n", src1, src2);
			flags.set_flag_sub_word( cpu, src1, src2 );
		break;
		
		default:
			//printf("reg = %x\n", cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) );
			return UNHANDLED_OPCODE;
		
	}
	return cpu.cnt;
}

private static int bit_count(int Byte) {
	
	
		int bit_index, count = 0;
		for( bit_index = 0; bit_index < 8; ++bit_index )
			count += ( Byte >> ( 7 - bit_index ) ) & 1;
		return count;

}

//Various rm16, imm8
static int op83( CPU_STATE_S cpu )
{
	int src1, src2, dst, offset;
	cpu.cnt |= BCNT_1; // modrm
	src1 = get_ea_val_16(cpu);
	offset = bit_count( cpu.cnt );
	src2 = MAKE_S16( cpu.mp.mp[offset] );
	cpu.cnt |= (1<<(offset));
	
	switch( cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) ) 
	{
		case 0x0: // ADD rm16, imm8
			//printf("ADD %s(0x%04x) %xh\n", wordRegName[cpu.modrm.bit.rm], src1, src2);
			dst = src1 + src2;
			set_ea_val_16(cpu, dst);
			flags.set_flag_add_word(cpu, src1, src2);
			break;
			
		case 0x1: // OR rm16, imm8
			return UNHANDLED_OPCODE;
		
			
		case 0x2: // ADC rm16, imm8
			//printf("ADC %s(0x%04x) %xh\n", wordRegName[cpu.modrm.bit.rm], src1, src2);
			//cpu.get_flag_bit(CPU_STATE_S.CF)
			dst = src1 +  src2 + cpu.get_flag_bit(CPU_STATE_S.CF);
			set_ea_val_16( cpu, dst );
			flags.set_flag_adc_word( cpu, src1, src2,  cpu.get_flag_bit(CPU_STATE_S.CF));
			break;
			
		case 0x3: // SBB rm16, imm8
			return UNHANDLED_OPCODE;
			
			
		case 0x4: // AND rm16, imm8 
			//printf("AND %s(0x%04x) %xh\n", wordRegName[cpu.modrm.bit.rm], src1, src2);
			dst = src1 & src2;
			set_ea_val_16( cpu, dst );
			flags.set_flag_log_word(cpu, dst);
			break;
			
		case 0x5: // SUB rm16, imm8
			//printf("SUB %s(0x%04x) %xh\n", wordRegName[cpu.modrm.bit.rm], src1, src2);
			dst = src1 - src2;
			set_ea_val_16( cpu, dst );
			flags.set_flag_sub_word(cpu, src1, src2);
			break;
			
		case 0x6: // XOR /m16, imm8
			//printf("XOR %s(0x%04x) %xh\n", wordRegName[cpu.modrm.bit.rm], src1, src2);
			dst = src1 ^ src2;
			set_ea_val_16( cpu, dst );
			flags.set_flag_log_word(cpu, dst);
			break;
			
		case 0x7: // CMP rm16, imm8
			//printf("CMP %s(0x%04x) %xh\n", wordRegName[cpu.modrm.bit.rm], src1, src2);
			flags.set_flag_sub_word( cpu, src1, src2 );
			break;
	}
	return cpu.cnt;
}


//XCHG  rm8, reg8
static int op86( CPU_STATE_S cpu )
{
	int src1 = get_ea_val_8(cpu);
	int src2 = get_reg_val_8(cpu, cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg));
	cpu.cnt |= BCNT_1; // modrm
	set_ea_val_8( cpu, src2);
	set_reg_val_8(cpu, cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg), src1);
	//printf( "XCHG %s, %s\n", byteRegName[ cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) ], "???" );
	return cpu.cnt;
}

//XCHG  rm16, reg16
static int op87( CPU_STATE_S cpu )
{
	int src1 = get_ea_val_16(cpu);
	int src2 = get_reg_val_16(cpu, cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg));
	cpu.cnt |= BCNT_1; // modrm
	set_ea_val_16(cpu, src2);
	set_reg_val_16(cpu, cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg), src1);
	//printf( "XCHG %s, %s\n", wordRegName[ cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) ], "???" );
	return cpu.cnt;
}

//MOV rm8, reg8
static int op88( CPU_STATE_S cpu )
{
	int val = get_reg_val_8( cpu, cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) );
	cpu.cnt |= BCNT_1; // modrm
	//printf("MOV [BX+DI](%xh) %s(0x%x)\n", cpu.BX + cpu.DI, byteRegName[ cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) ], val);
	set_ea_val_8(cpu, val);
	return cpu.cnt;
}

//MOV rm16, reg16
static int op89( CPU_STATE_S cpu )
{
	int val = get_reg_val_16( cpu, cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) );
	int addr = MAKE16( cpu.mp.mp[3], cpu.mp.mp[2] );
	cpu.cnt |= BCNT_1;
	//printf("MOV [%xh] %s(0x%x)\n", addr, wordRegName[ cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) ], val);
	set_ea_val_16( cpu, val );
	return cpu.cnt;
}

//MOV reg8, rm8
static int op8a( CPU_STATE_S cpu )
{
	int val = get_ea_val_8(cpu);
	cpu.cnt |= BCNT_1; // modrm
	set_reg_val_8(cpu, cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg), val);
	//printf("MOV %s 0x%02x \n", byteRegName[ cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) ], val );
	return cpu.cnt;
}

//MOV reg16, rm16
static int op8b( CPU_STATE_S cpu )
{
	int val = get_ea_val_16(cpu);
	cpu.cnt |= BCNT_1;
	set_reg_val_16(cpu, cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg), val );
	//printf("MOV %s 0x%04x \n", wordRegName[ cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) ], val );
	return cpu.cnt;
}

//XCHG AX, reg16
static int op90( CPU_STATE_S cpu )
{
	int rm = MODRM_RM(cpu.mp.mp[0]);
	int val = cpu.AX;
	val = cpu.AX;
	cpu.AX = get_reg_val_16( cpu, rm );
	set_reg_val_16( cpu, rm, val );
	//printf( "NOP/XCHG AX, %s\n", wordRegName[ rm ] );
	return cpu.cnt;
}


//MOV reg8, imm8
static int opb0( CPU_STATE_S cpu )
{
	int rm = MODRM_RM( cpu.mp.mp[0] );
	int val = cpu.mp.mp[1];
	cpu.cnt |= BCNT_1;
	set_reg_val_8(cpu, rm, val);
	//printf("MOV %s, $%x\n", byteRegName[rm], val);
	return cpu.cnt;
}


//MOV reg16, imm16
static int opb8( CPU_STATE_S cpu )
{
	int rm = MODRM_RM( cpu.mp.mp[0] );
	int val =  MAKE16( cpu.mp.mp[2], cpu.mp.mp[1] );
	cpu.cnt |= BCNT_1 | BCNT_2;
	set_reg_val_16(cpu, rm, val);
	//printf("MOV %s, $%x\n", wordRegName[rm], val);
	return cpu.cnt;
}

//RETN
static int opc3( CPU_STATE_S cpu )
{
	int addr =cpu8086.cpu_pop( cpu );
	//printf( "RETN\n");
	cpu.IP = addr;
	cpu.mp.mp[addr] = cpu.memory.mem[ addr ];
	return 0;
}

//LES reg16, rm16
static int opc4( CPU_STATE_S cpu )
{
	int val1, val2;
	cpu.cnt |= BCNT_1 | BCNT_2 | BCNT_3 | BCNT_4 | BCNT_5;
	val1 = MAKE16( cpu.mp.mp[3], cpu.mp.mp[2] );
	val2 = MAKE16( cpu.mp.mp[5], cpu.mp.mp[4] );
	set_reg_val_16(cpu, cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg), val1);
	cpu.ES = val2;
	return cpu.cnt;
}

//MOV(W) rm16, data16
static int opc7( CPU_STATE_S cpu )
{
	// @TODO: set the offsets based on modrm displacements instead of hard coding the 5,4 indexes here
	int val = MAKE16( cpu.mp.mp[5], cpu.mp.mp[4] );
	cpu.cnt |= BCNT_1 | BCNT_4 | BCNT_5 ;
	set_ea_val_16(cpu, val);
	return cpu.cnt;
}

//various Ev CL
static int opd3( CPU_STATE_S cpu )
{
	int src, dst=0;
	int cl = get_reg_val_8(cpu, REG_CL );
	cpu.cnt |= BCNT_1; // modrm
	src = get_ea_val_16(cpu);
	//cl &= 0x1f;
	if( cl == 0x0 )
		return cpu.cnt;
	switch( cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) )
	{
		case 0x0: // ROL
			dst = ( src << ( cl & 15 ) ) | ( src >> ( 16 - ( cl & 15 ) ) );
			cpu.flags(( ( dst & 1 ) != 0 ),CPU_STATE_S.CF);
			//cpu.flags.bit.CF  = ( ( dst & 1 ) != 0 );
			break;
		case 0x1: // ROR
			return UNHANDLED_OPCODE;
			
		case 0x2: // RCL
			return UNHANDLED_OPCODE;
			
		case 0x3: // RCR
			return UNHANDLED_OPCODE;
			
		case 0x4: // SHL
		case 0x6: // alias
			return UNHANDLED_OPCODE;
			
		case 0x5: // SHR
			return UNHANDLED_OPCODE;
			
		case 0x7: // SAR
			return UNHANDLED_OPCODE;
			
	}
	cpu.flags(( ( ( dst ^ src) & 0x8000 ) != 0 ),CPU_STATE_S.OF);
	//cpu.flags.bit.OF = ( ( ( dst ^ src) & 0x8000 ) != 0 );
	set_ea_val_16(cpu, (int)dst);
	return cpu.cnt;
}

//LOOPNZ//LOOPNE imm8
static int ope0( CPU_STATE_S cpu )
{
	cpu.cnt |= BCNT_1;
	cpu.CX -= 1;
	//cpu.get_flag_bit(CPU_STATE_S.ZF)
	if( cpu.CX != 0 && !(cpu.get_flag_bit(CPU_STATE_S.ZF)>0) )
	{
		cpu.IP = ADD_S8(cpu.IP, cpu.mp.mp[1] + 2);
		cpu.mp.mp[cpu.IP] = cpu.memory.mem[ cpu.IP ];
		return 0;
	}
	return cpu.cnt;
}

//CALL imm16
static int ope8( CPU_STATE_S cpu )
{
	int mem =  MAKE16( cpu.mp.mp[2], cpu.mp.mp[1] );
	cpu.cnt |= BCNT_1 | BCNT_2;
	cpu8086.cpu_push( cpu, cpu.IP + 3 );
	cpu.IP += 3 + mem;
	cpu.mp.mp[cpu.IP] = cpu.memory.mem[ cpu.IP ];
	//printf( "CALL %xh\n", cpu.IP  );
	// push the current address(well the next location to execute when the stack is popped) to the stack
	// then set the IP to the new location in memory at imm16
	return 0;
}

//JMP imm8
static int opeb( CPU_STATE_S cpu )
{
	cpu.IP = ADD_S8( cpu.IP, cpu.mp.mp[1] + 2 );
	cpu.mp .mp[ cpu.IP ]= cpu.memory.mem[ cpu.IP ];
	cpu.cnt |= BCNT_1;
	//printf( "JMP %xh\n", cpu.IP );
	return 0;
}

//HALT
static int opf4( CPU_STATE_S cpu )
{
	//printf( "HALT!\n");
	return UNHANDLED_OPCODE; 
}

static int opf7( CPU_STATE_S cpu )
{
	int src1, src2;
	cpu.cnt |= BCNT_1; // modrm
	//@TODO: once support for segmented memory is in, this will need to be reintegrated
	//src1 = ( (uint32_t)cpu.DX << 16 ) | cpu.AX;
	src1 = cpu.AX;
	src2 = get_ea_val_16( cpu );
	switch( cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) )
	{
		case 0x0:
			break;
		case 0x1:
			break;
		case 0x2:
			break;
		case 0x3:
			break;
		case 0x4:
			break;
		case 0x5:
			break;
		case 0x6:
			break;
		case 0x7: // IDIV rm16
		{
			int sign1, sign2, dst1, dst2;
			//printf("IDIV rm16\n");
			// TODO: same thing as above (segmentation)
			//src2 = ( src2 & 0x8000 ) ? ( src2 | 0xffff0000 ) :  src2;
			if( src2 == 0 )
			{
				//printf("DIVIDE ERROR\n");
			}
			sign1 = ( ( src1 & 0x80000000 ) != 0 )?1:0;
			sign2 = ( ( src2 & 0x80000000 ) != 0 )?1:0;
			dst1 = (int)src1 / (int)src2;
			dst2 = (int)src1 % (int)src2;
			if( sign1 != sign2 )
			{
				if( dst1 > 0x8000 )
				{
					//printf("DIVIDE ERROR\n");
				}
				dst1 = (( ~dst1 + 1 ) & 0xffff);
			}
			else
			{
				if( dst1 > 0x7fff )
				{
					//printf("DIVIDE ERROR\n");					
				}
				if( sign1 >0)
				{
					dst2 = (( ~dst2 + 1 ) & 0xffff);
				}
			}
			cpu.AX = (int)dst1;
			cpu.DX = (int)dst2;
			return cpu.cnt;
		}	
			
	}
	return UNHANDLED_OPCODE;
}


//CLC
static int opf8( CPU_STATE_S cpu )
{
	cpu.flags(CPU_STATE_S.CLEAR_BIT,CPU_STATE_S.CF);
	// cpu.flags.bit.CF = 0;
	return cpu.cnt;
}

//STC
static int opf9( CPU_STATE_S cpu )
{
	cpu.flags(CPU_STATE_S.SET_BIT,CPU_STATE_S.CF);
	//cpu.flags.bit.CF = 1;
	return cpu.cnt;
}

//INC/DEC 	rm8
static int opfe( CPU_STATE_S cpu )
{
	int src, cf;
	cpu.cnt |= BCNT_1; // modrm
	src = get_ea_val_8(cpu);
	switch( cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) )
	{
		case 0x0: // INC rm8
			set_ea_val_8(cpu, src + 1);
			// don't modify CF 
			
			cf = cpu.get_flag_bit(CPU_STATE_S.CF);
			flags.set_flag_add_byte(cpu, src, 1);
			cpu.flags( ( cf != 0 ),CPU_STATE_S.CF);
			//cpu.flags.bit.CF = ( cf != 0 );
			break;
			
		case 0x1: // DEC rm8
			set_ea_val_8(cpu, src - 1);
			// don't modify CF 
			cf = cpu.get_flag_bit(CPU_STATE_S.CF);
			flags.set_flag_sub_byte(cpu, src, 1);
			cpu.flags( ( cf != 0 ),CPU_STATE_S.CF);
			//cpu.flags.bit.CF = ( cf != 0 );		
			break;
			
		default:
			return UNHANDLED_OPCODE;
		
	}
	return cpu.cnt;
}
private static void set_ea_val_8(CPU_STATE_S cpu, int val) {

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

	int src1, src2, dst;
	cpu.cnt |= BCNT_1; // modrm
	src1 = get_ea_val_16( cpu );
	src2 = get_reg_val_16( cpu, cpu.modrm(CPU_STATE_S.GetBit,CPU_STATE_S.reg) );
	dst = src1 | src2;
	set_ea_val_16(cpu, dst);
	flags. set_flag_log_word(cpu, dst);
	//printf( "OR %s, %s\n", wordRegName[ cpu.modrm(CPU_STATE_S.GetBit, CPU_STATE_S.reg) ], wordRegName[ cpu.modrm.bit.rm ] );
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

public static int LO8(int w) {
	// TODO Auto-generated method stub
	return		((w)&0xFF);
	
}

public static int HI8(int w) {
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

private static int MAKE_S16(int lo) {
	// TODO Auto-generated method stub
	return ( ( (( lo ) & 0x80)>0 ) ? ( ( lo ) | 0xff00) : ( ( lo ) & 0xff ) );
}

private static int MAKE16(int h, int l)
{
	return (l | h << 8);
}
private static int MODRM_RM(int b) 
{
	// TODO Auto-generated method stub
	return  (( b ) & 0x7) ;
}


private static int ADD_S8(int w, int b) {
	return ( ( (( ( b ) & 0x80 )>0) ? ( ( w ) + ( ( b ) | 0xff00 ) ) : ( ( w ) + ( ( b ) & 0xff ) ) ) & 0xffff );
}


}
