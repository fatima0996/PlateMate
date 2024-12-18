package app;

import java.awt.CardLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.ProfileFactory;
import entity.UserFactory;
import interface_adapter.Calorie.CalorieController;
import interface_adapter.Calorie.CaloriePresenter;
import interface_adapter.Calorie.CalorieViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.groceries.GroceryController;
import interface_adapter.groceries.GroceryPresenter;
import interface_adapter.healthyreminders.HealthyRemindersController;
import interface_adapter.healthyreminders.HealthyRemindersPresenter;
import interface_adapter.healthyreminders.HealthyRemindersViewModel;
import interface_adapter.logged_in.LoggedInController;
import interface_adapter.logged_in.LoggedInPresenter;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.meal_plan.MealPlanController;
import interface_adapter.meal_plan.MealPlanPresenter;
import interface_adapter.meal_plan.MealPlanViewModel;
import interface_adapter.groceries.GroceryViewModel;
import interface_adapter.notes.NoteState;
import interface_adapter.notes.NotesController;
import interface_adapter.notes.NotesPresenter;
import interface_adapter.notes.NotesViewModel;
import interface_adapter.profile.*;
import interface_adapter.profile.ProfileState;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.calorie.CalorieInputBoundary;
import use_case.calorie.CalorieInteractor;
import use_case.calorie.CalorieOutputBoundary;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.grocery.GroceryInteractor;
import use_case.healthy_reminders.HealthyRemindersInputBoundary;
import use_case.healthy_reminders.HealthyRemindersInteractor;
import use_case.healthy_reminders.HealthyRemindersOutputBoundary;
import use_case.grocery.GroceryInputBoundary;
import use_case.grocery.GroceryInteractor;
import use_case.grocery.GroceryOutputBoundary;
import use_case.logged_in.LoggedInInputBoundary;
import use_case.logged_in.LoggedInInteractor;
import use_case.logged_in.LoggedInOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.meal_plan.MealPlanInputBoundary;
import use_case.meal_plan.MealPlanInteractor;
import use_case.meal_plan.MealPlanOutputBoundary;
import use_case.notes.NotesInputBoundary;
import use_case.notes.NotesInteractor;
import use_case.notes.NotesOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.user_profile.ProfileInputBoundary;
import use_case.user_profile.ProfileOutputBoundary;
import use_case.user_profile.ProfileInteractor;
import view.LoggedInView;
import view.LoginView;
import view.SignupView;
import view.ViewManager;
import view.ProfileView;
import view.MealView;
import view.GroceryView;
import view.CalorieView;
import view.*;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
// Checkstyle note: you can ignore the "Class Data Abstraction Coupling"
//                  and the "Class Fan-Out Complexity" issues for this lab; we encourage
//                  your team to think about ways to refactor the code to resolve these
//                  if your team decides to work with this as your starter code
//                  for your final project this term.
public class AppBuilder {
    private static String apiKey = "API_KEY";
    private HealthyRemindersInputBoundary healthyRemindersInteractor; // Declare at the class level
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    // thought question: is the hard dependency below a problem?
    private final UserFactory userFactory = new CommonUserFactory();
    private final ProfileFactory profileFactory = new CommonUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // thought question: is the hard dependency below a problem?
    private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();

    private JPanel views = new JPanel(new CardLayout());

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private CalorieViewModel calorieViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private ProfileViewModel profileViewModel;
    private ProfileView profileView;
    private MealPlanViewModel mealPlanViewModel;
    private MealView mealView;
    private GroceryViewModel groceryViewModel;
    private GroceryView groceryView;
    private CalorieView calorieView;
    private HealthyRemindersViewModel healthyRemindersViewModel;
    private HealthyRemindersView healthyRemindersView;
    private NotesView notesView;
    private NotesViewModel notesViewModel;

