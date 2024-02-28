package com.example.money.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "currencies")
@Getter
@Setter
@AllArgsConstructor
public class Currency {
    @Id
    @Column(name="name")
    private String name;
    @Column(name="value")
    private float value;

}
