package dev.yashgupta.calculator;

import java.util.Scanner;

public class Main {

	public static void main( String[] args ) {
		Calculator calc = new Calculator();
		Scanner sc = new Scanner( System.in );

		while ( true ) {

			System.out.println( "Calculator" );
			System.out.println( "1) Only +,-,* and / supported" );
			System.out.println( "2) No brackets allowed" );
			System.out.println( "Enter Q to quit, or enter the expression" );

			String expression = sc.nextLine().trim();

			if ( expression.compareToIgnoreCase( "q" ) == 0 ) {
				break;
			}

			double result = 0;
			try {
				result = calc.calculate( expression );
				System.out.println( "Answer: " + String.format( "%.4f", result ) );
			} catch ( Exception e ) {
				System.out.println( e.getMessage() );
				System.out.println();
			}
		}
	}
}
