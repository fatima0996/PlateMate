package interface_adapter.healthyreminders;

import interface_adapter.ViewModel;

public class HealthyRemindersViewModel extends ViewModel<HealthyRemindersState> {

    public HealthyRemindersViewModel() {
        super("HealthyReminders"); // Set the view name
        setState(new HealthyRemindersState()); // Initialize the state
    }

    /**
     * Updates the reminder in the state and notifies listeners.
     * @param reminder The new healthy reminder
     */
    public void setReminder(String reminder) {
        HealthyRemindersState state = getState();
        state.setCurrentReminder(reminder);
        firePropertyChanged();
    }
}


//package interface_adapter.healthyreminders;
//
//import interface_adapter.ViewModel;
//
//public class HealthyRemindersViewModel extends ViewModel<HealthyRemindersState> {
//    public HealthyRemindersViewModel() {
//        super("HealthyReminders");
//        // Initialize the state with a new instance of HealthyRemindersState
//        setState(new HealthyRemindersState());
//    }
//
//    /**
//     * Sets the current reminder and notifies listeners.
//     *
//     * @param reminder The random healthy reminder fetched from the API.
//     */
//    public void setReminder(String reminder) {
//        final HealthyRemindersState currentState = getState();
//        currentState.setCurrentReminder(reminder);
//        firePropertyChanged(); // Notify listeners of the new reminder
//    }
//}

