package com.jwh.toby.ch6.ch6_8.ch6_8_2.pointcut;

public interface TargetInterface {
	void hello();

	void hello(String s);

	int minus(int a, int b) throws RuntimeException;

	int plus(int a, int b);
}
