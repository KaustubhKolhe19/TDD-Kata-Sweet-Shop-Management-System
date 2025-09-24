package com.example.sweetshop.repository;

import com.example.sweetshop.model.Sweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SweetRepository extends JpaRepository<Sweet, Long> {

  List<Sweet> findByNameContainingIgnoreCase(String name);

  List<Sweet> findByCategoryIgnoreCase(String category);

  @Query("SELECT s FROM Sweet s WHERE s.price BETWEEN :minPrice AND :maxPrice")
  List<Sweet> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

}
