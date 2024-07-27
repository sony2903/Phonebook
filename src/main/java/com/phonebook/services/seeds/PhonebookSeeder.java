package com.phonebook.services.seeds;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.phonebook.services.model.Mst_Phonebook;
import com.phonebook.services.repo.PhonebookRepository;
import com.phonebook.services.service.PhonebookService;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class PhonebookSeeder implements CommandLineRunner {

    @Autowired
    PhonebookRepository repo;
    PhonebookService service;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists to prevent duplicate seeding
        if (repo.count() == 0) {
            // Seed data
            Mst_Phonebook data1 = new Mst_Phonebook();
            data1.setId(repo.subjectNextVal());
            data1.setName("Bruno");
            data1.setNumber("+62856545465");
            
            Mst_Phonebook data2 = new Mst_Phonebook();
            data2.setId(repo.subjectNextVal());
            data2.setName("Walter");
            data2.setNumber("+62811122233");

            repo.save(data1);
            repo.save(data2);

            System.out.println("Seeding Phonebooks.");
        } else {
            System.out.println("Phonebooks already seeded.");
        }
    }
}
