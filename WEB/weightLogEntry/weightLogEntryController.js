function WeightLogEntryController(model, view){
	this._model = model;
	this._view = view;
	
	var _this = this;
	
	this._view.cancelButtonClickedEvent.attach(function(sender){
		window.history.back();
	});
	
	this._view.deleteButtonClickedEvent.attach(function(sender){
		var api = new MeasurementAPI();
		ok = api.deleteWeight(_this._model.getID());
		if(ok){
			window.history.back();
		}
	});
	
	this._view.saveButtonClickedEvent.attach(function(sender){
		grams = _this._view._weightInputField.attr("value") * 1000;
		recordingDate = _this._view._dateInputField.attr("value");
		var api = new MeasurementAPI();
		ok = api.updateWeight(_this._model.getID(), grams, recordingDate);
		if(ok){
			window.history.back();
		}
	});
	
	this.init = function(){
		this._model.setID($.getURLParam("ID"));
		this._model.setGramms($.getURLParam("gramms"));
		this._model.setRecordingDate($.getURLParam("recordingDate").replace("%20", " "));
		this._view.init();
	}
}