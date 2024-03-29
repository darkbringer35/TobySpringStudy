package com.jwh.toby.ch6.ch6_3.ch6_3_3.domain;

public class User {
	private String id;
	private String name;
	private String password;
	private String email;
	private Level level;
	private int login;
	private int recommend;
	//private Date lastUpgraded;

	public User() {
	}

	public User(String id, String name, String password, String email, Level level, int login, int recommend) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.level = level;
		this.login = login;
		this.recommend = recommend;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public int getLogin() {
		return login;
	}

	public void setLogin(int login) {
		this.login = login;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public void upgradeLevel() {
		Level nextLevel = this.level.nextLevel();
		if (nextLevel == null)
			throw new IllegalStateException(this.level + "은 업그레이드가 불가능합니다.");
		else
			this.level = nextLevel;
		//this.lastUpgraded = new Date() 이런 로직을 붙이기가 수월해진다.
	}
}
