package com.example.demo.service;

import com.example.demo.model.Manufacturer;
import com.example.demo.model.Watch;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface WatchService {
    List<Watch> findAll();
    List<Watch> findByManufacturerId(Long manufacturerId);
    List<Watch> findAllSortedByPrice(boolean asc);
    Watch findById(Long id);
    Watch update(Long id, Watch watch, MultipartFile image) throws IOException;
    Watch save(String name, Float price, Integer quantity, Long manufacturerId);
    Watch save(Watch watch, MultipartFile image) throws IOException;
    void deleteById(Long id);
}
