package com.example.demo.repository;

import com.example.demo.model.Watch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchRepository extends JpaRepository<Watch, Long> {

    List<Watch> findAllByManufacturerId(@Param("id") Long id);
    List<Watch> findAllByOrderByPriceAsc();
    List<Watch> findAllByOrderByPriceDesc();

}
