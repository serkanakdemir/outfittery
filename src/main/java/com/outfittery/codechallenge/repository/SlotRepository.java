package com.outfittery.codechallenge.repository;

import com.outfittery.codechallenge.model.Slot;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SlotRepository extends JpaRepository<Slot, Long> {

    @Query("select a from Slot a where a.date >= :startDate and a.date <= :endDate")
    List<Slot> findAllSlotsByDateRange(
        @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    @Query(value = "select * from SLOT s where s.date = :date and exists (select 1 from STYLIST_SLOT ss where ss.SLOT_ID=s.ID and ss.CUSTOMER_ID is null)",nativeQuery = true)
    List<Slot> findAvailableSlots(
        @Param("date") LocalDate date);

}
