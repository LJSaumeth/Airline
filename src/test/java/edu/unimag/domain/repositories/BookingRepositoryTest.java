package edu.unimag.domain.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Pageable;

import edu.unimag.domain.Enum.Cabin;
import edu.unimag.domain.entity.Airline;
import edu.unimag.domain.entity.Airport;
import edu.unimag.domain.entity.Booking;
import edu.unimag.domain.entity.BookingItem;
import edu.unimag.domain.entity.Flight;
import edu.unimag.domain.entity.Passenger;


class BookingRepositoryTest extends AbstractRepository{
    @Autowired BookingRepository bookingRepository;
    @Autowired private TestEntityManager testEntityManager;

    private final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

    @BeforeEach
    void setUp() {
        //Given
        var passenger = Passenger.builder()
                .fullName("Carlos Guerrero")
                .email("guerre@example.com").build();
        var passenger_2 = Passenger.builder()
                .fullName("Pedro Saumeth")
                .email("ps@example.com").build();

        var airline = Airline.builder()
                .code("AVI").name("Avianca").build();

        var origin_airport = Airport.builder()
                .code("SMA").name("Simón Bolivar").city("Santa Marta").build();

        var destination_airport = Airport.builder()
                .code("BOG").name("EL DORADO").city("Bogota").build();

        var booking = Booking.builder()
                .createdAt(now.minusDays(3)).updatedAt(now).passenger(passenger).build();

        var booking_2 = Booking.builder()
                .createdAt(now.minusDays(2)).updatedAt(now).passenger(passenger_2).build();

        var flight = Flight.builder()
                .airline(airline).origin(origin_airport).destination(destination_airport)
                .departureTime(now.plusHours(1)).arrivalTime(now.plusHours(3)).build();

        var booking_item_1 = BookingItem.builder()
                .cabin(Cabin.ECONOMY).price(new BigDecimal("478000.00")).segmentOrder(5)
                .booking(booking).flight(flight).build();
        var booking_item_2 = BookingItem.builder()
                .cabin(Cabin.BUSINESS).price(new BigDecimal("1478000.00")).segmentOrder(5)
                .booking(booking).flight(flight).build();
        var booking_item_3 = BookingItem.builder()
                .cabin(Cabin.BUSINESS).price(new BigDecimal("1478000.00")).segmentOrder(5)
                .booking(booking_2).flight(flight).build();

        testEntityManager.persistAndFlush(passenger);
        testEntityManager.persistAndFlush(airline);
        testEntityManager.persistAndFlush(origin_airport);
        testEntityManager.persistAndFlush(destination_airport);
        testEntityManager.persistAndFlush(flight);
        testEntityManager.persistAndFlush(booking);
        testEntityManager.persistAndFlush(booking_2);
        testEntityManager.persistAndFlush(booking_item_1);
        testEntityManager.persistAndFlush(booking_item_2);
        testEntityManager.persistAndFlush(booking_item_3);
    }

    @Test @DisplayName("Booking: find by createdAt between two dates")
    void shouldFindByCreatedAtBetween() {
        //When
        var loaded = bookingRepository.findByCreatedAtBetween(now.minusDays(4), now.minusDays(1));

        //Then
        assertThat(loaded).isNotEmpty();
        assertThat(loaded.getFirst().getCreatedAt())
            .isBetween(now.minusDays(4), now.minusDays(1));

    }

    @Test  @DisplayName("Booking: find by updatedAt between two dates")
    void shouldFindByUpdatedAtBetween() {
        //When
        var loaded = bookingRepository.findByUpdatedAtBetween(now.minusDays(2), now.minusDays(1));

        //Then
        assertThat(loaded).isNotEmpty();
        assertThat(loaded.getFirst().getUpdatedAt())
                .isBetween(now.minusDays(2), now.minusDays(1));
    }

    @Test @DisplayName("Booking: find bookings by passenger email ignore case and ordered by created at desc")
    void shouldFindByPassengerEmailIgnoreCaseAndOrderedByCreatedAtDesc(){
        //When
        var loaded = bookingRepository.findByPassenger_EmailIgnoreCaseOrderByCreatedAtDesc(
            "nSauMEtH@exAmPle.cOm", Pageable.ofSize(5));

        //Then
        assertThat(loaded).hasSize(2);
        assertThat(loaded).extracting(booking -> booking.getPassenger().getEmail())
                .containsOnly("nsaumeth@example.com");
      //This method verifies orderByCreatedAtDesc
        assertThat(loaded.getContent())
            .extracting(Booking::getCreatedAt)
            .isSortedAccordingTo(Comparator.reverseOrder());
    }
}
