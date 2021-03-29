package org.toolup.archi.io.archi.xmlparser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.toolup.archi.business.archimate.ArchimateModel;
import org.toolup.archi.business.archimate.ArchimateModelException;
import org.toolup.archi.business.archimate.technology.AbstractElementTechnology;
import org.toolup.archi.business.archimate.technology.FolderTechnology;
import org.toolup.archi.business.archimate.technology.RootFolderTechnology;
import org.toolup.archi.business.archimate.technology.TechnologyArtifact;
import org.toolup.archi.business.archimate.technology.TechnologyCollaboration;
import org.toolup.archi.business.archimate.technology.TechnologyCommunicationNetworkElement;
import org.toolup.archi.business.archimate.technology.TechnologyDeviceElement;
import org.toolup.archi.business.archimate.technology.TechnologyDistributionNetwork;
import org.toolup.archi.business.archimate.technology.TechnologyEquipment;
import org.toolup.archi.business.archimate.technology.TechnologyEvent;
import org.toolup.archi.business.archimate.technology.TechnologyFacility;
import org.toolup.archi.business.archimate.technology.TechnologyFunction;
import org.toolup.archi.business.archimate.technology.TechnologyInteraction;
import org.toolup.archi.business.archimate.technology.TechnologyInterface;
import org.toolup.archi.business.archimate.technology.TechnologyMaterial;
import org.toolup.archi.business.archimate.technology.TechnologyNetworkElement;
import org.toolup.archi.business.archimate.technology.TechnologyNodeElement;
import org.toolup.archi.business.archimate.technology.TechnologyPath;
import org.toolup.archi.business.archimate.technology.TechnologyProcess;
import org.toolup.archi.business.archimate.technology.TechnologyService;
import org.toolup.archi.business.archimate.technology.TechnologySystemSoftwareElement;

public class TechnologyParser implements IArchiRootFolderParser<RootFolderTechnology>{
	
	public RootFolderTechnology parse(Element folderAppliRootElement, ArchimateModel model) throws ArchimateModelException {
		if(folderAppliRootElement == null) 
			return null;
		
		RootFolderTechnology result = new RootFolderTechnology();
		ElementParser.setValues(result, (Element)folderAppliRootElement);
		
		for(Node child = folderAppliRootElement.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("element".equals(((Element)child).getNodeName())){
				result.addElement(parseElementAppli(child));
			}else if("folder".equals(((Element)child).getNodeName())) {
				result.addFolder(parseFolderAppli(child));
			}else{
				throw new ArchimateModelException(String.format("Unsupported technology element : <%s", ((Element)child).getNodeName()));
			}
		}
		
		return result;
	}

	private static FolderTechnology parseFolderAppli(Node folderAppliElement) throws ArchimateModelException {

		if(folderAppliElement == null) 
			return null;
		
		FolderTechnology result = new FolderTechnology();
		ElementParser.setValues(result, (Element)folderAppliElement);
		
		for(Node child = folderAppliElement.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("element".equals(((Element)child).getNodeName())){
				result.addElement(parseElementAppli(child));
			}else if("folder".equals(((Element)child).getNodeName())) {
				result.addFolder(parseFolderAppli(child));
			}else{
				throw new ArchimateModelException(String.format("Unsupported technology element : <%s", ((Element)child).getNodeName()));
			}
		}
		return result;
	}
	
	private static AbstractElementTechnology parseElementAppli(Node child) throws ArchimateModelException {
		if(child == null)
			return null;
		String childXsiType = ((Element)child).getAttribute("xsi:type");
		
		AbstractElementTechnology elemAppli;
		if(TechnologyDeviceElement.XSITYPE.equals(childXsiType)) {
			elemAppli = new TechnologyDeviceElement();
		}else if(TechnologyNetworkElement.XSITYPE.equals(childXsiType)) {
			elemAppli = new TechnologyNetworkElement();
		}else if(TechnologyCommunicationNetworkElement.XSITYPE.equals(childXsiType)) {
			elemAppli = new TechnologyCommunicationNetworkElement();
		}else if(TechnologyNodeElement.XSITYPE.equals(childXsiType)) {
			elemAppli = new TechnologyNodeElement();
		}else if(TechnologySystemSoftwareElement.XSITYPE.equals(childXsiType)) {
			elemAppli = new TechnologySystemSoftwareElement();
		}else if(TechnologyCollaboration.XSITYPE.equals(childXsiType)) {
			elemAppli = new TechnologyCollaboration();
		}else if(TechnologyInterface.XSITYPE.equals(childXsiType)) {
			elemAppli = new TechnologyInterface();
		}else if(TechnologyPath.XSITYPE.equals(childXsiType)) {
			elemAppli = new TechnologyPath();
		}else if(TechnologyFunction.XSITYPE.equals(childXsiType)) {
			elemAppli = new TechnologyFunction();
		}else if(TechnologyProcess.XSITYPE.equals(childXsiType)) {
			elemAppli = new TechnologyProcess();
		}else if(TechnologyInteraction.XSITYPE.equals(childXsiType)) {
			elemAppli = new TechnologyInteraction();
		}else if(TechnologyEvent.XSITYPE.equals(childXsiType)) {
			elemAppli = new TechnologyEvent();
		}else if(TechnologyService.XSITYPE.equals(childXsiType)) {
			elemAppli = new TechnologyService();
		}else if(TechnologyArtifact.XSITYPE.equals(childXsiType)) {
			elemAppli = new TechnologyArtifact();
		}else if(TechnologyEquipment.XSITYPE.equals(childXsiType)) {
			elemAppli = new TechnologyEquipment();	
		}else if(TechnologyFacility.XSITYPE.equals(childXsiType)) {
			elemAppli = new TechnologyFacility();	
		}else if(TechnologyDistributionNetwork.XSITYPE.equals(childXsiType)) {
			elemAppli = new TechnologyDistributionNetwork();	
		}else if(TechnologyMaterial.XSITYPE.equals(childXsiType)) {
			elemAppli = new TechnologyMaterial();	
			
		}else {
			throw new ArchimateModelException(String.format("Unsupported technology xsi:type : %s", childXsiType));
		}
		ElementParser.setValues(elemAppli, (Element)child);
		return elemAppli;
	}

	@Override
	public String getType() {
		return RootFolderTechnology.TYPE;
	}

}
