package com.example.hotels.repository;

import com.example.hotels.model.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {
    Optional<Amenity> findByName(String name);

    @Query("SELECT a.name, COUNT(h) FROM Amenity a JOIN a.hotels h GROUP BY a.name")
    List<Object[]> countByAmenities();
}