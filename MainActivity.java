package com.example.myreport;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Expense Management UI elements
    private EditText expense1NameEditText, expense1ValueEditText;
    private EditText expense2NameEditText, expense2ValueEditText;
    private TextView totalExpenseTextView, expenseDetailsTextView;

    // Report Exporting UI elements
    private Spinner spinnerReportType;
    private RadioGroup radioGroupFormat;
    private Button buttonExport;

    private String selectedReportType;
    private String selectedFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Expense Management UI elements
        expense1NameEditText = findViewById(R.id.expense1_name);
        expense1ValueEditText = findViewById(R.id.expense1_value);
        expense2NameEditText = findViewById(R.id.expense2_name);
        expense2ValueEditText = findViewById(R.id.expense2_value);
        totalExpenseTextView = findViewById(R.id.total_expense);
        expenseDetailsTextView = findViewById(R.id.expense_details);
        Button addUpdateButton = findViewById(R.id.add_update_button);

        // Initialize Report Exporting UI elements
        spinnerReportType = findViewById(R.id.spinnerReportType);
        radioGroupFormat = findViewById(R.id.radioGroupFormat);
        buttonExport = findViewById(R.id.buttonExport);

        // Expense Management: Set Button Click Listener
        addUpdateButton.setOnClickListener(v -> addOrEditExpenses());

        // Report Exporting: Populate Spinner with report types
        String[] reportTypes = {"Daily", "Weekly", "Monthly"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, reportTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReportType.setAdapter(adapter);

        // Handle Spinner selection
        spinnerReportType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedReportType = reportTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedReportType = null;
            }
        });

        // Report Exporting: Set Button Click Listener
        buttonExport.setOnClickListener(v -> exportReport());
    }

    private void addOrEditExpenses() {
        // Expense Management Logic
        String expense1Name = expense1NameEditText.getText().toString();
        String expense1Value = expense1ValueEditText.getText().toString();
        String expense2Name = expense2NameEditText.getText().toString();
        String expense2Value = expense2ValueEditText.getText().toString();

        double totalExpense = 0.0;
        StringBuilder expenseDetails = new StringBuilder();

        try {
            if (!TextUtils.isEmpty(expense1Name) && !TextUtils.isEmpty(expense1Value)) {
                double value1 = Double.parseDouble(expense1Value);
                totalExpense += value1;
                expenseDetails.append(expense1Name).append(": ").append(value1).append("\n");
            }
            if (!TextUtils.isEmpty(expense2Name) && !TextUtils.isEmpty(expense2Value)) {
                double value2 = Double.parseDouble(expense2Value);
                totalExpense += value2;
                expenseDetails.append(expense2Name).append(": ").append(value2).append("\n");
            }

            totalExpenseTextView.setText("Total Expense: " + totalExpense);
            expenseDetailsTextView.setText("Expense Details:\n" + expenseDetails.toString());

            Toast.makeText(this, "Expenses updated successfully!", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers for the amounts.", Toast.LENGTH_SHORT).show();
        }
    }

    private void exportReport() {
        int selectedId = radioGroupFormat.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Please select an export format", Toast.LENGTH_SHORT).show();
            return;
        }

        selectedFormat = ((RadioButton) findViewById(selectedId)).getText().toString();

        if (selectedReportType == null) {
            Toast.makeText(this, "Please select a report type", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mock export action
        Toast.makeText(this, "Exporting " + selectedReportType + " report as " + selectedFormat, Toast.LENGTH_SHORT).show();
    }
}