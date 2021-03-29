package org.toolup.archi.business.archimate;

import java.util.HashMap;
import java.util.Map;

import org.toolup.archi.business.mxgraph.IMxElemArchi;

public abstract class AbstractArchimateElement extends AbstractArchimateObject implements IElement{

	private String documentation;
	private Map<String, String> customMap;

	public AbstractArchimateElement() {
		super();
		customMap = new HashMap<>();
	}


	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public String putCustomValue(final String key, final String value) {
		return customMap.put(key, value);
	}

	public String getCustomValue(final String key) {
		return customMap.get(key);
	}

	public boolean hasCustomValues() {
		return !customMap.isEmpty();
	}

	public Map<String, String> getCustomMap() {
		return customMap;
	}


	public boolean hasDocumentation() {
		return documentation != null && !documentation.isEmpty();
	}


	@Override
	public String toString() {
		return "AbstractArchimateElement [documentation=" + documentation + ", customMap=" + customMap + "] extends " + super.toString();
	}


	public IMxElemArchi getMxGraphDecorateElement(int x, int y, int width, int padding, String fillColor, String fontColor) {
		return null;
	}

	public String getDecoratedStyle(String s) {
		return s;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((customMap == null) ? 0 : customMap.hashCode());
		result = prime * result + ((documentation == null) ? 0 : documentation.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractArchimateElement other = (AbstractArchimateElement) obj;
		if (customMap == null) {
			if (other.customMap != null)
				return false;
		} else if (!customMap.equals(other.customMap))
			return false;
		if (documentation == null) {
			if (other.documentation != null)
				return false;
		} else if (!documentation.equals(other.documentation))
			return false;
		return true;
	}

	@Override
	public boolean isOverrideMxDraw() {
		return false;
	}	
}