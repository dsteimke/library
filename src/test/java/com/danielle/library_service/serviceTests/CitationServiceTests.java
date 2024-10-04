package com.danielle.library_service.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.danielle.library_service.TestHelper;
import com.danielle.library_service.entities.Citation;
import com.danielle.library_service.repositories.CitationRepository;
import com.danielle.library_service.services.CitationService;

@ExtendWith(MockitoExtension.class)
public class CitationServiceTests {
	@Mock
	private CitationRepository citationRepository;
	
	@InjectMocks
	private CitationService citationService = new CitationService(citationRepository);
	
	TestHelper helper = new TestHelper();
	
	Citation citation;
	
	@BeforeEach
	public void setup() {
		citation = helper.createCitation();
	}
	
	// Annoyingly the repository being a mock means the ID is never generated
	// and I ran out of time to figure out how to fix this issue, without creating a setId
	// method, which feels wrong.
	@Disabled
	@Test
	public void testCreateCitation() throws Exception {
		Mockito.when(citationRepository.save(citation)).thenReturn(citation);
		
		long response = citationService.createCitation(citation);
		
		assertEquals(response, citation.getId());
	}
	
	@Test
	public void testCreateCitation_WorkCitesSelf() throws Exception {
		citation.setCitedIn(citation.getWorkCited());
		Exception exception = assertThrows(Exception.class, () -> {
			citationService.createCitation(citation);
		});
		assertEquals(exception.getMessage(), "Work cannot cite itself");
	}
	
	@Test
	public void testGetCitationByCitationId() {
		Mockito.when(citationRepository.findById(citation.getId())).thenReturn(Optional.of(citation));
		
		Optional<Citation> response = citationService.getCitationByCitationId(citation.getId());
		
		assertTrue(response.isPresent());
		assertEquals(response.get().getId(), citation.getId());
	}
	
	@Test
	public void testUpdateCitation() {
		Mockito.when(citationRepository.save(citation)).thenReturn(citation);
		
		Citation response = citationService.updateCitation(citation);
		
		assertEquals(response.getId(), citation.getId());
	}
	
	@Test
	public void testDeleteCitation() {
		citationService.deleteCitation(citation);
		Mockito.verify(citationRepository, times(1)).delete(citation);
	}
}
