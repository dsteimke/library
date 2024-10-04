package com.danielle.library_service.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.danielle.library_service.entities.Work;
import com.danielle.library_service.entities.enums.WorkType;

public interface WorkRepository extends CrudRepository<Work, Long>{
	
	List<Work> findByTitle(String title);
	
	List<Work> findByWorkType(WorkType workType);
	
	@Query(value = "SELECT * FROM works WHERE is_rare = true", nativeQuery = true)
	List<Work> getRareWorks();
	
	@Query(value = "SELECT * FROM works WHERE is_rare = false", nativeQuery = true)
	List<Work> getCommonWorks();
}
