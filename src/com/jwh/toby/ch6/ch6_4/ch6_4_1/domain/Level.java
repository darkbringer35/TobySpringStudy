package com.jwh.toby.ch6.ch6_4.ch6_4_1.domain;

public enum Level {
	//초기화 순서 중요함(illegal forward reference)
	GOLD(3, null), SILVER(2, GOLD), BASIC(1, SILVER);
	private final int value;
	private final Level next;

	Level(int value, Level next) {
		this.value = value;
		this.next = next;
	}

	public int intValue() {
		return this.value;
	}

	public Level nextLevel() {
		return this.next;
	}

	public static Level valueOf(int value) {
		switch (value) {
			case 1:
				return BASIC;
			case 2:
				return SILVER;
			case 3:
				return GOLD;
			default:
				throw new AssertionError("Unknown value: " + value);
		}
	}
}
