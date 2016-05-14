package com.madx.query;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

import com.madx.field.FieldAccessController;
import com.madx.parentheses.Parenthesis;
import com.madx.parentheses.ParenthesisTree;
import com.madx.parentheses.ParenthesisTree.QueryContainer;

class IfQueryInterpreter extends QueryInterpreter {
	private static IfQueryInterpreter instance = null;
	public static IfQueryInterpreter getInstance() {
		if(instance == null) {
			instance = new IfQueryInterpreter();
		}
		return instance;
	}

	protected IfQueryInterpreter() {
		super("IF", Parenthesis.PAREN.getRegex() + Parenthesis.BRACE.getRegex());
	}

	@Override
	public Replacement evaluateExpression(Object navigated, Matcher m, QueryContainer c) throws Exception {
		// questo if gestisce il vero e proprio if della stringa
		String ifCondition = m.group(this.FIRST_GROUP);
		String ifExpression = m.group(this.FIRST_GROUP + 1);
		
		if(ifCondition.startsWith(ParenthesisTree.REPL)){
			ifCondition = ifCondition.replace(ifCondition, c.map.get(ifCondition));
		}
		
		Boolean condition = (Boolean) QueryInterpreter.getReplacement(new QueryContainer(ifCondition, c.map), navigated).objects.get(0);
		if(condition.booleanValue()){
			if(ifExpression.startsWith(ParenthesisTree.REPL)){
				ifExpression = ifExpression.replace(ifExpression, c.map.get(ifExpression));
				return QueryInterpreter.getReplacement(new QueryContainer(ifExpression, c.map), navigated);
			}
			Object o = FieldAccessController.getObjectFromComplexField(navigated, m.group(this.FIRST_GROUP + 1));
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
