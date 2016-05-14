# Simple Template Engine
Simple template engine for string replacements and if clauses

Here is an usage example:

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
	    	public ExampleClass(Integer a, List<Double> b, Long[] c) {
	    		this.a = a;
	    		this.b = b;
	    		this.c = c;
	    	}
    	}
    	public static void main(String[] args) throws Exception {
    		ExampleClass e = new ExampleClass(1, Arrays.asList(100D, 200D), new Long[]{123L, 456L, 789L});
    		String query = "insert into tab1 values (#{a}, val1, val2) where cod1 in ${b} AND cod2 not in ${c}";
    		Replacement r = QueryInterpreter.getReplacement(query, e);
    		System.out.println(r.query);
    		System.out.println(r.objects);
    	}
    }
    
Which will print you out:

    insert into tab1 values (?, val1, val2) where cod1 in (?, ?) AND cod2 not in (?, ?, ?)
    [1, 100.0, 200.0, 123, 456, 789]
