package com.phonebook.services.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.phonebook.services.model.Mst_Phonebook;
import com.phonebook.services.repo.PhonebookRepository;

@Service
public class PhonebookService {
	@Autowired
    PhonebookRepository repo;

    public Mst_Phonebook save(Mst_Phonebook req) {
        return repo.save(req);
    }

    public Mst_Phonebook get(Long Id) {
        return repo.findByIdSubject(Id);
    }

    public Mst_Phonebook softDelete(Long code){
        Mst_Phonebook data = repo.findById(code).orElse(null);
        if(data != null && data.getDelete_flag() == 0){
            data.setDelete_flag(1);
            data.setDelete_date(new Date());
            data.setActive_flg(0);
            repo.save(data);
            return data;
        } else {
            return null;
        }
    }

    public Long subjectNextVal(){
        return repo.subjectNextVal();
    }
    
    // public Page<Mst_Phonebook> Pagination(int page, int size) {
    //     PageRequest pageRequest = PageRequest.of(page, size);
    //     return repo.findAll(pageRequest);
    // }
    
    public Page<Mst_Phonebook> SearchPagination(String number, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return number.isEmpty() ? repo.search(pageRequest): repo.searchWithParam("+" + number, pageRequest);
    }

}
