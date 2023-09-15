package com.vaistra.master.controller.mines_master;


import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.mines_master.EntityTypeDto;
import com.vaistra.master.dto.mines_master.EquipmentDto;
import com.vaistra.master.service.mines_master.EntityTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("entity-type")
public class EntityTypeController {
    private final EntityTypeService entityTypeService;

    @Autowired
    public EntityTypeController(EntityTypeService entityTypeService){
        this.entityTypeService = entityTypeService;
    }

    @PostMapping
    public ResponseEntity<String> addEntityType(@RequestBody @Valid EntityTypeDto entityTypeDto){
        return new ResponseEntity<>(entityTypeService.addEntityType(entityTypeDto), HttpStatus.OK);
    }

    @PutMapping("/{entityTypeId}")
    public ResponseEntity<String> updateEntityType(@PathVariable Integer entityTypeId, @RequestBody @Valid EntityTypeDto entityTypeDto){
        return new ResponseEntity<>(entityTypeService.updateEntityType(entityTypeId,entityTypeDto), HttpStatus.OK);
    }

    @DeleteMapping("/{entityTypeId}")
    public ResponseEntity<String> deleteEntityType(@PathVariable Integer entityTypeId){
        return new ResponseEntity<>(entityTypeService.deleteEntityType(entityTypeId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<HttpResponse> getEntityType(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                     @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                     @RequestParam(value = "sortBy", defaultValue = "entityTypeId", required = false) String sortBy,
                                                     @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection){
        return new ResponseEntity<>(entityTypeService.getEntityType(pageNo,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<HttpResponse> getEntityTypeByKeyword(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                              @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                              @RequestParam(value = "sortBy", defaultValue = "entityTypeId", required = false) String sortBy,
                                                              @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection,
                                                              @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword){
        return new ResponseEntity<>(entityTypeService.getEntityTypeByKeyword(pageNo,pageSize,sortBy,sortDirection,keyword),HttpStatus.OK);
    }

}
