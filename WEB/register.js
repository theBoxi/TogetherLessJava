function RegisterAPI(){
	this.host = "http://localhost:8080";
	this.retValue = "";
	
	this.register = function(firstName, lastName, userName, password, password2, email){
		$.ajax({
		    url: this.host + '/rest/user/register',
		    type: 'get',
		    data: "firstName=" +firstName+ "&lastName="+ lastName +"&userName="+ userName +"&password="+ password +"&password2="+ password2+ "&email=" +email, 
		    success: function(data) { 
		    	if(data.registrationOK == "true"){
		    		document.location="registerDone.html";
		    	} else{
		    		for(var i = 0; i < data.errors.entry.length; i++){
		    			if(data.errors.entry[i].value == "true"){
		    				$("[name="+data.errors.entry[i].key+"]").attr("class", "");
		    			} else{
		    				$("[name="+data.errors.entry[i].key+"]").attr("class", "errorInput");
		    			}
		    		}
		    	}
		    },
		    error: function(data) { 
		    	alert('PUT failed\nStatusText:\t ' + data.statusText + '\nresponse:\t ' + data.responseText); 
		    }
		});
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