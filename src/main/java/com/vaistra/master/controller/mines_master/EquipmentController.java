package com.vaistra.master.controller.mines_master;


import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.mines_master.EquipmentDto;
import com.vaistra.master.dto.mines_master.EquipmentDto_Update;
import com.vaistra.master.dto.mines_master.VehicleDto;
import com.vaistra.master.service.mines_master.EquipmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {
    private final EquipmentService equipmentService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService){
        this.equipmentService = equipmentService;
    }

    @PostMapping
    public ResponseEntity<String> addEquipment(@RequestBody @Valid EquipmentDto equipmentDto){
        return new ResponseEntity<>(equipmentService.addEquipment(equipmentDto), HttpStatus.OK);
    }

    @PatchMapping("/{equipmentId}")
    public ResponseEntity<String> updateEquipment(@PathVariable Integer equipmentId, @RequestBody @Valid EquipmentDto_Update equipmentDto){
        return new ResponseEntity<>(equipmentService.updateEquipment(equipmentId,equipmentDto), HttpStatus.OK);
    }

    @DeleteMapping("/{equipmentId}")
    public ResponseEntity<String> deleteEquipment(@PathVariable Integer equipmentId){
        return new ResponseEntity<>(equipmentService.deleteEquipment(equipmentId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<HttpResponse> getEquipment(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                   @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                   @RequestParam(value = "sortBy", defaultValue = "equipmentId", required = false) String sortBy,
                                                   @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection){
        return new ResponseEntity<>(equipmentService.getEquipment(pageNo,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<HttpResponse> getEquipmentByKeyword(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                            @RequestParam(value = "sortBy", defaultValue = "equipmentId", required = false) String sortBy,
                                                            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection,
                                                            @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword){
        return new ResponseEntity<>(equipmentService.getEquipmentByKeyword(pageNo,pageSize,sortBy,sortDirection,keyword),HttpStatus.OK);
    }
}
