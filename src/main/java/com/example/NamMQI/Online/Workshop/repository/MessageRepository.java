package com.example.NamMQI.Online.Workshop.repository;

import com.example.NamMQI.Online.Workshop.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

}
