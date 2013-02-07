function WeightLogEntryModel(){
	this._ID = "";
	this._gramms = "";
	this._recordingDate = "";
	
	this.getID = function(){
		return this._ID;
	}
	
	this.setID = function(ID){
		this._ID = ID;
	}
	
	this.getGramms = function(){
		return this._gramms;
	}
	
	this.setGramms = function(gramms){
		this._gramms = gramms;
	}
	
	this.getRecordingDate = function(){
		return this._recordingDate;
	}
	
	this.setRecordingDate = function(recordingDate){
		this._recordingDate = recordingDate;
	}
}