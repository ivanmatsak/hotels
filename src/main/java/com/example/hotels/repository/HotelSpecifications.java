package com.example.hotels.repository;

import com.example.hotels.model.Amenity;
import com.example.hotels.model.Hotel;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class HotelSpecifications {
    private HotelSpecifications() {
    }

    public static Specification<Hotel> hasName(String name) {
        return (root, query, cb) -> (name == null || name.isBlank()) ? null :
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Hotel> hasBrand(String brand) {
        return (root, query, cb) -> (brand == null || brand.isBlank()) ? null :
                cb.equal(cb.lower(root.get("brand")), brand.toLowerCase());
    }

    public static Specification<Hotel> hasCity(String city) {
        return (root, query, cb) -> (city == null || city.isBlank()) ? null :
                cb.equal(cb.lower(root.get("address").get("city")), city.toLowerCase());
    }

    public static Specification<Hotel> hasCountry(String country) {
        return (root, query, cb) -> (country == null || country.isBlank()) ? null :
                cb.equal(cb.lower(root.get("address").get("country")), country.toLowerCase());
    }

    public static Specification<Hotel> hasAmenities(java.util.List<String> amenities) {
        return (root, query, cb) -> {
            if (amenities == null || amenities.isEmpty()) return null;
            query.distinct(true);
            Join<Hotel, Amenity> join = root.join("amenities");
            return join.get("name").in(amenities);
        };
    }
}
