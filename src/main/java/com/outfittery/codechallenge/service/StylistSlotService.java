package com.outfittery.codechallenge.service;


import static com.outfittery.codechallenge.scheduler.SlotScheduler.DATE_RANGE;

import com.outfittery.codechallenge.model.Slot;
import com.outfittery.codechallenge.model.Stylist;
import com.outfittery.codechallenge.model.StylistSlot;
import com.outfittery.codechallenge.repository.SlotRepository;
import com.outfittery.codechallenge.repository.StylistSlotRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StylistSlotService {

    @Autowired
    StylistSlotRepository stylistSlotRepository;

    @Autowired
    SlotService slotService;

    @Transactional
    public Optional<StylistSlot> makeAppointment(Long customerId, Long slotId) {

        final List<StylistSlot> availableStylistSlots = stylistSlotRepository.findAvailableStylistSlots(slotId);

        if (availableStylistSlots.isEmpty()) {
            return Optional.empty();
        }

        final StylistSlot stylistSlot = availableStylistSlots.get(0);
        stylistSlot.setCustomerId(customerId);
        return Optional.of(stylistSlotRepository.save(stylistSlot));
    }

    @Transactional
    public void prepareStylistSlots(Stylist stylist) {
        LocalDate localDate = LocalDate.now();

        final List<Slot> allSlotsByDateRange = slotService
            .findAllSlotsByDateRange(localDate, localDate.plusDays(DATE_RANGE));

        allSlotsByDateRange.forEach(slot -> {
            StylistSlot stylistSlot = new StylistSlot();
            stylistSlot.setStylist(stylist);
            stylistSlot.setSlot(slot);
            stylistSlotRepository.save(stylistSlot);
        });
    }


}
