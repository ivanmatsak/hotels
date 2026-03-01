package com.example.hotels.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class ArrivalTime {
    @NotBlank(message = "Arrival time is required")
    @JsonProperty("checkIn")
    private String checkIn;
    @JsonProperty("checkOut")
    private String checkOut;
}
