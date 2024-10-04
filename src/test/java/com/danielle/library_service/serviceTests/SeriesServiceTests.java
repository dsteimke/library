package com.danielle.library_service.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.danielle.library_service.TestHelper;
import com.danielle.library_service.entities.Series;
import com.danielle.library_service.repositories.SeriesRepository;
import com.danielle.library_service.services.SeriesService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SeriesServiceTests {
	@Mock
	private SeriesRepository seriesRepository;
	
	@InjectMocks
	private SeriesService seriesService = new SeriesService(seriesRepository);
	
	TestHelper helper = new TestHelper();
	
	Series series;
	
	@BeforeEach
	public void setup() {
		series = helper.createSeries();
	}
	
	@Test
	public void testCreateSeries() {
		Mockito.when(seriesRepository.save(series)).thenReturn(series);
		
		long response = seriesService.createSeries(series);
		
		assertEquals(response, series.getId());
	}
	
	@Test
	public void testGetSeries() {
		long seriesId = series.getId();
		
		Mockito.when(seriesRepository.findById(seriesId)).thenReturn(Optional.of(series));
		
		Optional<Series> response = seriesService.getSeries(seriesId);
		
		assertTrue(response.isPresent());
		assertEquals(response.get().getId(), seriesId);
	}
	
	@Test
	public void testGetSeriesByName() {
		// Moose problem: plural of series is series
		List<Series> serieses = new ArrayList<>();
		serieses.add(series);
		String seriesName = series.getName();
		
		Mockito.when(seriesRepository.findByName(seriesName)).thenReturn(serieses);
		
		List<Series> response = seriesService.getSeriesByName(seriesName);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), series.getId());
	}
	
	@Test
	public void testUpdateSeries() {
		Mockito.when(seriesRepository.save(series)).thenReturn(series);
		
		Series response = seriesService.updateSeries(series);
		
		assertEquals(response.getId(), series.getId());
	}
	
	@Test
	public void testDeleteSeries() {
		seriesService.deleteSeries(series);
		
		Mockito.verify(seriesRepository, times(1)).delete(series);
	}

}
