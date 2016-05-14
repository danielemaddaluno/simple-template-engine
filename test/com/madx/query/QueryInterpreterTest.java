package com.madx.query;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.madx.example.FirstExample;
import com.madx.example.SecondExample;
import com.madx.parentheses.ParenthesisTree;
import com.madx.parentheses.ParenthesisTree.QueryContainer;
import com.madx.query.QueryInterpreter.Replacement;

public class QueryInterpreterTest {
	@Test
	public void test1() throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		FirstExample f = new FirstExample(BigDecimal.valueOf(10), 20, new SecondExample(30, Arrays.asList(1,2,3,4), new Integer[]{5,6,7}), "SOSTITUITO!!!");
		
		map.put("f", f);
		map.put("a", "Prova Map String");
		map.put("bool", true);
//		System.out.println("-----------------------------------------------------------");
//		System.out.println(FieldAccessController.getObjectFromComplexField(f, "x"));
//		System.out.println(FieldAccessController.getObjectFromComplexField(f, "z.q"));
//		System.out.println(FieldAccessController.getObjectFromComplexField(f, "z.w"));
//		System.out.println(FieldAccessController.getObjectFromComplexField(f, "z.e"));
//		System.out.println("-----------------------------------------------------------");
//		System.out.println(FieldAccessController.getObjectFromComplexField(map, "f.x"));
//		System.out.println(FieldAccessController.getObjectFromComplexField(map, "f.z.q"));
//		System.out.println(FieldAccessController.getObjectFromComplexField(map, "f.z.w"));
//		System.out.println(FieldAccessController.getObjectFromComplexField(map, "f.z.e"));
//		System.out.println(FieldAccessController.getObjectFromComplexField(map, "a"));
//		System.out.println("-----------------------------------------------------------\n\n\n");
		
		
		//String unfilteredQuery = "AHAH in (a,b,c, #{bool}, IF(#{bool}){ #{f.x} } ) (aaa #{bool}) {ccc #{bool}} [bbb #{bool}]  AND IF( #{bool} ){PROVA IN ${f.z.w} AND C_STA = 2} 'some' string with #{f.x} #{f.z.q}'the data i want' inside a list ${f.z.w} ahah ${f.z.e} test @{f.m} #{a}";
//		String unfilteredQuery = "AHAH in (a, b, c, #{bool}, IF(#{bool}){#{f.x}}) (aaa, #{bool}) {ccc, #{bool}} [bbb, #{bool}]";
		String unfilteredQuery = "AHAH #{bool}, IF(#{bool}){#{f.x}} (aaa, #{bool}) {ccc, #{bool}} [bbb, #{bool}]";
		System.out.println(unfilteredQuery);
		QueryContainer q = ParenthesisTree.getQueryContainer(unfilteredQuery);
		System.out.println(q + "\n\n");
		Replacement r = QueryInterpreter.getReplacement(q, map);
		System.out.println(r);
	}
}
