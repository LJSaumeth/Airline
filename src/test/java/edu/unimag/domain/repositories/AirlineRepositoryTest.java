package edu.unimag.domain.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.unimag.domain.entity.Airline;

class AirlineRepositoryTest extends AbstractRepository{
    @Autowired AirlineRepository airlineRepository;

    @Test @DisplayName("Airline: find by code")
    void shouldFindByCode() {
        //Given
        airlineRepository.save(Airline.builder().code("DC").name("despegar.com").build());
        airlineRepository.save(Airline.builder().code("BG").name("Avianca").build());

        //When
        Optional<Airline> airline = airlineRepository.findByCode("DC");
        //Then
        assertThat(airline).isPresent();


    }
}
