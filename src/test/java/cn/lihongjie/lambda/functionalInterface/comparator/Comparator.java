package cn.lihongjie.lambda.functionalInterface.comparator;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author 982264618@qq.com
 */
public interface Comparator<T> {


	static <I, O extends Comparable<O>> Comparator<I> comparing(Function<I, O> keyExtractor) {

		Objects.requireNonNull(keyExtractor);
		return (o1, o2) -> keyExtractor.apply(o1).compareTo(keyExtractor.apply(o2));

	}

	int compare(T o1, T o2);
}
