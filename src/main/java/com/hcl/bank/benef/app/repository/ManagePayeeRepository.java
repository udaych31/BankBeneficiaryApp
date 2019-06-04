package com.hcl.bank.benef.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.bank.benef.app.entity.ManagePayee;



@Repository
public interface ManagePayeeRepository extends JpaRepository<ManagePayee, Long>{

	public ManagePayee findByPayeeId(Long payeeId);

}
