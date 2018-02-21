package application;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import userData.UserDataHandler;
import userData.UserDataSet;
import userData.UserDataTypes;

/**
 * <h1>Main</h1>
 * This is a password generator
 * The password is not saved in any way
 *
 * @author alphaClass
 * @version 1.0
 *
 */
public class Main extends Application {
	//primaryStage and rootScene are defined as global variables to use them in the start and loadGUI method
	private Stage primaryStage;
	private Scene rootScene;

	private MainController mainController;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		//create scene (init scene in the start method to avoid resizing of the window)
		rootScene = new Scene(new Parent() {}, 400, 425);

		//set stage for the global var
		primaryStage = stage;

		//disable resizing and set icon
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/userTheme/img/icon.png")));

		//load gui and show the stage
		loadGUI();
		primaryStage.show();

		//add listener for focus change on the window
		stage.focusedProperty().addListener((obs, oldVal, newVal) -> mainController.resetCopyBtn());
	}

	/**
	 * Loads the GUI
	 */
	public void loadGUI() {
		//get current set of user data
		UserDataSet userData = UserDataHandler.getInstance().getSettings();

		//read the current set language
		Locale locale = new Locale(userData.read(UserDataTypes.languageLangCode), userData.read(UserDataTypes.languageCountryCode));

		//load the correct language bundle
		ResourceBundle bundle = ResourceBundle.getBundle("LanguageBundle", locale);

		//set window title
		primaryStage.setTitle(bundle.getString("lang.windowTitle"));

		try {
			//load FXML
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/userTheme/Main.fxml"), bundle);

			//set root
			Parent root = loader.load();

			//set controller
			mainController = loader.getController();

			//set parent of the controller
			mainController.setParent(this);

			//create scene
			rootScene.setRoot(root);

			//clear css to avoid two css files being loaded at the same time
			rootScene.getStylesheets().clear();

			//add css selected by the user
			rootScene.getStylesheets().add(getClass().getResource("/userTheme/css/" + userData.read(UserDataTypes.userTheme) + ".css").toExternalForm());

			//insert scene into stage
			primaryStage.setScene(rootScene);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}