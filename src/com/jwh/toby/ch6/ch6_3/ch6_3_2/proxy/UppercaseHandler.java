package com.jwh.toby.ch6.ch6_3.ch6_3_2.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

//reflection이 적용된 프록시
public class UppercaseHandler implements InvocationHandler {
    Object target; //어떤 종류의 인터페이스를 구현한 타깃에도 적용 가능하도록 Hello에서 Object 타입으로 수정

    public UppercaseHandler(Object target) {
        this.target = target;
    }

    //호출한 메소드의 리턴 타입 String인 경우만 대문자 변경 기능 적용하도록 수정
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ret = method.invoke(target, args);
        if (ret instanceof String && method.getName().startsWith("say")) //리턴값이 String일 때뿐만 아니라 메소드 이름이 일치하는 경우에만 부가기능을 적용하도록 확장
            return ((String) ret).toUpperCase();
        else
            return ret;
    }
}
