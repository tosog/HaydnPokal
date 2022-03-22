<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String url = "http://localhost/hp";
	url = "http://skateaustria.vs91-250-98-130.cloud-he.de/img/competition/5.2019_20/090/";
	url = "http://skateaustria.vs91-250-98-130.cloud-he.de/img/competition/7.2021_22/130/";
	String urlpar = request.getParameter("url");
	if (urlpar != null && urlpar.length() > 0)
		url = urlpar;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Haydn Pokal 2020</title>
<style>
html {
	cursor: none;
}

body {
	background-color: #253a47;
	margin: 0px;
	padding: 0px;
	font-family: "Calibri";
	color: #eeeeee;
	cursor: none;
}

table {
	margin: 0px;
	padding: 0px;
	border-spacing: 0px;
	cursor: none;
}
.header {
	background-color: #349886;
	padding: 10px;
}

.headertbl {
	width: 1850px;
	border: 0px;
	font-size: 35px;
	font-weight: bold;
}

.schedulediv {
	position: absolute;
	left: 70px;
	top: 150px;
	width: 490px;
	height: 880px;
	background-color: #3da4c6;
}

.errordiv {
	position: absolute;
	left: 600px;
	right: 600px;
	top: 300px;
	background-color: #ffffff;
	color: #810003;
	text-align: center;
	font-size: 40px;
	font-weight: bold;
}

.errordiv img {
	padding: 20px;
}

.complistdiv {
	position: absolute;
	left: 620px;
	top: 150px;
	width: 1220px;
	height: 880px;
	background-color: #65a776;
}

.rankinghdr {
	background-color: #2693b7;
	width: 100%;
	height: 30px;
	padding: 10px;
	font-weight: bold;
	font-size: 30px;
}

.schedulehdr {
	background-color: #2b8e41;
	width: 100%;
	height: 30px;
	padding: 10px;
	font-weight: bold;
	font-size: 30px;
}

.rankingtbl {
	position: relative;
	top: 20px;
	left: 20px;
	font-size: 20px;
	width: 445px;
}

.rankingtbl td {
	border-bottom: 1px solid #c7c7c7;
}

.startertbl {
	position: relative;
	top: 20px;
	font-size: 20px;
	width: 1150px;
}

.startertbl td {
	border-bottom: 1px solid #c7c7c7;
}

.finishertbl {
	position: relative;
	top: 20px;
	font-size: 20px;
	width: 1150px;
}

.finishertbl th {
	font-size: 24px;
	font-weight: bold;
	border-bottom: 2px solid #ffffff;
	text-align: left;
}

.finishertbl td {
	border-bottom: 1px solid #c7c7c7;
}

</style>
<script src="jquery-3.2.1.min.js"></script>
<script>
var init = false;
var errcnt = 0;

