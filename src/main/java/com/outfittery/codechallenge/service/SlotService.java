package com.outfittery.codechallenge.service;


import com.outfittery.codechallenge.model.Slot;
import com.outfittery.codechallenge.repository.SlotRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlotService {

    @Autowired
    SlotRepository slotRepository;

    public List<Slot> getAvailableSlots(LocalDate date) {
        return slotRepository.findAvailableSlots(date);
    }

    public List<Slot> findAllSlotsByDateRange(
        LocalDate startDate, LocalDate endDate) {
        return slotRepository.findAllSlotsByDateRange(startDate, endDate);
    }

    public Slot save(Slot slot) {
        return slotRepository.save(slot);
    }


}
