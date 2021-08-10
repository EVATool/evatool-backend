package com.evatool.application.json;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@EqualsAndHashCode
@ToString
public class SuperJson {

    @Getter
    @Setter
    private UUID id;

}
