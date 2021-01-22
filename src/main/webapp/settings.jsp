<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="absolutePath" value="${pageContext.request.localName}"/>

<!DOCTYPE html>
<html lang="en">



<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="Web application for IQ BMS portal">
  <meta name="author" content="@z4nadeesh">

  <title>TEBBIQ - Dashboard</title>
  <link rel="icon" href="${contextPath}/resources/img/iq_systems.ico" />

<link href="${contextPath}/resources/css/sb-admin-2.css" rel="stylesheet">
<link href="${contextPath}/resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/resources/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
<link href="${contextPath}/resources/vendor/datatables/buttons.bootstrap4.min.css" rel="stylesheet">
<link href="${contextPath}/resources/vendor/googleFonts/googleFonts.css" rel="stylesheet">
<link href="${contextPath}/resources/Modules/iziToast/css/iziToast.min.css" rel="stylesheet" >

<%-- <link href="${contextPath}/resources/vendor/select2/css/select2.css" rel="stylesheet" /> --%>
<%-- <link rel="stylesheet" href="${contextPath}/resources/vendor/select2/css/select2-bootstrap.css"> --%>
<link href="${contextPath}/resources/vendor/bootstrapTags/bootstrap-tagsinput.css" rel="stylesheet">
<link href="${contextPath}/resources/css/switch.css" rel="stylesheet">


	
	
	<script type="text/javascript">
	var contextPath = "${contextPath}";
</script>

<script>

</script>

<script type="text/javascript">
var contextPath = "${contextPath}";
</script>
<style type="text/css">
.card.zooming:hover{
transform:scale(1.05);
}
</style>
</head>

