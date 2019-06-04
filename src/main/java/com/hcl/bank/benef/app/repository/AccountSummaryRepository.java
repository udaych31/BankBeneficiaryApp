package com.hcl.bank.benef.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.bank.benef.app.entity.AccountSummary;

@Repository
public interface AccountSummaryRepository extends JpaRepository<AccountSummary, Long> {
	
	public AccountSummary findByAccountNoAndIfscCodeAndEmail(Long accountNo,String ifscCode,String email);
	
	

}
