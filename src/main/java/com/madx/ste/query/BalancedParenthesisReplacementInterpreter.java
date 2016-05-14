package com.madx.ste.query;

import java.util.regex.Matcher;

import com.madx.ste.parenthesis.Parenthesis;
import com.madx.ste.parenthesis.ParenthesisTree;
import com.madx.ste.parenthesis.ParenthesisTree.QueryContainer;

/**
 * Uregistered Interpreter
 * @author madx
 *
 */
class BalancedParenthesisReplacementInterpreter extends QueryInterpreter {
	private static BalancedParenthesisReplacementInterpreter instance = null;
	public static BalancedParenthesisReplacementInterpreter getInstance() {
		if(instance == null) {
			instance = new BalancedParenthesisReplacementInterpreter();
		}
		return instance;
	}

	protected BalancedParenthesisReplacementInterpreter() {
		super("", Parenthesis.getFullRegex(), 3);
	}

	@Override
	public Replacement evaluateExpression(Object navigated, Matcher m, QueryContainer c) throws Exception {
		String completeString = m.group();
		if(completeString.matches(Parenthesis.getFullRegex())){
			String innerExpression = completeString.substring(1, completeString.length()-1);
			if(innerExpression.startsWith(ParenthesisTree.REPL)){
				innerExpression = innerExpression.replace(innerExpression, c.getReplacement(innerExpression));
				Replacement r = QueryInterpreter.getReplacement(new QueryContainer(innerExpression, c.replacements), navigated);
				r.query = completeString.charAt(0) + r.query + completeString.charAt(completeString.length()-1);
				return r;
			}
		}
		throw new Exception("Nothing to to with this string");
	}
}
