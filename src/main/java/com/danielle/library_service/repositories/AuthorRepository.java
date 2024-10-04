package com.danielle.library_service.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.danielle.library_service.entities.Author;

public interface AuthorRepository extends CrudRepository<Author, Long>{
	List<Author> findByName(String name);
}
