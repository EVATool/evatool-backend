package com.evatool.analysis.model;

import com.evatool.analysis.enums.StakeholderRules;
import com.evatool.analysis.repository.AnalysisRepository;
import com.evatool.analysis.repository.StakeholderRepository;
import com.evatool.analysis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Sampledata {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StakeholderRepository stakeholderRepository;
    @Autowired
    private AnalysisRepository analysisRepository;

    User user = new User();

    public void creatUser() {

        user.setUserName("Falah Obaidi");
        user.setUserEmail("fobaidi@gmx.de");
        user.setUserPassword("12345789");

        userRepository.save(user);
    }

    public void creatStakeholder() {
        Stakeholder stakeholder = new Stakeholder();
        stakeholder.setStakeholderName("Falah Obaid");
        stakeholder.setPriority(1);
        stakeholder.setStakeholderRule(StakeholderRules.society);

        stakeholderRepository.save(stakeholder);

    }

    public void creatAnalysis() {
        Analysis analysis = new Analysis();
        analysis.setAnalysisName("MArvin HallweGrY");
        analysis.setDescription("Das ist eine Beschreibung");

        analysisRepository.save(analysis);

    }

    public void loadUser() {
        Optional<User> falah = userRepository.findById(user.getUserId());
        System.out.println(falah.get().getUserName());

    }

}