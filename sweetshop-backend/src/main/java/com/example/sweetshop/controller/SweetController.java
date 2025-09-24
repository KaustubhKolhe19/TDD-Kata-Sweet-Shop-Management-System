package com.example.sweetshop.controller;

import com.example.sweetshop.model.Sweet;
import com.example.sweetshop.repository.SweetRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sweets")
public class SweetController {

  private final SweetRepository sweetRepository;

  public SweetController(SweetRepository sweetRepository) {
    this.sweetRepository = sweetRepository;
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Sweet> addSweet(@Valid @RequestBody Sweet sweet) {
    Sweet savedSweet = sweetRepository.save(sweet);
    return ResponseEntity.ok(savedSweet);
  }

  @GetMapping
  public ResponseEntity<List<Sweet>> getAllSweets() {
    return ResponseEntity.ok(sweetRepository.findAll());
  }

  @GetMapping("/search")
  public ResponseEntity<List<Sweet>> searchSweets(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String category,
      @RequestParam(required = false) Double minPrice,
      @RequestParam(required = false) Double maxPrice) {

    if (name != null && !name.isEmpty()) {
      return ResponseEntity.ok(sweetRepository.findByNameContainingIgnoreCase(name));
    }

    if (category != null && !category.isEmpty()) {
      return ResponseEntity.ok(sweetRepository.findByCategoryIgnoreCase(category));
    }

    if (minPrice != null && maxPrice != null) {
      return ResponseEntity.ok(sweetRepository.findByPriceRange(minPrice, maxPrice));
    }

    return ResponseEntity.ok(sweetRepository.findAll());
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> updateSweet(@PathVariable Long id, @Valid @RequestBody Sweet sweetDetails) {
    Optional<Sweet> sweetData = sweetRepository.findById(id);

    if (sweetData.isPresent()) {
      Sweet sweet = sweetData.get();
      sweet.setName(sweetDetails.getName());
      sweet.setCategory(sweetDetails.getCategory());
      sweet.setPrice(sweetDetails.getPrice());
      sweet.setQuantity(sweetDetails.getQuantity());
      Sweet updatedSweet = sweetRepository.save(sweet);
      return ResponseEntity.ok(updatedSweet);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> deleteSweet(@PathVariable Long id) {
    if (sweetRepository.existsById(id)) {
      sweetRepository.deleteById(id);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/{id}/purchase")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ResponseEntity<?> purchaseSweet(@PathVariable Long id) {
    Optional<Sweet> sweetData = sweetRepository.findById(id);

    if (sweetData.isPresent()) {
      Sweet sweet = sweetData.get();
      if (sweet.getQuantity() <= 0) {
        return ResponseEntity.badRequest().body("Sweet is out of stock");
      }
      sweet.setQuantity(sweet.getQuantity() - 1);
      sweetRepository.save(sweet);
      return ResponseEntity.ok(sweet);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/{id}/restock")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> restockSweet(@PathVariable Long id, @RequestParam int quantity) {
    Optional<Sweet> sweetData = sweetRepository.findById(id);

    if (sweetData.isPresent()) {
      Sweet sweet = sweetData.get();
      sweet.setQuantity(sweet.getQuantity() + quantity);
      sweetRepository.save(sweet);
      return ResponseEntity.ok(sweet);
    }
    return ResponseEntity.notFound().build();
  }
}
