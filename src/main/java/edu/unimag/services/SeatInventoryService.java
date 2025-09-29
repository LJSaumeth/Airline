package edu.unimag.services;

import java.util.List;
import edu.unimag.api.dto.SeatInventoryDTOs.*;


public interface SeatInventoryService {
    SeatInventoryResponse createSeatInventory(Long flight_id, SeatInventoryCreateRequest request);
    SeatInventoryResponse getSeatInventory(Long id);
    SeatInventoryResponse updateSeatInventory(Long id, SeatInventoryUpdateRequest request);
    void deleteSeatInventory(Long id);

    List<SeatInventoryResponse> listSeatInventoriesByFlight(Long flight_id);
    SeatInventoryResponse getSeatInventoryByFlightAndCabin(Long flight_id, String cabin);
    boolean existsSeatInventoryByFlightAndCabinWithMinAvailableSeats(Long flight_id, String cabin, Integer min);

}
