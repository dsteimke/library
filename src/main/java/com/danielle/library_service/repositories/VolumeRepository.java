package com.danielle.library_service.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.danielle.library_service.entities.Volume;

public interface VolumeRepository extends CrudRepository<Volume, Long>{
	Optional<Volume> findByWorkId(long workId);
	
	List<Volume> findBySeriesId(long seriesId);
}
