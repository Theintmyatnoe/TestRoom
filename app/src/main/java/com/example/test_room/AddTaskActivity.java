package com.example.test_room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    private EditText edtname, edtphone, edtemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_role);

        edtname = findViewById(R.id.editTextTask);
        edtphone = findViewById(R.id.editTextDesc);
        edtemail = findViewById(R.id.editTextFinishBy);

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });
    }

    private void saveTask() {
        final String sname = edtname.getText().toString().trim();
        final String sphone = edtphone.getText().toString().trim();
        final String semail = edtemail.getText().toString().trim();

        if (sname.isEmpty()) {
            edtname.setError("Name required");
            edtname.requestFocus();
            return;
        }

        if (sphone.isEmpty()) {
            edtphone.setError("Phone required");
            edtphone.requestFocus();
            return;
        }
        else if(!android.util.Patterns.PHONE.matcher(sphone).matches()){
            edtphone.setError("Invalid phone");
            edtphone.requestFocus();
            return;
        }

        if (semail.isEmpty()) {
            edtemail.setError("Finish by required");
            edtemail.requestFocus();
            return;
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(semail).matches()){
            edtemail.setError("Email is not valid");
            edtemail.requestFocus();
            return;
        }

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                Task task = new Task();
                task.setName(sname);
                task.setPhone(sphone);
                task.setEmail(semail);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .insert(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

}