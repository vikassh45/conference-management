package main;

import java.io.FileNotFoundException;
import java.util.*;

import bo.Conference;
import bo.Event;
import bo.Lunch;
import bo.Networking;
import bo.Slot;
import bo.Talk;
import bo.TalksCompare;
import bo.Track;
import enums.DataOutputEnum;
import enums.DataSourceEnum;
import exceptions.UnsupportedDestinationException;
import exceptions.UnsupportedSourceException;
import io.ConferenceFileSourceManager;
import io.ConsoleOutputManager;
import util.ConferenceUtils;

public class ConferenceManager {

    public List<Talk> fetchTalksListFromSource(DataSourceEnum dataSourceEnum) throws UnsupportedSourceException{

        if (dataSourceEnum.equals(DataSourceEnum.FILE)) {
            ConferenceFileSourceManager sourceManager = new ConferenceFileSourceManager();
            try {
                return sourceManager.fetchTalks();
            } catch (FileNotFoundException e) {

                return null;
            }
        } else {
            throw new UnsupportedSourceException("Source type not valid");
        }
    }

    @SuppressWarnings("static-access")
	public void outputConferenceSchedule(Conference conference, DataOutputEnum dataOutputEnum) throws UnsupportedDestinationException {

        if (dataOutputEnum.equals(DataOutputEnum.CONSOLE.CONSOLE)) {
            ConsoleOutputManager outputManager = new ConsoleOutputManager();
            outputManager.printSchedule(conference);
        } else {
            throw new UnsupportedDestinationException("Destination not valid.");
        }
    }


    public Conference processAndScheduleTalks(List<Talk> talkList){
        Conference conference = new Conference();

        Collections.sort(talkList, new TalksCompare());
        int trackCount = 0;

        while (0 != talkList.size()) {

            // create and fill morning slot.
            Slot morningSlot = new Slot(ConferenceManagementConfig.MORNING_SLOT_DURATION_MINUTES, ConferenceManagementConfig.TRACK_START_TIME);
            fillSlot(morningSlot, talkList);

            // create and fill lunch slot.
            Slot lunchSlot = new Slot(ConferenceManagementConfig.LUNCH_DURATION_MINUTES, ConferenceManagementConfig.LUNCH_START_TIME);
            lunchSlot.addEvent(new Lunch());

            // create and fill afternoon slot.
            Slot afternoonSlot = new Slot(ConferenceManagementConfig.AFTERNOON_SLOT_DURATION_MINUTES,
                    ConferenceManagementConfig.POST_LUNCH_SLOT_START_TIME);
            fillSlot(afternoonSlot, talkList);

            // create and fill networking slot.
            Slot networkingSlot = new Slot(ConferenceManagementConfig.NETWORKING_DURATION_MINUTES,
                    ConferenceManagementConfig.NETWORKING_START_TIME);
            networkingSlot.addEvent(new Networking());

            // add all the slots for the day into the track.
            Track track = new Track(++trackCount);
            track.addSlot(morningSlot);
            track.addSlot(lunchSlot);
            track.addSlot(afternoonSlot);
            track.addSlot(networkingSlot);
            // add track to the conference.
            conference.addTrack(track);
        }

        return conference;
    }

    private void fillSlot(Slot slot, List<Talk> talks) {
        // initialize the slot start time.
        Calendar currentStartTime = slot.getStartTime();
        for (Iterator<Talk> talksIterator = talks.iterator(); talksIterator.hasNext();) {
            Talk talk = talksIterator.next();
            if (slot.hasRoomFor(talk)) {
                // add an event to the slot at the currentStartTime calculated.
                slot.addEvent(new Event(currentStartTime, talk.getTitle(), talk.getDurationInMinutes()));
                // calculate the next start time based on the current start time and current talk duration.
                currentStartTime = ConferenceUtils.getNextStartTime(currentStartTime, talk);
                // remove the talk from the list. This means that the talk has been scheduled in the conference.
                talksIterator.remove();
            }
        }
    }

}