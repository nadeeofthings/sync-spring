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
	
	
	$('#EDR').on('click', function(event) {
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
		if(((d2-d1)/86400000)>7) {
			$('#ETOERROR').html('Please keep the duration less than 7 days');
			$('#ETO').addClass('is-invalid');
			error = true;
		}
		
		if(error){
			return false;
		}else{
			var EFR = $('#EFR').val();
			var ETO = $('#ETO').val();
		window.open(contextPath+"/report/electricalDailyReport?efr="+EFR+"&eto="+ETO);
		}
	
		});
	
	$('#ADR').on('click', function(event) {
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
		if(((d2-d1)/86400000)>7) {
			$('#ATOERROR').html('Please keep the duration less than 7 days');
			$('#ATO').addClass('is-invalid');
			error = true;
		}
		
		if(error){
			return false;
		}else{
			var AFR = $('#AFR').val();
			var ATO = $('#ATO').val();
		window.open(contextPath+"/report/airconDailyReport?efr="+AFR+"&eto="+ATO);
		}
	
		});
	
	$('#ETS').on('click', function(event) {
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
		if(((d2-d1)/86400000)>93) {
			$('#ETOERROR').html('Please keep the duration less than 3 months');
			$('#ETO').addClass('is-invalid');
			error = true;
		}
		
		if(error){
			return false;
		}else{
			var EFL = $('#EFL').val();
			var EFR = $('#EFR').val();
			var EOF = $('#EOF').val();
			var ETO = $('#ETO').val();
		window.open(contextPath+"/report/electricalTenantSummary?efl="+EFL+"&efr="+EFR+"&eof="+EOF+"&eto="+ETO);
		}
	
		});
	
	$('#ATS').on('click', function(event) {
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
		
		if(((d2-d1)/86400000)>93) {
			$('#ATOERROR').html('Please keep the duration less than 3 months');
			$('#ATO').addClass('is-invalid');
			error = true;
		}
		
		if(error){
			return false;
		}else{
			var AFL = $('#AFL').val();
			var AFR = $('#AFR').val();
			var AOF = $('#AOF').val();
			var ATO = $('#ATO').val();
		window.open(contextPath+"/report/airconTenantSummary?afl="+AFL+"&afr="+AFR+"&aof="+AOF+"&ato="+ATO);
		}
	
		});
	
	$('#FSR').on('click', function(event) {
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
		if(((d2-d1)/86400000)>31) {
			$('#ETOERROR').html('Please keep the duration less than 31 days');
			$('#ETO').addClass('is-invalid');
			error = true;
		}
		
		if(error){
			return false;
		}else{
			var EFR = $('#EFR').val();
			var ETO = $('#ETO').val();
		window.open(contextPath+"/report/facilitySummaryReport?efr="+EFR+"&eto="+ETO);
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