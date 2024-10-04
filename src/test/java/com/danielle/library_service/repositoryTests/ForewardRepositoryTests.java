package com.danielle.library_service.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.danielle.library_service.TestHelper;
import com.danielle.library_service.entities.Author;
import com.danielle.library_service.entities.Foreword;
import com.danielle.library_service.entities.Work;
import com.danielle.library_service.repositories.ForewordRepository;

@DataJpaTest
public class ForewardRepositoryTests {
	@Autowired
	private ForewordRepository forewordRepository;
	
	TestHelper helper = new TestHelper();
	
	@AfterEach
	public void cleanup() {
		forewordRepository.deleteAll();
	}
	
	@Test
	public void testFindByAuthorId() {
		Foreword foreword = helper.createForeword();
		Author author = foreword.getAuthor();
		Work work = foreword.getWork();
		
		forewordRepository.save(foreword);
		
		List<Foreword> forewords = forewordRepository.findByAuthorId(author.getId());
		assertEquals(forewords.size(), 1);
		Foreword response = forewords.get(0);
		assertEquals(response.getAuthor().getId(), author.getId());
		assertEquals(response.getWork().getId(), work.getId());
	}
	
	@Test
	public void testFindByWorkId() {
		Work work = helper.createWork();
		Author author = helper.createAuthor();
		Foreword foreword = new Foreword(work, author, "this is a very long foreword");
		
		forewordRepository.save(foreword);
		
		List<Foreword> forwards = forewordRepository.findByWorkId(work.getId());
		assertEquals(forwards.size(), 1);
		Foreword response = forwards.get(0);
		assertEquals(response.getAuthor().getId(), author.getId());
		assertEquals(response.getWork().getId(), work.getId());
	}

}
