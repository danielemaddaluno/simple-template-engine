package com.madx.ste.parenthesis;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParenthesisTree {

	public static final String REPL = "REPL_";
	private static final String ERROR_UNBALANCED = "String unbalanced";

	public static QueryContainer getQueryContainer(String s) throws Exception{
		return getQueryContainer(s, new AtomicInteger(0));
	}

	private static QueryContainer getQueryContainer(String s, AtomicInteger ai) throws Exception{
		QueryContainer q = new QueryContainer();
		Stack<Character> stack = new Stack<Character>();
		String tmp = "";
		for (int i = 0; i < s.length(); i++) {
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
						q.replacements.add(tmp);
						ai.set(ai.get() + 1);
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

		for(int i=0; i<q.replacements.size(); i++){
			QueryContainer qc = getQueryContainer(q.replacements.get(i), ai);
			q.replacements.set(i, qc.query);
			q.replacements.addAll(qc.replacements);
		}

		return q;
	}

	/**
	 * Tree structure
	 * @author madx
	 */
	public static class QueryContainer{
		public String query = "";
		public List<String> replacements = new ArrayList<String>();

		public QueryContainer() {}

		public QueryContainer(String query, List<String> replacements) {
			this.query = query;
			this.replacements = replacements;
		}

		@Override
		public String toString() {
			return query + "\n" + replacements;
		}

		public String getReplacement(String placeholder){
			int index = Integer.valueOf(placeholder.replace(REPL, ""));
			return replacements.get(index);
		}
	}
}
