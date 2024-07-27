package com.phonebook.services.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mst_phonebook")
public class Mst_Phonebook extends BaseStandartModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mst_phonebook_seq")
    @SequenceGenerator(name = "mst_phonebook_seq", sequenceName = "mst_phonebook_seq", allocationSize = 1)
	private Long id;

    @NotBlank(message = "Name Name is required")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Subject name should contain only alphabetic characters")
    private String name;

    @NotBlank(message = "Number is required")
    @Pattern(regexp = "^\\+62[0-9]*$", message = "The input should start with +62 and contain only numeric characters")
    @Column(unique = true)
    private String number;

	
}
