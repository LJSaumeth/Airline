package edu.unimag.services;

import java.math.BigDecimal;
import java.util.List;

import edu.unimag.api.dto.BookingDTOs.*;

public interface BookingItemService {
    BookingItemResponse addBookingItem(Long booking_id, Long flight_id, BookingItemCreateRequest request);
    BookingItemResponse getBookingItem(Long id);
    BookingItemResponse updateBookingItem(Long id, BookingItemUpdateRequest request, Long flight_id);
    void deleteBookingItem(Long id);
    
    List<BookingItemResponse> listBookingItemsByBooking(Long booking_id);
    Long countReservedSeatsByFlightAndCabin(Long flight_id, String cabin);
    BigDecimal calculateTotalPriceByBooking(Long booking_id);
}
