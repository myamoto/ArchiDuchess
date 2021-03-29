package org.toolup.archi.business.galerie;

import java.util.ArrayList;
import java.util.List;

public class Gallery {

	private final List<String> groups;
	
	public Gallery() {
		groups = new ArrayList<>();
	}
	
	public List<String> getGroups() {
		return groups;
	}
	
	public Gallery addGroup(String group) {
		this.groups.clear();
		if(group != null) this.groups.add(group);
		return this;
	}
	
	public Gallery groups(List<String> groups) {
		this.groups.clear();
		if(groups != null) this.groups.addAll(groups);
		return this;
	}

	@Override
	public String toString() {
		return "Galery [groups=" + groups + "]";
	}

}
