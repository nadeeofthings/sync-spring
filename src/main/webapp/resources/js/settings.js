var userTable, customerTable, networkTable;
var customerList= "";

$(document).ready(function(){
	//TODO CODE
	console.log( );
	if($('#name').hasClass('is-invalid') ||
		 $('#address').hasClass('is-invalid') ||
		 $('#email').hasClass('is-invalid') ||
		 $('#business_email').hasClass('is-invalid') ||
		 $('#phone').hasClass('is-invalid') ||
		 $('#business_phone').hasClass('is-invalid')){
		$('#customerTab').tab('show')
	}
    redirect_tab();
	loadTables();
	$('#userTable tbody').on('click', 'tr', function () {
        var data = userTable.row( this ).data();
        loadUserInfo(data.id);
        $('#UserInfo').modal('show');
    });

    $('#customerTable tbody').on('change', 'select', function () {
    	var data = customerTable.row( $(this).parent(2)).data();
         customerId = this.value;
         var ext = data.ext.slice(0, 3);
         $.ajax({
			type: "GET",
			url: contextPath+'/rest/assignCustomer?id='+data.id+'&unit='+data.unit+'&meter='+data.meter+'&ext='+ext+'&customerId='+customerId,
			dataType: 'json',
			success: function(data) {},
	        complete: function(){
				        	customerTable.ajax.reload();
				        },
			error: function(jqXHR, exception) {
							window.location.href = contextPath;
						}
	            
	            });
    });

    $('#promote').change(function() 
	  {		var $role = "";
	  		if(document.getElementById("promote").checked == true){
	  			//console.log("Promted to admin"+ $("#email").val());
	  			$role = "ADMIN"
	  		}else{
	  			//console.log("Deomted to user"+ $("#email").val());
	  			$role = "USER"
	  		}
	  		var $uname = $("#Memail").text();
	  		//console.log($role+" "+$uname);
	  		$.ajax({
			type: "GET",
			url: contextPath+'/rest/rank?uname='+$uname+'&role='+$role,
			dataType: 'json',
			success: function(data) {
				var firstName = data.firstname;
	 	        var lastName = data.lastname;
	 	        $("#Mname").text(firstName+" "+lastName); 	        
	 	        $("#Mfirstname").text(firstName+" "+lastName);
	 	        $("#Mdepartment").text(data.department);
	 	        $("#Memail").text(data.username);
	 	        $("#Mphone").text(data.phone);
	 	        if(data.role.name=='ROLE_SUPERADMIN'){
  				$("#roleH").text("Super Administrator");
	  			}
	  				
	  			else if (data.role.name=='ROLE_ADMIN' && document.getElementById("promote") !=null){
	  				$("#roleH").text("Administrator");
	  				document.getElementById("promote").checked =true;
	  			}

	  			else if (data.role.name=='ROLE_USER' && document.getElementById("promote") !=null){
	  				$("#roleH").text("User");
	  				document.getElementById("promote").checked =false;
	  			}
	  			if(data.role.name=='ROLE_SUPERADMIN'){
	  				$("#roleH").text("Super Administrator");
	  			}
	  				
	  			else if (data.role.name=='ROLE_ADMIN'){
	  				$("#roleH").text("Administrator");
	  			}

	  			else if (data.role.name=='ROLE_USER'){
	  				$("#roleH").text("User");
	  			}
	  			if(data.enabled==true){
	  				document.getElementById("enable").checked =true;
	  			}else{
	  				document.getElementById("enable").checked =false;
	  			}

	 	        var intials = firstName.charAt(0) + lastName.charAt(0);
	 	        var hue=((firstName.charCodeAt(0)+lastName.charCodeAt(0)-130)/114)*400;

	 	        $('#initials').empty().addClass( "avatar-circle-64").css("background-color", "hsl("+hue+", 50%, 50%)")
	 	        								.append($("<span>", {"class": "initials-64"}).text(intials));
	                      
	        },
	        complete: function(){
				        	userTable.ajax.reload();
				        },
			error: function(jqXHR, exception) {
							window.location.href = contextPath;
						}
	            
	            });
	  });

	  $('#enable').change(function() 
	  {		var $flag = "";
	  		if(document.getElementById("enable").checked == true){
	  			//console.log("Enabled"+ $("#email").val());
	  			$flag = "ENABLE"
	  		}else{
	  			//console.log("Enabled"+ $("#email").val());
	  			$flag = "DISABLE"
	  		}
	  		var $uname = $("#Memail").text();
	  		//console.log($flag+" "+$uname);
	  		$.ajax({
			type: "GET",
			url: contextPath+'/rest/enable?uname='+$uname+'&flag='+$flag,
			dataType: 'json',
			success: function(data) {
				var firstName = data.firstname;
	 	        var lastName = data.lastname;
	 	        $("#Mname").text(firstName+" "+lastName); 	        
	 	        $("#Mfirstname").text(firstName+" "+lastName);
	 	        $("#Mdepartment").text(data.department);
	 	        $("#Memail").text(data.username);
	 	        $("#Mphone").text(data.phone);
	 	        if(data.role.name=='ROLE_SUPERADMIN'){
  				$("#roleH").text("Super Administrator");
	  			}
	  				
	  			else if (data.role.name=='ROLE_ADMIN' && document.getElementById("promote") !=null){
	  				$("#roleH").text("Administrator");
	  				document.getElementById("promote").checked =true;
	  			}

	  			else if (data.role.name=='ROLE_USER' && document.getElementById("promote") !=null){
	  				$("#roleH").text("User");
	  				document.getElementById("promote").checked =false;
	  			}
	  			if(data.role.name=='ROLE_SUPERADMIN'){
	  				$("#roleH").text("Super Administrator");
	  			}
	  				
	  			else if (data.role.name=='ROLE_ADMIN'){
	  				$("#roleH").text("Administrator");
	  			}

	  			else if (data.role.name=='ROLE_USER'){
	  				$("#roleH").text("User");
	  			}
	  			if(data.enabled==true){
	  				document.getElementById("enable").checked =true;
	  			}else{
	  				document.getElementById("enable").checked =false;
	  			}

	 	        var intials = firstName.charAt(0) + lastName.charAt(0);
	 	        var hue=((firstName.charCodeAt(0)+lastName.charCodeAt(0)-130)/114)*400;

	 	        $('#initials').empty().addClass( "avatar-circle-64").css("background-color", "hsl("+hue+", 50%, 50%)")
	 	        								.append($("<span>", {"class": "initials-64"}).text(intials));
	                      
	        },
	        complete: function(){
				        	userTable.ajax.reload();
				        },
			error: function(jqXHR, exception) {
							window.location.href = contextPath;
						}
	            
	            });
	  });  
	  $('#elecPeak').tagsinput({
		  maxTags: 5,
		  maxChars: 6,
		  append:" LKR"
		});
	  $('#elecOffPeak').tagsinput({
		  maxTags: 5,
		  maxChars: 6,
		  append:" LKR"
		});
	  $('#airconPeak').tagsinput({
	  	  tagClass: function(item) {
		    return 'tagjs tagjs-primary';
		  },
		  maxTags: 5,
		  maxChars: 6,
		  append:" LKR"
		});
	  $('#airconOffPeak').tagsinput({
	  	  tagClass: function(item) {
		    return 'tagjs tagjs-primary';
		  },
		  maxTags: 5,
		  maxChars: 6,
		  append:" LKR"
		});

	  $('#elecDisc').tagsinput({
	   	tagClass: function(item) {
		    return 'tagjs tagjs-info';
		  },
		  maxTags: 5,
		  maxChars: 5,
		  append:" %"
		});
	  $('#elecPen').tagsinput({
	  	tagClass: function(item) {
		    return 'tagjs tagjs-danger';
		  },
		  maxTags: 5,
		  maxChars: 5,
		  append:" %"
		});
	  $('#airconDisc').tagsinput({
	  	  tagClass: function(item) {
		    return 'tagjs tagjs-info';
		  },
		  maxTags: 5,
		  maxChars: 5,
		  append:" %"
		});
	  $('#airconPen').tagsinput({
	  	  tagClass: function(item) {
		    return 'tagjs tagjs-danger';
		  },
		  maxTags: 5,
		  maxChars: 5,
		  append:" %"
		});

	  $.ajax({
		type: "GET",
		url: contextPath+'/rest/getAllBillingProperties?ext=kWh',
		dataType: 'json',
		success: function(data) {

			elptag = $('#elecPeak');
            $.each(data[5], function(key,value){
		      elptag.tagsinput('add', value.propertyValue);  	 
		    });
		    eloptag = $('#elecOffPeak');
            $.each(data[6], function(key,value){
		      eloptag.tagsinput('add', value.propertyValue);  	 
		    });
		    elopetag = $('#elecPen');
            $.each(data[7], function(key,value){
		      elopetag.tagsinput('add', value.propertyValue);  	 
		    });
		    elodtag = $('#elecDisc');
            $.each(data[8], function(key,value){
            	//console.log(value.propertyValue);
		      elodtag.tagsinput('add', value.propertyValue);  	 
		    });
        },
		error: function(jqXHR, exception) {
			window.location.href = contextPath;
		}
	      });

	  //load_billPropsMK1();
	  
	  $.ajax({
		type: "GET",
		url: contextPath+'/rest/getAllBillingProperties?ext=BTU',
		dataType: 'json',
		success: function(data) {
			var obj = data[0];
		    $("#TAndC").val(obj[0].propertyValue);
		    var obj = data[1];
		    $("#EmergencyContact").val(obj[0].propertyValue);
		    var obj = data[2];
		    $("#BillingInquiries").val(obj[0].propertyValue);
		    var obj = data[3];
		    $("#NBTax").val(obj[0].propertyValue);
		    var obj = data[4];
		    $("#VATax").val(obj[0].propertyValue);
 			var obj = data[9];
		    $("#DueDaysPeriod").val(obj[0].propertyValue);
 			var obj = data[10];
		    $("#ServiceCharge").val(obj[0].propertyValue);
			acptag = $('#airconPeak');
            $.each(data[5], function(key,value){
		      acptag.tagsinput('add', value.propertyValue);  	 
		    });
		    acoptag = $('#airconOffPeak');
            $.each(data[6], function(key,value){
		      acoptag.tagsinput('add', value.propertyValue);  	 
		    });
		    acopetag = $('#airconPen');
            $.each(data[7], function(key,value){
		      acopetag.tagsinput('add', value.propertyValue);  	 
		    });
		    acdtag = $('#airconDisc');
            $.each(data[8], function(key,value){
		      acdtag.tagsinput('add', value.propertyValue);  	 
		    });
        },
		error: function(jqXHR, exception) {
			window.location.href = contextPath;
		}
	      });


	  $("#newPassword").on("click", function(){
	  	isValid=true;
	  		if( $("#NewPassword").val().length < 8){
		   		$("#NewPassword").addClass( "is-invalid" );
		   		$("#NewPassword").parent().find(".error").replaceWith($("<div>", {"class": "invalid-feedback"}).text(""));
		   		isValid=false;
		   	}else{

		   		$("#NewPassword").removeClass( "is-invalid" );
		   		$("#NewPassword").parent().find(".error").replaceWith($("<div>", {"class": "error"}));
		   	}
	  		if( isValid && !$("#NewPassword").val() == $("#ConfNewPassword").val()){
	  			$("#ConfNewPassword").addClass( "is-invalid" );
		   		$("#ConfNewPassword").parent().find(".error").replaceWith($("<div>", {"class": "invalid-feedback"}).text("Passwords don't match"));
		   		isValid=false;
	  		}else{
		   		$("#ConfNewPassword").removeClass( "is-invalid" );
		   		$("#ConfNewPassword").parent().find(".error").replaceWith($("<div>", {"class": "error"}));
		   	}

		   	if(isValid){
		   		var obj = {"password":$("#NewPassword").val(),"username":$("#Memail").html()};
				var istringa = JSON.stringify(obj);
		   		$.ajax({
					type: "POST",
					url: contextPath+'/rest/resetPassword',
					data: istringa,
					success: function(data) {
						iziToast.success({
				          position: 'topRight', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter, center
				          transitionIn: 'bounceInLeft',
				          title: 'Success', 
				          message: "Password reset successful" });
						 $("#NewPassword").val("");
		    			 $("#ConfNewPassword").val("");
					    
			        },
					error: function(jqXHR, exception) {
						//window.location.href = contextPath;
					}
				      });
		   	}



	  });

	  //SYSTEM CONFIG
	  load_sys_config();
	  $("#sysConfig").on("click", function(){
		   //Code: Action (like ajax...)
		   var isValid = true;
		   if( !$("#peakStart").val().trim().match(/^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/)){
		   		$("#peakStart").addClass( "is-invalid" );
		   		$("#peakStart").parent().find(".error").replaceWith($("<div>", {"class": "invalid-feedback"}).text("Please enter a valid time in 24HR format"));
		   		isValid=false;
		   	}else{
		   		$("#peakStart").removeClass( "is-invalid" );
		   		$("#peakStart").parent().find(".error").replaceWith($("<div>", {"class": "error"}));
		   	}
		   	if( !$("#offPeakStart").val().trim().match(/^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/)){
		   		$("#offPeakStart").addClass( "is-invalid" );
		   		$("#offPeakStart").parent().find(".error").replaceWith($("<div>", {"class": "invalid-feedback"}).text("Please enter a valid time in 24HR format"));
		   		isValid=false
		   	}else{
		   		$("#offPeakStart").removeClass( "is-invalid" );
		   		$("#offPeakStart").parent().find(".error").replaceWith($("<div>", {"class": "error"}));
		   	}	

		   	if(isValid){
		   		var obj = {"PeakEnd":$("#offPeakStart").val().trim(), "PeakStart":$("#peakStart").val().trim()};
				var istringa = JSON.stringify(obj);
				//console.log(istringa);
		   		$.ajax({
					type: "POST",
					url: contextPath+'/rest/setSystemConfig',
					data: istringa,
					success: function(data) {
						iziToast.success({
				          position: 'topRight', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter, center
				          transitionIn: 'bounceInLeft',
				          title: 'Success', 
				          message: "System configuration successfully updated" });
						 $("#peakStart").val(data[2].configValue);
		    			 $("#offPeakStart").val(data[1].configValue);
					    
			        },
					error: function(jqXHR, exception) {
						window.location.href = contextPath;
					}
				      });
		   	}
		   
		 });
	  $("#sysConfig_RST").on("click", function(){
		   load_sys_config();
		   
		 });

	  //BILL PROPS MK1
	  load_billPropsMK1();
	  $("#billPropsMK1").on("click", function(){
		   //Code: Action (like ajax...)
		   var isValid = true;
		   if( !$("#ServiceCharge").val().trim().match(/^\d+\.\d{0,2}$/)){
		   		$("#ServiceCharge").addClass( "is-invalid" );
		   		$("#ServiceCharge").parent().find(".error").replaceWith($("<div>", {"class": "invalid-feedback"}).text("Please enter a valid number with 2 decimals"));
		   		isValid=false;
		   	}else{
		   		$("#ServiceCharge").removeClass( "is-invalid" );
		   		$("#ServiceCharge").parent().find(".error").replaceWith($("<div>", {"class": "error"}));
		   	}
		   	if( !$("#DueDaysPeriod").val().trim().match(/^[0-9]*$/)){
		   		$("#DueDaysPeriod").addClass( "is-invalid" );
		   		$("#DueDaysPeriod").parent().find(".error").replaceWith($("<div>", {"class": "invalid-feedback"}).text("Please enter a valid number"));
		   		isValid=false
		   	}else{
		   		$("#DueDaysPeriod").removeClass( "is-invalid" );
		   		$("#DueDaysPeriod").parent().find(".error").replaceWith($("<div>", {"class": "error"}));
		   	}	
		   	if( !$("#NBTax").val().trim().match(/^\d+\.\d{0,2}$/)){
		   		$("#NBTax").addClass( "is-invalid" );
		   		$("#NBTax").parent().find(".error").replaceWith($("<div>", {"class": "invalid-feedback"}).text("Please enter a valid number with 2 decimals"));
		   		isValid=false
		   	}else{
		   		$("#NBTax").removeClass( "is-invalid" );
		   		$("#NBTax").parent().find(".error").replaceWith($("<div>", {"class": "error"}));
		   	}	
		   	if( !$("#VATax").val().trim().match(/^\d+\.\d{0,2}$/)){
		   		$("#VATax").addClass( "is-invalid" );
		   		$("#VATax").parent().find(".error").replaceWith($("<div>", {"class": "invalid-feedback"}).text("Please enter a valid number with 2 decimals"));
		   		isValid=false
		   	}else{
		   		$("#VATax").removeClass( "is-invalid" );
		   		$("#VATax").parent().find(".error").replaceWith($("<div>", {"class": "error"}));
		   	}	
		   	if( !$("#EmergencyContact").val().replace(/ /g,'').match(/^\d{10}$/)){
		   		$("#EmergencyContact").addClass( "is-invalid" );
		   		$("#EmergencyContact").parent().find(".error").replaceWith($("<div>", {"class": "invalid-feedback"}).text("Please enter a valid number"));
		   		isValid=false
		   	}else{
		   		$("#EmergencyContact").removeClass( "is-invalid" );
		   		$("#EmergencyContact").parent().find(".error").replaceWith($("<div>", {"class": "error"}));
		   	}
		   	if( !$("#BillingInquiries").val().replace(/ /g,'').match(/^\d{10}$/)){
		   		$("#BillingInquiries").addClass( "is-invalid" );
		   		$("#BillingInquiries").parent().find(".error").replaceWith($("<div>", {"class": "invalid-feedback"}).text("Please enter a valid number"));
		   		isValid=false
		   	}else{
		   		$("#BillingInquiries").removeClass( "is-invalid" );
		   		$("#BillingInquiries").parent().find(".error").replaceWith($("<div>", {"class": "error"}));
		   	}
		   	if( $("#TAndC").val().length === 0){
		   		$("#TAndC").addClass( "is-invalid" );
		   		$("#TAndC").parent().find(".error").replaceWith($("<div>", {"class": "invalid-feedback"}).text("This is required"));
		   		isValid=false
		   	}else{
		   		$("#TAndC").removeClass( "is-invalid" );
		   		$("#TAndC").parent().find(".error").replaceWith($("<div>", {"class": "error"}));
		   	}
		   	if(isValid){
		   		$("#ServiceCharge").val()
		   		$("#DueDaysPeriod").val()
		   		$("#NBTax").val()
		   		$("#VATax").val()
		   		$("#EmergencyContact").val()
		   		$("#BillingInquiries").val()
		   		$("#TAndC").val()
		   		var obj = {"ServiceCharge":number_format($("#ServiceCharge").val().trim(), 2, ".", ""),
		   				   "DueDaysPeriod":$("#DueDaysPeriod").val().trim(),
		   				   "NBTax":number_format($("#NBTax").val().trim(), 2, ".", ""),
		   				   "VATax":number_format($("#VATax").val().trim(), 2, ".", ""),
		   				   "EmergencyContact":$("#EmergencyContact").val().replace(/ /g,''),
		   				   "BillingInquiries":$("#BillingInquiries").val().replace(/ /g,''),
		   				   "TAndC":$("#TAndC").val().trim()
		   				};
				var istringa = JSON.stringify(obj);
				//console.log(istringa);
		   		$.ajax({
					type: "POST",
					url: contextPath+'/rest/setBillPropsMK1',
					data: istringa,
					success: function(data) {
						iziToast.success({
				          position: 'topRight', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter, center
				          transitionIn: 'bounceInLeft',
				          title: 'Success', 
				          message: "Billing properties successfully updated" });
					    load_billPropsMK1();
			        },
					error: function(jqXHR, exception) {
						window.location.href = contextPath;
					}
				      });
		   	}
		   
		 });
	  $("#billPropsMK1_RST").on("click", function(){
		   load_billPropsMK1();
		 });

	  $("#elecRates").on("click", function(){
	  	
	  	$.ajax({
					type: "POST",
					url: contextPath+'/rest/setElecRatesPeak',
					data: JSON.stringify($("#elecPeak").tagsinput('items')),
					success: function(data) {
					  	 $.ajax({
								type: "POST",
								url: contextPath+'/rest/setElecRatesOffPeak',
								data: JSON.stringify($("#elecOffPeak").tagsinput('items')),
								success: function(data) {
									iziToast.success({
							          position: 'topRight', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter, center
							          transitionIn: 'bounceInLeft',
							          title: 'Success', 
							          message: "Electricity rates successfully updated" });
								    load_elecRates();
						        },
								error: function(jqXHR, exception) {
									window.location.href = contextPath;
								}
							      });
			        },
					error: function(jqXHR, exception) {
						window.location.href = contextPath;
					}
				      });
	  	
	  });

	  $("#elecRates_RST").on("click", function(){
		  load_elecRates();
		 });

	  $("#airconRates").on("click", function(){
	  	$.ajax({
					type: "POST",
					url: contextPath+'/rest/setAirconRatesPeak',
					data: JSON.stringify($("#airconPeak").tagsinput('items')),
					success: function(data) {
					    $.ajax({
								type: "POST",
								url: contextPath+'/rest/setAirconRatesOffPeak',
								data: JSON.stringify($("#airconOffPeak").tagsinput('items')),
								success: function(data) {
									iziToast.success({
							          position: 'topRight', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter, center
							          transitionIn: 'bounceInLeft',
							          title: 'Success', 
							          message: "Air conditioning rates successfully updated" });
								    load_airconRates();
						        },
								error: function(jqXHR, exception) {
									window.location.href = contextPath;
								}
							      });
			        },
					error: function(jqXHR, exception) {
						window.location.href = contextPath;
					}
				      });
	  });

	  $("#airconRates_RST").on("click", function(){
		  load_airconRates();
		 });

	  $("#discounts").on("click", function(){
	  	$.ajax({
					type: "POST",
					url: contextPath+'/rest/setElecDiscounts',
					data: JSON.stringify($("#elecDisc").tagsinput('items')),
					success: function(data) {
					    $.ajax({
								type: "POST",
								url: contextPath+'/rest/setAirconDiscounts',
								data: JSON.stringify($("#airconDisc").tagsinput('items')),
								success: function(data) {
									iziToast.success({
							          position: 'topRight', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter, center
							          transitionIn: 'bounceInLeft',
							          title: 'Success', 
							          message: "Discounts successfully updated" });
								    load_discounts();
						        },
								error: function(jqXHR, exception) {
									window.location.href = contextPath;
								}
							      });
			        },
					error: function(jqXHR, exception) {
						window.location.href = contextPath;
					}
				      });
	  });

	  $("#discounts_RST").on("click", function(){
		  load_discounts();
		 });

	  $("#peanlties").on("click", function(){
	  	$.ajax({
					type: "POST",
					url: contextPath+'/rest/setElecPenalties',
					data: JSON.stringify($("#elecPen").tagsinput('items')),
					success: function(data) {
					    $.ajax({
								type: "POST",
								url: contextPath+'/rest/setAirconPenalties',
								data: JSON.stringify($("#airconPen").tagsinput('items')),
								success: function(data) {
									iziToast.success({
							          position: 'topRight', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter, center
							          transitionIn: 'bounceInLeft',
							          title: 'Success', 
							          message: "Penalties successfully updated" });
								    load_peanlties();
						        },
								error: function(jqXHR, exception) {
									window.location.href = contextPath;
								}
							      });
			        },
					error: function(jqXHR, exception) {
						window.location.href = contextPath;
					}
				      });
	  });

	  $("#peanlties_RST").on("click", function(){
		  load_peanlties();
		 });

	  //END TODO CODE
});

