package com.jillesvangurp.countable;

public class IncrementingCounter implements Counter {
	private final String name;
	private volatile long counter;

	public IncrementingCounter(String name) {
		this.name = name;
	}

	public long count() {
		return counter;
	}

	public void inc() {
		counter++;
	}

	@Override
	public String toString() {
		return name + ": " + counter;
	}
}
