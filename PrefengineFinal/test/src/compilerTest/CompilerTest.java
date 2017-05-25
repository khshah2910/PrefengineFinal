package compilerTest;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;

import static org.hamcrest.CoreMatchers.*;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import com.prefengine.service.compilerForPF.*;

public class CompilerTest {
	Scanner scanner;	
	Parser parser;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	
	@Before
	public void setUpStreams() 
	{
		
		 System.setOut(new PrintStream(outContent));
		    System.setErr(new PrintStream(errContent));

		
	}
	@Test
	public void LANDA_Test() throws IOException
	{
		scanner = new Scanner("I want flight from boston to newyork at 5/27");
		scanner.scannerEngine();
		scanner.printMessage();
		
		parser = new Parser(scanner);
		parser.parserEngine();
		parser.generateMessageForUI();
		
		assertThat(outContent.toString(),is("i(GENERALPROPERTY)(1.0)     want(GENERALPROPERTY)(1.0)     flight(GENERALPROPERTY)(1.1)     from(LANDA)(-1.1)     boston(--)to(LANDA)(1.1)     newyork(--)at(LANDA)(1.5)     5/27/2017(null)(1.0)     \r\nLEAVE and ARRIVE request<br>leave : com.prefengine.service.compilerForPF.City@afffde   at:2017-05-27<br>---arrive : com.prefengine.service.compilerForPF.City@1690aa4   at:null<br>    .................. <br>\r\n"
				   ));
		}	
	@Test
	public void PriceOne_Test() throws IOException
	{
		scanner = new Scanner("I want flight within 50 and 100");
		scanner.scannerEngine();
		scanner.printMessage();
		
		parser = new Parser(scanner);
		parser.parserEngine();
		parser.generateMessageForUI();
		
		assertThat(outContent.toString(),is("i(GENERALPROPERTY)(1.0)     "
				+ "want(GENERALPROPERTY)(1.0)     flight(GENERALPROPERTY)(1.1)     "
				+ "within(COST)(-1.1)     50(null)(1.0)     and(GENERALPROPERTY)(1.1)"
				+ "     100(null)(1.0)     \r\nPRICE request<br>price :50.0 ~ 100.0<br>"
				+ "    .................. <br>\r\n"));
		}
	@Test
	public void Duration_Test() throws IOException
	{
		scanner = new Scanner("I want flight duration 5 hour~ 10 hour");
		scanner.scannerEngine();
		scanner.printMessage();
		
		parser = new Parser(scanner);
		parser.parserEngine();
		parser.generateMessageForUI();
		
		assertThat(outContent.toString(),is("i(GENERALPROPERTY)(1.0)     want(GENERALPROPERTY)(1.0)     "
				+ "flight(GENERALPROPERTY)(1.1)     duration(DURATION)(1.1)     5(null)(1.0)     "
				+ "hour(GENERALPROPERTY)(1.1)     numberto(--)10(null)(1.0)     hour(GENERALPROPERTY)(1.1)     \r\nDURATION request<br>duration : 5.0 ~ 10.0<br>    .................. <br>\r\n"
				  
				));
		}
	@Test
	public void Reputation_Test() throws IOException
	{
		scanner = new Scanner("I want reputation top 3 ");
		scanner.scannerEngine();
		scanner.printMessage();
		
		parser = new Parser(scanner);
		parser.parserEngine();
		parser.generateMessageForUI();
		
		assertThat(outContent.toString(),is("i(GENERALPROPERTY)(1.0)     want(GENERALPROPERTY)(1.0)     "
				+ "reputation(REPUTATION)(1.1)     top(REPUTATION)(2.0)     3(null)(1.0)     \r\nREPUTATION "
				+ "request<br>order from airline of : 3<br>    .................. <br>\r\n"
				));
		}
	@Test
	public void Mileage_Test() throws IOException
	{
		scanner = new Scanner("I want mileage under 500 ");
		scanner.scannerEngine();
		scanner.printMessage();
		
		parser = new Parser(scanner);
		parser.parserEngine();
		parser.generateMessageForUI();
		
		assertThat(outContent.toString(),is( "i(GENERALPROPERTY)(1.0)     want(GENERALPROPERTY)"
				+ "(1.0)     mileage(MILEAGE)(0.0)     under(GENERALPROPERTY)(-1.1)     500(null)"
				+ "(1.0)     \r\nMILEAGE request<br>mileage : 0.0 ~ 500.0<br>    .................. <br>\r\n"));
		}
	@Test
	public void PriceTwo_Test() throws IOException
	{
		scanner = new Scanner("I want flight under $500 ");
		scanner.scannerEngine();
		scanner.printMessage();
		
		parser = new Parser(scanner);
		parser.parserEngine();
		parser.generateMessageForUI();
		
		assertThat(outContent.toString(),is( "i(GENERALPROPERTY)(1.0)     want(GENERALPROPERTY)(1.0)     flight(GENERALPROPERTY)(1.1)"
				+ "     under(GENERALPROPERTY)(-1.1)     $(COST)(1.0)     500(null)(1.0)    "
				+ " \r\nPRICE request<br>price :0.0 ~ 500.0<br>    .................. <br>\r\n"));
				 }

