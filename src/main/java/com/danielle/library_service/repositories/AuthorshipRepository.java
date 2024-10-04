package com.danielle.library_service.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.danielle.library_service.entities.Authorship;

public interface AuthorshipRepository extends CrudRepository<Authorship, Long>{
	List<Authorship> findByAuthorId(long authorId);
	
	Authorship findByAuthorIdAndWorkId(long authorId, long workId);
}
