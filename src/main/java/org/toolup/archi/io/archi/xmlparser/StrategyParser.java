package org.toolup.archi.io.archi.xmlparser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.toolup.archi.business.archimate.ArchimateModel;
import org.toolup.archi.business.archimate.ArchimateModelException;
import org.toolup.archi.business.archimate.strategy.AbstractElementStrategy;
import org.toolup.archi.business.archimate.strategy.FolderStrategy;
import org.toolup.archi.business.archimate.strategy.RootFolderStrategy;
import org.toolup.archi.business.archimate.strategy.StrategyCapability;
import org.toolup.archi.business.archimate.strategy.StrategyCourseOfAction;
import org.toolup.archi.business.archimate.strategy.StrategyResource;
import org.toolup.archi.business.archimate.strategy.StrategyValueStream;



public class StrategyParser implements IArchiRootFolderParser<RootFolderStrategy>{
	
	public RootFolderStrategy parse(Element rootElement, ArchimateModel model) throws ArchimateModelException {
		if(rootElement == null) 
			return null;
		
		RootFolderStrategy result = new RootFolderStrategy();
		ElementParser.setValues(result, (Element)rootElement);
		
		for(Node child = rootElement.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("element".equals(((Element)child).getNodeName())){
				result.addElement(parseElementOther(child));
			}else if("folder".equals(((Element)child).getNodeName())) {
				result.addFolder(parseFolderStrategy(child));
			}else{
				throw new ArchimateModelException(String.format("Unsupported technology element : <%s", ((Element)child).getNodeName()));
			}
		}
		
		return result;
	}

	private static FolderStrategy parseFolderStrategy(Node folderAppliElement) throws ArchimateModelException {

		if(folderAppliElement == null) 
			return null;
		
		FolderStrategy result = new FolderStrategy();
		ElementParser.setValues(result, (Element)folderAppliElement);
		
		for(Node child = folderAppliElement.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("element".equals(((Element)child).getNodeName())){
				result.addElement(parseElementOther(child));
			}else if("folder".equals(((Element)child).getNodeName())) {
				result.addFolder(parseFolderStrategy(child));
			}else{
				throw new ArchimateModelException(String.format("Unsupported technology element : <%s", ((Element)child).getNodeName()));
			}
		}
		return result;
	}
	
	private static AbstractElementStrategy parseElementOther(Node child) throws ArchimateModelException {
		if(child == null)
			return null;
		String childXsiType = ((Element)child).getAttribute("xsi:type");
		
		AbstractElementStrategy elemAppli;
		
		if(StrategyResource.XSITYPE.equals(childXsiType)) {
			elemAppli = new StrategyResource();
		}else if(StrategyCapability.XSITYPE.equals(childXsiType)) {
			elemAppli = new StrategyCapability();
		}else if(StrategyValueStream.XSITYPE.equals(childXsiType)) {
			elemAppli = new StrategyValueStream();
		}else if(StrategyCourseOfAction.XSITYPE.equals(childXsiType)) {
			elemAppli = new StrategyCourseOfAction();
		}else {
			throw new ArchimateModelException(String.format("Unsupported Strategy xsi:type : %s", childXsiType));
		}
		ElementParser.setValues(elemAppli, (Element)child);
		return elemAppli;
	}

	@Override
	public String getType() {
		return RootFolderStrategy.TYPE;
	}


}
