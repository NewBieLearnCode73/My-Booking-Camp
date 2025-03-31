package com.example.trip_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationResponseDTO<T> {
    private List<T> items;
    private int currentPage;
    private long totalItems;
    private int totalPages;
    private int pageSize;
}
