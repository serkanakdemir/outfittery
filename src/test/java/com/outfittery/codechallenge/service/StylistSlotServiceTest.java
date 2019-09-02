package com.outfittery.codechallenge.service;


import static com.outfittery.codechallenge.scheduler.SlotScheduler.DATE_RANGE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.outfittery.codechallenge.model.Slot;
import com.outfittery.codechallenge.model.Stylist;
import com.outfittery.codechallenge.model.StylistSlot;
import com.outfittery.codechallenge.repository.StylistSlotRepository;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StylistSlotServiceTest {

    private static final Long SLOT_ID = 1L;
    private static final Long CUSTOMER_ID = 1L;


    @InjectMocks
    StylistSlotService stylistSlotService;

    @Mock
    StylistSlotRepository stylistSlotRepository;

    @Mock
    SlotService slotService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMakeAnAppointment() {
        final StylistSlot stylistSlot = new StylistSlot();
        when(stylistSlotRepository.findAvailableStylistSlots(SLOT_ID)).thenReturn(Arrays.asList(stylistSlot));
        when(stylistSlotRepository.save(stylistSlot)).thenReturn(stylistSlot);
        final Optional<StylistSlot> optionalStylistSlot = stylistSlotService.makeAppointment(CUSTOMER_ID, SLOT_ID);
        Assert.assertEquals(CUSTOMER_ID, optionalStylistSlot.get().getCustomerId());
    }

    @Test
    public void shouldPrepareSlotsForTheStylist() {
        final Stylist stylist = new Stylist();
        when(slotService.findAllSlotsByDateRange(any(), any())).thenReturn(Arrays.asList(new Slot(), new Slot()));
        stylistSlotService.prepareStylistSlots(stylist);
        verify(stylistSlotRepository, times(2)).save(any());
    }


}
