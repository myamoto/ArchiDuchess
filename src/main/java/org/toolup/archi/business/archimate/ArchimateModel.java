package org.toolup.archi.business.archimate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toolup.archi.business.archimate.application.RootFolderApplication;
import org.toolup.archi.business.archimate.business.RootFolderBusiness;
import org.toolup.archi.business.archimate.implementation.RootFolderImplementation;
import org.toolup.archi.business.archimate.movitation.RootFolderMotivation;
import org.toolup.archi.business.archimate.other.RootFolderOther;
import org.toolup.archi.business.archimate.relation.NoteRelationship;
import org.toolup.archi.business.archimate.relation.RootFolderRelation;
import org.toolup.archi.business.archimate.strategy.RootFolderStrategy;
import org.toolup.archi.business.archimate.technology.RootFolderTechnology;
import org.toolup.archi.business.archimate.view.AbstractChildElemView;
import org.toolup.archi.business.archimate.view.AbstractElementView;
import org.toolup.archi.business.archimate.view.DiagramModelView;
import org.toolup.archi.business.archimate.view.IViewParent;
import org.toolup.archi.business.archimate.view.RootFolderView;
import org.toolup.archi.business.archimate.view.SourceConnectionElementView;

public class ArchimateModel extends AbstractArchimateObject{

	private static Logger logger = LoggerFactory.getLogger(ArchimateModel.class);
	
	private RootFolderStrategy folderStrategy;
	private RootFolderBusiness folderBusiness;
	private RootFolderApplication folderApplication;
	private RootFolderTechnology folderTechnology;
	private RootFolderMotivation folderMotivation;
	private RootFolderImplementation folderImplementation;
	private RootFolderOther folderOther;
	private RootFolderRelation folderRelation;
	private RootFolderView folderViews;
	private String version;

	public ArchimateModel() {
		super();
	}

