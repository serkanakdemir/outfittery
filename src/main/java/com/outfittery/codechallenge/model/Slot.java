package com.outfittery.codechallenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "SLOT")
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private SlotDefinition slotDefinition;

    private LocalTime start;

    private LocalTime finish;

    private LocalDate date;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stylist",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("slot")
    private List<StylistSlot> stylistSlotList = new ArrayList<>();
}
