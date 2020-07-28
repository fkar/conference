package com.felicia.speakers.controllers;

import com.felicia.speakers.models.Speaker;
import com.felicia.speakers.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakerController {

    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> list() {
        return speakerRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Speaker get(@PathVariable Long id){
        return speakerRepository.getOne(id);
    }

    @PostMapping
    public Speaker create(@RequestBody final Speaker speaker) {
        return speakerRepository.saveAndFlush(speaker);
    }
    //this will just delete session reposotories without any children records therefore I have to doit
    @RequestMapping(value="{id}", method= RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
//Also need to check for the childre records before deleting
        speakerRepository.existsById(id);
    }
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    //because this is Put, we expect all attributes to be passed in. A PATCH would only allowed a portion of attributes to be uppdated
    //TODO: Add validation that all attributes are passed in, otherwise return a 400 bad payload
    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker){ //update all attributes in the RequestBody for that session and replace them all in the database
        Speaker existingSpeaker = speakerRepository.getOne(id);  //finns det en session då tar vi den
        BeanUtils.copyProperties(speaker, existingSpeaker, "speaker_id"); //taking an existing session and copy incoming session data on to it.
        // Ignor session_id becaus it's a primary key and we do not want to replace it
        return  speakerRepository.saveAndFlush(existingSpeaker);
    }

}
