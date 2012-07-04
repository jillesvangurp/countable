package com.jillesvangurp.countable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jillesvangurp.countable.AveragingDurationCounter;

public class AveragingDurationCounterTest {

	private AveragingDurationCounter durationCounter;

	@BeforeMethod
	public void before() {
		durationCounter = new AveragingDurationCounter("testcounter");

	}

	@Test
	public void averageDuration() {

		durationCounter.done(System.currentTimeMillis() - 1000);
		durationCounter.done(System.currentTimeMillis() - 1000);

		assertThat(durationCounter.count(), is(2l));
		assertThat(durationCounter.averageDuration(), is(1000l));
	}

	@Test
	public void shouldNotDivideByZero() {
		assertThat(durationCounter.count(), is(0l));
		assertThat(durationCounter.averageDuration(), is(0l));
	}

	@Test
	public void shouldAlwaysProvideConsistentCountAndAverage() throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(100);

		long total = 100000;
		for(int i=0;i<total;i++) {
			executorService.execute(new Runnable() {
				public void run() {
					durationCounter.done(System.currentTimeMillis() - 1000);
					assertThat("duration should stay 1000 because access to duration and done is synchronized", durationCounter.averageDuration(), is(1000l));
				}
			});
		}
		executorService.shutdown();
		executorService.awaitTermination(1000, TimeUnit.SECONDS);
		assertThat(durationCounter.count(), is(total));
		assertThat("duration should stay 1000 because access to duration and done is synchronized", durationCounter.averageDuration(), is(1000l));
	}
}
