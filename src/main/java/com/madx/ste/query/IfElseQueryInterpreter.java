package com.madx.ste.query;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.madx.ste.parenthesis.Parenthesis;
import com.madx.ste.parenthesis.ParenthesisTree;
import com.madx.ste.parenthesis.ParenthesisTree.QueryContainer;

class IfElseQueryInterpreter extends QueryInterpreter {
	private static IfElseQueryInterpreter instance = null;
	public static IfElseQueryInterpreter getInstance() {
		if(instance == null) {
			instance = new IfElseQueryInterpreter();
		}
		return instance;
	}

	protected IfElseQueryInterpreter() {
		super("IFE", Pattern.quote("IFE") + Parenthesis.PAREN.getRegex() + Parenthesis.BRACE.getRegex() + Pattern.quote("ELSE") + Parenthesis.BRACE.getRegex(), 3);
	}

	@Override
	protected Replacement evaluateExpression(Object navigated, Matcher m, QueryContainer c) throws Exception {
		// this if manages the string IF
		String ifCondition = m.group(this.FIRST_GROUP);
		String ifExpression = m.group(this.FIRST_GROUP + 1);
		String elseExpression = m.group(this.FIRST_GROUP + 2);

		if(ifCondition.startsWith(ParenthesisTree.REPL)){
			ifCondition = ifCondition.replace(ifCondition, c.getReplacement(ifCondition));
		}

		Object obj_condition = QueryInterpreter.getReplacement(new QueryContainer(ifCondition, c.replacements), navigated).objects.get(0);
		if(!obj_condition.getClass().equals(boolean.class) && ! obj_condition.getClass().equals(Boolean.class)) throw new Exception("If condition must be a boolean");
		Boolean condition = (Boolean) obj_condition;
		if(condition.booleanValue()){
			if(ifExpression.startsWith(ParenthesisTree.REPL)){
				ifExpression = ifExpression.replace(ifExpression, c.getReplacement(ifExpression));
				return QueryInterpreter.getReplacement(new QueryContainer(ifExpression, c.replacements), navigated);
			} else {
				return new Replacement(ifExpression, Collections.emptyList()); 
			}
		} else {
			if(elseExpression.startsWith(ParenthesisTree.REPL)){
				elseExpression = elseExpression.replace(elseExpression, c.getReplacement(elseExpression));
				return QueryInterpreter.getReplacement(new QueryContainer(elseExpression, c.replacements), navigated);
			} else {
				return new Replacement(elseExpression, Collections.emptyList()); 
			}
		}
	}
}
