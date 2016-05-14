package com.madx.parenthesis;

import org.junit.Test;

import com.madx.parenthesis.ParenthesisTree.QueryContainer;

public class ParenthesisTreeTest {
	@Test
	public void test1() throws Exception{
		String s = "a, b, c, #{bool}, IF(#{bool}){IF(#{bool}){#{f.x}}}";
		System.out.println(s);
		QueryContainer q = ParenthesisTree.getQueryContainer(s);
		System.out.println(q);
	}
}
