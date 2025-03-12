package model.additional;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeePerformance {

    private StringProperty employeeName;
    private IntegerProperty transactionCount;

    public EmployeePerformance() {
        this.employeeName = new SimpleStringProperty();
        this.transactionCount = new SimpleIntegerProperty();
    }

    public String getEmployeeName() {
        return employeeName.get();
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName.set(employeeName);
    }

    public StringProperty employeeNameProperty() {
        return employeeName;
    }

    public int getTransactionCount() {
        return transactionCount.get();
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount.set(transactionCount);
    }

    public IntegerProperty transactionCountProperty() {
        return transactionCount;
    }
}

