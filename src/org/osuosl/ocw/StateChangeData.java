package org.osuosl.ocw;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

// Class used for saving the current state of important objects when the orientation
// of the device is switched.
public class StateChangeData {

	private ArrayList<Date> DAYS;
	private ArrayList<Event> mEvents; 
	private HashMap<Integer, Speaker> mSpeakers;
	private HashMap<Integer, Track> mTracks;
	private Date mCurrentData;
	private int mDescriptionVisibility;
	private int mBioVisibility;
	private boolean mDetail;
	private int flipperTab;
	private String[] mSpeakerIds;
	
	public StateChangeData(ArrayList<Date> DAYS, ArrayList<Event> mEvents, 
			HashMap<Integer, Speaker> mSpeakers, HashMap<Integer, Track> mTracks,
			Date mCurrentDate, int mDescriptionVisibility, int mBioVisibility,
			boolean mDetail, int flipperTab, String[] mSpeakerIds){
		
		this.DAYS = DAYS;
		this.mEvents = mEvents;
		this.mSpeakers = mSpeakers;
		this.mTracks = mTracks;
		this.mCurrentData = mCurrentDate;
		this.mDescriptionVisibility = mDescriptionVisibility;
		this.mBioVisibility = mBioVisibility;
		this.mDetail = mDetail;
		this.flipperTab = flipperTab;
		this.mSpeakerIds = mSpeakerIds;
		
	}

	// Getters and Setters auto-generated by eclipse //
	
	public ArrayList<Date> getDAYS() {
		return DAYS;
	}


	public ArrayList<Event> getmEvents() {
		return mEvents;
	}


	public HashMap<Integer, Speaker> getmSpeakers() {
		return mSpeakers;
	}


	public HashMap<Integer, Track> getmTracks() {
		return mTracks;
	}


	public Date getmCurrentData() {
		return mCurrentData;
	}


	public int getmDescriptionVisibility() {
		return mDescriptionVisibility;
	}


	public int getmBioVisibility() {
		return mBioVisibility;
	}


	public boolean getmDetail() {
		return mDetail;
	}


	public int getFlipperTab() {
		return flipperTab;
	}

	public String[] getmSpeakerIds() {
		return mSpeakerIds;
	}

	

}
