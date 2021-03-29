package org.toolup.archi.io.archi.xmlparser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.toolup.archi.business.archimate.ArchimateModel;
import org.toolup.archi.business.archimate.ArchimateModelException;
import org.toolup.archi.business.archimate.implementation.AbstractElementImplementation;
import org.toolup.archi.business.archimate.implementation.FolderImplementation;
import org.toolup.archi.business.archimate.implementation.ImplementationDeliverable;
import org.toolup.archi.business.archimate.implementation.ImplementationEvent;
import org.toolup.archi.business.archimate.implementation.ImplementationGap;
import org.toolup.archi.business.archimate.implementation.ImplementationPlateau;
import org.toolup.archi.business.archimate.implementation.ImplementationWorkPackage;
import org.toolup.archi.business.archimate.implementation.RootFolderImplementation;

public class ImplementationParser implements IArchiRootFolderParser<RootFolderImplementation>{
	
	public RootFolderImplementation parse(Element rootElement, ArchimateModel model) throws ArchimateModelException {
		if(rootElement == null) 
			return null;
		
		RootFolderImplementation result = new RootFolderImplementation();
		ElementParser.setValues(result, (Element)rootElement);
		
		for(Node child = rootElement.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("element".equals(((Element)child).getNodeName())){
				result.addElement(parseElementImplementation(child));
			}else if("folder".equals(((Element)child).getNodeName())) {
				result.addFolder(parseFolderImplementation(child));
			}else{
				throw new ArchimateModelException(String.format("Unsupported technology element : <%s", ((Element)child).getNodeName()));
			}
		}
		
		return result;
	}

	private static FolderImplementation parseFolderImplementation(Node folderAppliElement) throws ArchimateModelException {

		if(folderAppliElement == null) 
			return null;
		
		FolderImplementation result = new FolderImplementation();
		ElementParser.setValues(result, (Element)folderAppliElement);
		
		for(Node child = folderAppliElement.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("element".equals(((Element)child).getNodeName())){
				result.addElement(parseElementImplementation(child));
			}else if("folder".equals(((Element)child).getNodeName())) {
				result.addFolder(parseFolderImplementation(child));
			}else{
				throw new ArchimateModelException(String.format("Unsupported technology element : <%s", ((Element)child).getNodeName()));
			}
		}
		return result;
	}
	
	private static AbstractElementImplementation parseElementImplementation(Node child) throws ArchimateModelException {
		if(child == null)
			return null;
		String childXsiType = ((Element)child).getAttribute("xsi:type");
		
		AbstractElementImplementation elemAppli;
		
		if(ImplementationWorkPackage.XSITYPE.equals(childXsiType)) {
			elemAppli = new ImplementationWorkPackage();
		}else if(ImplementationDeliverable.XSITYPE.equals(childXsiType)) {
			elemAppli = new ImplementationDeliverable();
		}else if(ImplementationEvent.XSITYPE.equals(childXsiType)) {
			elemAppli = new ImplementationEvent();
		}else if(ImplementationPlateau.XSITYPE.equals(childXsiType)) {
			elemAppli = new ImplementationPlateau();
		}else if(ImplementationGap.XSITYPE.equals(childXsiType)) {
			elemAppli = new ImplementationGap();
		}else {
			throw new ArchimateModelException(String.format("Unsupported Implementation xsi:type : %s", childXsiType));
		}
		ElementParser.setValues(elemAppli, (Element)child);
		return elemAppli;
	}

	@Override
	public String getType() {
		return RootFolderImplementation.TYPE;
	}

}
