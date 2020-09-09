package com.meta1203.jpa.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.meta1203.jpa.model.StockValue;

@Transactional
@Component
public interface StockValueRepository extends JpaRepository<StockValue, Long> {
// if you don't need to do anything fancy, you can just use the default methods the interface provides.
}
