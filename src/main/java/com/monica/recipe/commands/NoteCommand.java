package com.monica.recipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class NoteCommand {

    private Long id;
    private String recipeNote;

}
