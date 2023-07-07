package com.example.employeemonthlysalary;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {
    private TextView textView;
    private Button clearButton;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        textView = findViewById(R.id.text_view);
        clearButton = findViewById(R.id.clear_button);

        dbHelper = new DBHelper(this);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearEmployeeDetails();
            }
        });

        displayEmployeeDetails();
    }

    private void displayEmployeeDetails() {
        Cursor cursor = dbHelper.getAllEmployees();

        if (cursor != null && cursor.moveToFirst()) {
            StringBuilder sb = new StringBuilder();

            int employeeNameIndex = cursor.getColumnIndex(DBHelper.COLUMN_EMPLOYEE_NAME);
            int salaryIndex = cursor.getColumnIndex(DBHelper.COLUMN_EMPLOYEE_SALARY);
            int taxIndex = cursor.getColumnIndex(DBHelper.COLUMN_EMPLOYEE_TAX);
            int netSalaryIndex = cursor.getColumnIndex(DBHelper.COLUMN_EMPLOYEE_NETSAL);

            do {
                String employeeName = cursor.getString(employeeNameIndex);
                double salary = cursor.getDouble(salaryIndex);
                double tax = cursor.getDouble(taxIndex);
                double netSalary = cursor.getDouble(netSalaryIndex);

                sb.append("Employee Name: ").append(employeeName).append("\n")
                        .append("Salary: ").append(salary).append("\n")
                        .append("Tax: ").append(tax).append("\n")
                        .append("Net Salary: ").append(netSalary).append("\n\n");
            } while (cursor.moveToNext());

            textView.setText(sb.toString());
        } else {
            textView.setText("No employee data found.");
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    private void clearEmployeeDetails() {
        dbHelper.deleteAllEmployees();
        textView.setText("Employee details cleared.");
    }
}
