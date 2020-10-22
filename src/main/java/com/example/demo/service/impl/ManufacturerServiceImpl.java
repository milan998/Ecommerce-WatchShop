package com.example.demo.service.impl;

import com.example.demo.model.Manufacturer;
import com.example.demo.model.exceptions.ManufacturerNotFoundException;
import com.example.demo.repository.ManufacturerRepository;
import com.example.demo.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }


    @Override
    public List<Manufacturer> findAll() {
        return this.manufacturerRepository.findAll();
    }

    @Override
    public Manufacturer findById(Long id) {
        return this.manufacturerRepository.findById(id)
                .orElseThrow(()-> new ManufacturerNotFoundException(id));
    }

    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        return this.manufacturerRepository.save(manufacturer);
    }

    @Override
    public Manufacturer update(Long id, Manufacturer manufacturer) {
        Manufacturer m = this.findById(id);
        m.setName(m.getName());
        return this.manufacturerRepository.save(m);
    }

    @Override
    public Manufacturer updateName(Long id, String name) {
        Manufacturer manufacturer = this.findById(id);
        manufacturer.setName(name);
        return this.manufacturerRepository.save(manufacturer);
    }

    @Override
    public List<Manufacturer> findAllByName(String name) {
        return this.manufacturerRepository.findAllByName(name);
    }

    @Override
    public void deleteById(Long id) {
        this.manufacturerRepository.deleteById(id);
    }
}
