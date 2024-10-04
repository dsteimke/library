package com.danielle.library_service.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.danielle.library_service.TestHelper;
import com.danielle.library_service.entities.Foreword;
import com.danielle.library_service.repositories.ForewordRepository;
import com.danielle.library_service.services.ForewordService;

@ExtendWith(MockitoExtension.class)
public class ForewardServiceTests {
	@Mock
	private ForewordRepository forewordRepository;
	
	@InjectMocks
	private ForewordService forewordService = new ForewordService(forewordRepository);
	
	TestHelper helper = new TestHelper();
	
	Foreword foreword;
	
	@BeforeEach
	public void setup() {
		foreword = helper.createForeword();
	}
	
	@Test
	public void testCreateForeward() {
		Mockito.when(forewordRepository.save(foreword)).thenReturn(foreword);
		
		long savedId = forewordService.createForeword(foreword);
		
		assertEquals(savedId, foreword.getId());
	}
	
	@Test
	public void testGetForewardById() {
		Mockito.when(forewordRepository.findById(foreword.getId())).thenReturn(Optional.of(foreword));
		
		Optional<Foreword> response = forewordService.getForewordById(foreword.getId());
		
		assertTrue(response.isPresent());
		assertEquals(response.get().getId(), foreword.getId());
	}
	
	@Test
	public void testUpdateForeward() {
		Mockito.when(forewordRepository.save(foreword)).thenReturn(foreword);
		
		Foreword response = forewordService.updateForeword(foreword);
		
		assertEquals(response.getId(), foreword.getId());
	}
	
	@Test
	public void testDeleteForeward() {
		forewordService.deleteForeword(foreword);
		
		Mockito.verify(forewordRepository, times(1)).delete(foreword);
	}
	
	@Test
	public void testGetAllForewardsByAuthorId() {
		long id = foreword.getAuthor().getId();
		List<Foreword> forewords = new ArrayList<>();
		forewords.add(foreword);
		
		Mockito.when(forewordRepository.findByAuthorId(id)).thenReturn(forewords);
		
		List<Foreword> response = forewordService.getAllForewordsByAuthorId(id);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), foreword.getId());
	}
	
	@Test
	public void testGetAllForewardsByWorkId() {
		long id = foreword.getWork().getId();
		
		List<Foreword> forewords = new ArrayList<>();
		forewords.add(foreword);
		
		Mockito.when(forewordRepository.findByWorkId(id)).thenReturn(forewords);
		
		List<Foreword> response = forewordService.getAllForewordsByWorkId(id);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getId(), foreword.getId());
	}
	

}
