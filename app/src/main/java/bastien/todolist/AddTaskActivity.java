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
import java.text.SimpleDateFormat;
import java.util.Calendar;

import bastien.todolist.Data.Task;
import bastien.todolist.Database.TaskDAO;


public class AddTaskActivity extends AppCompatActivity {

    TaskDAO database;
    int user_id;

    SharedPreferences sharedPreferences;

    MaterialEditText titre, description;
    MaterialButton dateLimite, heureLimite;

    DatePickerDialog datePickerDialog;
    TimePickerDialog heurePickerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        database = new TaskDAO(getApplicationContext());

        // on charge les données de l'utilisateur
        sharedPreferences = getApplicationContext().getSharedPreferences("user", 0);
        user_id = sharedPreferences.getInt("userId", 0);

        titre = findViewById(R.id.add_task_titre);
        description = findViewById(R.id.add_task_description);

        dateLimite = findViewById(R.id.add_task_dateLimite);
        heureLimite = findViewById(R.id.add_task_heureLimite);

        Calendar  calendar = Calendar.getInstance();

        String dateActuelle = new SimpleDateFormat("MM/DD/YYY").format(calendar.getTime());

        String heureActuelle = new SimpleDateFormat("HH:mm").format(calendar.getTime());
        dateLimite.setText(dateActuelle);
        heureLimite.setText(heureActuelle);

        dateLimite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(AddTaskActivity.this, R.style.MyDialogTheme,
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

       heureLimite.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final Calendar c = Calendar.getInstance();
               int mHoure = c.get(Calendar.HOUR_OF_DAY);
               int mMinute = c.get(Calendar.MINUTE);
               heurePickerDialog = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                   @Override
                   public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       heureLimite.setText(" à "+hourOfDay + ":" + minute);
                   }
               }, mHoure, mMinute, true);

               heurePickerDialog.show();
           }
       });

    }

    // ajout d'une tâche à la base de donnée
    public void AddTask(View view) {

        String Titre = titre.getText().toString();
        String Description = description.getText().toString();
        String DateLimite = dateLimite.getText().toString();
        String HeureLimite = heureLimite.getText().toString();

        if (Titre.equals("") || Description.equals("")) {
            Toast.makeText(getApplicationContext(), "Remplissez tous les champs !", Toast.LENGTH_SHORT).show();

        } else {

            Task t = new Task(user_id,Titre, Description, DateLimite, HeureLimite);

            long verifAjout = database.ajouter(t);

            if (verifAjout > 0) {
                Intent intent = new Intent(this, TaskActivity.class);
                this.startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Erreur, la tâche n'a pas été ajoutée !", Toast.LENGTH_SHORT).show();
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
