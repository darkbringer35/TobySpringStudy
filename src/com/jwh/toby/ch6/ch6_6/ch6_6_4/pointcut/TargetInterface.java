package com.jwh.toby.ch6.ch6_6.ch6_6_4.pointcut;

public interface TargetInterface {
	void hello();

	void hello(String s);

	int minus(int a, int b) throws RuntimeException;

	int plus(int a, int b);
}
