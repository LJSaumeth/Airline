package edu.unimag.domain.entity;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "airlines")
public class Airline {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "airline_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 2)
    private String code;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "flight", fetch = FetchType.LAZY)
    private List<Flight> flights;

    public void addFlight(Flight flight) {
        flights.add(flight);
        flight.setAirline(this);
    }
}
