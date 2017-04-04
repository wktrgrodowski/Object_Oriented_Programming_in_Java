package module6;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

/**
 * A class to represent AirportMarkers on a world map.
 * 
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 *         MOOC team
 *
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	int fillColor;
	int myRadius;

	public AirportMarker(Feature city) {
		super(((PointFeature) city).getLocation(), city.getProperties());
		PGraphics pg = new PGraphics();
		fillColor = pg.color(255, 255, 255);
		myRadius = 5;
	}

	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.fill(fillColor); 
		pg.ellipse(x, y, myRadius, myRadius);

	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		// show rectangle with title
		String title = getTitle();
		pg.pushStyle();

		pg.rectMode(PConstants.CORNER);

		pg.stroke(110);
		pg.fill(255, 255, 255);
		pg.rect(x, y + 15, pg.textWidth(title) + 6, 18, 5);

		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.fill(0);
		pg.text(title, x + 3, y + 18);

		pg.popStyle();
		// show routes

	}
	
	public void setFillColor(int color){
		fillColor = color;
	}
	
	public void setRadius(int radius){
		myRadius = radius;
	}

	public String getTitle() {
		String title = (String) getProperty("name") + " in " + (String) getProperty("city") + ", "
				+ (String) getProperty("country") + " - " + (String) getProperty("code");
		return title;

	}

}
