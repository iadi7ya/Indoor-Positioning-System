function start(flag, x, y){
	var blinker = document.getElementById("blink");
	var y1 = 7 - y;
	var msg = document.getElementById("msg");
	
	if(flag.toString() == "true"){
		blinker.style.display = "block";
		
		var str1 = Math.round(x).toString() ;
		var str2 = Math.round(y1).toString() ;
		
		blinker.style['margin-left'] = str1+"cm";
		//alert(Math.round(x).toString() );
		blinker.style['margin-top'] = str2+"cm";
		//alert(Math.round(y).toString() );
		msg.innerHTML = "ALERT!!!<br/> X: " + str1 + "<br/>Y: " + str2;
		
	} else {
		blinker.style.display = "none";
	}
}