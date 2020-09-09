package com.meta1203.jpa;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/*
 * The main class and insertion point for Spring.
 * I use it to register the DataSource bean as well, but that could be moved elsewhere
 * if you have a better spot for beans.
 */
@SpringBootApplication
public class JpaApplication {
	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}
	
	/*
	 * Unless you're 100% SURE that code won't hit production, you should always use a connection pooler.
	 * This registers a DataSource bean using the common HikariCP connection pool.
	 * You can change it to a connection pooler of your choice if need be, just make sure it returns something
	 * that implements javax.sql.DataSource.
	 */
	private HikariConfig config = new HikariConfig();
	@Bean
	public DataSource defaultDataSource() {
		// set the data source's JDBC URL.
		// I'm using H2's in-memory DB for demonstration purposes 
		config.setJdbcUrl("jdbc:h2:mem:testdb");
		
		// set the username and password for the DB. set this how you want, just don't hard-code it in plain-text
		// H2's in-memory DB doesn't require a username or password, but they're commented here for the sake of completeness
		//config.setUsername(System.getenv("DB_USERNAME"));
		//config.setPassword(System.getenv("DB_PASSWORD"));

		// enforce transactionality
		config.setAutoCommit(false);

		// some basic connection pooling stuff. tweak as needed
		config.setMinimumIdle(5);
		config.setMaximumPoolSize(15);
		
		// basic test query to see if the connection is alive. SELECT 1 should work for most stuff, but you might need to change it.
		config.setConnectionTestQuery("SELECT 1");

		return new HikariDataSource(config);
	}
}
