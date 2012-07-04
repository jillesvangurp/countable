package com.jillesvangurp.countable;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.Validate;


public class CounterRegistry {
	ConcurrentHashMap<Countable, AveragingDurationCounter> map = new ConcurrentHashMap<Countable, AveragingDurationCounter>();

	/**
	 * Register duration counters for one or more Countables.
	 * @param cs one or more Countables. Tip: put your counters in an enum that implements NamedCountable and use the static values method on the enum.
	 */
	public void registerDurationCounters(Countable...cs) {
		for(Countable c:cs) {
			if(c instanceof NamedCountable) {
				AveragingDurationCounter durationCounter = new AveragingDurationCounter(((NamedCountable) c).name());
				map.put(c, durationCounter);
			} else {
				AveragingDurationCounter durationCounter = new AveragingDurationCounter(c.getClass().getCanonicalName());
				map.put(c, durationCounter);
			}
		}
	}

	/**
	 * Increment and record the duration of the countable.
	 * @param c the countable
	 * @param startTime start time of the activity you are counting in milli seconds.
	 */
	public void done(Countable c, long startTime) {
		AveragingDurationCounter durationCounter = map.get(c);
		Validate.notNull(durationCounter, "no counter found for " + c);
		durationCounter.done(startTime);
	}

	public Counter get(Countable c) {
		return map.get(c);
	}
}
