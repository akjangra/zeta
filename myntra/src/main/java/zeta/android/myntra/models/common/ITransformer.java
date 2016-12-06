package zeta.android.myntra.models.common;

public interface ITransformer<T, R> {
    R transform(T t);
}
