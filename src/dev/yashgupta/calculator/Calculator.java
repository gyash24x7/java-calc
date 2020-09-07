package dev.yashgupta.calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
	static final ArrayList< String > operatorList = new ArrayList<>();

	static {
		operatorList.add( "/" );
		operatorList.add( "*" );
		operatorList.add( "+" );
		operatorList.add( "-" );
	}

	private double performOperation( double operand1, double operand2, String operator ) {
		switch ( operator ) {
			case "/":
				return operand1 / operand2;
			case "*":
				return operand1 * operand2;
			case "+":
				return operand1 + operand2;
			case "-":
				return operand1 - operand2;
		}

		return 0;
	}

	public double calculate( String expressionStr ) throws Exception {
		Tokenizer tokenizer = new Tokenizer( expressionStr );
		List< String > expression = tokenizer.getTokens();

		for ( String operator : operatorList ) {
			while ( expression.contains( operator ) ) {
				System.out.println( expression );
				int operatorIdx = expression.indexOf( operator );
				if ( operatorIdx == 0 || operatorIdx == expression.size() - 1 ) {
					throw new InvalidExpressionException( "Expression Invalid!" );
				}

				double operand1, operand2;
				try {
					operand1 = Double.parseDouble( expression.get( operatorIdx - 1 ) );
					operand2 = Double.parseDouble( expression.get( operatorIdx + 1 ) );
				} catch ( NumberFormatException e ) {
					throw new NumberFormatException( "Expression should contain numbers only!" );
				}

				double result = this.performOperation( operand1, operand2, operator );
				expression.remove( operatorIdx - 1 );
				expression.remove( operatorIdx - 1 );
				expression.remove( operatorIdx - 1 );
				expression.add( operatorIdx - 1, Double.toString( result ) );

			}

		}
		return Double.parseDouble( expression.get( 0 ) );
	}


}
