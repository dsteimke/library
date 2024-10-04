package com.danielle.library_service.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

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
import com.danielle.library_service.repositories.LibraryRepository;
import com.danielle.library_service.services.LibraryService;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTests {
	@Mock
	private LibraryRepository libraryRepository;
	
	@InjectMocks
	private LibraryService libraryService = new LibraryService(libraryRepository);
	
	TestHelper helper = new TestHelper();
	
	Library library;
	
	@BeforeEach
	public void setup() {
		library = helper.createLibrary();
	}
	
	@Test
	public void testCreateLibrary() {
		Mockito.when(libraryRepository.save(library)).thenReturn(library);
		
		long response = libraryService.createLibrary(library);
		
		assertEquals(response, library.getId());
	}
	
	@Test
	public void testGetCitationByLibraryId() {
		Mockito.when(libraryRepository.findById(library.getId())).thenReturn(Optional.of(library));
		
		Optional<Library> response = libraryService.getById(library.getId());
		
		assertTrue(response.isPresent());
		assertEquals(response.get().getId(), library.getId());
	}
	
	@Test
	public void testUpdateLibrary() {
		Mockito.when(libraryRepository.save(library)).thenReturn(library);
		
		Library response = libraryService.updateLibrary(library);
		
		assertEquals(response.getId(), library.getId());
	}
	
	@Test
	public void testDeleteLibrary() {
		libraryService.deleteLibrary(library);
		
		Mockito.verify(libraryRepository, times(1)).delete(library);
	}

}
