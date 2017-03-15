package dsic.online.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by migui on 0015.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper (Context context, String databaseName,SQLiteDatabase.CursorFactory cursorFactory, int version) {
        super(context, databaseName ,cursorFactory, version );
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSQLSentence ;
        createSQLSentence = "CREATE TABLE Persona (idPersona integer PRIMARY KEY autoincrement, " +
                "nombre text NOT NULL, email text, telefono text) ";
        db.execSQL(createSQLSentence);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
