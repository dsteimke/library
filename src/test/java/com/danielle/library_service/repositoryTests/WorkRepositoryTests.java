package com.danielle.library_service.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.danielle.library_service.TestHelper;
import com.danielle.library_service.entities.Work;
import com.danielle.library_service.entities.enums.WorkType;
import com.danielle.library_service.repositories.WorkRepository;

@DataJpaTest
public class WorkRepositoryTests {
	@Autowired
	private WorkRepository workRepository;
	
	private TestHelper helper = new TestHelper();
	
    @AfterEach
    public void cleanup() {
    	workRepository.deleteAll();
    }
    
    @Test
    public void testSave() {
		Work testWork = workRepository.save(helper.createWork());
		
		Optional<Work> savedWork = workRepository.findById(testWork.getId());
		
		assertTrue(savedWork.isPresent());
    }
	
	@Test
	public void testFindByTitle() {
		Work work = helper.createWork();
		Work testWork = workRepository.save(work);
		
		Work savedWork = workRepository.findByTitle(work.getTitle()).get(0);
		
		assertEquals(testWork, savedWork);
	}
	
	@Test
	public void findRareWorks() {
		workRepository.save(helper.createRareWork());
		workRepository.save(helper.createWork());
		
		List<Work> result = workRepository.getRareWorks();
		
		assertEquals(result.size(), 1);
		assertTrue(result.get(0).isRare());
	}
	
	@Test
	public void findCommonWorks() {
		workRepository.save(helper.createRareWork());
		workRepository.save(helper.createWork());
		
		List<Work> result = workRepository.getCommonWorks();
		
		assertEquals(result.size(), 1);
		assertFalse(result.get(0).isRare());
	}
	
	@Test
	public void findCodexes() {
		workRepository.save(helper.createCodex());
		workRepository.save(helper.createScroll());
		workRepository.save(helper.createTablet());
		
		List<Work> result = workRepository.findByWorkType(WorkType.CODEX);
		
		assertEquals(result.size(), 1);
		assertEquals(result.get(0).getWorkType(), WorkType.CODEX);
	}
	
	@Test
	public void findCodexes_noCodexes() {
		workRepository.save(helper.createScroll());
		workRepository.save(helper.createTablet());
		
		List<Work> result = workRepository.findByWorkType(WorkType.CODEX);
		
		assertEquals(result.size(), 0);
	}
	
}
