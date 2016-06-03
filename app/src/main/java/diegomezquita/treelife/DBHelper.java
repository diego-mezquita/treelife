package diegomezquita.treelife;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by diegomezquita on 03/06/16.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper singletongDBHelper;

    // Database information
    private static final String DATABASE_NAME = "TreeLifeDB";
    private static final int DATABASE_VERSION = 1;

    // Tables names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_CONTAINERS = "containers";
    private static final String TABLE_ACTIONS = "actions";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // TABLE_USERS column names
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PROFILE_PIC_PATH = "picture_path";

    // TABLE_CONTAINERS column names
    private static final String KEY_TITLE = "title";
    private static final String KEY_PLACE = "place";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_TYPE = "type";


    // TABLE_ACTIONS column names
    private static final String KEY_USER_ID = "name";
    private static final String KEY_CONTAINER_ID = "name";
    private static final String KEY_TIME = "email";

    // Tables create statements
    // TABLE_USERS
    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_PROFILE_PIC_PATH + " TEXT,"
                + KEY_CREATED_AT + " DATETIME"
            + ")";

    // TABLE_CONTAINERS
    private static final String CREATE_TABLE_CONTAINERS = "CREATE TABLE "
            + TABLE_CONTAINERS + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TITLE + " TEXT,"
            + KEY_PLACE + " TEXT,"
            + KEY_LATITUDE + " REAL,"
            + KEY_LONGITUDE + " REAL,"
            + KEY_TYPE + " TEXT,"
            + KEY_CREATED_AT + " DATETIME"
            + ")";

    // TABLE_ACTIONS
    private static final String CREATE_TABLE_ACTIONS = "CREATE TABLE "
            + TABLE_ACTIONS + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_USER_ID + " INTEGER,"
            + KEY_CONTAINER_ID + " INTEGER,"
            + KEY_TIME + " DATETIME,"
            + KEY_CREATED_AT + " DATETIME"
            + ")";

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating all required tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_CONTAINERS);
        db.execSQL(CREATE_TABLE_ACTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // onUpgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTAINERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIONS);

        // Create new tables
        onCreate(db);
    }

    /*
    TODO uncomment when User class is added
    public long createUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_PROFILE_PIC_PATH, user.getProfilePicPath());
        values.put(KEY_CREATED_AT, getDateTime());

        // Insert row in db
        Long user_id = db.insert(TABLE_USERS, null, values);

        return user_id;
    }
*/

    public long createContainer(Container container) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, container.getTitle());
        values.put(KEY_PLACE, container.getPlace());
        values.put(KEY_LATITUDE, container.getLatitude());
        values.put(KEY_LONGITUDE, container.getLongitude());
        values.put(KEY_TYPE, container.getType());
        values.put(KEY_CREATED_AT, getDateTime());

        // Insert row in db
        Long container_id = db.insert(TABLE_CONTAINERS, null, values);

        return container_id;
    }



    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        // If this produce any error could be because I included
        //     import java.util.Date;
        // when I really needed
        //     import java.sql.Date;
        Date date = new Date();
        return dateFormat.format(date);
    }

    // Singleton to instantiate the SQLiteOpenHelper
    public static synchronized DBHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (singletongDBHelper == null) {
            singletongDBHelper = new DBHelper(context.getApplicationContext());
        }
        return singletongDBHelper;
    }
}
