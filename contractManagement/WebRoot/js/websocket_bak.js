var ws = null;
var count = 0;
var isConnected = false;//判断当前页面是否连接到后台

function connect() {
	
	var wsUrl = "";
	if (window.location.protocol == 'http:') {
		wsUrl = 'ws://' + window.location.host + '/demo';
	} else {
		wsUrl = 'wss://' + window.location.host + '/demo';
	}
	if ('WebSocket' in window) {
		ws = new WebSocket(wsUrl);
	} else if ('MozWebSocket' in window) {
		ws = new MozWebSocket(wsUrl);
	} else {
		alert('WebSocket is not supported by this browser.');
		return;
	}
	ws.onopen = function() {
		// log('Info: WebSocket connection opened.');
		isConnected = true;
	};
	ws.onmessage = function(event) {
		// log('Received: ' + event.data);
		var recMsg = event.data;
		//alert(recMsg);
		var obj = eval("(" + recMsg + ")");
		//alert(obj.type);
		if ("data_refresh" == obj.type) {//数据更新
			data_refresh(obj);
		} else if ("alarm" == obj.type) {//报警
			alarm(obj);
		} else if ("equipment_add" == obj.type) {//设备添加
			equipment_add(obj);
		} else if ("equipment_delete" == obj.type) {//设备删除
			equipment_delete(obj);
		} else if ("data_get" == obj.type) {//点击设备主动获取数据
			data_get(obj);
		} else {
			alert("others");
		}
		
	};
	ws.onclose = function(event) {
		isConnected = false;
		//服务器升级or重启需在10分钟之内完成，终端在检测到服务器关掉之后，10分钟之后会自动刷新页面进行重连
		//如果10分钟之内没起来，页面会提示无法访问，只能进行人工手动刷新
		setTimeout("close()", 600000);
	};
}

function close() {
	location.reload();
	//log('Info: WebSocket connection closed, Code: ' + event.code + (event.reason == "" ? "" : ", Reason: " + event.reason));
	//log("服务器断开连接，请刷新页面");
}

function equipment_add(obj) { //TODO 需要添加一个设备所属排字段，在设备添加之前判断该设备是否属于该排，如果不属于则直接跳过，只有属于当前排才添加进去
	//alert("equipment_add function");
	var container = document.getElementById("cabinet_container");
	
	var divHeight = 1150;
	var divWidth = 1080;
	
	var equipments = obj.equipmentAdds;
	for ( var i = 0; i < equipments.length; i++) {
		var equipment = equipments[i];
		var rowId = equipment.rowId;
		var row = document.getElementById(rowId);
		if (!row) {
			continue;
		}
		var eq = document.createElement('div');
		eq.id = equipment.equipmentId;
		var x = divWidth/100*(0.25+5*equipment.x2d);
		
		var unitHeight =divHeight/42;
		var unitWidth = divWidth/100*4.5;
		
		var x2d = x;
		var heightN = parseInt(equipment.y2d) - 1 + parseInt(equipment.y2dSize);
		var y = unitHeight*heightN;
		var y2d = -y;
		
		var heightEq =  unitHeight *equipment.y2dSize;
		eq.style.height=heightEq + "px"; //添加设备的高
		var blankWidth = divWidth/100*0.5;
		var widthEq = unitWidth * equipment.x2dSize + blankWidth*(parseInt(equipment.x2dSize)-1);
		
		eq.style.width= widthEq+"px";				//添加设备的宽
		var eqType = equipment.equipmentType;
		//alert(eqType);
		if ("1" == eqType) {
			//alert("1" == eqType);
			eq.style.color = "#666666";
			eq.style.backgroundColor = "#e0fbaa";
			eq.style.border = "1px solid";
			eq.style.fontSize = "11px";
			eq.style.borderColor = "black";
		} else if ("2" == eqType) {
			eq.style.color = "#666666";
			eq.style.backgroundColor = "#a3c8ff";
			eq.style.border = "1px solid";
			eq.style.fontSize = "11px";
			eq.style.borderColor = "black";
		} else if ("3" == eqType) {
			eq.style.color = "#666666";
			eq.style.backgroundColor = "#fcd0a3";
			eq.style.border = "1px solid";
			eq.style.fontSize = "11px";
			eq.style.borderColor = "black";
		} else if ("4" == eqType) {
			eq.style.color = "#666666";
			eq.style.backgroundColor = "#fcee77";
			eq.style.border = "1px solid";
			eq.style.fontSize = "11px";
			eq.style.borderColor = "black";
		} else if ("5" == eqType) {
			eq.style.color = "#666666";
			eq.style.backgroundColor = "#9effec";
			eq.style.border = "1px solid";
			eq.style.fontSize = "11px";
		} else if ("6" == eqType) {
			eq.style.color = "#666666";
			eq.style.backgroundColor = "#b6b6b5";
			eq.style.border = "1px solid";
			eq.style.fontSize = "11px";
		} else if ("12" == eqType) {
			eq.style.color = "#666666";
			eq.style.backgroundColor = "#b6b6b5";
			eq.style.border = "1px solid";
			eq.style.borderColor = "black";
			eq.style.fontSize = "11px";
		} else {
			eq.style.color = "#666666";
			eq.style.backgroundColor = "#fcee77";
			eq.style.border = "1px solid";
			eq.style.borderColor = "black";
			eq.style.fontSize = "11px";
		}
		//alert(3);
		if (equipment.y2dSize > 3) {
			eq.style.lineHeight =widthEq+ "px";
			eq.style.layoutFlow ="vertical-ideographic";
			eq.style.fontSize = "15px";
			eq.style.overflow = "hidden";
		} else {
			eq.style.lineHeight =heightEq+ "px";
			eq.style.overflow = "hidden";
		}
		
		eq.title = equipment.shortName2d;
		eq.style.textAlign = "center";
		eq.style.position = "absolute";
		eq.style.marginLeft = x2d + "px";
		eq.style.marginTop = y2d + "px";
		eq.style.cursor = "pointer";
		eq.setAttribute("onclick","getData('"+equipment.equipmentId+"')");
		//alert(4);
		eq.appendChild(document.createTextNode(equipment.shortName2d));
		container.appendChild(eq);
		//alert(5);
		
	}
	
}

