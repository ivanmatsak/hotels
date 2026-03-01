package com.example.hotels.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Краткая модель отеля для списков и результатов поиска")
public class HotelShortDto {

    @Schema(description = "ID отеля", example = "1")
    private Long id;

    @Schema(description = "Название отеля", example = "DoubleTree by Hilton Minsk")
    private String name;

    @Schema(description = "Описание отеля", example = "Luxurious rooms in the Belorussian capital...")
    private String description;

    @Schema(description = "Полный адрес (отформатированный)", example = "9 Pobediteley Avenue, Minsk, 220004, Belarus")
    private String address;

    @Schema(description = "Контактный телефон", example = "+375 17 309-80-00")
    private String phone;
}