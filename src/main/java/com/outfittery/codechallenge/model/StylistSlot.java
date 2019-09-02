package com.outfittery.codechallenge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.Data;

@Data
@Entity
@Table(name = "STYLIST_SLOT")
public class StylistSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("stylistSlotList")
    private Slot slot;

    @ManyToOne
    private Stylist stylist;

    private Long customerId;

    @Version
    private Integer version;
}
