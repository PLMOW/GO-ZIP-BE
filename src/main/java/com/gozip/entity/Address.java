package com.gozip.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Address {
    private String city;
    private String town;
    private String street;

    public Address(String city, String town, String street) {
        this.city = city;
        this.town = town;
        this.street = street;
    }
}
