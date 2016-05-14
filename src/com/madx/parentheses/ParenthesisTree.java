package com.madx.parentheses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParenthesisTree {
	public static void main(String[] args) throws Exception {
		String s = "a, b, c, #{bool}, IF(#{bool}){IF(#{bool}){#{f.x}}}";
		System.out.println(s);
		QueryContainer q = getQueryContainer(s, new AtomicInteger(0));
		System.out.println(q);
	}

	public static final String REPL = "REPL_";
	private static final String ERROR_UNBALANCED = "String unbalanced";
	
	
	public static QueryContainer getQueryContainer(String s) throws Exception{
		return getQueryContainer(s, new AtomicInteger(0));
	}
	
	private static QueryContainer getQueryContainer(String s, AtomicInteger ai) throws Exception{
		QueryContainer q = new QueryContainer();
		Stack<Character> stack = new Stack<Character>();
//		List<String> replaced = new ArrayList<String>();
		String tmp = "";
		for (int i = 0; i < s.length(); i++) {
//			System.out.println(s.charAt(i));
			char c = s.charAt(i);
			boolean isOpen = c == Parenthesis.PAREN.open || c == Parenthesis.BRACE.open || c == Parenthesis.BRACKET.open;
			boolean isClose = c == Parenthesis.PAREN.close || c == Parenthesis.BRACE.close || c == Parenthesis.BRACKET.close;
			if (stack.isEmpty() && isOpen){
				stack.push(c);
				q.query += c;
			} else if(!stack.isEmpty() && isOpen) {
				stack.push(c);
				tmp += c;
			}

			else if (isClose) {
				if (stack.isEmpty())     throw new Exception(ERROR_UNBALANCED);
				char open = Parenthesis.getBalanceFromClose(c).open;
				if (stack.pop() != open) throw new Exception(ERROR_UNBALANCED);
				
				if(stack.isEmpty()){
					Pattern p = Pattern.compile(Parenthesis.getFullRegex());
					Matcher m = p.matcher(tmp);
					if(m.find()){
						String key = REPL + ai.get();
						q.query += key  + c;
						ai.set(ai.get() + 1);
						
						q.map.put(key, tmp);
//						replaced.add(tmp);
						tmp = "";
					} else {
						q.query += tmp  + c;
						tmp = "";
					}
				} else {
					tmp += c;
				}
			// ALL THE OTHER CHARACTERS
			} else {
				if(stack.isEmpty()){
					q.query += c;
				} else {
					tmp += c;
				}
			}
		}
		
		List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>();
		if(!q.map.isEmpty()){
			for (Map.Entry<String, String> entry : q.map.entrySet()) {
				QueryContainer qc = getQueryContainer(entry.getValue(), ai);
				entry.setValue(qc.query);
				for(Map.Entry<String, String> entry_lower : qc.map.entrySet()) {
					list.add(entry_lower);
				}
			}	
		}
		
		for(Map.Entry<String, String> e : list){
			q.map.put(e.getKey(), e.getValue());
		}
		
		return q;
	}

	/**
	 * Tree structure
	 * @author madx
	 */
	public static class QueryContainer{
		public String query = "";
		public Map<String, String> map = new HashMap<String, String>();

		public QueryContainer() {}

		public QueryContainer(String query, Map<String, String> map) {
			this.query = query;
			this.map = map;
		}

		@Override
		public String toString() {
			return query + "\n" + map;
		}
	}
}
