package com.vaistra.master.repository.mines_master;

import com.vaistra.master.entity.cscv_master.Village;
import com.vaistra.master.entity.mines_master.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    boolean existsByVehicleNameIgnoreCase(String vehicleName);

    Vehicle findByVehicleNameIgnoreCase(String vehicleName);

    Page<Vehicle> findByVehicleNameContainingIgnoreCaseOrVehicleId(Pageable pageable, String keyword, Integer keyword2);

}
