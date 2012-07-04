package com.jillesvangurp.countable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

public class IncrementingCounterTest {
	@Test
	public void shouldIncrementCorrectly() throws InterruptedException {
		final IncrementingCounter incrementingCounter = new IncrementingCounter("testcounter");

		ExecutorService executorService = Executors.newFixedThreadPool(50);

		long total = 6666;
		for (int i = 0; i < total; i++) {
			executorService.execute(new Runnable() {

				public void run() {
					incrementingCounter.inc();
				}
			});
		}
		executorService.shutdown();
		executorService.awaitTermination(1000, TimeUnit.SECONDS);

		assertThat(incrementingCounter.count(), is(total));
	}
}
