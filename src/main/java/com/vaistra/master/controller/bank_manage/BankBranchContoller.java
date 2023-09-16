package com.vaistra.master.controller.bank_manage;


import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.bank_manage.BankBranchDto;
import com.vaistra.master.service.bank_manage.BankBranchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank-branch")
public class BankBranchContoller {
    private final BankBranchService bankBranchService;
    @Autowired
    public BankBranchContoller(BankBranchService bankBranchService){
        this.bankBranchService = bankBranchService;
    }

    @PostMapping
    public ResponseEntity<String> addBankBranch(@RequestBody @Valid BankBranchDto bankBranchDto){
        return new ResponseEntity<>(bankBranchService.addBankBranch(bankBranchDto), HttpStatus.OK);
    }

    @PutMapping("/{branchId}")
    public ResponseEntity<String> updateBankBranch(@PathVariable Integer branchId, @RequestBody @Valid BankBranchDto bankBranchDto){
        return new ResponseEntity<>(bankBranchService.updateBankBranch(branchId,bankBranchDto), HttpStatus.OK);

    }

    @DeleteMapping("/{branchId}")
    public ResponseEntity<String> deleteBankBranch(@PathVariable Integer branchId){
        return new ResponseEntity<>(bankBranchService.deleteBankBranch(branchId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<HttpResponse> getBankBranch(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                @RequestParam(value = "sortBy", defaultValue = "branchId", required = false) String sortBy,
                                                @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection){
        return new ResponseEntity<>(bankBranchService.getBankBranch(pageNo,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<HttpResponse> getBankBranchByKeyword(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                         @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                         @RequestParam(value = "sortBy", defaultValue = "branchId", required = false) String sortBy,
                                                         @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection,
                                                         @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword){
        return new ResponseEntity<>(bankBranchService.getBankBranchByKeyword(pageNo,pageSize,sortBy,sortDirection,keyword),HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<BankBranchDto>> getAllActiveBankBranch(){
        return new ResponseEntity<>(bankBranchService.getAllActiveBankBranch(),HttpStatus.OK);
    }
}
