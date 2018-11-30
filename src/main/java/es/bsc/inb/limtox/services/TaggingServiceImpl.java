
package es.bsc.inb.limtox.services;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.berlin.hu.chemspot.ChemSpot;
import de.berlin.hu.chemspot.Mention;
import edu.stanford.nlp.objectbank.ObjectBank;
import es.bsc.inb.limtox.config.ChemSpotConfig;
import es.bsc.inb.limtox.model.LTKBChemicalCompound;

@Service
class TaggingServiceImpl implements TaggingService {

	static final Logger taggingLog = Logger.getLogger("taggingLog");
	
	@Autowired
	private ChemSpotConfig chemSpotConfig;
	
	private Map<String, LTKBChemicalCompound> ltkbDictionary = new HashMap<String, LTKBChemicalCompound>();
	
	private Map<String, LTKBChemicalCompound> ltkbDictionaryByName = new HashMap<String, LTKBChemicalCompound>();
	
	public void execute(String propertiesParametersPath) {
		try {
			
			taggingLog.info("Tagging chemical compounds with properties :  " +  propertiesParametersPath);
			Properties propertiesParameters = this.loadPropertiesParameters(propertiesParametersPath);
			taggingLog.info("Input directory with the articles to tag : " + propertiesParameters.getProperty("inputDirectory"));
			taggingLog.info("Outup directory with the relevant articles : " + propertiesParameters.getProperty("outputDirectory"));
			taggingLog.info("LTKB dictionary used : " + propertiesParameters.getProperty("ltkbDictionary"));
			
			
			String inputDirectoryPath = propertiesParameters.getProperty("inputDirectory");
			String outputDirectoryPath = propertiesParameters.getProperty("outputDirectory");
			String ltkbDictionaryPath = propertiesParameters.getProperty("ltkbDictionary");
			
			Integer index_id = new Integer(propertiesParameters.getProperty("index_id"));
			Integer index_text_to_tag = new Integer(propertiesParameters.getProperty("index_text_to_tag"));
			
			
			File inputDirectory = new File(inputDirectoryPath);
		    if(!inputDirectory.exists()) {
		    	return ;
		    }
		    if (!Files.isDirectory(Paths.get(inputDirectoryPath))) {
		    	return ;
		    }
		    File outputDirectory = new File(outputDirectoryPath);
		    if(!outputDirectory.exists())
		    	outputDirectory.mkdirs();
		    
		    loadLTKBChemicalCompounds(ltkbDictionaryPath);
		    
		    
		    List<String> filesProcessed = readFilesProcessed(outputDirectoryPath); 
		    BufferedWriter filesPrecessedWriter = new BufferedWriter(new FileWriter(outputDirectoryPath + File.separator + "list_files_processed.dat", true));
		    File[] files =  inputDirectory.listFiles();
			for (File file_to_classify : files) {
				if(file_to_classify.getName().endsWith(".txt") && filesProcessed!=null && !filesProcessed.contains(file_to_classify.getName())){
					taggingLog.info("Processing file  : " + file_to_classify.getName());
					String fileName = file_to_classify.getName();
					String outputFilePath = outputDirectory + File.separator + fileName;
					BufferedWriter outPutFile = new BufferedWriter(new FileWriter(outputFilePath));
					for (String line : ObjectBank.getLineIterator(file_to_classify.getAbsolutePath(), "utf-8")) {
						try {
							String[] data = line.split("\t");
							String id =  data[index_id];
							String text =  data[index_text_to_tag];
							tagging(id, text, outPutFile, file_to_classify.getName());
						}  catch (Exception e) {
							taggingLog.error("Error tagging the document line " + line + " belongs to the file: " +  fileName,e);
						}
					}
					outPutFile.close();
					filesPrecessedWriter.write(file_to_classify.getName()+"\n");
					filesPrecessedWriter.flush();
				}
			}
			filesPrecessedWriter.close();
		}  catch (Exception e) {
			taggingLog.error("Generic error in the classification step",e);
		}
	}
	
