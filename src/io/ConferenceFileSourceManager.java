package io;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import bo.Talk;
import main.ConferenceManagementConfig;

public class ConferenceFileSourceManager {

    @SuppressWarnings("resource")
	public List<Talk> fetchTalks(String fileName) throws FileNotFoundException{
        FileInputStream fstream = null;
        List<Talk> talkList = new ArrayList<>();

        try {
            fstream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            System.err.println("Input file specified not found : " + ConferenceManagementConfig.TALKS_INPUT_FILE + ". Make sure the file exists");
            throw e;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;
        int intMinutes;

        try {
            while ((strLine = br.readLine()) != null) {
                if(strLine.contains("//") || strLine.isEmpty())
                    continue;

                String title = strLine.substring(0, strLine.lastIndexOf(" "));
                String minutesString = strLine.substring(strLine.lastIndexOf(" ") + 1);
                String minutes = strLine.replaceAll("\\D+", "");
                if (ConferenceManagementConfig.LIGHTNING_TALK.equals(minutesString)) {
                    intMinutes = ConferenceManagementConfig.LIGHTNING_TALK_DURATION_MINUTES;
                } else {
                    try {
                        intMinutes = Integer.parseInt(minutes);
                    } catch (NumberFormatException e) {
                        System.err.println("Could not parse the line : " + strLine);
                        throw e;
                    }
                }
                Talk singleTalk = new Talk(title, intMinutes);
                talkList.add(singleTalk);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fstream.close();
                br.close();
            } catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
        return talkList;
    }

    public List<Talk> fetchTalks() throws FileNotFoundException{
        return fetchTalks(ConferenceManagementConfig.TALKS_INPUT_FILE);
    }
}
