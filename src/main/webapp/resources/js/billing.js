var elecLogtable, airconLogtable, billTriggeredBy;
var eFlatpickr,aFlatpickr;
$(document).ready(function(){
	var startdate = moment().subtract(1, "days").format("DD/MM/YYYY");
	//console.log(startdate);
	eFlatpickr=$("#EFR").flatpickr(
		 
		    {
		    	onOpen: [
					        function(selectedDates, dateStr, instance){
					        	var EFL = $('#EFL').val();
					        	var EOF = $('#EOF').val();
					            $.ajax({
										type: "GET",
										url: contextPath+"/rest/getDisabledDates?id="+EFL+"&unit="+EOF+"&ext=kWh",
										dataType: 'json',
								        success: function(data) {
								        	instance.set('disable',data);
								        },
										error: function(jqXHR, exception) {
											window.location.href = contextPath;
										}
					            });
					        }
					    ],
		    mode: "range",
		    conjunction: " :: ",
		    altInput: true,
		    altFormat: "F j, Y",
		    dateFormat: "d/m/Y",
		    maxDate:startdate,
			
		}

		);
	aFlatpickr=$("#AFR").flatpickr(
		 
		    {
		    	onOpen: [
					        function(selectedDates, dateStr, instance){
					        	var AFL = $('#AFL').val();
					        	var AOF = $('#AOF').val();
					            $.ajax({
										type: "GET",
										url: contextPath+"/rest/getDisabledDates?id="+AFL+"&unit="+AOF+"&ext=BTU",
										dataType: 'json',
								        success: function(data) {
								        	console.log(data);
								        	instance.set('disable',data);
								        },
										error: function(jqXHR, exception) {
											window.location.href = contextPath;
										}
					            });
					        }
					    ],
		    mode: "range",
		    conjunction: " :: ",
		    altInput: true,
		    altFormat: "F j, Y",
		    dateFormat: "d/m/Y",
		    maxDate:startdate,
		}

		);
	$('#EGEN').on('click', function(event) {
		billTriggeredBy = "EGEN";
		var error = false;
		if(!$('#EFR').val()){
			$('#EFR').addClass('is-invalid');
			error = true;
		}else{
			$('#EFR').removeClass('is-invalid');
		}
		
		if(!$('#EPP').val()){
			$('#EPP').addClass('is-invalid');
			error = true;
		}else{
			$('#EPP').removeClass('is-invalid');
		}
		
		var SD = $('#EFR').val();
			var parts = SD.split(" to ");
			if(parts.length<2){
				$("#EFRERROR").html("Please input a date range");
				$('#EFR').addClass('is-invalid');
				error = true;
			}else{
				$('#EFR').removeClass('is-invalid');
			}
			var dFrom = parts[0];
			var dTo = parts[1];
		
		
		if(error){
			return false;
		}else{
			

			var EFL = $('#EFL').val();
			var EFR = dFrom;
			var ERO = $('#ERO').val();
			var EPP = $('#EPP').val();
			var EDI = $('#EDI').val();
			var EOF = $('#EOF').val();
			var ETO = dTo;
			var ERP = $('#ERP').val();
			var EPE = $('#EPE').val();
			var EADJ = $('#EADJ').val();
			if(EADJ=="") EADJ = 0;
			var modal = $('#BillConfirmation');
			if(EFL=="Ground"){
				var floorText ="Floor: <b>"+EFL+"</b>";
				var officeText = "Meter: <b>"+EOF+"</b>";
			}else{
				var floorText ="Floor: <b>"+EFL+"</b>";
				var officeText = "Office: <b>"+EOF+"</b>";
			}
			var $div = $("<div>", {"class": "container"})
							.append($("<div>", {"class": "row"}).append($("<div>", {"class": "col-sm"})
									.append($("<p>", {"class": "text-left"}).html(floorText))
									.append($("<p>", {"class": "text-left"}).html("From: <b>"+EFR+"</b>"))
									.append($("<p>", {"class": "text-left"}).html("Peak Rate: <b>LKR "+number_format(ERP, 2, ".", "")+"</b>"))
									.append($("<p>", {"class": "text-left"}).html("Previously Paid: <b>LKR "+number_format(EPP, 2, ".", "")+"</b>"))
									.append($("<p>", {"class": "text-left"}).html("Discount: <b>"+number_format(EDI, 2, ".", "")+"%"+"</b>"))
								).append($("<div>", {"class": "col-sm"})
									.append($("<p>", {"class": "text-left"}).html(officeText))
									.append($("<p>", {"class": "text-left"}).html("To: <b>"+ETO+"</b>"))
									.append($("<p>", {"class": "text-left"}).html("Off Peak Rate: <b>LKR "+number_format(ERO, 2, ".", "")+"</b>"))
									.append($("<p>", {"class": "text-left"}).html("Penalty: <b>"+number_format(EPE, 2, ".", "")+"%"+"</b>"))
									.append($("<p>", {"class": "text-left"}).html("Adjustment: <b>LKR "+number_format(EADJ, 2, ".", "")+"</b>"))
								)).append($("<div>", {"class": "row"}).append($("<p>", {"class": "text-left font-italic text-danger"}).html("*Bill generation cannot be reversed. Please make sure the details are correct")));
							

			modal.find('.modal-body').empty().append($div);
			$('#BillConfirmation').modal('show');
			
			
		}
	
		});

	$('#AGEN').on('click', function(event) {
		billTriggeredBy = "AGEN";
		var error = false;
		if(!$('#AFR').val()){
			$('#AFR').addClass('is-invalid');
			error = true;
		}else{
			$('#AFR').removeClass('is-invalid');
		}
		
		
		if(!$('#APP').val()){
			$('#APP').addClass('is-invalid');
			error = true;
		}else{
			$('#APP').removeClass('is-invalid');
		}

		var SD = $('#AFR').val();
			var parts = SD.split(" to ");
			if(parts.length<2){
				$("#AFRERROR").html("Please input a date range");
				$('#AFR').addClass('is-invalid');
				error = true;
			}else{
				$('#AFR').removeClass('is-invalid');
			}
			var dFrom = parts[0];
			var dTo = parts[1];



		if(error){
			return false;
		}else{
			

			var AFL = $('#AFL').val();
			var AFR = dFrom;
			var ARO = $('#ARO').val();
			var APP = $('#APP').val();
			var ADI = $('#ADI').val();
			var AOF = $('#AOF').val();
			var ATO = dTo;
			var ARP = $('#ARP').val();
			var APE = $('#APE').val();
			var AADJ = $('#AADJ').val();
			if(AADJ=="") AADJ = 0;
			var modal = $('#BillConfirmation');
			if(AFL=="Ground"){
				var floorText ="Floor: <b>"+AFL+"</b>";
				var officeText = "Meter: <b>"+AOF+"</b>";
			}else{
				var floorText ="Floor: <b>"+AFL+"</b>";
				var officeText = "Office: <b>"+AOF+"</b>";
			}
			var $div = $("<div>", {"class": "container"})
							.append($("<div>", {"class": "row"}).append($("<div>", {"class": "col-sm"})
									.append($("<p>", {"class": "text-left"}).html(floorText))
									.append($("<p>", {"class": "text-left"}).html("From: <b>"+dFrom+"</b>"))
									.append($("<p>", {"class": "text-left"}).html("Peak Rate: <b>LKR "+number_format(ARP, 2, ".", "")+"</b>"))
									.append($("<p>", {"class": "text-left"}).html("Previously Paid: <b>LKR "+number_format(APP, 2, ".", "")+"</b>"))
									.append($("<p>", {"class": "text-left"}).html("Discount: <b>"+number_format(ADI, 2, ".", "")+"%"+"</b>"))
								).append($("<div>", {"class": "col-sm"})
									.append($("<p>", {"class": "text-left"}).html(officeText))
									.append($("<p>", {"class": "text-left"}).html("To: <b>"+dTo+"</b>"))
									.append($("<p>", {"class": "text-left"}).html("Off Peak Rate: <b>LKR "+number_format(ARO, 2, ".", "")+"</b>"))
									.append($("<p>", {"class": "text-left"}).html("Penalty: <b>"+number_format(APE, 2, ".", "")+"%"+"</b>"))
									.append($("<p>", {"class": "text-left"}).html("Adjustment: <b>LKR "+number_format(AADJ, 2, ".", "")+"</b>"))
								)).append($("<div>", {"class": "row"}).append($("<p>", {"class": "text-left font-italic text-danger"}).html("*Bill generation cannot be reversed. Please make sure the details are correct")));
							

			modal.find('.modal-body').empty().append($div);
			$('#BillConfirmation').modal('show');
			
		}
	
		});
	$('#confirmed').on('click', function(event) {
		if(billTriggeredBy =="EGEN"){
			var SD = $('#EFR').val();
			var parts = SD.split(" to ");
			var dFrom = parts[0];
			var dTo =  parts[1];

			var EFL = $('#EFL').val();
			var EFR = dFrom;
			var ERO = $('#ERO').val();
			var EPP = $('#EPP').val();
			var EDI = $('#EDI').val();
			var EOF = $('#EOF').val();
			var ETO = dTo;
			var ERP = $('#ERP').val();
			var EPE = $('#EPE').val();
			var EADJ = $('#EADJ').val();
			if(EADJ=="") EADJ = 0;
			$.ajax({
					type: "GET",
					url: contextPath+"/rest/generateElec?efl="+EFL+"&efr="+EFR+"&ero="+ERO+"&epp="+EPP+"&edi="+EDI+"&eof="+EOF+"&eto="+ETO+"&erp="+ERP+"&epe="+EPE+"&eadj="+EADJ,
					dataType: 'json',
			        complete: function(){
			        	elecLogtable.ajax.reload();
			        	$('#BillConfirmation').modal('hide');
			        	eFlatpickr.clear();
			        },
					error: function(jqXHR, exception) {
						window.location.href = contextPath;
					}
            	});

		}else if(billTriggeredBy == "AGEN"){
			var SD = $('#AFR').val();
			var parts = SD.split(" to ");
			var dFrom = parts[0];
			var dTo = parts[1];

			var AFL = $('#AFL').val();
			var AFR = dFrom;
			var ARO = $('#ARO').val();
			var APP = $('#APP').val();
			var ADI = $('#ADI').val();
			var AOF = $('#AOF').val();
			var ATO = dTo;
			var ARP = $('#ARP').val();
			var APE = $('#APE').val();
			var AADJ = $('#AADJ').val();
			if(AADJ=="") AADJ = 0;
			$.ajax({
					type: "GET",
					url: contextPath+"/rest/generateAC?afl="+AFL+"&afr="+AFR+"&aro="+ARO+"&app="+APP+"&adi="+ADI+"&aof="+AOF+"&ato="+ATO+"&arp="+ARP+"&ape="+APE+"&aadj="+AADJ,
					dataType: 'json',
			        complete: function(){
			        	airconLogtable.ajax.reload();
			        	$('#BillConfirmation').modal('hide');
			        	aFlatpickr.clear();
			        },
					error: function(jqXHR, exception) {
						window.location.href = contextPath;
					}
            });

		}else{

		}
	});
	
	$("#EFL").change(function () {
        if($('#EFL').val()=="Ground"){
        	var gOptions = {"KWH 1": "1"};
        	$('#EOFL').html('Meter');
        	var $el = $("#EOF");
        	$el.empty(); // remove old options
        	eFlatpickr.clear();
        	$.each(gOptions, function(key,value) {
        	  $el.append($("<option></option>")
        	     .attr("value", value).text(key));
        	});
        	
		}else{
			var oOptions = {"One": "1",
      			  "Two": "2",
    			  "Three": "3",
    			  "Four": "4"
    			};
			$('#EOFL').html('Office');
			var $el = $("#EOF");
        	$el.empty(); // remove old options
        	eFlatpickr.clear();
        	$.each(oOptions, function(key,value) {
        	  $el.append($("<option></option>")
        	     .attr("value", value).text(key));
        	});
		}
    });
	$("#EOF").change(function () {
		eFlatpickr.clear();
	});
	$("#AFL").change(function () {
        if($('#AFL').val()=="Ground"){
        	var gOptions = {"BTU 1": "2",
    			  "BTU 2": "3",
    			  "BTU 3": "4"};
        	$('#AOFL').html('Meter');
        	var $el = $("#AOF");
        	$el.empty(); // remove old options
        	aFlatpickr.clear();
        	$.each(gOptions, function(key,value) {
        	  $el.append($("<option></option>")
        	     .attr("value", value).text(key));
        	});
        	
		}else{
			var oOptions = {"One": "1",
      			  "Two": "2",
    			  "Three": "3",
    			  "Four": "4"
    			};
			$('#AOFL').html('Office');
			var $el = $("#AOF");
        	$el.empty(); // remove old options
        	aFlatpickr.clear();
        	$.each(oOptions, function(key,value) {
        	  $el.append($("<option></option>")
        	     .attr("value", value).text(key));
        	});
		}
    });
    $("#AOF").change(function () {
		aFlatpickr.clear();
	});

	$.ajax({
		type: "GET",
		url: contextPath+'/rest/getAllBillingProperties?ext=kWh',
		dataType: 'json',
		success: function(data) {
			//console.log(data);
			var $el = $("#ERP");//Peak rate
			$el.empty(); // remove old options
			$.each(data[5], function(key,value){
				//console.log(value.propertyValue);
				$el.append($("<option></option>").attr("value", value.propertyValue).text(number_format(value.propertyValue,2,".",",")));
			});
			var $el = $("#EDI");//Discount
			$el.empty(); // remove old options
			$.each(data[8], function(key,value){
				//console.log(value.propertyValue);
				$el.append($("<option></option>").attr("value", value.propertyValue).text(number_format(value.propertyValue,1,".",",")+"%"));
			});
			var $el = $("#ERO");//Offpeak rate
			$el.empty(); // remove old options
			$.each(data[6], function(key,value){
				//console.log(value.propertyValue);
				$el.append($("<option></option>").attr("value", value.propertyValue).text(number_format(value.propertyValue,2,".",",")));
			});
			var $el = $("#EPE");//Offpeak rate
			$el.empty(); // remove old options
			$.each(data[7], function(key,value){
				//console.log(value.propertyValue);
				$el.append($("<option></option>").attr("value", value.propertyValue).text(number_format(value.propertyValue,1,".",",")+"%"));
			});
                       
        },
		error: function(jqXHR, exception) {
			window.location.href = contextPath;
		}
            });
	$.ajax({
		type: "GET",
		url: contextPath+'/rest/getAllBillingProperties?ext=BTU',
		dataType: 'json',
		success: function(data) {
			//console.log(data);
			var $el = $("#ARP");//Peak rate
			$el.empty(); // remove old options
			$.each(data[5], function(key,value){
				//console.log(value.propertyValue);
				$el.append($("<option></option>").attr("value", value.propertyValue).text(number_format(value.propertyValue,2,".",",")));
			});
			var $el = $("#ADI");//Discount
			$el.empty(); // remove old options
			$.each(data[8], function(key,value){
				//console.log(value.propertyValue);
				$el.append($("<option></option>").attr("value", value.propertyValue).text(number_format(value.propertyValue,1,".",",")+"%"));
			});
			var $el = $("#ARO");//Offpeak rate
			$el.empty(); // remove old options
			$.each(data[6], function(key,value){
				//console.log(value.propertyValue);
				$el.append($("<option></option>").attr("value", value.propertyValue).text(number_format(value.propertyValue,2,".",",")));
			});
			var $el = $("#APE");//Offpeak rate
			$el.empty(); // remove old options
			$.each(data[7], function(key,value){
				//console.log(value.propertyValue);
				$el.append($("<option></option>").attr("value", value.propertyValue).text(number_format(value.propertyValue,1,".",",")+"%"));
			});
                       
        },
		error: function(jqXHR, exception) {
			window.location.href = contextPath;
		}
            });
	loadTables();

});

