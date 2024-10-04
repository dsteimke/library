package com.danielle.library_service.repositoryTests;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.danielle.library_service.TestHelper;
import com.danielle.library_service.entities.Author;
import com.danielle.library_service.entities.Authorship;
import com.danielle.library_service.entities.Work;
import com.danielle.library_service.repositories.AuthorRepository;
import com.danielle.library_service.repositories.AuthorshipRepository;
import com.danielle.library_service.repositories.WorkRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class AuthorshipRepositoryTests {
	@Autowired
	private AuthorshipRepository authorshipRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private WorkRepository workRepository;
	
	TestHelper helper = new TestHelper();
	
	Authorship authorship;
	Work work;
	Author author;
	
	@BeforeEach
	public void setup() {
		work = helper.createWork();
		workRepository.save(work);
		
		author = helper.createAuthor();
		authorRepository.save(author);
		
		authorship = new Authorship(work, author);
		authorshipRepository.save(authorship);
	}
	
	@AfterEach
	public void cleanup() {
		authorshipRepository.deleteAll();
		workRepository.deleteAll();
		authorRepository.deleteAll();
	}
	
	@Test
	public void testFindByAuthorId() {
		List<Authorship> foundAuthorship = authorshipRepository.findByAuthorId(author.getId());
		
		assertEquals(foundAuthorship.size(), 1);
		assertEquals(foundAuthorship.get(0).getAuthor().getId(), author.getId());
	}
	
	@Test
	public void testFindByAuthorIdAndWorkId() {
		Authorship retrievedAuthorship = authorshipRepository.findByAuthorIdAndWorkId(author.getId(), work.getId());
		assertEquals(retrievedAuthorship.getId(), authorship.getId());
	}

}