function loadTables() {
		 userTable = $('#userTable').DataTable( {
		    ajax: {
		        url: contextPath+'/rest/getUsers',
		        dataType: 'json',
		        dataSrc: function ( json ) {
		            for ( var i=0, ien=json.length ; i<ien ; i++ ) {
			           json[i].firstname = json[i].firstname+" "+ json[i].lastname;
		              }
		              return json;
		            }
		    },

		    columns: [
	            { data: null,
  					render: function ( data, type, row ) {
  						return '<a href="#">'+data.firstname+'</a>'
  					}
  				 },
	            { "data": "department" },
	            { "data": "username" },
	            {
  					data: null,
  					render: function ( data, type, row ) {

	  					if(data.enabled==true){
	  						if(data.role.name=='ROLE_SUPERADMIN')
	  							return '<button type="button" class="btn btn-danger btn-sm btn-block">Super Admin</button>';
	  						else if (data.role.name=='ROLE_ADMIN')
	  							return '<button type="button" class="btn btn-warning btn-sm btn-block">Admin</button>';
	  						else if (data.role.name=='ROLE_USER')
	  							return '<button type="button" class="btn btn-success btn-sm btn-block">User</button>';
	  					}else{
	  						if(data.role.name=='ROLE_SUPERADMIN')
	  							return '<button type="button" class="btn btn-secondary btn-sm btn-block">Super Admin</button>';
	  						else if (data.role.name=='ROLE_ADMIN')
	  							return '<button type="button" class="btn btn-secondary btn-sm btn-block">Admin</button>';
	  						else if (data.role.name=='ROLE_USER')
	  							return '<button type="button" class="btn btn-secondary btn-sm btn-block">User</button>';
	  					}
  					}
				}
	        ],
		    order: [[ 0, "asc" ]]
		} );

		customerTable = $('#customerTable').DataTable( {

		    ajax: {
		        url: contextPath+'/rest/getAllConfigs',
		        dataType: 'json',
		        dataSrc: function ( json ) {

		            for ( var i=0, ien=json.length ; i<ien ; i++ ) {
			           json[i].ext = json[i].ext+" "+ json[i].meter;
		              }
		              return json;
		            }
		    },

		    columns: [
		    	{ "data": "no",
		    		"width": "1%"
		    	},
	            { "data": "id",
		    		"width": "1%"
		    	},
	            { "data": "unit",
		    		"width": "1%"
		    	},
	            { "data": "ext" ,
		    		"width": "1%"
		    	},
	            {
  					data: null,
  					render: function ( data, type, row ) {
	  					return '<select class="form-control slimSelect"></select>';
  					}
				}
	        ],
		    order: [[ 0, "asc" ]]
		} );

		customerTable.on( 'draw', function () {
			$.ajax({
		type: "GET",
		url: contextPath+'/rest/getAllCustomers',
		dataType: 'json',
		success: function(json) {
			$('#customerTable tbody').find('tr').each(function (i, el) {
			        var data = customerTable.row(this).data();
			        var $select = $(this).find('select');
			        $select.empty(); // remove old options
		        	$.each(json, function(key, item) {
		        	 if(data.customer.id === item.id){
		        	 	$select.append($("<option></option>")
		        	     .attr("value", item.id).attr("selected","selected").text(item.name));
		        	 }else{
		        	 	$select.append($("<option></option>")
		        	     .attr("value", item.id).text(item.name));
		        	 }
		        	});
			    });
			},
		error: function(jqXHR, exception) {
			window.location.href = contextPath;
		}
            });
	});

	networkTable = $('#networkTable').DataTable( {

		    ajax: {
		        url: contextPath+'/rest/getAllConfigs',
		        dataType: 'json',
		        dataSrc: function ( json ) {
		              return json;
		            }
		    },

		    columns: [
		    	{ data: null,
  					render: function ( data, type, row ) {
	  					return data.id+" Floor";
  					},
		    	},
	            { data: null,
  					render: function ( data, type, row ) {
	  					return "Office "+data.unit;
  					}
		    	},
	            { "data": "meter",
		    		"width": "1%"
		    	},
	            { "data": "ext" ,
		    		"width": "1%"
		    	},
	            { "data": "address" ,
		    		"width": "1%"
				}
	        ],
		    order: [[ 4, "asc" ]]
		} );
}

