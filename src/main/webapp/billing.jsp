<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
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

<!-- Custom fonts for this template-->
<link
	href="${contextPath}/resources/vendor/datatables/dataTables.bootstrap4.min.css"
	rel="stylesheet">
<link
	href="${contextPath}/resources/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link href="${contextPath}/resources/vendor/googleFonts/googleFonts.css"
	rel="stylesheet">
<link rel="stylesheet" href="${contextPath}/resources/Modules/iziToast/css/iziToast.min.css">
<link rel="stylesheet" href="${contextPath}/resources/vendor/flatpickr/flatpickr.min.css">




<!-- Custom styles for this template-->
<link href="${contextPath}/resources/css/sb-admin-2.css"
	rel="stylesheet">
	<link href="${contextPath}/resources/vendor/datatables/buttons.bootstrap4.min.css"
	rel="stylesheet">
	
	<script type="text/javascript">
	var contextPath = "${contextPath}";
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
                <span class="mr-2 d-none d-lg-inline text-gray-600 small">${pageContext.request.userPrincipal.name}</span>
                <div id="avatar"></div>
              </a>
              <!-- Dropdown - User Information -->
              <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                <a class="dropdown-item" href="#">
                  <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                  Profile
                </a>
                <a class="dropdown-item" href="#">
                  <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                  Settings
                </a>
                <a class="dropdown-item" href="#">
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

        <!-- Begin Page Content -->
        <div class="container">
        <div class="row">
        			<!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
		  	  	<h1 class="h3 mb-0 text-gray-800">Generate Bill</h1>
          </div>
        </div>
        </div>
        <!-- Begin tabs -->
        <div class="container">
        <div class="row">
        <div class="col"></div>
        <div class="col-xl-10 col-md-6 mb-4">
        <div class="card border-primary shadow">
			<ul class="nav nav-tabs" id="myTab" role="tablist">
			  <li class="nav-item">
			    <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">Electricity</a>
			  </li>
			  <li class="nav-item">
			    <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">Air Conditioning</a>
			  </li>
			</ul>
			<div class="tab-content" id="myTabContent">
			  <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
					<div class="card">
					      <div class="card-body">
					      <h6 class="h6 mb-0 text-gray-800">Electricity Invoice Wizard </h6>
					      <hr></hr>
		                  <div class="row no-gutters align-items-center">
		                  
							<div class="container-fluid">
								<div class="row">
									<div class="col-md-6">
										<div class="input-group mb-3">
										  <select class="custom-select" id="EFL">
										    <option selected value="Ground">Commercial Area</option>
										    <option value="3rd">Third</option>
										    <option value="4th">Fourth</option>
										    <option value="5th">Fifth</option>
										    <option value="6th">Sixth</option>
										  </select>
										   <div class="input-group-append">
										    <label class="input-group-text" for="inputGroupSelect02">Floor</label>
										  </div>
										</div> 
										
										<div class="input-group date mb-3" id="dp1">
										  <div class="input-group-prepend">
										    <span class="input-group-text " id="inputGroup-sizing-default">Period</span>
										  </div>
										  <input id ="EFR" type="text" class="form-control" aria-label="" aria-describedby="inputGroup-sizing-default"></input>
											<div id="EFRERROR" class="invalid-feedback">
									          Please fill this field.
									        </div>
										</div>
										
										<div class="input-group mb-3">
										  <div class="input-group-prepend">
											 <label class="input-group-text" for="inputGroupSelect01">Peak Rate</label>
										  </div>
										  <select class="custom-select" id="ERP">
										    <option selected value="20">20</option>
										    <option value="25">25</option>
										    <option value="30">30</option>
										    <option value="35">35</option>
										  </select>
										</div>
										 
										
										
										<div class="input-group mb-3">
										  <div class="input-group-prepend">
											 <label class="input-group-text" for="inputGroupSelect01">Discount</label>
										  </div>
										  <select class="custom-select" id="EDI">
										    <option selected value="0">None</option>
										    <option value="25">25% Special</option>
										  </select>
										</div>
										
										<div class="input-group mb-3">
										  <div class="input-group-prepend">
										    <span class="input-group-text" id="inputGroup-sizing-default">Adjustments</span>
										  </div>
										  <input id="EADJ" type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
											 <div class="invalid-feedback">
									          Please fill this field.
									        </div>
										</div> 
									</div>
									<div class="col-md-6">
										 
										<div class="input-group mb-3">
										  <div class="input-group-prepend">
											 <label class="input-group-text" for="inputGroupSelect01" id="EOFL" >Meter</label>
										  </div>
										  <select class="custom-select" id="EOF">
										    <option selected value="1">KWH 1</option>
										  </select>
										</div>
										 
										<div class="input-group mb-3">
										  <div class="input-group-prepend">
										    <span class="input-group-text" id="inputGroup-sizing-default">Previously Paid Amount</span>
										  </div>
										  <input id="EPP" type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
											 <div class="invalid-feedback">
									          Please fill this field.
									        </div>
										</div>
										 
										<div class="input-group mb-3">
										  <div class="input-group-prepend">
											 <label class="input-group-text" for="inputGroupSelect01">Off Peak Rate</label>
										  </div>
										  <select class="custom-select" id="ERO">
										    <option selected value="20">20</option>
										    <option value="25">25</option>
										    <option value="30">30</option>
										    <option value="35">35</option>
										  </select>
										</div>
										 
										<div class="input-group mb-3">
										  <div class="input-group-prepend">
											 <label class="input-group-text" for="inputGroupSelect01">Penalty</label>
										  </div>
										  <select class="custom-select" id="EPE">
										    <option selected value="0">None</option>
										    <option value="25">Delayed payment</option>
										    <option value="30">Damage</option>
										  </select>
										</div>
										<button id="EGEN" type="button" class="btn btn-success btn-block">
											Generate Bill
								</button>
										
									</div>
								</div>
								
							</div>
		                  </div>
		                </div>
		                		                	<hr></hr>
											<div class="card-body">
												<h6 class="h6 mb-0 text-gray-800">Electricity Bill Log</h6>
												<hr></hr>
													<br>
												<div class="row no-gutters align-items-center">
													<div class="table-responsive">
														<table class="table table-bordered" id="elecLog"
															width="100%" cellspacing="0">
															<thead>
																<tr>
																	<th>Invoice Date</th>
																	<th>Invoice No.</th>
																	<th>Floor</th>
																	<th>Office</th>
																	<th>From</th>
																	<th>To</th>
																	<th>Action</th>
																</tr>
															</thead>
															<tfoot>
																<tr>
																	<th>Invoice Date</th>
																	<th>Invoice No.</th>
																	<th>Floor</th>
																	<th>Office</th>
																	<th>From</th>
																	<th>To</th>
																	<th>Action</th>
																</tr>
															</tfoot>
															<tbody>

															</tbody>
														</table>
													</div>
												</div>
											</div>
	                </div>
				</div>
			  <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
					<div class="card">
					      <div class="card-body">
					      <h1 class="h6 mb-0 text-gray-800">Air Conditioning Invoice Wizard</h1>
					      <hr></hr>
		                  <div class="row no-gutters align-items-center">
						  <div class="container-fluid">
								<div class="row">
									<div class="col-md-6">
										<div class="input-group mb-3">
										  <select class="custom-select" id="AFL">
										    <option selected value="Ground">Commercial Area</option>
										    <option value="3rd">Third</option>
										    <option value="4th">Fourth</option>
										    <option value="5th">Fifth</option>
										    <option value="6th">Sixth</option>
										  </select>
										   <div class="input-group-append">
										    <label class="input-group-text" for="inputGroupSelect02">Floor</label>
										  </div>
										</div> 
										
										<div class="input-group date mb-3" id="dp3">
										  <div class="input-group-prepend">
										    <span class="input-group-text " id="inputGroup-sizing-default">Period</span>
										  </div>
										  <input id ="AFR" type="text"  class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default"></input>
										   <div id="AFRERROR" class="invalid-feedback">
									          Please fill this field.
									        </div>
										</div>
										
										<div class="input-group mb-3">
										  <div class="input-group-prepend">
											 <label class="input-group-text" for="inputGroupSelect01">Peak Rate</label>
										  </div>
										  <select class="custom-select" id="ARP">
										    <option selected value="0.020">0.020</option>
										    <option value="0.025">0.025</option>
										    <option value="0.030">0.030</option>
										    <option value="0.035">0.035</option>
										  </select>
										</div>
										 
										
										
										<div class="input-group mb-3">
										  <div class="input-group-prepend">
											 <label class="input-group-text" for="inputGroupSelect01">Discount</label>
										  </div>
										  <select class="custom-select" id="ADI">
										    <option selected value="0">None</option>
										    <option value="25">25% Special</option>
										  </select>
										</div>
										<div class="input-group mb-3">
										  <div class="input-group-prepend">
										    <span class="input-group-text" id="inputGroup-sizing-default">Adjustments</span>
										  </div>
										  <input id="AADJ" type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
											 <div class="invalid-feedback">
									          Please fill this field.
									        </div>
										</div>
										
									</div>
									<div class="col-md-6">
										 
										<div class="input-group mb-3">
										  <div class="input-group-prepend">
											 <label class="input-group-text" for="inputGroupSelect01" id="AOFL" >Meter</label>
										  </div>
										  <select class="custom-select" id="AOF">
										    <option selected value="2">BTU 1</option>
										    <option value="3">BTU 2</option>
										    <option value="4">BTU 3</option>
										  </select>
										</div>
										 
										<div class="input-group mb-3">
										  <div class="input-group-prepend">
										    <span class="input-group-text" id="inputGroup-sizing-default">Previously Paid Amount</span>
										  </div>
										  <input id="APP" type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
											 <div class="invalid-feedback">
									          Please fill this field.
									        </div>
										</div>
										 
										<div class="input-group mb-3">
										  <div class="input-group-prepend">
											 <label class="input-group-text" for="inputGroupSelect01">Off Peak Rate</label>
										  </div>
										  <select class="custom-select" id="ARO">
										    <option selected value="0.020">0.020</option>
										    <option value="0.025">0.025</option>
										    <option value="0.030">0.030</option>
										    <option value="0.035">0.035</option>
										  </select>
										</div>
										 
										<div class="input-group mb-3">
										  <div class="input-group-prepend">
											 <label class="input-group-text" for="inputGroupSelect01">Penalty</label>
										  </div>
										  <select class="custom-select" id="APE">
										    <option selected value="0">None</option>
										    <option value="25">Delayed payment</option>
										    <option value="30">Damage</option>
										  </select>
										</div>
										
										<button id="AGEN" type="button" class="btn btn-primary btn-block">
											Generate Bill
										</button>
										
									</div>
									
								</div>
							</div>
 
		                  </div>
		                </div>
		                	<hr></hr>
											<div class="card-body">
												<h6 class="h6 mb-0 text-gray-800">Air Conditioning Bill Log</h6>
												<hr></hr>
													<br>
												<div class="row no-gutters align-items-center">
													<div class="table-responsive">
														<table class="table table-bordered" id="airconLog"
															width="100%" cellspacing="0">
															<thead>
																<tr>
																	<th>Invoice Date</th>
																	<th>Invoice No.</th>
																	<th>Floor</th>
																	<th>Office</th>
																	<th>From</th>
																	<th>To</th>
																	<th>Action</th>
																</tr>
															</thead>
															<tfoot>
																<tr>
																	<th>Invoice Date</th>
																	<th>Invoice No.</th>
																	<th>Floor</th>
																	<th>Office</th>
																	<th>From</th>
																	<th>To</th>
																	<th>Action</th>
																</tr>
															</tfoot>
															<tbody>

															</tbody>
														</table>
													</div>
												</div>
											</div>
		               </div>
				</div>
			</div>
		</div>
		</div>
		<div class="col"></div>
		</div>
		</div>
		<!-- /.container-fluid -->

      </div>
      <!-- End of Main Content -->

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
  
<!-- Bill confirmation Modal -->
<div class="modal fade" id="BillConfirmation" tabindex="-1" role="dialog" aria-labelledby="Bill Confirmation" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Confirm Bill Generation</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      	<div class="modal-body">
		<p>*Bill generation cannot be undone or deleted. Please make sure the details are correct</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Back</button>
        <button type="button" class="btn btn-primary" id="confirmed">Confirm</button>
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
            <span aria-hidden="true"></span>
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
	<script src="${contextPath}/resources/vendor/moment/datetime-moment.js"></script>
	<script src="${contextPath}/resources/vendor/flatpickr/flatpickr.js"></script>
	<script src="${contextPath}/resources/vendor/flatpickr/rangePlugin.js"></script>
	<script src="${contextPath}/resources/Modules/iziToast/js/iziToast.min.js" type="text/javascript"></script>
	
	<!-- Page level custom scripts -->
	<script src="${contextPath}/resources/js/sockjs/sockjs.min.js"></script>
    <script src="${contextPath}/resources/js/stompjs/stomp.min.js"></script>
    <script src="${contextPath}/resources/js/WebSock.js"></script>
    <script src="${contextPath}/resources/js/billing.js" type="text/javascript"></script>

</body>

</html>
