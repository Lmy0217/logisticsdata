
// 百度地图API功能
var map = new BMap.Map("allmap");    // 创建Map实例
map.centerAndZoom(new BMap.Point(115.949,28.693), 13);  // 初始化地图,设置中心点坐标和地图级别

map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
map.addControl(new BMap.NavigationControl());	//左上角，添加默认缩放平移控件
map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT}));	// 左上角，添加比例尺

map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

//console.log("start");

var rs = [];
var startTime = "0000-00-00 00:00:01";
var endTime = "0000-00-00 00:00:01";

//console.log("init end");

var info = -1;
var proInfo = -1;

function lower(id) {
	this.id = id;
	this.data = [];
	this.preMarker = null;
	this.windowInfo = false;
	this.add = (function(aData) {
		this.data = (this.data).concat(aData);
	});
}

//----------------------------------------------
var requestMgr = {
	request: function (startTime, endTime, successCbk) {
		//console.log("request");
		var url = "http://182.254.210.110/get?startTime=" + startTime + "&endTime=" + endTime;
		//console.log(url);
		var xhr = new XMLHttpRequest();
		
		xhr.onreadystatechange = function() {
			//console.log("onreadystatechange " + xhr.readyState + " " + xhr.status);
			if( xhr.readyState == 4  && (xhr.status == 200 || xhr.status == 0) ) {
				//console.log("if");
				//console.log("out" + xhr.responseText + "end");
				var data = JSON.parse(xhr.responseText);
				//rs = rs.concat(JSON.parse(xhr.responseText));
				
				//rs = rs.concat(data);
				addData(data);
				addMarkers(data);
				addTuple(data);
				
				if (successCbk) {
					successCbk();
				}
			}
		}
		//console.log("open");
		xhr.open( "GET", url, true );
		//console.log("send");
		xhr.send( null );
	}
}

function nowTimeCbk (startTime, endTime) {
	//console.log("nowTimeCbk");
	requestMgr.request(startTime, endTime, function() {
		setTimeout(function() {
			startCbk(nowTimeCbk);
		}, 1000);
	});
}

function startCbk(cbk) {
	//console.log("startCbk");
	var now = new Date();
	startTime = endTime;
	
	var Y = now.getFullYear() + "-";
	var M = (now.getMonth() + 1 < 10 ? "0" + (now.getMonth() + 1) : now.getMonth() + 1) + "-";
	var D = (now.getDate() < 10 ? "0" + now.getDate() : now.getDate()) + " ";
	var h = (now.getHours() < 10 ? "0" + now.getHours() : now.getHours()) + ":";
	var m = (now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes()) + ":";
	var s = (now.getSeconds() < 10 ? "0" + now.getSeconds() : now.getSeconds());
	
	endTime = "" + Y + M + D + h + m + s;
	//console.log(endTime);
	
	if (cbk) {
		cbk(startTime, endTime);
	}
};

startCbk(nowTimeCbk);


//---------------------------------------------
	var opts = {
		width : 250,     // 信息窗口宽度
		height: 220,     // 信息窗口高度
		//title : "信息窗口" , // 信息窗口标题
		enableMessage:true//设置允许信息窗发送短息
	};

