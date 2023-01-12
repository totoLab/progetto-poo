package self.progetto.math;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.*;

public class Test {
	
	public static void main(String[] args) {
		//new Test().tests();
		
		new Test().littleTest();
		
	}
	
	private void littleTest() {
		int delta = 200;
		int lowerBound = 1;
		int upperBound = lowerBound + delta;
		
		int cont = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = lowerBound; i < upperBound; i++) {
			for (int j = lowerBound; j < upperBound; j++) {
				int r1 = add(i, j);
				int r2 = addTest(i, j);
				
				boolean result = r1 == r2;
				if (!result) {
					cont++;
					if (cont % 10 == 0) System.err.println("ERROR: " + cont);
				}
				sb.append("Test" + i + "," + j + ": " + r1 + ((result) ? "=" : "!=") +  r2 + "\n");
			}
		}
		System.out.println("Errors: " +  cont);
		System.out.println("Wanna print: ");
		Scanner s = new Scanner(System.in);
		String in = s.next();
		if (in.toLowerCase().contains("y")) {
			try {
				PrintWriter writer = new PrintWriter("bigIntLog.txt", "UTF-8");
				writer.print(sb.toString());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Done");
		
	}
	
	private int add(int a, int b) {
		return a + b;
	}
	
	private int addTest(int value1, int value2) {

		return Integer.parseInt(factory(value1).add(factory(value2)).toString());
		
	}

	BigInt factory(int x) {
		return new BigIntLL(x);
	}
	
	private void incrDecrTest(BigInt b) {
		System.out.println("Value: " + b.value());
		BigInt b1 = b.incr();
		System.out.println("Incr: " + b1.value());
		BigInt b2 = b.decr();
		System.out.println("Decr: " + b2.value());
	}

	private void tests() {
		powerTest(2, 128);
		comparableTest();
	}
	

	void powerTest(int base, int power) {
		String result = base + "^" + power + " = ";
		
		BigInteger b1 = new BigInteger(Integer.toString(base));
		BigInteger p1 = b1.pow(power);
		System.out.println("BigInteger " + result + p1);
		
		BigInt b2 = new BigIntLL(base);
		BigInt p2 = b2.pow(power);
		System.out.println("BigInt     " + result + p2);
	}
	
	boolean comparableTest() {
		BigInt b1 = new BigIntLL("52");		
		System.out.println(b1.value());

		BigInt b2 = new BigIntLL("51");	
		System.out.println(b2.value());
		
		BigInt b3 = new BigIntLL("50");	
		System.out.println(b3.value());
		
		return 
				b1.compareTo(b2) == 1 &&
				b2.compareTo(b2) == 0 &&
				b3.compareTo(b2) == -1
		;
	}
	
}