function loadUserInfo(id){
	$.ajax({
		type: "GET",
		url: contextPath+'/rest/getUser?id='+id,
		dataType: 'json',
		success: function(data) {
			var firstName = data.firstname;
 	        var lastName = data.lastname;
 	        $("#Mname").text(firstName+" "+lastName); 	        
 	        $("#Mfirstname").text(firstName+" "+lastName);
 	        $("#Mdepartment").text(data.department);
 	        $("#Memail").text(data.username);
 	        $("#Mphone").text(data.phone);
 	        if(data.role.name=='ROLE_SUPERADMIN'){
  				$("#roleH").text("Super Administrator");
  			}
  				
  			else if (data.role.name=='ROLE_ADMIN' && document.getElementById("promote") !=null){
  				$("#roleH").text("Administrator");
  				document.getElementById("promote").checked =true;
  			}

  			else if (data.role.name=='ROLE_USER' && document.getElementById("promote") !=null){
  				$("#roleH").text("User");
  				document.getElementById("promote").checked =false;
  			}
  			if(data.role.name=='ROLE_SUPERADMIN'){
  				$("#roleH").text("Super Administrator");
  			}
  				
  			else if (data.role.name=='ROLE_ADMIN'){
  				$("#roleH").text("Administrator");
  			}

  			else if (data.role.name=='ROLE_USER'){
  				$("#roleH").text("User");
  			}
  			if(data.enabled==true){
  				document.getElementById("enable").checked =true;
  			}else{
  				document.getElementById("enable").checked =false;
  			}

 	        var intials = firstName.charAt(0) + lastName.charAt(0);
 	        var hue=((firstName.charCodeAt(0)+lastName.charCodeAt(0)-130)/114)*400;

 	        $('#initials').empty().addClass( "avatar-circle-64").css("background-color", "hsl("+hue+", 50%, 50%)")
 	        								.append($("<span>", {"class": "initials-64"}).text(intials));
                      
        },
			error: function(jqXHR, exception) {
							window.location.href = contextPath;
						}
	            
            });
};

