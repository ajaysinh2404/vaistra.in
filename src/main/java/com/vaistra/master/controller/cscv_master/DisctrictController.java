package com.vaistra.master.controller.cscv_master;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.cscv_master.DistrictDto;
import com.vaistra.master.dto.cscv_master.DistrictDto_Update;
import com.vaistra.master.service.cscv_master.DistrictService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/district")
public class DisctrictController {
    private final DistrictService districtService;

    @Autowired
    public DisctrictController(DistrictService districtService){

        this.districtService = districtService;
    }

    @PostMapping
    public ResponseEntity<String> addDistrict(@RequestBody @Valid DistrictDto districtDto){
        return new ResponseEntity<>(districtService.addDistrict(districtDto), HttpStatus.OK);
    }

    @PatchMapping("/{districtId}")
    public ResponseEntity<String> updateDistrict(@PathVariable Integer districtId, @RequestBody @Valid DistrictDto_Update districtDto){
        return new ResponseEntity<>(districtService.updateDistrict(districtId,districtDto),HttpStatus.OK);
    }

    @DeleteMapping("/{districtId}")
    public ResponseEntity<String> deleteDistrict(@PathVariable Integer districtId){
        return new ResponseEntity<>(districtService.deleteDistrict(districtId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<HttpResponse> getDistrict(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = "districtId", required = false) String sortBy,
                                                    @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection){
        return new ResponseEntity<>(districtService.getDistrict(pageNo,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }

    @GetMapping("/{stateId}")
    public ResponseEntity<List<DistrictDto>> getDistrictByState(@PathVariable Integer stateId){
        return new ResponseEntity<>(districtService.getDistrictByState(stateId),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<HttpResponse> getDistrictByKeyword(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                             @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                             @RequestParam(value = "sortBy", defaultValue = "districtId", required = false) String sortBy,
                                                             @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection,
                                                             @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword){
        return new ResponseEntity<>(districtService.getDistrictByKeyword(pageNo,pageSize,sortBy,sortDirection,keyword),HttpStatus.OK);
    }

    @PostMapping("/csv")
    public ResponseEntity<String> uploadDistrictCSV(@RequestParam MultipartFile file) throws IOException {
        return new ResponseEntity<>(districtService.uploadDistrictCSV(file),HttpStatus.OK);
    }
}
