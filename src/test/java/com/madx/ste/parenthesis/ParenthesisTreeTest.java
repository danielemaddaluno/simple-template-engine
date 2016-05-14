package com.madx.ste.parenthesis;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.madx.ste.parenthesis.ParenthesisTree.QueryContainer;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParenthesisTreeTest {
	public static String TEST1 = "a, b, c, #{bool}, IF(#{bool}){IF(#{bool}){#{f.x}}}";
	
	@Test
	public void test1() throws Exception{
		QueryContainer q = ParenthesisTree.getQueryContainer(TEST1);
		assertEquals("a, b, c, #{bool}, IF(REPL_0){REPL_1}", q.query);
		assertEquals(Arrays.asList("#{bool}", "IF(REPL_2){REPL_3}", "#{bool}", "#{f.x}"), q.replacements);
	}
}
