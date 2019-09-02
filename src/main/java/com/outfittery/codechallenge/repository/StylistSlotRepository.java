package com.outfittery.codechallenge.repository;

import com.outfittery.codechallenge.model.StylistSlot;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StylistSlotRepository extends JpaRepository<StylistSlot, Long> {

    @Query(value = "select * from STYLIST_SLOT ss where ss.SLOT_ID = :slot_id and ss.CUSTOMER_ID is null",nativeQuery = true)
    List<StylistSlot> findAvailableStylistSlots(
        @Param("slot_id") Long slot_id);
}
