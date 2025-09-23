package edu.unimag.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "passenger_profiles")
public class PassengerProfile {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "passenger_profile_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, name = "country_code")
    private String countryCode;
}
