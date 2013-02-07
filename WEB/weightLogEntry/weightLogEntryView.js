function WeightLogEntryView(model, idInputField, weightInputField, dateInputField, cancelButton, deleteButton, saveButton){
	this._model = model;
	this._idInputField = idInputField;
	this._weightInputField = weightInputField;
	this._dateInputField = dateInputField;
	this._cancelButton = cancelButton;
	this._deleteButton = deleteButton;
	this._saveButton = saveButton;
	
	this.cancelButtonClickedEvent = new EventHandler(this);
	this.deleteButtonClickedEvent = new EventHandler(this);
	this.saveButtonClickedEvent = new EventHandler(this);
	
	var _this = this;
	
	this._cancelButton.click(function () {
        _this.cancelButtonClickedEvent.notify();
    });
	
	this._deleteButton.click(function () {
        _this.deleteButtonClickedEvent.notify(_this._idInputField.attr("value"));
    });
	
	this._saveButton.click(function () {
        _this.saveButtonClickedEvent.notify(_this._idInputField.attr("value"), _this._weightInputField.attr("value"), _this._dateInputField.attr("value"));
    });
	
	this.init = function(){
		this._idInputField.attr("value", this._model.getID());
		this._weightInputField.attr("value", this._model.getGramms()/1000);
		this._dateInputField.attr("value", this._model.getRecordingDate());
	}
	
}