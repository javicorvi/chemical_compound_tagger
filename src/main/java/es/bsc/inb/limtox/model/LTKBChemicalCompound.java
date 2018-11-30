package es.bsc.inb.limtox.model;

/**
 * Model of a LTKB entry from the LTKB database
 * @author jcorvi
 *
 */
public class LTKBChemicalCompound {
	
	private String ltkbKey;
	
	private String pubChemId;	
		
	private String chemicalCompoundName;
	
	private String approvalYear;
	
	private String diliConcernLabel;
	
	private String diliSeverityClass;
	
	private String diliSeverityDescription;
	
	private String labelSection;
	
	private String adjudicatedDILI;
	
	private String vDILIConcernLabel;

	private String greeneAnnotation;
	
	private String sakatisAnnotation;
	
	private String xuAnnotation;
	
	private String zhuAnnotation;
	
	private String smiles;
	
	private String url;
	
	/**
	 * 1=LTKB-BD, 2=others
	 */
	private String verDisp;
	
	public LTKBChemicalCompound() {
		super();
	}

	public String getLtkbKey() {
		return ltkbKey;
	}

	public void setLtkbKey(String ltkbKey) {
		this.ltkbKey = ltkbKey;
	}

	public String getPubChemId() {
		return pubChemId;
	}

	public void setPubChemId(String pubChemId) {
		this.pubChemId = pubChemId;
	}

	public String getChemicalCompoundName() {
		return chemicalCompoundName;
	}

	public void setChemicalCompoundName(String chemicalCompoundName) {
		this.chemicalCompoundName = chemicalCompoundName;
	}

	public String getApprovalYear() {
		return approvalYear;
	}

	public void setApprovalYear(String approvalYear) {
		this.approvalYear = approvalYear;
	}

	public String getDiliConcernLabel() {
		return diliConcernLabel;
	}

	public void setDiliConcernLabel(String diliConcernLabel) {
		this.diliConcernLabel = diliConcernLabel;
	}

	public String getDiliSeverityClass() {
		return diliSeverityClass;
	}

	public void setDiliSeverityClass(String diliSeverityClass) {
		this.diliSeverityClass = diliSeverityClass;
	}

	public String getDiliSeverityDescription() {
		return diliSeverityDescription;
	}

	public void setDiliSeverityDescription(String diliSeverityDescription) {
		this.diliSeverityDescription = diliSeverityDescription;
	}

	public String getLabelSection() {
		return labelSection;
	}

	public void setLabelSection(String labelSection) {
		this.labelSection = labelSection;
	}

	public String getAdjudicatedDILI() {
		return adjudicatedDILI;
	}

	public void setAdjudicatedDILI(String adjudicatedDILI) {
		this.adjudicatedDILI = adjudicatedDILI;
	}

	public String getvDILIConcernLabel() {
		return vDILIConcernLabel;
	}

	public void setvDILIConcernLabel(String vDILIConcernLabel) {
		this.vDILIConcernLabel = vDILIConcernLabel;
	}

	public String getGreeneAnnotation() {
		return greeneAnnotation;
	}

	public void setGreeneAnnotation(String greeneAnnotation) {
		this.greeneAnnotation = greeneAnnotation;
	}

	public String getSakatisAnnotation() {
		return sakatisAnnotation;
	}

	public void setSakatisAnnotation(String sakatisAnnotation) {
		this.sakatisAnnotation = sakatisAnnotation;
	}

	public String getXuAnnotation() {
		return xuAnnotation;
	}

	public void setXuAnnotation(String xuAnnotation) {
		this.xuAnnotation = xuAnnotation;
	}

	public String getZhuAnnotation() {
		return zhuAnnotation;
	}

	public void setZhuAnnotation(String zhuAnnotation) {
		this.zhuAnnotation = zhuAnnotation;
	}

	public String getSmiles() {
		return smiles;
	}

	public void setSmiles(String smiles) {
		this.smiles = smiles;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVerDisp() {
		return verDisp;
	}

	public void setVerDisp(String verDisp) {
		this.verDisp = verDisp;
	}

	
	
}
