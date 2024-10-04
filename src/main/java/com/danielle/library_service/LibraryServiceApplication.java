package com.danielle.library_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.danielle.library_service.Work.WorkType;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class LibraryServiceApplication {
	
	private static final Logger log = LoggerFactory.getLogger(LibraryServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LibraryServiceApplication.class, args);
	}
	
//	@Bean
//	public CommandLineRunner demo(WorksRepository worksRepository) {
//		return (args) -> {
//			ArrayList<String> authors1 = new ArrayList<String>(Arrays.asList("author1", "author2"));
//			worksRepository.save(new Work("title1", WorkType.CODEX, authors1, LocalDate.parse("2017-08-11"), true));
//			
//			ArrayList<String> authors2 = new ArrayList<String>(Arrays.asList("author3"));
//			worksRepository.save(new Work("title2", WorkType.SCROLL, authors2, LocalDate.parse("1985-12-11"), false));
//			
//			ArrayList<String> authors3 = new ArrayList<String>(Arrays.asList("author2", "author4"));
//			worksRepository.save(new Work("title3", WorkType.TABLET, authors3, LocalDate.parse("1872-04-28"), true));
//			
//			worksRepository.findAll().forEach(work -> {
//				log.info(work.toString());
//			});
//		};
//	}

}
