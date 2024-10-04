package com.danielle.library_service.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.danielle.library_service.TestHelper;
import com.danielle.library_service.entities.Loan;
import com.danielle.library_service.repositories.LibraryRepository;
import com.danielle.library_service.repositories.LoanRepository;
import com.danielle.library_service.repositories.WorkRepository;

@DataJpaTest
public class LoanRepositoryTests {
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private LibraryRepository libraryRepository;
	
	@Autowired
	private WorkRepository workRepository;
	
	TestHelper helper = new TestHelper();
	
	Loan loan;
	
	@BeforeEach
	public void setup() {
		loan = helper.createReturnedLoan();
		libraryRepository.save(loan.getLibrary());
		workRepository.save(loan.getWork());
		loanRepository.save(loan);
	}
	
	@AfterEach
	public void cleanup() {
		loanRepository.deleteAll();
		libraryRepository.deleteAll();
		workRepository.deleteAll();
	}
	
	@Test
	public void testFindByStatus() {
		List<Loan> response = loanRepository.findByStatus(loan.getLoanStatus());
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), loan.getId());
	}
	
	@Test
	public void testFindByLibraryId() {
		List<Loan> response = loanRepository.findByLibraryId(loan.getLibrary().getId());
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), loan.getId());
	}
	
	@Test
	public void testFindByWorkId() {
		List<Loan> response = loanRepository.findLoansByWorkId(loan.getWork().getId());
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), loan.getId());
	}

}
