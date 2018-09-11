package com.bericotech.clavin.rest;

import com.bazaarvoice.dropwizard.assets.ConfiguredAssetsBundle;
import com.bericotech.clavin.ClavinException;
import com.bericotech.clavin.gazetteer.query.Gazetteer;
import com.bericotech.clavin.gazetteer.query.LuceneGazetteer;
import com.bericotech.clavin.nerd.StanfordExtractor;
import com.bericotech.clavin.rest.command.IndexCommand;
import com.bericotech.clavin.rest.resource.ClavinRestResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.File;
import java.io.IOException;




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
        final String luceneDir = configuration.getLuceneDir();
        final Integer maxHitDepth = configuration.getMaxHitDepth();
        final Integer maxContextWindow = configuration.getMaxContextWindow();
        // final Boolean fuzzy = configuration.getFuzzy();
             
        Gazetteer gazetteer = new LuceneGazetteer(new File(luceneDir));
        StanfordExtractor extractor = new StanfordExtractor();

        environment.addResource(new ClavinRestResource(extractor, gazetteer, maxHitDepth, maxContextWindow, false));
    }

}
