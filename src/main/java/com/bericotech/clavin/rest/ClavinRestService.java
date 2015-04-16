package com.bericotech.clavin.rest;

import java.io.File;
import java.io.IOException;

import com.bericotech.clavin.ClavinException;
import com.bericotech.clavin.gazetteer.query.Gazetteer;
import com.bericotech.clavin.gazetteer.query.LuceneGazetteer;
import com.bericotech.clavin.rest.command.IndexCommand;
import org.apache.lucene.queryparser.classic.ParseException;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.bazaarvoice.dropwizard.assets.ConfiguredAssetsBundle;
import com.bericotech.clavin.GeoParser;
import com.bericotech.clavin.extractor.ApacheExtractor;
import com.bericotech.clavin.rest.resource.ClavinRestResource;




public class ClavinRestService extends Service<ClavinRestConfiguration> {
	
    public static void main(String[] args) throws Exception {
        new ClavinRestService().run(args);
    }

    @Override
    public void initialize(Bootstrap<ClavinRestConfiguration> bootstrap) {
        bootstrap.setName("clavin-rest");
        //bootstrap.addBundle(new AssetsBundle("/assets/", "/"));
        bootstrap.addBundle(new ConfiguredAssetsBundle("/assets/", "/"));
        bootstrap.addCommand(new IndexCommand());
    }

    @Override
    public void run(ClavinRestConfiguration configuration,
                    Environment environment) throws ClassCastException, ClassNotFoundException, IOException, ParseException, ClavinException {
        
        environment.addResource(new ClavinRestResource(configuration));
    }
    
    

    
    

}
