function WeightLogController(model, view){
	this._model = model;
	this._view = view;
	
	var _this = this;
	
	this._view.addButtonClickedEvent.attach(function(sender, weight){
		var api = new MeasurementAPI();
		api.logWeight(weight, "");
		_this.init();
	});
	
	this.init = function(){
		var api = new MeasurementAPI();
		var resp = api.getLogs(12);
		this._model.setLogs(resp.logs);
	}
}