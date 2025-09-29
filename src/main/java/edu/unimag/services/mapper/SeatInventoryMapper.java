package edu.unimag.services.mapper;

import edu.unimag.api.dto.SeatInventoryDTOs.SeatInventoryCreateRequest;
import edu.unimag.api.dto.SeatInventoryDTOs.SeatInventoryResponse;
import edu.unimag.api.dto.SeatInventoryDTOs.SeatInventoryUpdateRequest;
import edu.unimag.domain.Enum.Cabin;
import edu.unimag.domain.entity.SeatInventory;

public class SeatInventoryMapper {
	 public static SeatInventory toEntity(SeatInventoryCreateRequest request ) {
	        return SeatInventory.builder().cabin(Cabin.valueOf(request.cabin())).availableSeats(request.availableSeats())
	                .totalSeats(request.availableSeats()).build();
	    }
	    public static SeatInventoryResponse toResponse(SeatInventory seatInventory) {
	        return new SeatInventoryResponse(
	                seatInventory.getId(), seatInventory.getCabin().name(),
	                seatInventory.getTotalSeats(), seatInventory.getAvailableSeats(), seatInventory.getFlight().getId()
	        );
	    }

	    public static void patch(SeatInventory entity, SeatInventoryUpdateRequest update) {
	        if (update.cabin() != null) entity.setCabin(Cabin.valueOf(update.cabin()));
	        if (update.totalSeats() != null) entity.setTotalSeats(update.totalSeats());
	        if (update.availableSeats() != null) entity.setAvailableSeats(update.availableSeats());
	    }
}