function WeightLogView(model, listElement, weightInputField, addButton){
	this._model = model;
	this._listElement = listElement;
	this._weightInputField = weightInputField;
	this._addButton = addButton;
	
	this.addButtonClickedEvent = new EventHandler(this);
	
	var _this = this;
	
	this._addButton.click(function () {
        _this.addButtonClickedEvent.notify(_this._weightInputField.attr("value")*1000);
    });
	
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
				this._listElement.append($("<li><a href=\"/resource/weightLogEntry/weightLogEntry.html?ID=" + logs[i].ID + "&gramms=" + logs[i].grams + "&recordingDate=" + logs[i].recordingDate + "\">" + logs[i].grams/1000 + ", " + logs[i].recordingDate + "</a></li>"));
			}
		}
		this._model.setSelectedIndex(-1);
	}
	
}