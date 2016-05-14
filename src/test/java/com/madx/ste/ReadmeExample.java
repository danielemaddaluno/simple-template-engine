package com.madx.ste;

import java.util.Arrays;
import java.util.List;

import com.madx.ste.query.QueryInterpreter;
import com.madx.ste.query.QueryInterpreter.Replacement;

public class ReadmeExample {
	@SuppressWarnings("unused")
	private static class ExampleClass{
		private Integer a;
		private List<Double> b;
		private Long[] c;
		private boolean d;
		private boolean e;
		public ExampleClass(Integer a, List<Double> b, Long[] c, boolean d, boolean e) {
			this.a = a;
			this.b = b;
			this.c = c;
			this.d = d;
			this.e = e;
		}
	}

	private static final String QUERY = "insert into tab1 values (#{a}, val1, val2) where cod1 in ${b} IF(#{d}){AND cod2 = #{a} AND } IF(#{e}){OR} cod3 not in ${c}";

	public static void main(String[] args) throws Exception {
		ExampleClass e = new ExampleClass(1, Arrays.asList(100D, 200D), new Long[]{123L, 456L, 789L}, true, false);
		Replacement r = QueryInterpreter.getReplacement(QUERY, e);
		System.out.println(r.query);
		System.out.println(r.objects);
	}
}
