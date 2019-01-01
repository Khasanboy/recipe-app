package com.monica.recipe.converters;

import com.monica.recipe.commands.UnitOfMeasureCommand;
import com.monica.recipe.models.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;


public class UnitOfMeasureToUnitOfMeasureCommandTest {

    public static final String DESCRIPTION = "description";
    public static final Long ID = new Long(1L);

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Before
    public void setUp() throws Exception {
        //MockitoAnnotations.initMocks(this); Provides mocks for components declared with @Mock in this file
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullObject() {
        assertNull(unitOfMeasureToUnitOfMeasureCommand.convert(null));
    }

    @Test
    public void testEmptyObject() {
        UnitOfMeasureCommand unitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand.convert(new UnitOfMeasure());
        assertNotNull(unitOfMeasureCommand);
    }

    @Test
    public void convert(){

        //given
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(ID);
        unitOfMeasure.setDescription(DESCRIPTION);

        //when
        UnitOfMeasureCommand uomc = unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure);

        //then
        assertEquals(ID, uomc.getId());
        assertEquals(DESCRIPTION, uomc.getDescription());
    }
}