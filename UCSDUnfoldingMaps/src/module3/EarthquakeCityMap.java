package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;
import processing.core.PFont;
//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/**
 * EarthquakeCityMap An application with an interactive map displaying
 * earthquake data. Author: UC San Diego Intermediate Software Development MOOC
 * team
 * 
 * @author Your name here Date: July 17, 2015
 */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this. It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;

	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/**
	 * This is where to find the local tiles, for working without an Internet
	 * connection
	 */
	public static String mbTilesString = "blankLight-1-3.mbtiles";

	// The map
	private UnfoldingMap map;

	// feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	public void setup() {
		size(950, 600, OPENGL);
		//size(displayWidth, displayHeight, OPENGL);

		if (offline) {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
			earthquakesURL = "2.5_week.atom"; // Same feed, saved Aug 7, 2015,
												// for working offline
		} else {
			AbstractMapProvider mapProvider = new Microsoft.HybridProvider();
			//AbstractMapProvider mapProvider = new OpenStreetMap.OpenStreetMapProvider();
			//AbstractMapProvider mapProvider = new Google.GoogleMapProvider();
			map = new UnfoldingMap(this, 200, 50, 700, 500, mapProvider);
			//map = new UnfoldingMap(this, 50, 50, getSize().width-100, getSize().height-100, new Google.GoogleMapProvider());// OpenStreetMap.OpenStreetMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
			//earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
		}

		map.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this, map);

		// The List you will populate with new SimplePointMarkers
		List<Marker> markers = new ArrayList<Marker>();

		// Use provided parser to collect properties for each earthquake
		// PointFeatures have a getLocation method
		List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);

		for (PointFeature pf : earthquakes) {
			markers.add(createMarker(pf));
		}

		// These print statements show you (1) all of the relevant properties
		// in the features, and (2) how to get one property and use it
		if (earthquakes.size() > 0) {
			PointFeature f = earthquakes.get(0);
			System.out.println(f.getProperties());
			Object magObj = f.getProperty("magnitude");
			float mag = Float.parseFloat(magObj.toString());
			// PointFeatures also have a getLocation method
		}

		for (Marker m : markers) {
			setMarkerLook((SimplePointMarker) m);
		}

		map.addMarkers(markers);
	}

	// A suggested helper method that takes in an earthquake feature and
	// returns a SimplePointMarker for that earthquake
	private SimplePointMarker createMarker(PointFeature feature) {
		return new SimplePointMarker(feature.getLocation(), feature.getProperties());
	}

	private void setMarkerLook(SimplePointMarker m) {
		int blue = color(0, 0, 255);
		int yellow = color(255, 255, 0);
		int red = color(255, 0, 0);
		if (Float.parseFloat(m.getProperty("magnitude").toString()) < 4.0) {
			m.setRadius(5f);
			m.setColor(blue);
		}
		else if (Float.parseFloat(m.getProperty("magnitude").toString()) >= 4.0 && (Float.parseFloat(m.getProperty("magnitude").toString()) < 5.0)) {
			m.setRadius(10f);
			m.setColor(yellow);
		}
		else {
			m.setRadius(15f);
			m.setColor(red);
		}
	}

	public void draw() {
		background(10);
		map.draw();
		addKey();
	}

	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() {
		PFont mono = createFont("Comic Sans MS", 16);//loadFont("andalemo.ttf");
		textFont(mono);
		int blue = color(0, 0, 255);
		int yellow = color(255, 255, 0);
		int red = color(255, 0, 0);
		rect(20, 50, 150, 160, 7);
		fill(0, 102, 153);
		text("Earthquakes key", 39, 75);
		text("5.0+ magnitude", 55, 115);
		text("4.0+ magnitude", 55, 155);
		text("4.0- magnitude", 55, 195);
		fill(red);
		ellipse(40, 110, 15, 15);
		fill(yellow);
		ellipse(40, 150, 10, 10);
		fill(blue);
		ellipse(40, 190, 5, 5);
		fill(255, 255, 255);
		
	}
}
