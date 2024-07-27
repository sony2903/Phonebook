package com.phonebook.services.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phonebook.services.model.CustomPage;
import com.phonebook.services.model.Mst_Phonebook;
import com.phonebook.services.model.ResponseMdl;
import com.phonebook.services.model.ResponseMdlPagination;
import com.phonebook.services.service.PhonebookService;

import io.micrometer.core.ipc.http.HttpSender.Response;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/phonebook")
public class PhonebookController {

    @Autowired
    PhonebookService service;

    @RequestMapping("/")
    public String index() {
        log.trace("A TRACE Message");
        log.debug("A DEBUG Message");
        log.info("An INFO Message");
        log.warn("A WARN Message");
        log.error("An ERROR Message");

        return "Howdy! Check out the Logs to see the output...";
    }

    // Create
    @PostMapping("")
    public ResponseEntity<ResponseMdl> createSubject(@Valid @RequestBody Mst_Phonebook emp) {
    ResponseMdl response = new ResponseMdl();
    boolean isNew = emp.getId() == null;

    if (isNew) {
        emp.setId(service.subjectNextVal());
    }

    Mst_Phonebook result = service.save(emp);

    response.setMessage(isNew ? "Data " + result.getName() + " created" : "Data " + result.getName() + " edited");
    response.setData(result);
    response.setCode(isNew ? HttpStatus.CREATED.value() : HttpStatus.OK.value());

    return new ResponseEntity<>(response, isNew ? HttpStatus.CREATED : HttpStatus.OK);

    }

    @GetMapping("")
    public ResponseEntity<ResponseMdl> getByCode(@RequestParam Long code) {
        Mst_Phonebook data = service.get(code);
        ResponseMdl response = new ResponseMdl();
        if (data == null) {
            response.setCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Data not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.setData(data);
        response.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseMdlPagination> getAll(
            @RequestParam(defaultValue = "") String number,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ResponseMdlPagination response = new ResponseMdlPagination();
        if (!number.isEmpty() && !number.startsWith("62")) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Number should start with 62");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
        Page<Mst_Phonebook> data = service.SearchPagination(number, page, size);
        CustomPage<Mst_Phonebook> customPage = new CustomPage<>();
        customPage.setTotalPages(data.getTotalPages());
        customPage.setTotalElements(data.getTotalElements());
        customPage.setNumber(data.getNumber());
        customPage.setSize(data.getSize());
        customPage.setContent(data.getContent());
        response.setCode(200);
        response.setData(data);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("")
    public ResponseEntity<ResponseMdl> deleteByCode(@RequestParam Long code) {
        Mst_Phonebook data = service.softDelete(code);
        ResponseMdl response = new ResponseMdl();
        if(data == null){
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Bad Request");
            return ResponseEntity.badRequest().body(response);
        }
        response.setCode(HttpStatus.ACCEPTED.value());
        return ResponseEntity.accepted().body(response);
    }
    
}
