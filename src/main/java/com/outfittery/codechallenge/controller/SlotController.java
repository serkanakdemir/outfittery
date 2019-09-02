package com.outfittery.codechallenge.controller;

import com.outfittery.codechallenge.model.Slot;
import com.outfittery.codechallenge.model.StylistSlot;
import com.outfittery.codechallenge.service.SlotService;
import com.outfittery.codechallenge.service.StylistSlotService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("api/v1.0/slot")
public class SlotController {

    @Autowired
    private SlotService slotService;

    @Autowired
    private StylistSlotService stylistSlotService;

    @ApiOperation(value = "Get Available Slots to make an appointment", response = Slot.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully listed available time-slots")
    }
    )
    @GetMapping("/available-slots")
    @ResponseStatus(HttpStatus.OK)
    public List<Slot> getAvailableSlots(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        return slotService.getAvailableSlots(date);
    }


    @ApiOperation(value = "Make an appointment", response = Slot.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully appointment created")
    }
    )
    @PostMapping("/appointment")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StylistSlot> makeAppointment(@RequestParam Long customerId, @RequestParam Long slotId) {
        final Optional<StylistSlot> stylistSlot = stylistSlotService.makeAppointment(customerId, slotId);

        if (stylistSlot.isPresent())
        {
            return new ResponseEntity<>(stylistSlot.get(),HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null,null,HttpStatus.NOT_FOUND);
    }
}
