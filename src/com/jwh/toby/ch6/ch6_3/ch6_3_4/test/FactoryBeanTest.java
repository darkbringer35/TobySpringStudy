package com.jwh.toby.ch6.ch6_3.ch6_3_4.test;

import com.jwh.toby.ch6.ch6_3.ch6_3_4.domain.Message;
import com.jwh.toby.ch6.ch6_3.ch6_3_4.factoryBean.MessageFactoryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../FactoryBeanTest-context.xml") //설정파일 이름을 지정하지 않으면 클래스이름 + "-context.xml"이 디폴트로 사용된다.
public class FactoryBeanTest {
    @Autowired
    ApplicationContext context;

    @Test
    public void getMessageFromFactoryBean() {
        Object message = context.getBean("message");
        assertThat(message, is(Message.class));
        assertThat(((Message) message).getText(), is("Factory Bean"));
    }

    @Test
    public void getFactoryBean() throws Exception {
        Object factory = context.getBean("&message");   //&가 bean 이름 앞에 붙으면팩토리 빈 자체를 반환한다.
        assertThat(factory, is(MessageFactoryBean.class));
    }
}
