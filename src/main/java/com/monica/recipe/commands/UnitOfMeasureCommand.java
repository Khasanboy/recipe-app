package com.monica.recipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class UnitOfMeasureCommand implements Serializable {

    private Long id;
    private String description;

}
