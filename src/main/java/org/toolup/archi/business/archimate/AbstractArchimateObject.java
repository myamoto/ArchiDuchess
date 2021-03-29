package org.toolup.archi.business.archimate;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractArchimateObject implements IArchimateObject{
	
	private static AtomicInteger idIntCPT = new AtomicInteger(5);
	
	private int idMxgraph;
	private String id;
	private String name;
	
	public AbstractArchimateObject() {
		id = UUID.randomUUID().toString();
		idMxgraph = nextId();
	}
	
	public static int nextId() {
		return idIntCPT.getAndIncrement();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIdMxgraph() {
		return idMxgraph;
	}


	@Override
	public String toString() {
		return "AbstractArchimateObject [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		AbstractArchimateObject other = (AbstractArchimateObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
