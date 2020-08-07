# exception-consumer-wrapper Java8消费型接口异常处理

JDK compatibility:  1.8 -

## Features

* 避免在lambda表达式中处理异常，让代码更简洁

## Usage

```java
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
```

## Example

![code example](https://raw.githubusercontent.com/instaer/imgurl/master/20200807-exception-consumer-wrapper-readme.png)
