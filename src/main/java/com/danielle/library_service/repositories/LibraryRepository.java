package com.danielle.library_service.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.danielle.library_service.entities.Library;

public interface LibraryRepository extends CrudRepository<Library, Long>{
	List<Library> findByName(String name);
}
