package diegomezquita.treelife.DatabaseAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import diegomezquita.treelife.Models.Container;
import diegomezquita.treelife.Models.Containers;
import diegomezquita.treelife.Models.RecycleInAction;
import diegomezquita.treelife.Models.User;

/**
 * Created by diegomezquita on 03/06/16.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper singletoneDBHelper;
    private static int updateDB;

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
    private static final String KEY_KIRI_STATE = "kiri_state";

    // TABLE_CONTAINERS column names
    private static final String KEY_TITLE = "title";
    private static final String KEY_PLACE = "place";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_TYPE = "type";


    // TABLE_ACTIONS column names
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_CONTAINER_ID = "container_id";
    private static final String KEY_TIME = "time";
    private static final String KEY_POINTS = "points";

    // Tables create statements
    // TABLE_USERS
    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_PROFILE_PIC_PATH + " TEXT,"
                + KEY_CREATED_AT + " DATETIME,"
                + KEY_KIRI_STATE + " INTEGER"
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
            + KEY_POINTS + " INTEGER"
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

        // TODO upgrade db version?
        // Create new tables
        onCreate(db);
    }

    public long createUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        // TODO start
        // the next code restarts the database
        // this.onUpgrade(db, DBHelper.DATABASE_VERSION, DBHelper.DATABASE_VERSION + 1);
        // Remove this block of code
        // TODO end

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getUserName());
        values.put(KEY_EMAIL, user.getUserEmail());
        values.put(KEY_PASSWORD, user.getUserPassword());
        values.put(KEY_PROFILE_PIC_PATH, user.getUserProfilePictureUrl());
        values.put(KEY_CREATED_AT, getDateTime());
        values.put(KEY_KIRI_STATE, "0");

        // Insert row in db
        Long user_id = db.insert(TABLE_USERS, null, values);

        return user_id;
    }

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
        Long containerId = db.insert(TABLE_CONTAINERS, null, values);



        this.getContainerById(containerId);
        return containerId;
    }

    public void getContainerById(Long containerId) {
        Container container = new Container();

        String selectQuery = "SELECT  * FROM " + TABLE_CONTAINERS
                + " WHERE " + KEY_CONTAINER_ID + " = " + containerId.toString();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Log.e("DBHELPER-NEWRESULTDB", selectQuery);

                Long key_id = c.getLong(c.getColumnIndex(KEY_ID));
                String key_place = c.getString(c.getColumnIndex(KEY_PLACE));
                String key_title = c.getString(c.getColumnIndex(KEY_TITLE));
                Double key_latitude = c.getDouble(c.getColumnIndex(KEY_LATITUDE));
                Double key_longitude = c.getDouble(c.getColumnIndex(KEY_LONGITUDE));
                String key_type = c.getString(c.getColumnIndex(KEY_TYPE));
                String key_created_at = c.getString(c.getColumnIndex(KEY_CREATED_AT));

                Log.e("KEY_ID - ", key_id.toString());
                Log.e("KEY_PLACE - ", key_place);
                Log.e("KEY_TITLE - ", key_title);
                Log.e("KEY_LATITUDE - ", key_latitude.toString());
                Log.e("KEY_LONGITUDE - ", key_longitude.toString());
                Log.e("KEY_TYPE - ", key_type);
                Log.e("KEY_CREATED_AT - ", key_created_at);

                // adding to todo list
                container = new Container();
                // TODO remove comment
                Log.e("******* END ACTION LOOP", " ******");
            } while (c.moveToNext());
        }

        // TODO return the container with data
        //return container;

    }

    public long createRecycleInAction(RecycleInAction action) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_USER_ID, action.getUser().getId());
        values.put(KEY_CONTAINER_ID, action.getContainer().getId());
        values.put(KEY_TIME, getDateTime());
        values.put(KEY_POINTS, action.getPoints());

        // Insert row in db
        Long action_id = db.insert(TABLE_ACTIONS, null, values);

        // TODO remove the getAllContainers call - it was used for tracking and testing
        this.getAllContainers();
        return action_id;
    }

    public List<RecycleInAction> getActionsByUser(Long user_id) {
        user_id = new Long(3);
        // TODO this data is added to the db to be able to develop the listings
        // "Calle Avilés, 17, Gijón", 43.5375589, -5.6715278, "Colegio Virgen Reina", "oil"
        Container container_oil = new Container();
        container_oil.setTitle("Calle Avilés, 17, Gijón");
        container_oil.setPlace("Colegio Virgen Reina");
        container_oil.setType("oil");

        Container container_batteries = new Container();
        container_batteries.setTitle("Avenida Portugal, 44, Gijón");
        container_batteries.setPlace("Estación de servicio");
        container_batteries.setType("battery");


        createRecycleInAction(new RecycleInAction(container_batteries));
        createRecycleInAction(new RecycleInAction(container_oil));
        createRecycleInAction(new RecycleInAction(container_batteries));
        createRecycleInAction(new RecycleInAction(container_batteries));
        createRecycleInAction(new RecycleInAction(container_oil));
        createRecycleInAction(new RecycleInAction(container_oil));
        createRecycleInAction(new RecycleInAction(container_batteries));


        List<RecycleInAction> actions = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_ACTIONS
                + " WHERE " + KEY_USER_ID + " = " + user_id.toString();

        Log.e("DBHELPER-SELECTQUERY", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Log.e("DBHELPER-NEWRESULTDB", selectQuery);
                RecycleInAction action = new RecycleInAction();
                Log.e("KEY_ID - ", String.valueOf(c.getInt((c.getColumnIndex(KEY_ID)))));
                Log.e("KEY_USER_ID - ", c.getString(c.getColumnIndex(KEY_USER_ID)));
                Log.e("KEY_CONTAINER_ID - ", c.getString(c.getColumnIndex(KEY_CONTAINER_ID)));
                Log.e("KEY_TIME - ", c.getString(c.getColumnIndex(KEY_TIME)));
                Log.e("KEY_POINTS - ", c.getString(c.getColumnIndex(KEY_POINTS)));

                // adding to todo list
                actions.add(action);
                // TODO remove comment
                Log.e("******* END ACTION LOOP", " ******");
            } while (c.moveToNext());
        }

        return actions;
    }

    public List<RecycleInAction> getActionsByContainer(Long container_id) {
        List<RecycleInAction> actions = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_ACTIONS
                + " WHERE " + KEY_CONTAINER_ID + " = " + container_id.toString();

        Log.e("DBHELPER-SELECTQUERY", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Log.e("DBHELPER-NEWRESULTDB", selectQuery);
                RecycleInAction action = new RecycleInAction();
                Log.e("KEY_ID - ", String.valueOf(c.getInt((c.getColumnIndex(KEY_ID)))));
                Log.e("KEY_PLACE - ", c.getString(c.getColumnIndex(KEY_PLACE)));
                Log.e("KEY_TITLE - ", c.getString(c.getColumnIndex(KEY_TITLE)));
                Log.e("KEY_LATITUDE - ", c.getString(c.getColumnIndex(KEY_LATITUDE)));
                Log.e("KEY_LONGITUDE - ", c.getString(c.getColumnIndex(KEY_LONGITUDE)));
                Log.e("KEY_TYPE - ", c.getString(c.getColumnIndex(KEY_TYPE)));
                Log.e("KEY_CREATED_AT - ", c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to todo list
                actions.add(action);
                // TODO remove comment
                Log.e("******* END ACTION LOOP", " ******");
            } while (c.moveToNext());
        }

        return actions;
    }

   // TODO test the query once it is possible and complete the method development
   public Containers getContainersByUser(Long user_id) {
    // Steps to get the expected result
    // 1  getActionsByUser
    // 2  on the result of previous SELECT
    //      result inner join containers table
        /*
        * "SELECT  * FROM " + TABLE_ACTIONS + ", " + TABLE_CONTAINERS
        *       + " ON " + TABLE_ACTIONS + "." + KEY_CONTAINER_ID + " = " + TABLE_CONTAINERS + "." + KEY_CONTAINER_ID
                + " WHERE " + TABLE_ACTIONS + "." + KEY_USER_ID + " = " + user_id.toString();
        *
        *
        * */
    // 3  order the result
        /*
        * "SELECT  COUNT(KEY_CONTAINER_ID) AS container_actions FROM " + --- query from step 2 ----
        *       + " GROUP BY " + KEY_CONTAINER_ID
                + " ORDER BY " + container_actions + "DESC"
        *
        *
        * ++++++++++++++++++++++++ TOTAL QUERY
        * "SELECT  COUNT(KEY_CONTAINER_ID) AS container_actions FROM " +
          *             "SELECT  * FROM " + TABLE_ACTIONS + ", " + TABLE_CONTAINERS
          *                       + " ON " + TABLE_ACTIONS + "." + KEY_CONTAINER_ID + " = " + TABLE_CONTAINERS + "." + KEY_CONTAINER_ID
                                  + " WHERE " + TABLE_ACTIONS + "." + KEY_USER_ID + " = " + user_id.toString();
        *       + " GROUP BY " + KEY_CONTAINER_ID
                + " ORDER BY container_actions DESC"
        *
        *
        * */
    // 4  add result to 'containers' variable in here
    //

        Containers containers = new Containers();
        ArrayList<Container> contianersList = new ArrayList<>();
        /*
        String selectQuery = "SELECT  COUNT(KEY_CONTAINER_ID), KEY_CONTAINER_ID,  E AS container_actions FROM " +

                + " WHERE " + user_id.toString() + " IN (" TABLE_ACTIONS + "." + KEY_USER_ID + " = " + user_id.toString();
               + " GROUP BY " + KEY_CONTAINER_ID
                + " ORDER BY container_actions DESC";
                */

       String selectQuery =
               "SELECT " + TABLE_ACTIONS + "." + KEY_ID + " AS act_id, "
                       + TABLE_CONTAINERS + "." + KEY_CONTAINER_ID + " AS cont_id, "
                       + "COUNT(cont_id) AS container_actions"
               + " FROM " + TABLE_ACTIONS + " INNER JOIN " + TABLE_CONTAINERS
               + " WHERE " + TABLE_ACTIONS + "." + KEY_CONTAINER_ID + " = cont_id"
                       + " AND " + TABLE_ACTIONS + "." + KEY_USER_ID + " = " + user_id.toString()
               + " GROUP BY cont_id"
               + " ORDER BY containers_actions DESC";

        Log.e("DBHELPER-SELECTQUERY", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Log.e("DBHELPER-NEWRESULTDB", selectQuery);
                Container container = new Container();
                Log.e("KEY_ID - ", String.valueOf(c.getInt((c.getColumnIndex(KEY_ID)))));
                Log.e("KEY_PLACE - ", c.getString(c.getColumnIndex(KEY_PLACE)));
                Log.e("KEY_TITLE - ", c.getString(c.getColumnIndex(KEY_TITLE)));
                Log.e("KEY_LATITUDE - ", c.getString(c.getColumnIndex(KEY_LATITUDE)));
                Log.e("KEY_LONGITUDE - ", c.getString(c.getColumnIndex(KEY_LONGITUDE)));
                Log.e("KEY_TYPE - ", c.getString(c.getColumnIndex(KEY_TYPE)));
                Log.e("KEY_CREATED_AT - ", c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to todo list
                contianersList.add(container);
                // TODO remove comment
                Log.e("******* END ACTION LOOP", " ******");
            } while (c.moveToNext());
        }

        return new Containers(contianersList);
    }

    public Containers getRankingByContainer(Long container_id) {

        //TODO test the query once it is possible and complete the method development


        // Steps to get the expected result
        // 1  getActionsByContainer
        // 2  on the result of previous SELECT
        //      result inner join users table
        /*
        * "SELECT  * FROM " + TABLE_ACTIONS + ", " + TABLE_USERS
        *       + " ON " + TABLE_ACTIONS + "." + KEY_USER_ID + " = " + TABLE_USERS + "." + KEY_USER_ID
                + " WHERE " + TABLE_ACTIONS + "." + KEY_CONTAINER_ID + " = " + container_id.toString();
        *
        *
        * */
        // 3  order the result
        /*
        * "SELECT  COUNT(KEY_USER_ID) AS user_actions FROM " + --- query from step 2 ----
        *       + " GROUP BY " + KEY_USER_ID
                + " ORDER BY " + user_actions + "DESC"
        *
        *
        * ++++++++++++++++++++++++ TOTAL QUERY
        * "SELECT  COUNT(KEY_USER_ID) AS user_actions FROM " +
          *             "SELECT  * FROM " + TABLE_ACTIONS + ", " + TABLE_USERS
                        *       + " ON " + TABLE_ACTIONS + "." + KEY_USER_ID + " = " + TABLE_USERS + "." + KEY_USER_ID
                                + " WHERE " + TABLE_ACTIONS + "." + KEY_CONTAINER_ID + " = " + container_id.toString();
        *       + " GROUP BY " + KEY_USER_ID
                + " ORDER BY " + user_actions + "DESC"
        *
        *
        * */
        // 4  add result to 'containers' variable in here
        //

        List<User> users = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_ACTIONS + ", " + TABLE_CONTAINERS
                + " ON " + TABLE_ACTIONS + "." + KEY_CONTAINER_ID + " = " + TABLE_CONTAINERS + "." + KEY_CONTAINER_ID
                + " WHERE " + TABLE_ACTIONS + "." + KEY_USER_ID + " = " + container_id.toString();

        Log.e("DBHELPER-SELECTQUERY", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Log.e("DBHELPER-NEWRESULTDB", selectQuery);
                Container container = new Container();
                Log.e("KEY_ID - ", String.valueOf(c.getInt((c.getColumnIndex(KEY_ID)))));
                Log.e("KEY_PLACE - ", c.getString(c.getColumnIndex(KEY_PLACE)));
                Log.e("KEY_TITLE - ", c.getString(c.getColumnIndex(KEY_TITLE)));
                Log.e("KEY_LATITUDE - ", c.getString(c.getColumnIndex(KEY_LATITUDE)));
                Log.e("KEY_LONGITUDE - ", c.getString(c.getColumnIndex(KEY_LONGITUDE)));
                Log.e("KEY_TYPE - ", c.getString(c.getColumnIndex(KEY_TYPE)));
                Log.e("KEY_CREATED_AT - ", c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to todo list
                //   contianersList.add(container);
                // TODO remove comment
                Log.e("******* END ACTION LOOP", " ******");
            } while (c.moveToNext());
        }

        return new Containers(/*contianersList*/);
    }

    User getUserByEmail(String email) {
        // TODO doublecheck this - if difficult remove it to don't allow login, just sign in
        Container newContainer = new Container("Calle Avilés, 17, Gijón", "Colegio Virgen Reina", "oil");
        String selectQuery = "SELECT  * FROM " + TABLE_USERS
                + " WHERE " + KEY_EMAIL + " = " + email;

        Log.e("DBHELPER-SELECTQUERY", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            Log.e("DBHELPER-NEWRESULTDB", selectQuery);

            Long keyId = c.getLong(c.getColumnIndex(KEY_ID));
            String keyName = c.getString(c.getColumnIndex(KEY_NAME));
            String keyEmail = c.getString(c.getColumnIndex(KEY_EMAIL));
            String keyPassword = c.getString(c.getColumnIndex(KEY_PASSWORD));
            String keyProfilePicPath = c.getString(c.getColumnIndex(KEY_PROFILE_PIC_PATH));
            String keyCreatedAt = c.getString(c.getColumnIndex(KEY_CREATED_AT));
            Integer keyKiriState = c.getInt(c.getColumnIndex(KEY_KIRI_STATE));

            /*Log.e("KEY_ID - ", String.valueOf(keyId));
            Log.e("KEY_NAME - ", c.getString(c.getColumnIndex(KEY_USER_ID)));
            Log.e("KEY_CONTAINER_ID - ", c.getString(c.getColumnIndex(KEY_CONTAINER_ID)));
            Log.e("KEY_TIME - ", c.getString(c.getColumnIndex(KEY_TIME)));
            Log.e("KEY_POINTS - ", c.getString(c.getColumnIndex(KEY_POINTS)));*/

            User user = new User(keyId, keyName, keyEmail, keyPassword, keyProfilePicPath, keyCreatedAt, keyKiriState);

            // TODO remove comment
            Log.e("******* END USER", " ******");
        }

        // If the email received as parameter is not any user email
        return new User(new Long(-1), "", "", "", "", "", 0);
    }

    /* TODO function to test data insertion in the database
 * getting all containers
 * */
    public List<Container> getAllContainers() {
        List<Container> containers = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTAINERS;

        Log.e("DBHELPER-SELECTQUERY", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Log.e("DBHELPER-NEWRESULTDB", selectQuery);
                Container td = new Container();
                Log.e("KEY_ID - ", String.valueOf(c.getInt(c.getColumnIndex(KEY_ID))));
                Log.e("KEY_PLACE - ", c.getString(c.getColumnIndex(KEY_PLACE)));
                Log.e("KEY_TITLE - ", c.getString(c.getColumnIndex(KEY_TITLE)));
                Log.e("KEY_LATITUDE - ", c.getString(c.getColumnIndex(KEY_LATITUDE)));
                Log.e("KEY_LONGITUDE - ", c.getString(c.getColumnIndex(KEY_LONGITUDE)));
                Log.e("KEY_TYPE - ", c.getString(c.getColumnIndex(KEY_TYPE)));
                Log.e("KEY_CREATED_AT - ", c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to todo list
                containers.add(td);
            } while (c.moveToNext());
        }

        return containers;
    }

    /* TODO function to test data insertion in the database
 * getting all actions
 * */
    public List<RecycleInAction> getAllActions() {
        List<RecycleInAction> actions = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTAINERS;

        Log.e("DBHELPER-SELECTQUERY", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Log.e("DBHELPER-NEWRESULTDB", selectQuery);
                RecycleInAction td = new RecycleInAction();
                Log.e("KEY_ID - ", String.valueOf(c.getInt((c.getColumnIndex(KEY_ID)))));
                Log.e("KEY_USER_ID - ", c.getString(c.getColumnIndex(KEY_USER_ID)));
                Log.e("KEY_CONTAINER_ID - ", c.getString(c.getColumnIndex(KEY_CONTAINER_ID)));
                Log.e("KEY_TIME - ", c.getString(c.getColumnIndex(KEY_TIME)));
                Log.e("KEY_POINTS - ", c.getString(c.getColumnIndex(KEY_POINTS)));

                // adding to todo list
                actions.add(td);
            } while (c.moveToNext());
        }

        return actions;
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
        if (singletoneDBHelper == null) {
            singletoneDBHelper = new DBHelper(context);
        }
        return singletoneDBHelper;
    }

    public static synchronized DBHelper getInstance() {
        return singletoneDBHelper;
    }
}