    public void ChatGPTPost(String apiKey) {
        this.apiKey = apiKey;
        System.out.println("ChatPost initialized with API key: " + apiKey);
    }
    public AppBuilder() {
        this.views = views;
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        profileViewModel = new ProfileViewModel();
        mealPlanViewModel = new MealPlanViewModel();
        groceryViewModel = new GroceryViewModel();
        loggedInView = new LoggedInView(profileViewModel, loggedInViewModel, healthyRemindersViewModel);
        healthyRemindersViewModel = new HealthyRemindersViewModel();
        notesViewModel = new NotesViewModel();
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    /**
     * Adds the Profile View to the application.
     * @return this builder
     */
    public AppBuilder addProfileView() {
        profileViewModel = new ProfileViewModel();
        profileView = new ProfileView(profileViewModel);
        mealPlanViewModel = new MealPlanViewModel();
        groceryViewModel = new GroceryViewModel();
        cardPanel.add(profileView, profileView.getViewName());
        return this;
    }

    public AppBuilder addMealView() throws IOException {
        final MealPlanOutputBoundary mealPlanOutputBoundary = new MealPlanPresenter(viewManagerModel, profileViewModel,
        notesViewModel);
        final MealPlanInputBoundary mealPlanInteractor = new MealPlanInteractor(mealPlanOutputBoundary);
        MealPlanController mealPlanController = new MealPlanController(mealPlanInteractor);
        mealView = new MealView();
        mealView.setMealPlanController(mealPlanController);
        cardPanel.add(mealView, mealView.getViewName());
        return this;
    }

    public AppBuilder addHealthyRemindersView() {
        healthyRemindersViewModel = new HealthyRemindersViewModel();
        healthyRemindersView = new HealthyRemindersView(healthyRemindersViewModel); // Pass ViewModel
        cardPanel.add(healthyRemindersView, healthyRemindersViewModel.getViewName());
        return this;
    }

    public AppBuilder addGroceryPlanView() throws IOException {
        groceryViewModel = new GroceryViewModel();
        groceryView = new GroceryView();
        cardPanel.add(groceryView, groceryView.getViewName());
        return this;
    }

    public AppBuilder addCalorieView() throws IOException {
        calorieViewModel = new CalorieViewModel();
        calorieView = new CalorieView();
        cardPanel.add(calorieView, calorieView.getViewName());
        return this;
    }

    public AppBuilder addNotesView() {
        notesViewModel = new NotesViewModel();
        notesView = new NotesView();
        cardPanel.add(notesView, notesView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }
    public AppBuilder addLoggedInUseCase() {
        // Initialize dependencies for LoggedInUseCase
        final LoggedInOutputBoundary loggedInOutputBoundary = new LoggedInPresenter(
                viewManagerModel, loggedInViewModel, profileViewModel, healthyRemindersViewModel
        );
        final LoggedInInputBoundary loggedInInteractor = new LoggedInInteractor(
                userDataAccessObject, loggedInOutputBoundary, userFactory
        );
        final LoggedInController controller = new LoggedInController(loggedInInteractor);

        // Properly initialize HealthyRemindersInteractor
//        final ChatPost chatPost = new ChatPost("r4A0YoQcxKECMc4f2ipQT7PcKDqljAY8nYoLaETX"); // Replace with your API key
        final ChatGPTPost chatGPTPost = new ChatGPTPost("API_KEY"); // Replace with your API key
        final HealthyRemindersOutputBoundary healthyRemindersPresenter = new HealthyRemindersPresenter(viewManagerModel,
                loggedInViewModel,
                healthyRemindersViewModel);
        final HealthyRemindersInputBoundary healthyRemindersInteractor = new HealthyRemindersInteractor(
                userDataAccessObject, healthyRemindersPresenter, chatGPTPost
        );

        final HealthyRemindersController healthyRemindersController = new HealthyRemindersController(healthyRemindersInteractor);

        // Set the HealthyRemindersController in the LoggedInController
        controller.setHealthyRemindersController(healthyRemindersController);

        // Set the controller for the LoggedInView
        loggedInView.setLoggedInController(controller);

        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel, signupViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addProfileUseCase() {
        final ProfileOutputBoundary profileOutputBoundary = new ProfilePresenter(viewManagerModel, loggedInViewModel,
                mealPlanViewModel,calorieViewModel, profileViewModel, groceryViewModel);
        final ProfileInputBoundary profileInteractor = new ProfileInteractor(userDataAccessObject,
                profileOutputBoundary, profileFactory);

         final ProfileController profileController = new ProfileController(profileInteractor);
         profileView.setProfileController(profileController);
         loggedInView.setProfileController(profileController);
         return this;
    }

    /**
     * Adds the Change Password Use Case to the application.
     * @return this builder
     */
    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary =
                new ChangePasswordPresenter(loggedInViewModel);

        final ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);

        final ChangePasswordController changePasswordController =
                new ChangePasswordController(changePasswordInteractor);
        loggedInView.setChangePasswordController(changePasswordController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);
        return this;
    }

    public AppBuilder addMealPlanUseCase() {
        final MealPlanOutputBoundary mealPlanOutputBoundary = new MealPlanPresenter(viewManagerModel, profileViewModel,
                notesViewModel);
        final MealPlanInputBoundary mealPlanInteractor = new MealPlanInteractor(mealPlanOutputBoundary);
        final MealPlanController mealPlanController = new MealPlanController(mealPlanInteractor);
        return this;
    }

    public AppBuilder addCalorieUseCase() {
        final CalorieOutputBoundary calorieOutputBoundary = new CaloriePresenter(viewManagerModel, profileViewModel);
        final CalorieInputBoundary calorieInteractor = new CalorieInteractor(calorieOutputBoundary);
        final CalorieController calorieController = new CalorieController(calorieInteractor);
        calorieView.setCalorieController(calorieController);
        return this;
    }

    public AppBuilder addGroceryUseCase() {
        final GroceryOutputBoundary groceryOutputBoundary = new GroceryPresenter(viewManagerModel, profileViewModel);
        final GroceryInputBoundary groceryInteractor = new GroceryInteractor(groceryOutputBoundary);
        final GroceryController groceryController = new GroceryController(groceryInteractor);
        groceryView.setGroceryController(groceryController);
        return this;
    }

    public AppBuilder addHealthyRemindersUseCase() {
        final HealthyRemindersOutputBoundary healthyRemindersOutputBoundary = new HealthyRemindersPresenter(viewManagerModel,
                loggedInViewModel, healthyRemindersViewModel);

        final ChatGPTPost chatGPTPost = new ChatGPTPost("API_KEY");
        final HealthyRemindersInputBoundary healthyRemindersInteractor = new HealthyRemindersInteractor(
                userDataAccessObject, healthyRemindersOutputBoundary, chatGPTPost);
        final HealthyRemindersController healthyRemindersController = new HealthyRemindersController(
                healthyRemindersInteractor);
        healthyRemindersView.setHealthyRemindersController(healthyRemindersController);
        return this;
    }

    public AppBuilder addNotesUseCase() {
        final NotesOutputBoundary notesOutputBoundary = new NotesPresenter(viewManagerModel, mealPlanViewModel);
        final NotesInputBoundary notesInteractor = new NotesInteractor(notesOutputBoundary);
        final NotesController notesController = new NotesController(notesInteractor);
        notesView.setNotesController(notesController);
        return this;
    }
    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("PlateMate");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }

}
