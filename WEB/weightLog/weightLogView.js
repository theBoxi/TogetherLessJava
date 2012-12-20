function WeightLogView(model, listElement){
	this._model = model;
	this._listElement = listElement;
	
	var _this = this;
	
	this._model.logAddedEvent.attach(function(){
		_this.printList();
	});
	
	this._model.logRemovedEvent.attach(function(){
		_this.printList();
	});
	
	this._model.logChangedEvent.attach(function(){
		_this.printList();
	});
	
	this.printList = function(){
		this._listElement.html("");
		var logs = this._model.getLogs();
		for(i in logs){
			if(logs[i].hasOwnProperty("grams")){
				this._listElement.append($("<ul>" + logs[i].grams/1000 + ", " + logs[i].recordingDate + "</ul>"));
			}
		}
		this._model.setSelectedIndex(-1);
	}
	
}