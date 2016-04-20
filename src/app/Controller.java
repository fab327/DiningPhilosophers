package app;

import app.Model.Chopstick;
import app.Model.Philosopher;
import app.Utilities.Timer;
import app.Utilities.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public static boolean running;

    @FXML
    private ComboBox<Integer> thinkingTimeSelector;
    @FXML
    private ComboBox<Integer> hungryTimeSelector;
    @FXML
    private ComboBox<Integer> eatingTimeSelector;

    @FXML
    private TextArea logTextArea;

    @FXML
    private TableView tableAvgs;

    //Philosophers
    @FXML
    private ImageView aristotle;
    private Image[] aristotleImgs = new Image[3];
    @FXML
    private ImageView buddha;
    private Image[] buddhaImgs = new Image[3];
    @FXML
    private ImageView russell;
    private Image[] russellImgs = new Image[3];
    @FXML
    private ImageView marx;
    private Image[] marxImgs = new Image[3];
    @FXML
    private ImageView kant;
    private Image[] kantImgs = new Image[3];

    //Chopsticks
    @FXML
    private ImageView chopstick1;
    @FXML
    private ImageView chopstick2;
    @FXML
    private ImageView chopstick3;
    @FXML
    private ImageView chopstick4;
    @FXML
    private ImageView chopstick5;

    //Generate our philosophers and chopsticks
    private Philosopher[] philosophers = new Philosopher[5];
    private Chopstick[] chopsticks = new Chopstick[5];

    //List with average execution times
    ObservableList<Timer> timers = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        thinkingTimeSelector.setItems(generateSelectionValues(5, 15));
        hungryTimeSelector.setItems(generateSelectionValues(5, 15));
        eatingTimeSelector.setItems(generateSelectionValues(10, 20));

        aristotleImgs[0] = new Image(getClass().getResourceAsStream("img/Aristotle-Thinking.png"));
        aristotleImgs[1] = new Image(getClass().getResourceAsStream("img/Aristotle-Hungry.png"));
        aristotleImgs[2] = new Image(getClass().getResourceAsStream("img/Aristotle-Eating.png"));

        buddhaImgs[0] = new Image(getClass().getResourceAsStream("img/Buddha-Thinking.png"));
        buddhaImgs[1] = new Image(getClass().getResourceAsStream("img/Buddha-Hungry.png"));
        buddhaImgs[2] = new Image(getClass().getResourceAsStream("img/Buddha-Eating.png"));

        russellImgs[0] = new Image(getClass().getResourceAsStream("img/Russell-Thinking.png"));
        russellImgs[1] = new Image(getClass().getResourceAsStream("img/Russell-Hungry.png"));
        russellImgs[2] = new Image(getClass().getResourceAsStream("img/Russell-Eating.png"));

        marxImgs[0] = new Image(getClass().getResourceAsStream("img/Marx-Thinking.png"));
        marxImgs[1] = new Image(getClass().getResourceAsStream("img/Marx-Hungry.png"));
        marxImgs[2] = new Image(getClass().getResourceAsStream("img/Marx-Eating.png"));

        kantImgs[0] = new Image(getClass().getResourceAsStream("img/Kant-Thinking.png"));
        kantImgs[1] = new Image(getClass().getResourceAsStream("img/Kant-Hungry.png"));
        kantImgs[2] = new Image(getClass().getResourceAsStream("img/Kant-Eating.png"));

        for (int i = 0; i < 5; i++) {
            chopsticks[i] = new Chopstick(i, logTextArea);
        }
        assignChopstickToItsView();

        //create columns for averages
        TableColumn<Timer, String> philosopherColumn = new TableColumn<Timer, String>("Philosopher");
        philosopherColumn.setCellValueFactory(new PropertyValueFactory("philosopherName"));

        TableColumn<Timer, Double> avgThinkingColumn = new TableColumn<Timer, Double>("Avg. Thinking");
        avgThinkingColumn.setCellValueFactory(new PropertyValueFactory("averageThinkingTime"));

        TableColumn<Timer, Double> avgEatingColumn = new TableColumn<Timer, Double>("Avg. Eating");
        avgEatingColumn.setCellValueFactory(new PropertyValueFactory("averageEatingTime"));

        TableColumn<Timer, Double> avgHungerColumn = new TableColumn<Timer, Double>("Avg. Hungry");
        avgHungerColumn.setCellValueFactory(new PropertyValueFactory("averageHungryTime"));

        tableAvgs.getColumns().setAll(philosopherColumn, avgThinkingColumn, avgEatingColumn, avgHungerColumn);
        tableAvgs.setItems(timers);

        timers.add(0, new Timer("Aristotle"));
        timers.add(1, new Timer("Buddha"));
        timers.add(2, new Timer("Russel"));
        timers.add(3, new Timer("Marx"));
        timers.add(4, new Timer("Kant"));

        //Create philosophers
        philosophers[0] = new Philosopher(chopsticks[0], chopsticks[1], 0, "aristotle", aristotle, aristotleImgs[0], aristotleImgs[1], aristotleImgs[2], logTextArea, new Timer(), timers);
        philosophers[1] = new Philosopher(chopsticks[1], chopsticks[2], 1, "buddha", buddha, buddhaImgs[0], buddhaImgs[1], buddhaImgs[2], logTextArea, new Timer(), timers);
        philosophers[2] = new Philosopher(chopsticks[2], chopsticks[3], 2, "russel", russell, russellImgs[0], russellImgs[1], russellImgs[2], logTextArea, new Timer(), timers);
        philosophers[3] = new Philosopher(chopsticks[3], chopsticks[4], 3, "marx", marx, marxImgs[0], marxImgs[1], marxImgs[2], logTextArea, new Timer(), timers);
        philosophers[4] = new Philosopher(chopsticks[4], chopsticks[0], 4, "kant", kant, kantImgs[0], kantImgs[1], kantImgs[2], logTextArea, new Timer(), timers);
    }

    private ObservableList<Integer> generateSelectionValues(int lowerBound, int higherBound) {
        ObservableList<Integer> options = FXCollections.observableArrayList();
        for (int i = lowerBound; i <= higherBound; i++) {
            options.add(i);
        }

        return options;
    }

    public void startAction(ActionEvent actionEvent) {
        System.out.println("Started Simulation");
        logTextArea.appendText("Started Simulation \n");
        running = true;

        overrideDefaultValuesWhereNecessary();

        for (Philosopher p : philosophers) {
            new Thread(p).start();
        }
    }

    public void stopAction(ActionEvent actionEvent) {
        System.out.println("Stopped Simulation");
        logTextArea.appendText("Stopped Simulation, wait for each philosopher to finish \n");
        running = false;
    }

    private void assignChopstickToItsView() {
        chopsticks[0].setChopstickView(chopstick5);
        chopsticks[1].setChopstickView(chopstick1);
        chopsticks[2].setChopstickView(chopstick2);
        chopsticks[3].setChopstickView(chopstick3);
        chopsticks[4].setChopstickView(chopstick4);
    }

    private void overrideDefaultValuesWhereNecessary() {
        Object thinking = thinkingTimeSelector.getValue();
        Object hungry = hungryTimeSelector.getValue();
        Object eating = eatingTimeSelector.getValue();

        if (thinking != null) {
            int thinkingValue = Integer.parseInt(thinking.toString());
            Utils.setThinkingBoundary(thinkingValue);
        }
        if (hungry != null) {
            int hungryValue = Integer.parseInt(hungry.toString());
            Utils.setHungryBoundary(hungryValue);
        }
        if (eating != null) {
            int eatingValue = Integer.parseInt(eating.toString());
            Utils.setEatingBoundary(eatingValue);
        }
    }

}
