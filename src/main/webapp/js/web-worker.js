self.addEventListener('message', function(e) {
	var data = JSON.parse(e.data);
	var response = {}
	for(var i = 0; i < data.length; ++i) {
		response[data[i].id] = data[i];
	}

	self.postMessage(JSON.stringify(response));
}, false);