package org.osuosl.ocw;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataBaseHandler extends SQLiteAssetHelper {

	private static final String DATABASE_NAME = "ocw";     //Name of the .db file stored on the device
	private static final int DATABASE_VERSION = 1;		   

	private static DataBaseHandler mInstance = null;
	
	// Table names
	private static final String SCHEDULE_TABLE_NAME = "SCHEDULE";
	private static final String SPEAKERS_TABLE_NAME = "SPEAKERS";
	private static final String TRACKS_TABLE_NAME = "TRACKS";
	private static final String STATUS_TABLE_NAME = "STATUS";

	private static final String KEY_ID = "_id";
	// Schedule table column names
	private static final String KEY_EVENT_ID = "event_id";
	private static final String KEY_TITLE = "event_title";
	private static final String KEY_START = "start_time";
	private static final String KEY_END = "end_time";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_ROOM_TITLE = "room_title";
	private static final String KEY_TRACK_ID = "track_id";
	private static final String KEY_SPEAKER_IDS = "speaker_ids";
	private static final String KEY_PRESENTER = "presenter";
	
	// Speakers table column names
	private static final String KEY_SPEAKER_ID = "speaker_id";
	private static final String KEY_NAME = "fullname";
	private static final String KEY_BIO = "biography";
	private static final String KEY_AFFILIATION = "affiliation";
	private static final String KEY_TWITTER = "twitter";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_WEBSITE = "website";
	private static final String KEY_BLOG = "blog";
	private static final String KEY_LINKEDIN = "linkedin";
	
	// Tracks table column names
	// track_id already defined in event block
	private static final String KEY_TRACK_TITLE = "track_title";
	private static final String KEY_COLOR = "color";
	private static final String KEY_COLOR_TEXT = "color_text";
	
	// Status table column names
	private static final String KEY_STATUS_NAME = "name";
	private static final String KEY_STATUS_VALUE = "value";
	
	// Queries
	private static final String[] GET_SCHEDULE_ROW = new String[]{KEY_EVENT_ID, KEY_TITLE, KEY_START, KEY_END, KEY_DESCRIPTION, KEY_ROOM_TITLE, KEY_TRACK_ID, KEY_SPEAKER_IDS, KEY_PRESENTER};
	

	public static DataBaseHandler getInstance(Context ctx) {
	      
	    // Use the application context, which will ensure that you 
	    // don't accidentally leak an Activity's context.
	    // See this article for more information: http://bit.ly/6LRzfx
	    if (mInstance == null) {
	      mInstance = new DataBaseHandler(ctx.getApplicationContext());
	    }
	    return mInstance;
	  }

	public DataBaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	
	
	public Long addEvents(ArrayList<Event> mEvents){
		SQLiteDatabase db = null;
		Long i = 0l;

		try {
			db = this.getWritableDatabase();
			db.beginTransaction();
			try{
				for(int j = 0; j < mEvents.size(); j++){
					ContentValues values = new ContentValues();
					values.put(KEY_EVENT_ID, mEvents.get(j).getEvent_id());
					values.put(KEY_TITLE, mEvents.get(j).getEvent_title());
					values.put(KEY_START, mEvents.get(j).getStart_time().toString());
					values.put(KEY_END, mEvents.get(j).getEnd_time().toString());
					values.put(KEY_DESCRIPTION, mEvents.get(j).getDescription());
					values.put(KEY_ROOM_TITLE, mEvents.get(j).getRoom_title());
					values.put(KEY_TRACK_ID, mEvents.get(j).getTrack_id());

					String speakerIds = "";
					if(mEvents.get(j).getSpeaker_ids() != null){
						speakerIds = convertArrayToString(mEvents.get(j).getSpeaker_ids());
						values.put(KEY_SPEAKER_IDS, speakerIds);
					}

					values.put(KEY_PRESENTER, mEvents.get(j).getPresenter());

					// adding row
					i = db.insert(SCHEDULE_TABLE_NAME, null, values);
				}
				db.setTransactionSuccessful();

			} catch(Exception e){
				db.endTransaction();
				throw e;
			}

			db.endTransaction();

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			db.close();
		}
		return i;
	}
	
	
	
	
	public Long addSpeakers(ArrayList<Speaker> speakers){
		SQLiteDatabase db = null;
		Long i = 0l;

		try {
			db = this.getWritableDatabase();

			db.beginTransaction();
			try{
				
				for(int j = 0; j < speakers.size(); j++){
					ContentValues values = new ContentValues();
					values.put(KEY_SPEAKER_ID, speakers.get(j).getSpeaker_id());
					values.put(KEY_NAME, speakers.get(j).getFullname());
					values.put(KEY_BIO, speakers.get(j).getBiography());
					values.put(KEY_AFFILIATION, speakers.get(j).getAffiliation());
					values.put(KEY_TWITTER, speakers.get(j).getTwitter());
					values.put(KEY_EMAIL, speakers.get(j).getEmail());
					values.put(KEY_WEBSITE, speakers.get(j).getWebsite());
					values.put(KEY_BLOG, speakers.get(j).getBlog());
					values.put(KEY_LINKEDIN, speakers.get(j).getLinkedin());
					//adding row
					i = db.insert(SPEAKERS_TABLE_NAME, null, values);
				}
				
				db.setTransactionSuccessful();

			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			db.close();
		}
		return i;
	}
	
	
	
	public Long addTracks(ArrayList<Track> tracks){
		SQLiteDatabase db = null;
		Long i = 0l;

		try {
			db = this.getWritableDatabase();

			db.beginTransaction();
			
			try{
				for(int j = 0; j < tracks.size(); j++){
					ContentValues values = new ContentValues();
					values.put(KEY_TRACK_ID, tracks.get(j).getTrack_id());
					values.put(KEY_TRACK_TITLE, tracks.get(j).getTrack_title());
					values.put(KEY_COLOR, tracks.get(j).getColor());
					values.put(KEY_COLOR_TEXT, tracks.get(j).getColor_text());

					//adding row
					i = db.insert(TRACKS_TABLE_NAME, null, values);
				}
				
				db.setTransactionSuccessful();

			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			db.close();
		}
		return i;
	}
	
	/**
	 * Initialize a status table value
	 * @param table
	 * @param value
	 * @return
	 */
	public Long addStatusRow(String name, String value){
		SQLiteDatabase db = null;
		Long i = 0l;

		try {
			db = this.getWritableDatabase();
			db.beginTransaction();
			
			try{
				ContentValues values = new ContentValues();
				values.put(KEY_STATUS_NAME, name);
				values.put(KEY_STATUS_VALUE, value);

				// adding row
				i = db.insert(STATUS_TABLE_NAME, null, values);

				db.setTransactionSuccessful();

			} catch(Exception e){
				db.endTransaction();
				throw e;
			}

			db.endTransaction();

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			db.close();
		}
		return i;
	}

	
	public int updateEvents(ArrayList<Event> events){
		SQLiteDatabase db = null;
		int i = 0;

		try {
			db = this.getWritableDatabase();

			db.beginTransaction();
			try{
				for(int j = 0; j < events.size(); j++){
					ContentValues values = new ContentValues();
					values.put(KEY_EVENT_ID, events.get(j).getEvent_id());
					values.put(KEY_TITLE, events.get(j).getEvent_title());
					values.put(KEY_START, events.get(j).getStart_time().toString());
					values.put(KEY_END, events.get(j).getEnd_time().toString());
					values.put(KEY_DESCRIPTION, events.get(j).getDescription());
					values.put(KEY_ROOM_TITLE, events.get(j).getRoom_title());
					values.put(KEY_TRACK_ID, events.get(j).getTrack_id());

					String speakerIds = "";
					if(events.get(j).getSpeaker_ids() != null){
						speakerIds = convertArrayToString(events.get(j).getSpeaker_ids());
						values.put(KEY_SPEAKER_IDS, speakerIds);
					}

					values.put(KEY_PRESENTER, events.get(j).getPresenter());

					// updating row
					i = db.update(SCHEDULE_TABLE_NAME, values, KEY_EVENT_ID + " = ?",
							new String[] { String.valueOf(events.get(j).getEvent_id())});
				}
				db.setTransactionSuccessful();


			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			db.close();
		}
		return i;
	}

	
	public int updateSpeakers(ArrayList<Speaker> speakers){
		SQLiteDatabase db = null;
		int i = 0;

		try {
			db = this.getWritableDatabase();
			db.beginTransaction();
			try{
				for(int j = 0; j < speakers.size(); j++){
					ContentValues values = new ContentValues();
					values.put(KEY_SPEAKER_ID, speakers.get(j).getSpeaker_id());
					values.put(KEY_NAME, speakers.get(j).getFullname());
					values.put(KEY_BIO, speakers.get(j).getBiography());
					values.put(KEY_AFFILIATION, speakers.get(j).getAffiliation());
					values.put(KEY_TWITTER, speakers.get(j).getTwitter());
					values.put(KEY_EMAIL, speakers.get(j).getEmail());
					values.put(KEY_WEBSITE, speakers.get(j).getWebsite());
					values.put(KEY_BLOG, speakers.get(j).getBlog());
					values.put(KEY_LINKEDIN, speakers.get(j).getLinkedin());

					// updating row
					i = db.update(SPEAKERS_TABLE_NAME, values, KEY_SPEAKER_ID + " = ?",
							new String[] { String.valueOf(speakers.get(j).getSpeaker_id())});
				}
				db.setTransactionSuccessful();
				
			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			db.close();
		}
		return i;
	}
	
	
	public int updateTracks(ArrayList<Track> tracks){
		SQLiteDatabase db = null;
		int i = 0;

		try {
			db = this.getWritableDatabase();
			db.beginTransaction();
			try{
				for(int j = 0; j < tracks.size(); j++){
					ContentValues values = new ContentValues();
					values.put(KEY_TRACK_ID, tracks.get(j).getTrack_id());
					values.put(KEY_TRACK_TITLE, tracks.get(j).getTrack_title());
					values.put(KEY_COLOR, tracks.get(j).getColor());
					values.put(KEY_COLOR_TEXT, tracks.get(j).getColor_text());


					// updating row
					i = db.update(TRACKS_TABLE_NAME, values, KEY_TRACK_ID + " = ?",
							new String[] { String.valueOf(tracks.get(j).getTrack_id())});
				}
				db.setTransactionSuccessful();

			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			db.close();
		}
		return i;
	}
	
	
	
	/**
	 * Update the current time in ms that the table was last updated
	 * @param table
	 * @param value
	 * @return
	 */
	public int updateStatusRow(String name, String value){
		SQLiteDatabase db = null;
		int i = 0;
		
		try {
			db = this.getWritableDatabase();
			db.beginTransaction();
			try{
				ContentValues values = new ContentValues();
				values.put(KEY_STATUS_NAME, name);
				values.put(KEY_STATUS_VALUE, value);
				
				// updating row
				i = db.update(STATUS_TABLE_NAME, values, KEY_STATUS_NAME + " = ?",
						new String[] { name });

				db.setTransactionSuccessful();

			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			db.close();
		}
		return i;
		
	}
	
	

	/**
	 * Retrieve a single row(Event) from the schedule database table.
	 * @param id row to be retrieved.
	 * @return Event at row == id.
	 */
	public Event getScheduleRow(String id) {
		Event event = null;
		SQLiteDatabase db = null;
		try {
			db = this.getReadableDatabase();

			db.beginTransaction();
			try{

				Cursor cursor = db.query(SCHEDULE_TABLE_NAME, GET_SCHEDULE_ROW, KEY_ID + "=?",
						new String[] { id }, null, null, null, null);
				if (cursor != null){
					cursor.moveToFirst();
					
					String event_id = cursor.getString(0);
					String event_title = cursor.getString(1);
					String start_time = cursor.getString(2);
					String end_time = cursor.getString(3);
					String description = cursor.getString(4);
					String room_title = cursor.getString(5);
					String track_id = cursor.getString(6);
					String sIds = cursor.getString(7);
					String presenter = cursor.getString(8);
					cursor.close();
					String[] speakerids = {};
					if(sIds != null)
						speakerids  = sIds.split(",");

					event = new Event(
							event_id, event_title, start_time, end_time, description, room_title, track_id, speakerids, presenter);

				}
				db.setTransactionSuccessful();
			}catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return event;
	}

	/**
	 * Retrieve a single row(Speaker) from the speakers database table
	 * @param id row to be retrieved.
	 * @return Speaker at row == id.
	 */
	public Speaker getSpeakersRow(String id) {
		Speaker speaker = null;
		SQLiteDatabase db = null;
		try {
			db = this.getReadableDatabase();

			db.beginTransaction();
			try{

				Cursor cursor = db.rawQuery("SELECT * FROM SPEAKERS WHERE _id = "+id, null);

				if (cursor != null){
					cursor.moveToFirst();


					speaker = new Speaker(
							cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), 
							cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8),
							cursor.getString(9));
					cursor.close();
				}
				db.setTransactionSuccessful();
			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return speaker;
	}
	
	/**
	 * Retrieve a single row(Track) from the tracks database table
	 * @param id row of the track to be retrieved.
	 * @return Track at row == id.
	 */
	public Track getTracksRow(String id) {
		Track track = null;
		SQLiteDatabase db = null;
		try {
			db = this.getReadableDatabase();

			db.beginTransaction();
			try{

				Cursor cursor = db.rawQuery("SELECT * FROM TRACKS WHERE _id = "+id, null);

				if (cursor != null){
					cursor.moveToFirst();

					track = new Track(
							cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4) 
							);
					cursor.close();
				}
				db.setTransactionSuccessful();
			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return track;
	}
	
	/**
	 * Returns when a table was last updated.
	 */
	public Long getStatusRow(String name) {
		Long value = -1l;
		SQLiteDatabase db = null;
		
		try {
			db = this.getReadableDatabase();

			db.beginTransaction();
			try{

				Cursor cursor = db.rawQuery("SELECT * FROM STATUS WHERE name = '"+name+"'", null);

				if (cursor != null){
					cursor.moveToFirst();

					value = Long.parseLong(cursor.getString(2));
					
					cursor.close();
				}
				db.setTransactionSuccessful();
			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return value;
	}
	
	
	/**
	 * Returns the number of rows a table has.
	 * @param table_name The table to be queried.
	 * @return The number of rows that the table "table_name" has.
	 */
	public long numRows(String table_name){
		long numRows = 0l;
		SQLiteDatabase db = null;
		try {
			db = this.getReadableDatabase();
			db.beginTransaction();
			try{
				numRows = DatabaseUtils.queryNumEntries(db, table_name);
				db.setTransactionSuccessful();
			} catch (Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return numRows;

	}
	
	public long deleteAllRows(String table_name){
		SQLiteDatabase db = null;
		int i = 0;
		try {
			db = this.getReadableDatabase();
			db.beginTransaction();
			try{
				String deleteSQL = "DELETE FROM " + table_name;
				db.execSQL(deleteSQL);
				db.setTransactionSuccessful();
			} catch (Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return i;
	}

	/**
	 * Checks if a row(Event) already exists with event_id == id.
	 * @param id event_id to be checked if already exists in the database.
	 * @return 1 if exists, 0 if doesn't exist, -1 if error checking if exists.
	 */
	public int existsEvent(String id) {
		int exists = -1;
		SQLiteDatabase db = null;
		try {
			db = this.getReadableDatabase();

			db.beginTransaction();
			try{
				Cursor cursor = db.rawQuery("select 1 from SCHEDULE where event_id=?", 
						new String[] { id });
				Boolean b = (cursor.getCount() > 0);
				exists = b? 1 : 0;
				cursor.close();
				db.setTransactionSuccessful();
			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return exists;
	}

	/**
	 * Checks if a row(Speaker) already exists with speaker_id == id.
	 * @param id speaker_id to be checked if already exists in the database.
	 * @return 1 if exists, 0 if doesn't exist, -1 if error checking if exists.
	 */
	public int existsSpeaker(String id) {
		int exists = -1;
		SQLiteDatabase db = null;
		try {
			db = this.getReadableDatabase();

			db.beginTransaction();

			try{
				Cursor cursor = db.rawQuery("select 1 from SPEAKERS where speaker_id=?", 
						new String[] { id });
				Boolean b = (cursor.getCount() > 0);
				exists = b? 1 : 0;
				cursor.close();
				db.setTransactionSuccessful();
			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return exists;
	}

	/**
	 * Checks if a row(Track) already exists with track_id == id.
	 * @param id track_id to be checked if already exists in the database.
	 * @return 1 if exists, 0 if doesn't exist, -1 if error checking if exists.
	 */
	public int existsTrack(String id) {
		int exists = -1;
		SQLiteDatabase db = null;
		try {
			db = this.getReadableDatabase();

			db.beginTransaction();

			try{
				Cursor cursor = db.rawQuery("select 1 from TRACKS where track_id=?", 
						new String[] { id });
				Boolean b = (cursor.getCount() > 0);
				exists = b? 1 : 0;
				cursor.close();
				db.setTransactionSuccessful();
			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return exists;
	}
	
	/**
	 * Returns whether a last updated time exists for a table
	 * @param table
	 * @return 1 if exists, 0 if doesn't exist, -1 if error checking if exists.
	 */
	public int existsStatusRow(String table) {
		int exists = -1;
		SQLiteDatabase db = null;
		try {
			db = this.getReadableDatabase();

			db.beginTransaction();

			try{
				Cursor cursor = db.rawQuery("select 1 from STATUS where name=?", 
						new String[] { table });
				Boolean b = (cursor.getCount() > 0);
				exists = b? 1 : 0;
				cursor.close();
				db.setTransactionSuccessful();
			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return exists;
	}
	
	/**
	 * Converts an array of Strings to a comma separated string
	 * @param array
	 * @return 
	 */
	public static String convertArrayToString(String[] array){
		String str = "";
		for (int i = 0;i<array.length; i++) {
			str = str+array[i];
			// Do not append comma at the end of last element
			if(i<array.length-1){
				str = str+",";
			}
		}
		return str;
	}
	/**
	 * Converts a comma separated string of elements into an array of Strings
	 * @param str
	 * @return
	 */
	public static String[] convertStringToArray(String str){
		String[] arr = str.split(",");
		return arr;
	}
}
