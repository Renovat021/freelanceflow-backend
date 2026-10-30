package com.example.freelanceflow.service;

import com.example.freelanceflow.entity.Skill;
import com.example.freelanceflow.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public String saveSkill(Skill skill) {
        skillRepository.save(skill);
        String message = "Skill saved successfully";
        return message;
    }

    public List<Skill> getAllSkills() {
        List<Skill> skillList = skillRepository.findAll();
        return skillList;
    }

    public Optional<Skill> getSkillById(UUID id) {
        Optional<Skill> foundSkill = skillRepository.findById(id);
        return foundSkill;
    }

    public String updateSkill(UUID id, Skill newSkill) {
        String message;
        Optional<Skill> foundSkill = skillRepository.findById(id);
        if (foundSkill.isPresent()) {
            Skill skill = foundSkill.get();
            skill.setName(newSkill.getName());
            skillRepository.save(skill);
            message = "Skill updated successfully";
        } else {
            message = "The skill with that id does not exist";
        }
        return message;
    }

    public String deleteSkill(UUID id) {
        String message;
        Optional<Skill> foundSkill = skillRepository.findById(id);
        if (foundSkill.isPresent()) {
            skillRepository.deleteById(id);
            message = "Skill deleted successfully";
        } else {
            message = "The skill with that id does not exist";
        }
        return message;
    }
}
