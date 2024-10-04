package com.danielle.library_service.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.danielle.library_service.entities.Library;
import com.danielle.library_service.entities.Loan;
import com.danielle.library_service.entities.Work;
import com.danielle.library_service.entities.enums.LoanStatus;
import com.danielle.library_service.repositories.LoanRepository;

public class LoanService {
	LoanRepository loanRepository;
	
	@Autowired
	public LoanService(LoanRepository loanRepository) {
		this.loanRepository = loanRepository;
	}
	
	public long createLoan(Loan loan) {
		return loanRepository.save(loan).getId();
	}
	
	public long createLoan(Work work, Library library) throws Exception {
		if(work.isRare()) {
			throw new Exception("Rare work cannot be loaned");
		} 
		if(getCurrentLoanForWork(work).size() > 0) {
			throw new Exception("Work is already loaned out");
		}
		LocalDate today = LocalDate.now();
		Loan loan = new Loan(work, library, today, null, LoanStatus.IN_TRANSIT_OUT);
		return createLoan(loan);
	}
	
	public Optional<Loan> getById(long id) {
		return loanRepository.findById(id);
	}
	
	public Loan updateLoan(Loan loan) {
		return loanRepository.save(loan);
	}
	
	public void deleteLoan(Loan loan) {
		loanRepository.delete(loan);
	}

	public List<Loan> getAllLoansByStatus(LoanStatus loanStatus) {
		return loanRepository.findByStatus(loanStatus);
	}
	
	public List<Loan> getAllLoansByLibrary(Library library) {
		long libraryId = library.getId();
		return loanRepository.findByLibraryId(libraryId);
	}
	
	public List<Loan> getAllLoansByLibraryAndStatus(Library library, LoanStatus loanStatus) {
		long libraryId = library.getId();
		List<Loan> loans = loanRepository.findByLibraryId(libraryId);
		List<Loan> loansFilteredByStatus = loans.stream().filter(loan -> loan.getLoanStatus().equals(loanStatus)).collect(Collectors.toList());
		return loansFilteredByStatus;
	}
	
	public List<Loan> getAllLoansByWork(Work work) {
		long workId = work.getId();
		return loanRepository.findLoansByWorkId(workId);
	}
	
	public List<Loan> getCurrentLoanForWork(Work work) {
		long workId = work.getId();
		List<Loan> loans = loanRepository.findLoansByWorkId(workId);
		List<Loan> currentLoans = loans.stream().filter(loan -> isLoaned(loan)).collect(Collectors.toList());
		return currentLoans;
	}
	
	public void returnLoan(Work work) {
		// Making a lot of assumptions here about not caring who has the work, just caring that it's returned
		List<Loan> loans = loanRepository.findLoansByWorkId(work.getId());
		List<Loan> currentLoans = loans.stream().filter(loan -> loan.getLoanStatus() != LoanStatus.RETURNED).collect(Collectors.toList());
		if(currentLoans.size() > 0) {
			loans.stream().forEach(loan -> returnLoan(loan));
		}
	}
	
	private void returnLoan(Loan loan) {
		LocalDate today = LocalDate.now();
		loan.setReturnedDate(today);
		loanRepository.save(loan);
	}
	
	private boolean isLoaned(Loan loan) {
		return (loan.getLoanStatus() != LoanStatus.RETURNED) && (loan.getReturnedDate() == null);
	}
}
