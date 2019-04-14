package com.masivian.example.treeoperations.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Node {
    private Integer id;
    private String color;
    private Node left;
    private Node right;

    public Node(Integer id) {
        this.id = id;
        this.color = "white";
    }
}
