package com.vaistra.master.controller.cscv_master;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.cscv_master.StateDto;
import com.vaistra.master.dto.cscv_master.StateDto_Update;
import com.vaistra.master.service.cscv_master.StateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/state")
public class StateController {
    private final StateService stateService;

    public StateController(StateService stateService){
        this.stateService = stateService;
    }

    @PostMapping
    public ResponseEntity<String> addState(@RequestBody @Valid StateDto stateDto){
        return new ResponseEntity<>(stateService.addState(stateDto), HttpStatus.OK);
    }

    @PatchMapping("/{stateId}")
    public ResponseEntity<String> updateState(@PathVariable Integer stateId, @RequestBody @Valid StateDto_Update stateDto){
        return new ResponseEntity<>(stateService.updateState(stateId,stateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{stateId}")
    public ResponseEntity<String> deleteState(@PathVariable Integer stateId){
        return new ResponseEntity<>(stateService.deleteState(stateId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<HttpResponse> getState(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                 @RequestParam(value = "sortBy", defaultValue = "stateId", required = false) String sortBy,
                                                 @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection){
        return new ResponseEntity<>(stateService.getState(pageNo,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<List<StateDto>> getStateByCountry(@PathVariable Integer countryId){
        return new ResponseEntity<>(stateService.getStateByCountry(countryId),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<HttpResponse> getStateByKeyword(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                          @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                          @RequestParam(value = "sortBy", defaultValue = "stateId", required = false) String sortBy,
                                                          @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection,
                                                          @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword){
        return new ResponseEntity<>(stateService.getStateByKeyword(pageNo,pageSize,sortBy,sortDirection,keyword),HttpStatus.OK);
    }

    @PostMapping("/csv")
    public ResponseEntity<String> uploadStateCSV(@RequestParam MultipartFile file) throws IOException {
        return new ResponseEntity<>(stateService.uploadStateCSV(file),HttpStatus.OK);
    }

}
