package ch.boxi.togetherLess.facade.measurement;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class loadMeasurementsResponse {
	private MeasurementDTO[] logs;
	
	public loadMeasurementsResponse(){
		super();
	}
			
	public loadMeasurementsResponse(Set<MeasurementDTO> logs) {
		this();
		this.logs = logs.toArray(new MeasurementDTO[logs.size()]);
	}

	public MeasurementDTO[] getLogs() {
		return logs;
	}

	public void setLogs(MeasurementDTO[] logs) {
		this.logs = logs;
	}
}
