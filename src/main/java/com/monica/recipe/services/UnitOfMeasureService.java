package com.monica.recipe.services;

import com.monica.recipe.commands.UnitOfMeasureCommand;
import com.monica.recipe.models.UnitOfMeasure;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UnitOfMeasureService {

    Optional<UnitOfMeasure> findById(Long id);

    List<UnitOfMeasure> listAllUoms();

    Set<UnitOfMeasureCommand> listAllUomCommands();

}
