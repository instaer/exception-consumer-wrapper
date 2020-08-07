package com.consumerwrapper.exception;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Unit test for ExceptionConsumerWrapper.
 */
public class testExceptionConsumerWrapper {
	@Test
	public void test() {
		List<Integer> list = Arrays.asList(1, 0, 2, 4);

		// use try catch clause to handle checked exception
		list.forEach(i -> {
			try {
				throwIOException(i);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});

		// use throwableWrapper to throwing exception
		list.forEach(ExceptionConsumerWrapper.throwableWrapper(i -> throwIOException(i)));

		/**
		 * use hanleableWrapper to handling exception<br>
		 * throwing exception will exit the entire loop
		 */
		list.forEach(ExceptionConsumerWrapper.handleableWrapper(i -> System.out.println(4 / i), (i, e) -> {
			throw new RuntimeException(e);
		}));

		/**
		 * use hanleableWrapper to handling exception<br>
		 * handling the exception but not throwing it will execute each loop
		 */
		list.forEach(ExceptionConsumerWrapper.handleableWrapper(i -> System.out.println(4 / i),
				(i, e) -> System.out.println(e.getMessage())));
	}

	public static void throwIOException(Integer i) throws IOException {
		throw new IOException(i + " occurs IOException");
	}
}