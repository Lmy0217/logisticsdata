
// 百度地图API功能
var map = new BMap.Map("allmap");    // 创建Map实例
map.centerAndZoom(new BMap.Point(115.949,28.693), 13);  // 初始化地图,设置中心点坐标和地图级别

map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
map.addControl(new BMap.NavigationControl());	//左上角，添加默认缩放平移控件
map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT}));	// 左上角，添加比例尺

map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

console.log("start");

var rs = [];
var startTime = "0000-00-00 00:00:01";
var endTime = "0000-00-00 00:00:01";

console.log("init end");

var requestMgr = {
	request: function (startTime, endTime, successCbk) {
		console.log("request");
		var url = "http://182.254.210.110/get?startTime=" + startTime + "&endTime=" + endTime;
		console.log(url);
		var xhr = new XMLHttpRequest();
		
		xhr.onreadystatechange = function() {
			console.log("onreadystatechange " + xhr.readyState + " " + xhr.status);
			if( xhr.readyState == 4  && (xhr.status == 200 || xhr.status == 0) ) {
				console.log("if");
				console.log("out" + xhr.responseText + "end");
				var data = JSON.parse(xhr.responseText);
				//rs = rs.concat(JSON.parse(xhr.responseText));
				addMarkers(data);
				rs = rs.concat(data);
				
				if (successCbk) {
					successCbk();
				}
			}
		}
		console.log("open");
		xhr.open( "GET", url, true );
		console.log("send");
		xhr.send( null );
	}
}

function nowTimeCbk (startTime, endTime) {
	console.log("nowTimeCbk");
	requestMgr.request(startTime, endTime, function() {
		setTimeout(function() {
			startCbk(nowTimeCbk);
		}, 1000);
	});
}

function startCbk(cbk) {
	console.log("startCbk");
	var now = new Date();
	startTime = endTime;
	
	var Y = now.getFullYear() + "-";
	var M = (now.getMonth() + 1 < 10 ? "0" + (now.getMonth() + 1) : now.getMonth() + 1) + "-";
	var D = (now.getDate() < 10 ? "0" + now.getDate() : now.getDate()) + " ";
	var h = (now.getHours() < 10 ? "0" + now.getHours() : now.getHours()) + ":";
	var m = (now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes()) + ":";
	var s = (now.getSeconds() < 10 ? "0" + now.getSeconds() : now.getSeconds());
	
	endTime = "" + Y + M + D + h + m + s;
	console.log(endTime);
	
	if (cbk) {
		cbk(startTime, endTime);
	}
};

startCbk(nowTimeCbk);


	var opts = {
		width : 250,     // 信息窗口宽度
		height: 100,     // 信息窗口高度
		//title : "信息窗口" , // 信息窗口标题
		enableMessage:true//设置允许信息窗发送短息
	};

function addMarkers(data) {
	for ( var i = 0; i < data.length; i++ ) {
		var marker = new BMap.Marker(new BMap.Point(data[i][1],data[i][2]));
		var content = "<b class='iw_poi_title' title='" + data[i][0] + "'>" + data[i][0] + "</b><div class='iw_poi_content'>" + "温度：" + data[i][3] + "<br/>震动强度：" + data[i][4] + "<br/>时间：" + data[i][5] + "</div>";
		map.addOverlay(marker);
		addClickHandler(content,marker);
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





















