package com.jwh.toby.ch1.ch1_1.domain;


//@Data //@Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode 를 전부 설정해주는 lombok 어노테이션
//@RequiredArgsConstructor은 final 또는 @NonNull 필드 값을 파라미터로 받는 생성자가 만들어진다.
public class User {
    private String id;
    private String name;
    private String password;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
