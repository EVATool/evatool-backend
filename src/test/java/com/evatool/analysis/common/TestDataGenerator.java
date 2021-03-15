package com.evatool.analysis.common;

import com.evatool.analysis.dto.AnalysisDTO;
import com.evatool.analysis.dto.StakeholderDTO;
import com.evatool.analysis.dto.UserDTO;
import com.evatool.analysis.enums.Dimension;
import com.evatool.analysis.enums.StakeholderLevel;
import com.evatool.analysis.model.Analysis;
import com.evatool.analysis.model.AnalysisImpacts;
import com.evatool.analysis.model.Stakeholder;
import com.evatool.analysis.model.User;

public class TestDataGenerator {

    public static Analysis getAnalysis() {
        return new Analysis("AnalysisName", "descriptionTitle");
    }

    public static Stakeholder getStakholder() {
        return new Stakeholder("StakeholderName", Integer.valueOf(10), StakeholderLevel.NATURAL_PERSON);
    }

    public static User getUser() {
        return new User("UserName", "1234", "TestMail@gamil.com");
    }

    public static AnalysisDTO getAnalysisDTO(String name, String description) {

        var analysisDTO = new AnalysisDTO();

        analysisDTO.setAnalysisName(name);
        analysisDTO.setAnalysisDescription(description);
        return analysisDTO;

    }

    public static StakeholderDTO getStakeholderDTO(String stakeholderName, int priority, StakeholderLevel stakeholderLevel, AnalysisImpacts impacts) {
        var stakeholderDTO = new StakeholderDTO();

        stakeholderDTO.setStakeholderName(stakeholderName);
        stakeholderDTO.setPriority(priority);
        stakeholderDTO.setStakeholderLevel(stakeholderLevel);
        stakeholderDTO.setImpacts(impacts);

        return stakeholderDTO;
    }

    public static AnalysisImpacts getAnalysisImpacts(Dimension dimension) {
        return new AnalysisImpacts("title", "description", 1, dimension);
    }

    public static UserDTO getUserDTO(String userName, String userPassword, String userEmail){
        var userDTO = new UserDTO();

        userDTO.setUserName(userName);
        userDTO.setPassword(userPassword);
        userDTO.setEmail(userEmail);
        return userDTO;
    }
}
