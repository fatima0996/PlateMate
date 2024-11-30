package use_case.grocery;

import use_case.user_profile.ProfileOutputData;

public interface GroceryOutputBoundary {

    /**
     * Prepares the success view for the Meal Plan Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(ProfileOutputData outputData);

    /**
     * Prepares the failure view for the Meal Plan Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    void switchToProfileView();
}
