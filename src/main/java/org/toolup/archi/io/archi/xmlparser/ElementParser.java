package org.toolup.archi.io.archi.xmlparser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.toolup.archi.business.archimate.AbstractArchimateElement;
import org.toolup.archi.business.archimate.AbstractArchimateObject;

public class ElementParser {
	
	private static final String DOCUMENTATION_NODENAME = "documentation";
	private static final String PROPERTY_NODENAME = "property";
	private static final List<String> genericChildren = Arrays.asList(new String[] {DOCUMENTATION_NODENAME, PROPERTY_NODENAME});
	
	private ElementParser() {
		//static class
	}
	
	public static boolean isPossibleNodeName(String nodeName) {
		return genericChildren.contains(nodeName);
	}
		
	public static void setValues(AbstractArchimateElement archimateObject, Element xmlElem)  {
		if(archimateObject == null)
			return;
		if(xmlElem == null)
			return;
		setValues((AbstractArchimateObject)archimateObject, xmlElem);
		
		for(Node child = xmlElem.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if(DOCUMENTATION_NODENAME.equals(((Element)child).getNodeName())){
				archimateObject.setDocumentation(((Element)child).getFirstChild().getTextContent());
			}else if(PROPERTY_NODENAME.equals(((Element)child).getNodeName())) {
				archimateObject.putCustomValue(((Element)child).getAttribute("key"), ((Element)child).getAttribute("value"));
			}
		}
	}
	
	private static final Map<String, Element> ids = new HashMap<String, Element>();
	
	public static void setValues(AbstractArchimateObject archimateObject, Element xmlElem) {
		if(archimateObject == null)
			return;
		if(xmlElem == null)
			return;
		if(!ids.containsKey(xmlElem.getAttribute("id"))) {
			ids.put(xmlElem.getAttribute("id"), xmlElem);
		}
		
		archimateObject.setId(xmlElem.getAttribute("id"));
		archimateObject.setName(xmlElem.getAttribute("name"));
	}
	
	
}
