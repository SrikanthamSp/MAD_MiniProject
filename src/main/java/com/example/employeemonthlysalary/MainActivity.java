package com.example.employeemonthlysalary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText ed1, ed2, ed3, ed4;
    Button btn1, btn2, btnView;
    DBHelper dbHelper; // Declare the DBHelper object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this); // Instantiate the DBHelper object

        ed1 = findViewById(R.id.empsal);
        ed2 = findViewById(R.id.emptax);
        ed3 = findViewById(R.id.netsal);
        ed4 = findViewById(R.id.empname);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btnView = findViewById(R.id.btnView);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed1.setText("");
                ed2.setText("");
                ed3.setText("");
                ed4.setText("");
                ed4.requestFocus();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double salary = Double.parseDouble(ed1.getText().toString());
                double tax;

                if (salary > 50000) {
                    tax = salary * 13 / 100;
                } else if (salary > 30000) {
                    tax = salary * 7 / 100;
                } else {
                    tax = 0;
                }

                ed2.setText(String.valueOf(tax));
                double netSalary = salary - tax;
                ed3.setText(String.valueOf(netSalary));

                // Convert double values to String
                String employeeName = ed4.getText().toString();
                String salaryString = String.valueOf(salary);
                String taxString = String.valueOf(tax);
                String netSalaryString = String.valueOf(netSalary);

                // Generate a unique employee ID
                long employeeId = System.currentTimeMillis();

                // Insert employee data into the database
                dbHelper.insertEmployee(employeeName, salary, tax, netSalary);

                Toast.makeText(MainActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
            }
        });




        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                startActivity(intent);
            }
        });
    }
}
