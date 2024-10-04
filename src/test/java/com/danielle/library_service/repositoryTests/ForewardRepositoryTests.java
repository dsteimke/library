package com.danielle.library_service.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.danielle.library_service.TestHelper;
import com.danielle.library_service.entities.Author;
import com.danielle.library_service.entities.Foreword;
import com.danielle.library_service.entities.Work;
import com.danielle.library_service.repositories.AuthorRepository;
import com.danielle.library_service.repositories.ForewordRepository;
import com.danielle.library_service.repositories.WorkRepository;

@DataJpaTest
public class ForewardRepositoryTests {
	@Autowired
	private ForewordRepository forewordRepository;
	
	@Autowired
	private WorkRepository workRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	TestHelper helper = new TestHelper();
	
	Foreword foreword;
	Work work;
	Author author;
	
	@BeforeEach
	public void setup() {
		work = helper.createWork();
		workRepository.save(work);
		author = helper.createAuthor();
		authorRepository.save(author);
		foreword = new Foreword(work, author, "this is a very long foreword");
		forewordRepository.save(foreword);
	}
	
	@AfterEach
	public void cleanup() {
		forewordRepository.deleteAll();
		workRepository.deleteAll();
		authorRepository.deleteAll();
	}
	
	@Test
	public void testFindByAuthorId() {
		List<Foreword> forewords = forewordRepository.findByAuthorId(author.getId());
		assertEquals(forewords.size(), 1);
		Foreword response = forewords.get(0);
		assertEquals(response.getAuthor().getId(), author.getId());
		assertEquals(response.getWork().getId(), work.getId());
	}
	
	@Test
	public void testFindByWorkId() {
		List<Foreword> forwards = forewordRepository.findByWorkId(work.getId());
		assertEquals(forwards.size(), 1);
		Foreword response = forwards.get(0);
		assertEquals(response.getAuthor().getId(), author.getId());
		assertEquals(response.getWork().getId(), work.getId());
	}

}
