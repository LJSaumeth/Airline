package edu.unimag.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.unimag.api.dto.AirlineDTOs.AirlineCreateRequest;
import edu.unimag.api.dto.AirlineDTOs.AirlineResponse;
import edu.unimag.api.dto.AirlineDTOs.AirlineUpdateRequest;


public interface AirlineService {
	AirlineResponse createAirline(AirlineCreateRequest request);
    AirlineResponse getAirline(Long id);
    AirlineResponse getAirlineByCode(String code);
    AirlineResponse updateAirline(Long id, AirlineUpdateRequest request);
    void deleteAirline(Long id);
    List<AirlineResponse> listAllAirlines();
    Page<AirlineResponse> listAllAirlinesPage(Pageable pageable);

}
