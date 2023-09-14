package com.vaistra.master.controller.cscv_master;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.cscv_master.SubDistrictDto;
import com.vaistra.master.service.cscv_master.SubDistrictService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sub-district")
public class SubDistrictController {
    private final SubDistrictService subDistrictService;

    @Autowired
    public SubDistrictController(SubDistrictService subDistrictService){

        this.subDistrictService = subDistrictService;
    }

    @PostMapping
    public ResponseEntity<String> addSubDistrict(@RequestBody @Valid SubDistrictDto subDistrictDto){
        return new ResponseEntity<>(subDistrictService.addSubDistrict(subDistrictDto), HttpStatus.OK);
    }

    @PutMapping("/{subDistrictId}")
    public ResponseEntity<String> updateSubDistrict(@PathVariable Integer subDistrictId, @RequestBody @Valid SubDistrictDto subDistrictDto){
        return new ResponseEntity<>(subDistrictService.updateSubDistrict(subDistrictId,subDistrictDto), HttpStatus.OK);
    }

    @DeleteMapping("/{subDistrictId}")
    public ResponseEntity<String> deleteSubDistrict(@PathVariable Integer subDistrictId){
        return new ResponseEntity<>(subDistrictService.deleteSubDistrict(subDistrictId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<HttpResponse> getSubDistrict(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                       @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                       @RequestParam(value = "sortBy", defaultValue = "subDistrictId", required = false) String sortBy,
                                                       @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection){
        return new ResponseEntity<>(subDistrictService.getSubDistrict(pageNo,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }

    @GetMapping("/{districtId}")
    public ResponseEntity<List<SubDistrictDto>> getSubDistrictByDistrict(@PathVariable Integer districtId){
        return new ResponseEntity<>(subDistrictService.getSubDistrictByDistrict(districtId),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<HttpResponse> getSubDistrictByKeyword(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                                @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                                @RequestParam(value = "sortBy", defaultValue = "subDistrictId", required = false) String sortBy,
                                                                @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection,
                                                                @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword){
        return new ResponseEntity<>(subDistrictService.getSubDistrictByKeyword(pageNo,pageSize,sortBy,sortDirection,keyword),HttpStatus.OK);
    }

}
