package com.jwh.toby.ch2.ch2_5.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.either;
import static org.junit.matchers.JUnitMatchers.hasItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("junit.xml")
public class JUnitTest {
	@Autowired
	ApplicationContext context;

	static Set<JUnitTest> testObjects = new HashSet<>();
	static ApplicationContext contextObject = null;

	@Test
	public void test1() {
		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);

		assertThat(contextObject == null || contextObject == this.context, is(true));
		contextObject = this.context;
	}

	@Test
	public void test2() {
		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);

		assertTrue(contextObject == null || contextObject == this.context);
		contextObject = this.context;
	}

	@Test
	public void test3() {
		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);

		assertThat(contextObject, either(is(nullValue())).or(is(contextObject)));
		contextObject = this.context;
	}
}