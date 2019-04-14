package com.masivian.example.treeoperations.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LCARequest {
    @JsonProperty("first_node")
    private Integer firstNode;
    @JsonProperty("second_node")
    private Integer secondNode;
}
