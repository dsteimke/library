package com.danielle.library_service.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.danielle.library_service.entities.Citation;

public interface CitationRepository extends CrudRepository<Citation, Long>{
	@Query(value = "SELECT * FROM citations WHERE work_cited = :workId", nativeQuery = true)
	List<Citation> findByWorkCited(@Param("workId") long workId);
	
	@Query(value = "SELECT * FROM citations WHERE cited_in = :workId", nativeQuery = true)
	List<Citation> findByCitedIn(@Param("workId") long workId);
}
