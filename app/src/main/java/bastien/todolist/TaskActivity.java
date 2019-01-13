package bastien.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bastien.todolist.Adapter.TaskAdapter;
import bastien.todolist.Data.Task;
import bastien.todolist.Database.TaskDAO;


public class TaskActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedPreferences;

    TaskDAO taskDAO;

    Button showPopupBtn, closePopupBtn;
    PopupWindow popupWindow;
    LinearLayout linearLayout1;

    private DrawerLayout menuDrawerlayout;
    private ActionBarDrawerToggle menuToggle;
    private ActionBarOverlayLayout actionBar;

    private List<Task> tasks;

    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;


    private List<Task> checkedTasks = new ArrayList<Task>();


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // on charge les données de l'utilisateur
        sharedPreferences = getApplicationContext().getSharedPreferences("user", 0);
        int user_id = sharedPreferences.getInt("userId", 0);

        taskDAO = new TaskDAO(this);


        // Menu Toggle

        menuDrawerlayout = findViewById(R.id.layout_task_list);
        menuToggle = new ActionBarDrawerToggle(this, menuDrawerlayout, R.string.ouvrir, R.string.fermer);
        menuDrawerlayout.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView mNavigationView = (NavigationView) findViewById(R.id.menu_toggle1);

        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }


        // Popup ajout rapide

//        showPopupBtn = (Button) findViewById(R.id.showPopupBtn);
//        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);


        /* RecyclerView */
        // on lit les données
        tasks = taskDAO.getAllTaskByUserId(user_id);

        // RecyclerView permettant d'afficher la liste des tâches
        taskAdapter = new TaskAdapter(this, tasks, new TaskAdapter.ButtonAdapterListener() {

            // si on clique sur l'un des boutons du layout d'une tâche
            @Override
            public void shareOnClick(View v, int position) {
                Task t = tasks.get(position);

                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.setType("text/plain");
                String titre = t.getTitre();
                String description = t.getDescription();
                String dateLimite = t.getDateLimite();
                String heureLimite = t.getHeureLimite();


                intent.putExtra(Intent.EXTRA_SUBJECT, titre);

                intent.putExtra(Intent.EXTRA_TEXT, description);


                startActivity(Intent.createChooser(intent, "Partager la tâche avec:"));
            }

            @Override
            public void editOnClick(View v, int position) {
                Task t = tasks.get(position);

                Intent intent = new Intent(TaskActivity.this, EditTaskActivity.class);

                // on stocke les valeurs de la tâche pour l'activité EditTask
                intent.putExtra("id", t.getId());
                intent.putExtra("titre", t.getTitre());
                intent.putExtra("description", t.getDescription());
                intent.putExtra("dateLimite", t.getDateLimite());
                intent.putExtra("heureLimite", t.getHeureLimite());

                TaskActivity.this.startActivity(intent);
                Toast.makeText(TaskActivity.this, " Modification ! "+ String.valueOf(t.getId()), Toast.LENGTH_SHORT).show();

                // permet de notifier à l'adapter un changement dans la liste des tâches
                // permettant ainsi de supprimer plusieurs tâches à la suite sans problème
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void deleteOnClick(View v, int position) {
                taskDAO = new TaskDAO(TaskActivity.this);
                int resultat = taskDAO.supprimer(tasks.get(position).getId());

                if (resultat > 0) {
                    taskAdapter.removeTask(position);
                    Toast.makeText(TaskActivity.this, " Supprimée !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TaskActivity.this, "Erreur, la tâche n'a pas été supprimée !", Toast.LENGTH_SHORT).show();
                }

            }

        });

        recyclerView = findViewById(R.id.recycler_view);
        drawerLayout = findViewById(R.id.layout_task_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(taskAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (menuToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.ajoutTask) {
//            Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AddTaskActivity.class);
            this.startActivity(intent);
        }

        /*if (id == R.id.patageTask) {
            Intent intent = new Intent(this, SendTaskActivity.class);
            this.startActivity(intent);
        }*/

        //noinspection SimplifiableIfStatement
        if (menuToggle.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        Toast.makeText(this, Integer.toString(menuItem.getItemId()), Toast.LENGTH_LONG).show();


        if (id == R.id.nvl_tache) {
//            Toast.makeText(this, "Add liste", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, AddTaskActivity.class);
            this.startActivity(intent);
            return true;
        }
        if (id == R.id.deconnexion) {
//            Toast.makeText(this, "Add liste", Toast.LENGTH_LONG).show();
            Intent logOut = new Intent(this, MainActivity.class);
            this.startActivity(logOut);
            return true;
        }

        return false;
    }
}