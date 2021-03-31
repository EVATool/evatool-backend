package com.evatool.analysis.domain.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * This class represents a User
 *
 * @author fobaidi
 * @author MHallweg
 */
@Entity
@Table(name = "ANA_USER")
public class User {

    @Id
    @Getter
    @Setter
    private UUID userId = UUID.randomUUID();

}
