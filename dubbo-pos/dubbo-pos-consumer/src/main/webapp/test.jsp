<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%  
    String path = request.getContextPath();  
    String socPath="ws://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
<!DOCTYPE html>  
<html>  
<head>  
<title>HTML5 WebSocket测试</title>  
</head>  
<body>  
    <div>  
        <input type="button" value="Start" onclick="start()" />  
    </div>  
    <div id="messages"></div>  
    <script type="text/javascript">  
    	
    	function alertmsg(msg)) {  
            console.error(msg);
            alert(msg);
        }
        
    	 var wsuri = "<%=socPath%>/mywebsocket"; 
    	 alertmsg(wsuri);
         var webSocket = null;  
        if ('WebSocket' in window)  {
        	webSocket = new WebSocket(wsuri); 
        	alertmsg("WebSocket in window");
        }
        else if ('MozWebSocket' in window)  {
        	webSocket = new MozWebSocket(wsuri);  
        	alertmsg("MozWebSocket in window");
        }
         else  {
         	alertmsg("not support WebSocket!"); 
         	//alert("not support WebSocket");
         }    
                
       	webSocket.onerror = function(event) {  
       		alertmsg("not support WebSocket!"); 
            //alert(event.data);  
        };  
        //与WebSocket建立连接  
        webSocket.onopen = function(event) {  
            //document.getElementById('messages').innerHTML = '与服务器端建立连接';  
            alertmsg('与服务器端建立连接'); 
            //alert('与服务器端建立连接');
        };  
        //处理服务器返回的信息  
        webSocket.onmessage = function(event) {  
            //document.getElementById('messages').innerHTML += '<br />'+ event.data; 
            alertmsg(event.data);  
            if(event.data == "HasNewRecord")
            {
            	alert(event.data);
            }
            alert(event.data);
        };  
        function start() {  
            //向服务器发送请求  
            webSocket.send('我是jCuckoo');  
        }  
    </script>  
</body>  
</html>  