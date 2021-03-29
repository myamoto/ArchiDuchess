package org.toolup.archi.business.archimate.relation;

import org.toolup.archi.business.archimate.ArchimateModelException;

public class AccessRelationship extends AbstractElementRelation {

	public enum AccessType{READ, WRITE, READWRITE}
	
	public static final String XSITYPE = "archimate:AccessRelationship";
	
	private AccessType accessType;
	
	
	public AccessType getAccessType() {
		return accessType != null ? accessType : AccessType.WRITE;
	}

	public void setAccessType(AccessType accessType) {
		this.accessType = accessType;
	}

	@Override
	public String getXSIType() {
		return XSITYPE;
	}

	public void setAccessType(String accessTypeStr) throws ArchimateModelException {
		AccessType result;
		if("1".equals(accessTypeStr)){
			result = AccessType.READ;
		}else if("2".equals(accessTypeStr)){
			result = AccessType.WRITE;
		}else if("3".equals(accessTypeStr)){
			result = AccessType.READWRITE;
		}else{
			throw new ArchimateModelException(String.format("Unsupported relation accessType  : %s", accessTypeStr));
		}
		setAccessType(result);
	}

	public String getAccessTypeStr() {
		return accessType == AccessType.READ ? "1" 
				: accessType == AccessType.WRITE ? "2"
				: accessType == AccessType.READWRITE ? "3" : "2";
	}
	
	@Override
	public String getMxGraphLineStyle() {
		String result = "rounded=1;dashed=1;dashPattern=1 2;html=1;";
		
		result += (getAccessType() == AccessType.READ || getAccessType() == AccessType.READWRITE) ? "startArrow=classic;":"startArrow=none;";
		result += (getAccessType() == AccessType.WRITE || getAccessType() == AccessType.READWRITE) ? "endArrow=classic;":"endArrow=none;";
		return result;
	}

	@Override
	public String toString() {
		return "AccessRelationship [accessType=" + accessType + ", getId()=" + getId() + "]";
	}
	

}
