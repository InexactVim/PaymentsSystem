package me.inexactvim.paymentssystem.util.function;

@FunctionalInterface
public interface ThrowableConsumer<T> {

    void accept(T t) throws Exception;

}
