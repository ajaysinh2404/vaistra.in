package com.vaistra.master.repository.cscv_master;

import com.vaistra.master.entity.cscv_master.SubDistrict;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface SubDistrictRepository extends JpaRepository<SubDistrict, Integer> {
    boolean existsBySubDistrictNameIgnoreCase(String subDistrictName);

    SubDistrict findBySubDistrictNameIgnoreCase(String subDistrictName);

    List<SubDistrict> findAllByIsActiveAndDistrict_DistrictId(Boolean subDistrictIsActive, Integer districtId);

    Page<SubDistrict> findBySubDistrictNameContainingIgnoreCaseOrDistrict_DistrictNameContainingIgnoreCaseOrState_StateNameContainingIgnoreCaseOrCountry_CountryNameContainingIgnoreCaseOrSubDistrictIdOrIsActive(Pageable pageable, String keyword, String keyword2, String keyword3, String keyword4, Integer keyword5, Boolean keyword6);

    Page<SubDistrict> findAllByDistrict_isActive(boolean isActive, Pageable pageable);

}
