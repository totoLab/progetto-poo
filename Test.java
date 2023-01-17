package self.math;

import java.math.*;
import java.text.*;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) {

		int base = input("Inserire intero per la base: ");
		int power = input("Inserire intero per l'esponente: ");
		
		System.out.println("Test iniziato!");
		long start = System.nanoTime();
		powerTest(base, power);
		long end = System.nanoTime();
		System.out.println("Durata test: " + convertTime(start, end) + "s");
		
	}

	private static void powerTest(int base, int power) {		
		BigInteger b1 = new BigInteger(Integer.toString(base));
		BigInteger p1 = b1.pow(power);
		
		BigInt b2 = new BigIntLL(base);
		BigInt p2 = b2.pow(power);
		
		StringBuilder sb = new StringBuilder();
		String result = base + "^" + power + " = ";
		String bigInteger = "BigInteger " + result + p1;
		String bigInt = "BigInt     " + result + p2;
		
		if (!p1.toString().equals(p2.toString())) {
			sb.append("Risultati diversi:\n");
			sb.append(bigInteger);
			sb.append("\n");
			sb.append(bigInt);
		} else {
			sb.append("Test superato, risultato: ");
			sb.append(result + p1);
		}
		System.out.println(sb.toString());
	}
	
	private static String convertTime(long start, long end) {
		double time = (end - start) / Math.pow(10, 9);
		DecimalFormat df = new DecimalFormat("#.###");
		return df.format(time);
	}
	
	@SuppressWarnings("resource")
	private static int input(String s) {
		System.out.println(s);
		Scanner sc = new Scanner(System.in);
		return sc.nextInt();
	}

}
