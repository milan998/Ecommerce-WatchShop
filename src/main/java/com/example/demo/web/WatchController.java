package com.example.demo.web;

import com.example.demo.model.Manufacturer;
import com.example.demo.model.Watch;
import com.example.demo.service.ManufacturerService;
import com.example.demo.service.WatchService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/watches")
public class WatchController {

    private final WatchService watchService;
    private final ManufacturerService manufacturerService;

    public WatchController(WatchService watchService, ManufacturerService manufacturerService) {
        this.watchService = watchService;
        this.manufacturerService = manufacturerService;
    }


    @GetMapping
    public String getWatchPage(Model model){
        List<Watch> watches = this.watchService.findAll();
        model.addAttribute("watches", watches);
        return "watches";
    }

    @GetMapping("/add-new")
    public String addNewWatch(Model model){
        List<Manufacturer> manufacturers = this.manufacturerService.findAll();
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("watch", new Watch());
        return "add-watch";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable Long id, Model model){
        try{
            Watch watch = this.watchService.findById(id);
            List<Manufacturer> manufacturers = this.manufacturerService.findAll();
            model.addAttribute("watch", watch);
            model.addAttribute("manufacturers", manufacturers);
            return "add-watch";
        }catch (RuntimeException ex){
            return "redirect:/watches?error=" + ex.getMessage();
        }
    }


    @PostMapping
    public String save(@Valid Watch watch, BindingResult bindingResult, Model model, @RequestParam MultipartFile image) throws IOException {
        if(bindingResult.hasErrors()){
            List<Manufacturer> manufacturers = this.manufacturerService.findAll();
            model.addAttribute("manufacturers", manufacturers);
            return "add-watch";
        }try{
            this.watchService.save(watch, image);
        }catch (IOException e){
            e.printStackTrace();
        }
        return "redirect:/watches";
    }


    @PostMapping("/{id}/delete")
    public String deleteWatch(@PathVariable Long id){
        this.watchService.deleteById(id);
        return "redirect:/watches";
    }



}
