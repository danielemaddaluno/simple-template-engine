package com.madx.ste.parenthesis;

import org.junit.Test;

import com.madx.ste.parenthesis.ParenthesisTree;
import com.madx.ste.parenthesis.ParenthesisTree.QueryContainer;

public class ParenthesisTreeTest {
	@Test
	public void test1() throws Exception{
		String s = "a, b, c, #{bool}, IF(#{bool}){IF(#{bool}){#{f.x}}}";
		System.out.println(s);
		QueryContainer q = ParenthesisTree.getQueryContainer(s);
		System.out.println(q);
	}
}
