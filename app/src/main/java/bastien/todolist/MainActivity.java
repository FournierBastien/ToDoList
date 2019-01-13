package bastien.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import bastien.todolist.Database.UserDAO;

public class MainActivity extends AppCompatActivity {


    UserDAO database;

    EditText email, password;
    CheckBox mCheckBox;
    Button buttonConnexion;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new UserDAO(getApplicationContext());

        sharedPreferences = getApplicationContext().getSharedPreferences("user", 0);

        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        mCheckBox = findViewById(R.id.checkbox);
        //buttonConnexion = findViewById(R.id.buttonConnexion);

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        checkSharedPreference();
        /*buttonConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckBox.isChecked()){
                    String checkbox = sharedPreferences.getString(getString(R.string.checkbox),"True");

                    String mail = email.getText().toString();
                    editor.putString(getString(R.string.email), mail);

                    String psw = email.getText().toString();
                    editor.putString(getString(R.string.password), psw);
                    editor.commit();
                }else {
                    String checkbox = sharedPreferences.getString(getString(R.string.checkbox),"False");


                    editor.putString(getString(R.string.email), "");


                    editor.putString(getString(R.string.password), "");
                    editor.commit();
                }
            }
        });
*/

    }

    public void checkSharedPreference() {
        String checkbox = sharedPreferences.getString(getString(R.string.checkbox), "False");
        String mail = sharedPreferences.getString(getString(R.string.email), "");
        String pwd = sharedPreferences.getString(getString(R.string.password), "");

        email.setText(mail);
        password.setText(pwd);

        if (checkbox.equals("True")) {
            mCheckBox.setChecked(true);
        } else {
            mCheckBox.setChecked(false);
        }
    }

    // lorsque l'utilisateur clique sur le bouton connexion
    public void Connexion(View view) {

        String Email = email.getText().toString();
        String Password = password.getText().toString();


        // Verifie que les champs sont bien remplie sinon un message avertie l'utilisateur
        if (Email.equals("") || Password.equals("")) {

            Toast.makeText(this, "Veuillez remplir tous les champs !", Toast.LENGTH_LONG).show();
        } else {

            boolean verifIdentifiants = database.verificationUtilisateur(Email, Password);

            if (verifIdentifiants) {
                if (mCheckBox.isChecked()) {

                    editor.putString(getString(R.string.checkbox), "True");

                    editor.putString("Username", database.getUserName(Email));

                    editor.putInt("userId", database.getId(Email));

                    Email = email.getText().toString();

                    editor.putString(getString(R.string.email), Email);

                    Password = email.getText().toString();

                    editor.putString(getString(R.string.password), Password);

                } else {
                    editor.putString(getString(R.string.checkbox), "False");

                    editor.putString("Username", database.getUserName(Email));

                    editor.putInt("userId", database.getId(Email));

                    editor.putString(getString(R.string.email), "");


                    editor.putString(getString(R.string.password), "");

                }

                editor.commit();

                Toast.makeText(this, "Connexion réussie !", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, TaskActivity.class);
                this.startActivity(intent);
            } else {
                Toast.makeText(this, "Identifiants erronés !", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void Inscription(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
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

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
