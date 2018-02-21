package application;
import java.net.URL;
import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import passwordLogicCore.PWGen;
import passwordLogicCore.SequenceClassTypes;
import userData.UserDataHandler;
import userData.UserDataSet;
import userData.UserDataTypes;

public class MainController implements Initializable {
    /*
     ***************************************************************************
     *                                                                         *
     * Non-GUI parts                                                          *
     *                                                                         *
     **************************************************************************
     */

    //gets user data handler
    private final UserDataHandler dataHandler = UserDataHandler.getInstance();

    //parent of the controller (the Main application)
    private Main parent;

    //password generator object which stores all information about the password generation
    private PWGen generator;

    //language bundle
    //global variable for clipboardUpdate method
    ResourceBundle bundle;

    /*
    ========== Clipboard ==========
    */

    //Clipboard
    private final Clipboard clipboard = Clipboard.getSystemClipboard();

    //content of the Clipboard
    private final ClipboardContent content = new ClipboardContent();


    /*
     ***************************************************************************
     *                                                                         *
     * Buttons                                                               *
     *                                                                         *
     **************************************************************************
     */

    //buttons on main overview
    @FXML private Button generateBtn;
    @FXML private Button copyBtn;

    //buttons in settings
    @FXML private Button cancelBtn;
    @FXML private Button saveBtn;

    //button to open the settings
    //not an actual button since it takes up so much space and is an actual
    //part of the scrolling container
    @FXML private HBox settingsTabBtn;

    /*
     ***************************************************************************
     *                                                                         *
     * Slider                                                                 *
     *                                                                         *
     **************************************************************************
     */

    //slider on the main overview
    @FXML private Slider pwSize;

    //slider in the settings
    @FXML private Slider pwDefaultSize;


    /*
     ***************************************************************************
     *                                                                         *
     * ComboBox                                                               *
     *                                                                         *
     **************************************************************************
     */

    @FXML private ComboBox<String> languageList;
    @FXML private ComboBox<String> themeList;


    /*
     ***************************************************************************
     *                                                                         *
     * CheckBox                                                               *
     *                                                                         *
     **************************************************************************
     */

    @FXML private CheckBox lowerCaseSettingBox;
    @FXML private CheckBox upperCaseSettingBox;
    @FXML private CheckBox numberSettingBox;
    @FXML private CheckBox specialCharSettingBox;
    @FXML private CheckBox excludeConfusingCharSettingBox;


    /*
     ***************************************************************************
     *                                                                         *
     * NumberField                                                            *
     *                                                                         *
     **************************************************************************
     */

    //NumberField in the main overview
    @FXML private NumberField pwSizeText;

    //NumberField in the settings
    @FXML private NumberField pwDefaultSizeText;
    

    /*
     ***************************************************************************
     *                                                                         *
     * Layout Container                                                       *
     *                                                                         *
     **************************************************************************
     */

    //main container
    //used to move to the settings
    @FXML private VBox mainContainer;


    /*
     ***************************************************************************
     *                                                                         *
     * Other parts                                                            *
     *                                                                         *
     **************************************************************************
     */

    //output field
    @FXML private Label generatedPW;



