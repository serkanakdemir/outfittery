package com.outfittery.codechallenge.service;


import static com.outfittery.codechallenge.scheduler.SlotScheduler.DATE_RANGE;

import com.outfittery.codechallenge.model.Slot;
import com.outfittery.codechallenge.model.Stylist;
import com.outfittery.codechallenge.model.StylistSlot;
import com.outfittery.codechallenge.repository.SlotDefinitionRepository;
import com.outfittery.codechallenge.repository.SlotRepository;
import com.outfittery.codechallenge.repository.StylistRepository;
import com.outfittery.codechallenge.repository.StylistSlotRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StylistService {

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private StylistSlotService stylistSlotService;

    @Autowired
    StylistRepository stylistRepository;

    public List<Stylist> findAvailableStylists(){
        return stylistRepository.findAvailableStylists();
    }

    @Transactional
    public Stylist save(Stylist stylist) {
        final Stylist savedStylist = stylistRepository.save(stylist);
        stylistSlotService.prepareStylistSlots(savedStylist);
        return savedStylist;
    }



}


