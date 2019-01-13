package bastien.todolist.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String TASK_KEY = "id";
    public static final String TASK_TITRE = "titre";
    public static final String TASK_DESCRIPTION = "description";
    public static final String TASK_USER_ID = "user_id";
    public static final String TASK_DATE_LIMITE = "date_limite";
    public static final String TASK_HEURE_LIMITE = "heure_limite";
    public static final String TASK_CREATE_AT = "created_at";

    public static final String TASK_TABLE_NAME = "Task";
    public static final String TASK_TABLE_CREATE =
            "CREATE TABLE " + TASK_TABLE_NAME + " (" +
                    TASK_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TASK_TITRE + " TEXT, " +
                    TASK_DESCRIPTION + " TEXT, " +
                    TASK_USER_ID + " INTEGER, " +
                    TASK_DATE_LIMITE + " TEXT, " +
                    TASK_HEURE_LIMITE + " TEXT, " +
                    TASK_CREATE_AT + " TEXT " +
                    ");";
    public static final String TASK_TABLE_DROP = "DROP TABLE IF EXISTS " + TASK_TABLE_NAME + ";";


    public static final String USER_KEY = "id";
    public static final String USER_EMAIL = "email";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";

    public static final String USER_TABLE_NAME = "User";
    public static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    USER_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_EMAIL + " TEXT, " +
                    USER_USERNAME + " TEXT, " +
                    USER_PASSWORD + " TEXT);";
    public static final String USER_TABLE_DROP = "DROP TABLE IF EXISTS " + USER_TABLE_NAME + ";";


    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TASK_TABLE_CREATE);
        db.execSQL(USER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TASK_TABLE_DROP);
        db.execSQL(USER_TABLE_DROP);
        onCreate(db);
    }
}
