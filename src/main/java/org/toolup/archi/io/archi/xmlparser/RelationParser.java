package org.toolup.archi.io.archi.xmlparser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.toolup.archi.business.archimate.ArchimateModel;
import org.toolup.archi.business.archimate.ArchimateModelException;
import org.toolup.archi.business.archimate.relation.AbstractElementRelation;
import org.toolup.archi.business.archimate.relation.AccessRelationship;
import org.toolup.archi.business.archimate.relation.AggregationRelationship;
import org.toolup.archi.business.archimate.relation.AssignmentRelationship;
import org.toolup.archi.business.archimate.relation.AssociationRelationship;
import org.toolup.archi.business.archimate.relation.CompositeRelationship;
import org.toolup.archi.business.archimate.relation.FlowRelationship;
import org.toolup.archi.business.archimate.relation.FolderRelation;
import org.toolup.archi.business.archimate.relation.InfluenceRelationship;
import org.toolup.archi.business.archimate.relation.RealisationRelationship;
import org.toolup.archi.business.archimate.relation.RealizationRelationship;
import org.toolup.archi.business.archimate.relation.RootFolderRelation;
import org.toolup.archi.business.archimate.relation.ServingRelationship;
import org.toolup.archi.business.archimate.relation.SpecialisationRelationship;
import org.toolup.archi.business.archimate.relation.SpecializationRelationship;
import org.toolup.archi.business.archimate.relation.TriggeringRelationship;
import org.toolup.archi.business.archimate.relation.UsedByRelationship;

public class RelationParser implements IArchiRootFolderParser<RootFolderRelation>{

	public RootFolderRelation parse(Element folderRelationRootElement, ArchimateModel model) throws ArchimateModelException {
		if(folderRelationRootElement == null) 
			return null;

		RootFolderRelation result = new RootFolderRelation();
		ElementParser.setValues(result, (Element)folderRelationRootElement);

		for(Node child = folderRelationRootElement.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("element".equals(((Element)child).getNodeName())){
				result.addElement(parseElementRelation(child, model));
			}else if("folder".equals(((Element)child).getNodeName())) {
				result.addFolder(parseFolderRelation(child, model));
			}else{
				throw new ArchimateModelException(String.format("Unsupported relation element : <%s", ((Element)child).getNodeName()));
			}
		}

		return result;
	}

	private static FolderRelation parseFolderRelation(Node folderRelationElement, ArchimateModel model) throws ArchimateModelException {

		if(folderRelationElement == null) 
			return null;

		FolderRelation result = new FolderRelation();
		ElementParser.setValues(result, (Element)folderRelationElement);

		for(Node child = folderRelationElement.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("element".equals(((Element)child).getNodeName())){
				result.addElement(parseElementRelation(child, model));
			}else if("folder".equals(((Element)child).getNodeName())) {
				result.addFolder(parseFolderRelation(child, model));
			}else{
				throw new ArchimateModelException(String.format("Unsupported relation element : <%s", ((Element)child).getNodeName()));
			}
		}
		return result;
	}

	private static AbstractElementRelation parseElementRelation(Node child, ArchimateModel model) throws ArchimateModelException {
		if(child == null)
			return null;
		String childXsiType = ((Element)child).getAttribute("xsi:type");

		AbstractElementRelation elemRelation;

		if(AccessRelationship.XSITYPE.equals(childXsiType)) {
			elemRelation = new AccessRelationship();
			String accessTypeStr = ((Element)child).getAttribute("accessType");
			if(accessTypeStr != null && !accessTypeStr.isEmpty()) {
				((AccessRelationship)elemRelation).setAccessType(accessTypeStr);
			}

		}else if(AssociationRelationship.XSITYPE.equals(childXsiType)) {
			elemRelation = new AssociationRelationship();
		}else if(CompositeRelationship.XSITYPE.equals(childXsiType)) {
			elemRelation = new CompositeRelationship();
		}else if(FlowRelationship.XSITYPE.equals(childXsiType)) {
			elemRelation = new FlowRelationship();
		}else if(RealizationRelationship.XSITYPE.equals(childXsiType)) {
			elemRelation = new RealizationRelationship();
		}else if(RealisationRelationship.XSITYPE.equals(childXsiType)) {
			elemRelation = new RealisationRelationship();
		}else if(SpecializationRelationship.XSITYPE.equals(childXsiType)) {
			elemRelation = new SpecializationRelationship();
		}else if(UsedByRelationship.XSITYPE.equals(childXsiType)) {
			elemRelation = new UsedByRelationship();
		}else if(ServingRelationship.XSITYPE.equals(childXsiType)) {
			elemRelation = new ServingRelationship();
		}else if(AggregationRelationship.XSITYPE.equals(childXsiType)) {
			elemRelation = new AggregationRelationship();
		}else if(TriggeringRelationship.XSITYPE.equals(childXsiType)) {
			elemRelation = new TriggeringRelationship();
		}else if(AssignmentRelationship.XSITYPE.equals(childXsiType)) {
			elemRelation = new AssignmentRelationship();
		}else if(SpecialisationRelationship.XSITYPE.equals(childXsiType)) {
			elemRelation = new SpecialisationRelationship();
		}else if(InfluenceRelationship.XSITYPE.equals(childXsiType)) {
			elemRelation = new InfluenceRelationship();	
			
		}else{
			throw new ArchimateModelException(String.format("Unsupported relation xsi:type : %s", childXsiType));
		}
		ElementParser.setValues(elemRelation, (Element)child);
		
		elemRelation.setSource(model.findArchimateElement(((Element)child).getAttribute("source")));
		if(elemRelation.getSource() == null)
			throw new ArchimateModelException(String.format("source element not found : %s", ((Element)child).getAttribute("source")));
		
		elemRelation.setTarget(model.findArchimateElement(((Element)child).getAttribute("target")));
		if(elemRelation.getTarget() == null)
			throw new ArchimateModelException(String.format("target element not found : %s", ((Element)child).getAttribute("target")));

		return elemRelation;
	}

	@Override
	public String getType() {
		return RootFolderRelation.TYPE;
	}

}
