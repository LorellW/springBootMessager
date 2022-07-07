package com.github.lorellw.letscode.repositories;

import com.github.lorellw.letscode.entiites.Message;
import com.github.lorellw.letscode.entiites.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;


@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {
    Page<Message> findByTag(String tag, Pageable pageable);

    Page<Message> findAll(Pageable pageable);

    @Query("from Message as m where m.author =:author ")
    Page<Message> findByUser(Pageable pageable,@Param("author") User author);
}
