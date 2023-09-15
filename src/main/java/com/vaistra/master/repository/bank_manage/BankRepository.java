package com.vaistra.master.repository.bank_manage;


import com.vaistra.master.entity.bank_manage.Bank;
import com.vaistra.master.entity.bank_manage.BankBranch;
import com.vaistra.master.entity.mines_master.Designation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank,Integer> {
    boolean existsByBankShortNameIgnoreCase(String bankShortName);

    boolean existsByBankLongNameIgnoreCase(String bankLongName);

    Bank findByBankShortNameIgnoreCase(String bankShortName);

    Bank findByBankLongNameIgnoreCase(String bankLongName);

    Page<Bank> findByBankShortNameContainingIgnoreCaseOrBankLongNameContainingIgnoreCaseOrBankIdOrIsActive(Pageable pageable, String keyword, String keyword2, Integer keyword3, Boolean keyword4);

    List<Bank> findAllByIsActive(boolean isActive);

}
