package sg.edu.nus.iss.mobilelusis.model;

public class CollectionPoint {
	
	private int identity = 0;
	private String description = null;
	private String location = null;
	
	public CollectionPoint(int id, String desc, String location) {
		super();
		this.identity = id;
		this.description = desc;
		this.setLocation(location);
	}


	public int getIdentity() {
		return identity;
	}


	public void setIdentity(int identity) {
		this.identity = identity;
	}


	public String getDescription() {
		return description;
	}

	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	@Override
	public String toString() {
		return this.location;
	}

}
