package app;

import javax.swing.JFrame;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                                            .addLoginView()
                                            .addSignupView()
                                            .addLoggedInView()
                                            .addSignupUseCase()
                                            .addLoggedInUseCase()
                                            .addLoginUseCase()
                                            .addChangePasswordUseCase()
                                            .addLogoutUseCase()
                                            .addProfileView()
                                            .addMealPlanView()
                                            .addCalorieView()
                                            .addGroceryPlanView()
                                            .addCalorieUseCase()
                                            .addGroceryUseCase()
                                            .addProfileUseCase()
                                            .addMealPlanUseCase()
                                            .build();

        application.pack();
        application.setVisible(true);
    }
}