function jsonResponse(data) {
	
	var cntSat = 0;
	var cntSun = 0;
	var active = null;
	
	if (typeof data == "string") {
		handleErr(null, "Web Error", data) 
		return;
	}
	
	$(".schedulediv").show();
	$(".complistdiv").show();
	$(".errordiv").hide();
	errcnt = 0;
	
	$(".rankingtbl tbody tr").remove();
	data.forEach(function(entry) {
		
		if (entry.finisher.length > 0)
			active = entry;
		
		if (entry.time == "09:00") {
			cntSat++;
		}
		else if (entry.time == "10:00") {
			cntSun++;
		}
		if (cntSat == 1)
			$(".rankingtbl tbody").append("<tr><td colspan=2 style='font-size: 26px'><b>Samstag, 1. Februar</b></td></tr>");
		if (cntSun == 3)
			$(".rankingtbl tbody").append("<tr><td colspan=2 style='font-size: 26px'><br><b>Sonntag, 2. Februar</b></td></tr>");
		
		$(".rankingtbl tbody").append("<tr><td>" + entry.time + "</td><td>" + entry.name + "</td></tr>");
		
	});
	
	if ((active == null) && (data.length > 0)) {
		console.log("no active found, opening first cat");
		active = data[0];
	}
	
	// remove, just for debugging
	//active = data[1];
	
	if (active != null) {
		$(".schedulehdr").html(active.name + " - " + active.segment);

		$(".finishertbl thead tr").remove();
		$(".finishertbl tbody tr").remove();
		$(".startertbl tbody tr").remove();
		
		if (active.finisher.length > 0) {
			
			//$(".finishertbl tbody").append("<tr><td colspan='3' style='font-size: 26px'><b>Ergebnisse:</b></td>)");
			
			// header
			var html = "<tr>";
			active.finisherHeader.forEach(function(hdr) {
				html += "<th>" + hdr + "</th>";
			});
			html += "</tr>";
			$(".finishertbl thead").append(html);
			
			active.finisher.forEach(function(finisher) {
				var html = "<tr>";

				finisher.values.forEach(function(td) {
					html += "<td>" + td + "</td>";	
				});
				html += "</tr>";
				
				$(".finishertbl tbody").append(html);
			});
		}
		
		if (active.starter.length > 0) {
			var br = active.finisher.length > 0 ? "<br>" : "";
			$(".startertbl tbody").append("<tr><td colspan='3' style='font-size: 26px'><b>" + br + "Startreihenfolge:</b></td>)");
			active.starter.forEach(function(starter) {
				var html = "<tr>";

				if (starter.type == "einlaufgruppe") 
					html += "<td colspan='3'><b>" + starter.values[1] + "</b></td>";
				else { 
					starter.values.forEach(function(td) {
						html += "<td>" + td + "</td>";	
					});
				}				
				html += "</tr>";
				
				$(".startertbl tbody").append(html);
			});
		}
	}

	init = true;
	
	setTimeout(updateJson, 10000);
}

function handleErr(xhr, textStat, errthrwn) {
	console.log("error: " + textStat + " / " + errthrwn)
	$("#errormsg").html(textStat + " / " + errthrwn);
	
	if (errcnt++ == 5) {
		$(".schedulediv").hide();
		$(".complistdiv").hide();
		$(".errordiv").show();
	}
		
	setTimeout(updateJson, 1000);
	
}

function updateJson() {
	console.log("called updateJson");
	$.ajax({
		dataType: "json",
		url: "display.json?url=<%=url%>",
		success: jsonResponse,
		error: handleErr,
		timeout: 5000
	});
}

$(document).ready(function(){
	updateJson();
	$(".errordiv").hide();
});
</script>
</head>
<body>
	<div class="header">
		<table class="headertbl"><tr>
		<td><img src="res/ues.png"></td><td style="text-align: right"><img style="width: 170px" src="res/hpwm.png"></td>
		</tr></table>
	</div>
	<div class="schedulediv">
	<table width="100%">
		<tr><td class="rankinghdr">Zeitplan</td></tr>
		<tr><td>
			<table class="rankingtbl" style="">
				<colgroup>
					<col width=70>
					<col width=*>
				</colgroup>
				<thead></thead>
				<tbody></tbody>
			</table>
		</td></tr>
	</table>
	</div>
	
	<div class="complistdiv">
	<table width="100%">
		<tr><td class="schedulehdr" id="schedule"></td></tr>
		<tr><td align="center">
			<table class="finishertbl">
				<!-- colgroup>
					<col width=30>
					<col width=450>
					<col width=*>
				</colgroup -->
				<thead></thead>
				<tbody></tbody>
			</table>
			<table class="startertbl">
				<colgroup>
					<col width=30>
					<col width=450>
					<col width=*>
				</colgroup>
				<tbody></tbody>
			</table>
		</td></tr>
	</table>
	</div>
	
	<div class="errordiv">
		<img src="res/haydnpokal.jpg"><br>
		<br>
		warte auf Wettkampfsystem ..<br>&nbsp;
		<span id="errormsg" style="font-size: 10px; color: #878787"></span> 
	</div>
	
</body>
</html>
