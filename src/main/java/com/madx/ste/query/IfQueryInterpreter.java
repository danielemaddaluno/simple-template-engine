package com.madx.ste.query;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

import com.madx.ste.field.FieldAccessor;
import com.madx.ste.parenthesis.Parenthesis;
import com.madx.ste.parenthesis.ParenthesisTree;
import com.madx.ste.parenthesis.ParenthesisTree.QueryContainer;

class IfQueryInterpreter extends QueryInterpreter {
	private static IfQueryInterpreter instance = null;
	public static IfQueryInterpreter getInstance() {
		if(instance == null) {
			instance = new IfQueryInterpreter();
		}
		return instance;
	}

	protected IfQueryInterpreter() {
		super("IF", Parenthesis.PAREN.getRegex() + Parenthesis.BRACE.getRegex(), 2);
	}

	@Override
	public Replacement evaluateExpression(Object navigated, Matcher m, QueryContainer c) throws Exception {
		// this if manages the string IF
		String ifCondition = m.group(this.FIRST_GROUP);
		String ifExpression = m.group(this.FIRST_GROUP + 1);

		if(ifCondition.startsWith(ParenthesisTree.REPL)){
			ifCondition = ifCondition.replace(ifCondition, c.getReplacement(ifCondition));
		}

		Boolean condition = (Boolean) QueryInterpreter.getReplacement(new QueryContainer(ifCondition, c.replacements), navigated).objects.get(0);
		if(condition.booleanValue()){
			if(ifExpression.startsWith(ParenthesisTree.REPL)){
				ifExpression = ifExpression.replace(ifExpression, c.getReplacement(ifExpression));
				return QueryInterpreter.getReplacement(new QueryContainer(ifExpression, c.replacements), navigated);
			}
			// TODO verify this below is correct
			Object o = FieldAccessor.getObjectFromComplexField(navigated, m.group(this.FIRST_GROUP + 1));
			return new Replacement(expressionQuestionMarks(o), expressionObjects(o));
		} else {
			return new Replacement("", Collections.emptyList());
		}
	}

	private String expressionQuestionMarks(Object o) throws Exception {
		if(o.getClass().isArray() || o instanceof Iterable<?>) throw new Exception();
		return "?";
	}

	private List<Object> expressionObjects(Object o) throws Exception {
		return Arrays.asList(o);
	}
}