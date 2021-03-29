package org.toolup.archi.io.archi.xmlparser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.toolup.archi.business.archimate.ArchimateModel;
import org.toolup.archi.business.archimate.ArchimateModelException;
import org.toolup.archi.business.archimate.movitation.AbstractElementMotivation;
import org.toolup.archi.business.archimate.movitation.FolderMotivation;
import org.toolup.archi.business.archimate.movitation.MotivationAssessment;
import org.toolup.archi.business.archimate.movitation.MotivationConstraint;
import org.toolup.archi.business.archimate.movitation.MotivationDriver;
import org.toolup.archi.business.archimate.movitation.MotivationGoal;
import org.toolup.archi.business.archimate.movitation.MotivationMeaning;
import org.toolup.archi.business.archimate.movitation.MotivationOutcome;
import org.toolup.archi.business.archimate.movitation.MotivationPrinciple;
import org.toolup.archi.business.archimate.movitation.MotivationRequirement;
import org.toolup.archi.business.archimate.movitation.MotivationStakeholder;
import org.toolup.archi.business.archimate.movitation.MotivationValue;
import org.toolup.archi.business.archimate.movitation.RootFolderMotivation;

public class MotivationParser implements IArchiRootFolderParser<RootFolderMotivation>{
	
	public RootFolderMotivation parse(Element rootElement, ArchimateModel model) throws ArchimateModelException {
		if(rootElement == null) 
			return null;
		
		RootFolderMotivation result = new RootFolderMotivation();
		ElementParser.setValues(result, (Element)rootElement);
		
		for(Node child = rootElement.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("element".equals(((Element)child).getNodeName())){
				result.addElement(parseElementMotivation(child));
			}else if("folder".equals(((Element)child).getNodeName())) {
				result.addFolder(parseFolderMotivation(child));
			}else{
				throw new ArchimateModelException(String.format("Unsupported technology element : <%s", ((Element)child).getNodeName()));
			}
		}
		
		return result;
	}

	private static FolderMotivation parseFolderMotivation(Node folderAppliElement) throws ArchimateModelException {

		if(folderAppliElement == null) 
			return null;
		
		FolderMotivation result = new FolderMotivation();
		ElementParser.setValues(result, (Element)folderAppliElement);
		
		for(Node child = folderAppliElement.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("element".equals(((Element)child).getNodeName())){
				result.addElement(parseElementMotivation(child));
			}else if("folder".equals(((Element)child).getNodeName())) {
				result.addFolder(parseFolderMotivation(child));
			}else{
				throw new ArchimateModelException(String.format("Unsupported technology element : <%s", ((Element)child).getNodeName()));
			}
		}
		return result;
	}
	
	private static AbstractElementMotivation parseElementMotivation(Node child) throws ArchimateModelException {
		if(child == null)
			return null;
		String childXsiType = ((Element)child).getAttribute("xsi:type");
		
		AbstractElementMotivation elemAppli;
		
		if(MotivationStakeholder.XSITYPE.equals(childXsiType)) {
			elemAppli = new MotivationStakeholder();
		}else if(MotivationDriver.XSITYPE.equals(childXsiType)) {
			elemAppli = new MotivationDriver();
		}else if(MotivationAssessment.XSITYPE.equals(childXsiType)) {
			elemAppli = new MotivationAssessment();
		}else if(MotivationGoal.XSITYPE.equals(childXsiType)) {
			elemAppli = new MotivationGoal();
		}else if(MotivationOutcome.XSITYPE.equals(childXsiType)) {
			elemAppli = new MotivationOutcome();
		}else if(MotivationPrinciple.XSITYPE.equals(childXsiType)) {
			elemAppli = new MotivationPrinciple();
		}else if(MotivationRequirement.XSITYPE.equals(childXsiType)) {
			elemAppli = new MotivationRequirement();
		}else if(MotivationConstraint.XSITYPE.equals(childXsiType)) {
			elemAppli = new MotivationConstraint();
		}else if(MotivationMeaning.XSITYPE.equals(childXsiType)) {
			elemAppli = new MotivationMeaning();
		}else if(MotivationValue.XSITYPE.equals(childXsiType)) {
			elemAppli = new MotivationValue();
		
//		}else if(MotivationValueStream.XSITYPE.equals(childXsiType)) {
//			elemAppli = new MotivationValueStream();
//		}else if(MotivationCourseOfAction.XSITYPE.equals(childXsiType)) {
//			elemAppli = new MotivationCourseOfAction();
		}else {
			throw new ArchimateModelException(String.format("Unsupported Motivation xsi:type : %s", childXsiType));
		}
		ElementParser.setValues(elemAppli, (Element)child);
		return elemAppli;
	}

	@Override
	public String getType() {
		return RootFolderMotivation.TYPE;
	}

}


