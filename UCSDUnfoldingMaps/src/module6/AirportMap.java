package module6;

import java.awt.Color;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;

/**
 * An applet that shows airports (and routes) on a world map.
 * 
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 *         MOOC team
 *
 */
public class AirportMap extends PApplet {

	UnfoldingMap map;
	private List<Marker> airportList;
	List<Marker> routeList;

	private CommonMarker lastSelected;
	private CommonMarker lastClicked;

	public void setup() {
		// setting up PAppler
		size(800, 600, OPENGL);

		// setting up map and default events
		map = new UnfoldingMap(this, 50, 50, 750, 550);
		MapUtils.createDefaultEventDispatcher(this, map);

		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");

		// list for markers, hashmap for quicker access when matching with
		// routes
		airportList = new ArrayList<Marker>();
		HashMap<Integer, Location> airports = new HashMap<Integer, Location>();

		// create markers from features
		for (PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature);

			m.setRadius(5);
			airportList.add(m);

			// put airport in hashmap with OpenFlights unique id for key
			airports.put(Integer.parseInt(feature.getId()), feature.getLocation());

		}

		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		for (ShapeFeature route : routes) {

			// get source and destination airportIds
			int source = Integer.parseInt((String) route.getProperty("source"));
			int dest = Integer.parseInt((String) route.getProperty("destination"));

			// get locations for airports on route
			if (airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
			}

			SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());

			System.out.println(sl.getProperties());

			sl.setHidden(true);

			// UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
			routeList.add(sl);
		}

		// UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		map.addMarkers(routeList);

		map.addMarkers(airportList);

	}

	public void mouseMoved() {
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;

		}
		selectMarkerIfHover(airportList);
		// loop();
	}

	private void selectMarkerIfHover(List<Marker> markers) {
		// Abort if there's already a marker selected
		if (lastSelected != null) {
			return;
		}

		for (Marker m : markers) {
			AirportMarker marker = (AirportMarker) m;
			if (marker.isInside(map, mouseX, mouseY)) {
				lastSelected = marker;
				marker.setSelected(true);
				/*
				 * System.out.println(lastSelected.getProperties());
				 * System.out.println(m.getId());
				 * System.out.println(marker.getId());
				 * System.out.println(lastSelected.getId());
				 */
				return;
			}
		}
	}

	public void mouseClicked() {
		if (lastClicked != null) {
			unhideMarkers();
			hideRoutes();
			lastClicked = null;
		} else if (lastClicked == null) {
			checkAirportsForClick();
		}
	}

	private void checkAirportsForClick() {
		if (lastClicked != null)
			return;
		// Loop over the airport markers to see if one of them is selected
		for (Marker marker : airportList) {
			if (!marker.isHidden() && marker.isInside(map, mouseX, mouseY)) {
				lastClicked = (CommonMarker) marker;
				// Hide all the airports without routes
				hideAllWithoutRoutes();
				return;
			}
		}
	}

	private void hideAllWithoutRoutes() {
		String id = (String) lastClicked.getProperty("id");
		System.out.println(id);
		List<String> connectedAirports = new ArrayList<String>();
		for (Marker m : routeList) {
			if (((String) m.getProperty("destination")).equals(id)) {
				connectedAirports.add((String) m.getProperty("source"));
				m.setHidden(false);
			} else if (m.getProperty("source").equals(id)) {
				connectedAirports.add((String) m.getProperty("destination"));
				m.setHidden(false);
			}
		}
		for (Marker marker : airportList) {
			if (!connectedAirports.contains((String) marker.getProperty("id"))) {
				marker.setHidden(true);
				if (((String) marker.getProperty("id")).equals(id)) {
					AirportMarker am = (AirportMarker) marker;
					am.setHidden(false);
					am.setFillColor(Color.RED.getRGB());
					am.setRadius(20);
				}
			}
		}
	}

	// loop over and unhide all markers
	private void unhideMarkers() {
		for (Marker marker : airportList) {
			AirportMarker am = (AirportMarker) marker;
			am.setHidden(false);
			am.setFillColor(Color.BLACK.getRGB());
			am.setRadius(5);
		}
	}

	// loop over and hide all routes
	private void hideRoutes() {
		for (Marker m : routeList) {
			m.setHidden(true);
		}
	}

	public void draw() {
		background(0);
		map.draw();

	}

}
