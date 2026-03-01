package com.example.hotels.service;

import com.example.hotels.dto.HotelFullDto;
import com.example.hotels.dto.HotelShortDto;
import com.example.hotels.mapper.HotelMapper;
import com.example.hotels.model.Hotel;
import com.example.hotels.repository.AmenityRepository;
import com.example.hotels.repository.HotelRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private HotelMapper hotelMapper;
    @Mock
    private AmenityRepository amenityRepository;
    @InjectMocks
    private HotelService hotelService;

    @Test
    @DisplayName("Поиск всех отелей должен возвращать список DTO")
    void getAllHotels_Success() {
        Hotel hotel = new Hotel();
        HotelShortDto dto = new HotelShortDto(1L, "Test", "Desc", "Addr", "123");
        when(hotelRepository.findAll()).thenReturn(List.of(hotel));
        when(hotelMapper.toShortDto(any())).thenReturn(dto);

        List<HotelShortDto> result = hotelService.getAllHotels();

        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getName());
    }

    @Test
    @DisplayName("Поиск по ID должен возвращать полную информацию об отеле")
    void getHotelById_Success() {
        Long id = 1L;
        Hotel hotel = new Hotel();
        HotelFullDto fullDto = new HotelFullDto();
        fullDto.setId(id);
        fullDto.setName("DoubleTree");

        when(hotelRepository.findById(id)).thenReturn(Optional.of(hotel));
        when(hotelMapper.toFullDto(hotel)).thenReturn(fullDto);

        HotelFullDto result = hotelService.getHotelById(id);

        assertNotNull(result);
        assertEquals("DoubleTree", result.getName());
        verify(hotelRepository).findById(id);
    }

    @Test
    @DisplayName("Поиск с параметрами должен возвращать отфильтрованные DTO")
    void searchHotels_WithParams_Success() {
        String city = "Minsk";
        Hotel hotel = new Hotel();
        HotelShortDto shortDto = new HotelShortDto(1L, "Minsk Hotel", "Desc", "Addr", "123");

        when(hotelRepository.findAll(any(Specification.class))).thenReturn(List.of(hotel));
        when(hotelMapper.toShortDto(hotel)).thenReturn(shortDto);

        List<HotelShortDto> result = hotelService.searchHotels(null, null, city, null, null);

        assertFalse(result.isEmpty());
        assertEquals("Minsk Hotel", result.get(0).getName());
        verify(hotelRepository).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Создание отеля должно сохранять сущность и возвращать краткую инфо")
    void createHotel_Success() {
        HotelFullDto inputDto = new HotelFullDto();
        Hotel hotel = new Hotel();
        HotelShortDto outputDto = new HotelShortDto(1L, "New", "Desc", "Addr", "123");

        when(hotelMapper.toEntity(inputDto)).thenReturn(hotel);
        when(hotelRepository.save(hotel)).thenReturn(hotel);
        when(hotelMapper.toShortDto(hotel)).thenReturn(outputDto);

        HotelShortDto result = hotelService.createHotel(inputDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Гистограмма должна корректно группировать данные")
    void getHistogram_Success() {
        List<Object[]> mockData = List.of(new Object[]{"Minsk", 5L}, new Object[]{"Gomel", 2L});
        when(hotelRepository.countByCity()).thenReturn(mockData);

        Map<String, Long> result = hotelService.getHistogram("city");

        assertEquals(2, result.size());
        assertEquals(5L, result.get("Minsk"));
    }

    @Test
    @DisplayName("Добавление удобств: если удобство новое, оно должно создаваться в БД")
    void addAmenitiesToHotel_NewAmenity() {
        Hotel hotel = new Hotel();
        hotel.setAmenities(new HashSet<>());
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(amenityRepository.findByName("WiFi")).thenReturn(Optional.empty());
        when(amenityRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        hotelService.addAmenitiesToHotel(1L, List.of("WiFi"));

        verify(amenityRepository, times(1)).save(any());
        assertEquals(1, hotel.getAmenities().size());
    }
}