package com.example.demo.service;

import com.example.demo.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {
    List<Manufacturer> findAll();
    Manufacturer findById(Long id);
    Manufacturer save(Manufacturer manufacturer);
    Manufacturer update(Long id, Manufacturer manufacturer);
    Manufacturer updateName(Long id, String name);
    List<Manufacturer> findAllByName(String name);
    void deleteById(Long id);
}
