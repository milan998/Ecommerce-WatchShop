package com.example.demo.service.impl;

import com.example.demo.model.Manufacturer;
import com.example.demo.model.Watch;
import com.example.demo.model.exceptions.WatchNotFoundException;
import com.example.demo.repository.WatchRepository;
import com.example.demo.service.ManufacturerService;
import com.example.demo.service.WatchService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class WatchServiceImpl implements WatchService {

    private final WatchRepository watchRepository;
    private final ManufacturerService manufacturerService;

    public WatchServiceImpl(WatchRepository watchRepository, ManufacturerService manufacturerService) {
        this.watchRepository = watchRepository;
        this.manufacturerService = manufacturerService;
    }


    @Override
    public List<Watch> findAll() {
        return this.watchRepository.findAll();
    }

    @Override
    public List<Watch> findByManufacturerId(Long manufacturerId) {
        return this.watchRepository.findAllByManufacturerId(manufacturerId);
    }

    @Override
    public List<Watch> findAllSortedByPrice(boolean asc) {
        if(asc){
            return this.watchRepository.findAllByOrderByPriceAsc();
        }
        return this.watchRepository.findAllByOrderByPriceDesc();

    }

    @Override
    public Watch findById(Long id) {
        return this.watchRepository.findById(id)
                .orElseThrow(() -> new WatchNotFoundException(id));
    }

    @Override
    public Watch update(Long id, Watch watch, MultipartFile image) throws IOException {
        Watch w = this.findById(id);
        Manufacturer manufacturer = this.manufacturerService.findById(w.getManufacturer().getId());
        w.setManufacturer(manufacturer);
        w.setQuantity(w.getQuantity());
        if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
            w.setImageBase64(base64Image);
        }
        return this.watchRepository.save(w);
    }

    @Override
    public Watch save(String name, Float price, Integer quantity, Long manufacturerId) {
        Manufacturer manufacturer = this.manufacturerService.findById(manufacturerId);
        Watch watch = new Watch(null, name, price, quantity, manufacturer);
        return this.watchRepository.save(watch);
    }

    @Override
    public Watch save(Watch watch, MultipartFile image) throws IOException {
        Manufacturer manufacturer = this.manufacturerService.findById(watch.getManufacturer().getId());
        watch.setManufacturer(manufacturer);
        if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
            watch.setImageBase64(base64Image);
    }
        return this.watchRepository.save(watch);
    }

    @Override
    public void deleteById(Long id) {
        this.watchRepository.deleteById(id);
    }
}
