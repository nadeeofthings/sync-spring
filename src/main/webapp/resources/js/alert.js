$('document').ready(function(){                 
	$('#alertDataTable').DataTable( {
			    ajax: {
			        url: contextPath+'/rest/getAllAlerts',
			        dataType: 'json',
			        dataSrc: function ( json ) {
			            for ( var i=0, ien=json.length ; i<ien ; i++ ) {
				            var options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric', hour:'numeric', minute:'numeric', second:'numeric' };
				            var newDate = new Date(json[i].timeStamp);
			                //json[i].dateTime= newDate.toLocaleDateString("en-US", options);
				            json[i].dateTime=moment(newDate).format("dddd, MMMM Do YYYY, h:mm:ss a");
				            	 if(json[i].level == 1){
									//Info
									json[i].level = "Info";
								 } else if(json[i].level == 2){
									//Warning
									json[i].level = "Warning";
								 }else if(json[i].level == 3){
									//Error
									json[i].level = "Error";
								 }else if(json[i].level == 4){
								 	//Success
									json[i].level = "Success";
								 }
			              }
			              return json;
			            },
					    error: function(jqXHR, exception) {
					        window.location.href = contextPath;
					    }
			    },
			    columns: [
		            { "data": "dateTime" },
		            { "data": "level" },
		            { "data": "message" },
		        ],
			    order: [[ 0, "desc" ]]
			} );

});