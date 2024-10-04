package com.danielle.library_service.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.danielle.library_service.entities.Author;
import com.danielle.library_service.entities.Authorship;
import com.danielle.library_service.entities.Foreword;
import com.danielle.library_service.entities.Work;
import com.danielle.library_service.repositories.AuthorRepository;
import com.danielle.library_service.repositories.AuthorshipRepository;
import com.danielle.library_service.repositories.ForewordRepository;
import com.danielle.library_service.repositories.WorkRepository;

public class AuthorService {
	AuthorRepository authorRepository;
	AuthorshipRepository authorshipRepository;
	WorkRepository workRepository;
	ForewordRepository forewordRepository;
	
	@Autowired
	public AuthorService(AuthorRepository authorsRepository, AuthorshipRepository authorshipRepository, WorkRepository worksRepository, ForewordRepository forewordRepository) {
		this.authorRepository = authorsRepository;
		this.authorshipRepository = authorshipRepository;
		this.workRepository = worksRepository;
		this.forewordRepository = forewordRepository;
	}
	
	public long createAuthor(Author author) {
		return authorRepository.save(author).getId();
	}
	
	public Optional<Author> getAuthorById(long id) {
		return authorRepository.findById(id);
	}
	
	public Author updateAuthor(Author author) {
		return authorRepository.save(author);
	}
	
	public void deleteAuthor(Author author) {
		authorRepository.delete(author);
	}
	
	public List<Author> getAuthorByName(String name) {
		return authorRepository.findByName(name);
	}

	public List<Work> getAllWorksByAuthor(long authorId) {
		List<Authorship> authorships = authorshipRepository.findByAuthorId(authorId);
		List<Work> works = authorships.stream().map(Authorship::getWork).collect(Collectors.toList());
		return works;
	}
	
	public void removeAuthorFromWork(long authorId, long workId) {
		Authorship authorshipToDelete = authorshipRepository.findByAuthorIdAndWorkId(authorId, workId);
		if(Objects.nonNull(authorshipToDelete)) {
			authorshipRepository.delete(authorshipToDelete);
		}
		// do nothing if the relationship isn't there so deletion is idempotent
	}
	
	// If I had more time I'd probably build out custom exceptions for these types of scenarios
	public Authorship addAuthorToWork(long authorId, long workId) throws Exception {
		Optional<Work> work = workRepository.findById(workId);
		Optional<Author> author = authorRepository.findById(authorId);
		
		if(work.isPresent() && author.isPresent()) {
			Authorship authorship = new Authorship(work.get(), author.get());
			return authorshipRepository.save(authorship);
		} else if (!work.isPresent()) {
			throw new Exception("Work does not exist");
		} else {
			throw new Exception("Author does not exist");
		}
		
	}
	
	public List<Foreword> getAllForewordsByAuthor(long authorId) {
		return forewordRepository.findByAuthorId(authorId);
	}

}
