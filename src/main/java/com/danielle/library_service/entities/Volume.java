package com.danielle.library_service.entities;

import jakarta.persistence.CascadeType;
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
@Table(name = "volume", uniqueConstraints = { @UniqueConstraint(columnNames = { "series_id", "work_id" }) })
public class Volume {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "series_id", nullable = false)
	private Series series;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "work_id", nullable = false)
	private Work work;
	
	private int volumeNumber;
	
	public Volume(Series series, Work work, int volumeNumber) {
		this.series = series;
		this.work = work;
		this.volumeNumber = volumeNumber;
	}
	
	public long getId() {
		return id;
	}
	
	public Series getSeries() {
		return series;
	}
	
	public void setSeries(Series series) {
		this.series = series;
	}
	
	public Work getWork() {
		return work;
	}
	
	public void setWork(Work work) {
		this.work = work;
	}

	public int getVolumeNumber() {
		return volumeNumber;
	}

	public void setVolumeNumber(int volumeNumber) {
		this.volumeNumber = volumeNumber;
	}

}
