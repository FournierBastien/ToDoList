package bastien.todolist.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bastien.todolist.Data.Task;

/**
 * Modele de la classe Task
 */
public class TaskDAO extends DatabaseDAO {

    public static final String TABLE_NAME = "Task";
    public static final String KEY = "id";
    public static final String USER_ID = "user_id";
    public static final String TITRE = "titre";
    public static final String DESCRIPTION = "description";
    public static final String DATE_LIMITE = "date_limite";
    public static final String HEURE_LIMITE = "heure_limite";
    public static final String CREATED_AT = "created_at";

//    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_ID + " INTEGER" + TITLE + " TEXT, " + DESCRIPTION + " TEXT, " + DATE_LIMITE + " TEXT);";
//
//    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public TaskDAO(Context pContext) {
        super(pContext);
    }

    public Task getTaskById(long id) {
        read();
        Task t = new Task();

        Cursor cursor = database.query(TABLE_NAME, null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
//        Cursor cursor = database.rawQuery("select * from " + TABLE_NAME + " where id = ?", new String[]{Long.toString(user_id)});

        if (cursor.moveToFirst()) {

            t.setId(cursor.getInt(0));
            t.setUser_id(cursor.getInt(1));
            t.setDescription(cursor.getString(3));
            t.setTitre(cursor.getString(2));
            t.setDateLimite(cursor.getString(4));
            t.setHeureLimite(cursor.getString(5));

        }

        cursor.close();
        close();
        return t;
    }

    /**
     * @param t la tâche à ajouter dans la base de donnée
     */
    public long ajouter(Task t) {
        open();
        ContentValues value = new ContentValues();
        value.put(TITRE, t.getTitre());
        value.put(USER_ID, t.getUser_id());
        value.put(DESCRIPTION, t.getDescription());
        value.put(DATE_LIMITE, t.getDateLimite());
        value.put(HEURE_LIMITE, t.getHeureLimite());
        value.put(CREATED_AT, Calendar.getInstance().getTimeInMillis());
        long res = database.insert(TABLE_NAME, null, value);
        close();
        return res;
    }

    /**
     * @param id l'identifiant de la tâche à supprimer
     */
    public int supprimer(long id) {
        open();
        int res = database.delete(TABLE_NAME, KEY + "=?", new String[]{String.valueOf(id)});
        close();
        return res;
    }

    /**
     * @param t modification d'une tâche
     */
    public int modifier(Task t) {
        open();
        ContentValues value = new ContentValues();
        value.put(TITRE, t.getTitre());
        value.put(DESCRIPTION, t.getDescription());
        value.put(DATE_LIMITE, t.getDateLimite());
        value.put(HEURE_LIMITE,t.getHeureLimite());

        String where = "id=?";
        String[] whereArgs = new String[]{String.valueOf(t.getId())};

//        String query = "SELECT Task SET "+TITRE+" ="+t.getTitre()+ " and "+DESCRIPTION+" ="+t.getDescription()+ " and "+DATE_LIMITE+" = "+t.getDateLimite()+ " WHERE id="+String.valueOf(t.getId());
//        database.execSQL(query);

//        return database.update(TABLE_NAME, value, where, whereArgs);

//        database.execSQL("UPDATE Task SET title = ? and description = ? and date_limite = ? WHERE id = ?;", new String[]{t.getTitre(),t.getDescription(),t.getDateLimite(),String.valueOf(t.getId())});
        int res = database.update("Task", value, where, whereArgs);
        close();
        return res;
    }


    public List<Task> getAllTaskByUserId(int user_id) {
        List<Task> list = new ArrayList<Task>();
        read();

        String[] whereArgs = new String[]{String.valueOf(user_id)};
        Cursor cursor = database.rawQuery("SELECT id,user_id,titre,description,date_limite,heure_limite FROM Task WHERE user_id=?", whereArgs);
//        Cursor cursor = database.query("Task", null, "user_id=?", new String[]{String.valueOf(user_id)}, null, null, null);
//        Cursor cursor = database.rawQuery("select * from " + TABLE_NAME + " where id = ?", new String[]{Long.toString(user_id)});

        if (cursor.moveToFirst()) {
            do {
                Task t = new Task();

                t.setId(cursor.getInt(0));
                t.setUser_id(cursor.getInt(1));
                t.setTitre(cursor.getString(2));
                t.setDescription(cursor.getString(3));
                t.setDateLimite(cursor.getString(4));
                t.setHeureLimite(cursor.getString(5));

                list.add(t);

            } while (cursor.moveToNext());
        }

        cursor.close();
        close();
        return list;
    }


}