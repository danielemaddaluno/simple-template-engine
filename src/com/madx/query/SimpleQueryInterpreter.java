package com.madx.query;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import com.madx.field.FieldAccessController;
import com.madx.parentheses.Parenthesis;
import com.madx.parentheses.ParenthesisTree.QueryContainer;

class SimpleQueryInterpreter extends QueryInterpreter {
	private static SimpleQueryInterpreter instance = null;
	public static SimpleQueryInterpreter getInstance() {
		if(instance == null) {
			instance = new SimpleQueryInterpreter();
		}
		return instance;
	}
	
	protected SimpleQueryInterpreter() {
		super("#", Parenthesis.BRACE.getRegex());
	}

	@Override
	public Replacement evaluateExpression(Object navigated, Matcher m, QueryContainer c) throws Exception {
		Object o = FieldAccessController.getObjectFromComplexField(navigated, m.group(this.FIRST_GROUP));
		return new Replacement(expressionQuestionMarks(o), expressionObjects(o));
	}
	
	private String expressionQuestionMarks(Object o) throws Exception {
		if(o.getClass().isArray() || o instanceof Iterable<?>) throw new Exception();
		return "?";
	}

	private List<Object> expressionObjects(Object o) throws Exception {
		return Arrays.asList(o);
	}

	

}
