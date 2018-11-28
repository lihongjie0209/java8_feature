package cn.lihongjie.lambda.functionalInterface;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author 982264618@qq.com
 */
public class ConsumerTest {

	private static Logger logger = getLogger(ConsumerTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testConsumer() throws Exception {


		Consumer<String> stringConsumer = new Consumer<String>() {
			@Override
			public void consume(String o) {
				logger.info(o);
			}
		};
		stringConsumer.consume("hello world");

	}

	@Test
	public void testConsumerWithLambda() throws Exception {


		Consumer<String> stringConsumer = o -> logger.info(o);
		stringConsumer.consume("hello world");

	}

	@Test
	public void testConsumerWithMethodRefer() throws Exception {


		Consumer<String> stringConsumer = logger::info;
		stringConsumer.consume("hello world");

	}


	@Test
	public void testConsumerCompose() throws Exception {

		Consumer<String> first = logger::info;
		Consumer<String> second = logger::info;

		Consumer<String> composed = first.then(second);

		composed.consume("hello, world");






	}
}