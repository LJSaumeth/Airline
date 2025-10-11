package edu.unimag.services.imp;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unimag.api.dto.BookingDTOs.*;
import edu.unimag.domain.Enum.Cabin;
import edu.unimag.domain.entity.BookingItem;
import edu.unimag.domain.repositories.BookingItemRepository;
import edu.unimag.domain.repositories.BookingRepository;
import edu.unimag.domain.repositories.FlightRepository;
import edu.unimag.exceptions.NotFoundException;
import edu.unimag.services.BookingItemService;
import edu.unimag.services.mapper.BookingMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class BookingItemServiceImp implements BookingItemService {
    private final BookingItemRepository bookingItemRepository;
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;

    @Override @Transactional
    public BookingItemResponse addBookingItem(Long booking_id, BookingItemCreateRequest request) {
        var flight =  flightRepository.findById(request.flight_id()).orElseThrow(
                () -> new NotFoundException("Flight %d not found".formatted(request.flight_id()))
        );
        var booking = bookingRepository.findById(booking_id).orElseThrow(
                () -> new NotFoundException("Booking %d not found".formatted(booking_id))
        );

        var bookingItem = BookingItem.builder().cabin(Cabin.valueOf(request.cabin())).price(request.price()).segmentOrder(request.segmentOrder())
                .flight(flight).build();
        BookingMapper.addItem(bookingItem, booking);

        return BookingMapper.toItemResponse(bookingItem);
    }

    @Override
    public BookingItemResponse getBookingItem(Long id) {
        return bookingItemRepository.findById(id).map(BookingMapper::toItemResponse).orElseThrow(
                () -> new NotFoundException("Booking Item %d not found".formatted(id))
        );
    }

    @Override @Transactional
    public BookingItemResponse updateBookingItem(Long id, BookingItemUpdateRequest request) {
        var bookingItem = bookingItemRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Booking Item %d not found".formatted(id))
        );
        BookingMapper.itemPatch(bookingItem, request);

        return BookingMapper.toItemResponse(bookingItem);
    }

    @Override @Transactional
    public void deleteBookingItem(Long id) {
        bookingItemRepository.deleteById(id);
    }

    @Override
    public List<BookingItemResponse> listBookingItemsByBooking(Long booking_id) {
        var booking = bookingRepository.findById(booking_id).orElseThrow(
                () -> new NotFoundException("Booking %d not found".formatted(booking_id))
        );
        return bookingItemRepository.findByBooking_IdOrderBySegmentOrder(booking.getId()).stream().map(BookingMapper::toItemResponse).toList();
    }

    @Override
    public Long countReservedSeatsByFlightAndCabin(Long flight_id, String cabin) {
        var flight = flightRepository.findById(flight_id).orElseThrow(
                () -> new NotFoundException("Flight %d not found".formatted(flight_id))
        );
        return bookingItemRepository.countReservedSeatsByFlight_IdAndCabin(flight.getId(), Cabin.valueOf(cabin));
    }

    @Override
    public BigDecimal calculateTotalPriceByBooking(Long booking_id) {
        var booking = bookingRepository.findById(booking_id).orElseThrow(
                () -> new NotFoundException("Booking %d not found".formatted(booking_id))
        );
        return bookingItemRepository.calculateTotalByBooking_Id(booking.getId());
    }
}