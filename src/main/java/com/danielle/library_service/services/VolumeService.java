package com.danielle.library_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.danielle.library_service.entities.Series;
import com.danielle.library_service.entities.Volume;
import com.danielle.library_service.repositories.SeriesRepository;
import com.danielle.library_service.repositories.VolumeRepository;

public class VolumeService {
	VolumeRepository volumeRepository;
	SeriesRepository seriesRepository;
	
	@Autowired
	public VolumeService(VolumeRepository volumeRepository, SeriesRepository seriesRepository) {
		this.volumeRepository = volumeRepository;
		this.seriesRepository = seriesRepository;
	}
	
	public long createVolume(Volume volume) {
		return volumeRepository.save(volume).getId();
	}
	
	public Volume updateVolume(Volume volume) {
		return volumeRepository.save(volume);
	}
	
	public Optional<Volume> getVolume(long volumeId) {
		return volumeRepository.findById(volumeId);
	}
	
	public void deleteVolume(Volume volume) {
		volumeRepository.delete(volume);
	}
	
	public Optional<Volume> getVolumeByWorkId(long workId) {
		return volumeRepository.findByWorkId(workId);
	}
	
	public Optional<Series> getSeriesFromVolume(Volume volume) {
		long seriesId = volume.getSeries().getId();
		return seriesRepository.findById(seriesId);
	}
	
	public List<Volume> getAllVolumesInSeries(Series series) {
		long seriesId = series.getId();
		return volumeRepository.findBySeriesId(seriesId);
	}
	
	
}
