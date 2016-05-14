package com.madx.ste.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.madx.ste.parenthesis.Parenthesis;
import com.madx.ste.parenthesis.ParenthesisTree.QueryContainer;

public abstract class QueryInterpreter {
	protected String SYMBOL;
	protected String SYMBOL_REGEX;
	protected int GROUP_SIZE;
	public int FIRST_GROUP;
	
	protected static HashMap<String, QueryInterpreter> interpreters = new LinkedHashMap<String, QueryInterpreter>();
	
	static {
		AtomicInteger i = new AtomicInteger(1);
		QueryInterpreter.registerQueryExpression(SimpleQueryInterpreter.getInstance(), i);
		QueryInterpreter.registerQueryExpression(ListQueryInterpreter.getInstance(), i);
		QueryInterpreter.registerQueryExpression(StringReplaceInterpreter.getInstance(), i);
		QueryInterpreter.registerQueryExpression(IfQueryInterpreter.getInstance(), i);
		QueryInterpreter.registerQueryExpression(BalancedParenthesisReplacementInterpreter.getInstance(), i);
	}

	private static void registerQueryExpression(QueryInterpreter q, AtomicInteger i){
		q.FIRST_GROUP = i.get();
		i.set(i.get() + q.GROUP_SIZE);
		interpreters.put(q.SYMBOL, q);
	}
	
	public static String getFullRegex(){
		StringBuilder regexBuilder = new StringBuilder();
		for (Map.Entry<String, QueryInterpreter> entry : interpreters.entrySet()) {
			regexBuilder.append(entry.getValue().getRegex() + "|");
		}
		String regex = regexBuilder.toString();
		return regex.substring(0, regex.length()-1);
	}
	
	public static QueryInterpreter getInterpreterFromString(String s) throws Exception {
		if(s.matches(Parenthesis.getFullRegex())) return BalancedParenthesisReplacementInterpreter.getInstance();
		
		if(s.contains("(")){
			return interpreters.get(s.substring(0, s.indexOf("(")));
		} else if(s.contains("{")) {
			return interpreters.get(s.substring(0, s.indexOf("{")));
		}
		throw new Exception("Cannot find any interpreter for this string");
	}

	protected QueryInterpreter(String symbol, String symbolRegex, int groupSize) {
		this.SYMBOL = symbol;
		this.SYMBOL_REGEX = Pattern.quote(symbol) + symbolRegex;
		this.GROUP_SIZE = groupSize;
	}
	
	protected QueryInterpreter(String symbol, String symbolRegex) {
		this(symbol, symbolRegex, 1);
	}
	
	public String getRegex(){
		return SYMBOL_REGEX;
	}

	public abstract Replacement evaluateExpression(Object navigated, Matcher m, QueryContainer c) throws Exception;
	
	public static Replacement getReplacement(QueryContainer c, Object obj) throws Exception{
		List<Object> list = new ArrayList<Object>();
		Pattern p = Pattern.compile(QueryInterpreter.getFullRegex());
		Matcher m = p.matcher(c.query);
		int offset = 0;
		while (m.find()) {
			String token = m.group();
//			System.out.println(token);
			QueryInterpreter q = QueryInterpreter.getInterpreterFromString(token);
			Replacement r = q.evaluateExpression(obj, m, c);
			list.addAll(r.objects);
			
			Pair pair = replaceInside(c.query, r.query, m.start(), m.end(), offset);
			offset = pair.offset;
			c.query = pair.s;
//			System.err.println("q: " + c.query);
		}
		return new Replacement(c.query, list);
	}
	
	public static class Replacement{
		public String query;
		public List<Object> objects;
		public Replacement(String query, List<Object> objects) {
			this.query = query;
			this.objects = objects;
		}
		@Override
		public String toString() {
			return query + "\n" + objects;
		}
	}
	
	private static Pair replaceInside(String s, String replacement, int start, int end, int offset){
		s = s.replace(s, s.substring(0, start + offset) + replacement + s.substring(end + offset, s.length()));
		return new Pair(s, replacement.length() - (end-start) + offset);
	}
	
	private static class Pair{
		public String s;
		public int offset;
		public Pair(String s, int variation) {
			this.s = s;
			this.offset = variation;
		}
	}
}
