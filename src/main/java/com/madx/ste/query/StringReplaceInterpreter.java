package com.madx.ste.query;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

import com.madx.ste.field.FieldAccessor;
import com.madx.ste.parenthesis.Parenthesis;
import com.madx.ste.parenthesis.ParenthesisTree.QueryContainer;

class StringReplaceInterpreter extends QueryInterpreter {
	private static StringReplaceInterpreter instance = null;
	public static StringReplaceInterpreter getInstance() {
		if(instance == null) {
			instance = new StringReplaceInterpreter();
		}
		return instance;
	}
	
	protected StringReplaceInterpreter() {
		super("@", Parenthesis.BRACE.getRegex());
	}

	@Override
	protected Replacement evaluateExpression(Object navigated, Matcher m, QueryContainer c) throws Exception {
		Object o = FieldAccessor.getObjectFromComplexField(navigated, m.group(this.FIRST_GROUP));
		return new Replacement(expressionQuestionMarks(o), expressionObjects(o));
	}
	
	private String expressionQuestionMarks(Object o) throws Exception {
		if(!o.getClass().equals(String.class)) throw new Exception();
		return o.toString();
	}

	private List<Object> expressionObjects(Object o) throws Exception {
		return Collections.emptyList();
	}
}
