package com.jwh.toby.ch6.ch6_3.ch6_3_2.test;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReflectionTest {

	//reflection의 Method 사용 테스트 (String의 length, charAt으로)
	@Test
	public void invokeMethod() throws Exception {
		String name = "Spring";

		// length()
		assertThat(name.length(), is(6));

		Method lengthMethod = String.class.getMethod("length");
		assertThat((Integer)lengthMethod.invoke(name), is(6));

		//charAt()
		assertThat(name.charAt(0), is('S'));
		Method charAtMethod = String.class.getMethod("charAt", int.class);
		assertThat((Character)charAtMethod.invoke(name, 0), is('S'));
	}
}