function equipment_show(rowId,equipmentId,x2d,y2d,y2dSize,x2dSize,equipmentType,shortName2d) { //
	//alert("equipment_add function");
	var container = document.getElementById("cabinet_container");
	
	//var divHeight = container.scrollHeight;
	var divHeight = screenHeight;
	var divWidth = container.scrollWidth;
	
		var row = document.getElementById(rowId);
		if (!row) {
			return;
		}
		var eq = document.createElement('div');
		eq.id = equipmentId;
		var x = divWidth/100*(0.25+5*x2d);
		
		var unitHeight =divHeight/42;
		var unitWidth = divWidth/100*4.5;
		
		var x2d = x;
		var heightN = parseInt(y2d) - 1 + parseInt(y2dSize);
		var y = unitHeight*heightN;
		var y2d = -y;
		
		var heightEq =  unitHeight *y2dSize;
		eq.style.height=heightEq + "px"; //添加设备的高
		var blankWidth = divWidth/100*0.5;
		var widthEq = unitWidth * x2dSize + blankWidth*(parseInt(x2dSize)-1);
		eq.style.width= widthEq+"px";				//添加设备的宽
		var eqType = equipmentType;
		//alert(eqType);
		if ("1" == eqType) {
			//alert("1" == eqType);
			eq.style.color = "#666666";
			eq.style.backgroundColor = "#e0fbaa";
			eq.style.border = "1px";
			eq.style.fontSize = "11px";
		} else if ("2" == eqType) {
			eq.style.color = "#666666";
			eq.style.backgroundColor = "#a3c8ff";
			eq.style.border = "1px";
			eq.style.fontSize = "11px";
		} else if ("3" == eqType) {
			eq.style.color = "#666666";
			eq.style.backgroundColor = "#fcd0a3";
			eq.style.border = "1px";
			eq.style.fontSize = "11px";
		} else if ("4" == eqType) {
			eq.style.color = "#666666";
			eq.style.backgroundColor = "#fcee77";
			eq.style.border = "1px";
			eq.style.fontSize = "11px";
		} else if ("5" == eqType) {
			eq.style.color = "#666666";
			eq.style.backgroundColor = "#9effec";
			eq.style.border = "1px";
			eq.style.fontSize = "11px";
		} else if ("6" == eqType) {
			eq.style.color = "#666666";
			eq.style.backgroundColor = "#b6b6b5";
			eq.style.border = "1px";
			eq.style.fontSize = "11px";
		} else if ("7" == eqType) {
			eq.style.color = "#666666";
			eq.style.backgroundColor = "#b6b6b5";
			eq.style.border = "1px";
			eq.style.fontSize = "11px";
		} else {
			eq.style.color = "#666666";
			eq.style.backgroundColor = "#b6b6b5";
			eq.style.border = "1px";
			eq.style.fontSize = "11px";
		}
		//alert(3);
		eq.style.textAlign = "center";
		eq.style.position = "absolute";
		eq.style.marginLeft = x2d + "px";
		eq.style.marginTop = y2d + "px";
		eq.style.cursor = "pointer";
		eq.setAttribute("onclick","getData('"+equipmentId+"')");
		//alert(4);
		eq.appendChild(document.createTextNode(shortName2d));
		container.appendChild(eq);
		//alert(5);
		
	
}

function alarm(obj) {
	var equipments = obj.alarms;
	for (var i = 0 ;i <equipments.length; i++) {
		var equipment = equipments[i];
		var eq = document.getElementById(equipment.equipmentId);
		if (!eq) {
			continue;
		}
		eq.style.backgroundColor= "red";	
		
	}
	
}

