package com.outfittery.codechallenge.scheduler;

import static java.time.temporal.ChronoUnit.DAYS;

import com.outfittery.codechallenge.model.Slot;
import com.outfittery.codechallenge.model.SlotDefinition;
import com.outfittery.codechallenge.model.Stylist;
import com.outfittery.codechallenge.model.StylistSlot;
import com.outfittery.codechallenge.repository.SlotDefinitionRepository;
import com.outfittery.codechallenge.repository.SlotRepository;
import com.outfittery.codechallenge.repository.StylistRepository;
import com.outfittery.codechallenge.repository.StylistSlotRepository;
import com.outfittery.codechallenge.service.SlotDefinitonService;
import com.outfittery.codechallenge.service.SlotService;
import com.outfittery.codechallenge.service.StylistService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SlotScheduler {

    @Autowired
    private SlotDefinitonService slotDefinitonService;

    @Autowired
    private SlotService slotService;

    @Autowired
    StylistService stylistService;

    //TODO I assumed that maximum day to make a appointment is 2 weeks
    public static Integer DATE_RANGE = 14;


    private static final LocalTime START_TIME = LocalTime.of(9, 0);
    private static final LocalTime END_TIME = LocalTime.of(15, 30);


    //TODO it should only work once in a day at midnight, but for testing reason, i keep it as fixedRate
    @Scheduled(fixedRate = 200000,initialDelay = 20000)
    public void fillSlotsAndStylistSlots() {

        LocalDate localDate = LocalDate.now();

        final SlotDefinition activeSlotDefinition = slotDefinitonService.getActiveSlotDefiniton();
        final List<Stylist> availableStylists = stylistService.findAvailableStylists();

        Map<Integer, List<Slot>> slotMap = prepareSlotMap(localDate);

        //find missing slots and fill the map
        slotMap.entrySet().forEach(entry -> {
            final List<Slot> slotList = entry.getValue();

            // Slot creation is Transactional, so we can trust all save is atomic.
            if (slotList.isEmpty()) {
                LocalTime startTime = START_TIME;

                while (!startTime.isAfter(END_TIME)) {
                    Slot slot = new Slot();
                    slot.setDate(localDate.plusDays(entry.getKey()));
                    slot.setStart(startTime);
                    slot.setFinish(startTime.plusMinutes(activeSlotDefinition.getDuration()));
                    slot.setSlotDefinition(activeSlotDefinition);

                    // it is time to fill stylistSlot table for each slot and stylist
                    availableStylists.forEach(stylist->{
                        StylistSlot stylistSlot=new StylistSlot();
                        stylistSlot.setSlot(slot);
                        stylistSlot.setStylist(stylist);
                        slot.getStylistSlotList().add(stylistSlot);
                    });

                    slotService.save(slot);
                    startTime = startTime.plusMinutes(activeSlotDefinition.getDuration());
                }
            }
        });
    }

    private Map<Integer,List<Slot>> prepareSlotMap(LocalDate localDate)
    {
        final List<Slot> allSlotsByDateRange = slotService
            .findAllSlotsByDateRange(localDate, localDate.plusDays(DATE_RANGE));

        Map<Integer, List<Slot>> slotMap = new HashMap<>();
        IntStream.range(0, DATE_RANGE).forEach(day -> slotMap.put(day, new ArrayList<>()));

        //fill map with existing slots by date
        allSlotsByDateRange.forEach(slot -> slotMap.get(DAYS.between(localDate, slot.getDate())).add(slot));
        return slotMap;
    }

}
