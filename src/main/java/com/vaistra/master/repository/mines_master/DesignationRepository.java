package com.vaistra.master.repository.mines_master;

import com.vaistra.master.entity.mines_master.Designation;
import com.vaistra.master.entity.mines_master.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignationRepository extends JpaRepository<Designation,Integer> {
    boolean existsByDesignationNameIgnoreCase(String designationName);

    Designation findByDesignationNameIgnoreCase(String designationName);

    Page<Designation> findByDesignationNameContainingIgnoreCaseOrDesignationId(Pageable pageable, String keyword, Integer keyword2);
}