function data_get(obj) {
	
	/*var cabinet = obj.cabinet;
	document.getElementById(cabinet.equipmentId+"_name" ).innerHTML= cabinet.name;*/
	
	var equipment = obj.equipment;
	document.getElementById(equipment.equipmentId + "_name").innerHTML = equipment.equipmentName;
	/*document.getElementById(equipment.equipmentId + "_name").innerHTML = equipment.equipmentName;
	document.getElementById(equipment.equipmentId + "_labe").innerHTML = equipment.equipmentName;
	document.getElementById(equipment.equipmentId + "_type").innerHTML = equipment.equipmentName;
	document.getElementById(equipment.equipmentId + "_position").innerHTML = equipment.equipmentName;
	document.getElementById(equipment.equipmentId + "_ip").innerHTML = equipment.equipmentName;
	document.getElementById(equipment.equipmentId + "_department").innerHTML = equipment.equipmentName;
	document.getElementById(equipment.equipmentId + "_system").innerHTML = equipment.equipmentName;
	
	var alarm = obj.alarm;
	document.getElementById(alarm.equipmentId + "_alarm_name").innerHTML = alarm.alarmType;
	document.getElementById(alarm.equipmentId + "_alarm_level").innerHTML = alarm.alarmType;
	document.getElementById(alarm.equipmentId + "_alarm_type").innerHTML = alarm.alarmType;
	document.getElementById(alarm.equipmentId + "_alarm_content").innerHTML = alarm.alarmType;*/
	
}

function data_refresh(obj) {
	var dataRefreshs = obj.dataReFresh;
	for ( var i = 0; i < dataRefreshs.length; i++) {
		var dataRefresh = dataRefreshs[i];
		var eq = document.getElementById(dataRefresh.id);
		if (!eq) {
			continue;
		}
		eq.innerHTML = dataRefresh.value; //TODO 刷新数据
	}
}

function equipment_delete(obj) {
	var container = document.getElementById("cabinet_container");
	var equipments = obj.equipmentDeletes;
	for ( var i = 0; i < equipments.length; i++) {
		var equipment = equipments[i];
		var eq = document.getElementById(equipment.equipmentId);
		if (!eq) {
			continue;
		}
		container.removeChild(eq);
	}
}

function disconnect() {
	if (ws != null) {
		ws.close();
		ws = null;
	}
}

function echo() {
	if (ws != null) {
		var message = document.getElementById('message').value;
		log('Sent: ' + message);
		ws.send(message);
	} else {
		alert('WebSocket connection not established, please connect.');
	}
}

function getData(equipmentId) {
	//需要将设备相关字段对应的ID做相应的修改 TODO 这样是不行的，需要做修改，父框innerHTML来实现
	document.getElementById("equipment_name").innerHTML="<span id='"+ equipmentId+"_name'></span>";
	/*var name = '${pd.IS_DEFAULT.EQUIPMENT_ID}' +"_name";
	alert(name);
	document.getElementById("${pd.IS_DEFAULT.EQUIPMENT_ID }_name").setAttribute("id", equipmentId+"_name");
	document.getElementById("${pd.IS_DEFAULT.EQUIPMENT_ID }_labe").setAttribute("id", equipmentId+"_labe");
	document.getElementById("${pd.IS_DEFAULT.EQUIPMENT_ID }_type").setAttribute("id", equipmentId+"_type");
	document.getElementById("${pd.IS_DEFAULT.EQUIPMENT_ID }_position").setAttribute("id", equipmentId+"_position");
	document.getElementById("${pd.IS_DEFAULT.EQUIPMENT_ID }_ip").setAttribute("id", equipmentId+"_ip");
	document.getElementById("${pd.IS_DEFAULT.EQUIPMENT_ID }_department").setAttribute("id", equipmentId+"_department");
	document.getElementById("${pd.IS_DEFAULT.EQUIPMENT_ID }_system").setAttribute("id", equipmentId+"_system");
	
	document.getElementById("${pd.IS_DEFAULT.EQUIPMENT_ID }_alarm_name").setAttribute("id", equipmentId+"_alarm_name");
	document.getElementById("${pd.IS_DEFAULT.EQUIPMENT_ID }_alarm_level").setAttribute("id", equipmentId+"_alarm_level");
	document.getElementById("${pd.IS_DEFAULT.EQUIPMENT_ID }_alarm_type").setAttribute("id", equipmentId+"_alarm_type");
	document.getElementById("${pd.IS_DEFAULT.EQUIPMENT_ID }_alarm_content").setAttribute("id", equipmentId+"_alarm_content");*/
	
	var jsonMsg='{"equipmentId": "' +equipmentId + '"}';
	ws.send(jsonMsg);
}

function log(message) {
	var console = document.getElementById('console');
	var p = document.createElement('p');
	p.style.wordWrap = 'break-word';
	p.appendChild(document.createTextNode(message));
	console.appendChild(p);
	while (console.childNodes.length > 25) {
		console.removeChild(console.firstChild);
	}
	console.scrollTop = console.scrollHeight;
}