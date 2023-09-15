package com.vaistra.master.repository.mines_master;

import com.vaistra.master.entity.mines_master.Mineral;
import com.vaistra.master.entity.mines_master.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MineralRepository extends JpaRepository<Mineral,Integer> {
    boolean existsByMineralNameIgnoreCase(String mineralName);

    boolean existsByAtrNameIgnoreCase(String atrName);

    boolean existsByHsnCodeIgnoreCase(String hsnCode);


    Mineral findByMineralNameIgnoreCase(String mineralName);

    Mineral findByAtrNameIgnoreCase(String atrName);

    Mineral findByHsnCodeIgnoreCase(String hsnCode);

    Page<Mineral> findByMineralNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrAtrNameContainingIgnoreCaseOrHsnCodeContainingIgnoreCaseOrMineralId(Pageable pageable, String keyword, String keyword2, String keyword3, String keyword4, Integer keyword5);
}
