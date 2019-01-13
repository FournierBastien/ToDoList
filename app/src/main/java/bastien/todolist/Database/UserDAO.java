package bastien.todolist.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


/**
 * Modele de la classe User
 */
public class UserDAO extends DatabaseDAO {

    public static final String KEY = "id";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static final String TABLE_NAME = "User";
    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    EMAIL + " TEXT, " +
                    USERNAME + " TEXT, " +
                    PASSWORD + " TEXT);";
    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public UserDAO(Context pContext) {
        super(pContext);
    }


    public boolean verificationMail(String email) {
        open();
//        Cursor cursor = database.rawQuery("SELECT * FROM User where email=?", new String[]{email});
        Cursor cursor = database.query("User", null, "email=?", new String[]{email}, null, null, null);
        if (cursor.getCount() > 0) return false;
        else return true;
    }

    public boolean verificationUtilisateur(String email, String password) {

        open();
//        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " where email=? and password=?", new String[]{email, password});
        Cursor cursor = database.query("User", null, "email=? and password=?", new String[]{email, password}, null, null, null);
        if (cursor.getCount() <= 0) return false;
        else return true;
    }

    public long insererUtilisateur(String email, String password, String username) {
        open();
        ContentValues value = new ContentValues();
        value.put(UserDAO.EMAIL, email);
        value.put(UserDAO.PASSWORD, password);
        value.put(UserDAO.USERNAME, username);
        return database.insert(UserDAO.TABLE_NAME, null, value);
    }

    // retourne le password de l'utilisateur
    public String getPassword(String email) {
        open();
        Cursor cursor = database.query("User", null, "email=?", new String[]{email}, null, null, null);
        if (cursor.getCount() < 1)
            return "Le nom d'utilisateur n'existe pas !";
        cursor.moveToFirst();
        String getPassword = cursor.getString(cursor.getColumnIndex("password"));
        return getPassword;
    }

    public String getUserName(String email) {
        open();
        Cursor cursor = database.query("User", null, "email=?", new String[]{email}, null, null, null);
        if (cursor.getCount() < 1)
            return "";
        cursor.moveToFirst();
        String getName = cursor.getString(2);
        return getName;
    }

    // retourne l'id de l'utilisateur
    public Integer getId(String email) {
        open();
        Cursor cursor = database.query("User", null, "email=?", new String[]{email}, null, null, null);
        if (cursor.getCount() < 1)
            return 0;
        cursor.moveToFirst();

        Integer getId = cursor.getInt(0);
        return getId;
    }


}