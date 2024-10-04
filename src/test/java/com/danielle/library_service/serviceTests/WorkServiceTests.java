package com.danielle.library_service.serviceTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.danielle.library_service.TestHelper;
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
import com.danielle.library_service.services.WorkService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class WorkServiceTests {
	
	@Mock
	private WorkRepository workRepository;
	
	@Mock
	private ForewordRepository forewordRepository;
	
	@Mock
	private VolumeRepository volumeRepository;
	
	@Mock
	private SeriesRepository seriesRepository;
	
	@Mock
	private CitationRepository citationRepository;
	
	@Mock
	private LoanRepository loanRepository;
	
	@InjectMocks
	private WorkService worksService = new WorkService(workRepository,
			forewordRepository, seriesRepository, volumeRepository,
			citationRepository, loanRepository);
	
	TestHelper helper = new TestHelper();
	
	@Test
	public void testSaveWork() {
		Work work = helper.createWork();
		long workId = work.getId();
		
		Mockito.when(workRepository.save(work)).thenReturn(work);
		
		long response = worksService.createWork(work);
		
		assertNotNull(response);
		assertEquals(workId, response);
	}
	
	@Test
	public void testGetWorkById() {
		Work work = helper.createWork();
		long id = work.getId();
		
		Mockito.when(workRepository.findById(id)).thenReturn(Optional.of(work));
		
		Optional<Work> response = worksService.getWorkById(id);
		
		assertTrue(response.isPresent());
		assertEquals(response.get().getId(), id);
	}
	
	@Test
	public void testGetAllWorks() {
		Work work = helper.createWork();
		List<Work> works = new ArrayList<>();
		works.add(work);
		
		Mockito.when(workRepository.findAll()).thenReturn(works);
		
		List<Work> response = worksService.getAllWorks();
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), work.getId());
	}
	
	@Test
	public void testUpdateWork() {
		Work work = helper.createWork();
		
		Mockito.when(workRepository.save(work)).thenReturn(work);
		
		Work response = worksService.updateWork(work);
		
		assertNotNull(response);
	}
	
	@Test
	public void testDelete() {
		Work work = helper.createWork();
		
		worksService.deleteWork(work);
		
		Mockito.verify(workRepository, times(1)).delete(work);
	}
	
	@Test
	public void testFindByTitle() {
		Work work = helper.createWork();
		String title = work.getTitle();
		List<Work> works = new ArrayList<>();
		works.add(work);
		
		Mockito.when(workRepository.findByTitle(title)).thenReturn(works);
		
		List<Work> response = worksService.findByTitle(title);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getTitle(), work.getTitle());
	}
	
	@Test
	public void testGetAllCodexes() {
		Work codex = helper.createCodex();
		List<Work> works = new ArrayList<>();
		works.add(codex);
		
		Mockito.when(workRepository.findByWorkType(WorkType.CODEX)).thenReturn(works);
		
		List<Work> response = worksService.getAllCodexes();
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getWorkType(), WorkType.CODEX);
	}
	
	@Test
	public void testGetAllScrolls() {
		Work scroll = helper.createScroll();
		List<Work> scrolls = new ArrayList<>();
		scrolls.add(scroll);
		
		Mockito.when(workRepository.findByWorkType(WorkType.SCROLL)).thenReturn(scrolls);
		
		List<Work> response = worksService.getAllScrolls();
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getWorkType(), WorkType.SCROLL);
	}
	
	@Test
	public void testGetAllTablets() {
		Work tablet = helper.createTablet();
		List<Work> tablets = new ArrayList<>();
		tablets.add(tablet);
		
		Mockito.when(workRepository.findByWorkType(WorkType.TABLET)).thenReturn(tablets);
		
		List<Work> response = worksService.getAllTablets();
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getWorkType(), WorkType.TABLET);
	}
	
	@Test
	public void testGetRareWorks() {
		Work work = helper.createRareWork();
		List<Work> works = new ArrayList<>();
		works.add(work);
		
		Mockito.when(workRepository.getRareWorks()).thenReturn(works);
		
		List<Work> response = worksService.getRareWorks();
		
		assertEquals(response.size(), 1);
		assertTrue(response.get(0).isRare());
	}
	
	@Test
	public void testGetCommonWorks() {
		Work work = helper.createWork();
		List<Work> works = new ArrayList<>();
		works.add(work);
		
		Mockito.when(workRepository.getCommonWorks()).thenReturn(works);
		
		List<Work> response = worksService.getCommonWorks();
		
		assertEquals(response.size(), 1);
		assertFalse(response.get(0).isRare());
	}
	
	@Test
	public void testGetAllForewordsForWork() {
		Foreword foreword = helper.createForeword();
		long workId = foreword.getWork().getId();
		List<Foreword> forewords = new ArrayList<>();
		forewords.add(foreword);
		
		Mockito.when(forewordRepository.findByWorkId(workId)).thenReturn(forewords);
		
		List<Foreword> response = worksService.getAllForewordsForWork(workId);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), foreword.getId());
	}
	
	@Test
	public void testGetSeriesFromWork() throws Exception {
		Volume volume = helper.createVolume();
		Series series = volume.getSeries();
		Work work = volume.getWork();
		
		Mockito.when(volumeRepository.findByWorkId(work.getId())).thenReturn(Optional.of(volume));
		
		Series response = worksService.getSeriesFromWork(work.getId());
		
		assertEquals(response.getId(), series.getId());
	}
	
	@Test
	public void testGetSeriesFromWork_throwsExceptionIfNotPartOfSeries() throws Exception {
		Work work = helper.createWork();
		long workId = work.getId();
		
		Mockito.when(volumeRepository.findByWorkId(workId)).thenReturn(Optional.empty());
		
		Exception exception = assertThrows(Exception.class, () -> {
			worksService.getSeriesFromWork(workId);
		});
		
		assertEquals(exception.getMessage(), "Work is not part of a series");
	}
	
	@Test
	public void testGetLoanedWorksByLibrary() {
		Loan loan = helper.createReturnedLoan();
		List<Loan> loans = new ArrayList<>();
		loans.add(loan);
		Library library = loan.getLibrary();
		Work work = loan.getWork();
		
		Mockito.when(loanRepository.findByLibraryId(library.getId())).thenReturn(loans);
		
		List<Work> response = worksService.getAllLoanedWorksByLibrary(library);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), work.getId());
	}
	
	@Test
	public void testGetLoanedWorksByLibraryAndStatus() {
		Loan returnedLoan = helper.createReturnedLoan();
		returnedLoan.setLoanStatus(LoanStatus.RETURNED);
		Loan inTransitLoan = helper.createReturnedLoan();
		inTransitLoan.setLoanStatus(LoanStatus.IN_TRANSIT_BACK);
		
		List<Loan> loans = new ArrayList<>();
		loans.add(returnedLoan);
		loans.add(inTransitLoan);
		
		Library library = returnedLoan.getLibrary();
		Work work = returnedLoan.getWork();
		
		Mockito.when(loanRepository.findByLibraryId(library.getId())).thenReturn(loans);
		List<Work> response = worksService.getAllLoanedWorksByLibraryAndStatus(library, LoanStatus.IN_TRANSIT_BACK);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), work.getId());
	}
	
	@Test
	public void testGetAllCitationsInWork() {
		Work work = helper.createWork();
		Work citedWork = helper.createWork();
		Citation citation = helper.createCitation();
		citation.setCitedIn(work);
		citation.setWorkCited(citedWork);
		List<Citation> citations = new ArrayList<>();
		citations.add(citation);	
		List<Work> citedWorks = new ArrayList<>();
		citedWorks.add(citedWork);
		List<Long> workIds = new ArrayList<>();
		workIds.add(citedWork.getId());
		
		Mockito.when(citationRepository.findByCitedIn(work.getId())).thenReturn(citations);
		
		List<Work> response = worksService.getAllCitationsInWork(work);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), citedWork.getId());
	}
	
	@Test
	public void testGetAllCitationsOfWork() {
		Work work = helper.createWork();
		Work citedWork = helper.createWork();
		Citation citation = helper.createCitation();
		citation.setCitedIn(work);
		citation.setWorkCited(citedWork);
		List<Citation> citations = new ArrayList<>();
		citations.add(citation);	
		List<Work> citedIn = new ArrayList<>();
		citedIn.add(work);
		List<Long> workIds = new ArrayList<>();
		workIds.add(work.getId());
		
		Mockito.when(citationRepository.findByWorkCited(work.getId())).thenReturn(citations);
		
		List<Work> response = worksService.getAllCitationsOfWork(citedWork);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), work.getId());
	}
}
