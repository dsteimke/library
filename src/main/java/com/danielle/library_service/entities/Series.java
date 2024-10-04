package com.danielle.library_service.entities;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "series")
public class Series {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String name;
	
	// Using cascadeType.ALL because it makes sense (to me) that if you wanted to delete
	// a series you'd also want to delete all the volumes associated with that series.
	@OneToMany(mappedBy = "series", cascade = CascadeType.ALL)
	private Set<Volume> volumes;
	
	public Series(String name) {
		this.name = name;
	}
	
	public Series(String name, Set<Volume> volumes) {
		this.name = name;
		this.volumes = volumes;
	}
	
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Volume> getVolumes() {
		return volumes;
	}
	
	public void setVolume(Set<Volume> getVolumes) {
		
	}

}
