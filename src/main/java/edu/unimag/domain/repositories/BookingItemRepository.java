package edu.unimag.domain.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.unimag.domain.Enum.Cabin;
import edu.unimag.domain.entity.BookingItem;


public interface BookingItemRepository extends JpaRepository<BookingItem, Long> {
    List<BookingItem> findByBooking_IdOrderBySegmentOrder(Long booking_Id);

    @Query("""
    SELECT COALESCE(SUM(bi.price), 0)
    FROM BookingItem bi
    WHERE bi.booking.id = :booking_Id
""")
    BigDecimal calculateTotalByBooking_Id(@Param("booking_Id") Long booking_Id);

    @Query("""
    SELECT COUNT(bi)
    FROM BookingItem bi
    WHERE bi.flight.id = :flight_Id AND bi.cabin = :cabin
""")
    Long countReservedSeatsByFlightAndCabin(@Param("flight_Id") Long flight_Id, @Param("cabin") Cabin cabin);
}
