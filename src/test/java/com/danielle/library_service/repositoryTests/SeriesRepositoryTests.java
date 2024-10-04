package com.danielle.library_service.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.danielle.library_service.TestHelper;
import com.danielle.library_service.entities.Series;
import com.danielle.library_service.repositories.SeriesRepository;

@DataJpaTest
public class SeriesRepositoryTests {
	@Autowired
	private SeriesRepository seriesRepository;
	
	TestHelper helper = new TestHelper();
	
	@AfterEach
	public void cleanup() {
		seriesRepository.deleteAll();
	}
	
	@Test
	public void testFindByName() {
		Series series = helper.createSeries();
		seriesRepository.save(series);
		
		List<Series> response = seriesRepository.findByName(series.getName());
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), series.getId());
	}

}
