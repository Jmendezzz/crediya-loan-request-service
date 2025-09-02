package co.com.crediya.api.dto;

import java.util.List;

public record PaginatedResponseDto<T>(
        List<T> items,
        long totalItems,
        int page,
        int size
) { }
