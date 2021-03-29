package org.toolup.archi.business.archimate.relation;

public class RealisationRelationship extends AbstractElementRelation {

	public static final String XSITYPE = "archimate:RealisationRelationship";

	@Override
	public String getXSIType() {
		return XSITYPE;
	}
	
	@Override
	public String getMxGraphLineStyle() {
		return "html=1;rounded=1;endArrow=block;endSize=11;endFill=0;dashed=1;dashPattern=2 2;";
	}

	@Override
	public String toString() {
		return "RealisationRelationship [getId()=" + getId() + "]";
	}
	
	

}
