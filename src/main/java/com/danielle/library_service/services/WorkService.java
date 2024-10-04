package com.danielle.library_service.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.danielle.library_service.entities.Citation;
import com.danielle.library_service.entities.Foreword;
import com.danielle.library_service.entities.Library;
import com.danielle.library_service.entities.Loan;
import com.danielle.library_service.entities.Series;
import com.danielle.library_service.entities.Volume;
import com.danielle.library_service.entities.Work;
import com.danielle.library_service.entities.enums.LoanStatus;
import com.danielle.library_service.entities.enums.WorkType;
import com.danielle.library_service.repositories.CitationRepository;
import com.danielle.library_service.repositories.ForewordRepository;
import com.danielle.library_service.repositories.LoanRepository;
import com.danielle.library_service.repositories.SeriesRepository;
import com.danielle.library_service.repositories.VolumeRepository;
import com.danielle.library_service.repositories.WorkRepository;

public class WorkService {
	WorkRepository worksRepository;
	ForewordRepository forewordRepository;
	VolumeRepository volumeRepository;
	SeriesRepository seriesRepository;
	CitationRepository citationRepository;
	LoanRepository loanRepository;
	
	@Autowired
	public WorkService(WorkRepository worksRepository,
			ForewordRepository forewordRepository,
			SeriesRepository seriesRepository,
			VolumeRepository volumeRepository,
			CitationRepository citationRepository,
			LoanRepository loanRepository) {
		this.worksRepository = worksRepository;
		this.forewordRepository = forewordRepository;
		this.seriesRepository = seriesRepository;
		this.volumeRepository = volumeRepository;
		this.citationRepository = citationRepository;
		this.loanRepository = loanRepository;
	}
		
	public long createWork(Work newWork) {
		return worksRepository.save(newWork).getId();
	}
	
	public Optional<Work> getWorkById(long id) {
		return worksRepository.findById(id);
	}
	
	public List<Work> getAllWorks() {
		return (List<Work>) worksRepository.findAll();
	}
	
	public Work updateWork(Work work) {
		return worksRepository.save(work);
	}
	
	public void deleteWork(Work work) {
		worksRepository.delete(work);
	}
	
	public List<Work> findByTitle(String title) {
		return worksRepository.findByTitle(title);
	}
	
	public List<Work> getAllCodexes() {
		return worksRepository.findByWorkType(WorkType.CODEX);
	}
	
	public List<Work> getAllScrolls() {
		return worksRepository.findByWorkType(WorkType.SCROLL);
	}
	
	public List<Work> getAllTablets() {
		return worksRepository.findByWorkType(WorkType.TABLET);
	}
	
	public List<Work> getRareWorks() {
		return worksRepository.getRareWorks();
	}
	
	public List<Work> getCommonWorks() {
		return worksRepository.getCommonWorks();
	}
	
	public List<Foreword> getAllForewordsForWork(long workId) {
		return forewordRepository.findByWorkId(workId);
	}
	
	public Series getSeriesFromWork(long workId) throws Exception {
		Optional<Volume> volumes = volumeRepository.findByWorkId(workId);
		if(volumes.isPresent()) {
			Series series = volumes.get().getSeries();
			return series;
		} else {
			throw new Exception("Work is not part of a series");
		}
	}
	
	public List<Work> getAllLoanedWorksByLibrary(Library library) {
		long libraryId = library.getId();
		List<Loan> loans = loanRepository.findByLibraryId(libraryId);
		List<Work> works = loans.stream().map(Loan::getWork).collect(Collectors.toList());
		return works;
	}
	
	public List<Work> getAllLoanedWorksByLibraryAndStatus(Library library, LoanStatus status) {
		long libraryId = library.getId();
		List<Loan> loans = loanRepository.findByLibraryId(libraryId);
		List<Work> works = loans.stream().filter(loan -> loan.getLoanStatus().equals(status)).map(Loan::getWork).collect(Collectors.toList());
		return works;
	}
	
	public List<Work> getAllLoanedWorksByStatus(LoanStatus status) {
		List<Loan> loans = loanRepository.findByStatus(status);
		List<Work> works = loans.stream().filter(loan -> loan.getLoanStatus().equals(status)).map(Loan::getWork).collect(Collectors.toList());
		return works;
	}
	
	public List<Work> getAllCitationsInWork(Work work) {
		long workId = work.getId();
		List<Citation> citations = citationRepository.findByCitedIn(workId);
		List<Work> works = citations.stream().map(Citation::getWorkCited).collect(Collectors.toList());
		return works;
	}
	
	public List<Work> getAllCitationsOfWork(Work work) {
		long workId = work.getId();
		List<Citation> citations = citationRepository.findByWorkCited(workId);
		List<Work> works = citations.stream().map(Citation::getCitedIn).collect(Collectors.toList());
		return works;
	}
	
}
