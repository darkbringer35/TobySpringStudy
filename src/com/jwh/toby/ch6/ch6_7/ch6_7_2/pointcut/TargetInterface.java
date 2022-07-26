package com.jwh.toby.ch6.ch6_7.ch6_7_2.pointcut;

public interface TargetInterface {
    void hello();

    void hello(String s);

    int minus(int a, int b) throws RuntimeException;

    int plus(int a, int b);
}
