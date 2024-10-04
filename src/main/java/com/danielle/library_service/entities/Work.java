package com.danielle.library_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

import com.danielle.library_service.entities.enums.WorkType;

@Entity
@Table(name = "works")
public class Work {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private WorkType workType; 

	private LocalDate publicationDate;
	
	// Further enhancements; this could be an enum to encompass more rarity types. Maybe the library gets some ultra rare stuff
	private boolean isRare;
		
	public Work() {};
	
	public Work(String title, WorkType workType, LocalDate publicationDate, boolean isRare) {
		this.title = title;
		this.workType = workType;
		this.publicationDate = publicationDate;
		this.isRare = isRare;
	}
	
	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(LocalDate publicationDate) {
		this.publicationDate = publicationDate;
	}

	public boolean isRare() {
		return isRare;
	}

	public void setRare(boolean isRare) {
		this.isRare = isRare;
	}

	public WorkType getWorkType() {
		return workType;
	}

	public void setWorkType(WorkType workType) {
		this.workType = workType;
	}
	
	@Override
	public String toString() {
		return String.format("Id='%d', title='%s', publicationDate=%s, isRare='%s', workType='%s'",
				id, title, publicationDate.toString(), isRare, workType);
	}



}
