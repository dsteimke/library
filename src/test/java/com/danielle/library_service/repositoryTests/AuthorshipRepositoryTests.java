package com.danielle.library_service.repositoryTests;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.danielle.library_service.TestHelper;
import com.danielle.library_service.entities.Author;
import com.danielle.library_service.entities.Authorship;
import com.danielle.library_service.entities.Work;
import com.danielle.library_service.repositories.AuthorshipRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class AuthorshipRepositoryTests {
	@Autowired
	private AuthorshipRepository authorshipRepository;
	
	TestHelper helper = new TestHelper();
	
	@AfterEach
	public void cleanup() {
		authorshipRepository.deleteAll();
	}
	
	@Test
	public void testFindByAuthorId() {
		Work work1 = helper.createWork();
		Author author1 = helper.createAuthor();
		
		Work work2 = helper.createWork();
		Author author2 = helper.createAuthor();
		
		Authorship authorship = new Authorship(work1, author1);
		Authorship authorship2 = new Authorship(work2, author2);
		
		authorshipRepository.save(authorship);
		authorshipRepository.save(authorship2);
		
		List<Authorship> foundAuthorship = authorshipRepository.findByAuthorId(author1.getId());
		
		assertEquals(foundAuthorship.size(), 1);
		assertEquals(foundAuthorship.get(0).getAuthor().getId(), author1.getId());
	}
	
	@Test
	public void testFindByAuthorIdAndWorkId() {
		Work work = helper.createWork();
		Author author = helper.createAuthor();
		Authorship authorship = new Authorship(work, author);
		
		authorshipRepository.save(authorship);
		
		Authorship retrievedAuthorship = authorshipRepository.findByAuthorIdAndWorkId(author.getId(), work.getId());
		
		assertEquals(retrievedAuthorship.getId(), authorship.getId());
	}

}
