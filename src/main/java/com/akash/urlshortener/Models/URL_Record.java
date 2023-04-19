package com.akash.urlshortener.Models;
import jakarta.persistence.*;

@Entity
public class URL_Record {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "full_url", nullable = false, unique = true)
	private String full_url;
	@Column
	private String short_url;

	public String getFull_url() {
		return full_url;
	}

	public void setFull_url(String full_url) {
		this.full_url = full_url;
	}

	public String getShort_url() {
		return short_url;
	}

	public void setShort_url(String short_url) {
		this.short_url = short_url;
	}

}