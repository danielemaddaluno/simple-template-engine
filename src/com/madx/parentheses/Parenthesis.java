package com.madx.parentheses;

public enum Parenthesis {
	PAREN('(', ')', "\\((.*?)\\)"),
	BRACE('{', '}', "\\{([^}]*)\\}"),
	BRACKET('[', ']', "\\[([^}]*)\\]");
	
	public char open;
	public char close;
	public String regex;
	
	private Parenthesis(char open, char close, String regex) {
		this.open = open;
		this.close = close;
		this.regex = regex;
	}
	
	public static Parenthesis getBalanceFromOpen(char open){
		switch (open) {
		case '(':
			return PAREN;
		case '{':
			return BRACE;
		case '[':
			return BRACKET;
		default:
			return null;
		}
	}
	
	public static Parenthesis getBalanceFromClose(char close){
		switch (close) {
		case ')':
			return PAREN;
		case '}':
			return BRACE;
		case ']':
			return BRACKET;
		default:
			return null;
		}
	}
	
	public static boolean containsParenthesis(String s){
		for(Parenthesis p : Parenthesis.values()){
			if(s.contains(String.valueOf(p.open)) || s.contains(String.valueOf(p.close))){
				return true;
			}
		}
		return false;
	}
	
	public String getRegex(){
		//return "\\" + this.open + "([^}]*)\\" + this.close;
		return regex;
	}
	
	public static String getFullRegex(){
		StringBuilder regexBuilder = new StringBuilder();
		for (Parenthesis p : Parenthesis.values()) {
			regexBuilder.append(p.getRegex() + "|");
		}
		String regex = regexBuilder.toString();
		return regex.substring(0, regex.length()-1);
	}
}
