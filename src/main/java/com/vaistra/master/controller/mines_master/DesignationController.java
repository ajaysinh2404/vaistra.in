package com.vaistra.master.controller.mines_master;


import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.mines_master.DesignationDto;
import com.vaistra.master.dto.mines_master.EquipmentDto;
import com.vaistra.master.service.mines_master.DesignationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/designation")

public class DesignationController {
    private final DesignationService designationService;

    @Autowired
    public DesignationController(DesignationService designationService){
        this.designationService = designationService;
    }

    @PostMapping
    public ResponseEntity<String> addDesignation(@RequestBody @Valid DesignationDto designationDto){
        return new ResponseEntity<>(designationService.addDesignation(designationDto), HttpStatus.OK);
    }

    @PutMapping("/{designationId}")
    public ResponseEntity<String> updateDesignation(@PathVariable Integer designationId, @RequestBody @Valid DesignationDto designationDto){
        return new ResponseEntity<>(designationService.updateDesignation(designationId,designationDto), HttpStatus.OK);
    }

    @DeleteMapping("/{designationId}")
    public ResponseEntity<String> deleteDesignation(@PathVariable Integer designationId){
        return new ResponseEntity<>(designationService.deleteDesignation(designationId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<HttpResponse> getDesignation(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                     @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                     @RequestParam(value = "sortBy", defaultValue = "designationId", required = false) String sortBy,
                                                     @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection){
        return new ResponseEntity<>(designationService.getDesignation(pageNo,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<HttpResponse> getDesignationByKeyword(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                              @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                              @RequestParam(value = "sortBy", defaultValue = "designationId", required = false) String sortBy,
                                                              @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection,
                                                              @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword){
        return new ResponseEntity<>(designationService.getDesignationByKeyword(pageNo,pageSize,sortBy,sortDirection,keyword),HttpStatus.OK);
    }
}
