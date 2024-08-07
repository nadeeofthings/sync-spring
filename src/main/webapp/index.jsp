<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="Web application for IQ BMS portal">
<meta name="author" content="@z4nadeesh">

<title>TEBBIQ - Dashboard</title>
<link rel="icon" href="${contextPath}/resources/img/iq_systems.ico" />

<!-- Custom fonts for this template-->
<link
	href="${contextPath}/resources/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link href="${contextPath}/resources/vendor/googleFonts/googleFonts.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/bootstrap-datepicker3.min.css" rel="stylesheet">
<link rel="stylesheet" href="${contextPath}/resources/Modules/iziToast/css/iziToast.min.css">
<script src="${contextPath}/resources/Modules/iziToast/js/iziToast.min.js" type="text/javascript"></script>

<script src="${contextPath}/resources/js/jquery.min.js"
	type="text/javascript"></script>
<script src="${contextPath}/resources/js/bootstrap-datepicker.min.js"></script>
<!-- Custom styles for this template-->
<link href="${contextPath}/resources/css/sb-admin-2.css"
	rel="stylesheet">
<script src="${contextPath}/resources/vendor/jquery/jquery.min.js"></script>
<script type="text/javascript">
var contextPath = "${contextPath}";
</script>
</head>

<body id="page-top" onload="startTime()">

	<!-- Page Wrapper -->
	<div id="wrapper">

		<!-- Sidebar -->
		<ul class="navbar-nav bg-gradient-iq sidebar sidebar-dark accordion"
			id="accordionSidebar">

			<!-- Sidebar - Brand -->
			<a
				class="sidebar-brand d-flex align-items-center justify-content-center"
				href="${contextPath}/">
				<div class="sidebar-brand-icon ">
					<i class="fas fa-city"></i>
				</div>
				<div class="sidebar-brand-text mx-3 text-lg">TEBBIQ</div>
			</a>

			<!-- Divider -->
			<hr class="sidebar-divider my-0">

			<!-- Nav Item - Dashboard -->
			<li class="nav-item active"><a class="nav-link"
				href="${contextPath}/"> <i class="fas fa-fw fa-tachometer-alt"></i>
					<span>Dashboard</span></a></li>

			<!-- Divider -->
			<hr class="sidebar-divider">

			<!-- Heading -->
			<div class="sidebar-heading">Services</div>

			<!-- Nav Item - Pages Collapse Menu -->
			<li class="nav-item"><a class="nav-link collapsed" href="#"
				data-toggle="collapse" data-target="#collapseTwo"
				aria-expanded="true" aria-controls="collapseTwo"> <i
					class="fas fa-fw fa-building"></i> <span>Property</span>
			</a>
				<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo"
					data-parent="#accordionSidebar">
					<div class="bg-white py-2 collapse-inner rounded">
						<h6 class="collapse-header">Utilities:</h6>
						<a class="collapse-item" href="property?id=Ground"><i
							class="far fa-fw fa-building "></i>&nbsp&nbspGround Floor</a> <a
							class="collapse-item" href="property?id=3rd"><i
							class="far fa-fw fa-building "></i>&nbsp&nbspThird Floor</a> <a
							class="collapse-item" href="property?id=4th"><i
							class="far fa-fw fa-building "></i>&nbsp&nbspFourth Floor</a> <a
							class="collapse-item" href="property?id=5th"><i
							class="far fa-fw fa-building "></i>&nbsp&nbspFifth Floor</a> <a
							class="collapse-item" href="property?id=6th"><i
							class="far fa-fw fa-building "></i>&nbsp&nbspSixth Floor</a>
					</div>
				</div></li>

			<!-- Nav Item - Utilities Collapse Menu -->
			<li class="nav-item"><a class="nav-link collapsed" href="#"
				data-toggle="collapse" data-target="#collapseUtilities"
				aria-expanded="true" aria-controls="collapseUtilities"> <i
					class="fas fa-fw fa-clipboard-list"></i> <span>Reports</span>
			</a>
				<div id="collapseUtilities" class="collapse"
					aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
					<div class="bg-white py-2 collapse-inner rounded">
						<h6 class="collapse-header">Report Explorer:</h6>
            <a class="collapse-item" href="${contextPath}/reports?id=dailyuseage"><i class="fas fa-fw fa-table"></i>&nbsp&nbspDaily Usage</a>
            <a class="collapse-item" href="${contextPath}/reports?id=customeruseage"><i class="fas fa-fw fa-table"></i>&nbsp&nbspTenant Summary</a>
            <a class="collapse-item" href="${contextPath}/reports?id=facilityuseage"><i class="fas fa-fw fa-table"></i>&nbsp&nbspFacility Summary</a>
					</div>
				</div></li>

			<!-- Nav Item - Charts -->
			<li class="nav-item"><a class="nav-link"
				href="${contextPath}/billing"> <i
					class="fas fa-fw fa-dollar-sign"></i> <span>Billing</span></a></li>

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
				<nav
					class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

					<!-- Sidebar Toggle (Topbar) -->
					<button id="sidebarToggleTop"
						class="btn btn-link d-md-none rounded-circle mr-3">
						<i class="fa fa-bars"></i>
					</button>

					<!-- Topbar Date/time -->
					<div
						class="d-none d-sm-inline-block mr-auto ml-md-3 my-2 my-md-0 mw-100">

						<h1 id="dateTime" class="h3 mb-0 text-gray-800"></h1>
						<!--<a href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i class="fas fa-download fa-sm text-white-50"></i> Generate Report</a>-->

					</div>


					<!-- Topbar Navbar -->
					<ul class="navbar-nav ml-auto">



						<!-- Nav Item - Alerts -->
						<li id="alertsDropdown" class="nav-item dropdown no-arrow mx-1">
						<a	class="nav-link dropdown-toggle" href="#" 
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
						<li class="nav-item dropdown no-arrow"><a
							class="nav-link dropdown-toggle" href="#" id="userDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <span
								class="mr-2 d-none d-lg-inline text-gray-600 small">${pageContext.request.userPrincipal.name}</span>
								<div id="avatar"></div>
						</a> <!-- Dropdown - User Information -->
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
              </div></li>

					</ul>

				</nav>
				<!-- End of Topbar -->

				<!-- Begin Page Content -->
				<div class="container-fluid">

					<!-- Page Heading -->
					<div
						class="d-sm-flex align-items-center justify-content-between mb-4">
						<h1 class="h3 mb-0 text-gray-800">Dashboard</h1>
						<!--<a href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i class="fas fa-download fa-sm text-white-50"></i> Generate Report</a>-->
					</div>

					<!-- Content Row -->
					<div class="row">
						<!-- Electricity Monthly consumption Card -->
						<div class="col-xl-3 col-md-6 mb-4">
							<div class="card border-left-warning shadow h-100 py-2">
								<div class="card-body">
									<div class="row no-gutters align-items-center">
										<div class="col mr-2">
											<div id="prevElecMonth"
												class="text-xs font-weight-bold text-success text-uppercase mb-1"></div>
											<div id="prevElecMonthValue"
												class="h5 mb-0 font-weight-bold text-gray-800"></div>
										</div>
										<div class="col-auto">
											<i class="fas fa-plug fa-2x text-gray-300"></i>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- Electricity Monthly consumption Card -->
						<div class="col-xl-3 col-md-6 mb-4">
							<div class="card border-left-success shadow h-100 py-2">
								<div class="card-body">
									<div class="row no-gutters align-items-center">
										<div class="col mr-2">
											<div id="currElecMonth"
												class="text-xs font-weight-bold text-success text-uppercase mb-1"></div>
											<div id="currElecMonthValue"
												class="h5 mb-0 font-weight-bold text-gray-800"></div>
										</div>
										<div class="col-auto">
											<i class="fas fa-plug fa-2x text-gray-300"></i>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- Water Monthly consumption Card -->
						<div class="col-xl-3 col-md-6 mb-4">
							<div class="card border-left-warning shadow h-100 py-2">
								<div class="card-body">
									<div class="row no-gutters align-items-center">
										<div class="col mr-2">
											<div id="prevAirMonth"
												class="text-xs font-weight-bold text-info text-uppercase mb-1"></div>
											<div id="prevAirMonthValue"
												class="h5 mb-0 font-weight-bold text-gray-800"></div>
										</div>
										<div class="col-auto">
											<i class="fas fa-fan fa-2x text-gray-300"></i>
										</div>
									</div>
								</div>
							</div>
						</div>

						<!-- Water Monthly consumption Card -->
						<div class="col-xl-3 col-md-6 mb-4">
							<div class="card border-left-info shadow h-100 py-2">
								<div class="card-body">
									<div class="row no-gutters align-items-center">
										<div class="col mr-2">
											<div id="currAirMonth"
												class="text-xs font-weight-bold text-info text-uppercase mb-1"></div>
											<div id="currAirMonthValue"
												class="h5 mb-0 font-weight-bold text-gray-800"></div>
										</div>
										<div class="col-auto">
											<i class="fas fa-fan fa-2x text-gray-300"></i>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!--<security:authorize access="hasRole('ROLE_SUPERADMIN')"> -->
						<!--</security:authorize> -->
						<!-- Gas Monthly consumption Card 
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-warning shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">Gas Consumption<br>(June)</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">18,000 m<sup>3</sup></div>
                    </div>
                    <div class="col-auto">
                      <i class="fas fa-burn fa-2x text-gray-300"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div> -->


						<!-- System Health 
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-info shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-info text-uppercase mb-1">System Health</div>
                      <div class="row no-gutters align-items-center">
                        <div class="col-auto">
                          <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">99%</div>
                        </div>
                        <div class="col">
                          <div class="progress progress-sm mr-2">
                            <div class="progress-bar bg-info" role="progressbar" style="width: 99%" aria-valuenow="99" aria-valuemin="0" aria-valuemax="100"></div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-auto">
                      <i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div> -->
					</div>


					<!-- Total energy charts -->
					<div class="row">

						<!-- Area Chart -->
						<div class="col-xl-8 col-lg-7">
							<div class="card shadow mb-4">
								<!-- Card Header - Dropdown -->
								<div
									class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
									<h6 class="m-0 font-weight-bold text-primary">Total Energy
										Consumption</h6>
									<div class="dropdown no-arrow">
										<a class="dropdown-toggle" href="#" role="button"
											id="dropdownMenuLink" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="false"> <i
											class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
										</a>
										<div
											class="dropdown-menu dropdown-menu-right shadow animated--fade-in"
											aria-labelledby="dropdownMenuLink">
											<form class="px-4 py-3">
												<div class="form-group">
													<label for="limit">Span</label> <select
														class="form-control form-control-sm" id="limit">
														<option value="7">7</option>
														<option value="14" selected>14</option>
														<option value="21">21</option>
														<option value="31">31</option>
													</select>
												</div>
												<div class="form-group">
													<label for="start">Start</label>
													<input type="text"
														class="form-control form-control-sm" id="start">
												</div>
												<button type="button" onclick="launch();"
													class="btn btn-primary btn-sm btn-block">Apply</button>
											</form>
										</div>
									</div>
								</div>
								<!-- Card Body -->
								<div class="card-body">
									<div class="chart-area">
										<canvas id="myAreaChart"></canvas>
									</div>
								</div>
							</div>
							<!-- End card body -->
						</div>
						<!-- /.Area Chart -->

						<!-- Pie Chart -->
						<div class="col-xl-4 col-lg-5">
							<div class="card shadow mb-4">
								<!-- Card Header - Dropdown -->
								<div
									class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
									<h6 class="m-0 font-weight-bold text-primary">Highest Energy Demand</h6>
									<!-- 
                  <div class="dropdown no-arrow">
                    <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
                      <div class="dropdown-header">Dropdown Header:</div>
                      <a class="dropdown-item" href="#">Electricity Usage</a>
                      <a class="dropdown-item" href="#">AirCon Usage</a>
                      <div class="dropdown-divider"></div>
                      <a class="dropdown-item" href="#">Change month</a>
                    </div>
                  </div> -->
								</div>
								<!-- Card Body -->
								<div class="card-body">
									<div class="chart-pie pt-4 pb-2">
										<canvas id="myPieChart"></canvas>
									</div>
								</div>
							</div>
						</div>
						<!-- Pie Chart -->
						<!-- <div class="col-xl-4 col-lg-5">
                          Project Card Example
              <div class="card shadow mb-4">
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary">Summary</h6>
                </div>
                <div class="card-body">
                  <h4 class="small font-weight-bold">Transformer load <span class="float-right">60%</span></h4>
                  <div class="progress mb-4">
                    <div class="progress-bar bg-danger" role="progressbar" style="width: 60%" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"></div>
                  </div>
                  <h4 class="small font-weight-bold">Generator load <span class="float-right">0%</span></h4>
                  <div class="progress mb-4">
                    <div class="progress-bar bg-warning" role="progressbar" style="width: 0%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                  </div>
                  <h4 class="small font-weight-bold">Pump load <span class="float-right">60%</span></h4>
                  <div class="progress mb-4">
                    <div class="progress-bar" role="progressbar" style="width: 60%" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"></div>
                  </div>
                  <h4 class="small font-weight-bold">HVAC load <span class="float-right">80%</span></h4>
                  <div class="progress mb-4">
                    <div class="progress-bar bg-info" role="progressbar" style="width: 80%" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"></div>
                  </div>
                  <h4 class="small font-weight-bold">BMS free memory <span class="float-right">30%</span></h4>
                  <div class="progress mb-4">
                    <div class="progress-bar bg-success" role="progressbar" style="width: 30%" aria-valuenow="30" aria-valuemin="0" aria-valuemax="100"></div>
                  </div>
                </div>
              </div>
            </div> -->

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
	<a class="scroll-to-top rounded" href="#page-top"> <i
		class="fas fa-angle-up"></i>
	</a>

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
	<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">Select "Logout" below if you are ready
					to end your current session.</div>
				<div class="modal-footer">
					<form id="logoutForm" method="POST" action="${contextPath}/logout">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<button class="btn btn-secondary" type="button"
							data-dismiss="modal">Cancel</button>
						<input class="btn btn-primary" type="submit" value="Logout"></input>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- Bootstrap core JavaScript-->
	<script src="${contextPath}/resources/vendor/jquery/jquery.min.js"></script>
	<script
		src="${contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script
		src="${contextPath}/resources/vendor/jquery-easing/jquery.easing.min.js"></script>
			<script src="${contextPath}/resources/js/bootstrap-datepicker.min.js"></script>

	<!-- Custom scripts for all pages-->
	<script src="${contextPath}/resources/js/sb-admin-2.min.js"></script>

	<!-- Page level plugins -->
	<script src="${contextPath}/resources/vendor/chart.js/Chart.min.js"></script>
	<script
		src="${contextPath}/resources/vendor/datatables/jquery.dataTables.min.js"></script>
		<script src="${contextPath}/resources/vendor/moment/moment.min.js"></script>
	<script src="${contextPath}/resources/vendor/moment/datetime-moment.js"></script>

	<!-- Page level custom scripts -->
	<script src="${contextPath}/resources/js/demo/chart-area-dashboard.js"></script>
	<script src="${contextPath}/resources/js/demo/chart-pie-dashboard.js"></script>
	<script src="${contextPath}/resources/js/sockjs/sockjs.min.js"></script>
    <script src="${contextPath}/resources/js/stompjs/stomp.min.js"></script>
    <script src="${contextPath}/resources/js/WebSock.js"></script>

</body>

</html>
