function WeightLogListModel(weightLogs){
	this._weightLogs = weightLogs;
	this._selectedIndex = -1;
	
	this.logAddedEvent = new EventHandler(this);
	this.logRemovedEvent = new EventHandler(this);
	this.logChangedEvent = new EventHandler(this);
	this.selectedIndexChanged = new EventHandler(this);
	
	this.getLogs = function(){
		return [].concat(this._weightLogs);
	}
	
	this.getSelectedIndex = function(){
		return this._selectedIndex
	}
	
	this.setSelectedIndex = function(index){
		this._selectedIndex = index;
		this.selectedIndexChanged.notify();
	}
	
	this.addLog = function(weightLog){
		this._weightLogs.push(weightLog);
		this.logAddedEvent.notify({weightLog : weightLog});
	}
	
	this.setLogs = function(weightLogs){
		this._weightLogs = weightLogs;
		this.logAddedEvent.notify({weightLogs : weightLogs});
	}
	
	this.removeSelected = function(){
		var index = this.getSelectedIndex();
		var log = this._weightLogs[index];
		this._weightLogs.splice(index, 1);
		this.logRemovedEvent.notify({ log : log});
		this.setSelectedIndex(-1);
	}
}