package com.danielle.library_service.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.danielle.library_service.entities.Loan;
import com.danielle.library_service.entities.enums.LoanStatus;

public interface LoanRepository extends CrudRepository<Loan, Long>{
	List<Loan> findByStatus(LoanStatus loanStatus);
	
	@Query(value = "SELECT * FROM loan_history WHERE library_id = :libraryId", nativeQuery = true)
    List<Loan> findByLibraryId(@Param("libraryId") long libraryId);
	
	@Query(value = "SELECT * FROM loan_history WHERE work_id = :workId", nativeQuery = true)
    List<Loan> findLoansByWorkId(@Param("workId") long workId);
}
