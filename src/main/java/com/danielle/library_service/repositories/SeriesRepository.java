package com.danielle.library_service.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.danielle.library_service.entities.Series;

public interface SeriesRepository extends CrudRepository<Series, Long>{
	List<Series> findByName(String name);
}
