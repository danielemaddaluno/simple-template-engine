package com.madx.ste.query;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import com.madx.ste.field.FieldAccessor;
import com.madx.ste.parenthesis.Parenthesis;
import com.madx.ste.parenthesis.ParenthesisTree.QueryContainer;

class ListQueryInterpreter extends QueryInterpreter {

	private static ListQueryInterpreter instance = null;
	public static ListQueryInterpreter getInstance() {
		if(instance == null) {
			instance = new ListQueryInterpreter();
		}
		return instance;
	}

	protected ListQueryInterpreter() {
		super("$", Parenthesis.BRACE.getRegex());
	}

	@Override
	protected Replacement evaluateExpression(Object navigated, Matcher m, QueryContainer c) throws Exception {
		Object o = FieldAccessor.getObjectFromComplexField(navigated, m.group(this.FIRST_GROUP));
		return new Replacement(expressionQuestionMarks(o), expressionObjects(o));
	}

	private String expressionQuestionMarks(Object o) throws Exception {
		boolean isArray = o.getClass().isArray();
		boolean isList = o instanceof List<?>;
		if(!isArray && !isList) throw new Exception();
		int length = 0;
		if(isArray){
			length = ((Object[])o).length;
		} else {
			length = ((List<?>)o).size();
		}

		StringBuilder questionMarks = new StringBuilder();
		questionMarks.append("(");
		for(int i=0; i<length; i++){
			if(i==length-1){
				questionMarks.append("?)");
			} else {
				questionMarks.append("?, ");
			}
		}
		return questionMarks.toString();
	}

	@SuppressWarnings("unchecked")
	private List<Object> expressionObjects(Object o) throws Exception {
		boolean isArray = o.getClass().isArray();
		boolean isList = o instanceof List<?>;
		if(!isArray && !isList) throw new Exception();
		if(isArray){
			return Arrays.asList((Object[])o);
		} else {
			return (List<Object>) o;
		}
	}
}
