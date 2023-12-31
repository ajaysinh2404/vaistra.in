package com.vaistra.master.controller.cscv_master;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.cscv_master.CountryDto;
import com.vaistra.master.dto.cscv_master.CountryDto_Update;
import com.vaistra.master.exception.ResourceNotFoundException;
import com.vaistra.master.service.cscv_master.CountryService;
import jakarta.validation.Valid;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/country")
public class CountryController {
    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService){

        this.countryService = countryService;
    }

    @PostMapping
    public ResponseEntity<String> addCountry(@RequestBody @Valid CountryDto countryDto){
        return new ResponseEntity<>(countryService.addCountry(countryDto), HttpStatus.OK);
    }

    @PatchMapping("/{countryId}")
    public ResponseEntity<String> updateCountry(@PathVariable Integer countryId, @RequestBody @Valid CountryDto_Update countryDto){
        return new ResponseEntity<>(countryService.updateCountry(countryId,countryDto),HttpStatus.OK);
    }

    @DeleteMapping("/{countryId}")
    public ResponseEntity<String> deleteCountry(@PathVariable Integer countryId){
        return new ResponseEntity<>(countryService.deleteCountry(countryId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<HttpResponse> getCountry(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                   @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                   @RequestParam(value = "sortBy", defaultValue = "countryId", required = false) String sortBy,
                                                   @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection){
        return new ResponseEntity<>(countryService.getCountry(pageNo,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }

    /*@GetMapping("/active")
    public ResponseEntity<HttpResponse> getActiveCountry(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                         @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                         @RequestParam(value = "sortBy", defaultValue = "countryId", required = false) String sortBy,
                                                         @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection){
       return new ResponseEntity<>(countryService.getIsActiveCountry(pageNo,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }*/

    @GetMapping("/allactive")
    public ResponseEntity<List<CountryDto>> getAllActiveCountry(CountryDto countryDto){
        return new ResponseEntity<>(countryService.getAllActiveCountry(countryDto),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<HttpResponse> getCountryByKeyword(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                            @RequestParam(value = "pageSize", defaultValue = "", required = false) Integer pageSize,
                                                            @RequestParam(value = "sortBy", defaultValue = "countryId", required = false) String sortBy,
                                                            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection,
                                                            @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword){
        return new ResponseEntity<>(countryService.getCountryByKeyword(pageNo,pageSize,sortBy,sortDirection,keyword),HttpStatus.OK);
    }

    @PostMapping("/csv")
    public ResponseEntity<String> uploadCountryCSV(@RequestParam MultipartFile file) throws IOException {
        return new ResponseEntity<>(countryService.uploadCountryCSV(file),HttpStatus.OK);
    }


    @PostMapping("/batchcsv")
    public ResponseEntity<String> uploadCountryCSVBatch(@RequestParam MultipartFile file) throws IOException {
        return new ResponseEntity<>(countryService.uploadCountryCSVBatch(file),HttpStatus.OK);
    }
}
