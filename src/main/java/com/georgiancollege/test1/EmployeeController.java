import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    private ObservableList<Employee> employees;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String DB_USERNAME = "your_username";
    private static final String DB_PASSWORD = "your_password";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the table columns
        employeeIdColumn.setCellValueFactory(cellData -> cellData.getValue().employeeIdProperty().asObject());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        cityColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        provinceColumn.setCellValueFactory(cellData -> cellData.getValue().provinceProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());

        // Initialize the area code combo box
        areaCodeComboBox.getItems().addAll("All", "416", "905"); // Example area codes

        // Load employee data from the database
        loadDataFromDatabase();

        // Update the table view with the loaded data
        updateTableView();
    }

    @FXML
    void ontarioOnlyCheckBox_OnClick(ActionEvent event) {
        updateTableView();
    }

    @FXML
    void areaCodeComboBox_OnClick(ActionEvent event) {
        updateTableView();
    }

    private void loadDataFromDatabase() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");

            employees = FXCollections.observableArrayList();
            while (resultSet.next()) {
                int employeeId = resultSet.getInt("employee_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String province = resultSet.getString("province");
                String phone = resultSet.getString("phone");

                Employee employee = new Employee(employeeId, firstName, lastName, address, city, province, phone);
                employees.add(employee);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTableView() {
        // Apply filters if enabled
        ObservableList<Employee> filteredEmployees = employees;
        if (ontarioOnlyCheckBox.isSelected()) {
            filteredEmployees = filteredEmployees.filtered(employee -> employee.getProvince().equals("ON"));
        }
        String selectedAreaCode = areaCodeComboBox.getValue();
        if (!selectedAreaCode.equals("All")) {
            filteredEmployees = filteredEmployees.filtered(employee -> employee.getPhone().startsWith(selectedAreaCode));
        }

        // Update the table view with the filtered data
        tableView.setItems(filteredEmployees);

        // Update the label showing the number of employees
        noOfEmployeesLabel.setText("Total Employees: " + filteredEmployees.size());
    }
}
