package com.outfittery.codechallenge.repository;

import com.outfittery.codechallenge.model.Slot;
import com.outfittery.codechallenge.model.SlotDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SlotDefinitionRepository extends JpaRepository<SlotDefinition, Long> {

    @Query("select a from SlotDefinition a where a.validUntil is null")
    SlotDefinition findActiveSlotDefinition();

}
