package org.toolup.archi.business.archimate;

public class ArchimateModelException extends Exception{
	
	private static final long serialVersionUID = -2031250894702108006L;

	public ArchimateModelException(Throwable cause) {
        super(cause);
    }
	
	public ArchimateModelException(String message) {
        super(message);
    }

    public ArchimateModelException(String message, Throwable cause) {
        super(message, cause);
    }

}
