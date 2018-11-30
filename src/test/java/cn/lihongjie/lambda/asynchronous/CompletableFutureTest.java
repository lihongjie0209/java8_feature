package cn.lihongjie.lambda.asynchronous;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author 982264618@qq.com
 */
public class CompletableFutureTest {

	private static Logger logger = getLogger(CompletableFutureTest.class);


	@Test
	public void testCompletableFuture() throws Exception {


		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> logger.info("hello async"))
				.thenAccept((Void v) -> logger.info("second call"))
				.thenAccept((Void v) -> logger.info("third call"));

		future.get(); // 阻塞调用, 直到运行结束
		logger.info("hello, main");

	}


	@Test
	public void testCompletableFutureWithSupplier() throws Exception {


		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 1)
				.thenApply(i -> i++);

		logger.info(future.get().toString());

	}


	@Test
	public void testGetWithTimeOut() throws Exception {

		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return 1;
		})

				.thenApply(i -> i++);
		logger.info("任务创建完成");
		Integer now = future.getNow(-1);

		Assert.assertTrue(now == -1);

	}


	@Test
	public void testChangeThreadPoolOfCompletableFuture() throws Exception {


		CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
			logger.info("异步运行");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return 1;
		}, Executors.newCachedThreadPool())  // 每一个异步任务都可以在提供的线程池中运行

				.thenApply(i -> i++)
				.thenRun(() -> logger.info("最后一步"))
				.thenRunAsync(() -> logger.info("在另一个线程池中运行!!!"), Executors.newCachedThreadPool());

		logger.info("任务创建完成");
		future.get();


	}


	@Test
	public void testExceptionInCompletableFuture() throws Exception {


		CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
			throw new RuntimeException("第一次异常");
		})
				.exceptionally(e -> {
					logger.info("第一个异常处理, 我将返回1");
					return 1;
				}).thenApply(i -> {
					logger.info(i.toString());
					return i;
				})
				.thenApply(i -> {
					logger.info("第二次异常");
					throw new RuntimeException();
				}).exceptionally(e -> {
					logger.info("第二次异常处理, 我将抛出异常");
					throw new RuntimeException();
				}).exceptionally(e -> {
					logger.info("第三次异常处理, 我将返回10");
					return 10;
				}).thenApply(item -> {
					logger.info(item.toString());
					return item;
				});


		Assert.assertTrue((Integer)future.get() == 10);


	}


	@Test
	public void testPutDataInCompletableFutureDataChannel() throws Exception {


		CompletableFuture<Integer> future = new CompletableFuture<>();
		future.thenApply(item -> item * 2)
				.thenAccept(item -> logger.info(item.toString()));


		// 第三方可以创建初始值
		boolean complete = future.complete(2);
		Assert.assertTrue(future.get() == 2);


	}


	@Test
	public void testCombine() throws Exception {


		CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> 1);
		CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> 2);
		f1.thenCombine(f2, Integer::sum).thenAccept(i -> logger.info(String.valueOf(i)));

	}


	/**
	 * 类似 flatmap
	 * @throws Exception
	 */
	@Test
	public void testCompose() throws Exception {

		// 嵌套 future
		CompletableFuture<CompletableFuture<Integer>> future = CompletableFuture.supplyAsync(() -> 1)
				.thenApply(CompletableFuture::completedFuture);

		// 扁平的 future
		CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 1)
				.thenCompose(CompletableFuture::completedFuture);


	}


	@Test
	public void testAsyncWithThreadPool() throws Exception {


		ExecutorService pool = Executors.newCachedThreadPool();


		Future<Integer> async1 = pool.submit(() -> 1);
		Future<Integer> async2 = pool.submit(() -> 2);
		Integer integer1 = async1.get(); // 阻塞调用
		Integer integer2 = async2.get(); // 阻塞调用
		pool.shutdownNow();

	}


	// todo 添加使用callback的代码示例
	@Test
	public void testAsyncWithCallback() throws Exception {



	}


	@Test
	public void testConstructFutureWithFixedValue() throws Exception {

		// 把数据放到数据Channel
		CompletableFuture<Integer> future = CompletableFuture.completedFuture(1);

		Assert.assertTrue(future.get() ==1);
	}


	@Test
	public void testConstructFutureWithThreadPool() throws Exception {


		CompletableFuture.supplyAsync(() -> 1, Executors.newCachedThreadPool());


	}


	/**
	 * 可以把thenApply 认为是非阻塞的Map
	 *
	 * @throws Exception
	 */
	@Test
	public void testFutureWithAsyncMap() throws Exception {


		CompletableFuture.supplyAsync(() -> 1)
				.thenApply(i -> i + 1) // 非阻塞, 直到上一步完成
				.thenApply(i -> i * 2)
				.thenApply(String::valueOf)
				.get();






	}


	@Test
	public void testAllOf() throws Exception {


		CompletableFuture<Integer> future = CompletableFuture.completedFuture(1);
		CompletableFuture<Integer> future2 = CompletableFuture.completedFuture(2);


		CompletableFuture<Void> allOf = CompletableFuture.allOf(future, future2);



	}

	@Test
	public void testAnyOf() throws Exception {


		CompletableFuture<Integer> future = CompletableFuture.completedFuture(1);
		CompletableFuture<Integer> future2 = CompletableFuture.completedFuture(2);


		CompletableFuture<Object> anyOf = CompletableFuture.anyOf(future, future2);


	}
}
