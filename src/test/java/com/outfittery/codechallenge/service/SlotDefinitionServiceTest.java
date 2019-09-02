package com.outfittery.codechallenge.service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.outfittery.codechallenge.model.SlotDefinition;
import com.outfittery.codechallenge.repository.SlotDefinitionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SlotDefinitionServiceTest {

    @InjectMocks
    SlotDefinitonService slotDefinitonService;

    @Mock
    SlotDefinitionRepository slotDefinitionRepositoryMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetExistingActiveSlotDefinition()
    {
        when(slotDefinitionRepositoryMock.findActiveSlotDefinition()).thenReturn(new SlotDefinition());
        slotDefinitonService.getActiveSlotDefiniton();
        verify(slotDefinitionRepositoryMock).findActiveSlotDefinition();
        verify(slotDefinitionRepositoryMock,never()).save(any());
    }

    @Test
    public void shouldCreateNewSlotDefinitionIfThereIsNoDefinedBefore()
    {
        slotDefinitonService.getActiveSlotDefiniton();
        verify(slotDefinitionRepositoryMock).findActiveSlotDefinition();
        verify(slotDefinitionRepositoryMock).save(any());
    }

}
