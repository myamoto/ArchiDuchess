package org.toolup.archi.io.archi;

public class ArchimateIOException extends Exception {

	private static final long serialVersionUID = -6764091481897842903L;
	
	public ArchimateIOException(String msg, Throwable t) {
		super(msg, t);
	}

	public ArchimateIOException(String msg) {
		super(msg);
	}
}
