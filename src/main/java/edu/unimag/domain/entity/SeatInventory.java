package edu.unimag.domain.entity;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "seats_inventory")
public class SeatInventory {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seat_inventory_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Cabin cabin;

    @Column(nullable = false, name = "total_seats")
    private Integer totalSeats;

    @Column(nullable = false, name = "available_seats")
    private Integer availableSeats;

    @ManyToOne @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;
}
