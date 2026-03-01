package com.example.hotels.mapper;

import com.example.hotels.dto.HotelFullDto;
import com.example.hotels.dto.HotelShortDto;
import com.example.hotels.model.Address;
import com.example.hotels.model.Amenity;
import com.example.hotels.model.Hotel;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class HotelMapper {

    public HotelShortDto toShortDto(Hotel hotel) {
        if (hotel == null) return null;

        return new HotelShortDto(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                formatAddress(hotel.getAddress()),
                hotel.getContacts() != null ? hotel.getContacts().getPhone() : null
        );
    }

    public HotelFullDto toFullDto(Hotel hotel) {
        if (hotel == null) return null;

        return new HotelFullDto(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                hotel.getBrand(),
                hotel.getAddress(),
                hotel.getContacts(),
                hotel.getArrivalTime(),
                hotel.getAmenities().stream()
                        .map(Amenity::getName)
                        .collect(Collectors.toList())
        );
    }

    public Hotel toEntity(HotelFullDto dto) {
        if (dto == null) return null;

        Hotel hotel = new Hotel();
        hotel.setName(dto.getName());
        hotel.setDescription(dto.getDescription());
        hotel.setBrand(dto.getBrand());
        hotel.setAddress(dto.getAddress());
        hotel.setContacts(dto.getContacts());
        hotel.setArrivalTime(dto.getArrivalTime());
        return hotel;
    }

    private String formatAddress(Address addr) {
        if (addr == null) return "";
        return String.format("%s %s, %s, %s, %s",
                addr.getHouseNumber(),
                addr.getStreet(),
                addr.getCity(),
                addr.getPostCode(),
                addr.getCountry());
    }
}