package org.toolup.archi.io.archi.xmlparser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.toolup.archi.business.archimate.ArchimateModel;
import org.toolup.archi.business.archimate.ArchimateModelException;
import org.toolup.archi.business.archimate.application.AbstractElementApplication;
import org.toolup.archi.business.archimate.application.ApplicationCollaboration;
import org.toolup.archi.business.archimate.application.ApplicationComponentElement;
import org.toolup.archi.business.archimate.application.ApplicationDataObjectElement;
import org.toolup.archi.business.archimate.application.ApplicationEvent;
import org.toolup.archi.business.archimate.application.ApplicationFunctionElement;
import org.toolup.archi.business.archimate.application.ApplicationInteraction;
import org.toolup.archi.business.archimate.application.ApplicationInterface;
import org.toolup.archi.business.archimate.application.ApplicationProcess;
import org.toolup.archi.business.archimate.application.ApplicationServiceElement;
import org.toolup.archi.business.archimate.application.FolderApplication;
import org.toolup.archi.business.archimate.application.RootFolderApplication;

public class ApplicationParser implements IArchiRootFolderParser<RootFolderApplication>{
	
	public RootFolderApplication parse(Element folderAppliRootElement, ArchimateModel model) throws ArchimateModelException {
		if(folderAppliRootElement == null) 
			return null;
		
		RootFolderApplication result = new RootFolderApplication();
		ElementParser.setValues(result, folderAppliRootElement);
		for(Node child = folderAppliRootElement.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("element".equals(((Element)child).getNodeName())){
				result.addElement(parseElementAppli((Element)child));
			}else if("folder".equals(((Element)child).getNodeName())) {
				result.addFolder(parseFolderAppli((Element)child));
			}else{
				throw new ArchimateModelException(String.format("Unsupported application element : <%s", ((Element)child).getNodeName()));
			}
		}
		
		return result;
	}

	private static FolderApplication parseFolderAppli(Element folderAppliElement) throws ArchimateModelException {

		if(folderAppliElement == null) 
			return null;
		
		FolderApplication result = new FolderApplication();
		ElementParser.setValues(result, folderAppliElement);
		
		for(Node child = folderAppliElement.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("element".equals(((Element)child).getNodeName())){
				result.addElement(parseElementAppli((Element)child));
			}else if("folder".equals(((Element)child).getNodeName())) {
				result.addFolder(parseFolderAppli((Element)child));
			}else{
				throw new ArchimateModelException(String.format("Unsupported application element : <%s", ((Element)child).getNodeName()));
			}
		}
		return result;
	}
	
	private static AbstractElementApplication parseElementAppli(Element child) throws ArchimateModelException {
		if(child == null)
			return null;
		String childXsiType = (child).getAttribute("xsi:type");
		
		AbstractElementApplication elemAppli;
		if(ApplicationComponentElement.XSITYPE.equals(childXsiType)) {
			elemAppli = new ApplicationComponentElement();
		}else if(ApplicationDataObjectElement.XSITYPE.equals(childXsiType)) {
			elemAppli = new ApplicationDataObjectElement();
		}else if(ApplicationServiceElement.XSITYPE.equals(childXsiType)) {
			elemAppli = new ApplicationServiceElement();
		}else if(ApplicationFunctionElement.XSITYPE.equals(childXsiType)) {
			elemAppli = new ApplicationFunctionElement();
		}else if(ApplicationInterface.XSITYPE.equals(childXsiType)) {
			elemAppli = new ApplicationInterface();
		}else if(ApplicationEvent.XSITYPE.equals(childXsiType)) {
			elemAppli = new ApplicationEvent();
		}else if(ApplicationCollaboration.XSITYPE.equals(childXsiType)) {
			elemAppli = new ApplicationCollaboration();
		}else if(ApplicationInteraction.XSITYPE.equals(childXsiType)) {
			elemAppli = new ApplicationInteraction();
		}else if(ApplicationProcess.XSITYPE.equals(childXsiType)) {
			elemAppli = new ApplicationProcess();
		}else {
			throw new ArchimateModelException(String.format("Unsupported application xsi:type : %s", childXsiType));
		}
		
		ElementParser.setValues(elemAppli, child);
		
		return elemAppli;
	}

	@Override
	public String getType() {
		return RootFolderApplication.TYPE;
	}
}
