$(document).ready(function(){
	$("#dp1").datepicker({
		format: "dd/mm/yyyy",
		todayBtn: "linked",
	    clearBtn: true,
	    orientation: "bottom auto",
	    autoclose: true
		});
	$("#dp2").datepicker({
		format: "dd/mm/yyyy",
	    todayBtn: "linked",
	    clearBtn: true,
	    orientation: "bottom auto",
	    autoclose: true
		});
	$("#dp3").datepicker({
		format: "dd/mm/yyyy",
	    todayBtn: "linked",
	    clearBtn: true,
	    orientation: "bottom auto",
	    autoclose: true
		});
	$("#dp4").datepicker({
		format: "dd/mm/yyyy",
	    todayBtn: "linked",
	    clearBtn: true,
	    orientation: "bottom auto",
	    autoclose: true
		});
	
	$('#EGEN').on('click', function(event) {
		var error = false;
		if(!$('#EFR').val()){
			$('#EFR').addClass('is-invalid');
			error = true;
		}else{
			$('#EFR').removeClass('is-invalid');
		}
		
		if(!$('#ETO').val()){
			$('#ETO').addClass('is-invalid');
			error = true;
		}else{
			$('#ETO').removeClass('is-invalid');
		}
		
		if(!$('#EPP').val()){
			$('#EPP').addClass('is-invalid');
			error = true;
		}else{
			$('#EPP').removeClass('is-invalid');
		}
		
		var SD = $('#EFR').val();
		var parts = SD.split("/");
		var d1 = new Date(Number(parts[2]), Number(parts[1]) - 1, Number(parts[0]));
		var ED = $('#ETO').val();
		var parts = ED.split("/");
		var d2 = new Date(Number(parts[2]), Number(parts[1]) - 1, Number(parts[0]));
		
		if(d1>=d2) {
			$('#ETOERROR').html('Please select a date later than the start date');
			$('#ETO').addClass('is-invalid');
			error = true;
		}
		
		if(error){
			return false;
		}else{
			var EFL = $('#EFL').val();
			var EFR = $('#EFR').val();
			var ERO = $('#ERO').val();
			var EPP = $('#EPP').val();
			var EDI = $('#EDI').val();
			var EOF = $('#EOF').val();
			var ETO = $('#ETO').val();
			var ERP = $('#ERP').val();
			var EPE = $('#EPE').val();
		window.open(contextPath+"/billing/generateElec?efl="+EFL+"&efr="+EFR+"&ero="+ERO+"&epp="+EPP+"&edi="+EDI+"&eof="+EOF+"&eto="+ETO+"&erp="+ERP+"&epe="+EPE);
		}
	
		});
	
	$('#AGEN').on('click', function(event) {
		var error = false;
		if(!$('#AFR').val()){
			$('#AFR').addClass('is-invalid');
			error = true;
		}else{
			$('#AFR').removeClass('is-invalid');
		}
		
		if(!$('#ATO').val()){
			$('#ATO').addClass('is-invalid');
			error = true;
		}else{
			$('#ATO').removeClass('is-invalid');
		}
		
		if(!$('#APP').val()){
			$('#APP').addClass('is-invalid');
			error = true;
		}else{
			$('#APP').removeClass('is-invalid');
		}
		
		var SD = $('#AFR').val();
		var parts = SD.split("/");
		var d1 = new Date(Number(parts[2]), Number(parts[1]) - 1, Number(parts[0]));
		var ED = $('#ATO').val();
		var parts = ED.split("/");
		var d2 = new Date(Number(parts[2]), Number(parts[1]) - 1, Number(parts[0]));
		
		if(d1>=d2) {
			$('#ATOERROR').html('Please select a date later than the start date');
			$('#ATO').addClass('is-invalid');
			error = true;
		}
		
		if(error){
			return false;
		}else{
			var AFL = $('#AFL').val();
			var AFR = $('#AFR').val();
			var ARO = $('#ARO').val();
			var APP = $('#APP').val();
			var ADI = $('#ADI').val();
			var AOF = $('#AOF').val();
			var ATO = $('#ATO').val();
			var ARP = $('#ARP').val();
			var APE = $('#APE').val();
			window.open(contextPath+"/billing/generateAC?afl="+AFL+"&afr="+AFR+"&aro="+ARO+"&app="+APP+"&adi="+ADI+"&aof="+AOF+"&ato="+ATO+"&arp="+ARP+"&ape="+APE);
		}
	
		});
	
	$("#EFL").change(function () {
        if($('#EFL').val()=="Ground"){
        	var gOptions = {"KWH 1": "1"};
        	$('#EOFL').html('Meter');
        	var $el = $("#EOF");
        	$el.empty(); // remove old options
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
        	$.each(oOptions, function(key,value) {
        	  $el.append($("<option></option>")
        	     .attr("value", value).text(key));
        	});
		}
    });
	
	$("#AFL").change(function () {
        if($('#AFL').val()=="Ground"){
        	var gOptions = {"BTU 1": "2",
    			  "BTU 2": "3",
    			  "BTU 3": "4"};
        	$('#AOFL').html('Meter');
        	var $el = $("#AOF");
        	$el.empty(); // remove old options
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
        	$.each(oOptions, function(key,value) {
        	  $el.append($("<option></option>")
        	     .attr("value", value).text(key));
        	});
		}
    });
	
});