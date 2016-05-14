package com.madx.ste.query;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.madx.ste.field.FieldAccessorTest;
import com.madx.ste.parenthesis.ParenthesisTree;
import com.madx.ste.parenthesis.ParenthesisTree.QueryContainer;
import com.madx.ste.query.QueryInterpreter.Replacement;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QueryInterpreterTest {
	public static String test1 = "AHAH #{bool}, IF(#{bool}){#{f.x}} (aaa, #{bool}) {ccc, #{bool}} [bbb, #{bool}]";
	public static String test2 = "insert into tab1 values (#{f.x}, #{f.y}, #{f.m})";
//	String unfilteredQuery = "AHAH in (a,b,c, #{bool}, IF(#{bool}){ #{f.x} } ) (aaa #{bool}) {ccc #{bool}} [bbb #{bool}]  AND IF( #{bool} ){PROVA IN ${f.z.w} AND C_STA = 2} 'some' string with #{f.x} #{f.z.q}'the data i want' inside a list ${f.z.w} ahah ${f.z.e} test @{f.m} #{a}";
//	String unfilteredQuery = "AHAH in (a, b, c, #{bool}, IF(#{bool}){#{f.x}}) (aaa, #{bool}) {ccc, #{bool}} [bbb, #{bool}]";
	
	@Test
	public void test1() throws Exception{
		Map<String, Object> map = FieldAccessorTest.getMapExample();
		QueryContainer q = ParenthesisTree.getQueryContainer(test1);
		Replacement r = QueryInterpreter.getReplacement(q, map);
		
		assertEquals("AHAH ?, ? (aaa, ?) {ccc, ?} [bbb, ?]", r.query);
		List<Object> o = new ArrayList<Object>();
		o.add(true);
		o.add(BigDecimal.valueOf(10));
		o.add(true);
		o.add(true);
		o.add(true);
		
		assertEquals(o, r.objects);
	}
	
	@Test
	public void test2() throws Exception{
		Map<String, Object> map = FieldAccessorTest.getMapExample();
		QueryContainer q = ParenthesisTree.getQueryContainer(test2);
		Replacement r = QueryInterpreter.getReplacement(q, map);
		
		assertEquals("insert into tab1 values (?, ?, ?)", r.query);
		List<Object> o = new ArrayList<Object>();
		o.add(BigDecimal.valueOf(10));
		o.add(20);
		o.add("SOSTITUITO!!!");
		
		assertEquals(o, r.objects);
	}
}
