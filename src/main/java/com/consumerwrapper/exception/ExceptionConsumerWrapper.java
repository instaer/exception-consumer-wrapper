package com.consumerwrapper.exception;

import java.util.function.Consumer;

/**
 * 对消费型函数式接口进行异常封装的包装类
 * 
 * @author instaer
 * @date 2020-08-07 15:14:34
 *
 */
public class ExceptionConsumerWrapper {

	/**
	 * 可抛出异常的Consumer包装类
	 * 
	 * @param <T>
	 * @param throwableConsumer
	 * @return
	 */
	public static <T> Consumer<T> throwableWrapper(ThrowableConsumer<T, Exception> throwableConsumer) {
		return t -> {
			try {
				throwableConsumer.accept(t);
			} catch (Exception e) {
				throwCheckedUnchecked(e);
			}
		};
	}

	/**
	 * 可处理异常的Consumer包装类
	 * 
	 * @param <T>
	 * @param throwableConsumer
	 * @param handleableConsumer
	 * @return
	 */
	public static <T> Consumer<T> handleableWrapper(ThrowableConsumer<T, Exception> throwableConsumer,
			HandleableConsumer<T, Exception> handleableConsumer) {
		return t -> {
			try {
				throwableConsumer.accept(t);
			} catch (Exception e) {
				handleableConsumer.accept(t, e);
			}
		};
	}

	/**
	 * 可抛出异常消费型接口
	 *
	 * @param <T>
	 * @param <E>
	 */
	@FunctionalInterface
	public interface ThrowableConsumer<T, E extends Exception> {
		void accept(T t) throws E;
	}

	/**
	 * 可处理异常消费型接口
	 *
	 * @param <T>
	 * @param <E>
	 */
	@FunctionalInterface
	public interface HandleableConsumer<T, E extends Exception> {
		void accept(T t, E e);
	}

	/**
	 * 抛出检查异常和非检查异常
	 * 
	 * @param <E>
	 * @param t
	 * @throws E
	 */
	@SuppressWarnings("unchecked")
	public static <E extends Throwable> void throwCheckedUnchecked(Throwable t) throws E {
		throw (E) t;
	}
}