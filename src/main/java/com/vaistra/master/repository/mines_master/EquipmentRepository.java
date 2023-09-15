package com.vaistra.master.repository.mines_master;

import com.vaistra.master.entity.mines_master.Equipment;
import com.vaistra.master.entity.mines_master.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
    boolean existsByEquipmentNameIgnoreCase(String equipmentName);

    Equipment findByEquipmentNameIgnoreCase(String equipmentName);

    Page<Equipment> findByEquipmentNameContainingIgnoreCaseOrEquipmentId(Pageable pageable, String keyword, Integer keyword2);
}