<body id="page-top" onload="startTime()" >

  <!-- Page Wrapper -->
  <div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-iq sidebar sidebar-dark accordion" id="accordionSidebar">

      <!-- Sidebar - Brand -->
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="${contextPath}/">
        <div class="sidebar-brand-icon ">
          <i class="fas fa-city"></i>
        </div>
        <div class="sidebar-brand-text mx-3 text-lg">TEBBIQ</div>
      </a>

      <!-- Divider -->
      <hr class="sidebar-divider my-0">

      <!-- Nav Item - Dashboard -->
      <li class="nav-item active">
        <a class="nav-link" href="${contextPath}/">
          <i class="fas fa-fw fa-tachometer-alt"></i>
          <span>Dashboard</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider">

      <!-- Heading -->
      <div class="sidebar-heading">
        Services
      </div>

      <!-- Nav Item - Pages Collapse Menu -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
          <i class="fas fa-fw fa-building"></i>
          <span>Property</span>
        </a>
        <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">Utilities:</h6>
            <a class="collapse-item" href="property?id=Ground"><i class="far fa-fw fa-building "></i>&nbsp&nbspGround Floor</a>
            <a class="collapse-item" href="property?id=3rd"><i class="far fa-fw fa-building "></i>&nbsp&nbspThird Floor</a>
            <a class="collapse-item" href="property?id=4th"><i class="far fa-fw fa-building "></i>&nbsp&nbspFourth Floor</a>
            <a class="collapse-item" href="property?id=5th"><i class="far fa-fw fa-building "></i>&nbsp&nbspFifth Floor</a>
            <a class="collapse-item" href="property?id=6th"><i class="far fa-fw fa-building "></i>&nbsp&nbspSixth Floor</a>
          </div>
        </div>
      </li>

      <!-- Nav Item - Utilities Collapse Menu -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilities" aria-expanded="true" aria-controls="collapseUtilities">
          <i class="fas fa-fw fa-clipboard-list"></i>
          <span>Reports</span>
        </a>
        <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">Report Explorer:</h6>
            <a class="collapse-item" href="${contextPath}/reports?id=dailyuseage"><i class="fas fa-fw fa-table"></i>&nbsp&nbspDaily Usage</a>
            <a class="collapse-item" href="${contextPath}/reports?id=customeruseage"><i class="fas fa-fw fa-table"></i>&nbsp&nbspTenant Summary</a>
            <a class="collapse-item" href="${contextPath}/reports?id=facilityuseage"><i class="fas fa-fw fa-table"></i>&nbsp&nbspFacility Summary</a>
          </div>
        </div>
      </li>
      
      <!-- Nav Item - Charts -->
      <li class="nav-item">
        <a class="nav-link" href="${contextPath}/billing">
          <i class="fas fa-fw fa-dollar-sign"></i>
          <span>Billing</span></a>
      </li>
      <security:authorize access="hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN')">
						

				<!-- Divider -->
				<hr class="sidebar-divider">
	
				<!-- Heading -->
				<div class="sidebar-heading">System</div>
				<!-- Nav Item - Settings -->
				<li class="nav-item"><a class="nav-link" href="${contextPath}/settings"> <i
						class="fas fa-fw fa-tools"></i> <span>Settings</span></a></li>
					
			</security:authorize>

      <!-- Divider -->
      <hr class="sidebar-divider d-none d-md-block">

      <!-- Sidebar Toggler (Sidebar) -->
      <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
      </div>
      
    </ul>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <!-- Topbar -->
        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

          <!-- Sidebar Toggle (Topbar) -->
          <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
            <i class="fa fa-bars"></i>
          </button>
          
			<!-- Topbar date/time -->
          <div class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100">
            <div>
            <h1 id="dateTime" class="h3 mb-0 text-gray-800"></h1>
          </div>
          </div>
       

          <!-- Topbar Navbar -->
          <ul class="navbar-nav ml-auto">

            

				<!-- Nav Item - Alerts -->
						<li id="alertsDropdown" class="nav-item dropdown no-arrow mx-1"><a
							class="nav-link dropdown-toggle" href="#" 
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <i class="fas fa-bell fa-lg"></i> 
							<!-- Counter - Alerts -->
							<span id='alertCount' class="badge badge-danger badge-counter "></span>
						</a> <!-- Dropdown - Alerts -->
							<div  class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="alertsDropdown">
								<h6 class="dropdown-header">Alerts Center</h6>
								<span id=alertElements>

								</span>
								<a class="dropdown-item text-center small text-gray-500"
									href="${contextPath}/alerts">Show All Alerts</a>
							</div></li>
				

            <div class="topbar-divider d-none d-sm-block"></div>

            <!-- Nav Item - User Information -->
            <li class="nav-item dropdown no-arrow">
              <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <span id="uname" class="mr-2 d-none d-lg-inline text-gray-600 small">${pageContext.request.userPrincipal.name}</span>
                <div id="avatar"></div>
              </a>
              <!-- Dropdown - User Information -->
              <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                <a class="dropdown-item" href="#" id="profile_BTN" >
                  <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                  Profile
                </a>
                <security:authorize access="hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN')">
                <a class="dropdown-item" href="${contextPath}/settings">
                  <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                  Settings
                </a>
                </security:authorize>
                <a class="dropdown-item" href="${contextPath}/activity">
                  <i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
                  Activity Log
                </a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                  <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                  Logout
                </a>
              </div>
            </li>

          </ul>

        </nav>
        <!-- End of Topbar -->

        <!-- Begin Page Header -->
        <div class="container">
        <div class="row">
        			<!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
		  	  	<h1 class="h3 mb-0 text-gray-800">Settings</h1>
          </div>
        </div>
        </div>
        <!-- Begin Content -->
        <div class="container">
	      <div class="row">
	        <div class="col-md-4 d-none d-md-block">
	          <div class="card">
	            <div class="card-body ">
	              <nav class="nav flex-column nav-pills nav-gap-y-1">
	                <a href="#user" data-toggle="tab" class="nav-item nav-link has-icon nav-link-faded active">
	                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-user mr-2"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path><circle cx="12" cy="7" r="4"></circle></svg>
	                  User Settings
	                </a>
	                <a href="#customer" id="customerTab" data-toggle="tab" class="nav-item nav-link has-icon nav-link-faded">
	                  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24" fill="currentColor" stroke="currentColor" stroke-width="0.5" stroke-linecap="round" stroke-linejoin="round" class="feather feather-credit-card mr-2"><path fill-rule="evenodd" d="M11.03 2.59a1.5 1.5 0 011.94 0l7.5 6.363a1.5 1.5 0 01.53 1.144V19.5a1.5 1.5 0 01-1.5 1.5h-5.75a.75.75 0 01-.75-.75V14h-2v6.25a.75.75 0 01-.75.75H4.5A1.5 1.5 0 013 19.5v-9.403c0-.44.194-.859.53-1.144l7.5-6.363zM12 3.734l-7.5 6.363V19.5h5v-6.25a.75.75 0 01.75-.75h3.5a.75.75 0 01.75.75v6.25h5v-9.403L12 3.734z"></path></svg>
	                  Customer Settings
	                </a>
	                <a href="#billing" data-toggle="tab" class="nav-item nav-link has-icon nav-link-faded">
	                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-credit-card mr-2"><rect x="1" y="4" width="22" height="16" rx="2" ry="2"></rect><line x1="1" y1="10" x2="23" y2="10"></line></svg>
	                  Billing Settings
	                </a>
	                <a href="#system" data-toggle="tab" class="nav-item nav-link has-icon nav-link-faded">
	                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-settings mr-2"><circle cx="12" cy="12" r="3"></circle><path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"></path></svg>
	                  System Settings
	                </a>
	                
	              </nav>
	            </div>
	          </div>
	        </div>
	        <div class="col-md-8">
	          <div class="card">
	            <div class="card-header border-bottom mb-3 d-flex d-md-none">
	              <ul class="nav nav-tabs card-header-tabs nav-gap-x-1" role="tablist">
	                <li class="nav-item">
	                  <a href="#user" data-toggle="tab" class="nav-link has-icon active"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-user"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path><circle cx="12" cy="7" r="4"></circle></svg></a>
	                </li>
	                <li class="nav-item">
	                  <a href="#customer" data-toggle="tab" class="nav-link has-icon"><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24" fill="currentColor" stroke="currentColor" stroke-width="0.5" stroke-linecap="round" stroke-linejoin="round" class="feather feather-credit-card mr-2"><path fill-rule="evenodd" d="M11.03 2.59a1.5 1.5 0 011.94 0l7.5 6.363a1.5 1.5 0 01.53 1.144V19.5a1.5 1.5 0 01-1.5 1.5h-5.75a.75.75 0 01-.75-.75V14h-2v6.25a.75.75 0 01-.75.75H4.5A1.5 1.5 0 013 19.5v-9.403c0-.44.194-.859.53-1.144l7.5-6.363zM12 3.734l-7.5 6.363V19.5h5v-6.25a.75.75 0 01.75-.75h3.5a.75.75 0 01.75.75v6.25h5v-9.403L12 3.734z"></path></svg></a>
	                </li>
	                <li class="nav-item">
	                  <a href="#billing" data-toggle="tab" class="nav-link has-icon"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-credit-card"><rect x="1" y="4" width="22" height="16" rx="2" ry="2"></rect><line x1="1" y1="10" x2="23" y2="10"></line></svg></a>
	                </li>
	                <li class="nav-item">
	                  <a href="#system" data-toggle="tab" class="nav-link has-icon"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-settings"><circle cx="12" cy="12" r="3"></circle><path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"></path></svg></a>
	                </li>
	                
	              </ul>
	            </div>
	            <div class="card-body tab-content">
	              <div class="tab-pane active" id="user">
	                <h5>USER SETTINGS</h5>
	                <hr>
	                <div class="container">
												<div class="row no-gutters align-items-center">
													<div class="table-responsive">
														<table class="table table-bordered stripe" id="userTable"
															width="100%" cellspacing="0">
															<thead>
																<tr>
																	<th>Name</th>
																	<th>Department</th>
																	<th>Email</th>
																	<th>Role</th>
																</tr>
															</thead>
															<tbody>
															</tbody>
														</table>
													</div>
												</div>
											</div>
	              </div>
	              <div class="tab-pane " id="customer">
	                <h5>CUSTOMER SETTINGS</h5>
	                <hr>
	                <div class="container">
		                <form:form method="POST" modelAttribute="customerForm" class="customer" autocomplete="off">
		                <!-- 	                "name": "Default Company",
												"address": "Default Address",
												"nic": "123456789",
												"brc": "12345",
												"phone": "0112123456",
												"email": "admin@default.com",
												"business_phone": "0112123456",
												"business_email": "admin@default.com"
	 							--> 
	
		                		<div class="form-group">
							    <label for="inputAddress2">Name</label>
							    <spring:bind path="name">
							    <form:input type="text" class="form-control ${status.error ? 'is-invalid' : ''}" path="name" placeholder="ABC (Pvt) Ltd."></form:input>
							    <form:errors path="name" class="invalid-feedback"></form:errors>
							    </spring:bind>
							  </div>
							  <div class="form-group">
							    <label for="inputAddress">Address</label>
							    <spring:bind path="address">
							    <form:input type="text" class="form-control ${status.error ? 'is-invalid' : ''}" path="address" placeholder="1234 Main St"></form:input>
							    <form:errors path="address" class="invalid-feedback"></form:errors>
							    </spring:bind>
							  </div>
							  <div class="form-row">
							    <div class="form-group col-md-6">
							      <label for="inputEmail4">National Identity Number</label>
							      <spring:bind path="nic">
							      <form:input type="text" class="form-control " path="nic" placeholder="123456789V"></form:input>
							      </spring:bind>
							    </div>
							    <div class="form-group col-md-6">
							      <label for="inputPassword4">Business Registration</label>
							      <spring:bind path="brc">
							      <form:input type="text" class="form-control" path="brc" placeholder="123456789"></form:input>
							      </spring:bind>
							    </div>
							  </div>
							  <div class="form-row">
							    <div class="form-group col-md-6">
							      <label for="inputEmail4">Email</label>
							      <spring:bind path="email">
							      <form:input type="text" class="form-control" path="email" placeholder="admin@company.lk"></form:input>
							      </spring:bind>
							    </div>
							    <div class="form-group col-md-6">
							      <label for="inputPassword4">Phone</label>
							      <spring:bind path="phone">
							      <form:input type="text" class="form-control" path="phone" placeholder="0112345678"></form:input>
							      </spring:bind>
							    </div>
							  </div>
							  <div class="form-row">
							    <div class="form-group col-md-6">
							      <label for="inputEmail4">Business Email</label>
							      <spring:bind path="business_email">
							      <form:input type="text" class="form-control ${status.error ? 'is-invalid' : ''}" path="business_email" placeholder="emergency@company.lk"></form:input>
							      <form:errors path="business_email" class="invalid-feedback"></form:errors>
							      </spring:bind>
							    </div>
							    <div class="form-group col-md-6">
							      <label for="inputPassword4">Business Phone</label>
							      <spring:bind path="business_phone">
							      <form:input type="text" class="form-control ${status.error ? 'is-invalid' : ''}" path="business_phone" placeholder="0112345678"></form:input>
							      <form:errors path="business_phone" class="invalid-feedback"></form:errors>
							      </spring:bind>
							    </div>
							  </div>
							  <div class="text-right">
							  <button type="submit" class="btn btn-primary pull-right">Add Customer</button>
							  </div>
						</form:form>
					</div>
	                <hr>
	                <div class="container">
						     <div class="row no-gutters align-items-center">
								<div class="table-responsive">
									<table class="table table-bordered stripe" id="customerTable"
											width="100%" cellspacing="0">
											<thead>
												<tr>
													<th>No</th>
													<th>Floor</th>
													<th>Unit</th>
													<th>Meter</th>
													<th>Customer</th>
												</tr>
											</thead>
											<tbody>
											</tbody>
									</table>
								</div>
							</div>
						</div>
	              </div>
	              <div class="tab-pane" id="billing">
	                <h5>BILLING SETTINGS</h5>
	                <hr>
	                <div class="container">
	                <form>
						<!-- 	   
								 "EmergencyContact";
								 "BillingInquiries";
							"DueDaysPeriod";
						"NBTax";
								"VATax";
								 "ServiceCharge";
								 
								  "TAndC";
								 
								 "PeakRate";
					 "OffPeakRate";
							 "Penalty";
							 "Discount";            -->
					   <div class="form-row">
						<div class="form-group col-md-6">
						   <label for="ServiceCharge">Service Charge (LKR)</label>
						   <input type="text" class="form-control" id="ServiceCharge" placeholder="LKR"></input>
						   <div class="error"></div>
						   </div>
						   <div class="form-group col-md-6">
						   <label for="DueDaysPeriod">Due Days Period (Days)</label>
						   <input type="text" class="form-control" id="DueDaysPeriod" placeholder="Days"></input>
						   <div class="error"></div>
						</div>
					  </div>
					  <div class="form-row">
						<div class="form-group col-md-6">
						   <label for="NBTax">NBT (Percentage %)</label>
						   <input type="text" class="form-control" id="NBTax" placeholder="%"></input>
						   <div class="error"></div>
						   </div>
						   <div class="form-group col-md-6">
						   <label for="VATax">VAT (Percentage %)</label>
						   <input type="text" class="form-control" id="VATax" placeholder="%"></input>
						   <div class="error"></div>
						</div>
					  </div>
					  <div class="form-row">
						<div class="form-group col-md-6">
						   <label for="EmergencyContact">Emergency Contact</label>
						   <input type="text" class="form-control" id="EmergencyContact" placeholder="0112345678"></input>
						   <div class="error"></div>
						   </div>
						   <div class="form-group col-md-6">
						   <label for="BillingInquiries">Billing Inquiries</label>
						   <input type="text" class="form-control" id="BillingInquiries" placeholder="0112345678"></input>
						   <div class="error"></div>
						</div>
					  </div>
	                  <div class="form-group">
	                    <label for="TAndC">Terms & Conditions</label>
	                    <textarea class="form-control autosize" id="TAndC" maxlength="520" placeholder="Legal" style="overflow: hidden; overflow-wrap: break-word; resize: none; height: 190px;"></textarea>
	                 	<div class="error"></div>
	                  </div>
	                  <div class="text-right">
	                  <button type="button" id="billPropsMK1" class="btn btn-warning pull-right">Update Values</button>
	                  <button type="button" id="billPropsMK1_RST" class="btn btn-light pull-right">Reset Changes</button>
	                  
	                  </div>
	                  <input class="form-control invisible"></input>
	                  <h6>Electricity Unit Rate Settings</h6>
	                  <hr>
						<div class="form-group">
						<label for="elecPeak">Peak Electricity Unit Rates (LKR/kWh)</label>
						  <input id="elecPeak" multiple type="number" class="form-control" aria-label="Username" aria-describedby="basic-addon3">
						  <small id="emailHelp" class="form-text text-muted"><i>To enter a new value, type it in and press enter (5 entries max)</i></small>
						</div>
						<div class="form-group">
						<label for="elecOffPeak">Off-Peak  Electricity Unit Rates (LKR/kWh)</label>
						  <input id="elecOffPeak" multiple type="text" class="form-control" aria-label="Username" aria-describedby="basic-addon3">
						  <small id="emailHelp" class="form-text text-muted"><i>To enter a new value, type it in and press enter (5 entries max)</i></small>
						</div>
						<div class="text-right">
		                  <button type="button" id=elecRates class="btn btn-success pull-right">Update Values</button>
		                  <button type="button" id=elecRates_RST class="btn btn-light pull-right">Reset Changes</button>
		                  </div>
		                  <input class="form-control invisible"></input>
		                  <h6>Air Conditioning Unit Rate Settings</h6>
	                  <hr>
						<div class="form-group">
						<label for="airconPeak">Peak Air Conditioning Unit Rates (LKR/BTU)</label>
						  <input id="airconPeak" multiple type="text" class="form-control" aria-label="Username" aria-describedby="basic-addon3">
						  <small id="emailHelp" class="form-text text-muted"><i>To enter a new value, type it in and press enter (5 entries max)</i></small>
						</div>
						<div class="form-group">
						<label for="airconOffPeak">Off-Peak Air Conditioning Unit Rates (LKR/BTU)</label>
						  <input id="airconOffPeak" multiple type="text" class="form-control" aria-label="Username" aria-describedby="basic-addon3">
						  <small id="emailHelp" class="form-text text-muted"><i>To enter a new value, type it in and press enter (5 entries max)</i></small>
						</div>
						<div class="text-right">
		                  <button type="button" id="airconRates" class="btn btn-primary pull-right">Update Values</button>
		                  <button type="button" id="airconRates_RST" class="btn btn-light pull-right">Reset Changes</button>
		                  </div>
		                  
		                  <input class="form-control invisible"></input>
	                  	  <h6>Discount Settings</h6>
	                  	  <hr>
						<div class="form-group">
						<label for="elecDisc">Electricity Bill Discounts (%)</label>
						  <input id="elecDisc" multiple type="number" class="form-control" aria-label="Username" aria-describedby="basic-addon3">
						  <small id="emailHelp" class="form-text text-muted"><i>To enter a new value, type it in and press enter (5 entries max)</i></small>
						</div>
						<div class="form-group">
						<label for="airconDisc">Air Conditioning Discounts (%)</label>
						  <input id="airconDisc" multiple type="text" class="form-control" aria-label="Username" aria-describedby="basic-addon3">
						  <small id="emailHelp" class="form-text text-muted"><i>To enter a new value, type it in and press enter (5 entries max)</i></small>
						</div>
						<div class="text-right">
		                  <button type="button" id=discounts class="btn btn-info pull-right">Update Values</button>
		                  <button type="button" id="discounts_RST" class="btn btn-light pull-right">Reset Changes</button>
		                  </div>
		                  
						<input class="form-control invisible"></input>
	                  	  <h6>Penalty Settings</h6>
	                  	  <hr>
						<div class="form-group">
						<label for="elecPen">Electricity Bill Penalties (%)</label>
						  <input id="elecPen" multiple type="text" class="form-control" aria-label="Username" aria-describedby="basic-addon3">
						  <small id="emailHelp" class="form-text text-muted"><i>To enter a new value, type it in and press enter (5 entries max)</i></small>
						</div>
						
						<div class="form-group">
						<label for="airconPen">Air Conditioning Bill Penalties (%)</label>
						  <input id="airconPen" multiple type="text" class="form-control" aria-label="Username" aria-describedby="basic-addon3">
						  <small id="emailHelp" class="form-text text-muted"><i>To enter a new value, type it in and press enter (5 entries max)</i></small>
						</div>
						<div class="text-right">
		                  <button type="button" id="peanlties" class="btn btn-danger pull-right">Update Values</button>
		                  <button type="button" id="peanlties_RST" class="btn btn-light pull-right">Reset Changes</button>
		                  </div>
	                </form>
	                </div>
	              </div>
	              <div class="tab-pane " id="system">
	                <h5>SYSTEM SETTINGS</h5>
	                <hr>
	         		<div class="container">
	                <form>
					   <div class="form-row">
						<div class="form-group col-md-6">
						   <label for="peakStart">Peak Demand Start</label>
						   <input type="text" class="form-control" id="peakStart" placeholder="HH:MM"></input>
						   <div class="error"></div>
						   </div>
						   <div class="form-group col-md-6">
						   <label for="offPeakStart">Peak Demand End</label>
						   <input type="text" class="form-control" id="offPeakStart" placeholder="HH:MM"></input>
						   <div class="error"></div>
						</div>
					  </div>
	                  <div class="text-right">
	                  <button type="button" id="sysConfig" class="btn btn-warning pull-right">Update Values</button>
	                  <button type="button" id="sysConfig_RST" class="btn btn-light pull-right">Reset Changes</button>
	                  </div>
	                  </form>
	                  <input class="form-control invisible"></input>
	                  <h6>Network Settings</h6>
	                  <hr>
	                  <div class="container">
						     <div class="row no-gutters align-items-center">
								<div class="table-responsive">
									<table class="table table-bordered stripe" id="networkTable"
											width="100%" cellspacing="0">
											<thead>
												<tr>
													<th>Id</th>
													<th>Unit</th>
													<th>Meter No</th>
													<th>Extension</th>
													<th>ModBus Address</th>
												</tr>
											</thead>
											<tbody>
											</tbody>
									</table>
								</div>
							</div>
						</div>
	                  <hr>
	            
	                </div>
	              </div>
	              
	            </div>
	          </div>
	        </div>
	      </div>
	      
		</div>
		<!-- /.container-fluid -->

      </div>
      <!-- End of Main Content -->
