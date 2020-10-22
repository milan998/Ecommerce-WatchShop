package com.example.demo.repository;

import com.example.demo.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    List<Manufacturer> findAll();
    Optional<Manufacturer> findById(Long id);
    List<Manufacturer> findAllByName(String name);
    Manufacturer save(Manufacturer manufacturer);
    void deleteById(Long id);

}
