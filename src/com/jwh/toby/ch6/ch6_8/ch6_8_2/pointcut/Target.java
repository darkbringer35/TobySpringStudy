package com.jwh.toby.ch6.ch6_8.ch6_8_2.pointcut;

public class Target implements TargetInterface {
	@Override
	public void hello() {

	}

	@Override
	public void hello(String s) {

	}

	@Override
	public int minus(int a, int b) throws RuntimeException {
		return 0;
	}

	@Override
	public int plus(int a, int b) {
		return 0;
	}

	public void method() {
	}
}
