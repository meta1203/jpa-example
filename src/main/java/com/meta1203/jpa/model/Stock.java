package com.meta1203.jpa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/*
 * The main entity used by this example.
 * Normally I'd use Lombok's @Data annotation (https://projectlombok.org/features/Data) for creating POJOs,
 * but for the sake of simplicity I went ahead and just created getters and setters myself.
 * Check StockValue.java for some more commonly used annotations.
 */

@Entity // defines a POJO as an entity to be managed by JPA

//instructs the Jackson JSON library to prevent circular reference loops by replacing nested whole objects with their ID instead
@JsonIdentityInfo( 
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Stock {
	@Id // denotes this field is the ID value. Every entity must have one of these
	@GeneratedValue // JPA will automatically generate a value for this field when saved to the DB. Useful for IDs
	private long id;
	
	// describes a one to many relation with another entity
	// CascadeType.ALL means that when an entity is deleted, all related entities are deleted as well
	// mappedBy is the name of the field in the related entity (in this case, StockValue) that should point to this entity
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "stock")
	private Set<StockValue> valueHistory = new HashSet<StockValue>();

	@Size(max = 5) // this field can only be 5 characters long maximum
	@NotEmpty // this field cannot be null or zero length
	@Column(length = 5) // size of the column in the DB. NOTE: this annotation does not perform any constraint validations (yes, those other annotations are HIGHLY recommended)
	private String symbol;
	
	@NotEmpty
	private String name;
	
	/* getters and setters */
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Set<StockValue> getValueHistory() {
		return valueHistory;
	}
	public void setValueHistory(Set<StockValue> valueHistory) {
		this.valueHistory = valueHistory;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Stock [id=" + id + ", valueHistory=" + valueHistory + ", symbol=" + symbol + ", name=" + name + "]";
	}
}
