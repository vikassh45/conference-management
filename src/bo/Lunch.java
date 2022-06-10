package bo;

import main.ConferenceManagementConfig;

public class Lunch extends Event {
    public Lunch() {
        super(ConferenceManagementConfig.LUNCH_START_TIME, "Lunch", ConferenceManagementConfig.LUNCH_DURATION_MINUTES);
    }
}
