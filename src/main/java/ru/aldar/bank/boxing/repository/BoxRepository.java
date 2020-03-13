package ru.aldar.bank.boxing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.aldar.bank.boxing.domain.Box;
public interface BoxRepository extends JpaRepository<Box, Integer> {
}

