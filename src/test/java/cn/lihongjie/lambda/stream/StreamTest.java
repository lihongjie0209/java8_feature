package cn.lihongjie.lambda.stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author 982264618@qq.com
 */
public class StreamTest {

	private static Logger logger = getLogger(StreamTest.class);
	private String[] names;

	@Before
	public void setUp() throws Exception {
		names = "赵银杰, 王云凯, 李化普, 马霁林, 朱庆霏, 陆简琪, 王忠喜, 郭凡平, 邱葵峰, 卢有举, 姜光青, 赵炫均, 王兴敏, 张勤达, 李公坚, 侯庭鹏, 白楠龙, 张延辉, 段瑞宣, 曾米泉, 张清滨, 李瑞雅, 兰士山, 罗显营, 陈云芝, 郑姣炼, 余楚凤, 孙迈超, 范鲜桂, 阎亚飞, 宋南军, 姜正泽, 李焕海, 施云敏, 唐昊田, 陈中松, 王炳桥, 刘刘铭, 王堃腾, 张俊林, 杨瑞芳, 石烨环, 刘阳旺, 黄达强, 张爱锋, 韩涵亮, 陈艺竞, 白爽璋, 陈培琳, 韩若讷, 张庆山, 梅利鹏, 陆晓国, 刘连云, 刘娟涛, 张学涛, 朱佳梁, 孙作慧, 赵荣恒, 姚琼峥, 许斌天, 阎则森, 陶祥朋, 钟翠强, 陈敏杰, 周倩权, 赵玉淇, 张保逸, 王君楷, 陈铁芬, 王铭峰, 周见侯, 杨茜乐, 张建赛, 罗巍华, 刘喜倩, 朱延威, 魏玫嘉, 徐小英, 黄晓茜, 王旭波, 刘瑞海, 颜仕明, 李衍新, 易小涵, 韩芳峰, 乔慧智, 张宝业, 李家琪, 张文俭, 张昊源, 李献宝, 杨光辉, 杨永辉, 杨云花, 梁文思, 伍亲平, 萧守瑶, 王茗欣, 张红华".split(", ");
	}

	@Test
	public void testMapAndFilter() throws Exception {


		Arrays.stream(names)
				.filter(name -> name.startsWith("李"))
				.map(name -> name.substring(0, 1) + " " + name.substring(1))
				.forEachOrdered(logger::info);


	}


	@Test
	public void testFlatMap() throws Exception {

		String s = "Masterpiece Generator refers to a set of text generator tools created by Aardgo";


		Stream.of(s)
				.flatMap(item -> Arrays.stream(item.split(" ")))
				.forEachOrdered(logger::info);

	}


	// stream  -> object
	@Test
	public void testReduce() throws Exception {


		BigDecimal reduce = LongStream.rangeClosed(1, 21)
				.mapToObj(BigDecimal::new)
				// 初始化值, pair 函数
				.reduce(BigDecimal.ONE, BigDecimal::multiply);

		logger.info(reduce.toString());

	}


	@Test
	public void testCollectorToMap() throws Exception {


		List<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());

		list.stream()
				.collect(Collectors.toMap(Function.identity(), Function.identity()))
				.forEach((key, value) -> logger.info(String.format("%d -> %d", key, value)));


	}

	@Test(expected = Exception.class)
	public void testCollectorToMapWithDuplicateKey() throws Exception {


		Arrays.stream(names)
				.collect(Collectors.toMap(item -> item.substring(0, 1), Function.identity()));


	}


	@Test()
	public void testCollectorToMapWithDuplicateKeyAndCombineThen() throws Exception {


		Map<String, String> lastWin = Arrays.stream(names)
				.collect(Collectors.toMap(item -> item.substring(0, 1), Function.identity(), (n1, n2) -> n2, HashMap::new));//
// last win


		logger.info(lastWin.toString());


	}


	@Test
	public void testCollectorGroupingBy() throws Exception {
		Map<String, List<String>> collect = Arrays.stream(names).collect(Collectors.groupingBy(item -> item.substring(0, 1)));


		logger.info(collect.toString());


	}

	@Test
	public void testCollectorGroupingByWithMap() throws Exception {

		Map<String, List<String>> listMap = Arrays.stream(names)
				.collect(Collectors.toMap(item -> item.substring(0, 1), item -> new ArrayList(Arrays.asList(item)), (n1,
				                                                                                                     n2) -> {
					boolean b = n1.addAll(n2);
					return n1;
				}));


		logger.info(listMap.toString());

	}


	@Test
	public void testCascadingCollectors() throws Exception {

		Map<String, Long> collect = Arrays.stream(names).collect(Collectors.groupingBy(item -> item.substring(0, 1), Collectors.counting()));


		logger.info(collect.toString());


		Map<String, List<String>> collect2 = Arrays.stream(names)
				.collect(Collectors.groupingBy(item -> item.substring(0, 1),
						Collectors.mapping(Function.identity(), Collectors.toList())));


		Map<String, Set<String>> collect3 = Arrays.stream(names)
				.collect(Collectors.groupingBy(item -> item.substring(0, 1),
						Collectors.mapping(Function.identity(), Collectors.toSet())));


	}


	@Test
	public void testMapStream() throws Exception {

		Map<String, Long> collect = Arrays.stream(names).collect(Collectors.groupingBy(item -> item.substring(0, 1), Collectors.counting()));


		Optional<Map.Entry<String, Long>> max = collect.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue());

		Assert.assertTrue(max.isPresent());

		logger.info(max.get().toString());

	}

	@Test
	public void testSwapMap() throws Exception {

		Map<String, Long> collect = Arrays.stream(names).collect(Collectors.groupingBy(item -> item.substring(0, 1), Collectors.counting()));


		Map<Long, List<String>> map = collect.entrySet()
				.stream()
				.collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())));


		logger.info(map.toString());


	}


	@Test
	public void testStreamOverIndex() throws Exception {


		IntStream.range(0, names.length)
				.forEachOrdered(item -> logger.info(names[item]));










	}
}
