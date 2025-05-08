package com.example.booking_service.mapper;

import com.example.booking_service.dto.request.BookingSeatDetailRequest;
import com.example.booking_service.dto.response.BookingSeatDetailResponse;
import com.example.booking_service.entity.BookingSeatDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingSeatDetailMapper {
    BookingSeatDetail toBookingSeatDetail(BookingSeatDetailRequest bookingSeatDetailRequest);
    BookingSeatDetailResponse toBookingSeatDetailResponse(BookingSeatDetail bookingSeatDetail);
}