	public List<IRootFolder<? extends AbstractArchimateElement>> getRootFolderList(){
		List<IRootFolder<? extends AbstractArchimateElement>> result = new ArrayList<>();
		if(folderStrategy != null) result.add(folderStrategy);
		if(folderBusiness != null) result.add(folderBusiness);
		if(folderApplication != null) result.add(folderApplication);
		if(folderTechnology != null) result.add(folderTechnology);
		if(folderMotivation != null) result.add(folderMotivation);
		if(folderImplementation != null) result.add(folderImplementation);
		if(folderOther != null) result.add(folderOther);
		if(folderRelation != null) result.add(folderRelation);
		if(folderViews != null) result.add(folderViews);
		return result;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public RootFolderStrategy getFolderStrategy() {
		return folderStrategy;
	}

	public void setFolderStrategy(RootFolderStrategy folderStrategy) {
		this.folderStrategy = folderStrategy;
	}

	public RootFolderBusiness getFolderBusiness() {
		return folderBusiness;
	}

	public void setFolderBusiness(RootFolderBusiness folderBusiness) {
		this.folderBusiness = folderBusiness;
	}

	public RootFolderApplication getFolderApplication() {
		return folderApplication;
	}

	public void setFolderApplication(RootFolderApplication folderApplication) {
		this.folderApplication = folderApplication;
	}

	public RootFolderTechnology getFolderTechnology() {
		return folderTechnology;
	}

	public void setFolderTechnology(RootFolderTechnology folderTechnology) {
		this.folderTechnology = folderTechnology;
	}

	public RootFolderMotivation getFolderMotivation() {
		return folderMotivation;
	}

	public void setFolderMotivation(RootFolderMotivation folderMotivation) {
		this.folderMotivation = folderMotivation;
	}

	public RootFolderImplementation getFolderImplementation() {
		return folderImplementation;
	}

	public void setFolderImplementation(RootFolderImplementation folderImplementation) {
		this.folderImplementation = folderImplementation;
	}

	public RootFolderOther getFolderOther() {
		return folderOther;
	}

	public void setFolderOther(RootFolderOther folderOther) {
		this.folderOther = folderOther;
	}

	public RootFolderRelation getFolderRelation() {
		return folderRelation;
	}

	public void setFolderRelation(RootFolderRelation folderRelation) {
		this.folderRelation = folderRelation;
	}

	public RootFolderView getFolderViews() {
		return folderViews;
	}

	public void setFolderViews(RootFolderView folderViews) {
		this.folderViews = folderViews;
	}

	public IElement findArchimateRelationship(String elemID) {
		List<IFolder<? extends IElement>> rootFolderList = new ArrayList<>();
		rootFolderList.add(getFolderRelation());
		return findArchimateElement(elemID, rootFolderList);
	}
	
	private IElement findArchimateElement(String elemID, List<IFolder<? extends IElement>> rootFolderList) {
		logger.debug("looking for element with ID {}", elemID);
		if(rootFolderList == null)
			return null;
		for (IFolder<?> iFolder : rootFolderList) {
			if(iFolder == null) continue;
			logger.debug("checking folder {}", iFolder.getName());
			IElement result = findArchimateElementRecur(iFolder, elemID);
			if(result != null)
				return result;
		}
		return null;
	}
	public List<IArchimateObject> listArchimateElements() {
		List<IArchimateObject> result = new ArrayList<>();
		
		for (IFolder<? extends IElement> rootFldr : getAllFoldersList()) {
			addElementsRecur(result, rootFldr);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private void addElementsRecur(List<IArchimateObject> result, IArchimateObject ele) {
		result.add(ele);
		if(ele instanceof IFolder) {
			for (IArchimateObject c : ((IFolder<? extends IArchimateObject>)ele).getElementList()) {
				addElementsRecur(result, c);
			}
			for (IFolder<? extends IArchimateObject> c : ((IFolder<? extends IArchimateObject>)ele).getFolderList()) {
				addElementsRecur(result, c);
			}
		}else if(ele instanceof DiagramModelView) {
			for (AbstractChildElemView c : ((DiagramModelView) ele).getChildList()) {
				addElementsRecur(result, c);
			}
		}else if(ele instanceof IViewParent) {
			for (AbstractChildElemView c : ((IViewParent) ele).getChildList()) {
				addElementsRecur(result, c);
			}
		}else if(ele instanceof AbstractChildElemView) {
			for (SourceConnectionElementView c : ((AbstractChildElemView) ele).getSourceConList()) {
				addElementsRecur(result, c);
			}
		}else if(ele instanceof SourceConnectionElementView) {
			for (SourceConnectionElementView c : ((SourceConnectionElementView) ele).getSourceConList()) {
				addElementsRecur(result, c);
			}
			if(!(((SourceConnectionElementView) ele).getArchimateRelationship() instanceof NoteRelationship))
				result.add(((SourceConnectionElementView) ele).getArchimateRelationship());
			result.add(((SourceConnectionElementView) ele).getSource());
			result.add(((SourceConnectionElementView) ele).getTarget());
		}
	}
	public IElement findArchimateElementView(String elemID) {
		logger.debug("looking for view element with ID {}", elemID);
		List<IFolder<? extends IElement>> result = new ArrayList<>();
		result.add(getFolderViews());
		//result = Arrays.asList(getFolderViews());
		return findArchimateElement(elemID, result);
	}
	public IElement findArchimateElement(String elemID) {
		return findArchimateElement(elemID, getReferencableObjectFolderList());
	}
	private List<IFolder<? extends IElement>> getAllFoldersList(){
		List<IFolder<? extends IElement>> result = new ArrayList<>(getReferencableObjectFolderList());
		result.add(getFolderViews());
		return result;
	}
	private List<IFolder<? extends IElement>> getReferencableObjectFolderList(){
		List<IFolder<? extends IElement>> result = new ArrayList<>();
		
		result.add(getFolderApplication());
		result.add(getFolderBusiness());
		result.add(getFolderImplementation());
		result.add(getFolderMotivation());
		result.add(getFolderStrategy());
		result.add(getFolderTechnology());
		result.add(getFolderOther());
		return result;
	}

	private static IElement findArchimateElementRecur(IFolder<? extends IElement> iFolder, String elemID) {
		if(iFolder == null) return null;
		logger.debug("checking iFolder {} which has {} elems and {} folders"
				, iFolder.getName()
				, iFolder.getElementList().size()
				, iFolder.getFolderList().size());
		for (IElement elem : iFolder.getElementList()) {
			logger.debug("checking elem {}", elem.getId());
			if(elem.getId().equals(elemID)){
				return elem;
			}
		}
		for (IFolder<?> subFolder : iFolder.getFolderList()) {
			logger.debug("checking subfolder {}", subFolder.getName());
			IElement result = findArchimateElementRecur(subFolder, elemID);
			if(result != null)
				return result;
		}
		return null;
	}

	public List<DiagramModelView> getDiagramModelViewList() {
		return findDiagramModelViewRecur(getFolderViews());
	}

	private List<DiagramModelView> findDiagramModelViewRecur(IFolder<AbstractElementView> folderViews) {
		List<DiagramModelView> result = new ArrayList<>();
		for (AbstractElementView view : folderViews.getElementList()) {
			if(view instanceof DiagramModelView)
				result.add((DiagramModelView) view);
		}
		for (IFolder<AbstractElementView> subFolder : folderViews.getFolderList()) {
			result.addAll(findDiagramModelViewRecur(subFolder));
		}
		return result;
	}

	public static ArchimateModel createBaseModel() {
		ArchimateModel result = new ArchimateModel();
		result.setFolderApplication(new RootFolderApplication());
		result.setFolderBusiness(new RootFolderBusiness());
		result.setFolderImplementation(new RootFolderImplementation());
		result.setFolderMotivation(new RootFolderMotivation());
		result.setFolderOther(new RootFolderOther());
		result.setFolderRelation(new RootFolderRelation());
		result.setFolderStrategy(new RootFolderStrategy());
		result.setFolderTechnology(new RootFolderTechnology());
		result.setFolderViews(new RootFolderView());
		return result;
	}

	public String getTargetConnectionsListStr(IViewParent viewPrt) {
		if(folderViews == null)
			return null;

		List<SourceConnectionElementView> result = getTargetSourceConRecur(viewPrt.getId(), folderViews.getElementList(), folderViews.getFolderList());

		StringBuilder resultSb = new StringBuilder();
		for (int i = 0; i < result.size(); ++i) {
			SourceConnectionElementView sourceConnectionElementView = result.get(i);
			resultSb.append(sourceConnectionElementView.getId());
			if(i < result.size() - 1)
				resultSb.append(" ");
		}
		return resultSb.toString();
	}

	private List<SourceConnectionElementView> getTargetSourceConRecur(
			String targetId
			, List<AbstractElementView> elementList
			, List<IFolder<AbstractElementView>> folderList) {
		List<SourceConnectionElementView> result = new ArrayList<>();
		for (AbstractElementView viewElem : elementList) {
			if(viewElem instanceof DiagramModelView)
				result.addAll(getTargetSourceConRecur(targetId, ((DiagramModelView)viewElem).getChildList()));
		}
		for (IFolder<AbstractElementView> folder : folderList) {
			result.addAll(getTargetSourceConRecur(targetId, folder.getElementList(), folder.getFolderList()));
		}
		return result;
	}

	private List<SourceConnectionElementView> getTargetSourceConRecur(String targetId, List<AbstractChildElemView> childList) {
		List<SourceConnectionElementView> result = new ArrayList<>();
		for (AbstractElementView child : childList) {
			if(child instanceof IViewParent) {
				result.addAll(getTargetSourceConRecur(targetId, ((IViewParent)child).getChildList()));
				for (SourceConnectionElementView sourceCon : ((IViewParent)child).getSourceConList()) {
					if(targetId.equals(sourceCon.getTarget().getId())) {
						result.add(sourceCon);
					}
				}
			}
		}
		return result;
	}
	
	public DiagramModelView findViewName(String viewName) {
		Optional<DiagramModelView> optional = getDiagramModelViewList()
				.stream()
				.filter(v -> viewName.equals(v.getName()))
				.findFirst();
		return optional.isPresent() ? optional.get() : null;
	}

	@Override
	public String toString() {
		return "ArchimateModel [folderStrategy=" + folderStrategy + ", folderBusiness=" + folderBusiness
				+ ", folderApplication=" + folderApplication + ", folderTechnology=" + folderTechnology
				+ ", folderMotivation=" + folderMotivation + ", folderImplementation=" + folderImplementation
				+ ", folderOther=" + folderOther + ", folderRelation=" + folderRelation + ", folderViews=" + folderViews
				+ ", version=" + version + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((folderApplication == null) ? 0 : folderApplication.hashCode());
		result = prime * result + ((folderBusiness == null) ? 0 : folderBusiness.hashCode());
		result = prime * result + ((folderImplementation == null) ? 0 : folderImplementation.hashCode());
		result = prime * result + ((folderMotivation == null) ? 0 : folderMotivation.hashCode());
		result = prime * result + ((folderOther == null) ? 0 : folderOther.hashCode());
		result = prime * result + ((folderRelation == null) ? 0 : folderRelation.hashCode());
		result = prime * result + ((folderStrategy == null) ? 0 : folderStrategy.hashCode());
		result = prime * result + ((folderTechnology == null) ? 0 : folderTechnology.hashCode());
		result = prime * result + ((folderViews == null) ? 0 : folderViews.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArchimateModel other = (ArchimateModel) obj;
		if (folderApplication == null) {
			if (other.folderApplication != null)
				return false;
		} else if (!folderApplication.equals(other.folderApplication))
			return false;
		if (folderBusiness == null) {
			if (other.folderBusiness != null)
				return false;
		} else if (!folderBusiness.equals(other.folderBusiness))
			return false;
		if (folderImplementation == null) {
			if (other.folderImplementation != null)
				return false;
		} else if (!folderImplementation.equals(other.folderImplementation))
			return false;
		if (folderMotivation == null) {
			if (other.folderMotivation != null)
				return false;
		} else if (!folderMotivation.equals(other.folderMotivation))
			return false;
		if (folderOther == null) {
			if (other.folderOther != null)
				return false;
		} else if (!folderOther.equals(other.folderOther))
			return false;
		if (folderRelation == null) {
			if (other.folderRelation != null)
				return false;
		} else if (!folderRelation.equals(other.folderRelation))
			return false;
		if (folderStrategy == null) {
			if (other.folderStrategy != null)
				return false;
		} else if (!folderStrategy.equals(other.folderStrategy))
			return false;
		if (folderTechnology == null) {
			if (other.folderTechnology != null)
				return false;
		} else if (!folderTechnology.equals(other.folderTechnology))
			return false;
		if (folderViews == null) {
			if (other.folderViews != null)
				return false;
		} else if (!folderViews.equals(other.folderViews))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
	
}
