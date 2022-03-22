package at.schnedl.ues.hp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.schnedl.ues.hp.Competitor.CompType;
import at.schnedl.ues.hp.Competitor.Gender;

public class WebReader {

	private String mainUrl;
	ArrayList<Category> categories;

	public WebReader(String url) {
		this.mainUrl = url;
		categories = new ArrayList<>();
	}

	public static void main(String[] args) throws Exception {
		//WebReader wr = new WebReader("http://skateaustria.vs91-250-98-130.cloud-he.de/img/competition/5.2019_20/090/");
		WebReader wr = new WebReader("http://skateaustria.vs91-250-98-130.cloud-he.de/img/competition/7.2021_22/130/");
		
		wr.readWeb();
	}
	
	public ArrayList<Category> getCategories() {
		return categories;
	}

	void readWeb() throws IOException {
		URL url = new URL(mainUrl);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.ISO_8859_1));
		boolean planB = true;
		
		// reading categories from main page
		categories = new ArrayList<>();
		String line;
		boolean inZeitplan = false;
		while ((line = br.readLine()) != null) {
			if (planB) {
				categories = readSchedulePlanB(br);
			}
			else {
				if (inZeitplan) {
					categories = readSchedule(br);
					inZeitplan = false;
				}
				else
					if (line.contains("Zeitplan")) 
						inZeitplan = true;
			}
		}
		br.close();
		
		categories = reorderCategories(categories);
		
		// reading separate cat files
		for (Category cat : categories) {
			readCategory(cat);
		}
		
		// debug print out categories
