package com.vaistra.master.repository.bank_manage;


import com.vaistra.master.entity.bank_manage.BankBranch;
import com.vaistra.master.entity.cscv_master.SubDistrict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankBranchRepository extends JpaRepository<BankBranch, Integer> {
    boolean existsByBranchNameIgnoreCase(String branchName);

    boolean existsByBranchCodeIgnoreCase(String branchCode);

    boolean existsByBranchIfscIgnoreCase(String branchIfsc);

    boolean existsByBranchMicrIgnoreCase(String branchMicr);

    BankBranch findByBranchNameIgnoreCase(String branchName);

    BankBranch findByBranchCodeIgnoreCase(String branchCode);

    BankBranch findByBranchIfscIgnoreCase(String branchIfsc);

    BankBranch findByBranchMicrIgnoreCase(String branchMicr);

    Page<BankBranch> findByBank_BankLongNameContainingIgnoreCaseOrState_StateNameContainingIgnoreCaseOrDistrict_DistrictNameContainingIgnoreCaseOrBranchNameContainingIgnoreCaseOrBranchCodeContainingIgnoreCaseOrBranchAddressContainingIgnoreCaseOrBranchIfscContainingIgnoreCaseOrBranchPhoneNumberContainingIgnoreCaseOrBranchIdOrIsActive(Pageable pageable, String keyword, String keyword2,String keyword3, String keyword4, String keyword5, String keyword6, String keyword7, String keyword8, Integer keyword9, Boolean keyword10);

    List<BankBranch> findAllByIsActive(boolean isActive);

}
