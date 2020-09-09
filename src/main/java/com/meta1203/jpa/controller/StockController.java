package com.meta1203.jpa.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.time.Instant;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.meta1203.jpa.model.Stock;
import com.meta1203.jpa.model.StockValue;
import com.meta1203.jpa.repository.StockRepository;
import com.meta1203.jpa.repository.StockValueRepository;

/*
 * Basic CRUD REST controller.
 * If you don't quite grasp how Spring's REST controllers work yet, don't worry. Just look at these as basic
 * functions for Creating, Reading, Updating, and Deleting (CRUD, see?) like you would use in your own application.
 */
@RestController
public class StockController {
	// inject the repositories for use here. Spring handles it all for you.
	@Autowired
	private StockRepository stockRepository;
	@Autowired
	private StockValueRepository valueRepository;
	
	@RequestMapping(value="/stock", method=POST)
	public @ResponseBody Stock createNewStock(@RequestParam String symbol, @RequestParam String name) {
		// create a new Stock instance
		Stock stock = new Stock();
		stock.setName(name);
		stock.setSymbol(symbol);
		// save the new Stock to the DB and get the handled entity
		// NOTE: handled entities will automatically save changes made to the object back to the DB!
		stock = stockRepository.save(stock);
		// return the freshly created Stock object
		return stock;
	}
	
	@RequestMapping(value="/stock/{symbol}", method=GET)
	public @ResponseBody Stock getStockForSymbol(@PathVariable String symbol, HttpServletResponse response) {
		// use the custom method in StockRepository.java to get a Stock entity by its field "symbol"  
		Stock stock = stockRepository.findBySymbol(symbol);
		// return 404 if we didn't find anything, 200 otherwise
		response.setStatus(stock == null ? 404 : 200);
		return stock;
	}
	
	@RequestMapping(value="/stock/{symbol}", method=PUT)
	public @ResponseBody Stock updateValue(@PathVariable String symbol, @RequestParam long value, @RequestParam long marketCap,
			HttpServletResponse response) {
		// use the custom method in StockRepository.java to get a Stock entity by its field "symbol"
		Stock stock = stockRepository.findBySymbol(symbol);
		// create a new StockValue instance
		StockValue stockValue = new StockValue();
		stockValue.setMarketCap(marketCap);
		stockValue.setStock(stock); // we set its owner for JPA to manage the relationship
		stockValue.setTimestamp(Instant.now());
		stockValue.setValue(value);
		// save the new StockValue to the DB and get the handled entity
		stockValue = valueRepository.save(stockValue);
		// as JPA automatically handles relationship data, even though we looked up the Stock entity before saving the StockValue,
		// the Stock entity will have the new StockValue added without any additional work on our side.
		return stock;
	}
	
	@RequestMapping(value="/stock/{symbol}", method=DELETE)
	public void removeStock(@PathVariable String symbol, HttpServletResponse response) {
		// use the custom method in StockRepository.java to get a Stock entity by its field "symbol"
		Stock stock = stockRepository.findBySymbol(symbol);
		// delete the given entity from the DB
		stockRepository.delete(stock);
		return;
	}
}
