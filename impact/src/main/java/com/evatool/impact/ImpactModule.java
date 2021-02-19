package com.evatool.impact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Features:
// TODO [hbuhl & tzaika] Event Listener (wait for other group to implement events)
// TODO [tzaika] add swagger API documentation (@Api, @ApiOperation, @ApiResponses, @ApiModel?, @ApiModelProperty?, @ApiParam)
// TODO [hbuhl & tzaika] Rest Level 3: What links to include
// TODO [hbuhl & tzaika] Logging
// TODO [hbuhl & tzaika] DTO Validator (package DTO)
// TODO Implement 422 and other http return codes

// TODO After new repo structure:
//  Redo .run xml configs
//  Rename GitHub Action paths (build, deploy)
//  Remove public modifiers and use default

// Tests:
// TODO [tzaika] Implement Impact API Tests
// TODO [hbuhl & tzaika] Event Tests

// Misc:
// TODO [hbuhl & tzaika] GitHub Issues in backend repo: https://github.com/EVATool/evatool-backend/labels/Team%20Impact
// TODO [tzaika] use new wireframe in impact domain model wiki
// TODO [hbuhl & tzaika] make API and Event spec better
// TODO Use CDC pattern to solve eventing problem?

// SIG:
// TODO [hbuhl] Redo Event Testing (wiki and present)
//  - Event configs have been moved to global module
//  - Real events are async, tests are sync
//  - Bente: No UUID collisions possible (GeneratedValue)?
// TODO [hbuhl & tzaika] Build Tool Chain (bis 19.02.2021)

@SpringBootApplication
public class ImpactModule {
    public static void main(String[] args) {
        SpringApplication.run(ImpactModule.class, args);
    }
}
