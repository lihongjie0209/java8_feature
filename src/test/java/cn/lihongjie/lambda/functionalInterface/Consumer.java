package cn.lihongjie.lambda.functionalInterface;

/**
 * @author 982264618@qq.com
 */
@FunctionalInterface
public interface Consumer<T> {


	void consume(T o);


	default Consumer<T> then(Consumer<T> second) {

		return o -> {
			this.consume(o);
			second.consume(o);
		};
	}
}
