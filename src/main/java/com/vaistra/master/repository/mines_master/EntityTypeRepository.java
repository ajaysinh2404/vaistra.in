package com.vaistra.master.repository.mines_master;

import com.vaistra.master.entity.mines_master.EntityType;
import com.vaistra.master.entity.mines_master.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityTypeRepository extends JpaRepository<EntityType, Integer> {
    boolean existsByEntityTypeNameIgnoreCase(String entityTypeName);

    EntityType findByEntityTypeNameIgnoreCase(String entityTypeName);

    Page<EntityType> findByEntityTypeNameContainingIgnoreCaseOrShortNameContainingIgnoreCaseOrEntityTypeId(Pageable pageable, String keyword, String keyword2, Integer keyword3);
}
