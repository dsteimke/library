package com.danielle.library_service.serviceTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.danielle.library_service.TestHelper;
import com.danielle.library_service.entities.Series;
import com.danielle.library_service.entities.Volume;
import com.danielle.library_service.repositories.SeriesRepository;
import com.danielle.library_service.repositories.VolumeRepository;
import com.danielle.library_service.services.VolumeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class VolumeServiceTests {
	
	@Mock
	VolumeRepository volumeRepository;
	
	@Mock
	SeriesRepository seriesRepository;
	
	@InjectMocks
	private VolumeService volumeService = new VolumeService(volumeRepository, seriesRepository);
	
	TestHelper helper = new TestHelper();
	
	@Test
	public void testCreateVolume() {
		Volume volume = helper.createVolume();
		
		Mockito.when(volumeRepository.save(volume)).thenReturn(volume);
		
		long response = volumeService.createVolume(volume);
		
		assertEquals(response, volume.getId());
	}

	@Test
	public void testUpdateVolume() {
		Volume volume = helper.createVolume();
		
		Mockito.when(volumeRepository.save(volume)).thenReturn(volume);
		
		Volume response = volumeService.updateVolume(volume);
		
		assertEquals(response.getId(), volume.getId());
	}
	
	@Test
	public void testGetVolume() {
		Volume volume = helper.createVolume();
		
		Mockito.when(volumeRepository.findById(volume.getId())).thenReturn(Optional.of(volume));
		
		Optional<Volume> response = volumeService.getVolume(volume.getId());
		
		assertTrue(response.isPresent());
		assertEquals(response.get().getId(), volume.getId());
	}
	
	@Test
	public void testDeleteVolume() {
		Volume volume = helper.createVolume();
		
		volumeService.deleteVolume(volume);
		
		Mockito.verify(volumeRepository, times(1)).delete(volume);
	}
	
	@Test
	public void testGetVolumeByWorkId() {
		Volume volume = helper.createVolume();
		
		Mockito.when(volumeRepository.findById(volume.getId())).thenReturn(Optional.of(volume));
		
		Optional<Volume> response = volumeService.getVolume(volume.getId());
		
		assertTrue(response.isPresent());
		assertEquals(response.get().getId(), volume.getId());
	}
	
	@Test
	public void testGetSeriesFromVolume() {
		Volume volume = helper.createVolume();
		Series series = volume.getSeries();
		
		Mockito.when(seriesRepository.findById(series.getId())).thenReturn(Optional.of(series));
		
		Optional<Series> response = volumeService.getSeriesFromVolume(volume);
		
		assertTrue(response.isPresent());
		assertEquals(response.get().getId(), series.getId());
	}
	
	@Test
	public void getAllVolumesInSeries () {
		Volume volume = helper.createVolume();
		Series series = volume.getSeries();
		List<Volume> volumes = new ArrayList<>();
		volumes.add(volume);
		
		Mockito.when(volumeRepository.findBySeriesId(series.getId())).thenReturn(volumes);
		
		List<Volume> response = volumeService.getAllVolumesInSeries(series);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), volume.getId());
	}

}
