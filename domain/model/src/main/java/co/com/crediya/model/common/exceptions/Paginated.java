package co.com.crediya.model.common.exceptions;

import java.util.List;


public record Paginated<T>(
        List<T> items,
        long totalItems,
        int page,
        int size
) {

}