function loadTables() {
	  $.fn.dataTable.moment( "DD/MM/YYYY" );
		 elecLogtable = $('#elecLog').DataTable( {
		    ajax: {
		        url: contextPath+'/rest/getAllBillingLogs?ext=kWh',
		        dataType: 'json',
		        dataSrc: function ( json ) {
		            for ( var i=0, ien=json.length ; i<ien ; i++ ) {
			           var options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric', hour:'numeric', minute:'numeric', second:'numeric'};
			            var newDate1 = new Date(json[i].timestamp);
			            var newDate2 = new Date(json[i].fromDate);
			            var newDate3 = new Date(json[i].toDate);
		                //json[i].timeStamp= newDate.toLocaleDateString("en-US", options);
			            json[i].timestamp=moment(newDate1).format("DD/MM/YYYY");
			            json[i].fromDate=moment(newDate2).format("DD/MM/YYYY");
			            json[i].toDate=moment(newDate3).format("DD/MM/YYYY");


		              }
		              return json;
		            }
		    },

		    columns: [
	            { "data": "timestamp" },
	            { "data": "billId" },
	            { "data": "id" },
	            { "data": "unit" },
	            { "data": "fromDate" },
	            { "data": "toDate" },
	            {
  					data: null,
  					render: function ( data, type, row ) {
  						//console.log(data.billId);
  						if(data.id=='Ground')
  							return '<button type="button" class="btn btn-dark btn-sm" onclick="downloadBill(\''+data.billId+'\')">Download</button>';
  						else
  							return '<button type="button" class="btn btn-dark btn-sm" onclick="downloadBill(\''+data.billId+'\')">Download</button>';
  					}
				}
	        ],
		    order: [[ 0, "desc" ]]
		} );
	  airconLogtable = $('#airconLog').DataTable( {
		    ajax: {
		        url: contextPath+'/rest/getAllBillingLogs?ext=BTU',
		        dataType: 'json',
		        dataSrc:  function ( json ) {
		            for ( var i=0, ien=json.length ; i<ien ; i++ ) {
			            var options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric', hour:'numeric', minute:'numeric', second:'numeric'};
			            var newDate1 = new Date(json[i].timestamp);
			            var newDate2 = new Date(json[i].fromDate);
			            var newDate3 = new Date(json[i].toDate);
		                //json[i].timeStamp= newDate.toLocaleDateString("en-US", options);
			            json[i].timestamp=moment(newDate1).format("DD/MM/YYYY");
			            json[i].fromDate=moment(newDate2).format("DD/MM/YYYY");
			            json[i].toDate=moment(newDate3).format("DD/MM/YYYY");
		              }
		              return json;
		            }
		    },
		    columns: [
	            { "data": "timestamp" },
	            { "data": "billId" },
	            { "data": "id" },
	            { "data": "unit" },
	            { "data": "fromDate" },
	            { "data": "toDate" },
	            {
  					data: null,
  					render: function ( data, type, row ) {
  						//console.log(data);
  						if(data.id=='Ground')
  							return '<button type="button" class="btn btn-dark btn-sm" onclick="downloadBill(\''+data.billId+'\')">Download</button>';
  						else
  							return '<button type="button" class="btn btn-dark btn-sm" onclick="downloadBill(\''+data.billId+'\')">Download</button>';
  					}
				}
	        ],
		    order: [[ 0, "desc" ]]
		} );
	  //Dirty default set to two weeks
	 // getElecData(id,unit,$("#limitElec")[0].value,$("#fromdatepickelec")[0].value);
	 // getAirconData(id,unit,$("#limitAir")[0].value,$("#fromdatepickair")[0].value);
	}

	function downloadBill(billId) {
			window.open(contextPath+"/billing/downloadBill?billId="+billId);
	}

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
