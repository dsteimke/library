package com.danielle.library_service.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.danielle.library_service.entities.Foreword;

public interface ForewordRepository extends CrudRepository<Foreword, Long>{
	List<Foreword> findByAuthorId(long authorId);
	
	List<Foreword> findByWorkId(long workId);
}
