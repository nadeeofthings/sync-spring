function number_format(number, decimals, dec_point, thousands_sep) {
	  // *     example: number_format(1234.56, 2, ',', ' ');
	  // *     return: '1 234,56'
	  number = (number + '').replace(',', '').replace(' ', '');
	  var n = !isFinite(+number) ? 0 : +number,
	    prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
	    sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
	    dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
	    s = '',
	    toFixedFix = function(n, prec) {
	      var k = Math.pow(10, prec);
	      return '' + Math.round(n * k) / k;
	    };
	  // Fix for IE parseFloat(0.55).toFixed(0) = 0;
	  s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
	  if (s[0].length > 3) {
	    s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
	  }
	  if ((s[1] || '').length < prec) {
	    s[1] = s[1] || '';
	    s[1] += new Array(prec - s[1].length + 1).join('0');
	  }
	  return s.join(dec);
	}

$(document).ready(function() {
	$("#fromdatepickelec").datepicker({
		format: "dd/mm/yyyy",
		todayBtn: "linked",
	    clearBtn: true,
	    orientation: "bottom auto",
	    autoclose: true,
	    todayHighlight: true
		});
	$("#fromdatepickair").datepicker({
		format: "dd/mm/yyyy",
		todayBtn: "linked",
	    clearBtn: true,
	    orientation: "bottom auto",
	    autoclose: true,
	    todayHighlight: true
		});
	 $("#fromdatepickelec,#fromdatepickair").datepicker('setDate', new Date());
});

$(document).ready(function() {
	var limitElec = 14;
	var limitAir = 14;
	loadValues();
	loadTables();
});
 


$("#limitElec").change(function () {
    getElecData(id,unit,this.value,$("#fromdatepickelec")[0].value);
});

$("#fromdatepickelec").change(function () {
	getElecData(id,unit,$("#limitElec")[0].value,this.value);
});

$("#limitAir").change(function () {
	getAirconData(id,unit,this.value,$("#fromdatepickair")[0].value);
});

$("#fromdatepickair").change(function () {
	getAirconData(id,unit,$("#limitAir")[0].value,this.value);
});


function loadTables() {
	  $.fn.dataTable.moment( "dddd, MMMM Do YYYY, h:mm:ss a" );
	  $('#elecDataTable').DataTable( {
		    ajax: {
		        url: 'http://localhost:8080/tebbiq/rest/byId?ext=kWh&id='+id+'&unit='+unit+'',
		        dataSrc: function ( json ) {
		            for ( var i=0, ien=json.length ; i<ien ; i++ ) {
			            json[i].meter= 'Meter '+json[i].meter;
			            json[i].value=number_format(json[i].value,2,".",",");
			            var options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric', hour:'numeric', minute:'numeric', second:'numeric' };
			            var newDate = new Date(json[i].timeStamp);
		                //json[i].dateTime= newDate.toLocaleDateString("en-US", options);
			            json[i].dateTime=moment(newDate).format("dddd, MMMM Do YYYY, h:mm:ss a");
		              }
		              return json;
		            }
		    },
		    columns: [
	            { "data": "meter" },
	            { "data": "dateTime" },
	            { "data": "value" },
	        ],
		    order: [[ 1, "desc" ]]
		} );
	  $('#airconDataTable').DataTable( {
		    ajax: {
		        url: 'http://localhost:8080/tebbiq/rest/byId?ext=BTU&id='+id+'&unit='+unit+'',
		        dataSrc:  function ( json ) {
		            for ( var i=0, ien=json.length ; i<ien ; i++ ) {
			            json[i].meter= 'Meter '+(json[i].meter-1);
			            json[i].value=number_format(json[i].value,2,".",",");
			            var options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric', hour:'numeric', minute:'numeric', second:'numeric'};
			            var newDate = new Date(json[i].timeStamp);
		                //json[i].timeStamp= newDate.toLocaleDateString("en-US", options);
			            json[i].dateTime=moment(newDate).format("dddd, MMMM Do YYYY, h:mm:ss a");
		              }
		              return json;
		            }
		    },
		    columns: [
	            { "data": "meter" },
	            { "data": "dateTime" },
	            { "data": "value" }
	        ],
		    order: [[ 1, "desc" ]]
		} );
	  //Dirty default set to two weeks
	 // getElecData(id,unit,$("#limitElec")[0].value,$("#fromdatepickelec")[0].value);
	 // getAirconData(id,unit,$("#limitAir")[0].value,$("#fromdatepickair")[0].value);
	}
  
  
	function startTime() {
		var days = [ 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday',
				'Friday', 'Saturday' ];
		var months = [ 'January', 'February', 'March', 'April', 'May', 'June',
				'July', 'August', 'September', 'October', 'November',
				'December' ];

		var today = new Date();
		var day = days[today.getDay()];
		var month = months[today.getMonth()];
		var year = today.getFullYear();
		var h = today.getHours();
		var m = today.getMinutes();
		var s = today.getSeconds();
		m = checkTime(m);
		s = checkTime(s);
		document.getElementById('dateTime').innerHTML = day + ", " + month + " "
				+ year + " - " + h + ":" + m + ":" + s;
		var t = setTimeout(startTime, 500);
	}
	function checkTime(i) {
		if (i < 10) {
			i = "0" + i
		}
		; // add zero in front of numbers < 10
		return i;
	}

function loadValues() {
                $.ajax({
                	type: "GET",
                    url: 'http://localhost:8080/tebbiq/rest/reading?id='+id+'&unit='+unit+'',
                    dataType: 'json',
                    success: function(data) {
                       // $("#refresh").html(data);
                       if(id == "Ground"){
                       document.getElementById("energyxxxxx").innerHTML = number_format(data[0].value,2,".",",")+" "+data[0].ext;
                       document.getElementById("btuxxxxx1").innerHTML = number_format(data[1].value,2,".",",")+" "+data[1].ext;
                       document.getElementById("btuxxxxx2").innerHTML = number_format(data[2].value,2,".",",")+" "+data[2].ext;
                       document.getElementById("btuxxxxx3").innerHTML = number_format(data[3].value,2,".",",")+" "+data[3].ext;
                       console.log(data[0].id);
                       }else{
                       document.getElementById("energyxxxxx").innerHTML = number_format(data[0].value,2,".",",")+" "+data[0].ext;
                       document.getElementById("btuxxxxx1").innerHTML = number_format(data[1].value,2,".",",")+" "+data[1].ext;
                           }
                       
                    },
                    complete: function() {

                        //alert("refresh");
                        // 
                        // Schedule the next request when the current one's complete
                        setTimeout(loadValues, 5000);
                    }
                });
            }