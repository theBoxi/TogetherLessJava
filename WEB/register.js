function RegisterAPI(){
	this.host = "http://localhost:8080";
	this.retValue = "";
	
	this.register = function(firstName, lastName, userName, password, password2, email){
		var result = $.ajax({
		    url: this.host + '/rest/user/register',
		    type: 'get',
		    async: false,
		    data: "firstName=" +firstName+ "&lastName="+ lastName +"&userName="+ userName +"&password="+ password +"&password2="+ password2+ "&email=" +email
		});
		
		if(199 < result.status && result.status < 300){
			var data = $.parseJSON(result.responseText);
			if(data.registrationOK == "true"){
	    		return true;
	    	} else{
	    		return data.errors.entry;
	    	}
		} else{
			alert('PUT failed\nStatusText:\t ' + data.statusText + '\nresponse:\t ' + data.responseText);
		}
	}
	
	this.activate = function(activationCode){
		var result = $.ajax({
		    url: this.host + '/rest/user/activate',
		    type: 'get',
		    data: 'activationCode=' + activationCode,
		    async: false
		});
		
		if(199 < result.status && result.status < 300){
			return "activationDone";
		}else{
			if(result.status == 461){
				return "alreadyActivated";
	    	} else{
	    		return "activationFailed";
	    	}
		}
	}
}