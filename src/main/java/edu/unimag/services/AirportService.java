package edu.unimag.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.unimag.api.dto.AirportDTOs.*;

public interface AirportService {
	AirportResponse createAirport(AirportCreateRequest request);
    AirportResponse getAirport(Long id);
    AirportResponse getAirportByCode(String code);
    List<AirportResponse> getCityAirports(String city);
    AirportResponse updateAirport(Long id, AirportUpdateRequest request);
    void deleteAirport(Long id);
    Page<AirportResponse> listAllAirports(Pageable pageable);
}
