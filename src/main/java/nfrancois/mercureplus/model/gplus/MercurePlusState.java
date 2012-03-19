package nfrancois.mercureplus.model.gplus;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.joda.time.DateTime;

@JsonAutoDetect
public class MercurePlusState {
	
	private DateTime updated;

	public MercurePlusState() {
	}
	
	public MercurePlusState(DateTime updated) {
		super();
		this.updated = updated;
	}

	public DateTime getUpdated() {
		return updated;
	}

	public void setUpdated(DateTime updated) {
		this.updated = updated;
	}
	
	

}
