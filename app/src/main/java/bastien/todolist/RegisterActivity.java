package bastien.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bastien.todolist.Database.UserDAO;

public class RegisterActivity extends AppCompatActivity {

    UserDAO database;

    EditText email, username, password, confirmPassword;

    Button register;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        database = new UserDAO(getApplicationContext());

        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        confirmPassword = findViewById(R.id.ConfirmPassword);
        username = findViewById(R.id.Username);

//        register = findViewById(R.id.buttonValider);

    }

    public void ValiderInscription(View view) {

        String s1 = email.getText().toString();
        String s2 = password.getText().toString();
        String s3 = confirmPassword.getText().toString();
        String s4 = username.getText().toString();

        if (s1.equals("") || s2.equals("") || s3.equals("") || s4.equals("")) {
            Toast.makeText(this, "Remplissez tous les champs !", Toast.LENGTH_SHORT).show();

        } else {
            if (s2.equals(s3)) {
                boolean verifEmail = database.verificationMail(s2);
                if (verifEmail == true) {
                    long insert = database.insererUtilisateur(s1, s2, s4);
//                    if (insert > 0) {
                    Toast.makeText(getApplicationContext(), "Vous êtes  maintenant enregistré !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    this.startActivity(intent);
//                    }
                } else {
                    Toast.makeText(this, "L'adresse mail est déjà enregistrée !", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Les mot de passes sont différents !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void RetourAccueil(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
