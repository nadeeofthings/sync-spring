<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>TEBBIQ - 404</title>
  <link rel="icon" href="img/iq_systems.ico" />

  <!-- Custom fonts for this template
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="vendor/googleFonts/googleFonts.css" rel="stylesheet">-->
  
  <!-- Custom styles for this template-->
  <link href="${contextPath}/resources/css/sb-admin-2.css" rel="stylesheet">

</head>

<body  id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">

    

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

       

        <!-- Begin Page Content -->
        <div class="container-fluid">

          <!-- 404 Error Text -->
          <div class="text-center">
            <div class="error mx-auto" data-text="${error}">${error}</div>
            <p class="lead text-gray-800 mb-5">${errorText}</p>
            <p class="text-gray-500 mb-0">It looks like you found a glitch in the matrix...</p>
            <a href="${contextPath}/dashboard">&larr; Back to Dashboard</a>
          </div>

        </div>
        <!-- /.container-fluid -->

      </div>
      <!-- End of Main Content -->

      <!-- Footer -->
      <footer class="sticky-footer bg-gray">
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

  <!-- Bootstrap core JavaScript
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>-->

  <!-- Core plugin JavaScript
  <script src="vendor/jquery-easing/jquery.easing.min.js"></script>-->

  <!-- Custom scripts for all pages
  <script src="js/sb-admin-2.min.js"></script>-->

</body>

</html>
