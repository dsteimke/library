package com.danielle.library_service.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.danielle.library_service.TestHelper;
import com.danielle.library_service.entities.Citation;
import com.danielle.library_service.repositories.CitationRepository;
import com.danielle.library_service.repositories.WorkRepository;

@DataJpaTest
public class CitationRepositoryTests {
	@Autowired
	private CitationRepository citationRepository;
	
	@Autowired
	private WorkRepository workRepository;
	
	TestHelper helper = new TestHelper();
	
	Citation citation;
	
	@BeforeEach
	public void setup() {
		citation = helper.createCitation();
		workRepository.save(citation.getCitedIn());
		workRepository.save(citation.getWorkCited());
		citationRepository.save(citation);
	}
	
	@AfterEach
	public void cleanup() {
		citationRepository.deleteAll();
		workRepository.deleteAll();
	}
	
	@Test
	public void testFindByWorkCited() {
		long workCitedId = citation.getWorkCited().getId();
		
		List<Citation> response = citationRepository.findByWorkCited(workCitedId);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getWorkCited().getId(), workCitedId);
	}
	
	@Test
	public void testFindByCitedBy() {
		long citedInId = citation.getCitedIn().getId();
		
		List<Citation> response = citationRepository.findByCitedIn(citedInId);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getCitedIn().getId(), citedInId);
	}
}
