package com.monica.recipe.services;

import com.monica.recipe.commands.UnitOfMeasureCommand;
import com.monica.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.monica.recipe.models.UnitOfMeasure;
import com.monica.recipe.repositories.UnitOfMeasureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService{

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Override
    public Optional<UnitOfMeasure> findById(Long id) {
        return unitOfMeasureRepository.findById(id);
    }

    @Override
    public List<UnitOfMeasure> listAllUoms() {
        return unitOfMeasureRepository.findAll();
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUomCommands() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll()
                .spliterator(), false)
                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                .collect(Collectors.toSet());
    }
}
