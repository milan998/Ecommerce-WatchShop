package com.example.demo.web.rest;

import com.example.demo.model.Watch;
import com.example.demo.service.WatchService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/watches")
public class WatchRestController {

    private final WatchService watchService;

    public WatchRestController(WatchService watchService) {
        this.watchService = watchService;
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public List<Watch> findAll(){
        return this.watchService.findAll();
    }

    @GetMapping("/{id}")
    public Watch findById(@PathVariable Long id){
        return this.watchService.findById(id);
    }

    @GetMapping("/by-manufacturer/{manufacturerId}")
    public List<Watch> findByManufacturerId(@PathVariable Long manufacturerId){
        return this.watchService.findByManufacturerId(manufacturerId);
    }

    @PostMapping
    public Watch save(@Valid Watch watch, @RequestParam(required = false) MultipartFile image) throws IOException {
        return this.watchService.save(watch, image);
    }

    @PutMapping("/{id}")
    public Watch update(@PathVariable Long id, @Valid Watch watch, @RequestParam MultipartFile image) throws IOException {
        return this.watchService.update(id, watch, image);
    }

    @GetMapping("/by-price")
    public List<Watch> findAllSortedByPrice(@RequestParam(required = false, defaultValue = "true") Boolean asc){
        return this.watchService.findAllSortedByPrice(asc);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        this.watchService.findById(id);
    }
}
