package cn.lihongjie.lambda.functionalInterface.predicate;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author 982264618@qq.com
 */
public class PredicateTest {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPredicate() throws Exception {


		Assert.assertTrue(new Predicate<String>() {
			@Override
			public boolean test(String toTest) {
				return toTest.isEmpty();
			}
		}.test(""));





	}

	@Test
	public void testPredicateWithLambda() throws Exception {


		Assert.assertTrue(((Predicate<String>) toTest -> {
			return toTest.isEmpty();
		}).test(""));


	}

	@Test
	public void testPredicateWithMethodRefer() throws Exception {


		Assert.assertTrue(((Predicate<String>) String::isEmpty).test(""));


	}

	@Test
	public void testNot() throws Exception {


		Predicate<String> emptyTest = new Predicate<String>() {
			@Override
			public boolean test(String toTest) {
				return toTest.isEmpty();
			}

		};

		Predicate<String> notEmpty = emptyTest.not();

		Assert.assertTrue(notEmpty.test("aa"));

	}
}