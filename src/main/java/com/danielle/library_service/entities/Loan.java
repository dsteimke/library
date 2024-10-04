package com.danielle.library_service.entities;

import java.time.LocalDate;

import com.danielle.library_service.entities.enums.LoanStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "loan_history")
public class Loan {
	// A future enhancement could be the concept of due dates
	// and past due loans. Current class assumes that all loans
	// are indefinite
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "work_id", nullable = false)
	private Work work;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "library_id", nullable = false)
	private Library library;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private LocalDate sentOutDate;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private LocalDate returnedDate;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private LoanStatus status;
	
	public Loan(Work work, Library library, LocalDate sentOutDate, LocalDate returnedDate, LoanStatus loanStatus) {
		this.work = work;
		this.library = library;
		this.sentOutDate = sentOutDate;
		this.returnedDate = returnedDate;
		this.status = loanStatus;
	}
	
	public long getId() {
		return id;
	}

	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public LocalDate getSentOutDate() {
		return sentOutDate;
	}

	public void setSentOutDate(LocalDate sentOutDate) {
		this.sentOutDate = sentOutDate;
	}

	public LocalDate getReturnedDate() {
		return returnedDate;
	}

	public void setReturnedDate(LocalDate returnedDate) {
		this.returnedDate = returnedDate;
	}
	
	public LoanStatus getLoanStatus() {
		return status;
	}

	public void setLoanStatus(LoanStatus status) {
		this.status = status;
	}
	
	
	
}