package com.example.hotels.dto;

import com.example.hotels.model.Address;
import com.example.hotels.model.ArrivalTime;
import com.example.hotels.model.Contacts;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Полная модель отеля для создания и детального просмотра")
public class HotelFullDto {

    @Schema(description = "ID отеля (генерируется автоматически)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Schema(description = "Название отеля", example = "DoubleTree by Hilton Minsk")
    private String name;

    @Schema(description = "Детальное описание отеля", example = "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms...")
    private String description;

    @NotBlank(message = "Brand is required")
    @Schema(description = "Бренд отеля", example = "Hilton")
    private String brand;

    @NotNull(message = "Address is required")
    @Valid
    @Schema(description = "Объект адреса")
    private Address address;

    @NotNull(message = "Contacts are required")
    @Valid
    @Schema(description = "Контактные данные")
    private Contacts contacts;

    @NotNull(message = "Arrival time object is required")
    @Valid
    @Schema(description = "Время заезда и выезда")
    private ArrivalTime arrivalTime;

    @Schema(description = "Список удобств", example = "Free parking, Free WiFi")
    private List<String> amenities;
}