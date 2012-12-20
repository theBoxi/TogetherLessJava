function MeasurementAPI(){
	this.host = "http://localhost:8080";
	
	this.logWeight = function(grams, date){
		var result = $.ajax({
		    url: this.host + '/rest/measurement/logWeight',
		    type: 'get',
		    async: false,
		    data: "grams=" + grams + "&date=" + date
		});
		
		if(199 < result.status && result.status < 300){
			return true;
		} else{
			alert('PUT failed\nStatusText:\t ' + data.statusText + '\nresponse:\t ' + data.responseText);
		}
	}
	
	this.getLogs = function(month){
		var result = $.ajax({
		    url: this.host + '/rest/measurement/actualMonth',
		    type: 'get',
		    async: false,
		});
		
		if(199 < result.status && result.status < 300){
			return $.parseJSON(result.responseText);
		} else{
			alert('PUT failed\nStatusText:\t ' + data.statusText + '\nresponse:\t ' + data.responseText);
		}
	}
}