function load_peanlties(){
	$.ajax({
		type: "GET",
		url: contextPath+'/rest/getAllBillingProperties?ext=kWh',
		dataType: 'json',
		success: function(data) {
			elopetag = $('#elecPen');
            $.each(data[7], function(key,value){
		      elopetag.tagsinput('add', value.propertyValue);  	 
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
			acopetag = $('#airconPen');
            $.each(data[7], function(key,value){
		      acopetag.tagsinput('add', value.propertyValue);  	 
		    });
		    },
		error: function(jqXHR, exception) {
			window.location.href = contextPath;
		}
	      });
}

function load_discounts(){
	$.ajax({
		type: "GET",
		url: contextPath+'/rest/getAllBillingProperties?ext=kWh',
		dataType: 'json',
		success: function(data) {
			elodtag = $('#elecDisc');
            $.each(data[8], function(key,value){
            	//console.log(value.propertyValue);
		      elodtag.tagsinput('add', value.propertyValue);  	 
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
			acdtag = $('#airconDisc');
            $.each(data[8], function(key,value){
		      acdtag.tagsinput('add', value.propertyValue);  	 
		    });
		    },
		error: function(jqXHR, exception) {
			window.location.href = contextPath;
		}
	      });
}
function load_airconRates(){
 $.ajax({
		type: "GET",
		url: contextPath+'/rest/getAllBillingProperties?ext=BTU',
		dataType: 'json',
		success: function(data) {
			acptag = $('#airconPeak');
            $.each(data[5], function(key,value){
		      acptag.tagsinput('add', value.propertyValue);  	 
		    });
		    acoptag = $('#airconOffPeak');
            $.each(data[6], function(key,value){
		      acoptag.tagsinput('add', value.propertyValue);  	 
		    });
		    },
		error: function(jqXHR, exception) {
			window.location.href = contextPath;
		}
	      });
}


function load_elecRates(){
 $.ajax({
		type: "GET",
		url: contextPath+'/rest/getAllBillingProperties?ext=kWh',
		dataType: 'json',
		success: function(data) {
			elptag = $('#elecPeak');
			elptag.tagsinput('removeAll');
            $.each(data[5], function(key,value){
		      elptag.tagsinput('add', value.propertyValue);  	 
		    });
		    eloptag = $('#elecOffPeak');
		    eloptag.tagsinput('removeAll');
            $.each(data[6], function(key,value){
		      eloptag.tagsinput('add', value.propertyValue);  	 
		    });
        },
		error: function(jqXHR, exception) {
			window.location.href = contextPath;
		}
	      });
}

function load_sys_config(){

	$.ajax({
		type: "GET",
		url: contextPath+'/rest/getSystemConfigs',
		dataType: 'json',
		success: function(data) {
		    $("#peakStart").val(data[2].configValue);
		    $("#offPeakStart").val(data[1].configValue);
		    
        },
		error: function(jqXHR, exception) {
			window.location.href = contextPath;
		}
	      });
}

function load_billPropsMK1(){
	$.ajax({
		type: "GET",
		url: contextPath+'/rest/getAllBillingProperties?ext=BTU',
		dataType: 'json',
		success: function(data) {
			var obj = data[0];
		    $("#TAndC").val(obj[0].propertyValue);
		    var obj = data[1];
		    $("#EmergencyContact").val(obj[0].propertyValue);
		    var obj = data[2];
		    $("#BillingInquiries").val(obj[0].propertyValue);
		    var obj = data[3];
		    $("#NBTax").val(obj[0].propertyValue);
		    var obj = data[4];
		    $("#VATax").val(obj[0].propertyValue);
 			var obj = data[9];
		    $("#DueDaysPeriod").val(obj[0].propertyValue);
 			var obj = data[10];
		    $("#ServiceCharge").val(obj[0].propertyValue);
        },
		error: function(jqXHR, exception) {
			window.location.href = contextPath;
		}
	      });
}

function redirect_tab(){
	var url_string =window.location.search;
	var urlParams = new URLSearchParams(url_string);
	if(url_string.includes("tag=customer")){
		$('#customerTab').tab('show')
	}
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
