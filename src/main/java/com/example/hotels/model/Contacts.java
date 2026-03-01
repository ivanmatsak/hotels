package com.example.hotels.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Contacts {
    private String phone;
    private String email;
}