package com.georgiancollege.test1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {
    @FXML
    private TableView<Employee> tableView;

    @FXML
    private TableColumn<Employee, Integer> employeeIdColumn;

    @FXML
    private TableColumn<Employee, String> firstNameColumn;

    @FXML
    private TableColumn<Employee, String> lastNameColumn;

    @FXML
    private TableColumn<Employee, String> addressColumn;

    @FXML
    private TableColumn<Employee, String> cityColumn;

    @FXML
    private TableColumn<Employee, String> provinceColumn;

    @FXML
    private TableColumn<Employee, String> phoneColumn;

    @FXML
    private CheckBox ontarioOnlyCheckBox;

    @FXML
    private ComboBox<String> areaCodeComboBox;

    @FXML
    private Label noOfEmployeesLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        areaCodeComboBox.getItems().add("All");
    }

    @FXML
    void ontarioOnlyCheckBox_OnClick(ActionEvent event) {

    }

    @FXML
    void areaCodeComboBox_OnClick(ActionEvent event) {

    }
}