	@Override
    synchronized public void initialize(URL location, ResourceBundle bundle) {
	    //set bundle
        this.bundle = bundle;

	    //retrieve user data from the dataHandler
		UserDataSet userData = dataHandler.getSettings();
		
		//only one read to store in temporary var until everything is set
		String lengthOfPw = userData.read(UserDataTypes.lengthOfPw);
		
		//set password size slider and text
		pwSize.setValue(Integer.parseInt(lengthOfPw));
    	pwSizeText.setText(lengthOfPw);
    	
    	//set the password size slider and text for the settings tab
    	pwDefaultSize.setValue(Integer.parseInt(lengthOfPw));
    	pwDefaultSizeText.setText(lengthOfPw);
    	
    	//set the current language in the comboBox in the settings tab
    	languageList.setValue(userData.read(UserDataTypes.languageName));

    	//set theme in settings tab
        themeList.setValue(userData.read(UserDataTypes.userTheme));

        //get all CheckBox settings
        Map<SequenceClassTypes, Boolean> map = userData.CheckBoxSettings();

        //set all the checkboxes according to the user specified settings
        lowerCaseSettingBox.setSelected(map.get(SequenceClassTypes.lowerCaseLetters));
        upperCaseSettingBox.setSelected(map.get(SequenceClassTypes.upperCaseLetters));
        numberSettingBox.setSelected(map.get(SequenceClassTypes.numbers));
        specialCharSettingBox.setSelected(map.get(SequenceClassTypes.specialChars));
        excludeConfusingCharSettingBox.setSelected(map.get(SequenceClassTypes.excludeConfusingChars));


        /*
        ========== Create PWGen object to generate passwords ==========
        */

        //create sequence list to store the activated sequences
        HashSet<SequenceClassTypes> sequenceList = new HashSet<>();

        //loops trough all the CheckBox settings and add them to the sequence list if they are set to true
        for(SequenceClassTypes elem: SequenceClassTypes.values()) {
            if(map.get(elem)) {
                sequenceList.add(elem);
            }
        }

        //initiate generator with sequence list
        generator = new PWGen(sequenceList);



        /*
         ***************************************************************************
         *                                                                         *
         * Actions                                                                *
         *                                                                         *
         **************************************************************************
         */

        /*
        ========== GenerateBtn ==========
        */
    	//generates the password
    	generateBtn.setOnAction((event) -> {

            //check if min input is valid as there is no check for the minimum limitation in the LimitedNumberField
            if(pwSizeText.getText().matches("(^|[0-3])")) {
                //set slider and the LimitedNumberField to the minimum value (4)
                pwSizeText.setText("4");
                pwSize.setValue(4.0);
            }

            //gets slider size (decimal cast to int) and generates password
            //generated password gets set to the Text area
            generatedPW.setText(generator.generatePassword((int)pwSize.getValue()));

            //reset copy button
            copyBtn.setText(bundle.getString("lang.copyBtn"));

            //remove style class
            copyBtn.getStyleClass().remove("activated");
    	});

        /*
        ========== CancelBtn ==========
        */
        //Cancels save of the settings and Resets the GUI
    	cancelBtn.setOnAction((event) -> parent.loadGUI());

        /*
        ========== SaveBtn ==========
        */
    	//saves all settings and reloads the GUI to take affect
    	saveBtn.setOnAction((event) -> {

    	    //save and check if save of the settings was successful
            if(!dataHandler.saveAll(new UserDataSet(dataHandler.getLanguage(languageList.getValue()),
                                                        pwDefaultSizeText.getText(),
                                                        themeList.getValue(),
                                                        lowerCaseSettingBox.isSelected(),
                                                        upperCaseSettingBox.isSelected(),
                                                        numberSettingBox.isSelected(),
                                                        specialCharSettingBox.isSelected(),
                                                        excludeConfusingCharSettingBox.isSelected()
                                                        )))
            {
                //alert if saving of the settings wasn't successful
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(bundle.getString("lang.invalidSettingsAlertTitle"));
                alert.setHeaderText(bundle.getString("lang.invalidSettingsAlertText"));
                alert.showAndWait();
            }
            else {
                //saving was successful
                //reload the GUI
                parent.loadGUI();
            }
    	});

        /*
        ========== CopyBtn ==========
        */
    	//copies password on click
    	copyBtn.setOnAction((event) -> {
    	    //check if a password has been generated yet
            if(!generatedPW.getText().equals("")) {

                //set content
                content.putString(generatedPW.getText());

                //set the content to the clipboard
                clipboard.setContent(content);

                //set the copyBtn to copied
                copyBtn.setText(bundle.getString("lang.copiedBtn"));

                //add style class
                copyBtn.getStyleClass().add("activated");
            }
    	});

        /*
        ========== settingsTabBtn ==========
        */
        //opens settingsTab by moving the container up
        settingsTabBtn.setOnMouseClicked((event) -> {
            //init timeline for the animation
            final Timeline timeline = new Timeline();

            //determine if the settings menu is expanded (Y-Property == -385) or if it isn't (Y-Property == 0)
            double toValue = (mainContainer.getLayoutY() < 0) ? 0 : -385;

            //generate keyframe with duration of the animation and the positions
            final KeyFrame kf = new KeyFrame(Duration.millis(400), new KeyValue(mainContainer.layoutYProperty(), toValue));

            //add keyframe and play animation
            timeline.getKeyFrames().add(kf);
            timeline.play();
        });


        /*
         ***************************************************************************
         *                                                                         *
         * Listeners                                                              *
         *                                                                         *
         **************************************************************************
         */

        /*
        ========== pwSizeText (LimitedNumberField in the main overview) ==========
        */
        //listens if the current value is allowed or if it's lower
        pwSizeText.textProperty().addListener((obs, oldVal, newVal) ->  {
            //check if the input value isn't less than 4 or nothing
            if(!newVal.matches("(^|[0-3])")) {
                pwSize.setValue(Double.parseDouble(pwSizeText.getText()));
            }
        });

        /*
        ========== pwDefaultSizeText (LimitedNumberField in the settingsTab) ==========
        */
        //listens if the current value is allowed or if it's lower
        pwDefaultSizeText.textProperty().addListener((obs, oldVal, newVal) ->  {
            //check if the input value isn't less than 4 or nothing
            if(!newVal.matches("(^|[0-3])")) {
                pwDefaultSize.setValue(Double.parseDouble(pwDefaultSizeText.getText()));
            }
        });

        /*
        ========== pwSize (Slider in the main overview) ==========
        */
        //prevents slider from setting decimal numbers --> automatically rounds and sets value in the LimitedNumberField as well
    	pwSize.valueProperty().addListener((obs, oldVal, newVal) ->  {
    	    pwSize.setValue(Math.round(newVal.doubleValue()));
            pwSizeText.setText("" + Math.round(newVal.doubleValue()));
        });

        /*
        ========== pwDefaultSize (Slider in the settingsTab) ==========
        */
        //prevents slider from setting decimal numbers --> automatically rounds and sets value in the LimitedNumberField as well
    	pwDefaultSize.valueProperty().addListener((obs, oldVal, newVal) -> {
    	    pwDefaultSize.setValue(Math.round(newVal.doubleValue()));
            pwDefaultSizeText.setText("" + Math.round(newVal.doubleValue()));
        });


    }

    /**
     * Resets copyBtn if the password isn't in the clipboard anymore
     * Only there for the stage focus listener in the Main class
     */
    public void resetCopyBtn() {
        if (!generatedPW.getText().equals(clipboard.getString())) {
            copyBtn.setText(bundle.getString("lang.copyBtn"));

            //remove style class
            copyBtn.getStyleClass().remove("activated");
        }
    }

    /**
     * Sets the Main class as parent of the controller
     * @param parent given Main class
     */
    public void setParent(Main parent) {
        this.parent = parent;
    }
}

