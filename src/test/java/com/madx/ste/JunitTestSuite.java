package com.madx.ste;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.madx.ste.field.FieldAccessorTest;
import com.madx.ste.parenthesis.ParenthesisTreeTest;
import com.madx.ste.query.QueryInterpreterTest;
@RunWith(Suite.class)
@Suite.SuiteClasses({
	FieldAccessorTest.class,
	ParenthesisTreeTest.class,
	QueryInterpreterTest.class
})
public class JunitTestSuite {   
}  	