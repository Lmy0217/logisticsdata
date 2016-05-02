<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@page import="java.util.List" %>
	<%@page import="java.text.SimpleDateFormat" %>
	<%@page import="logistics.data.bean.Data" %>
	<%@page import="logistics.data.util.ServiceFactory" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	style="width:100%;height:100%;margin:0px;padding:0px;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Test</title>
<style type="text/css">
html,body {
	margin: 0;
	padding: 0;
}

.iw_poi_title {
	color: #CC5522;
	font-size: 14px;
	font-weight: bold;
	overflow: hidden;
	padding-right: 13px;
	white-space: nowrap
}

.iw_poi_content {
	font: 12px arial, sans-serif;
	overflow: visible;
	padding-top: 4px;
	white-space: -moz-pre-wrap;
	word-wrap: break-word
}
</style>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?key=&v=1.1&services=true"></script>
</head>

<body style="width: 100%; height: 100%; margin: 0px; padding: 0px;">
	<%
		List<Data> dataList = ServiceFactory.getDataService().get(-1, "0000-00-00 00:00:01", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
	int length = dataList.size();	
	String[][] dataArray = new String[dataList.size()][];
		for(int i = 0; i < dataArray.length; i++) {
			String[] data = new String[5];
			data[0] = "" + dataList.get(i).getLower();
			data[1] = "温度：" + dataList.get(i).getT() + "<br/>震动强度：" + dataList.get(i).getK() + "<br/>时间：" + dataList.get(i).getTime();
			data[2] = "" + dataList.get(i).getX() + "|" + dataList.get(i).getY();
			dataArray[i] = data;
		}
	%>
	<div style="position: absulte; width: 100%; height: 100%; border: #ccc"
		id="dituContent"></div>
</body>
<script type="text/javascript">
	function initMap() {
		createMap();
		setMapEvent();
		addMapControl();
		initMarker();
		addMarker();
	}

	function createMap() {
		var map = new BMap.Map("dituContent");
		var point = new BMap.Point(115.949652,28.693851);
		map.centerAndZoom(point, 12);
		window.map = map;
	}

	function setMapEvent() {
		map.enableDragging();
		map.enableScrollWheelZoom();
		map.enableDoubleClickZoom();
		map.enableKeyboard();
	}

	function addMapControl() {
		var ctrl_nav = new BMap.NavigationControl({
			anchor : BMAP_ANCHOR_TOP_LEFT,
			type : BMAP_NAVIGATION_CONTROL_LARGE
		});
		map.addControl(ctrl_nav);
		
		var ctrl_ove = new BMap.OverviewMapControl({
			anchor : BMAP_ANCHOR_BOTTOM_RIGHT,
			isOpen : 1
		});
		map.addControl(ctrl_ove);

		var ctrl_sca = new BMap.ScaleControl({
			anchor : BMAP_ANCHOR_BOTTOM_LEFT
		});
		map.addControl(ctrl_sca);
	}
	
	function Marker(title,content,point,isOpen,icon) {
		this.title = title;
		this.content = content;
		this.point = point;
		this.isOpen = isOpen;
		this.icon = icon;
	}
	
	var markerArr = new Array();
	
	function initMarker() {
		<%for(int i = 0; i < dataList.size(); i++) {%>
		var marker1 = new Marker(
				<%="\"" + dataArray[i][0] + "\""%>,
				<%="\"" + dataArray[i][1] + "\""%>,
				<%="\"" + dataArray[i][2] + "\""%>,
				1,
				{
					w : 23,
					h : 25,
					l : 46,
					t : 21,
					x : 9,
					lb : 12
				});
		markerArr[<%=i%>] = marker1;
		<%}%>
	}
	
	function addMarker() {
		for ( var i = 0; i < markerArr.length; i++) {
			var json = markerArr[i];
			var p0 = json.point.split("|")[0];
			var p1 = json.point.split("|")[1];
			var point = new BMap.Point(p0, p1);
			var iconImg = createIcon(json.icon);
			var marker = new BMap.Marker(point, {
				icon : iconImg
			});
			var iw = createInfoWindow(i);
			var label = new BMap.Label(json.title, {
				"offset" : new BMap.Size(json.icon.lb - json.icon.x + 10, -20)
			});
			marker.setLabel(label);
			map.addOverlay(marker);
			label.setStyle({
				borderColor : "#808080",
				color : "#333",
				cursor : "pointer"
			});

			(function() {
				var index = i;
				var _iw = createInfoWindow(i);
				var _marker = marker;
				_marker.addEventListener("click", function() {
					this.openInfoWindow(_iw);
				});
				_iw.addEventListener("open", function() {
					_marker.getLabel().hide();
				})
				_iw.addEventListener("close", function() {
					_marker.getLabel().show();
				})
				label.addEventListener("click", function() {
					_marker.openInfoWindow(_iw);
				})
				if (!!json.isOpen) {
					label.hide();
					_marker.openInfoWindow(_iw);
				}
			})()
		}
	}
	
	function createInfoWindow(i) {
		var json = markerArr[i];
		var iw = new BMap.InfoWindow(
				"<b class='iw_poi_title' title='" + json.title + "'>"
						+ json.title + "</b><div class='iw_poi_content'>"
						+ json.content + "</div>");
		return iw;
	}
	
	function createIcon(json) {
		var icon = new BMap.Icon("http://map.baidu.com/image/us_cursor.gif",
				new BMap.Size(json.w, json.h), {
					imageOffset : new BMap.Size(-json.l, -json.t),
					infoWindowOffset : new BMap.Size(json.lb + 5, 1),
					offset : new BMap.Size(json.x, json.h)
				})
		return icon;
	}

	initMap();
</script>
</html>