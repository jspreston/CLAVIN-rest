package com.bericotech.clavin.rest.resource;

import com.bericotech.clavin.GeoParser;
import com.bericotech.clavin.extractor.LocationExtractor;
import com.bericotech.clavin.extractor.LocationOccurrence;
import com.bericotech.clavin.gazetteer.query.Gazetteer;
import com.bericotech.clavin.resolver.ClavinLocationResolver;
import com.bericotech.clavin.resolver.ResolvedLocation;
import com.bericotech.clavin.rest.core.ResolvedLocations;
import com.bericotech.clavin.rest.core.ResolvedLocationsMinimum;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/v0")
@Produces(MediaType.APPLICATION_JSON)
public class ClavinRestResource {
    private final GeoParser parser;
    private final ClavinLocationResolver resolver;
    private int maxHitDepth;
    private int maxContextWindow;
    private boolean fuzzy;

    public ClavinRestResource(LocationExtractor extractor, Gazetteer gazetteer, int maxHitDepth, int maxContextWindow, boolean fuzzy) {

        this.parser = new GeoParser(extractor, gazetteer, maxHitDepth, maxContextWindow, fuzzy);
        this.resolver = new ClavinLocationResolver(gazetteer);
        this.maxHitDepth = maxHitDepth;
        this.maxContextWindow = maxContextWindow;
        this.fuzzy = fuzzy;
    }

    @GET
    public String index() {
        return "CLAVIN-rest 0.1";
    }
    
        
    @POST
    @Path("/geotag")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response extractAndResolveSimpleLocationsFromText(String text) {
    
        ResolvedLocations result = null;
        try {
            List<ResolvedLocation> resolvedLocations = parser.parse(text);
        result = new ResolvedLocations(resolvedLocations);
        
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity(e).build();
        }
      
        return Response.status(200).entity(result).build();
        
    }
    
    
    @POST
    @Path("/geotagmin")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response extractAndResolveSimpleShortLocationsFromText(String text) {
    
        ResolvedLocationsMinimum result = null;
        try {
            List<ResolvedLocation> resolvedLocations = parser.parse(text);
            result = new ResolvedLocationsMinimum(resolvedLocations);
            
        
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity(e).build();
        }
      
        return Response.status(200).entity(result).build();
        
    }

    @POST
    @Path("/resolve")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response disambiguate(List<LocationMention> locationMentions) {

        ResolvedLocationsMinimum result = null;
        List<LocationOccurrence> locationOccurences = locationMentions.stream()
                .map(loc -> new LocationOccurrence(loc.text, loc.position))
                .collect(Collectors.toList());
        try {
            List<ResolvedLocation> resolvedLocations = resolver.resolveLocations(locationOccurences, this.maxHitDepth, this.maxContextWindow, this.fuzzy);
            result = new ResolvedLocationsMinimum(resolvedLocations);

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity(e).build();
        }

        return Response.status(200).entity(result).build();
    }

    
}