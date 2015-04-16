package com.bericotech.clavin.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.bericotech.clavin.ClavinException;
import com.bericotech.clavin.GeoParser;
import com.bericotech.clavin.extractor.ApacheExtractor;
import com.bericotech.clavin.gazetteer.query.Gazetteer;
import com.bericotech.clavin.gazetteer.query.LuceneGazetteer;
import com.bericotech.clavin.resolver.ResolvedLocation;
import com.bericotech.clavin.rest.ClavinRestConfiguration;
import com.bericotech.clavin.rest.core.ResolvedLocations;
import com.bericotech.clavin.rest.core.ResolvedLocationsMinimum;

@Path("/v0")
@Produces(MediaType.APPLICATION_JSON)
public class ClavinRestResource {
    private GeoParser parser;    
    private ThreadLocal<GeoParser> threadId;
    		
    public ClavinRestResource(ClavinRestConfiguration configuration) {
        //this.parser = parser;
        
        final String luceneDir = configuration.getLuceneDir();
        final Integer maxHitDepth = configuration.getMaxHitDepth();
        final Integer maxContextWindow = configuration.getMaxContextWindow();
        // final Boolean fuzzy = configuration.getFuzzy();
    
        threadId = new ThreadLocal<GeoParser>() {
                    @Override protected GeoParser initialValue() {
                        try {
                        	
                        	Gazetteer gazetteer = new LuceneGazetteer(new File(luceneDir));
                        	
							ApacheExtractor extractor = new ApacheExtractor();
							
							return new GeoParser(extractor, gazetteer, maxHitDepth, maxContextWindow, false);
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ClavinException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						return null;
                }
            };
               
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
    
    	parser = threadId.get();
    	
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
    
    	parser = threadId.get();
    	
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
    
    
    
}