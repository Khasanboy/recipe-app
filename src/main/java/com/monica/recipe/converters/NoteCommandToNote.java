package com.monica.recipe.converters;

import com.monica.recipe.commands.NoteCommand;
import com.monica.recipe.models.Note;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NoteCommandToNote implements Converter<NoteCommand, Note> {

    @Synchronized
    @Nullable
    @Override
    public Note convert(NoteCommand noteCommand) {

        if (noteCommand == null) {
            return null;
        }

        final Note note = new Note();
        note.setId(noteCommand.getId());
        note.setRecipeNote(noteCommand.getRecipeNote());

        return note;
    }
}