function addMarkers(data) {
	for ( var i = 0; i < data.length; i++ ) {
		for(var n = 0; n < rs.length; n++) {
			//console.log("data rs " + data[i][0]);
			if(rs[n].id == data[i][0]) {
				if(rs[n].preMarker != null) {
					map.removeOverlay(rs[n].preMarker);
					var polyline = new BMap.Polyline([
						new BMap.Point(rs[n].preMarker.getPosition().lng, rs[n].preMarker.getPosition().lat),
						new BMap.Point(data[i][1],data[i][2])
						], {strokeColor:"blue", strokeWeight:2, strokeOpacity:0.5});
					map.addOverlay(polyline);
					var lineContent = "<b class='iw_poi_title' title='" + data[i][0] + "'>" 
								+ data[i][0] + "</b><div class='iw_poi_content'>" + "温度：" + data[i][3] 
								+ "℃<br/>震动强度：<br/>&nbsp;&nbsp;&nbsp;&nbsp;X轴：" + data[i][4] 
								+ "m/s²<br/>&nbsp;&nbsp;&nbsp;&nbsp;Y轴：" + data[i][5] 
								+ "m/s²<br/>&nbsp;&nbsp;&nbsp;&nbsp;Z轴：" + data[i][6] 
								+ "m/s²<br/>剩余电量：" + data[i][7] + "%<br/>报警标志：" 
								+ (data[i][8] == 0 ? "无报警" : "") + (data[i][8] == 1 ? "震动报警" : "") 
								+ (data[i][8] == 2 ? "温度报警" : "") + (data[i][8] == 3 ? "温度报警、震动报警" : "") 
								+ "<br/>时间：" + data[i][9] + "</div>";
					addLineClickHandler(lineContent,polyline);
				}
				var marker = new BMap.Marker(new BMap.Point(data[i][1],data[i][2]));
				var content = "<b class='iw_poi_title' title='" + data[i][0] + "'>" 
					+ data[i][0] + "</b><div class='iw_poi_content'>" + "温度：" + data[i][3] 
					+ "℃<br/>震动强度：<br/>&nbsp;&nbsp;&nbsp;&nbsp;X轴：" + data[i][4] 
					+ "m/s²<br/>&nbsp;&nbsp;&nbsp;&nbsp;Y轴：" + data[i][5] 
					+ "m/s²<br/>&nbsp;&nbsp;&nbsp;&nbsp;Z轴：" + data[i][6] 
					+ "m/s²<br/>剩余电量：" + data[i][7] + "%<br/>报警标志：" 
					+ (data[i][8] == 0 ? "无报警" : "") + (data[i][8] == 1 ? "震动报警" : "") 
					+ (data[i][8] == 2 ? "温度报警" : "") + (data[i][8] == 3 ? "温度报警、震动报警" : "") 
					+ "<br/>时间：" + data[i][9] + "</div>";
				map.addOverlay(marker);
				addClickHandler(content,marker);
				if(rs[n].windowInfo) {
					
				}
				rs[n].preMarker = marker;
				//console.log("pre " + rs[n].preMarker);
				break;
			}
		}
		
	}
}

	function addClickHandler(content,marker){
		marker.addEventListener("click",function(e){
			openInfo(content,e)}
		);
	}
	
	function openInfo(content,e){
		var p = e.target;
		var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
		var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
		map.openInfoWindow(infoWindow,point); //开启信息窗口
	}
	
	function addLineClickHandler(content,marker){
		marker.addEventListener("mouseover",function(e){
			openLineInfo(content,e)}
		);
	}
	
	function openLineInfo(content,e){
		var p = e.point;
		var point = new BMap.Point(p.lng, p.lat);
		var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
		map.openInfoWindow(infoWindow,point); //开启信息窗口
	}
	//----------------------------------------------------------

	/*
	function deleteMarker(x, y) {
		var allOverlay = map.getOverlays();
		for (var i = 0; i < allOverlay.length; i++) {
			if(allOverlay[i].point.lng == x && allOverlay[i].point.lat == y) {
				map.removeOverlay(allOverlay[i]);
			}
		}
	}//*/
	
	function addData(data) {
		for(var n = 0; n < data.length; n++) {
			var i;
			var flag = true;
			var da = data[n].concat();
			for(i = 0; i < rs.length; i++) {
				if(rs[i].id == da[0]) {
					da.shift();
					rs[i].add(da);
					flag = false;
					break;
				}
			}
			if(flag) {
				var d = new lower(da[0]);
				da.shift();
				d.add(da);
				rs = rs.concat(d);
			}
			//console.log(rs);
		}
	}
	
	function addTuple(data) {
		console.log("addTuple");
		var table = document.getElementById("table");
		
		for(var n = 0; n < data.length; n++) {
			var tr = table.insertRow(-1);
			var td1 = tr.insertCell(-1);
			td1.innerHTML = data[n][0];
			var td2 = tr.insertCell(-1);
			td2.innerHTML = data[n][1];
			var td3 = tr.insertCell(-1);
			td3.innerHTML = data[n][2];
			var td4 = tr.insertCell(-1);
			td4.innerHTML = data[n][3];
			var td5 = tr.insertCell(-1);
			td5.innerHTML = data[n][4];
			var td6 = tr.insertCell(-1);
			td6.innerHTML = data[n][5];
			var td7 = tr.insertCell(-1);
			td7.innerHTML = data[n][6];
			var td8 = tr.insertCell(-1);
			td8.innerHTML = data[n][7];
			var td9 = tr.insertCell(-1);
			td9.innerHTML = data[n][8];
			var td10 = tr.insertCell(-1);
			td10.innerHTML = data[n][9];
		}
	}

/*
// 定义一个控件类,即function
	function ZoomControl(){
	  // 默认停靠位置和偏移量
	  this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
	  this.defaultOffset = new BMap.Size(100, 100);
	}

	// 通过JavaScript的prototype属性继承于BMap.Control
	ZoomControl.prototype = new BMap.Control();

	// 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
	// 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
	ZoomControl.prototype.initialize = function(map){
	  // 创建一个DOM元素
	  var div = document.createElement("div");
	  // 添加文字说明
	  div.appendChild(document.createTextNode("放大2级"));
	  // 设置样式
	  div.style.cursor = "pointer";
	  div.style.border = "1px solid gray";
	  div.style.backgroundColor = "rgba(11, 103, 170, 0.5)";
	  
	  // 绑定事件,点击一次放大两级
	  div.onclick = function(e){
		map.setZoom(map.getZoom() + 2);
	  }
	  // 添加DOM元素到地图中
	  map.getContainer().appendChild(div);
	  // 将DOM元素返回
	  return div;
	}
	// 创建控件
	var myZoomCtrl = new ZoomControl();
	// 添加到地图当中
	map.addControl(myZoomCtrl);
	*/






