//		if (true) {
//			for (Category cat : categories) {
//				System.out.println("CAT: " + cat.getName() + " @" + cat.getTime());
//				
//				System.out.println(" Starter:");
//				for (Competitor c : cat.getStarter()) {
//					System.out.print(" - " + c.getType() + " ### ");
//					for (int i = 0; i < cat.getStarterHeader().size(); i++) {
//						System.out.print(" - " + cat.getStarterHeader().get(i) + ":" + c.getValue(i));
//					}
//					System.out.println();
//				}
//				
//				System.out.println(" Finisher:");
//				for (Competitor c : cat.getFinisher()) {
//					System.out.print(" - " + c.getType() + " ### ");
//					for (int i = 0; i < cat.getFinisherHeader().size(); i++) {
//						System.out.print(" - " + cat.getFinisherHeader().get(i) + ":" + c.getValue(i));
//					}
//					System.out.println();
//				}
//				
//			}
//		}
	}

	private ArrayList<Category> reorderCategories(ArrayList<Category> oldCats) {
		
		ArrayList<Category> ret = new ArrayList<>();
		
		LocalDate ld = LocalDate.now();
		
		
//		// Freitag
//		ret.add(getCatByID(oldCats, "SEG001"));
//		ret.add(getCatByID(oldCats, "SEG002"));
//		ret.add(getCatByID(oldCats, "SEG003"));
//		ret.add(getCatByID(oldCats, "SEG004"));
//		ret.add(getCatByID(oldCats, "SEG005"));
//		ret.add(getCatByID(oldCats, "SEG006"));
		
		
		// Samstag
		if (ld.getDayOfMonth() <= 19) {
			attRet(ret, getCatByID(oldCats, "SEG007"), "10:00");
			
			attRet(ret, getCatByID(oldCats, "SEG008"), "10:30");
			attRet(ret, getCatByID(oldCats, "SEG009"), "10:30");
			attRet(ret, getCatByID(oldCats, "SEG010"), "12:00");
			attRet(ret, getCatByID(oldCats, "SEG011"), "12:00");
			attRet(ret, getCatByID(oldCats, "SEG012"), "12:45");
			
			attRet(ret, getCatByID(oldCats, "SEG016"), "15:15");
			attRet(ret, getCatByID(oldCats, "SEG018"), "15:15");
			attRet(ret, getCatByID(oldCats, "SEG021"), "15:45");
			attRet(ret, getCatByID(oldCats, "SEG025"), "16:15");
			attRet(ret, getCatByID(oldCats, "SEG027"), "16:15");
			
			attRet(ret, getCatByID(oldCats, "SEG030"), "16:30");
			attRet(ret, getCatByID(oldCats, "SEG031"), "16:30");
			attRet(ret, getCatByID(oldCats, "SEG032"), "16:50");
			attRet(ret, getCatByID(oldCats, "SEG033"), "16:50");
		}
		
		// Sonntag
		if (ld.getDayOfMonth() == 20) {
			attRet(ret, getCatByID(oldCats, "SEG013"), "10:00");
			attRet(ret, getCatByID(oldCats, "SEG014"), "10:00");
			attRet(ret, getCatByID(oldCats, "SEG015"), "10:45");
			
			attRet(ret, getCatByID(oldCats, "SEG029"), "11:30");
			attRet(ret, getCatByID(oldCats, "SEG017"), "11:40");
			attRet(ret, getCatByID(oldCats, "SEG019"), "11:40");
			attRet(ret, getCatByID(oldCats, "SEG020"), "12:10");
			
			attRet(ret, getCatByID(oldCats, "SEG022"), "12:45");
			attRet(ret, getCatByID(oldCats, "SEG023"), "13:15");
			attRet(ret, getCatByID(oldCats, "SEG024"), "13:15");
			attRet(ret, getCatByID(oldCats, "SEG026"), "13:40");
			attRet(ret, getCatByID(oldCats, "SEG028"), "13:40");
		}
		
		return ret;
	}

	private void attRet(ArrayList<Category> ret, Category cat, String time) {
		cat.setTime(time);
		ret.add(cat);
	}

	private Category getCatByID(ArrayList<Category> oldCats, String id) {
		for (Category cat : oldCats) {
//			System.out.println("compare " + id + " with " + cat.getId());
			if (cat.getId().equals(id))
				return cat;
		}
		System.out.println("no cat found for " + id);
		return null;
	}

	enum ParseState {none, starter, finisher, legend};
	private void readCategory(Category cat) throws IOException {
		String line;
		ParseState state = ParseState.none;

		Pattern patEinlaufgruppe = Pattern.compile(".*<td>.*(" + getTxt("Einlaufgruppe") + " [0-9]*)</tr>");
		
		URL caturl = new URL(mainUrl + "/" + cat.getId() + ".HTM"); 
		//URL caturl = new URL(mainUrl + "/" + "SEG001" + ".HTM");
		BufferedReader br = new BufferedReader(new InputStreamReader(caturl.openStream(), StandardCharsets.ISO_8859_1));
		
		while ((line = br.readLine()) != null) {
			
			if (line.contains(getTxt("Startreihenfolge"))) {
				state = ParseState.starter;
				continue;
			}
			
			if (line.contains(getTxt("Ergebnisse"))) {
				state = ParseState.finisher;
				br.readLine(); // ignore empty table line
				continue;
			}
			
			if (line.contains("<td>Legend</td>")) {
				state = ParseState.legend;
				continue;
			}
			
			if (state == ParseState.starter) {
				// read column headers of table
				if (line.startsWith("<tr><th>")) {
					cat.setStarterHeader(readCols(line, true));
					continue;
				}

				// match "Einlaufgruppe
				Matcher m = patEinlaufgruppe.matcher(line);
				if (m.matches()) {
					Competitor c = new Competitor(CompType.einlaufgruppe);
					c.setOpts("0", m.group(1), "");
					cat.addStarter(c);
					continue;
				}

				// match Competitor
				if (line.contains("isufs.org")) {
					Competitor c = new Competitor(CompType.starter);
					c.setOpts(readCols(line, false));
					cat.addStarter(c);
					continue;
				}
			}
			
			if (state == ParseState.finisher) {
				// read column headers of table
				if (line.startsWith("<tr><th>")) {
					cat.setFinisherHeader(readCols(line, true));
					continue;
				}
				
				// match Competitor
				if (line.contains("isufs.org")) {
					Competitor c = new Competitor(CompType.finisher);
					c.setOpts(readCols(line, false));
					cat.addFinisher(c);
					continue;
				}
			}
			
			if (state == ParseState.legend && line.startsWith("<tr>")) {
				ArrayList<String> r = readCols(line, false);
				cat.addLegend(r.get(0), r.get(1));
				continue;
			}

			// fetch end of list
			if (line.startsWith("</table>")) {
				state = ParseState.none;
				continue;
			}

			// remove later:
			if (state.ordinal() >= ParseState.finisher.ordinal())
				System.out.println("R: " + line);

		}
		
		br.close();
	}

	private String getTxt(String de) {
		if (false)
			return de;
		
		if (de.equals("Einlaufgruppe"))
			return "Warm-Up Group";
		else if (de.equals("Startreihenfolge"))
			return "Starting Order";
		else if (de.equals("Ergebnisse"))
			return "Result";
		
		return null;
	}

	private ArrayList<String> readCols(String line, boolean isHdr) {
		ArrayList<String> ret = new ArrayList<>();
		String el = isHdr ? "th" : "td";
		
		int pos = 0;
		String strElOpenStart = "<" + el;
		String strElOpenEnd = ">";
		String strElClose = "</" + el + ">";
		
		while (true) {
			int elOpenStart = line.indexOf(strElOpenStart, pos);
			int elClose = line.indexOf(strElClose, elOpenStart);
			int elOpenEnd = line.indexOf(strElOpenEnd, elOpenStart) + 1;
			pos = elClose + strElClose.length();
			
			if (elOpenStart < 0)
				break;
			
			String str = line.substring(elOpenEnd, elClose);
			if (str.contains("isufs.org"))
				str = str.substring(str.indexOf(">") +1, str.lastIndexOf("<"));
			
			int brCheck = str.indexOf("<br />");
			if (brCheck >= 0) 
				str = str.substring(0, brCheck);
			
			ret.add(str);
		}
	
		return ret;
	}

	private ArrayList<Category> readSchedule(BufferedReader br) throws IOException {
		ArrayList<Category> ret = new ArrayList<>();
		String line;
		Pattern p = Pattern.compile(".*bgcolor='(.*)'.*>([0-9:]*):00.*<td>(.*)</td>.*href=(.*).HTM.*");
		while (!(line = br.readLine()).contains("</table>")) {
			Matcher m = p.matcher(line);
			if (m.matches()) {
				Gender gen = m.group(1).equals("#FF8888") ? Gender.female : Gender.male;
				String time = m.group(2);
				String name = m.group(3);
				String id = m.group(4);
				ret.add(new Category(time, gen, name, id, "N/A"));
			}
		}
		return ret;
	}
	
	private ArrayList<Category> readSchedulePlanB(BufferedReader br) throws IOException {
		ArrayList<Category> ret = new ArrayList<>();
		
		Pattern p1 = Pattern.compile(".*bgcolor='(.*)'> <td>([a-zA-Z\\ äöü0-9]*)</td>.*CAT[0-9]{3}.*");
		Pattern p2 = Pattern.compile(".*bgcolor='(.*)'> <td></td><td>([A-Za-z\\ äöü0-9]*)</td>.*href=(.*).HTM.*");
		
		String line;
		String name = "";
		while (!(line = br.readLine()).contains("</html>")) {
			Matcher m1 = p1.matcher(line);
			if (m1.matches()) {
				name = m1.group(2);
				continue;
			}
			
			Matcher m2 = p2.matcher(line);
			if (m2.matches()) {
				
				Gender gen = getGender(m2.group(1));
				String segment = m2.group(2);
				String id = m2.group(3);
				ret.add(new Category("", gen, name, id, segment));
			}

		}
		return ret;
	}

	private Gender getGender(String group) {
		if (group.contains("88FFFF"))
			return Gender.male;
		else if (group.contains("FF8888"))
			return Gender.female;
		else if (group.contains("88FF88"))
			return Gender.pair;
		else 
			return null;
	}

}
