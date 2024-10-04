package com.danielle.library_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "author_work_mapping", uniqueConstraints = { @UniqueConstraint(columnNames = { "author_id", "work_id" }) })
public class Authorship {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id")
	private Author author;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "work_id")
	private Work work;
	
	public Authorship(Work work, Author author) {
		this.work = work;
		this.author = author;
	}
	
	public long getId() {
		return id;
	}
	
	public Author getAuthor() {
		return author;
	}
	
	public void setAuthor(Author author) {
		this.author = author;
	}
	
	public Work getWork() {
		return work;
	}
	
	public void setWork(Work work) {
		this.work = work;
	}

}
