package cn.lihongjie.lambda.functionalInterface.comparator;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author 982264618@qq.com
 */
public class ComparatorTest {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testComparator() throws Exception {


		Comparator<String> comparator = (String s1, String s2) -> s1.substring(0, 1).compareTo(s2.substring(0, 1));


		assertListSort(comparator);


	}

	@Test
	public void testComparatorWithFactoryMethod() throws Exception {


		Comparator<String> comparator = Comparator.comparing((String s) -> s.substring(0, 1));


		assertListSort(comparator);


	}

	private void assertListSort(Comparator<String> comparator) {
		List<String> stringList = Arrays.asList("eaa", "baa", "caa", "aaa");

		stringList.sort(comparator::compare);

		Assert.assertThat(stringList, Is.is(Arrays.asList("aaa", "baa", "caa", "eaa")));
	}
}