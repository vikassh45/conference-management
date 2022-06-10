package test;

import java.util.Calendar;
import org.junit.Assert;
import org.junit.Test;
import bo.Talk;
import util.ConferenceUtils;

public class ConferenceUtilsTest {


    @Test
    public void testGetCalendarTime() {

        Calendar cal1 = ConferenceUtils.getCalendarTime(5, 50);

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.HOUR_OF_DAY, 5);
        cal2.set(Calendar.MINUTE, 50);
        Assert.assertEquals(cal1.get(Calendar.HOUR_OF_DAY), cal2.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(cal1.get(Calendar.MINUTE), cal2.get(Calendar.MINUTE));

        cal1 = ConferenceUtils.getCalendarTime(5, 70);
        cal2 = Calendar.getInstance();
        cal2.set(Calendar.HOUR_OF_DAY, 5);
        cal2.set(Calendar.MINUTE, 70);

        Assert.assertEquals(cal1.get(Calendar.HOUR_OF_DAY), cal2.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(cal1.get(Calendar.MINUTE), cal2.get(Calendar.MINUTE));

    }

    @Test
    public void testGetNextStartTime() {
        Calendar currentStartTime = ConferenceUtils.getCalendarTime(5, 50);
        Talk talk = new Talk("Test Talk", 20);

        Calendar nextStartTimeManual = ConferenceUtils.getCalendarTime(6, 10);
        Calendar nextStartTimeCalculated = ConferenceUtils.getNextStartTime(currentStartTime, talk);

        Assert.assertEquals(nextStartTimeManual.get(Calendar.HOUR_OF_DAY), nextStartTimeCalculated.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(nextStartTimeManual.get(Calendar.MINUTE), nextStartTimeCalculated.get(Calendar.MINUTE));
    }
}
