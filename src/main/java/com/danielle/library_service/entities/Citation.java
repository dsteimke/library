package com.danielle.library_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "citations", uniqueConstraints = { @UniqueConstraint(columnNames = { "work_cited", "cited_in" }) })
public class Citation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_cited", nullable = false)
    private Work workCited;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cited_in", nullable = false)
    private Work citedIn;
	
	private int pageNumber;
	
	// Using Date instead of LocalDateTime because it seems like access time would want an instant, not a relative date/time
    @Temporal(TemporalType.TIMESTAMP)
	private long accessTimeUTC;
    
    public Citation(Work workCited, Work citedIn, int pageNumber, long accessTimeUTC) {
    	this.workCited = workCited;
    	this.citedIn = citedIn;
    	this.pageNumber = pageNumber;
    	this.accessTimeUTC = accessTimeUTC;
    }
    
    public long getId() {
    	return id;
    }

	public Work getWorkCited() {
		return workCited;
	}

	public void setWorkCited(Work workCited) {
		this.workCited = workCited;
	}

	public Work getCitedIn() {
		return citedIn;
	}

	public void setCitedIn(Work citedIn) {
		this.citedIn = citedIn;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public long getAccessTime() {
		return accessTimeUTC;
	}

	public void setAccessTime(long accessTimeUTC) {
		this.accessTimeUTC = accessTimeUTC;
	}

}
