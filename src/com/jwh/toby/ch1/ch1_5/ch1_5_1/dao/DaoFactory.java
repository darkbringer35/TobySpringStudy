package com.jwh.toby.ch1.ch1_5.ch1_5_1.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//빈 팩토리에 주입하기 위한 메타 정보 클래스
@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao(){
        return new UserDao(connectionMaker());
    }

    @Bean
    public MessageDao messageDao(){
        return new MessageDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker(){
        return new NConnectionMaker();
    }
}
