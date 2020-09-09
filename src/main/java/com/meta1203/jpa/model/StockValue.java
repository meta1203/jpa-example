package com.meta1203.jpa.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/*
 * An entity that exists in a ManyToOne relation to the Stock entity.
 * Check Stock.java for info on annotations I don't describe here.
 */

@Entity
@Table(name="value_history") // defines the table this entity should be stored in.
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class StockValue {
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne // the other side of the @OneToMany annotation found in Stock.java
	private Stock stock;
	
	@Column(columnDefinition = "TIMESTAMP") // Clarifies that this field should be stored as a SQL TIMESTAMP
	private Instant timestamp;
	
	// for most primitives and Strings, JPA is able to handle them without any special annotations
	private long value;
	private long marketCap;
	
	/* getters and setters */
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Stock getStock() {
		return stock;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}
	public long getMarketCap() {
		return marketCap;
	}
	public void setMarketCap(long marketCap) {
		this.marketCap = marketCap;
	}
	public Instant getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}
}
