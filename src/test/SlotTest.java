package test;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import bo.Slot;
import bo.Talk;
import util.ConferenceUtils;

public class SlotTest {

    @Test
    public void testHasRoomForTalk(){

        Calendar slotStartTime = ConferenceUtils.getCalendarTime(9, 00);
        Slot slot = new Slot(50, slotStartTime);

        Talk talk1 = new Talk("Valid Talk", 45);
        Assert.assertTrue(slot.hasRoomFor(talk1));

        Talk talk2 = new Talk("InValid Talk", 60);
        Assert.assertFalse(slot.hasRoomFor(talk2));

    }
}
