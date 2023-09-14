package com.vaistra.master.repository.cscv_master;

import com.vaistra.master.entity.cscv_master.Village;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface VillageRepository extends JpaRepository<Village,Integer> {
    boolean existsByVillageNameIgnoreCase(String villageName);

    Village findByVillageNameIgnoreCase(String villageName);

    List<Village> findAllByIsActiveAndSubDistrict_SubDistrictId(Boolean villageIsActive, Integer subDistrictId);

    Page<Village> findByVillageNameContainingIgnoreCaseOrSubDistrict_SubDistrictNameContainingIgnoreCaseOrDistrict_DistrictNameContainingIgnoreCaseOrState_StateNameContainingIgnoreCaseOrCountry_CountryNameContainingIgnoreCaseOrVillageIdOrIsActive(Pageable pageable, String keyword, String keyword2, String keyword3, String keyword4,String keyword5, Integer keyword6, Boolean keyword7);

    Page<Village> findAllBySubDistrict_isActive(boolean isActive, Pageable pageable);
}
