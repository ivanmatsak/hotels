package com.example.hotels.controller;

import com.example.hotels.dto.HotelFullDto;
import com.example.hotels.dto.HotelShortDto;
import com.example.hotels.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/property-view")
@RequiredArgsConstructor
@Tag(name = "Hotel Management", description = "Операции по управлению отелями и их удобствами")
public class HotelController {

    private final HotelService hotelService;

    @Operation(summary = "Получить краткий список всех отелей")
    @GetMapping("/hotels")
    public List<HotelShortDto> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @Operation(summary = "Найти отель по ID")
    @GetMapping("/hotels/{id}")
    public HotelFullDto getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @Operation(summary = "Поиск отелей по фильтрам", description = "Можно фильтровать по имени, бренду, городу, стране и списку удобств")
    @GetMapping("/search")
    public List<HotelShortDto> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) List<String> amenities) {
        return hotelService.searchHotels(name, brand, city, country, amenities);
    }

    @Operation(summary = "Создать новый отель")
    @PostMapping("/hotels")
    @ResponseStatus(HttpStatus.CREATED)
    public HotelShortDto createHotel(@Valid @RequestBody HotelFullDto dto) {
        return hotelService.createHotel(dto);
    }

    @Operation(summary = "Добавить удобства к существующему отелю")
    @PostMapping("/hotels/{id}/amenities")
    @ResponseStatus(HttpStatus.OK)
    public String addAmenities(@PathVariable Long id,
                               @RequestBody @NotEmpty
                               List<@NotBlank(message = "Amenity name cannot be empty") String> amenities) {
        hotelService.addAmenitiesToHotel(id, amenities);
        return "Amenities added";
    }

    @Operation(summary = "Получить гистограмму по параметру", description = "Доступные параметры: brand, city, country, amenities")
    @GetMapping("/histogram/{param}")
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return hotelService.getHistogram(param);
    }
}
