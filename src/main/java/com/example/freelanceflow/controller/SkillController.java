package com.example.freelanceflow.controller;

import com.example.freelanceflow.entity.Skill;
import com.example.freelanceflow.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @PostMapping("/save")
    public ResponseEntity<String> saveSkill(@RequestBody Skill skill) {
        String message = skillService.saveSkill(skill);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Skill>> getAllSkills() {
        List<Skill> skillList = skillService.getAllSkills();
        return new ResponseEntity<>(skillList, HttpStatus.OK);
    }

    @GetMapping("/unused")
    public ResponseEntity<List<Skill>> getUnusedSkills() {
        List<Skill> skillList = skillService.getUnusedSkills();
        return new ResponseEntity<>(skillList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSkillById(@PathVariable UUID id) {
        Optional<Skill> foundSkill = skillService.getSkillById(id);
        if (foundSkill.isPresent()) {
            return new ResponseEntity<>(foundSkill.get(), HttpStatus.OK);
        } else {
            String message = "The skill with that id does not exist";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSkill(@PathVariable UUID id, @RequestBody Skill skill) {
        String message = skillService.updateSkill(id, skill);
        if (message.equals("The skill with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSkill(@PathVariable UUID id) {
        String message = skillService.deleteSkill(id);
        if (message.equals("The skill with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
