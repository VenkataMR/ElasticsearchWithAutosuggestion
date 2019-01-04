package com.hcl.experian.es.model;

public class Content {
	private String requirementStatement;
	private String taxonomyLevel1;
	private String taxonomyLevel2;
	private String taxonomyLevel3;
	private String taxonomyLevel4;
	
	public Content() {
		super();
	}

	public Content(String requirementStatement, String taxonomyLevel1, String taxonomyLevel2, String taxonomyLevel3, String taxonomyLevel4) {
		super();
		this.requirementStatement = requirementStatement;
		this.taxonomyLevel1 = taxonomyLevel1;
		this.taxonomyLevel2 = taxonomyLevel2;
		this.taxonomyLevel3 = taxonomyLevel3;
		this.taxonomyLevel4 = taxonomyLevel4;
	}

	public String getRequirementStatement() {
		return requirementStatement;
	}

	public void setRequirementStatement(String requirementStatement) {
		this.requirementStatement = requirementStatement;
	}

	public String getTaxonomyLevel1() {
		return taxonomyLevel1;
	}

	public void setTaxonomyLevel1(String taxonomyLevel1) {
		this.taxonomyLevel1 = taxonomyLevel1;
	}
	
	public String getTaxonomyLevel2() {
		return taxonomyLevel2;
	}

	public void setTaxonomyLevel2(String taxonomyLevel2) {
		this.taxonomyLevel2 = taxonomyLevel2;
	}
	
	public String getTaxonomyLevel3() {
		return taxonomyLevel3;
	}

	public void setTaxonomyLevel3(String taxonomyLevel3) {
		this.taxonomyLevel3 = taxonomyLevel3;
	}
	
	public String getTaxonomyLevel4() {
		return taxonomyLevel4;
	}

	public void setTaxonomyLevel4(String taxonomyLevel4) {
		this.taxonomyLevel4 = taxonomyLevel4;
	}
	
}