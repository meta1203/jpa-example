package com.meta1203.jpa.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.meta1203.jpa.model.Stock;

/*
 * An auto-magically managed interface controlled by Spring for accessing entities stored in the database.
 * Make one of these for any entity you have to store.
 */

@Transactional // makes operations performed with this repository transactional. you most likely want this
@Component // make Spring aware it exists
public interface StockRepository extends JpaRepository<Stock, Long> {
	/*
	 * These interfaces are super cool, as most of the time you'll never have to write any SQL/JPQL.
	 * The example given here finds a single Stock entity by its 'symbol' field.
	 * You can add similar ones like this to get single or collections of entities, find by mulitple fields, etc.
	 * For more info on what you can do with this, check 
	 * https://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#jpa.query-methods.query-creation
	 */
	public Stock findBySymbol(String symbol);
}
