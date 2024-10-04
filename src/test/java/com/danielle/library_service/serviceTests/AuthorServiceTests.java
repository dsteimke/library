package com.danielle.library_service.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.danielle.library_service.TestHelper;
import com.danielle.library_service.entities.Author;
import com.danielle.library_service.entities.Authorship;
import com.danielle.library_service.entities.Foreword;
import com.danielle.library_service.entities.Work;
import com.danielle.library_service.repositories.AuthorRepository;
import com.danielle.library_service.repositories.AuthorshipRepository;
import com.danielle.library_service.repositories.ForewordRepository;
import com.danielle.library_service.repositories.WorkRepository;
import com.danielle.library_service.services.AuthorService;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTests {
	@Mock
	private AuthorRepository authorRepository;
	
	@Mock
	private AuthorshipRepository authorshipRepository;
	
	@Mock
	private WorkRepository workRepository;
	
	@Mock
	private ForewordRepository forewordRepository;
	
	@InjectMocks
	private AuthorService authorService = new AuthorService(authorRepository, authorshipRepository, workRepository, forewordRepository);

	TestHelper helper = new TestHelper();
	
	@Test
	public void testCreateAuthor() {
		Author author = helper.createAuthor();
		long authorId = author.getId();
		
		Mockito.when(authorRepository.save(author)).thenReturn(author);
		
		long savedId = authorService.createAuthor(author);
		
		assertEquals(authorId, savedId);
	}
	
	@Test
	public void testGetAuthorById() {
		Author author = helper.createAuthor();
		
		Mockito.when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
		
		Optional<Author> response = authorService.getAuthorById(author.getId());
		
		assertTrue(response.isPresent());
		assertEquals(author.getId(), response.get().getId());
	}

	@Test
	public void testUpdateAuthor() {
		Author author = helper.createAuthor();
		
		Mockito.when(authorRepository.save(author)).thenReturn(author);
		
		Author response = authorService.updateAuthor(author);
		
		assertEquals(response.getId(), author.getId());
	}

	@Test
	public void testDeleteAuthorById() {
		Author author = helper.createAuthor();
		
		authorService.deleteAuthor(author);
		
		Mockito.verify(authorRepository, times(1)).delete(author);
	}
	
	@Test
	public void testGetAuthorByName() {
		Author author = helper.createAuthor();
		List<Author> authors = new ArrayList<>();
		authors.add(author);
		
		Mockito.when(authorRepository.findByName(author.getName())).thenReturn(authors);
		
		List<Author> response = authorService.getAuthorByName(author.getName());
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), author.getId());
	} 
	
	@Test
	public void testGetAllWorksByAuthor() {
		Author author = helper.createAuthor();
		Work work1 = helper.createWork();
		Work work2 = helper.createWork();
		List<Authorship> authorships = new ArrayList<>();
		authorships.add(new Authorship(work1, author));
		authorships.add(new Authorship(work2, author));
		
		Mockito.when(authorshipRepository.findByAuthorId(author.getId())).thenReturn(authorships);
		
		List<Work> response = authorService.getAllWorksByAuthor(author.getId());
		
		assertEquals(response.size(), 2);
		assertEquals(response.get(0).getId(), work1.getId());
		assertEquals(response.get(1).getId(), work2.getId());
	}

	@Test
	public void testRemoveAuthorFromWork_authorshipExists() {
		Author author = helper.createAuthor();
		Work work = helper.createWork();
		Authorship authorship = new Authorship(work, author);
		
		Mockito.when(authorshipRepository.findByAuthorIdAndWorkId(author.getId(), work.getId())).thenReturn(authorship);
		
		authorService.removeAuthorFromWork(author.getId(), work.getId());
		
		Mockito.verify(authorshipRepository, times(1)).delete(authorship);
	}
	
	@Test
	public void testRemoveAuthorFromWork_authorshipDoesNotExist() {
		Mockito.when(authorshipRepository.findByAuthorIdAndWorkId(1, 1)).thenReturn(null);
		
		authorService.removeAuthorFromWork(1, 1);
		
		Mockito.verify(authorshipRepository, never()).delete(any());
	}
	
	@Test
	public void testAddAuthorToWork() throws Exception {
		Work work = helper.createWork();
		Author author = helper.createAuthor();
		
		Mockito.when(workRepository.findById(work.getId())).thenReturn(Optional.of(work));
		Mockito.when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
		
		authorService.addAuthorToWork(author.getId(), work.getId());
		
		Mockito.verify(authorshipRepository, times(1)).save(any());
	}
	
	@Test
	public void testAddAuthorToWork_authorDoesNotExist() throws Exception {
		Work work = helper.createWork();
		Author author = helper.createAuthor();
		
		Mockito.when(workRepository.findById(work.getId())).thenReturn(Optional.of(work));
		Mockito.when(authorRepository.findById(author.getId())).thenReturn(Optional.empty());
		
		Exception exception = assertThrows(Exception.class, () -> {
			authorService.addAuthorToWork(author.getId(), work.getId());
		});
		
		assertEquals(exception.getMessage(), "Author does not exist");
		Mockito.verify(authorshipRepository, never()).save(any());
	}
	
	@Test
	public void testAddAuthorToWork_workDoesNotExist() {
		Work work = helper.createWork();
		Author author = helper.createAuthor();
		
		Mockito.when(workRepository.findById(work.getId())).thenReturn(Optional.empty());
		Mockito.when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
		
		Exception exception = assertThrows(Exception.class, () -> {
			authorService.addAuthorToWork(author.getId(), work.getId());
		});
		
		assertEquals(exception.getMessage(), "Work does not exist");
		Mockito.verify(authorshipRepository, never()).save(any());
	}
	
	@Test
	public void testGetAllForewordsForWork() {
		Foreword foreword = helper.createForeword();
		List<Foreword> forewords = new ArrayList<>();
		forewords.add(foreword);
		long authorId = foreword.getAuthor().getId();
		
		Mockito.when(forewordRepository.findByAuthorId(authorId)).thenReturn(forewords);
		
		List<Foreword> response = authorService.getAllForewordsByAuthor(authorId);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), foreword.getId());
	}
}
