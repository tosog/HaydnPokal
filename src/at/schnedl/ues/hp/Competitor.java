package at.schnedl.ues.hp;

import java.util.ArrayList;
import java.util.Arrays;

public class Competitor {
	
	public enum Gender {male, female, pair}
	public enum CompType {einlaufgruppe, starter, finisher}

	private CompType type;
	private ArrayList<String> values;

	public Competitor(CompType t) {
		type = t;
	}

	public void setOpts(String...strings) {
		setOpts(new ArrayList<String>(Arrays.asList(strings)));
		
	}

	public void setOpts(ArrayList<String> cols) {
		this.values = cols;
	}
	
//	public String getHead(int idx) {
//		return header.get(idx);
//	}
//	
//	public int getHdrLength() {
//		return header.size();
//	}
	
	public String getValue(int idx) {
		return values.get(idx);
	}
	
//	public String getValue(String hdr) {
//		for (int cnt = 0; cnt < header.size(); cnt++) {
//			if (getHead(cnt).equals(hdr)) {
//				return getValue(cnt);
//			}
//		}
//		return null;
//	}
	
	public CompType getType() {
		return type;
	}
}
