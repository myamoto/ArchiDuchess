package org.toolup.archi.business.archimate.relation;

public class AssociationRelationship extends AbstractElementRelation {
	
	public static final String XSITYPE = "archimate:AssociationRelationship";

	@Override
	public String getXSIType() {
		return XSITYPE;
	}

	@Override
	public String getMxGraphLineStyle() {
		return "html=1;rounded=1;endArrow=none;";
	}

	@Override
	public String toString() {
		return "AssociationRelationship [getId()=" + getId() + "]";
	}
	
	
}
