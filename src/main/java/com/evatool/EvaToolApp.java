package com.evatool;

import com.evatool.requirements.domain.entity.*;
import com.evatool.requirements.domain.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class EvaToolApp {
    public static void main(String[] args) {
        SpringApplication.run(EvaToolApp.class, args);
    }
    @Bean
    public CommandLineRunner initData(RequirementsVariantsRepository requirementsVariantsRepository,
                                      RequirementRepository requirementRepository,
                                      RequirementsImpactsRepository requirementsImpactsRepository,
                                      RequirementPointRepository requirementPointRepository,
                                      RequirementAnalysisRepository requirementAnalysisRepository,
                                      RequirementDimensionRepository requirementDimensionRepository) {
        return (args) -> {
            RequirementsAnalysis requirementsAnalysis = new RequirementsAnalysis();
            requirementAnalysisRepository.save(requirementsAnalysis);

            RequirementsVariant home = new RequirementsVariant("Home-Office","If people only work from home.");
            RequirementsVariant office = new RequirementsVariant("Office","If people only work in the office.");
            requirementsVariantsRepository.save(home);
            requirementsVariantsRepository.save(office);

            Requirement requirement1 = new Requirement("RE01","Documentation for the Software",requirementsAnalysis, Arrays.asList(home,office));
            requirementRepository.save(requirement1);

            Requirement requirement2 = new Requirement("RE02","Privacy data show encoding (not visible at home)",requirementsAnalysis, Arrays.asList(office));
            requirementRepository.save(requirement2);

            RequirementDimension requirementDimension = new RequirementDimension("Privat");
            requirementDimensionRepository.save(requirementDimension);
            RequirementDimension requirementDimension2 = new RequirementDimension("Safety");
            requirementDimensionRepository.save(requirementDimension2);
            RequirementDimension requirementDimension3 = new RequirementDimension("Feelings");
            requirementDimensionRepository.save(requirementDimension3);

            RequirementsImpact requirementsImpact1 = new RequirementsImpact("IM01",-1d,requirementDimension);
            requirementsImpactsRepository.save(requirementsImpact1);
            for(int i=2;i<10;i++){
                RequirementsImpact requirementsImpact = new RequirementsImpact("IM0"+i,-1d,requirementDimension2);
                requirementsImpactsRepository.save(requirementsImpact);
            }for(int i=10;i<30;i++){
                RequirementsImpact requirementsImpact = new RequirementsImpact("IM"+i,-1d,requirementDimension3);
                requirementsImpactsRepository.save(requirementsImpact);
            }

            RequirementPoint requirement_gr1 = new RequirementPoint(requirementsImpact1,1d);
            RequirementPoint requirement_gr2 = new RequirementPoint(requirementsImpact1,1d);

            requirementPointRepository.save(requirement_gr1);
            requirementPointRepository.save(requirement_gr2);

            requirement1.getRequirementPointCollection().add(requirement_gr1);
            requirement2.getRequirementPointCollection().add(requirement_gr2);

            requirementRepository.saveAll(Arrays.asList(requirement1,requirement2));
        };
    }
}
