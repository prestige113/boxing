package ru.aldar.bank.boxing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.aldar.bank.boxing.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}