package at.schnedl.ues.hp;

import java.util.ArrayList;
import java.util.HashMap;

import at.schnedl.ues.hp.Competitor.Gender;

public class Category {

	private String time;
	private Gender gender;
	private String name;
	private String id;
	private String segment;
	private ArrayList<Competitor> starter;
	private ArrayList<Competitor> finisher;
	private ArrayList<String> starterHeader;
	private ArrayList<String> finisherHeader;
	private HashMap<String, String> legend;

	public Category(String time, Gender gen, String name, String id, String segment) {
		this.time = time;
		this.gender = gen;
		this.name = name;
		this.id = id;
		this.segment = segment;
		starter = new ArrayList<>();
		finisher = new ArrayList<>();
		starterHeader = new ArrayList<>();
		finisherHeader = new ArrayList<>();
		legend = new HashMap<>();
	}

	public String getTime() {
		return time;
	}

	public Gender getGender() {
		return gender;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
	
	public String getSegment() {
		return segment;
	}
	
	public ArrayList<Competitor> getStarter() {
		return starter;
	}

	public void addStarter(Competitor c) {
		starter.add(c);		
	}

	public ArrayList<Competitor> getFinisher() {
		return finisher;
	}

	public void addFinisher(Competitor c) {
		finisher.add(c);		
	}

	public void setStarterHeader(ArrayList<String> cols) {
		this.starterHeader = cols;
	}
	
	public ArrayList<String> getStarterHeader() {
		return starterHeader;
	}
	
	public void setFinisherHeader(ArrayList<String> cols) {
		this.finisherHeader = cols;
	}
	
	public ArrayList<String> getFinisherHeader() {
		return finisherHeader;
	}

	public void addLegend(String key, String val) {
		if (key.trim().length() > 0)
			legend.put(key, val);
	}
	
	public HashMap<String, String> getLegend() {
		return legend;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
