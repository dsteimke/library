package com.danielle.library_service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.danielle.library_service.entities.Author;
import com.danielle.library_service.entities.Citation;
import com.danielle.library_service.entities.Foreword;
import com.danielle.library_service.entities.Library;
import com.danielle.library_service.entities.Loan;
import com.danielle.library_service.entities.Series;
import com.danielle.library_service.entities.Volume;
import com.danielle.library_service.entities.Work;
import com.danielle.library_service.entities.enums.LoanStatus;
import com.danielle.library_service.entities.enums.WorkType;

public class TestHelper {
	
	Random random = new Random();
	
	private List<String> firstNames;
	private List<String> lastNames;
	private List<String> workTitles;
	
	public TestHelper() {
		firstNames = new ArrayList<>();
		firstNames.add("Lucas");
		firstNames.add("Rachel");
		firstNames.add("Ava");
		
		lastNames = new ArrayList<>();
		lastNames.add("Thompson");
		lastNames.add("Roberts");
		lastNames.add("Mason");
		
		//don't judge I used chatGPT to generate these
		workTitles = new ArrayList<>();
		workTitles.add("Whispers from the Forgotten Sky");
		workTitles.add("The Alchemist's Last Recipe");
		workTitles.add("Echoes in the Labyrinth of Time");
	}
	
	public Work createWork() {
		String title = workTitles.get(random.nextInt(3));
		return new Work(title, WorkType.CODEX, getRandomDate(), false);
	}
	
	public Work createRareWork() {
		String title = workTitles.get(random.nextInt(3));
		return new Work(title, WorkType.CODEX, getRandomDate(), true);
	}
	
	public Work createCodex() {
		String title = workTitles.get(random.nextInt(3));
		return new Work(title, WorkType.CODEX, getRandomDate(), false);
	}
	
	public Work createTablet() {
		String title = workTitles.get(random.nextInt(3));
		return new Work(title, WorkType.TABLET, getRandomDate(), false);
	}
	
	public Work createScroll() {
		String title = workTitles.get(random.nextInt(3));
		return new Work(title, WorkType.SCROLL, getRandomDate(), false);
	}
	
	public Author createAuthor() {
		String name = firstNames.get(random.nextInt(3)) + " " + lastNames.get(random.nextInt(3));
		return new Author(name);
	}
	
	public Foreword createForeword() {
		Author author = createAuthor();
		Work work = createWork();
		return new Foreword(work, author, "this is a lot of text");
	}
	
	public Volume createVolume() {
		Series series = createSeries();
		Work work = createWork();
		return new Volume(series, work, 2);
	}
	
	public Series createSeries() {
		return new Series("random series name");
	}
	
	public Citation createCitation() {
		Work workCited = createWork();
		Work citedBy = createWork();
		System.out.println(workCited);
		System.out.println(citedBy);
		int pageNum = random.nextInt(1000);
		long accessTime = getTimestamp();
		return new Citation(workCited, citedBy, pageNum, accessTime);
	}
	
	public Library createLibrary() {
		return new Library("another library in system");
	}
	
	public Loan createReturnedLoan() {
		Work work = createWork();
		Library library = createLibrary();
		LocalDate sentOutDate = getRandomDate();
		LocalDate returnedDate = getRandomDate();
		LoanStatus loanStatus = LoanStatus.RETURNED;
		return new Loan(work, library, sentOutDate, returnedDate, loanStatus);
	}
	
	public Loan createLoanedLoan() {
		Work work = createWork();
		Library library = createLibrary();
		LocalDate sentOutDate = getRandomDate();
		LocalDate returnedDate = null;
		LoanStatus loanStatus = LoanStatus.LOANED;
		return new Loan(work, library, sentOutDate, returnedDate, loanStatus);
	}	
	
	private long getTimestamp() {
		Date now = new Date();
		return now.getTime();
	}
	
	public LocalDate getRandomDate() {
		int day = random.nextInt(28) + 1;
		int month = random.nextInt(12) + 1;
		int year = random.nextInt(2024) + 1;
		return LocalDate.of(year, month, day);
	}
}
