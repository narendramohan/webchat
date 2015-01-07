			var request = getXmlHttpRequestObject();
			var response = getXmlHttpRequestObject();
			var lastMessage = 0;
			var mTimer;
			

			function startChat() {
				document.getElementById('chatMessage').focus();
				getChatText();
			}		

			function getXmlHttpRequestObject() {				
				if (window.XMLHttpRequest) {					
					return new XMLHttpRequest();
				} else if(window.ActiveXObject) {					
					return new ActiveXObject("Microsoft.XMLHTTP");
				}
			}
			
			function getChatText() {	
				if (response.readyState == 4 || response.readyState == 0) {
					var toUser = document.getElementById('chatUser').value;
					response.open("GET", 'loadChat.jsp?chat=1&chatUser='+toUser+'&last=' + lastMessage, true);
					response.onreadystatechange = handleReceiveChat;
					response.send(null);
				}			
			}

			function sendChatText() {
				if(document.getElementById('chatMessage').value == '') {
					alert("You have not entered a message");
					return;
				}
				if (request.readyState == 4 || request.readyState == 0) {
					var toUser = document.getElementById('chatUser').value;
					request.open("POST", 'loadChat.jsp?chat=1&chatUser='+toUser+'&last=' + lastMessage, true);
					request.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
					request.onreadystatechange = handleSendChat; 
					var param = 'message=' + document.getElementById('chatMessage').value;									
					param += '&chat=1&chatUser='+toUser;
					request.send(param);
					document.getElementById('chatMessage').value = '';
				}							
			}
			
			function handleSendChat() {				
				clearInterval(mTimer);
				getChatText();
			}
			
			function handleReceiveChat() {							
				if (response.readyState == 4) {			
					var chat_div = document.getElementById('chat');					
					var xmldoc = response.responseXML;					
					var message_nodes = xmldoc.getElementsByTagName("message");					
					var n_messages = message_nodes.length;				
					for (i = 0; i < n_messages; i++) {						
						var user_node = message_nodes[i].getElementsByTagName("user");
						var text_node = message_nodes[i].getElementsByTagName("text");
						var time_node = message_nodes[i].getElementsByTagName("time");
						chat_div.innerHTML += '<font color=green>'+user_node[0].firstChild.nodeValue + '</font>&nbsp;';
						chat_div.innerHTML += '<font color=red class="chat_time">' + time_node[0].firstChild.nodeValue + '</font><br />';				
						chat_div.innerHTML += text_node[0].firstChild.nodeValue.replace(":)", "<img src='images/smile.png'/>").replace(":P", "<img src='images/wink.png'/>").replace(":BS", "<img src='images/bsmile.png'/>")+"<br>";
						chat_div.scrollTop = chat_div.scrollHeight;
						lastMessage = (message_nodes[i].getAttribute('id'));					
					}
					mTimer = setTimeout('getChatText();',1000); 
				}
			}

			function blockSubmit() {
				sendChatText();
				return false;
			}

			function resetChat() {
				if (request.readyState == 4 || request.readyState == 0) {
					var toUser = document.getElementById('chatUser').value;
					request.open("POST", 'loadChat.jsp?chat=1&chatUser='+toUser+'&last=' + lastMessage, true);
					request.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
					request.onreadystatechange = handleResetChat; 
					var param = 'action=reset&chatUser='+toUser;
					request.send(param);
					document.getElementById('chatMessage').value = '';
				}
			}

			function handleResetChat() {
				document.getElementById('chat').innerHTML = '';
				getChatText();
			}	
			function goBack(){
				window.history.back();
			}