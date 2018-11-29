package cn.lihongjie.lambda.parallel;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author 982264618@qq.com
 */
public class ParallelTest {


	private static Logger logger = getLogger(ParallelTest.class);


	@Test
	public void testParallelStream() throws Exception {

		// 从源头创建并行流
		Stream<Integer> parallelStream = Arrays.asList(1, 2, 3).parallelStream();

		Assert.assertTrue(parallelStream.isParallel());


		Stream<Integer> stream = Arrays.asList(1, 2, 3).stream();
		// 在已有的流的基础上创建并行流
		stream.parallel();
		Assert.assertTrue(stream.isParallel());

	}


	@Test
	public void testThreadInStream() throws Exception {


		Arrays.asList(1, 2, 3).parallelStream().forEach(item -> logger.info(item.toString()));
		Arrays.asList(1, 2, 3).stream().forEach(item -> logger.info(item.toString()));


	}

	@Test
	public void testParallelStreamOrder() throws Exception {

		Arrays.asList(1, 2, 3).parallelStream().forEachOrdered(slowLog());
		Arrays.asList(1, 2, 3).parallelStream().forEach(slowLog());


	}


	@Test
	public void testParallelReduce() throws Exception {


		Integer parallelReduce = Arrays.asList(1, 2, 3)
				.parallelStream()
				.reduce(10, Integer::sum);

		// 并行 reduce 会采用分治算法, 每一个元素都会单独的并行计算
		// 计算的初始值就是 10
		// 所以在这里, 10 被加了三次
		// 为了避免这种问题, 初始值应该是一个不影响结果的值, 在加法中, 这个值是0
		Assert.assertTrue(parallelReduce == 36);


	}

	private Consumer<Integer> slowLog() {


		return item -> {

			try {
				logger.info("start sleep");
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logger.info(item.toString());
		};
	}
}
