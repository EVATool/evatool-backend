package com.evatool.analysis.domain.model;

import com.evatool.analysis.domain.enums.StakeholderLevel;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


@Entity
@Table(name = "ANA_STAKEHOLDER")
public class Stakeholder {

    @Id
    @Getter
    @Setter
    @Type(type= "uuid-char")
    @Column(columnDefinition = "CHAR(36)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID stakeholderId = UUID.randomUUID();

    /**
     * Name of the Stakeholder {@link String}
     */
    @Getter
    private String stakeholderName;

    /**
     * Priority of the Stakeholder {@link int}
     */
    @Getter
    private Integer priority;

    /**
     * The Level of an stakeholder
     */
    @Getter
    @Enumerated(EnumType.STRING)
    private StakeholderLevel stakeholderLevel;


    @Getter
    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<AnalysisImpact> impact = new ArrayList<>();

    @Getter
    @Setter
    @ManyToOne // TODO has to become optional=false
    private Analysis analysis;

    /**
     * GuiId of the Stakeholder {@link String}
     */
    @Getter
    private String guiId;


    public Stakeholder(String stakeholderName, Integer priority, StakeholderLevel stakeholderLevel) {
        this.stakeholderName = stakeholderName;
        this.priority = priority;
        this.stakeholderLevel = stakeholderLevel;
    }

    public Stakeholder() {
    }

    public void setStakeholderLevel(StakeholderLevel stakeholderLevel) {
        if (stakeholderLevel == null){
            throw new IllegalArgumentException("Level name cannot be null.");
        }
        this.stakeholderLevel = stakeholderLevel;
    }

    public void setStakeholderName(String stakeholderName) {
        if (stakeholderName == null){
            throw new IllegalArgumentException("Stakeholder name cannot be null.");
        }
        this.stakeholderName = stakeholderName;
    }

    public void setPriority(Integer priority) {
        if (priority == null){
            throw new IllegalArgumentException("priority name cannot be null.");
        }
        this.priority = priority;
    }

    public void setGuiId(String guiId) {
        if (guiId == null){
            throw new IllegalArgumentException("guiId cannot be null.");
        }
        this.guiId = guiId;
    }





    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
