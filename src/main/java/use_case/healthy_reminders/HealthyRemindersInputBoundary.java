package use_case.healthy_reminders;

public interface HealthyRemindersInputBoundary {

    /**
     * Executes the profile use case.
     */
    String generateReminder();

    void execute(HealthyRemindersInputData healthyRemindersInputData);

}
//package use_case.healthy_reminders;
//
//public interface HealthyRemindersInputBoundary {
//    String generateReminder();
//}
