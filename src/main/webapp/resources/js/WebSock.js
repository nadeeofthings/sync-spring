function alertDOMGenerate(message, level, timestamp) {
	 var options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric', hour:'numeric', minute:'numeric', second:'numeric' };
	 var newDate = new Date(timestamp);
	 //console.log(timestamp);
	 var iconClass,faClass;
	 if(level == 1){
		//Info
		iconClass = "icon-circle bg-info";
		faClass = "fas fa-thumbs-up fa-2x text-white";
	 } else if(level == 2){
		//Warning
		iconClass = "icon-circle bg-warning";
		faClass = "fas fa-exclamation fa-2x text-white";
	 }else if(level == 3){
		//Error
		iconClass = "icon-circle bg-danger";
		faClass = "fas fa-ban fa-2x text-white";
	 }else if(level == 4){
	 	//Success
		iconClass = "icon-circle bg-success";
		faClass = "fas fa-check fa-2x text-white";
	 }
	 	            /*<div class="mr-3">
					<div class="icon-circle bg-success">
						<i class="fas fa-thumbs-up text-white"></i>
					</div>
				</div>
				<div>
					<div class="small text-gray-500">June 12, 2019</div>
					<span class="font-weight-bold">Recovered from CEB loss! GEN 1 stopped.</span>
				</div>
	            */
     var tree = document.createDocumentFragment();
		          maindiv = document.createElement('a');
		          maindiv.className = "dropdown-item d-flex align-items-center";
		          
			          newdiv1 = document.createElement('div');
			          newdiv1.id = alertCount;
			          newdiv1.classList.add("mr-3");
				          newdiv1_1 = document.createElement('div');
				          newdiv1_1.className = iconClass;
					          newdiv1_1_1 = document.createElement('i');
					          newdiv1_1_1.className = faClass;
				          newdiv1_1.appendChild(newdiv1_1_1);
			          newdiv1.appendChild(newdiv1_1);
			          
			          newdiv2 = document.createElement('div');
				          newdiv2_1 = document.createElement('div');
				          newdiv2_1.className ="small text-gray-500";
				          newdiv2_1.innerHTML = moment(newDate).format("dddd, MMMM Do YYYY, h:mm:ss a");
				          newdiv2_2 = document.createElement('span');
				          newdiv2_2.className = "font-weight-normal text-dark";
				          newdiv2_2.innerHTML = message;
			          newdiv2.appendChild(newdiv2_1);
			          newdiv2.appendChild(newdiv2_2);
	
				  maindiv.appendChild(newdiv1);
				  maindiv.appendChild(newdiv2);  
			  tree.appendChild(maindiv);
	  return tree;
	}
let stompClient;
$('document').ready(function(){
	    
	$('#alertsDropdown').on('show.bs.dropdown', function () {
			$.ajax({
                	type: "GET",
                	//dataType: 'json',
                    url: contextPath+'/rest/resetAlertCount',
                    success: function() {
						document.getElementById("alertCount").style.display= 'none';
						document.getElementById("alertCount").innerHTML = 0;
                    },
					error: function(jqXHR, exception) {
						window.location.href = contextPath;
					}
                }); 
		});
	$.ajax({
                	type: "GET",
                    url: contextPath+'/rest/alertCount',
                    dataType: 'json',
                    success: function(data) {
                       // $("#refresh").html(data);
                       if(data == 0){
                       	document.getElementById("alertCount").style.display= 'none';
                       	document.getElementById("alertCount").innerHTML = 0;
                       } else {
                       	document.getElementById("alertCount").innerHTML = data;
                       }
                       
                    },
                    complete: function() {

                        //alert("refresh");
                        // 
                        // Schedule the next request when the current one's complete
                        //setTimeout(loadValues, 5000);
                    },
					error: function(jqXHR, exception) {
						window.location.href = contextPath;
					}
                });
    $.ajax({
                	type: "GET",
                    url: contextPath+'/rest/getTop3Alerts',
                    dataType: 'json',
                    success: function(data) {
                      for ( var i=0, ien=data.length ; i<ien ; i++ ) {
                      		var alert1 = document.getElementById("alertElements");
				
					          alert1.insertBefore(alertDOMGenerate(data[i].message, data[i].level, data[i].timeStamp),alert1.firstChild)
					          
					          while (alert1.childElementCount > 3) {
							    alert1.removeChild(alert1.lastChild);
							  }
                      }
                       
                    },
                    complete: function() {

                        //alert("refresh");
                        // 
                        // Schedule the next request when the current one's complete
                        //setTimeout(loadValues, 5000);
                    },
					error: function(jqXHR, exception) {
						window.location.href = contextPath;
					}
                });                
	if (!stompClient) {
	      const socket = new SockJS(contextPath+"/notifications");
	      stompClient = Stomp.over(socket);
	      stompClient.connect({}, function () {
			
	        stompClient.subscribe('/user/notification/item', function (response) {
	          var data = JSON. parse(response.body);
	          var alertCount = parseInt(document.getElementById("alertCount").innerHTML)
	          if(!isNaN(alertCount)){
	          	document.getElementById("alertCount").style.display= '';
	          	document.getElementById("alertCount").innerHTML = alertCount+1;
	          }else{
	          	alertCount = 1;
	          	document.getElementById("alertCount").innerHTML = 1;
	          	document.getElementById("alertCount").style.display= '';
	          }
	          
	          if(data.level == 1){
		          iziToast.info({
		          position: 'topRight', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter, center
		          transitionIn: 'bounceInLeft',
		          title: 'FYI', 
		          message: data.message });
	          } else if(data.level == 2){
		          iziToast.warning({
		          position: 'topRight', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter, center
		          transitionIn: 'bounceInLeft',
		          title: 'Warning', 
		          message: data.message });
	          }else if(data.level == 3){
		          iziToast.error({
		          position: 'topRight', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter, center
		          transitionIn: 'bounceInLeft',
		          title: 'Error', 
		          message: data.message });
	          }else if(data.level == 4){
		          iziToast.success({
		          position: 'topRight', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter, center
		          transitionIn: 'bounceInLeft',
		          title: 'Success!', 
		          message: data.message });
	          }
	          
	          var alert1 = document.getElementById("alertElements");
	          alert1.insertBefore(alertDOMGenerate(data.message, data.level, data.timeStamp),alert1.firstChild)
	          
	          //console.log(data);
	          while (alert1.childElementCount > 3) {
			    alert1.removeChild(alert1.lastChild);
			  }
			 
	          
	        });
			stompClient.send("/alert/start", {});
	        console.info('connected!')
	      });
	    }

	    
});