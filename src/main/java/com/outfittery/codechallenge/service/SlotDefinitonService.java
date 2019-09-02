package com.outfittery.codechallenge.service;


import com.outfittery.codechallenge.model.SlotDefinition;
import com.outfittery.codechallenge.repository.SlotDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SlotDefinitonService {

    public static Integer SLOT_DURATION = 30;

    @Autowired
    SlotDefinitionRepository slotDefinitionRepository;

    @Transactional
    public SlotDefinition getActiveSlotDefiniton() {

        SlotDefinition activeSlotDefinition = slotDefinitionRepository.findActiveSlotDefinition();

        if (activeSlotDefinition == null) {
            //If there is no SlotDefinition defined yet, create a new one
            SlotDefinition slotDefinition = new SlotDefinition();
            slotDefinition.setDuration(SLOT_DURATION);
            activeSlotDefinition = slotDefinitionRepository.save(slotDefinition);
        }
        return activeSlotDefinition;
    }


}
