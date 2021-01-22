$(document).ready(function(){
	//TODO CODE
	$.fn.dataTable.moment( "dddd, MMMM Do YYYY, h:mm:ss a" );
	 activityTable = $('#activityDataTable').DataTable( {
		    ajax: {
		        url: contextPath+'/rest/getActivities',
		        dataType: 'json',
		        dataSrc: function ( json ) {
		           
		              return json;
		            }
		    },
		    dom: 'Bfrtip',
		    buttons: [{
                extend: 'excelHtml5',
                title: 'Activity Data Export '+moment().format("DD_MM_YYYY_HH_mm_ss")
            }, {
                extend: 'pdfHtml5',
                title: 'Activity Data Export '+moment().format("DD_MM_YYYY_HH_mm_ss")
            }, 'print'
       			 ],

		    columns: [
	            { data: null,
  					render: function ( data, type, row ) {
  						return data.user.firstname+" "+data.user.lastname;
  					}
  				 },
	            {  data: null,
  					render: function ( data, type, row ) {
  						return moment(data.timeStamp).format("dddd, MMMM Do YYYY, h:mm:ss a");
  					}
	             },
	            {
  					data: null,
  					render: function ( data, type, row ) {

	  						if(data.user.role.name=='ROLE_SUPERADMIN')
	  							return "Super Admin";
	  						else if (data.user.role.name=='ROLE_ADMIN')
	  							return "Admin";
	  						else if (data.user.role.name=='ROLE_USER')
	  							return "User";

  					},
  					"width": "1%"
				},
				{ "data":"message"}
	        ],
		    order: [[ 1, "desc" ]]
		} );
	 activityTable.buttons().container().find( "button" ).addClass('btn btn-outline-dark').removeClass('dt-button buttons-print');
});
