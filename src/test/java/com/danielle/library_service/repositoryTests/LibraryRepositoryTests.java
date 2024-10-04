package com.danielle.library_service.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.danielle.library_service.TestHelper;
import com.danielle.library_service.entities.Library;
import com.danielle.library_service.repositories.LibraryRepository;

@DataJpaTest
public class LibraryRepositoryTests {
	@Autowired
	private LibraryRepository libraryRepository;
	
	TestHelper helper = new TestHelper();
	
	@AfterEach
	public void cleanup() {
		libraryRepository.deleteAll();
	}
	
	@Test
	public void testFindByName() {
		Library library = helper.createLibrary();
		libraryRepository.save(library);
		
		List<Library> response = libraryRepository.findByName(library.getName());
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getName(), library.getName());
	}
}
