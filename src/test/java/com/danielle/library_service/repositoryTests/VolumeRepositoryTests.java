package com.danielle.library_service.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.danielle.library_service.TestHelper;
import com.danielle.library_service.entities.Volume;
import com.danielle.library_service.repositories.VolumeRepository;

@DataJpaTest
public class VolumeRepositoryTests {
	@Autowired
	private VolumeRepository volumeRepository;
	
	TestHelper helper = new TestHelper();
	
	@AfterEach
	public void cleanup() {
		volumeRepository.deleteAll();
	}
	
	@Test
	public void testFindByWorkId() {
		Volume volume = helper.createVolume();
		volumeRepository.save(volume);
		
		Optional<Volume> response = volumeRepository.findByWorkId(volume.getWork().getId());
		
		assertTrue(response.isPresent());
		assertEquals(response.get().getId(), volume.getId());
	}
	
	@Test
	public void testFindBySeriesId() {
		Volume volume = helper.createVolume();
		volumeRepository.save(volume);
		
		List<Volume> response = volumeRepository.findBySeriesId(volume.getSeries().getId());
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), volume.getId());
	}
	

}
