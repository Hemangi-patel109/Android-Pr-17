package com.example.studentelectives; import android.annotation.SuppressLint; import android.database.Cursor;
import android.os.Bundle; import android.widget.*;
import androidx.appcompat.app.AppCompatActivity; import java.util.ArrayList;
public class MainActivity extends AppCompatActivity { EditText editName, editEnrollment, editEmail; RadioGroup radioGroup;
Button btnSubmit, btnUpdate, btnDelete; ListView listView;
DatabaseHelper databaseHelper; ArrayAdapter<String> adapter; ArrayList<String> studentList;
@SuppressLint("MissingInflatedId") @Override
protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main); editName = findViewById(R.id.editName);
editEnrollment = findViewById(R.id.editEnrollment); editEmail = findViewById(R.id.editEmail); radioGroup = findViewById(R.id.radioGroup); btnSubmit = findViewById(R.id.btnSubmit); btnUpdate = findViewById(R.id.btnUpdate);
btnDelete = findViewById(R.id.btnDelete); listView = findViewById(R.id.listView);
databaseHelper = new DatabaseHelper(this); studentList = new ArrayList<>();
adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList); listView.setAdapter(adapter);
btnSubmit.setOnClickListener(view -> {

submitData(); loadData();
});
btnUpdate.setOnClickListener(view -> { updateData();
loadData();
});
btnDelete.setOnClickListener(view -> { deleteData();
loadData();
});
loadData(); // Load data when app starts
}
private void submitData() {
String name = editName.getText().toString();
String enrollment = editEnrollment.getText().toString(); String email = editEmail.getText().toString();
String elective = getSelectedElective();
if (name.isEmpty() || enrollment.isEmpty() || email.isEmpty() || elective.isEmpty()) { Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show(); return;
}
boolean result = databaseHelper.insertData(name, enrollment, email, elective); Toast.makeText(this, result ? "Data Inserted" : "Error in inserting data",
Toast.LENGTH_SHORT).show();
}
private void updateData() {
String name = editName.getText().toString();
String enrollment = editEnrollment.getText().toString(); String email = editEmail.getText().toString();
String elective = getSelectedElective();
if (name.isEmpty() || enrollment.isEmpty() || email.isEmpty() || elective.isEmpty()) { Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show(); return;
}
boolean result = databaseHelper.updateData(enrollment, name, email, elective); Toast.makeText(this, result ? "Data Updated" : "Error in updating data",
Toast.LENGTH_SHORT).show();
}
private void deleteData() {
String enrollment = editEnrollment.getText().toString(); if (enrollment.isEmpty()) {
Toast.makeText(this, "Enter Enrollment No", Toast.LENGTH_SHORT).show(); return;
}
boolean result = databaseHelper.deleteData(enrollment);

Toast.makeText(this, result ? "Data Deleted" : "Error in deleting data", Toast.LENGTH_SHORT).show();
}
private void loadData() { studentList.clear();
Cursor cursor = databaseHelper.getAllData();
if (cursor.getCount() == 0) {
studentList.add("No Records Found");
} else {
while (cursor.moveToNext()) {
String student = "Name: " + cursor.getString(1) + "\nEnrollment: " + cursor.getString(2) + "\nEmail: " + cursor.getString(3) +
"\nElective: " + cursor.getString(4); studentList.add(student);
}
}
adapter.notifyDataSetChanged(); cursor.close();
}
private String getSelectedElective() {
int selectedId = radioGroup.getCheckedRadioButtonId(); if (selectedId == -1) return "";
RadioButton selectedRadioButton = findViewById(selectedId); return selectedRadioButton.getText().toString();
}
}
