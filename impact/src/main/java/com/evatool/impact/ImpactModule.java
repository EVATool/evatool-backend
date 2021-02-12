package com.evatool.impact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Features:
// TODO [hbuhl & tzaika] Events (wait until 19.02.2021)

// Tests:
// TODO [tzaika] Implement Impact API Tests
// TODO [hbuhl] ErrorMessage tests in RestController?

// TODO [hbuhl & tzaika] Event Tests (wait until 19.02.2021)

// Team:
// TODO Install SonarLint IDE plugin and remove warnings
// TODO [hbuhl] Use poc-way of creating and inserting Rest Level 3 links
// TODO [hbuhl & tzaika] Test if real events are asynchronous
// TODO [tzaika] ImpactRest with Swagger
// TODO [tzaika] use new wireframe in impact wiki
// TODO [hbuhl & tzaika] GitHub Issues in backend repo: https://github.com/EVATool/evatool-backend/labels/Team%20Impact
// TODO [hbuhl & tzaika] Clean dependencies and put in root pom
// TODO [hbuhl & tzaika] add swagger API documentation (@Api, @ApiOperation, @ApiResponses, @ApiModel, @ApiModelProperty, @ApiParam)
// TODO [hbuhl] Mandantenfähigkeit? (just for fun)

// TODO [hbuhl] use strings in request mappings for readability (wait until 19.02.2021) [Property file?]
// TODO [hbuhl & tzaika] Use UUID class in Entity? (wait until 19.02.2021)
// TODO [hbuhl & tzaika] Keep id in DTO? (Rest Level 3 HATEOAS) (wait until 19.02.2021)
// TODO [hbuhl & tzaika] Auth - @RolesAllowed (wait until 19.02.2021)

// SIG:
// TODO [hbuhl & tzaika] Build Tool Chain (bis 19.02.2021)
// TODO [hbuhl] Redo Event Testing (after async test behaviour has been validated in real application) [Config in root?]

// Bente:
// TODO Config files in root

@SpringBootApplication
public class ImpactModule {
    public static void main(String[] args) {
        SpringApplication.run(ImpactModule.class, args);
    }
}
