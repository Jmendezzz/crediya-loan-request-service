package co.com.crediya.api.utils;

import java.util.Optional;

public class QueryParamParser {

    public static Optional<Integer> toInteger(Optional<String> param) {
        return param.filter(v -> !v.isBlank()).map(Integer::valueOf);
    }

    public static Optional<Long> toLong(Optional<String> param) {
        return param.filter(v -> !v.isBlank()).map(Long::valueOf);
    }

    public static Optional<Double> toDouble(Optional<String> param) {
        return param.filter(v -> !v.isBlank()).map(Double::valueOf);
    }

    public static Optional<String> toStringValue(Optional<String> param) {
        return param.filter(v -> !v.isBlank());
    }
}
