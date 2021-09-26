package com.evatool.domain.entity;

import com.evatool.common.enums.StakeholderLevel;
import com.evatool.common.enums.StakeholderPriority;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "stakeholder")
@Table(name = "stakeholder")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Stakeholder extends PrefixIdEntity implements FindByAnalysis {

    private static final Logger logger = LoggerFactory.getLogger(Stakeholder.class);

    @Getter
    @Setter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter
    @Setter
    @Column(name = "description", nullable = false, length = 2048)
    private String description;

    @Getter
    @Setter
    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    private StakeholderPriority priority;

    @Getter
    @Setter
    @Column(name = "level", nullable = false)
    @Enumerated(EnumType.STRING)
    private StakeholderLevel level;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    private Analysis analysis;

    @Getter
    @OneToMany(orphanRemoval = true, mappedBy = "stakeholder")
    @LazyCollection(LazyCollectionOption.FALSE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final Set<Impact> impacts = new HashSet<>();

    public Stakeholder(String name,String description, StakeholderPriority priority, StakeholderLevel level, Analysis analysis) {
        super();
        logger.trace("Constructor");
        setName(name);
        setDescription(description);
        setPriority(priority);
        setLevel(level);
        setAnalysis(analysis);
    }

    private Stakeholder() {

    }

    @PrePersist
    @PreUpdate
    @PreRemove
    void prePersistUpdateRemove() {
        logger.trace("Pre Persist/Pre Update/Pre Remove");
        FindByAnalysis.super.updateAnalysisLastUpdated();
    }

    @PostPersist
    void postPersist() {
        logger.trace("Post Persist");
        analysis.getStakeholders().add(this);
    }

    @PostRemove
    void postRemove() {
        logger.trace("Post Remove");
        analysis.getStakeholders().remove(this);
    }

    public Float getImpacted() {
        logger.trace("Get Impacted");
        if (this.impacts.isEmpty()) {
            return null;
        }

        var impactSum = 0f;
        var impactNum = this.impacts.size();
        for (var impact : this.impacts) {
            impactSum += impact.getMerit();
        }
        return impactSum / impactNum;
    }

    @Override
    public String getPrefix() {
        String prefix;

        switch (level) {
            case INDIVIDUAL:
                prefix = "IND";
                break;

            case ORGANIZATION:
                prefix = "ORG";
                break;

            case SOCIETY:
                prefix = "SOC";
                break;

            default:
                throw new IllegalArgumentException(String.format("Stakeholder level \"%s\" has no prefix", level));
        }

        return prefix;
    }

    @Override
    public String getParentId() {
        if(this.analysis.getId() == null){
            throw new IllegalStateException("Parent entity analysis must be persisted before this method is called");
        }
        return this.analysis.getId().toString();
    }

    @Override
    public String getParentClass() {
        return "Analysis";
    }

    @Override
    public String getChildClass() {
        return "Stakeholder";
    }
}
