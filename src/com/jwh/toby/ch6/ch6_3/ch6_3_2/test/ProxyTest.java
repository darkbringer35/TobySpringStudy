package com.jwh.toby.ch6.ch6_3.ch6_3_2.test;

import com.jwh.toby.ch6.ch6_3.ch6_3_2.proxy.Hello;
import com.jwh.toby.ch6.ch6_3.ch6_3_2.proxy.HelloTarget;
import com.jwh.toby.ch6.ch6_3.ch6_3_2.proxy.HelloUppercase;
import com.jwh.toby.ch6.ch6_3.ch6_3_2.proxy.UppercaseHandler;
import org.junit.Test;

import java.lang.reflect.Proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProxyTest {
    @Test
    public void simpleProxy() {
        Hello hello = new HelloTarget(); //타깃은 인터페이스로 접근하는 것이 좋다.
        assertThat(hello.sayHello("Toby"), is("Hello Toby"));
        assertThat(hello.sayHi("Toby"), is("Hi Toby"));
        assertThat(hello.sayThankYou("Toby"), is("Thank You Toby"));
    }

    @Test
    public void delegateTargetProxyTest() {
        Hello proxiedHello = new HelloUppercase(new HelloTarget());
        assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
        assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
        assertThat(proxiedHello.sayThankYou("Toby"), is("THANK YOU TOBY"));
    }

    @Test
    public void dynamicProxyTest() {
        //프록시 팩토리를 통한 다이나믹 프록시 생성
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(), //동적으로 생성되는 다이나믹 프록시 클래스의 로딩에 사용할 클래스 로더
                new Class[]{Hello.class},  //구현할 인터페이스
                new UppercaseHandler(new HelloTarget()) //부가기능과 위임코드를 담은 InvocationHandler
        );

        assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
        assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
        assertThat(proxiedHello.sayThankYou("Toby"), is("THANK YOU TOBY"));
    }
}
