package edu.unimag.services.imp;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unimag.api.dto.BookingDTOs.*;
import edu.unimag.domain.Enum.Cabin;
import edu.unimag.domain.entity.Booking;
import edu.unimag.domain.entity.BookingItem;
import edu.unimag.domain.entity.Flight;
import edu.unimag.domain.repositories.*;
import edu.unimag.exceptions.NotFoundException;
import edu.unimag.services.BookingItemService;
import edu.unimag.services.mapper.BookingMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingItemServiceImp implements BookingItemService {
    private final BookingItemRepository bookingItemRepository;
    private final FlightRepository      flightRepository;
    private final BookingRepository     bookingRepository;
    private final BookingMapper         bookingMapper;

    @Override
    @Transactional
    public BookingItemResponse addBookingItem(
        @Nonnull Long bookingId,
        @Nonnull Long flightId,
        BookingItemCreateRequest request
    ) {
        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new NotFoundException("Flight %d not found".formatted(flightId)));
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new NotFoundException("Booking %d not found".formatted(bookingId)));

        // Map DTO → entity and wire relationships
        BookingItem item = bookingMapper.toItemEntity(request);
        item.setFlight(flight);
        item.setBooking(booking);

        // Persist the new item
        BookingItem saved = bookingItemRepository.save(item);

        // Maintain both sides if you use bi-directional
        booking.getItems().add(saved);

        return bookingMapper.toItemResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingItemResponse getBookingItem(@Nonnull Long id) {
        return bookingItemRepository.findById(id)
            .map(bookingMapper::toItemResponse)
            .orElseThrow(() -> new NotFoundException("Booking Item %d not found".formatted(id)));
    }

    @Override
    @Transactional
    public BookingItemResponse updateBookingItem(
        @Nonnull Long id,
        BookingItemUpdateRequest request,
        @Nonnull Long flightId
    ) {
        BookingItem item = bookingItemRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Booking Item %d not found".formatted(id)));

        // Null-safe patch
        bookingMapper.patch(request, item);

        // Reassign flight
        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new NotFoundException("Flight %d not found".formatted(flightId)));
        item.setFlight(flight);

        // Persist changes
        BookingItem updated = bookingItemRepository.save(item);
        return bookingMapper.toItemResponse(updated);
    }

    @Override
    @Transactional
    public void deleteBookingItem(@Nonnull Long id) {
        bookingItemRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingItemResponse> listBookingItemsByBooking(@Nonnull Long bookingId) {
        bookingRepository.findById(bookingId)
            .orElseThrow(() -> new NotFoundException("Booking %d not found".formatted(bookingId)));

        return bookingItemRepository
            .findByBooking_IdOrderBySegmentOrder(bookingId)
            .stream()
            .map(bookingMapper::toItemResponse)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Long countReservedSeatsByFlightAndCabin(
        @Nonnull Long flightId,
        String cabin
    ) {
        flightRepository.findById(flightId)
            .orElseThrow(() -> new NotFoundException("Flight %d not found".formatted(flightId)));

        return bookingItemRepository
            .countReservedSeatsByFlight_IdAndCabin(flightId, Cabin.valueOf(cabin));
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalPriceByBooking(@Nonnull Long bookingId) {
        bookingRepository.findById(bookingId)
            .orElseThrow(() -> new NotFoundException("Booking %d not found".formatted(bookingId)));

        return bookingItemRepository.calculateTotalByBooking_Id(bookingId);
    }
}