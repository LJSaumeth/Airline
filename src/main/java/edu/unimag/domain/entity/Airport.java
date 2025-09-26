package edu.unimag.domain.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "airports")
@Builder
public class Airport {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "airport_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 3)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;
    
    @OneToMany (mappedBy = "Origin")
    @Builder.Default
    private Set<Flight> flights = new HashSet<>();

    @OneToMany (mappedBy = "destination")
    @Builder.Default
    private Set<Flight> destinations = new HashSet<>();
}
