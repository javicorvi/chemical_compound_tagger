package es.bsc.inb.limtox.services;

import de.berlin.hu.chemspot.ChemSpot;
import de.berlin.hu.chemspot.ChemSpotFactory;
import de.berlin.hu.chemspot.Mention;

public class ChemSpotBasicExample {

	public static void main(String[] args) {
		ChemSpot tagger = ChemSpotFactory.createChemSpot("/home/jcorvi/chemspot-2.0/dict.zip", "/home/jcorvi/chemspot-2.0/ids.zip", "/home/jcorvi/chemspot-2.0/multiclass.bin");
		String text = "The abilities of LHRH and a potent LHRH agonist ([D-Ser-(But),6, " +
		  "des-Gly-NH210]LHRH ethylamide) inhibit FSH responses by rat " +
		  "granulosa cells and Sertoli cells in vitro have been compared.";
		for (Mention mention : tagger.tag(text)) {
			System.out.printf("%d\t%d\t%s\t%s\t%s,\t%s%n", 
					mention.getStart(), mention.getEnd(), mention.getText(), 
					mention.getCHID(), mention.getSource(), mention.getType().toString(), mention.getCAS(),mention.getCHEB(),mention.getDRUG(),mention.getFDA(),
					mention.getINCH());
		}
	}
}
