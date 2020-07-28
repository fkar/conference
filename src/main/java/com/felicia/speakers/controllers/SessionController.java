package com.felicia.speakers.controllers;

import com.felicia.speakers.models.Session;
import com.felicia.speakers.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping
    public List<Session> list() {
        return sessionRepository.findAll();

    }
    @GetMapping
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id){
        return sessionRepository.getOne(id);
    }

    @PostMapping
    public Session create(@RequestBody final Session session) {
        return sessionRepository.saveAndFlush(session);
    }
//this will just delete session reposotories without any children records therefore I have to doit
    @RequestMapping(value="{id}", method= RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
//Also need to check for the childre records before deleting
        sessionRepository.existsById(id);
    }
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    //because this is Put, we expect all attributes to be passed in. A PATCH would only allowed a portion of attributes to be uppdated
    //TODO: Add validation that all attributes are passed in, otherwise return a 400 bad payload
    public Session update(@PathVariable Long id, @RequestBody Session session){ //update all attributes in the RequestBody for that session and replace them all in the database
        Session existingSession = sessionRepository.getOne(id);  //finns det en session då tar vi den
        BeanUtils.copyProperties(session, existingSession, "session_id"); //taking an existing session and copy incoming session data on to it.
        // Ignor session_id becaus it's a primary key and we do not want to replace it
        return  sessionRepository.saveAndFlush(existingSession);
    }

}
