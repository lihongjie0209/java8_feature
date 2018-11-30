package cn.lihongjie.lambda.optional;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author 982264618@qq.com
 */
public class OptionalTest {


	private static Logger logger = getLogger(OptionalTest.class);

	@Test
	public void testOptionalTest() throws Exception {



		Optional<Object> empty = Optional.empty();


		OptionalInt optionalInt = OptionalInt.empty();


		OptionalLong optionalLong = OptionalLong.empty();


		OptionalDouble optionalDouble = OptionalDouble.empty();


	}

	@Test(expected = java.util.NoSuchElementException.class)
	public void testEmptyOptionalGet() throws Exception {


		Optional<Object> empty = Optional.empty();

		empty.get();

	}

	@Test
	public void testEmptyOptionWithTest() throws Exception {


		Optional<Object> empty = Optional.empty();


		if (empty.isPresent()) {
			empty.get();
		}


	}

	@Test
	public void testOptionalWithDefaultVal() throws Exception {


		Optional<Integer> empty = Optional.empty();
		Assert.assertTrue(empty.orElse(1) == 1);

		Optional<Integer> zero = Optional.of(0);
		Assert.assertTrue(zero.orElse(1) == 0);

	}

	@Test
	public void testOptionalMap() throws Exception {
		Optional<Integer> empty = Optional.empty();

		Integer integer = empty.map(item -> item * 2).orElse(0);


		Assert.assertTrue(integer == 0);

	}

	@Test
	public void testOptionalConsumer() throws Exception {


		Optional.of(1).ifPresent(i -> logger.info(String.valueOf(i)));

	}

	@Test
	public void testOptionalWithNull() throws Exception {


		Optional.ofNullable(null);

	}
}
