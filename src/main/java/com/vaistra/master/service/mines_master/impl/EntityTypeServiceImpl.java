package com.vaistra.master.service.mines_master.impl;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.mines_master.EntityTypeDto;
import com.vaistra.master.entity.mines_master.EntityType;
import com.vaistra.master.exception.DuplicateEntryException;
import com.vaistra.master.exception.ResourceNotFoundException;
import com.vaistra.master.repository.mines_master.EntityTypeRepository;
import com.vaistra.master.service.mines_master.EntityTypeService;
import com.vaistra.master.utils.mines_master.AppUtils_mines;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntityTypeServiceImpl implements EntityTypeService {
    private final AppUtils_mines appUtilsMines;
    private final EntityTypeRepository entityTypeRepository;

    @Autowired
    public EntityTypeServiceImpl(AppUtils_mines appUtilsMines, EntityTypeRepository entityTypeRepository){
        this.appUtilsMines = appUtilsMines;
        this.entityTypeRepository = entityTypeRepository;
    }
    @Override
    public String addEntityType(EntityTypeDto entityTypeDto) {
        if(entityTypeRepository.existsByEntityTypeNameIgnoreCase(entityTypeDto.getEntityTypeName())){
            throw new DuplicateEntryException("Entity with name: " + entityTypeDto.getEntityTypeName() + " already exist in current record.");
        }

        EntityType entityType = new EntityType();

        entityType.setEntityTypeName(entityTypeDto.getEntityTypeName());
        entityType.setShortName(entityTypeDto.getShortName());

        entityTypeRepository.save(entityType);

        return "Record added successfully.";
    }

    @Override
    public String updateEntityType(Integer entityTypeId, EntityTypeDto entityTypeDto) {
        EntityType entityType = entityTypeRepository.findById(entityTypeId).orElseThrow(()->new ResourceNotFoundException("Entity not found with given id: " + entityTypeId));

        EntityType entityWithSameName = entityTypeRepository.findByEntityTypeNameIgnoreCase(entityTypeDto.getEntityTypeName());

        if(entityWithSameName != null && !entityWithSameName.getEntityTypeId().equals(entityType.getEntityTypeId())){
            throw new DuplicateEntryException("Entity : " + entityTypeDto.getEntityTypeName() + " is already exist in current record...!");
        }

        entityType.setEntityTypeName(entityTypeDto.getEntityTypeName());
        entityType.setShortName(entityTypeDto.getShortName());

        entityTypeRepository.save(entityType);

        return "Record updated successfully.";
    }

    @Override
    public String deleteEntityType(Integer entityTypeId) {
        EntityType entityType = entityTypeRepository.findById(entityTypeId).orElseThrow(()->new ResourceNotFoundException("Entity not found with given id: " + entityTypeId));
        entityTypeRepository.deleteById(entityTypeId);
        return "Record deleted successfully.";
    }

    @Override
    public HttpResponse getEntityType(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<EntityType> entityTypePage = entityTypeRepository.findAll(pageable);

        List<EntityTypeDto> entities = appUtilsMines.entityTypesToDtos(entityTypePage.getContent());

        return HttpResponse.builder()
                .pageNumber(entityTypePage.getNumber())
                .pageSize(entityTypePage.getSize())
                .totalElements(entityTypePage.getTotalElements())
                .totalPages(entityTypePage.getTotalPages())
                .isLastPage(entityTypePage.isLast())
                .data(entities)
                .build();
    }

    @Override
    public HttpResponse getEntityTypeByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, Integer.MAX_VALUE, sort);

        Integer keyword3 = null;

        try {
            keyword3 = Integer.parseInt(keyword);
        } catch (NumberFormatException e) {
            keyword3 = null;
        }

        Page<EntityType> entityTypePage = entityTypeRepository.findByEntityTypeNameContainingIgnoreCaseOrShortNameContainingIgnoreCaseOrEntityTypeId(pageable,keyword,keyword,keyword3);

        List<EntityTypeDto> entities = appUtilsMines.entityTypesToDtos(entityTypePage.getContent());

        return HttpResponse.builder()
                .pageNumber(entityTypePage.getNumber())
                .pageSize(entityTypePage.getSize())
                .totalElements(entityTypePage.getTotalElements())
                .totalPages(entityTypePage.getTotalPages())
                .isLastPage(entityTypePage.isLast())
                .data(entities)
                .build();
    }
}
