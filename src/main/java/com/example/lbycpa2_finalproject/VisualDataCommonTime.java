package com.example.lbycpa2_finalproject;

import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    private int [] schedule = {600, 615, 630, 645, 700, 715, 730, 745, 800, 815, 830, 845, 900, 915,
            930, 945, 1000, 1015, 1030, 1045, 1100, 1115, 1130, 1145, 1200, 1215, 1230, 1245, 1300,
            1315, 1330, 1345, 1400, 1415, 1430, 1445, 1500, 1515, 1530, 1545, 1600, 1615, 1630, 1645, 1700,
            1715, 1730, 1745, 1800, 1815, 1830, 1845, 1900, 1915, 1930, 1945, 2000, 2015, 2030, 2045, 2100, 2115, 2130, 2145, 2200};



    public VisualDataCommonTime(){
        compiledSchedule = new CompiledSchedule();
        monday = compiledSchedule.getMondayCommonTime();
        tuesday = compiledSchedule.getTuesdayCommonTime();
        wednesday = compiledSchedule.getWednesdayCommonTime();
        thursday = compiledSchedule.getThursdayCommonTime();
        friday = compiledSchedule.getFridayCommonTime();
        saturday = compiledSchedule.getSaturdayCommonTime();
    }


//    @FXML
//    private void Refresh(){
//        monday = compiledSchedule.getMondayCommonTime();
//        tuesday = compiledSchedule.getTuesdayCommonTime();
//        wednesday = compiledSchedule.getWednesdayCommonTime();
//        thursday = compiledSchedule.getThursdayCommonTime();
//        friday = compiledSchedule.getFridayCommonTime();
//        saturday = compiledSchedule.getSaturdayCommonTime();
//        boxCreator(monday,mondayVBox);
//        boxCreator(tuesday,tuesdayVBox);
//        boxCreator(wednesday,wednesdayVBox);
//        boxCreator(thursday,thursdayVBox);
//        boxCreator(friday,fridayVBox);
//        boxCreator(saturday,saturdayVBox);
//    }

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
            String[] timeRange = panelist[2].split("-");
            int beginningOfTimeRange = Integer.parseInt(timeRange[0]);
            int endOfTimeRange = Integer.parseInt(timeRange[1]);

            for (int j = 0; j < schedule.length; j++) {
                if (schedule[j] >= beginningOfTimeRange && schedule[j] <= endOfTimeRange) {
                    timeBusy[i][j] = 1; // Mark as busy
                } else {
                    timeBusy[i][j] = 0; // Mark as free
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

        System.out.println("Updated Monday: " + Arrays.toString(compiledSchedule.getMondayCommonTime())); // Debugging line

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
        this.listOfPaneLlists = listOfPanelists;
        panelistMenu.getItems().clear();
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

    private void boxCreator(int[] day, VBox dayVBox){
        dayVBox.getChildren().clear();
        int counter = 0;  // Track consecutive intervals
        int previous = -1;  // Track the previous value (initialize to -1 to handle the first value)

        for (int i = 0; i < day.length ; i++) {
            int current = day[i];
            if (current == previous) {

                counter++;
            } else {

                if (previous != -1) {
                    Rectangle rectangle = new Rectangle();
                    rectangle.setWidth(100);
                    rectangle.setHeight(counter * 15);
                    rectangle.setFill(previous == 1 ? Color.GREEN : Color.RED);
                    rectangle.setOpacity(0.5);
                    dayVBox.getChildren().add(rectangle);
                }
                counter = 1;
                previous = current;
            }
        }
        if (previous != -1) {
            Rectangle rectangle = new Rectangle();
            rectangle.setWidth(100);
            rectangle.setHeight(counter * 15);
            rectangle.setFill(previous == 1 ? Color.GREEN : Color.RED);
            rectangle.setOpacity(0.5);
            dayVBox.getChildren().add(rectangle);
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
