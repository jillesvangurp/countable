package com.jillesvangurp.countable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jillesvangurp.countable.Counter;
import com.jillesvangurp.countable.CounterRegistry;
import com.jillesvangurp.countable.NamedCountable;

public class CounterRegistryTest {

	enum MyCounters implements NamedCountable {
		FOO,BAR
	}

	private CounterRegistry reg;

	@BeforeMethod
	public void before() {
		reg = new CounterRegistry();
		reg.registerDurationCounters(MyCounters.FOO);
		reg.registerDurationCounters(MyCounters.BAR);
	}

	@Test
	public void shouldCount() {
		reg.done(MyCounters.FOO, System.currentTimeMillis()-1000);
		reg.done(MyCounters.BAR, System.currentTimeMillis()-500);
		reg.done(MyCounters.FOO, System.currentTimeMillis()-2000);

		Counter counter = reg.get(MyCounters.BAR);
		assertThat(counter.count(), is(1l));
	}

}