	@Test
	public void Layout_Test() throws IOException
	{
		scanner = new Scanner("I want flight layout 5 hour to 10 hour ");
		scanner.scannerEngine();
		scanner.printMessage();
		
		parser = new Parser(scanner);
		parser.parserEngine();
		parser.generateMessageForUI();
		
		assertThat(outContent.toString(),is( "i(GENERALPROPERTY)(1.0)     want(GENERALPROPERTY)(1.0)     "
				+ "flight(GENERALPROPERTY)(1.1)     layout(LAYOUT)(1.1)     5(null)(1.0)     "
				+ "hour(GENERALPROPERTY)(1.1)     to(GENERALPROPERTY)(1.1)     10(null)(1.0)     hour(GENERALPROPERTY)(1.1)     \r\n5.0,,,,,\r\n10.0,,,,,\r\nLAYOUT request<br>layout : 5.0 ~ 10.0<br>    .................. <br>\r\n"));
			    
				 }
	@Test
	public void SeatClass_Test() throws IOException
	{
		scanner = new Scanner("I want flight of economy class");
		scanner.scannerEngine();
		scanner.printMessage();
		
		parser = new Parser(scanner);
		parser.parserEngine();
		parser.generateMessageForUI();
		
		assertThat(outContent.toString(),is( "i(GENERALPROPERTY)(1.0)     want(GENERALPROPERTY)(1.0)     flight(GENERALPROPERTY)(1.1)     "
				+ "of(GENERALPROPERTY)(1.0)     economy(SEATCLASS)(0.0)     class(SEATCLASS)(0.0)     \r\nSEATCLASS request<br>"
				+ "seat class : COACH<br>    .................. <br>\r\n"
				 ));
				 }
	@Test
	public void operator_Test() throws IOException
	{
		scanner = new Scanner("I want flight of economy class and price between 10 and 100 from boston to newyork at may 27");
		scanner.scannerEngine();
		scanner.printMessage();
		
		parser = new Parser(scanner);
		parser.parserEngine();
		parser.generateMessageForUI();
		
		assertThat(outContent.toString(),is(   "i(GENERALPROPERTY)(1.0)     want(GENERALPROPERTY)(1.0)     flight(GENERALPROPERTY)(1.1)"
				+ "     of(GENERALPROPERTY)(1.0)     economy(SEATCLASS)(0.0)     class(SEATCLASS)(0.0)     \r\nAND\r\nprice(COST)(1.1)"
				+ "     between(GENERALPROPERTY)(-1.1)     10(null)(1.0)     and(GENERALPROPERTY)(1.1)     100(null)(1.0)     "
				+ "from(LANDA)(-1.1)     boston(--)to(LANDA)(1.1)     newyork(--)at(LANDA)(1.5)     5/27/2017(null)(1.0)    "
				+ " \r\nSEATCLASS request<br>seat class : COACH<br>    .................. <br>PRICE request<br>price :10.0 ~ 100.0<br>    .................. <br>LEAVE and ARRIVE request<br>leave : com.prefengine.service.compilerForPF.City@18e2867   at:2017-05-27<br>---arrive : com.prefengine.service.compilerForPF.City@f40c25   at:null<br>    .................. <br>\r\n"

				 ));
				 }
	@Test
	public void misspell_Test() throws IOException
	{
		scanner = new Scanner("tickeet duraition stope hor pricing unimportantly");
		scanner.scannerEngine();
		scanner.printMessage();
		
		assertThat(outContent.toString(),is(  "tickeet(GENERALPROPERTY)(1.0)     duraition(DURATION)(1.1)     stope(NOSTOP)(0.0)     hor(GENERALPROPERTY)(1.0)"
				+ "     pricing(COST)(1.1)     unimportantly(GENERALPROPERTY)(-1.5)     \r\n" ));
				 }

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}
	
}

