package com.vaistra.master.repository.cscv_master;

import com.vaistra.master.entity.cscv_master.State;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
    boolean existsByStateNameIgnoreCase(String stateName);

    State findByStateNameIgnoreCase(String stateName);

    List<State> findAllByIsActiveAndCountry_CountryId(Boolean stateIsActive, Integer countryId);

    Page<State> findByStateNameContainingIgnoreCaseOrCountry_CountryNameContainingIgnoreCaseOrStateIdOrIsActive(Pageable pageable, String keyword, String keyword2, Integer keyword3, Boolean keyword4);

    Page<State> findAllByCountry_isActive(boolean isActive, Pageable pageable);

}