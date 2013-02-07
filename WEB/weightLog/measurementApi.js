function MeasurementAPI(){
	this.host = "http://<tgl:property name="tgl.host"/>:<tgl:property name="tgl.port"/>";
	
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
			alert('PUT failed\nStatusText:\t ' + result.statusText + '\nresponse:\t ' + result.responseText);
		}
	}
	
	this.deleteWeight = function(id){
		var result = $.ajax({
		    url: this.host + '/rest/measurement/delete',
		    type: 'get',
		    async: false,
		    data: "id=" + id
		});
		
		if(199 < result.status && result.status < 300){
			return true;
		} else{
			alert('PUT failed\nStatusText:\t ' + result.statusText + '\nresponse:\t ' + result.responseText);
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
			alert('PUT failed\nStatusText:\t ' + result.statusText + '\nresponse:\t ' + result.responseText);
		}
	}
}