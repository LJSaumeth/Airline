package edu.unimag.domain.entity;

import java.math.BigDecimal;

import edu.unimag.domain.Enum.Cabin;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "booking_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingItem {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "booking_item_id")
    private Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Cabin cabin;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false, name = "segment_order")
    private Integer segmentOrder;

    @ManyToOne(optional = false)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne(optional = false)
    @JoinColumn(name = "flight_id")
    private Flight flight;
}
