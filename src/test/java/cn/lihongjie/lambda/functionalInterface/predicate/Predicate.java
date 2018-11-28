package cn.lihongjie.lambda.functionalInterface.predicate;

/**
 * @author 982264618@qq.com
 */
public interface Predicate<T> {


	public boolean test(T toTest);

	default Predicate<T> not() {
		return toTest -> ! this.test(toTest);
	}
}
