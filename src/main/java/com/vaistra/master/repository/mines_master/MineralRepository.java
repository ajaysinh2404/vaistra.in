package com.vaistra.master.repository.mines_master;

import com.vaistra.master.entity.mines_master.Mineral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MineralRepository extends JpaRepository<Mineral,Integer> {
}
