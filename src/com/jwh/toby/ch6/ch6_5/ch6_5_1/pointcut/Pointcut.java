package com.jwh.toby.ch6.ch6_5.ch6_5_1.pointcut;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;

public interface Pointcut {
	ClassFilter getClassFilter();

	MethodMatcher getMethodMatcher();
}
