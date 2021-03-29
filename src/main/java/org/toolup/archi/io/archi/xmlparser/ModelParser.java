package org.toolup.archi.io.archi.xmlparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.toolup.archi.business.archimate.AbstractRootFolder;
import org.toolup.archi.business.archimate.ArchimateModel;
import org.toolup.archi.business.archimate.ArchimateModelException;
import org.toolup.archi.business.archimate.application.RootFolderApplication;
import org.toolup.archi.business.archimate.business.RootFolderBusiness;
import org.toolup.archi.business.archimate.implementation.RootFolderImplementation;
import org.toolup.archi.business.archimate.movitation.AbstractElementMotivation;
import org.toolup.archi.business.archimate.movitation.RootFolderMotivation;
import org.toolup.archi.business.archimate.other.RootFolderOther;
import org.toolup.archi.business.archimate.relation.RootFolderRelation;
import org.toolup.archi.business.archimate.strategy.RootFolderStrategy;
import org.toolup.archi.business.archimate.technology.RootFolderTechnology;
import org.toolup.archi.business.archimate.view.RootFolderView;

public class ModelParser {
	
	private ModelParser() {/*static class*/}
	
	private static List<IArchiRootFolderParser<?>> parserTypes;
	static{
		parserTypes = new ArrayList<>();
		parserTypes.add(new ApplicationParser());
		parserTypes.add(new BusinessParser());
		parserTypes.add(new ImplementationParser());
		parserTypes.add(new MotivationParser());
		parserTypes.add(new OtherParser());
		parserTypes.add(new StrategyParser());
		parserTypes.add(new TechnologyParser());
		parserTypes.add(new RelationParser());
		parserTypes.add(new ViewParser());
		parserTypes.add(new IArchiRootFolderParser<ConnectorsFolder>() {
			@Override
			public String getType() {
				return ConnectorsFolder.TYPE;
			}

			@Override
			public ConnectorsFolder parse(Element rootFolderEle, ArchimateModel model)
					throws ArchimateModelException {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}
	
	private class ConnectorsFolder extends AbstractRootFolder<AbstractElementMotivation>{

		private final static String TYPE = "connectors";
		@Override
		public String getType() {
			return TYPE;
		}
		
	}

	public static ArchimateModel parse(Element racine) throws ArchimateModelException {
		ArchimateModel result = new ArchimateModel();
		ElementParser.setValues(result, racine);
		result.setVersion(racine.getAttribute("version"));

		//check for unsupported folder types...
		for(Node child = racine.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			String childType = ((Element)child).getAttribute("type");
			IArchiRootFolderParser<?> parser = findParser(childType);
			
			if(parser == null) {
				throw new ArchimateModelException(String.format("Unsupported folder type for archimate model : %s", childType));
			}
		}
		//make sure all root folders are present
//		for (Entry<String, IFolderParser> parserEntry: parserMap.entrySet()) {
//			if(parserEntry.getValue().isMandatorty() && findFolder(racine, parserEntry.getKey()) == null) {
//				throw new ArchimateModelException(String.format("Mandatory folder not found for archimate model : type=\"%s\"", parserEntry.getKey()));
//			}
//		}

		//handle Archimate elements with no dependencies between themselves
		result.setFolderApplication(parse(result, racine, RootFolderApplication.class, RootFolderApplication.TYPE));
		result.setFolderBusiness(parse(result, racine, RootFolderBusiness.class, RootFolderBusiness.TYPE));
		result.setFolderImplementation(parse(result, racine, RootFolderImplementation.class, RootFolderImplementation.TYPE));
		result.setFolderMotivation(parse(result, racine, RootFolderMotivation.class, RootFolderMotivation.TYPE));
		result.setFolderOther(parse(result, racine, RootFolderOther.class, RootFolderOther.TYPE));
		result.setFolderStrategy(parse(result, racine, RootFolderStrategy.class, RootFolderStrategy.TYPE));
		result.setFolderTechnology(parse(result, racine, RootFolderTechnology.class, RootFolderTechnology.TYPE));
		
		//handle Relations elements : links between archimate elements (requires that referenced archimate elements be parsed)
		result.setFolderRelation(parse(result, racine, RootFolderRelation.class, RootFolderRelation.TYPE));
		result.setFolderViews(parse(result, racine, RootFolderView.class, RootFolderView.TYPE));
		
		//handle Views elements : references
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T parse(ArchimateModel result, Element racine, Class<T> clazz, String type) throws ArchimateModelException {
		return (T)findParser(type).parse(findFolder(racine, type), result);
	}
	
	private static IArchiRootFolderParser<?> findParser(String type) {
		if(type == null) return null;
		Optional<IArchiRootFolderParser<?>> parser = parserTypes.stream().filter( p -> type.equals(p.getType())).findFirst();
		return parser.isPresent() ? parser.get() : null;
	}

	private static Element findFolder(Element racine, String folderType) {
		Element result = null;
		for(Node child = racine.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if(folderType.equals(((Element)child).getAttribute("type"))){
				result = (Element)child;
			}
		}
		return result;
	}
}
