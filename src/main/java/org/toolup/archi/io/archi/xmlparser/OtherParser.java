package org.toolup.archi.io.archi.xmlparser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.toolup.archi.business.archimate.ArchimateModel;
import org.toolup.archi.business.archimate.ArchimateModelException;
import org.toolup.archi.business.archimate.other.AbstractElementOther;
import org.toolup.archi.business.archimate.other.FolderOther;
import org.toolup.archi.business.archimate.other.GroupingElement;
import org.toolup.archi.business.archimate.other.JunctionElement;
import org.toolup.archi.business.archimate.other.JunctionElement.JunctionType;
import org.toolup.archi.business.archimate.other.LocationElement;
import org.toolup.archi.business.archimate.other.RootFolderOther;

public class OtherParser implements IArchiRootFolderParser<RootFolderOther>{
	
	public RootFolderOther parse(Element rootElement, ArchimateModel model) throws ArchimateModelException {
		if(rootElement == null) 
			return null;
		
		RootFolderOther result = new RootFolderOther();
		ElementParser.setValues(result, (Element)rootElement);
		
		for(Node child = rootElement.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("element".equals(((Element)child).getNodeName())){
				result.addElement(parseElementOther(child));
			}else if("folder".equals(((Element)child).getNodeName())) {
				result.addFolder(parseFolderOther(child));
			}else{
				throw new ArchimateModelException(String.format("Unsupported technology element : <%s", ((Element)child).getNodeName()));
			}
		}
		
		return result;
	}

	private static FolderOther parseFolderOther(Node folderAppliElement) throws ArchimateModelException {

		if(folderAppliElement == null) 
			return null;
		
		FolderOther result = new FolderOther();
		ElementParser.setValues(result, (Element)folderAppliElement);
		
		for(Node child = folderAppliElement.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("element".equals(((Element)child).getNodeName())){
				result.addElement(parseElementOther(child));
			}else if("folder".equals(((Element)child).getNodeName())) {
				result.addFolder(parseFolderOther(child));
			}else{
				throw new ArchimateModelException(String.format("Unsupported technology element : <%s", ((Element)child).getNodeName()));
			}
		}
		return result;
	}
	
	private static AbstractElementOther parseElementOther(Node child) throws ArchimateModelException {
		if(child == null)
			return null;
		String childXsiType = ((Element)child).getAttribute("xsi:type");
		
		AbstractElementOther elemAppli;
		
		if(JunctionElement.XSITYPE.equals(childXsiType)) {
			JunctionType t = 
					"or".equals(((Element)child).getAttribute("type")) ? JunctionType.or : JunctionType.and;
			elemAppli = new JunctionElement()
					.type(t);
		}else if(LocationElement.XSITYPE.equals(childXsiType)) {
			elemAppli = new LocationElement();
		}else if(GroupingElement.XSITYPE.equals(childXsiType)) {
			elemAppli = new GroupingElement();
		}else {
			throw new ArchimateModelException(String.format("Unsupported Other xsi:type : %s", childXsiType));
		}
		ElementParser.setValues(elemAppli, (Element)child);
		return elemAppli;
	}

	@Override
	public String getType() {
		return RootFolderOther.TYPE;
	}
}
