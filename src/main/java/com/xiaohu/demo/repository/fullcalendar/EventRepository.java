package com.xiaohu.demo.repository.fullcalendar;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xiaohu.demo.entity.fullcalendar.Event;

public interface EventRepository extends JpaRepository<Event,String>{

}
