package org.toolup.archi.io.archi.xmlparser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.toolup.archi.business.archimate.ArchimateModel;
import org.toolup.archi.business.archimate.ArchimateModelException;
import org.toolup.archi.business.archimate.business.AbstractElementBusiness;
import org.toolup.archi.business.archimate.business.BusinessActorElement;
import org.toolup.archi.business.archimate.business.BusinessCollaboration;
import org.toolup.archi.business.archimate.business.BusinessContract;
import org.toolup.archi.business.archimate.business.BusinessEvent;
import org.toolup.archi.business.archimate.business.BusinessFunction;
import org.toolup.archi.business.archimate.business.BusinessInteraction;
import org.toolup.archi.business.archimate.business.BusinessInterface;
import org.toolup.archi.business.archimate.business.BusinessObject;
import org.toolup.archi.business.archimate.business.BusinessProcess;
import org.toolup.archi.business.archimate.business.BusinessProduct;
import org.toolup.archi.business.archimate.business.BusinessRepresentation;
import org.toolup.archi.business.archimate.business.BusinessRole;
import org.toolup.archi.business.archimate.business.BusinessService;
import org.toolup.archi.business.archimate.business.FolderBusiness;
import org.toolup.archi.business.archimate.business.RootFolderBusiness;

public class BusinessParser implements IArchiRootFolderParser<RootFolderBusiness>{
	
	public RootFolderBusiness parse(Element folderAppliRootElement, ArchimateModel model) throws ArchimateModelException {
		if(folderAppliRootElement == null) 
			return null;
		
		RootFolderBusiness result = new RootFolderBusiness();
		ElementParser.setValues(result, (Element)folderAppliRootElement);
		
		for(Node child = folderAppliRootElement.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("element".equals(((Element)child).getNodeName())){
				result.addElement(parseElementBusiness(child));
			}else if("folder".equals(((Element)child).getNodeName())) {
				result.addFolder(parseFolderBusiness(child));
			}else{
				throw new ArchimateModelException(String.format("Unsupported technology element : <%s", ((Element)child).getNodeName()));
			}
		}
		
		return result;
	}

	private static FolderBusiness parseFolderBusiness(Node folderAppliElement) throws ArchimateModelException {

		if(folderAppliElement == null) 
			return null;
		
		FolderBusiness result = new FolderBusiness();
		ElementParser.setValues(result, (Element)folderAppliElement);
		
		for(Node child = folderAppliElement.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("element".equals(((Element)child).getNodeName())){
				result.addElement(parseElementBusiness(child));
			}else if("folder".equals(((Element)child).getNodeName())) {
				result.addFolder(parseFolderBusiness(child));
			}else{
				throw new ArchimateModelException(String.format("Unsupported technology element : <%s", ((Element)child).getNodeName()));
			}
		}
		return result;
	}
	
	private static AbstractElementBusiness parseElementBusiness(Node child) throws ArchimateModelException {
		if(child == null)
			return null;
		String childXsiType = ((Element)child).getAttribute("xsi:type");
		
		AbstractElementBusiness elemAppli;
		if(BusinessActorElement.XSITYPE.equals(childXsiType)) {
			elemAppli = new BusinessActorElement();
		}else if(BusinessFunction.XSITYPE.equals(childXsiType)) {
			elemAppli = new BusinessFunction();
		}else if(BusinessService.XSITYPE.equals(childXsiType)) {
			elemAppli = new BusinessService();
		}else if(BusinessObject.XSITYPE.equals(childXsiType)) {
			elemAppli = new BusinessObject();
		}else if(BusinessEvent.XSITYPE.equals(childXsiType)) {
			elemAppli = new BusinessEvent();
		}else if(BusinessRole.XSITYPE.equals(childXsiType)) {
			elemAppli = new BusinessRole();
		}else if(BusinessCollaboration.XSITYPE.equals(childXsiType)) {
			elemAppli = new BusinessCollaboration();
		}else if(BusinessInterface.XSITYPE.equals(childXsiType)) {
			elemAppli = new BusinessInterface();
		}else if(BusinessProcess.XSITYPE.equals(childXsiType)) {
			elemAppli = new BusinessProcess();
		}else if(BusinessInteraction.XSITYPE.equals(childXsiType)) {
			elemAppli = new BusinessInteraction();
		}else if(BusinessContract.XSITYPE.equals(childXsiType)) {
			elemAppli = new BusinessContract();
		}else if(BusinessProduct.XSITYPE.equals(childXsiType)) {
			elemAppli = new BusinessProduct();
		}else if(BusinessRepresentation.XSITYPE.equals(childXsiType)) {
			elemAppli = new BusinessRepresentation();
		}
		
		else{
			throw new ArchimateModelException(String.format("Unsupported business xsi:type : %s", childXsiType));
		}
		ElementParser.setValues(elemAppli, (Element)child);
		return elemAppli;
	}

	@Override
	public String getType() {
		return RootFolderBusiness.TYPE;
	}

}
