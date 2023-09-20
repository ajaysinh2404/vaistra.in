package com.vaistra.master.controller.cscv_master;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.cscv_master.VillageDto;
import com.vaistra.master.dto.cscv_master.VillageDto_Update;
import com.vaistra.master.service.cscv_master.VillageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/village")
public class VillageController {
    public final VillageService villageService;

    @Autowired
    public VillageController(VillageService villageService){
        this.villageService = villageService;
    }

    @PostMapping
    public ResponseEntity<String> addVillage(@RequestBody @Valid VillageDto villageDto){
        return new ResponseEntity<>(villageService.addVillage(villageDto), HttpStatus.OK);
    }

    @PatchMapping("/{villageId}")
    public ResponseEntity<String> updateVillage(@PathVariable Integer villageId, @RequestBody @Valid VillageDto_Update villageDto){
        return new ResponseEntity<>(villageService.updateVillage(villageId,villageDto), HttpStatus.OK);
    }

    @DeleteMapping("/{villageId}")
    public ResponseEntity<String> deleteVillage(@PathVariable Integer villageId){
        return new ResponseEntity<>(villageService.deleteVillage(villageId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<HttpResponse> getVillage(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                   @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                   @RequestParam(value = "sortBy", defaultValue = "villageId", required = false) String sortBy,
                                                   @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection){
        return new ResponseEntity<>(villageService.getVillage(pageNo,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }

    @GetMapping("/{subDistrictId}")
    public ResponseEntity<List<VillageDto>> getVillageBySubDistrict(@PathVariable Integer subDistrictId){
        return new ResponseEntity<>(villageService.getVillageBySubDistrict(subDistrictId),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<HttpResponse> getVillageByKeyword(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                            @RequestParam(value = "sortBy", defaultValue = "villageId", required = false) String sortBy,
                                                            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection,
                                                            @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword){
        return new ResponseEntity<>(villageService.getVillageByKeyword(pageNo,pageSize,sortBy,sortDirection,keyword),HttpStatus.OK);
    }
}
