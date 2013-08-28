package org.osuosl.ocw;

import android.graphics.Color;

public class Track {

	private int track_id;
	private String track_title;
	private int color;
	private int color_text;
	
	
	public Track() {
		
		this.track_id = -1;
		this.track_title = "";
		this.color = Color.parseColor("#116db6"); //TODO Match color chosen for event list
		this.color_text = Color.parseColor("white"); //TODO Match color chosen for event list
	}
	
	public Track(int track_id, String track_title, int color, int color_text) {
		
		this.track_id = track_id;
		this.track_title = track_title;
		this.color = color;
		this.color_text = color_text;
	}
	
	public Track(String track_id, String track_title, int color, int color_text) {
		
		this.track_id = Integer.parseInt(track_id);
		this.track_title = track_title;
		this.color = color;
		this.color_text = color_text;
	}

	public int getTrack_id() {
		return track_id;
	}

	public void setTrack_id(int track_id) {
		this.track_id = track_id;
	}

	public String getTrack_title() {
		return track_title;
	}

	public void setTrack_title(String track_title) {
		this.track_title = track_title;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getColor_text() {
		return color_text;
	}

	public void setColor_text(int color_text) {
		this.color_text = color_text;
	}
	
	
}
