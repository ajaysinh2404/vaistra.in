package com.vaistra.master.repository.mines_master;

import com.vaistra.master.entity.mines_master.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
}
