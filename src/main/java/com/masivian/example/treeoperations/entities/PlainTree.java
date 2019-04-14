package com.masivian.example.treeoperations.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
public class PlainTree {
    @Id
    @GeneratedValue
    private Integer id;

    @JsonProperty("structure")
    @NotEmpty
    @Pattern(regexp = "[0-9]+(([,\n])[0-9]+)*")
    private String structure;
}
