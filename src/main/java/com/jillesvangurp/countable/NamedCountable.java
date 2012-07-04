package com.jillesvangurp.countable;

/**
 * The named countable API has only one method (name) and it is conveniently the same as you will find in enums. This means
 * that you can create an enum of named countables simply by implementing this interface on an enum of counters. Even
 * better, you can register counters for your countables simply by using the values() method on the enum and passing the
 * result into the appropriate method in CounterRegistry.
 *
 * Using enums allows you to group related counters in a type and have type safe references to countables across your code.
 *
 * While this is the best way to use the countable framework, you can of course decorate any type you want with
 * this interface and implement the name method.
 */
public interface NamedCountable extends Countable {
	String name();
}
