package com.danielle.library_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.danielle.library_service.entities.Series;
import com.danielle.library_service.entities.Volume;
import com.danielle.library_service.repositories.SeriesRepository;

public class SeriesService {
	SeriesRepository seriesRepository;
	
	@Autowired
	public SeriesService(SeriesRepository seriesRepository) {
		this.seriesRepository = seriesRepository;
	}
	
	public long createSeries(Series series) {
		return seriesRepository.save(series).getId();
	}
	
	public Optional<Series> getSeries(long id) {
		return seriesRepository.findById(id);
	}
	
	public List<Series> getSeriesByName(String name) {
		return seriesRepository.findByName(name);
	}
	
	public Series updateSeries(Series series) {
		return seriesRepository.save(series);
	}
	
	public void deleteSeries(Series series) {
		seriesRepository.delete(series);
	}
	
	public Optional<Series> getSeriesFromVolume(Volume volume) {
		long seriesId = volume.getSeries().getId();
		return seriesRepository.findById(seriesId);
	}
}
