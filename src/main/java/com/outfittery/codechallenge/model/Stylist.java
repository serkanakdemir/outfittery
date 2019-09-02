package com.outfittery.codechallenge.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@Entity
@Table(name = "STYLIST")
public class Stylist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Stylist name can not be blank")
    @Size(message = "name length should be min 2 and max 50", min = 2, max = 50)
    private String name;

    @Email
    @NotBlank(message = "Stylist email can not be blank")
    @Size(message = "name length should be max 50", max = 50)
    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private StylistState state=StylistState.ACTIVE;

}
