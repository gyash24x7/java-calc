package dev.yashgupta.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Tokenizer {
	private static final List< String > separatorList;

	static {
		separatorList = new ArrayList<>( Arrays.asList( "/", "*", "+", "-" ) );
	}

	private final String expression;

	public Tokenizer( String expression ) {
		this.expression = sanitiseExpression( expression );
	}

	private static String sanitiseExpression( String expression ) {
		expression = String.join( "", expression.trim().split( " " ) );
		StringBuilder newExpression = new StringBuilder();
		newExpression.append( expression.charAt( 0 ) );

		for ( int i = 1; i < expression.length() - 1; i++ ) {
			char currentChar = expression.charAt( i );
			String previousChar = "" + expression.charAt( i - 1 );
			String nextChar = "" + expression.charAt( i + 1 );

			if ( currentChar == '-' && !separatorList.contains( nextChar ) && !separatorList.contains(
					previousChar ) ) {
				newExpression.append( "+-" );
			} else {
				newExpression.append( expression.charAt( i ) );
			}
		}
		newExpression.append( expression.charAt( expression.length() - 1 ) );
		return newExpression.toString();

	}

	public List< String > getTokens() throws InvalidExpressionException {
		return this.sanitiseTokens( this.separateTokens() );
	}

	private List< String > sanitiseTokens( List< String > tokens ) throws InvalidExpressionException {
		List< Integer > indicesToRemove = new ArrayList<>();
		for ( int i = 0; i < tokens.size() - 2; i++ ) {
			if ( tokens.get( i ).compareTo( "" ) == 0 ) {
				if ( tokens.get( i + 1 ).compareTo( "+" ) != 0 && tokens.get( i + 1 ).compareTo( "-" ) != 0 ) {
					throw new InvalidExpressionException( "Expression Invalid!" );
				}

				if ( tokens.get( i + 2 ).compareTo( "" ) != 0 ) {
					String sign = tokens.get( i + 1 );
					tokens.set( i + 2, sign + tokens.get( i + 2 ) );
					indicesToRemove.add( i - indicesToRemove.size() );
					indicesToRemove.add( i + 1 - indicesToRemove.size() );
				} else {
					throw new InvalidExpressionException( "Expression Invalid!" );
				}
			}
		}

		for ( int index : indicesToRemove ) {
			tokens.remove( index );
		}

		return tokens;
	}

	private List< String > separateTokens() {
		List< String > tokens = new LinkedList<>();

		int idx = 0;

		while ( idx < this.expression.length() ) {
			int min = this.expression.length();

			for ( String separator : separatorList ) {
				int i = this.expression.indexOf( separator, idx );
				if ( i > -1 ) {
					min = Math.min( min, i );
				}
			}

			if ( min < this.expression.length() ) {
				tokens.add( this.expression.substring( idx, min ) );
				tokens.add( this.expression.charAt( min ) + "" );
				idx = min + 1;
			} else {
				tokens.add( this.expression.substring( idx ) );
				break;
			}
		}

		return tokens;
	}
}
