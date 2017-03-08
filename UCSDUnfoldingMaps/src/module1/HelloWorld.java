package module1;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

/** HelloWorld
  * An application with two maps side-by-side zoomed in on different locations.
  * Author: UC San Diego Coursera Intermediate Programming team
  * @author Your name here
  * Date: July 17, 2015
  * */
public class HelloWorld extends PApplet
{
	/** Your goal: add code to display second map, zoom in, and customize the background.
	 * Feel free to copy and use this code, adding to it, modifying it, etc.  
	 * Don't forget the import lines above. */

	// You can ignore this.  It's to keep eclipse from reporting a warning
	private static final long serialVersionUID = 1L;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// IF YOU ARE WORKING OFFLINE: Change the value of this variable to true
	private static final boolean offline = false;
	
	/** The map we use to display our home town: La Jolla, CA */
	UnfoldingMap map1;
	
	/** The map you will use to display your home town */ 
	UnfoldingMap map2;
	
	private int yellow = color(255, 255, 0);
	private int red = color(255,0,0);
	
	private void addKey() {
		
	}

	public void setup() {
		size(850, 600, P2D);  // Set up the Applet window to be 800x600
		                      // The OPENGL argument indicates to use the 
		                      // Processing library's 2D drawing
		                      // You'll learn more about processing in Module 3

		// This sets the background color for the Applet.  
		// Play around with these numbers and see what happens!
		this.background(200, 200, 200);
		
		// Select a map provider
		//AbstractMapProvider provider = new Google.GoogleMapProvider();
		AbstractMapProvider provider = new Google.GoogleTerrainProvider();
		//AbstractMapProvider provider = new OpenStreetMap.OpenStreetMapProvider();
		//AbstractMapProvider provider = new Microsoft.HybridProvider();

		// Set a zoom level
		int zoomLevel = 10;
		
		if (offline) {
			// If you are working offline, you need to use this provider 
			// to work with the maps that are local on your computer.  
			provider = new MBTilesMapProvider(mbTilesString);
			// 3 is the maximum zoom level for working offline
			zoomLevel = 3;
		}
		
		// Create a new UnfoldingMap to be displayed in this window.  
		// The 2nd-5th arguments give the map's x, y, width and height
		// When you create your map we want you to play around with these 
		// arguments to get your second map in the right place.
		// The 6th argument specifies the map provider.  
		// There are several providers built-in.
		// Note if you are working offline you must use the MBTilesMapProvider
		map1 = new UnfoldingMap(this, 50, 50, 350, 500, provider);

		// The next line zooms in and centers the map at 
	    // 32.9 (latitude) and -117.2 (longitude)
	    map1.zoomAndPanTo(zoomLevel, new Location(32.9f, -117.2f));
		
		// This line makes the map interactive
		MapUtils.createDefaultEventDispatcher(this, map1);
		
		map2 = new UnfoldingMap(this, 450, 50, 350, 500, provider);
		map2.zoomAndPanTo(zoomLevel, new Location(50.04f, 19.95f));
		MapUtils.createDefaultEventDispatcher(this, map2);
		
		Location cracow = new Location(50.061389, 19.938333);
		Location wieliczka = new Location(49.986111, 20.061667);
		Location skawina = new Location(49.975, 19.828333);
		
		Feature cracowFeat = new PointFeature(cracow);
		Feature wieliczkaFeat = new PointFeature(wieliczka);
		Feature skawinaFeat = new PointFeature(skawina);
		
		cracowFeat.addProperty("Name", "Cracow");
		cracowFeat.addProperty("Founded", "1257");
		cracowFeat.addProperty("Population", "762448");
		
		wieliczkaFeat.addProperty("Name", "Wieliczka");
		wieliczkaFeat.addProperty("Founded", "1290");
		wieliczkaFeat.addProperty("Population", "22278");
		
		skawinaFeat.addProperty("Name", "Skawina");
		skawinaFeat.addProperty("Founded", "1364");
		skawinaFeat.addProperty("Population", "24246");
		
		List<Feature> pfList = new ArrayList<Feature>();
		pfList.add(cracowFeat);
		pfList.add(wieliczkaFeat);
		pfList.add(skawinaFeat);		
		
		List<Marker> markers = new ArrayList<Marker>();
		for (Feature pf : pfList) {
			Marker m = new SimplePointMarker(((PointFeature) pf).getLocation(), pf.getProperties());
			markers.add(m);
		}
		
		for (Marker m : markers){
			if( Integer.parseInt((String) m.getProperty("Population")) > 100000){
				m.setColor(red);
			}
			else {
				m.setColor(yellow);
			}
		}
		
		map2.addMarkers(markers);
	}

	/** Draw the Applet window.  */
	public void draw() {
		map1.draw();
		map2.draw();
		addKey();
	}

	
}
