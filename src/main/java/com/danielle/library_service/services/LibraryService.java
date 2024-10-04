package com.danielle.library_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.danielle.library_service.entities.Library;
import com.danielle.library_service.repositories.LibraryRepository;

public class LibraryService {
	LibraryRepository libraryRepository;
	
	@Autowired
	public LibraryService(LibraryRepository libraryRepository) {
		this.libraryRepository = libraryRepository;
	}
	
	public long createLibrary(Library library) {
		return libraryRepository.save(library).getId();
	}
	
	public Optional<Library> getById(long id) {
		return libraryRepository.findById(id);
	}
	
	public Library updateLibrary(Library library) {
		return libraryRepository.save(library);
	}
	
	public void deleteLibrary(Library library) {
		libraryRepository.delete(library);
	}
	
	public List<Library> getByName(String name) {
		return libraryRepository.findByName(name);
	}
}
