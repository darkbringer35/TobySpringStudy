package com.jwh.toby.ch3.ch3_5.ch3_5_3;

public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
