package com.bizware.bizware.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "visitor")
public class Visitor {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public Visitor() { }

    public Visitor(String name) {
        this.name = name;
    }
}
