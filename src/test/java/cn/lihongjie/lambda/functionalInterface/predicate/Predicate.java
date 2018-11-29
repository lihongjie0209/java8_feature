package cn.lihongjie.lambda.functionalInterface.predicate;

import java.util.Objects;

/**
 * @author 982264618@qq.com
 */
public interface Predicate<T> {


	public boolean test(T toTest);

	default Predicate<T> not() {
		return toTest -> ! this.test(toTest);
	}

	default Predicate<T> and(Predicate<T> another) {
		Objects.requireNonNull(another);
		return (T totest) -> test(totest) && another.test(totest);
	}

	default Predicate<T> xor(Predicate<T> another) {



		return (T totest) -> test(totest) ^ another.test(totest);
	}
}
