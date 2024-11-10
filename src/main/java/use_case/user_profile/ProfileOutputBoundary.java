package use_case.user_profile;

public interface ProfileOutputBoundary {

    /**
     * Prepares the success view for the Profile Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(ProfileOutputData outputData);

    /**
     * Prepares the failure view for the Profile Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches to the Profile View.
     */
    void switchToProfileView();

}