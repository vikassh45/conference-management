package bo;

import main.ConferenceManagementConfig;

public class Networking extends Event {

    public Networking () {
        super(ConferenceManagementConfig.NETWORKING_START_TIME, "Networking Event", 0);
    }
}
