package com.vaistra.master.repository.cscv_master;

import com.vaistra.master.entity.cscv_master.District;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
    boolean existsByDistrictNameIgnoreCase(String districtName);

    District findByDistrictNameIgnoreCase(String districtName);

    List<District> findAllByIsActiveAndState_StateId(Boolean districtIsActive, Integer stateId);

    Page<District> findByDistrictNameContainingIgnoreCaseOrState_StateNameContainingIgnoreCaseOrCountry_CountryNameContainingIgnoreCaseOrDistrictIdOrIsActive(Pageable pageable, String keyword, String keyword2, String keyword3, Integer keyword4, Boolean keyword5);

    Page<District> findAllByState_isActive(boolean isActive, Pageable pageable);

}
