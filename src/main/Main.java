package main;


import java.util.List;

import bo.Conference;
import bo.Talk;
import enums.DataOutputEnum;
import enums.DataSourceEnum;
import exceptions.UnsupportedDestinationException;
import exceptions.UnsupportedSourceException;
import util.ConferenceUtils;

public class Main {

    public static void main(String[] args) {

        ConferenceManager conferenceManager = new ConferenceManager();
        List<Talk> talkList = null;
        try {
            talkList = conferenceManager.fetchTalksListFromSource(DataSourceEnum.FILE);
        } catch (UnsupportedSourceException e){
            System.err.println(e.getMessage());
        }

        if(talkList == null || talkList.size() == 0)
            return;

        ConferenceUtils.printTalks(talkList);

        Conference conference = conferenceManager.processAndScheduleTalks(talkList);

        try {
            conferenceManager.outputConferenceSchedule(conference, DataOutputEnum.CONSOLE);
        } catch (UnsupportedDestinationException e){
            System.err.println(e.getMessage());
        }

    }
}