	/**
	 * Load the LTKB dictionary
	 * @param ltkbDictionaryPath
	 */
	private void loadLTKBChemicalCompounds(String ltkbDictionaryPath) {
		try {
			File excelFile = new File(ltkbDictionaryPath);
		    FileInputStream fis = new FileInputStream(excelFile);
		    // we create an XSSF Workbook object for our XLSX Excel File
		    HSSFWorkbook workbook = new HSSFWorkbook(fis);
		    // we get first sheet
		    HSSFSheet sheet = workbook.getSheetAt(0);
		    for (Row myrow : sheet) {
		        if(myrow.getRowNum()!=0) {
		        	
		        	for (Cell mycell : myrow) {
		                mycell.setCellType(CellType.STRING);
		            }
		        	
		        	LTKBChemicalCompound ltkbChemicalCompound = new LTKBChemicalCompound();
			        
			    	ltkbChemicalCompound.setLtkbKey(myrow.getCell(0).getStringCellValue());
			        ltkbChemicalCompound.setPubChemId(myrow.getCell(1).getStringCellValue());
			        ltkbChemicalCompound.setChemicalCompoundName(myrow.getCell(2).getStringCellValue());
			        ltkbChemicalCompound.setApprovalYear(myrow.getCell(3).getStringCellValue());
			        ltkbChemicalCompound.setDiliSeverityDescription(myrow.getCell(4).getStringCellValue());
			        ltkbChemicalCompound.setDiliSeverityClass(myrow.getCell(5).getStringCellValue());
			        ltkbChemicalCompound.setLabelSection(myrow.getCell(6).getStringCellValue());
			        ltkbChemicalCompound.setAdjudicatedDILI(myrow.getCell(7).getStringCellValue());
			        ltkbChemicalCompound.setvDILIConcernLabel(myrow.getCell(8).getStringCellValue());
			        ltkbChemicalCompound.setGreeneAnnotation(myrow.getCell(9).getStringCellValue());
			        ltkbChemicalCompound.setSakatisAnnotation(myrow.getCell(10).getStringCellValue());
			        ltkbChemicalCompound.setXuAnnotation(myrow.getCell(11).getStringCellValue());
			        ltkbChemicalCompound.setZhuAnnotation(myrow.getCell(12).getStringCellValue());
			        ltkbChemicalCompound.setSmiles(myrow.getCell(13).getStringCellValue());
			        //ltkbChemicalCompound.setUrl(myrow.getCell(14).getStringCellValue());
			        ltkbChemicalCompound.setVerDisp(myrow.getCell(15).getStringCellValue());
			        
			        ltkbDictionary.put(ltkbChemicalCompound.getPubChemId(), ltkbChemicalCompound);
			        ltkbDictionaryByName.put(ltkbChemicalCompound.getChemicalCompoundName(), ltkbChemicalCompound);
		        }
		    }
		    workbook.close();
		    fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			taggingLog.error("Processing ltkb dictionary , file not found " , e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			taggingLog.error("Processing ltkb dictionary , io exception  " , e);
		}

	    
	}
		
	


	/**
	 * Findings of ChemicalCompunds with synonyms
	 * 
	 * getCHID()	ChemIDplus
	   getCHEB()	ChEBI
	   getCAS()	CAS registry number
	   getPUBC()	PubChem compound
	   getPUBS()	PubChem substance
	   getINCH()	InChI
	   getDRUG()	DrugBank
	   getHMBD()	Human Metabolome Database
	   getKEGG()	KEGG compound
	   getKEGD()	KEGG drug
	   getMESH()	MeSH
	 * 
	 * @param sourceId
	 * @param document_model
	 * @param first_finding_on_document
	 * @param section
	 * @param sentence_text
	 * @return
	 * @throws MoreThanOneEntityException
	 */
	private void tagging(String id, String text_to_tag, BufferedWriter output, String fileName) {
		ChemSpot tagger = chemSpotConfig.getChemSpotTagger();
		for (Mention mention : tagger.tag(text_to_tag)) {
			try {
				LTKBChemicalCompound ltkb = ltkbDictionary.get(mention.getPUBC());
				if(ltkb!=null) {
					output.write(id + "\t" + mention.getStart() + "\t" + mention.getEnd() + "\t" +  
							mention.getText() + "\t" + mention.getType().toString() + "\t"  +  mention.getSource() + "\t"  +  
							mention.getCHID() + "\t" + mention.getCHEB() + "\t" + mention.getCAS() + "\t" + 
							mention.getPUBC() + "\t" + mention.getPUBS() + "\t" + mention.getINCH() + "\t" + 
							mention.getDRUG() + "\t" + mention.getHMBD() + "\t" + mention.getKEGG() + "\t" + 
							mention.getKEGD() + "\t" + mention.getMESH() + "\t" +
							ltkb.getLtkbKey() + "\t" + ltkb.getDiliSeverityClass() + "\t" +
							ltkb.getDiliConcernLabel() + "\t" + ltkb.getvDILIConcernLabel() + "\t" +
							ltkb.getLabelSection() + "\t" + ltkb.getApprovalYear() + "\t" + 
							ltkb.getAdjudicatedDILI() + "\t" + ltkb.getGreeneAnnotation() + "\t" +
							ltkb.getSakatisAnnotation() + "\t" + ltkb.getXuAnnotation() + "\t" +
							ltkb.getZhuAnnotation() +"\n");
				} else {
					ltkb = ltkbDictionaryByName.get(mention.getText().toLowerCase());
					if(ltkb!=null) {
						output.write(id + "\t" + mention.getStart() + "\t" + mention.getEnd() + "\t" +  
								mention.getText() + "\t" + mention.getType().toString() + "\t"  +  mention.getSource() + "\t"  +  
								mention.getCHID() + "\t" + mention.getCHEB() + "\t" + mention.getCAS() + "\t" + 
								mention.getPUBC() + "\t" + mention.getPUBS() + "\t" + mention.getINCH() + "\t" + 
								mention.getDRUG() + "\t" + mention.getHMBD() + "\t" + mention.getKEGG() + "\t" + 
								mention.getKEGD() + "\t" + mention.getMESH() + "\t" +
								ltkb.getLtkbKey() + "\t" + ltkb.getDiliSeverityClass() + "\t" +
								ltkb.getDiliConcernLabel() + "\t" + ltkb.getvDILIConcernLabel() + "\t" +
								ltkb.getLabelSection() + "\t" + ltkb.getApprovalYear() + "\t" + 
								ltkb.getAdjudicatedDILI() + "\t" + ltkb.getGreeneAnnotation() + "\t" +
								ltkb.getSakatisAnnotation() + "\t" + ltkb.getXuAnnotation() + "\t" +
								ltkb.getZhuAnnotation() +"\n");
					}else {
						output.write(id + "\t" + mention.getStart() + "\t" + mention.getEnd() + "\t" +  
								mention.getText() + "\t" + mention.getType().toString() + "\t"  +  mention.getSource() + "\t"  +  
								mention.getCHID() + "\t" + mention.getCHEB() + "\t" + mention.getCAS() + "\t" + 
								mention.getPUBC() + "\t" + mention.getPUBS() + "\t" + mention.getINCH() + "\t" + 
								mention.getDRUG() + "\t" + mention.getHMBD() + "\t" + mention.getKEGG() + "\t" + 
								mention.getKEGD() + "\t" + mention.getMESH() + "\t null \t null \t null \t null \t null \t null \t null \t null \t null \t null \t null \n");
					}
					
				}
				
				
				output.flush();
			} catch (IOException e) {
				taggingLog.error("IOException Error tagging " + fileName, e);
			}catch (Exception e) {
				taggingLog.error("Generic Error tagging id "  + id + " in file " + fileName, e);
			}
		}
	}
	
	
	private List<String> readFilesProcessed(String outputDirectoryPath) {
		try {
			List<String> files_processed = new ArrayList<String>();
			if(Files.isRegularFile(Paths.get(outputDirectoryPath + File.separator + "list_files_processed.dat"))) {
				FileReader fr = new FileReader(outputDirectoryPath + File.separator + "list_files_processed.dat");
			    BufferedReader br = new BufferedReader(fr);
			    
			    String sCurrentLine;
			    while ((sCurrentLine = br.readLine()) != null) {
			    	files_processed.add(sCurrentLine);
				}
			    br.close();
			    fr.close();
			}
			return files_processed;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	  * Load Properties
	  * @param properitesParametersPath
	  */
	 public Properties loadPropertiesParameters(String properitesParametersPath) {
		 Properties prop = new Properties();
		 InputStream input = null;
		 try {
			 input = new FileInputStream(properitesParametersPath);
			 // load a properties file
			 prop.load(input);
			 return prop;
		 } catch (IOException ex) {
			 ex.printStackTrace();
		 } finally {
			 if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			 }
		}
		return null;
	 }	
}
