package bastien.todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.DateFormat;
import java.util.Calendar;

import bastien.todolist.Data.Task;
import bastien.todolist.Database.TaskDAO;

public class EditTaskActivity extends AppCompatActivity {

    TaskDAO database;
    int user_id, id;

    SharedPreferences sharedPreferences;

    MaterialEditText titre, description;
    MaterialButton dateLimite,heureLimite;

    DatePickerDialog datePickerDialog;
    TimePickerDialog heurePickerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        database = new TaskDAO(getApplicationContext());

        id = getIntent().getIntExtra("id", 0);
        String old_Titre = getIntent().getStringExtra("titre");
        String old_Description = getIntent().getStringExtra("description");
        String old_Date = getIntent().getStringExtra("dateLimite");
        String old_Heure = getIntent().getStringExtra("heureLimite");


        titre = findViewById(R.id.add_task_titre);
        description = findViewById(R.id.add_task_description);
        dateLimite = findViewById(R.id.add_task_dateLimite);
        heureLimite = findViewById(R.id.add_task_heureLimite);
        //Modification de la date
        dateLimite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                String dateActuelle = DateFormat.getDateInstance().format(c.getTime());
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                datePickerDialog = new DatePickerDialog(EditTaskActivity.this, R.style.MyDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dateLimite.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        //Modification de l'heure
        heureLimite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHoure = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                heurePickerDialog = new TimePickerDialog(EditTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        heureLimite.setText("  "+hourOfDay + ":" + minute);
                    }
                }, mHoure, mMinute, true);

                heurePickerDialog.show();
            }
        });

        ((MaterialEditText) findViewById(R.id.add_task_description)).setText(old_Description);
        ((MaterialButton) findViewById(R.id.add_task_dateLimite)).setText(old_Date);
        ((MaterialEditText) findViewById(R.id.add_task_titre)).setText(old_Titre);
        ((MaterialButton) findViewById(R.id.add_task_heureLimite)).setText(old_Heure);
    }

    // Ici, on ajoute pas une tâche mais on l'a modifie
    public void AddTask(View view) {

        String Titre = titre.getText().toString();
        String Description = description.getText().toString();
        String DateLimite = dateLimite.getText().toString();
        String HeureLimite = heureLimite.getText().toString();

        if (Titre.equals("") || Description.equals("")) {
            Toast.makeText(getApplicationContext(), "Remplissez tous les champs !", Toast.LENGTH_SHORT).show();

        } else {


            Task t = new Task(user_id, Titre, Description, DateLimite, HeureLimite);
//            Toast.makeText(getApplicationContext(), t.getTitre() + " " + t.getDescription() + " " + t.getDateLimite(), Toast.LENGTH_SHORT).show();
            // Ici on set l'id car il a été définie par la bdd
            t.setId(id);
            Toast.makeText(getApplicationContext(), "Task id" + String.valueOf(t.getId()), Toast.LENGTH_LONG).show();

            int verifModif = database.modifier(t);
//            database.modifier(t);

            if (verifModif > 0) {
                Intent intent = new Intent(this, TaskActivity.class);
                this.startActivity(intent);
                Task t2 = database.getTaskById(t.getId());

                Toast.makeText(EditTaskActivity.this, "Resultat " + String.valueOf(id) + " " + t2.getDescription(), Toast.LENGTH_LONG).show();
//                taskAdapter.restoreTask(t, position);
//                Toast.makeText(getApplicationContext(), "Modification réussie !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Erreur, la tâche n'a pas été modifiée !" + String.valueOf(id), Toast.LENGTH_SHORT).show();
            }

        }

    }

    // annulation de l'ajout de tâche
    public void cancelTask(View view) {
        Intent intent = new Intent(this, TaskActivity.class);
        this.startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

}
