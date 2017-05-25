package com.prefengine.service.compilerForPF;

import java.io.FileNotFoundException;
import java.io.IOException;





public class Test {
	public static void main(String [] args) throws FileNotFoundException, IOException 
	{
		String word =  " duration 5 to 10 hours ";
				//String wordv= "  short duration, price from 50 to 600 trips 500 miles from boston at may 24 to newyork at 5/3/14 and non stop";
		Scanner scanner = new Scanner(word);
		scanner.scannerEngine();
		scanner.printMessage();
		Parser parser = new Parser(scanner);
		parser.parserEngine();
		parser.printMessage();
			
	}
}

