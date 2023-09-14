package com.vaistra.master.repository.mines_master;

import com.vaistra.master.entity.mines_master.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignationRepository extends JpaRepository<Designation,Integer> {
}
