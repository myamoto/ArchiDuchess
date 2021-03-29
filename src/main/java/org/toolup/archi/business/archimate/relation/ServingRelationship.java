package org.toolup.archi.business.archimate.relation;

public class ServingRelationship extends AbstractElementRelation {
	
	public static final String XSITYPE = "archimate:ServingRelationship";

	@Override
	public String getXSIType() {
		return XSITYPE;
	}
	
	@Override
	public String getMxGraphLineStyle() {
		return "html=1;rounded=1;endArrow=open;";
	}

	@Override
	public String toString() {
		return "ServingRelationship [getId()=" + getId() + "]";
	}
	
	

}
