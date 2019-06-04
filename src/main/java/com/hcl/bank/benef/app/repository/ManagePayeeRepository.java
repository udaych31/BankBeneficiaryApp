package com.hcl.bank.benef.app.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.bank.benef.app.entity.ManagePayee;

@Repository
@Transactional
public interface ManagePayeeRepository extends JpaRepository<ManagePayee, Long> {

	public void deleteByPayeeId(Long payeeId);

	public ManagePayee findByPayeeId(Long payeeId);

}
