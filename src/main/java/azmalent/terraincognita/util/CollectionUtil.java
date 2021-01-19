package azmalent.terraincognita.util;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtil {
    @SafeVarargs
    public static <T> List<T> nonNullList(T... values) {
        List<T> list = new ArrayList<>();
        for (T t : Lists.newArrayList(values)) {
            if (t != null) {
                list.add(t);
            }
        }
        return list;
    }
}
