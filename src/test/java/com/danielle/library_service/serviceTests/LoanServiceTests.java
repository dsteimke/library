package com.danielle.library_service.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.danielle.library_service.TestHelper;
import com.danielle.library_service.entities.Library;
import com.danielle.library_service.entities.Loan;
import com.danielle.library_service.entities.Work;
import com.danielle.library_service.entities.enums.LoanStatus;
import com.danielle.library_service.repositories.LoanRepository;
import com.danielle.library_service.services.LoanService;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTests {
	@Mock
	private LoanRepository loanRepository;
	
	@InjectMocks
	private LoanService loanService = new LoanService(loanRepository);
	
	TestHelper helper = new TestHelper();
	
	Loan loan;
	
	@BeforeEach
	public void setup() {
		loan = helper.createReturnedLoan();
	}
	
	@Test
	public void testCreate() {
		Mockito.when(loanRepository.save(loan)).thenReturn(loan);
		
		long response = loanService.createLoan(loan);
		
		assertEquals(response, loan.getId());
	}
	
	@Test
	public void testCreateLoanFromWork() throws Exception {
		Work work = helper.createWork();
		Library library = helper.createLibrary();
		loan.setLibrary(library);
		loan.setWork(work);
		
		Loan oldLoan = new Loan(work, library, helper.getRandomDate(), helper.getRandomDate(), LoanStatus.RETURNED);
		List<Loan> oldLoans = new ArrayList<>();
		oldLoans.add(oldLoan);
		
		Mockito.when(loanRepository.findLoansByWorkId(work.getId())).thenReturn(oldLoans);
		// The service creates a new loan that I don't have access to, so stub with any()
		Mockito.when(loanRepository.save(any())).thenReturn(loan);
		
		long response = loanService.createLoan(work, library);
		assertNotNull(response);
	}
	
	@Test
	public void testCreateLoanRareWork() throws Exception {
		Work work = helper.createRareWork();
		Library library = helper.createLibrary();
		Exception exception = assertThrows(Exception.class, () -> {
			loanService.createLoan(work, library);
		});
		assertEquals(exception.getMessage(), "Rare work cannot be loaned");
	}
	
	@Test
	public void testCreateLoanLoanedOutWork() throws Exception {
		Work work = helper.createWork();
		Library library = helper.createLibrary();
		Loan currentLoan = helper.createLoanedLoan();
		List<Loan> loans = new ArrayList<>();
		loans.add(currentLoan);
		
		Mockito.when(loanRepository.findLoansByWorkId(work.getId())).thenReturn(loans);
		
		Exception exception = assertThrows(Exception.class, () -> {
			loanService.createLoan(work, library);
		});
		assertEquals(exception.getMessage(), "Work is already loaned out");
	}
	
	@Test
	public void testGetById() {
		Mockito.when(loanRepository.findById(loan.getId())).thenReturn(Optional.of(loan));
		
		Optional<Loan> response = loanService.getById(loan.getId());
		
		assertTrue(response.isPresent());
		assertEquals(response.get().getId(), loan.getId());
	}
	
	@Test
	public void testUpdate() {
		Mockito.when(loanRepository.save(loan)).thenReturn(loan);
		
		Loan response = loanService.updateLoan(loan);
		
		assertEquals(response.getId(), loan.getId());
	}
	
	@Test
	public void testDelete() {
		loanService.deleteLoan(loan);
		
		Mockito.verify(loanRepository, times(1)).delete(loan);
	}
	
	@Test
	public void getAllLoansByStatus() {
		LoanStatus status = loan.getLoanStatus();
		List<Loan> loans = new ArrayList<>();
		loans.add(loan);
		
		Mockito.when(loanRepository.findByStatus(status)).thenReturn(loans);
		
		List<Loan> response = loanService.getAllLoansByStatus(status);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), loan.getId());
	}
	
	@Test
	public void getAllLoansByLibrary() {
		Library library = loan.getLibrary();
		List<Loan> loans = new ArrayList<>();
		loans.add(loan);
		
		Mockito.when(loanRepository.findByLibraryId(library.getId())).thenReturn(loans);
		
		List<Loan> response = loanService.getAllLoansByLibrary(library);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), loan.getId());
	}
	
	@Test
	public void getAllLoansByLibraryAndStatus() {
		Loan inTransitLoan = helper.createReturnedLoan();
		inTransitLoan.setLoanStatus(LoanStatus.IN_TRANSIT_OUT);
		Loan returnedLoan = helper.createReturnedLoan();
		returnedLoan.setLoanStatus(LoanStatus.RETURNED);
		List<Loan> loans = new ArrayList<>();
		loans.add(returnedLoan);
		loans.add(inTransitLoan);
		
		Mockito.when(loanRepository.findByLibraryId(inTransitLoan.getLibrary().getId())).thenReturn(loans);
		
		List<Loan> response = loanService.getAllLoansByLibraryAndStatus(inTransitLoan.getLibrary(), LoanStatus.RETURNED);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), returnedLoan.getId());
	}
	
	@Test
	public void getAllLoansByWork() {
		Work work = loan.getWork();
		List<Loan> loans = new ArrayList<>();
		loans.add(loan);
		
		Mockito.when(loanRepository.findLoansByWorkId(work.getId())).thenReturn(loans);
		
		List<Loan> response = loanService.getAllLoansByWork(work);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), loan.getId());
	}
	
	

}
