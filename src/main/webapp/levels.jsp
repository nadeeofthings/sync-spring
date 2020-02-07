<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

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
  <link href="${contextPath}/resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="${contextPath}/resources/vendor/googleFonts/googleFonts.css" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="${contextPath}/resources/css/sb-admin-2.css" rel="stylesheet">
<script>
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
</script>
<style type="text/css">
a.custom-card,
a.custom-card:hover {
  color: inherit;
}
.card:hover{
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
            <a class="collapse-item" href="utilities-color.html"><i class="fas fa-fw fa-table"></i>&nbsp&nbspSummary Report</a>
            <a class="collapse-item" href="utilities-border.html"><i class="fas fa-fw fa-table"></i>&nbsp&nbspTenant Report</a>
          </div>
        </div>
      </li>
      
      <!-- Nav Item - Charts -->
      <li class="nav-item">
        <a class="nav-link" href="">
          <i class="fas fa-fw fa-dollar-sign"></i>
          <span>Billing</span></a>
      </li>
      
      

      <!-- Divider -->
      <hr class="sidebar-divider">

      <!-- Heading -->
      <div class="sidebar-heading">
        Settings
      </div>

      <!-- Nav Item - Pages Collapse Menu
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePages" aria-expanded="true" aria-controls="collapsePages">
          <i class="fas fa-fw fa-folder"></i>
          <span>Pages</span>
        </a>
        <div id="collapsePages" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">Login Screens:</h6>
            <a class="collapse-item" href="login.html">Login</a>
            <a class="collapse-item" href="register.html">Register</a>
            <a class="collapse-item" href="forgot-password.html">Forgot Password</a>
            <div class="collapse-divider"></div>
            <h6 class="collapse-header">Other Pages:</h6>
            <a class="collapse-item" href="404.html">404 Page</a>
            <a class="collapse-item" href="blank.html">Blank Page</a>
          </div>
        </div>
      </li>-->

      <!-- Nav Item - Charts -->
      <li class="nav-item">
        <a class="nav-link" href="">
          <i class="fas fa-fw fa-tools"></i>
          <span>System</span></a>
      </li>

      <!-- Nav Item - Tables
      <li class="nav-item">
        <a class="nav-link" href="tables.html">
          <i class="fas fa-fw fa-table"></i>
          <span>Tables</span></a>
      </li> -->

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
            <li class="nav-item dropdown no-arrow mx-1">
              <a class="nav-link dropdown-toggle" href="#" id="alertsDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-bell fa-lg"></i>
                <!-- Counter - Alerts -->
                <!--<span class="badge badge-danger badge-counter ">1+</span>-->
              </a>
              <!-- Dropdown - Alerts -->
              <div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="alertsDropdown">
                <h6 class="dropdown-header">
                  Alerts Center
                </h6>
                <!-- <a class="dropdown-item d-flex align-items-center" href="#">
                  <div class="mr-3">
                    <div class="icon-circle bg-success">
                      <i class="fas fa-thumbs-up text-white"></i>
                    </div>
                  </div>
                  <div>
                    <div class="small text-gray-500">June 12, 2019</div>
                    <span class="font-weight-bold">Recovered from CEB loss! GEN 1 stopped.</span>
                  </div>
                </a> -->
                <!-- <a class="dropdown-item d-flex align-items-center" href="#">
                  <div class="mr-3">
                    <div class="icon-circle bg-info">
                      <i class="fas fa-check-circle text-white"></i>
                    </div>
                  </div>
                  <div>
                    <div class="small text-gray-500">June 12, 2019</div>
                    GEN 1 started!
                  </div>
                </a> -->
                <a class="dropdown-item d-flex align-items-center" href="#">
                  <div class="mr-3">
                    <div class="icon-circle bg-success">
                      <i class="fas fa-exclamation-triangle text-white"></i>
                    </div>
                  </div>
                  <div>
                    <div class="small text-gray-500">February 05, 2020</div>
                    No alerts to display
                  </div>
                </a>
                <a class="dropdown-item text-center small text-gray-500" href="#">Show All Alerts</a>
              </div>
            </li>
				

            <div class="topbar-divider d-none d-sm-block"></div>

            <!-- Nav Item - User Information -->
            <li class="nav-item dropdown no-arrow">
              <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <span class="mr-2 d-none d-lg-inline text-gray-600 small">${pageContext.request.userPrincipal.name}</span>
                <img class="img-profile rounded-circle" src="${contextPath}/resources/img/user.png">
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
        <div class="container-fluid">
			<div class="row">
			<div class="col-xl-2 col-md-6 mb-4">
          <!-- Page Heading -->
          <div class="d-sm-flex align-items-center mb-4">
            <h1 class="h3 mb-0 text-gray-800">${id} floor</h1>
          </div>
          </div>
          <div class="col-xl-4 col-md-6 mb-4">
           <div class="input-group">
              <input type="text" class="form-control border-2" placeholder="Search for office" aria-label="Search" aria-describedby="basic-addon2">
              <div class="input-group-append">
                <button class="btn btn-primary" type="button">
                  <i class="fas fa-search"></i>&nbspSearch
                </button>
              </div>
            </div>
		</div>
		</div>
		<c:choose>
		  <c:when test="${id == 'Ground'}">
	<!-- Content Row -->
        <div class="row">
            <!--2br apartment Card -->
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-gray-900 text-uppercase mb-1">Common Area</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">Common Area</div>
                    </div>
                    <div class="col-auto">
                      <i class="fas fa-home fa-2x text-gray-300"></i>
                    </div>
                  </div>
                  <a href="unit?id=${id}&unit=1" class="stretched-link"></a>
                </div>
              </div>
            </div>
		</div>
          
		  </c:when>
		  <c:otherwise>
		    <%int room=01; for(int i=0;i<1;i++){ %>
          <!-- Content Row -->
        <div class="row">
       		 <%for(int j=0;j<4;j++){%>
            <%if(room % 9 != 0){%>
            <!--2br apartment Card -->
            <div class="col-xl-3 col-md-6 mb-4">
              
              <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-gray-900 text-uppercase mb-1">${id} floor office Area</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">Office-0<%=room%></div>
                    </div>
                    <div class="col-auto">
                      <i class="fas fa-home fa-2x text-gray-300"></i>
                    </div>
                  </div>
                  <a href="unit?id=${id}&unit=<%=room%>" class="stretched-link"></a>
                </div>
              </div>
             
            </div>
			<%}else{%>
           <!-- 3br apartment Card -->
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-warning zoom shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-gray-900 text-uppercase mb-1">Commercial Area</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">EM-01</div>
                    </div>
                    <div class="col-auto">
                      <i class="fas fa-home fa-2x text-gray-300"></i>
                    </div>
                  </div>
                  <a href="apartment?no=<%=room%>" class="stretched-link"></a>
                </div>
              </div>
            </div>
            <%}room++;}%>
			</div>
			<%}%>
		  </c:otherwise>
		</c:choose>
		
			
			


		


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

  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">×</span>
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

  <!-- Page level plugins -->
  <script src="${contextPath}/resources/vendor/chart.js/Chart.min.js"></script>


</body>

</html>
