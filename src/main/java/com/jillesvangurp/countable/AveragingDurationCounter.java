package com.jillesvangurp.countable;

public class AveragingDurationCounter implements DurationCounter {
	private volatile long count = 0;
	private volatile long cumulativeDuration = 0;

	private final String name;

	public AveragingDurationCounter(String name) {
		this.name = name;
	}

	public void done(long startTimeInMillis) {
		synchronized (this) {
			cumulativeDuration += System.currentTimeMillis() - startTimeInMillis;
			count++;
		}
	}

	public String getName() {
		return name;
	}

	public long averageDuration() {
		synchronized(this) {
			if(count > 0) {
				return cumulativeDuration/count;
			}
			else {
				return 0;
			}
		}
	}

	public long count() {
		return count;
	}

	@Override
	public String toString() {
		return name + ": " + count + " average duration " + averageDuration();
	}
}
