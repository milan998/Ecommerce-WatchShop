package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "manufacturers")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 3, message = "Must be min 3 letters")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "manufacturer")
    private List<Watch> watches;

    public Manufacturer() {}

    public Manufacturer(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Watch> getWatches() {
        return watches;
    }

    public void setWatches(List<Watch> watches) {
        this.watches = watches;
    }
}
