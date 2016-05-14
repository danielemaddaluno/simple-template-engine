package com.madx.field;

import java.lang.reflect.Field;
import java.util.Map;

public class FieldAccessor {
	
	public static Object getObjectFromComplexField(Object o, String complexField) throws Exception{
		if(complexField.contains(".")){
			int dotPosition = complexField.indexOf(".");
			String simpleField = complexField.substring(0, dotPosition);
			o = getObjectFromSimpleField(o, simpleField);
			return getObjectFromComplexField(o, complexField.substring(dotPosition+1, complexField.length()));
		}
		return getObjectFromSimpleField(o, complexField);
	}
	
	private static Object getObjectFromSimpleField(Object o, String simpleField) throws Exception{
		if(o instanceof Map<?,?>){
			Map<?,?> map = (Map<?,?>)o;
			return map.get(simpleField);
		} else {
			Class<?> c = o.getClass();
			Field f = c.getDeclaredField(simpleField);
			f.setAccessible(true);
			return f.get(o);
		}
	}
}
