package com.vaistra.master.controller.bank_manage;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.bank_manage.BankDto;
import com.vaistra.master.dto.mines_master.DesignationDto;
import com.vaistra.master.service.bank_manage.BankService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/bank")

public class BankController {
    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService){
        this.bankService = bankService;
    }

    @PostMapping
    public ResponseEntity<String> addBank(@RequestBody @Valid BankDto bankDto, @RequestPart MultipartFile file) throws IOException {
        return new ResponseEntity<>(bankService.addBank(bankDto,file), HttpStatus.OK);
    }

    @PutMapping("/{bankId}")
    public ResponseEntity<String> updateBank(@PathVariable Integer bankId, @RequestBody @Valid BankDto bankDto, @RequestPart MultipartFile file) throws IOException {
        return new ResponseEntity<>(bankService.updateBank(bankId,bankDto,file), HttpStatus.OK);
    }

    @DeleteMapping("/{bankId}")
    public ResponseEntity<String> deleteBank(@PathVariable Integer bankId){
        return new ResponseEntity<>(bankService.deleteBank(bankId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<HttpResponse> getBank(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                       @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                       @RequestParam(value = "sortBy", defaultValue = "bankId", required = false) String sortBy,
                                                       @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection){
        return new ResponseEntity<>(bankService.getBank(pageNo,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<HttpResponse> getBankByKeyword(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                                @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                                @RequestParam(value = "sortBy", defaultValue = "bankId", required = false) String sortBy,
                                                                @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection,
                                                                @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword){
        return new ResponseEntity<>(bankService.getBankByKeyword(pageNo,pageSize,sortBy,sortDirection,keyword),HttpStatus.OK);
    }

    @GetMapping("/{bankId}/logo")
    public ResponseEntity<byte[]> getBankLogo(@PathVariable Integer bankId) {
        byte[] imageBytes = bankService.getBankLogo(bankId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Adjust content type based on your image type

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}
