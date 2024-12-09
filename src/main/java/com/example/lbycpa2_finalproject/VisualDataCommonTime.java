package com.example.lbycpa2_finalproject;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.MenuItem;

import static com.example.lbycpa2_finalproject.Main.scheduleLength;

public class VisualDataCommonTime {
    private CompiledSchedule compiledSchedule;
    @FXML
    private VBox mondayVBox = new VBox();
    @FXML
    private VBox tuesdayVBox = new VBox();
    @FXML
    private VBox wednesdayVBox = new VBox();
    @FXML
    private VBox thursdayVBox = new VBox();
    @FXML
    private VBox fridayVBox = new VBox();
    @FXML
    private VBox saturdayVBox = new VBox();
    @FXML
    private MenuButton panelistMenu;

    private String[] listOfPaneLlists;
    private String[][] panelists;
    private int[] monday;
    private int[] tuesday;
    private int[] wednesday;
    private int[] thursday;
    private int[] friday;
    private int[] saturday;
    private int[] schedule = {600, 615, 630, 645, 700, 715, 730, 745, 800, 815, 830, 845, 900, 915,
            930, 945, 1000, 1015, 1030, 1045, 1100, 1115, 1130, 1145, 1200, 1215, 1230, 1245, 1300,
            1315, 1330, 1345, 1400, 1415, 1430, 1445, 1500, 1515, 1530, 1545, 1600, 1615, 1630, 1645, 1700,
            1715, 1730, 1745, 1800, 1815, 1830, 1845, 1900, 1915, 1930, 1945, 2000, 2015, 2030, 2045, 2100, 2115, 2130, 2145, 2200};


    public VisualDataCommonTime() {
        compiledSchedule = new CompiledSchedule();
        monday = compiledSchedule.getMondayCommonTime();
        tuesday = compiledSchedule.getTuesdayCommonTime();
        wednesday = compiledSchedule.getWednesdayCommonTime();
        thursday = compiledSchedule.getThursdayCommonTime();
        friday = compiledSchedule.getFridayCommonTime();
        saturday = compiledSchedule.getSaturdayCommonTime();
    }

    @FXML
    private void Refresh() {
        mondayVBox.getChildren().clear();
        tuesdayVBox.getChildren().clear();
        wednesdayVBox.getChildren().clear();
        thursdayVBox.getChildren().clear();
        fridayVBox.getChildren().clear();
        saturdayVBox.getChildren().clear();
        System.out.println("Pressed");

        // Get selected panelists
        List<String> selectedPanelists = panelistMenu.getItems().stream()
                .filter(item -> item instanceof CheckMenuItem && ((CheckMenuItem) item).isSelected())
                .map(MenuItem::getText)
                .collect(Collectors.toList());
        System.out.println("Selected Panelists: " + selectedPanelists); // Debugging line
        if (selectedPanelists.isEmpty()) {
            System.out.println("No panelists selected.");
            return;
        }

        // Filter panelists array to include only selected panelists
        List<String[]> filteredPanelists = Arrays.stream(panelists)
                .filter(panelist -> selectedPanelists.contains(panelist[0]))
                .collect(Collectors.toList());
        // Convert filtered panelists to array for processing
        String[][] selectedPanelistsArray = filteredPanelists.toArray(new String[0][]);

        // Algorithm for giving an array of integers that shows time busy of selected panelists
        int[][] timeBusy = new int[selectedPanelistsArray.length][scheduleLength];

        for (int i = 0; i < selectedPanelistsArray.length; i++) {
            String[] panelist = selectedPanelistsArray[i];
            String day = panelist[1];
            String[] timeRange = panelist[2].split("-");  // Time range (e.g., 1230-1300)
            int beginningOfTimeRange = Integer.parseInt(timeRange[0]);
            int endOfTimeRange = Integer.parseInt(timeRange[1]);

            for (int j = 0; j < schedule.length - 1; j++) {
                // Check if the current interval falls within the time range
                if (schedule[j] >= beginningOfTimeRange && schedule[j + 1] <= endOfTimeRange) {
                    if (day.contains("M")) {  // If day contains Monday
                        timeBusy[i][j] = 1; // Mark as busy for Monday
                    }
                    if (day.contains("T")) {  // If day contains Tuesday
                        timeBusy[i][j] = 1; // Mark as busy for Tuesday
                    }
                    if (day.contains("W")) {  // If day contains Wednesday
                        timeBusy[i][j] = 1; // Mark as busy for Wednesday
                    }
                    if (day.contains("H")) {  // If day contains Thursday
                        timeBusy[i][j] = 1; // Mark as busy for Thursday
                    }
                    if (day.contains("F")) {  // If day contains Friday
                        timeBusy[i][j] = 1; // Mark as busy for Friday
                    }
                    if (day.contains("S")) {  // If day contains Saturday
                        timeBusy[i][j] = 1; // Mark as busy for Saturday
                    }
                }
            }
        }

        // Update common time for each day based on selected panelists
        compiledSchedule.setMondayCommonTime(commonTimeSchedule(getDaySpecificSchedule("M", timeBusy, selectedPanelistsArray)));
        compiledSchedule.setTuesdayCommonTime(commonTimeSchedule(getDaySpecificSchedule("T", timeBusy, selectedPanelistsArray)));
        compiledSchedule.setWednesdayCommonTime(commonTimeSchedule(getDaySpecificSchedule("W", timeBusy, selectedPanelistsArray)));
        compiledSchedule.setThursdayCommonTime(commonTimeSchedule(getDaySpecificSchedule("H", timeBusy, selectedPanelistsArray)));
        compiledSchedule.setFridayCommonTime(commonTimeSchedule(getDaySpecificSchedule("F", timeBusy, selectedPanelistsArray)));
        compiledSchedule.setSaturdayCommonTime(commonTimeSchedule(getDaySpecificSchedule("S", timeBusy, selectedPanelistsArray)));
        System.out.println("Test Case 1");
        System.out.println(Arrays.toString(compiledSchedule.getTuesdayCommonTime()));
        System.out.println(Arrays.toString(compiledSchedule.getFridayCommonTime()));
        compiledSchedule.setMondayThursdayCommonTime(commonTimeSchedule(getDaySpecificSchedule("MH", timeBusy, selectedPanelistsArray)));
        compiledSchedule.setTuesdayFridayCommonTime(commonTimeSchedule(getDaySpecificSchedule("TF", timeBusy, selectedPanelistsArray)));
        System.out.println("Test Case 2");
        System.out.println(Arrays.toString(compiledSchedule.getTuesdayCommonTime()));
        System.out.println(Arrays.toString(compiledSchedule.getFridayCommonTime()));
        // Refresh visual data
        monday = compiledSchedule.getMondayCommonTime();
        tuesday = compiledSchedule.getTuesdayCommonTime();
        wednesday = compiledSchedule.getWednesdayCommonTime();
        thursday = compiledSchedule.getThursdayCommonTime();
        friday = compiledSchedule.getFridayCommonTime();
        saturday = compiledSchedule.getSaturdayCommonTime();
        boxCreator(monday, mondayVBox);
        boxCreator(tuesday, tuesdayVBox);
        boxCreator(wednesday, wednesdayVBox);
        boxCreator(thursday, thursdayVBox);
        boxCreator(friday, fridayVBox);
        boxCreator(saturday, saturdayVBox);
    }


