package com.outfittery.codechallenge.repository;

import com.outfittery.codechallenge.model.Stylist;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StylistRepository extends JpaRepository<Stylist, Long> {

    @Query("select a from Stylist a where a.state=com.outfittery.codechallenge.model.StylistState.ACTIVE")
    List<Stylist> findAvailableStylists();
}
