package com.vaistra.master.repository.cscv_master;

import com.vaistra.master.entity.cscv_master.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    boolean existsByCountryNameIgnoreCase(String countryName);

    Country findByCountryNameIgnoreCase(String countryName);

    //Page<Country> findByIsActive(Pageable p, Boolean isActive);

    List<Country> findByIsActive(Boolean isActive);

    Page<Country> findByCountryNameContainingIgnoreCaseOrCountryIdOrIsActive(Pageable p, String keyword, Integer keyword2, Boolean keyword3);

}
