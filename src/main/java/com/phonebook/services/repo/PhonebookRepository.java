package com.phonebook.services.repo;

import java.util.Optional;

import org.hibernate.jpa.TypedParameterValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.phonebook.services.model.Mst_Phonebook;

@Repository
public interface PhonebookRepository extends JpaRepository<Mst_Phonebook, Long> {

    @Query(value = "select nextval('mst_phonebook_seq')", nativeQuery = true)
    public Long subjectNextVal();

    @Query(value = "SELECT e FROM Mst_Phonebook e WHERE e.id = ?1 AND e.active_flg = 1")
    public Mst_Phonebook findByIdSubject(Long id);

    // @Query("SELECT p FROM Mst_Phonebook p WHERE p.number LIKE %:substring%")
    // Page<Mst_Phonebook> findByNumberContaining(@Param("substring") String substring, PageRequest pageRequest);

    @Query(value ="SELECT p FROM Mst_Phonebook p WHERE p.number LIKE ?1 AND p.delete_flag = 0 and p.active_flg = 1")
    Page<Mst_Phonebook> searchWithParam(String number, PageRequest pageRequest);

    @Query(value ="SELECT p FROM Mst_Phonebook p WHERE p.delete_flag = 0 AND p.active_flg = 1")
    Page<Mst_Phonebook> search(PageRequest pageRequest);

    // @Query(value = "SELECT * FROM mst_phonebook WHERE " +
    //                "(COALESCE(:name, '') = '' OR name ILIKE %:name%) AND " +
    //                "(COALESCE(:number, '') = '' OR number ILIKE %:number%)", nativeQuery = true)
    // Page<Mst_Phonebook> search(@Param("name") String name,
    //                            @Param("number") String number,
    //                            PageRequest pageable);
}
