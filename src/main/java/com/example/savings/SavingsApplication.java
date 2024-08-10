package com.example.savings;

import com.example.savings.entities.Savingstable;
import com.example.savings.repositories.SavingsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SavingsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SavingsApplication.class, args);

	}
	@Bean
	CommandLineRunner commandLineRunner(SavingsRepository savingsRepository) {
		return args -> {
			savingsRepository.save(new Savingstable(null, "Alice", "C12345", 5000.0, 5, "Savings De-luxe"));
			savingsRepository.save(new Savingstable(null, "Bob", "C67890", 15000.0, 10, "Fixed"));
			savingsRepository.save(new Savingstable(null, "Charlie", "C54321", 3000.0, 2, "Savings De-luxe"));
			savingsRepository.save(new Savingstable(null, "Daisy", "C98765", 25000.0, 15, "Savings De-luxe"));
			savingsRepository.save(new Savingstable(null, "Eve", "C13579", 7000.0, 7, "Education"));
			savingsRepository.save(new Savingstable(null, "Alice", "C12345", 5000.0, 5, "Regular"));
			savingsRepository.save(new Savingstable(null, "Bob", "C67890", 15000.0, 10, "Savings De-luxe"));
			savingsRepository.save(new Savingstable(null, "Charlie", "C54321", 3000.0, 2, "High Yield"));
			savingsRepository.save(new Savingstable(null, "Daisy", "C98765", 25000.0, 15, "Savings De-luxe"));
			savingsRepository.save(new Savingstable(null, "Eve", "C13579", 7000.0, 7, "Education"));

        };
    }
}
