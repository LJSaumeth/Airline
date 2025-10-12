package edu.unimag.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import edu.unimag.domain.entity.Airline;
import edu.unimag.domain.repositories.AirlineRepository;
import edu.unimag.services.imp.AirlineServiceImp;
import edu.unimag.api.dto.AirlineDTOs.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AirlineServiceImpTest {
    @Mock
    AirlineRepository airlineRepository;

    @InjectMocks
    AirlineServiceImp airlineService;

    @Test
    void shouldCreateAirlineAndMapToResponse(){
        when(airlineRepository.save(any())).thenAnswer(inv -> {
            Airline a = inv.getArgument(0);
            a.setId(1L); return a;
        });

        var response = airlineService.createAirline(new AirlineCreateRequest("XD", "Despegar.com"));

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.code()).isEqualTo("XD");
        assertThat(response.name()).isEqualTo("Despegar.com");
    }

    @Test
    void shouldUpdateAirlineAndMapToResponse(){
        when(airlineRepository.findById(1L)).thenReturn(Optional.of(Airline.builder().id(1L).code("XD").name("Despegar.com").build()));
        when(airlineRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var response = airlineService.updateAirline(1L, new AirlineUpdateRequest(null, "Wingo"));

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.code()).isEqualTo("XD");
        assertThat(response.name()).isEqualTo("Wingo");
    }

    @Test
    void shouldListAllAirlinesInPages(){
        when(airlineRepository.findAll(Pageable.ofSize(4))).thenReturn(new PageImpl<>(List.of(
                Airline.builder().id(1L).code("XD").name("Despegar.com").build(),
                Airline.builder().id(2L).code("DD").name("Wingo").build(),
                Airline.builder().id(3L).code("XL").name("Trivago").build(),
                Airline.builder().id(4L).code("VI").name("LATAM Airlines").build()
        )));

        var response = airlineService.listAllAirlinesPage(Pageable.ofSize(4));

        assertThat(response).hasSize(4);
        assertThat(response).extracting(AirlineResponse::id).containsExactlyInAnyOrder(1L, 2L, 3L, 4L);
    }
}