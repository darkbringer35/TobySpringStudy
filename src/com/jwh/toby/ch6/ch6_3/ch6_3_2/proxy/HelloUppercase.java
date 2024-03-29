package com.jwh.toby.ch6.ch6_3.ch6_3_2.proxy;

public class HelloUppercase implements Hello {
	Hello hello; //위임할 타깃 오브젝트

	public HelloUppercase(Hello hello) {
		this.hello = hello;
	}

	@Override
	public String sayHello(String name) {
		return hello.sayHello(name).toUpperCase();
	}

	@Override
	public String sayHi(String name) {
		return hello.sayHi(name).toUpperCase();
	}

	@Override
	public String sayThankYou(String name) {
		return hello.sayThankYou(name).toUpperCase();
	}
}
