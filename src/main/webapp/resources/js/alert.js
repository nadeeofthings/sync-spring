var alertDataTable;
$('document').ready(function(){                 
	$.fn.dataTable.moment("dddd, MMMM Do YYYY, h:mm:ss a" );
	alertDataTable = $('#alertDataTable').DataTable( {
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
		            {
  					data: null,
  					render: function ( data, type, row ) {
	  						if(data.status == 1){
	  							return '<button type="button" class="btn btn-danger btn-sm btn-block" onClick="acknowledge('+data.id+')">Acknowledge</button>';
	  						}else{
	  							return '<button type="button" class="btn btn-success btn-sm btn-block" >Acknowledged</button>';
	  						}
	  					}
				}
		        ],
			    order: [[ 0, "desc" ]]
			} );

});

function acknowledge(id) {
$.ajax({
		type: "GET",
		url: contextPath+'/rest/acknowledgeAlert?id='+id,
		dataType: 'json',
		success: function(json) {
			refreshAlertCount();
			alertDataTable.ajax.reload();
		},
		error: function(jqXHR, exception) {
			window.location.href = contextPath;
		}
            });

}
function refreshAlertCount(){
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
}