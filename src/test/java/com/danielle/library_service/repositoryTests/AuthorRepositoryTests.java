package com.danielle.library_service.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.danielle.library_service.TestHelper;
import com.danielle.library_service.entities.Author;
import com.danielle.library_service.repositories.AuthorRepository;

@DataJpaTest
public class AuthorRepositoryTests {
	@Autowired
	private AuthorRepository authorRepository;
	
	TestHelper helper = new TestHelper();
	
	@AfterEach
	public void cleanup() {
		authorRepository.deleteAll();
	}
	
	@Test
	public void testFindByName() {
		Author author = helper.createAuthor();
		authorRepository.save(author);
		
		List<Author> savedAuthor = authorRepository.findByName(author.getName());
		
		assertEquals(savedAuthor.size(), 1);
		assertEquals(savedAuthor.get(0).getName(), author.getName());
	}

}
