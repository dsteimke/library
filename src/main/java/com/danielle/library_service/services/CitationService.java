package com.danielle.library_service.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.danielle.library_service.entities.Citation;
import com.danielle.library_service.repositories.CitationRepository;

public class CitationService {
	CitationRepository citationRepository;
	
	@Autowired
	public CitationService(CitationRepository citationRepository) {
		this.citationRepository = citationRepository;
	}
	
	public long createCitation(Citation citation) throws Exception {
		long work1Id = citation.getCitedIn().getId();
		long work2Id = citation.getWorkCited().getId();
		if(work1Id == work2Id) {
			throw new Exception("Work cannot cite itself");
		}
		return citationRepository.save(citation).getId();
	}
	
	public Optional<Citation> getCitationByCitationId(long id) {
		return citationRepository.findById(id);
	}
	
	public Citation updateCitation(Citation citation) {
		return citationRepository.save(citation);
	}
	
	public void deleteCitation(Citation citation) {
		citationRepository.delete(citation);
	}
	
}