    public void setPanelistsName(String[] listOfPanelists) {
        Arrays.sort(listOfPanelists);
        panelistMenu.getItems().clear();
        this.listOfPaneLlists = listOfPanelists;
        for (String panelist : listOfPanelists) {
            CheckMenuItem checkMenuItem = new CheckMenuItem(panelist);
            panelistMenu.getItems().add(checkMenuItem);
        }
    }

    private int[][] getDaySpecificSchedule(String day, int[][] timeBusy, String[][] panelistsArray) {
        int[][] specificDaySchedule = new int[panelistsArray.length][scheduleLength];
        for (int i = 0; i < panelistsArray.length; i++) {
            if (panelistsArray[i][1].equals(day)) {
                System.arraycopy(timeBusy[i], 0, specificDaySchedule[i], 0, scheduleLength);
            }
        }
        return specificDaySchedule;
    }


    private void boxCreator(int[] day, VBox dayVBox) {
        dayVBox.getChildren().clear(); // Clear previous content

        int intervalHeight = 20; // Height for each 15-minute interval
        int blockWidth = 100;   // Fixed width for rectangles
        int accumulatedHeight = 0; // Track accumulated height for precise alignment

        for (int i = 0; i < day.length; i++) {
            // Create HBox for the time interval
            HBox timeBlock = new HBox();
            timeBlock.setSpacing(10);
            timeBlock.setStyle("-fx-alignment: CENTER_LEFT;");

            // Create rectangle for the time block
            Rectangle rectangle = new Rectangle();
            rectangle.setWidth(blockWidth);
            rectangle.setHeight(intervalHeight);
            rectangle.setArcWidth(10);
            rectangle.setArcHeight(10);

            // Set rectangle color based on availability
            rectangle.setFill(day[i] == 1 ? Color.web("#8AC7AD") : Color.web("#F75A3B"));
            rectangle.setOpacity(0.7);

            // Add time label and rectangle to the HBox
            timeBlock.getChildren().addAll(rectangle);

            // Add the HBox to the VBox
            dayVBox.getChildren().add(timeBlock);

            // Increment accumulated height
            accumulatedHeight += intervalHeight;

            // Add a separator line every 60 minutes (4 intervals of 15 minutes)
            if ((i + 1) % 4 == 0) {
                Rectangle separator = new Rectangle();
                separator.setWidth(blockWidth + 80); // Align with time label and rectangle
                separator.setHeight(1); // Line thickness
                separator.setVisible(false); // Line color
                separator.setOpacity(0.5);

                // Use accumulatedHeight to align the separator precisely
                VBox.setMargin(separator, new Insets(0, 0, -0.5, 0)); // Compensate for floating-point inaccuracies
                dayVBox.getChildren().add(separator);
            }
        }
    }


    private int[] commonTimeSchedule(int[][] timeBusy){
        int[] commonAvailability = new int[65];
        for (int j = 0; j < 65; j++) {
            boolean allAvailable = true;
            for (int i = 0; i < timeBusy.length; i++) {
                if (timeBusy[i][j] == 1) { // Busy
                    allAvailable = false;
                    break;
                }
            }
            commonAvailability[j] = allAvailable ? 1 : 0;
        }
        return commonAvailability;
    }

    public CompiledSchedule getCompiledSchedule() {
        return compiledSchedule;
    }

    public void setCompiledSchedule(CompiledSchedule compiledSchedule) {
        this.compiledSchedule = compiledSchedule;
    }

    public String[] getPanelistsName() {
        return listOfPaneLlists;
    }

    public void setPanelists(String[][] panelists){
        this.panelists = panelists;
    }

}
