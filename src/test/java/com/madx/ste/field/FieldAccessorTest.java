package com.madx.ste.field;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.madx.ste.example.FirstExample;
import com.madx.ste.example.SecondExample;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FieldAccessorTest {
	
	public static FirstExample getFirstExample(){
		return new FirstExample(BigDecimal.valueOf(10), 20, new SecondExample(30, Arrays.asList(1,2,3,4), new Integer[]{5,6,7}), "SOSTITUITO!!!");
	}
	
	public static Map<String, Object> getMapExample(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("f", getFirstExample());
		map.put("a", "Prova Map String");
		map.put("bool", true);
		return map;
	}
	
	private static FirstExample f;
	private static Map<String, Object> m;
	
	@BeforeClass
	public static void onceExecutedBeforeAll() {
		f = getFirstExample();
		m = getMapExample();
	}
	
	@Test
	public void test1_simple_object() throws Exception{
		Object o1 = FieldAccessor.getObjectFromComplexField(f, "x");
		Object o2 = FieldAccessor.getObjectFromComplexField(m, "f.x");
		
		assertEquals(o1.getClass(), BigDecimal.class);
		assertEquals(o2.getClass(), BigDecimal.class);
		
		assertEquals(o1, f.getX());
		assertEquals(o2, f.getX());
	}
	
	@Test
	public void test2_list() throws Exception{
		Object o1 = FieldAccessor.getObjectFromComplexField(f, "z.w");
		Object o2 = FieldAccessor.getObjectFromComplexField(m, "f.z.w");
		
		assertEquals(o1, f.getZ().getW());
		assertEquals(o2, f.getZ().getW());
	}
	
	@Test
	public void test3_array() throws Exception{
		Object o1 = FieldAccessor.getObjectFromComplexField(f, "z.e");
		assertEquals(o1, f.getZ().getE());
		
		// TODO verify why this is not returning the same pointer to the same object
//		Object o2 = FieldAccessor.getObjectFromComplexField(m, "f.z.e");
//		assertEquals(o2, f.getZ().getE());
//		System.out.println(o2);
//		System.out.println(f.getZ().getE());
//		System.out.println();
	}
	
	@Test
	public void test4_map() throws Exception{
		Object o1 = FieldAccessor.getObjectFromComplexField(m, "a");
		
		assertEquals(o1, m.get("a"));
	}
	
	
}
