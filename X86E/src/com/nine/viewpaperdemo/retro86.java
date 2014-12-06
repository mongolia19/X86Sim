package com.nine.viewpaperdemo;

public class retro86 {
	
	public static int AsmCode[];
	
	public final static  int MEMSIZE = 0x10000;
	public final static int  MEMOFFSET_VIDEO= 0x8000;
	 
	
	public static void startCPU(CPU_STATE_S cpu){
		
	
	

	cpu8086.cpu_create(cpu );
	cpu8086.cpu_reset( cpu );
	//print_cpu_state( cpu );



	// load the file into emulator memory
	
	cpu.memory.mem=AsmCode;
	
	
		disasm( cpu );


	cpu8086.cpu_destroy( cpu );
	
	}



	private static void disasm(CPU_STATE_S cpu) {
		// TODO Auto-generated method stub
		while(true )
		{
			int count = process_operation( cpu );
			int memoryOffset; 

			if( opcodes.UNHANDLED_OPCODE == count )
			{
				//printf("operation returned as unhandled\n");
				break;
			}

			cpu.IP += count;
			cpu.mp.mp[ cpu.IP ] = cpu.memory.mem[ cpu.IP ];
			
			
			print_cpu_state( cpu );
			//print_screen( cpu );
		}
		
		print_screen(cpu);
	}



	private static void print_cpu_state(CPU_STATE_S cpu) {
		System.out.println( "...............................................................\n");
		System.out.println( "AX:"+cpu.AX+" BX:"+cpu.BX +"CX:" +cpu.CX+" DX:"+ cpu.DX+"\n" );
		System.out.println( "AH:" +opcodes.HI8(cpu.AX) + "BH:"  +opcodes. HI8(cpu.BX)+ " CH:"+opcodes.HI8(cpu.CX)+  " DH: "+opcodes. HI8(cpu.DX)+"\n" );
		System.out.println( "AL: " +opcodes.LO8(cpu.AX)+ "BL:" + opcodes.LO8(cpu.BX)+ "CL: " + opcodes. LO8(cpu.CX)+" DL: "+ opcodes.LO8(cpu.DX) +"\n" );
		System.out.println( "SP:" + cpu.SP +"BP:"+cpu.BP + "SI:" +cpu.SI+"DI:"+ cpu.DI+"\n");
		//System.out.println( "CS: %04x DS: %04x SS: %04x ES: %04x\n",  cpu->CS, cpu->DS, cpu->SS, cpu->ES );
		System.out.println( "CF:"+cpu.get_flag_bit(CPU_STATE_S.CF)+"PF:"+ cpu.get_flag_bit(CPU_STATE_S.PF)+"AF:"+cpu.get_flag_bit(CPU_STATE_S.AF)+"ZF:"+ cpu.get_flag_bit(CPU_STATE_S.ZF)+"SF:" +cpu.get_flag_bit(CPU_STATE_S.SF)+"TF:"+ cpu.get_flag_bit(CPU_STATE_S.TF)+"IF:"+cpu.get_flag_bit(CPU_STATE_S.IF)+" DF:"+cpu.get_flag_bit(CPU_STATE_S.DF) +"OF:"+cpu.get_flag_bit(CPU_STATE_S.OF)+"\n"); 
		System.out.println( "IP:"+ cpu.IP );
		
	}



	private static void print_screen(CPU_STATE_S cpu) {
	
		int x, y;
		// print out the screen
		for( y = 0; y < 25; ++y )
		{
			for( x = 0; x < 80; ++x )
				System.out.println(  cpu.memory.mem[ MEMOFFSET_VIDEO + ( y * 80 ) + x ] );
			System.out.println(  "\n" );
		}
		
	}



	private static int process_operation(CPU_STATE_S cpu) {
		int i, b;
		int ret;

		System.out.println( "===============================================================\n");
		for( i = 0; i < 6; ++i )
		{
			int c = cpu.mp.mp[i];
			System.out.println( "0x%02x - "+c );
			for( b = 0; b < 8; ++b )
				System.out.println( (c >> (7-b)) & 1 );
			System.out.println("\n");
		}
		ret = cpu8086. cpu_execute( cpu );
		return ret;
	}




}
