package bastien.todolist.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DatabaseDAO {

    // Nous sommes à la première version de la base
    // Si je décide de la mettre à jour, il faudra changer cet attribut
    protected final static int VERSION = 1;
    // Le nom du fichier qui représente ma base
    protected final static String NOM = "todolist.db";

    protected SQLiteDatabase database = null;
    protected DatabaseHandler mHandler;

    public DatabaseDAO(Context pContext) {
        this.mHandler = new DatabaseHandler(pContext, NOM, null, VERSION);
    }

    public SQLiteDatabase open() {
        database = mHandler.getWritableDatabase();
        return database;
    }

    public SQLiteDatabase read() {
        database = mHandler.getReadableDatabase();
        return database;
    }

    public void close() {
        database.close();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}
