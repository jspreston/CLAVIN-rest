package com.bericotech.clavin.rest.core;

import com.bericotech.clavin.resolver.ResolvedLocation;

public class ResolvedLocationMinimum {
	
	public final int geonameID;
	public final String name;
	public final String countryCode;
	public final double latitude;
	public final double longitude;
	public final String featureCode;
	public final String featureClass;
	public final float confidence;
	public final long population;
	public final String matchedName;
	public final String sourceText;
	public final long sourceOffset;
	
	ResolvedLocationMinimum(ResolvedLocation rl) {
		this.geonameID = rl.getGeoname().getGeonameID();
		this.name = rl.getGeoname().getName();
		this.countryCode = rl.getGeoname().getPrimaryCountryCode().toString();
		this.latitude = rl.getGeoname().getLatitude();
		this.longitude = rl.getGeoname().getLongitude();
		this.featureCode = rl.getGeoname().getFeatureCode().toString();
		this.featureClass = rl.getGeoname().getFeatureClass().toString();
		this.confidence = rl.getConfidence();
		this.population = rl.getGeoname().getPopulation();
		this.matchedName = rl.getMatchedName();
		this.sourceText = rl.getLocation().getText();
		this.sourceOffset = rl.getLocation().getPosition();
	}

}