<div class="container invisible"><div class="row"><p>padding</p></div></div>
      <!-- Footer -->
      <footer class="sticky-footer bg-white">
        <div class="container my-auto">
          <div class="copyright text-center my-auto">
            <span>Copyright &copy; IQ Systems 2019</span>
          </div>
        </div>
      </footer>
      <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->
  </div>

  <!-- End of Page Wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>
  
<!-- User Info Modal -->
<div class="modal fade" id="UserInfo" tabindex="-1" role="dialog" aria-labelledby="Bill Confirmation" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Information</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      	<div class="modal-body">
						<div class="card">
					<div class="card-header">
						<h5 id="roleH" class="card-title mb-0"></h5>
					</div>
					<div class="card-body">
						<div class="row justify-content-center">
							<div  class="col">
								<div id="initials"></div>
							</div>
							<div class="col-sm-9 col-xl-12 col-xxl-9 text-center text-capitalize font-weight-bold">
								<p id="Mfirstname"></p>
							</div>
						</div>

						<table class="table table-sm mt-2 mb-4">
							<tbody>
								<tr>
									<th class="align-middle">Name</th>
									<td class="align-middle" id="Mname"></td>
								</tr>
								<tr>
									<th class="align-middle">Department</th>
									<td class="align-middle" id="Mdepartment"></td>
								</tr>
								<tr>
									<th class="align-middle">Email</th>
									<td class="align-middle" id="Memail"></td>
								</tr>
								<tr>
									<th class="align-middle">Phone</th>
									<td class="align-middle" id="Mphone" ></td>
								</tr>
								<security:authorize access="hasRole('ROLE_SUPERADMIN')">
									<tr>
										<th class="align-middle">Promote/Demote</th>
										<td class="align-middle">
										<form>
				                               <label class="switch ">
										          <input id="promote" data-toggle="toggle" type="checkbox" value="true" class="warning">
										          <span class="slider round"></span>
										        </label>
										</form>
									   </td>
									</tr>
								</security:authorize>
								<security:authorize access="hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN')">
									<tr>
										<th class="align-middle">Enable</th>
										<td class="align-middle">
										<form>
				                               <label class="switch ">
										          <input id="enable" data-toggle="toggle" type="checkbox" class="primary"  >
										          <span class="slider2 round"></span>
										        </label>
										</form>
									   </td>
									</tr>
								</security:authorize>
								<tr>
									<th class="align-middle"></th>
									<td class="align-middle"></td>
								</tr>
							</tbody>
						</table>

					</div>
				</div>
		

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<!-- Profile Modal -->
<div class="modal fade" id="userProfile" tabindex="-1" role="dialog" aria-labelledby="Bill Confirmation" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Profile</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      	<div class="modal-body">
						<div class="card">
					<div class="card-header">
						<h5 id="ProleH" class="card-title mb-0"></h5>
					</div>
					<div class="card-body">
						<div class="row justify-content-center">
							<div  class="col">
								<div id="Pinitials"></div>
							</div>
							<div class="col-sm-9 col-xl-12 col-xxl-9 text-center text-capitalize font-weight-bold">
								<p id="Pfirstname"></p>
							</div>
						</div>

						<table class="table table-sm mt-2 mb-4">
							<tbody>
								<tr>
									<th class="align-middle">Name</th>
									<td class="align-middle" id="Pname"></td>
								</tr>
								<tr>
									<th class="align-middle">Department</th>
									<td class="align-middle" id="Pdepartment"></td>
								</tr>
								<tr>
									<th class="align-middle">Email</th>
									<td class="align-middle" id="Pemail"></td>
								</tr>
								<tr>
									<th class="align-middle">Phone</th>
									<td class="align-middle" id="Pphone" ></td>
								</tr>
								<tr>
									<th class="align-middle"></th>
									<td class="align-middle"></td>
								</tr>
							</tbody>
						</table>

					</div>
				</div>
		

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
        <div class="modal-footer">
	      <form id="logoutForm" method="POST" action="${contextPath}/logout">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          	<button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
          	<input class="btn btn-primary" type="submit" value="Logout"></input>
          </form>
        </div>
      </div>
    </div>
  </div>
	<!-- Bootstrap core JavaScript-->
	<script src="${contextPath}/resources/vendor/jquery/jquery.min.js"></script>
	<script src="${contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script src="${contextPath}/resources/vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Custom scripts for all pages-->
	<script src="${contextPath}/resources/js/sb-admin-2.min.js"></script>
	<script src="${contextPath}/resources/js/bootstrap-datepicker.min.js"></script>

	<!-- Page level plugins -->
	<script src="${contextPath}/resources/vendor/chart.js/Chart.min.js"></script>
	<script src="${contextPath}/resources/vendor/datatables/jquery.dataTables.min.js"></script>
	<script src="${contextPath}/resources/vendor/datatables/dataTables.bootstrap4.min.js"></script>
	<script src="${contextPath}/resources/vendor/datatables/dataTables.buttons.min.js"></script>
	<script src="${contextPath}/resources/vendor/datatables/buttons.bootstrap4.min.js"></script>
	<script src="${contextPath}/resources/vendor/moment/moment.min.js"></script>
	<script src="${contextPath}/resources/Modules/iziToast/js/iziToast.min.js" type="text/javascript"></script>
<%-- 	<script src="${contextPath}/resources/vendor/select2/js/select2.min.js"></script> --%>
	<script src="${contextPath}/resources/vendor/bootstrapTags/typeahead.bundle.js"></script>
	<script src="${contextPath}/resources/vendor/bootstrapTags/bootstrap-tagsinput.js"></script>

	<!-- Page level custom scripts -->
	<script src="${contextPath}/resources/js/sockjs/sockjs.min.js"></script>
    <script src="${contextPath}/resources/js/stompjs/stomp.min.js"></script>
    <script src="${contextPath}/resources/js/WebSock.js"></script>
    <script src="${contextPath}/resources/js/settings.js" type="text/javascript"></script>
</body>

</html>
