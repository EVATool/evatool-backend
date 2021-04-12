package com.evatool.variants.domain.entities;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.hateoas.RepresentationModel;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "VAR_VariantsRequirements")
public class VariantsRequirements extends RepresentationModel<VariantsRequirements> {

    @Id
    @Type(type = "uuid-char")
    @Column(columnDefinition = "CHAR(36)")
    private UUID requirementId;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Variant> variants = new ArrayList<>();

    public static VariantsRequirements fromJson(String json) {
        return new Gson().fromJson(json, VariantsRequirements.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VariantsRequirements that = (VariantsRequirements) o;
        return Objects.equals(requirementId, that.requirementId) && Objects.equals(variants, that.variants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), requirementId, variants);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
