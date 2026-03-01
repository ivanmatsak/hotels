package com.example.hotels.service;

import com.example.hotels.dto.HotelFullDto;
import com.example.hotels.dto.HotelShortDto;
import com.example.hotels.mapper.HotelMapper;
import com.example.hotels.model.Amenity;
import com.example.hotels.model.Hotel;
import com.example.hotels.repository.AmenityRepository;
import com.example.hotels.repository.HotelRepository;
import com.example.hotels.repository.HotelSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final AmenityRepository amenityRepository;

    public List<HotelShortDto> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(hotelMapper::toShortDto)
                .toList();
    }

    public HotelFullDto getHotelById(Long id) {
        return hotelRepository.findById(id)
                .map(hotelMapper::toFullDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found"));
    }

    public List<HotelShortDto> searchHotels(String name, String brand, String city, String country, List<String> amenities) {
        Specification<Hotel> spec = Specification.where(HotelSpecifications.hasName(name))
                .and(HotelSpecifications.hasBrand(brand))
                .and(HotelSpecifications.hasCity(city))
                .and(HotelSpecifications.hasCountry(country))
                .and(HotelSpecifications.hasAmenities(amenities));

        return hotelRepository.findAll(spec).stream()
                .map(hotelMapper::toShortDto)
                .toList();
    }

    @Transactional
    public HotelShortDto createHotel(HotelFullDto dto) {
        Hotel hotel = hotelMapper.toEntity(dto);
        Hotel savedHotel = hotelRepository.save(hotel);
        return hotelMapper.toShortDto(savedHotel);
    }

    @Transactional
    public void addAmenitiesToHotel(Long hotelId, List<String> amenityNames) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found"));

        Set<Amenity> amenities = amenityNames.stream()
                .map(name -> amenityRepository.findByName(name)
                        .orElseGet(() -> {
                            Amenity newAmenity = new Amenity();
                            newAmenity.setName(name);
                            return amenityRepository.save(newAmenity);
                        }))
                .collect(Collectors.toSet());

        hotel.getAmenities().addAll(amenities);
        hotelRepository.save(hotel);
    }

    public Map<String, Long> getHistogram(String param) {
        List<Object[]> results = switch (param.toLowerCase()) {
            case "brand" -> hotelRepository.countByBrand();
            case "city" -> hotelRepository.countByCity();
            case "country" -> hotelRepository.countByCountry();
            case "amenities" -> amenityRepository.countByAmenities();
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid parameter for histogram");
        };

        return results.stream()
                .collect(Collectors.toMap(
                        row -> row[0] != null ? row[0].toString() : "Unknown",
                        row -> (Long) row[1]
                ));
    }
}
