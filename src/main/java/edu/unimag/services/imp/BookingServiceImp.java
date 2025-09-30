package edu.unimag.services.imp;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unimag.api.dto.BookingDTOs.*;
import edu.unimag.domain.entity.Booking;
import edu.unimag.domain.repositories.BookingRepository;
import edu.unimag.domain.repositories.PassengerRepository;
import edu.unimag.exceptions.NotFoundException;
import edu.unimag.services.BookingService;
import edu.unimag.services.mapper.BookingMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class BookingServiceImp implements BookingService{
	private final BookingRepository bookingRepository;
    private final PassengerRepository passengerRepository;

    @Override @Transactional
    public BookingResponse createBooking(BookingCreateRequest request) {
        var passenger = passengerRepository.findById(request.passenger_id()).orElseThrow(
                () -> new NotFoundException("Passenger %d not found.".formatted(request.passenger_id()))
        );
        var booking = Booking.builder().createdAt(OffsetDateTime.now()).passenger(passenger).build();
        return BookingMapper.toResponse(bookingRepository.save(booking));
    }

    @Override
    public BookingResponse getBooking(@Nonnull Long id) {
        return bookingRepository.findById(id).map(BookingMapper::toResponse).orElseThrow(
                () -> new NotFoundException("Booking %d not found.".formatted(id))
        );
    }

    @Override
    public List<BookingResponse> listBookingsBetweenDates(@Nonnull OffsetDateTime start, @Nonnull OffsetDateTime end) {
        if (start.isAfter(end)) throw new IllegalArgumentException("Start date is after end date.");
        return bookingRepository.findByCreatedAtBetween(start, end).stream()
                .map(BookingMapper::toResponse).toList();
    }

    @Override
    public Page<BookingResponse> listBookingsByPassengerEmailAndOrderedMostRecently(@Nonnull String passenger_email, Pageable pageable) {
        return bookingRepository.findByPassenger_EmailIgnoreCaseOrderByCreatedAtDesc(passenger_email,
                pageable).map(BookingMapper::toResponse);
    }

    @Override
    public BookingResponse getBookingWithAllDetails(@Nonnull Long id) {
        return bookingRepository.findById(id).map(BookingMapper::toResponse).orElseThrow(
                () -> new NotFoundException("Booking %d not found.".formatted(id))
        );
    }

    @Override @Transactional
    public BookingResponse updateBooking(@Nonnull Long id, Long passenger_id) {
        var booking = bookingRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Booking %d not found.".formatted(id))
        );
        if (passenger_id != null) {
            var new_passenger = passengerRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Passenger %d not found.".formatted(id))
            );
            booking.setPassenger(new_passenger);
        }
        return BookingMapper.toResponse(bookingRepository.save(booking));
    }

    @Override @Transactional
    public void deleteBooking(@Nonnull Long id) {
        bookingRepository.deleteById(id);
    }
}
