package com.superhero.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "HEROES")
public class Hero {
    @Id
    private Long id;
    private String name;

}
