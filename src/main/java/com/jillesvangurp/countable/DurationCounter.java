package com.jillesvangurp.countable;

public interface DurationCounter extends Counter {
	void done(long startTimeInMillis);
	long averageDuration();
}
