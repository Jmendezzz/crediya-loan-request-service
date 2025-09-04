package co.com.crediya.model.common.results;

import java.util.List;


public record Paginated<T>(
        List<T> items,
        long totalItems,
        int page,
        int size
) {

}