package com.example.savings.repositories;

import com.example.savings.entities.Savingstable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavingsRepository extends JpaRepository<Savingstable,Long> {
    List<Savingstable> findByCustNo(String custNo);
    Optional<Savingstable> findById(Long id);
    @Override
    void deleteById(Long id);
}
