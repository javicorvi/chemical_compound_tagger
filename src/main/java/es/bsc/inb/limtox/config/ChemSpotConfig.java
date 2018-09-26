package es.bsc.inb.limtox.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import de.berlin.hu.chemspot.ChemSpot;
import de.berlin.hu.chemspot.ChemSpotFactory;

@PropertySource({ "classpath:chemspot.properties" })
@Configuration
public class ChemSpotConfig {
	@Autowired
    private Environment env; 
	static ChemSpot tagger = null;
	public ChemSpot getChemSpotTagger() {
		if (tagger == null) {
			tagger = ChemSpotFactory.createChemSpot(env.getProperty("limtox.chemspot.dict"), env.getProperty("limtox.chemspot.ids"), env.getProperty("limtox.chemspot.multiclass"));
			return tagger;
		}else {
			return tagger;
		}
	}
}
