package com.danielle.library_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.danielle.library_service.entities.Foreword;
import com.danielle.library_service.repositories.ForewordRepository;

public class ForewordService {
	ForewordRepository forewordRepository;
	
	@Autowired
	public ForewordService(ForewordRepository forewordRepository) {
		this.forewordRepository = forewordRepository;
	}
	
	public long createForeword(Foreword forward) {
		return forewordRepository.save(forward).getId();
	}
	
	public Optional<Foreword> getForewordById(long id) {
		return forewordRepository.findById(id);
	}
	
	public Foreword updateForeword(Foreword forward) {
		return forewordRepository.save(forward);
	}
	
	public void deleteForeword(Foreword forward) {
		forewordRepository.delete(forward);
	}
	
	public List<Foreword> getAllForewordsByAuthorId(long authorId) {
		return forewordRepository.findByAuthorId(authorId);
	}
	
	public List<Foreword> getAllForewordsByWorkId(long workId) {
		return forewordRepository.findByWorkId(workId);
	}

}
