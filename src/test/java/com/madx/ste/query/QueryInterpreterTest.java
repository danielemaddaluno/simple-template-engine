package com.madx.ste.query;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.madx.ste.example.ExampleClass;
import com.madx.ste.field.FieldAccessorTest;
import com.madx.ste.parenthesis.ParenthesisTree;
import com.madx.ste.parenthesis.ParenthesisTree.QueryContainer;
import com.madx.ste.query.QueryInterpreter.Replacement;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QueryInterpreterTest {
	public static String test1 = "AHAH #{bool}, IF(#{bool}){#{f.x}} (aaa, #{bool}) {ccc, #{bool}} [bbb, #{bool}]";
	public static String test2 = "insert into tab1 values (#{f.x}, #{f.y}, #{f.m})";
	public static String test4 = "insert into tab1 values (#{a}, val1, val2) where cod1 in ${b} IF(#{d}){AND} IF(#{e}){OR} cod2 not in ${c}";
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
		System.out.println(test1);
		System.out.println(r+"\n");
	}
	
	@Test
	public void test2() throws Exception{
		Map<String, Object> map = FieldAccessorTest.getMapExample();
		map.put("bool", false);
		QueryContainer q = ParenthesisTree.getQueryContainer(test1);
		Replacement r = QueryInterpreter.getReplacement(q, map);
		
		assertEquals("AHAH ?,  (aaa, ?) {ccc, ?} [bbb, ?]", r.query);
		List<Object> o = new ArrayList<Object>();
		o.add(false);
		o.add(false);
		o.add(false);
		o.add(false);
		
		assertEquals(o, r.objects);
		System.out.println(test1);
		System.out.println(r+"\n");
	}
	
	@Test
	public void test3() throws Exception{
		Map<String, Object> map = FieldAccessorTest.getMapExample();
		QueryContainer q = ParenthesisTree.getQueryContainer(test2);
		Replacement r = QueryInterpreter.getReplacement(q, map);
		
		assertEquals("insert into tab1 values (?, ?, ?)", r.query);
		List<Object> o = new ArrayList<Object>();
		o.add(BigDecimal.valueOf(10));
		o.add(20);
		o.add("SOSTITUITO!!!");
		
		assertEquals(o, r.objects);
		System.out.println(test2);
		System.out.println(r+"\n");
	}
	
	@Test
	public void test4() throws Exception{
		ExampleClass e = new ExampleClass(1, Arrays.asList(100D, 200D), new Long[]{123L, 456L, 789L}, true, false);
		Replacement r = QueryInterpreter.getReplacement(test4, e);
		
		assertEquals("insert into tab1 values (?, val1, val2) where cod1 in (?, ?) AND  cod2 not in (?, ?, ?)", r.query);
		List<Object> o = new ArrayList<Object>();
		o.add(1);
		o.add(100D);
		o.add(200D);
		o.add(123L);
		o.add(456L);
		o.add(789L);
		
		assertEquals(o, r.objects);
		System.out.println(test4);
		System.out.println(r+"\n");
	}
}
