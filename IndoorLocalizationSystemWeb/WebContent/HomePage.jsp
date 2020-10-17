<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>	Home Page - Indoor Localization System	</title>
		<link rel="stylesheet" type="text/css" href="style.css" />
		<script src="jquery-1.11.3.js"></script>
		<script  src="jquery-1.6.2.js"></script>
		<script src="BlinkLogic.js"></script>
		<script>
		$(document).ready(function(){
			var callAjax = function() {
				$.ajax({
					type:"GET",	
					url:"http://www.domain_name.net:80/api/observations?sensor=mySensor&time=latest",
					contentType:'application/json',
					headers: {			
						"x-api-key":"auth_api_key",
					},	
					success:
						function(data){	
						var dataJSParsed = JSON.stringify(data);
						var dataParsed = JSON.parse(dataJSParsed);
						flag = dataParsed.observations[0].record[0].output[0].value;
						x = dataParsed.observations[0].record[0].output[1].value;
						y = dataParsed.observations[0].record[0].output[2].value;
						//alert(JSON.stringify(data));
						//alert(flag+","+x+","+y);
						start(flag,x,y);
					},
					error: function(jqXHR, textStatus, errorThrown) 
					{
						document.writeln(textStatus);
						document.writeln(errorThrown);
						console.log("status code : "+textStatus);
						console.log("error thrown : "+errorThrown);
					} 
				});
			}
			setInterval(callAjax,10000);
		});
		</script>
	</head>
	<body>
		<!-- <div style="background-image: url('images/img1.jpg');width:100%;height:100%;background-size: cover;z-index: -9999;">
		</div>-->
		<div id="header">
			<span style="font-family: Arial;font-size: 35px;font-weight: bolder;color: black;">	Indoor Localization System	</span>
		</div>
		
		<div id="content">
		<br/>
		<div id="region">
			<!-- <input type="button" id="button" value="Start"/> -->
			<div id="blink" style="display:block;"></div>
		</div>
		<div id="msg" style="float: left;">
				Message
			</div>
		</div>
		
		<div id="footer">
			<span style="font-family: Arial;font-size:15px;font-weight: bold;">	Copyright @TVM23 Group-2	</span>
		</div> 
	</body>
</html>
