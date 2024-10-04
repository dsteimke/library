package com.danielle.library_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "forwards", uniqueConstraints = { @UniqueConstraint(columnNames = { "author_id", "work_id" }) })
public class Foreword {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "work_id", nullable = false)
	private Work work;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", nullable = false)
	private Author author;
	
	@Lob
	@Column(nullable = false, columnDefinition="CLOB") // CLOB = Character Large Object - large text data
	private String forward;
	
	public Foreword(Work work, Author author, String forward) {
		this.work = work;
		this.author = author;
		this.forward = forward;
	}
	
	public long getId() {
		return id;
	}
	
	public void setWork(Work work) {
		this.work = work;
	}
	
	public Work getWork() {
		return work;
	}
	
	public void setAuthor(Author author) {
		this.author = author;
	}
	
	public Author getAuthor() {
		return author;
	}
	
	public void setForward(String forward) {
		this.forward = forward;
	}
	
	public String getForward() {
		return forward;
	}
}
