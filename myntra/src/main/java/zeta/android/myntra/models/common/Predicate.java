package zeta.android.myntra.models.common;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface Predicate<T> {

    boolean test(T t);

}
