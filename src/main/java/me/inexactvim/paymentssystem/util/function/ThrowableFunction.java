package me.inexactvim.paymentssystem.util.function;

@FunctionalInterface
public interface ThrowableFunction<T, R> {

    R apply(T t) throws Exception;

}
