<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="utf-8">
        <title>Request an account</title>
  		<link rel="icon" href="${contextPath}/resources/img/iq_systems.ico" />
      

  <!-- Custom fonts for this template-->
  <link href="${contextPath}/resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="${contextPath}/resources/vendor/googleFonts/googleFonts.css" rel="stylesheet">
  
  <!-- Custom styles for this template-->
  <link href="${contextPath}/resources/css/sb-admin-2.css" rel="stylesheet">
  </head>

  <body class="bg-gradient-iq">


  <div class="container">

    <div class="card o-hidden border-0 shadow-lg my-5">
      <div class="card-body p-0">
        <!-- Nested Row within Card Body -->
        <div class="row">
          <div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
          <div class="col-lg-7">
            <div class="p-5">
              <div class="text-center">
                <h1 class="h4 text-gray-900 mb-4">Request an Account!</h1>
              </div>
              <form:form method="POST" modelAttribute="userForm" class="form-signin user">
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <spring:bind path="firstname">
                    <form:input type="text" path="firstname" class="form-control form-control-user" autofocus="true" placeholder="First Name"></form:input>
                    </spring:bind>
                  </div>
                  <div class="col-sm-6">
                  <spring:bind path="lastname">
                    <form:input type="text" path="lastname" class="form-control form-control-user"  placeholder="Last Name"></form:input>
                  </spring:bind>
                  </div>
                </div>
                <div class="form-group">
                  <spring:bind path="department">
                  	<form:input type="text" path="department"  class="form-control form-control-user"  placeholder="Department"></form:input>
                  </spring:bind>
                </div>
                <div class="form-group">
                  <spring:bind path="username">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                  	<form:input type="text" path="username"  class="form-control form-control-user" placeholder="Email Address"></form:input>
                  	<form:errors path="username"></form:errors>
                  </div>
                  </spring:bind>
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                  <spring:bind path="password">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="password" path="password" class="form-control form-control-user" placeholder="Password"></form:input>
                    <form:errors path="password"></form:errors>
                                    </div>
            </spring:bind>
                  </div>
                  <div class="col-sm-6">
                              <spring:bind path="passwordConfirm">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="password" path="passwordConfirm" placeholder="Confirm your password" class="form-control form-control-user"></form:input>
                     <form:errors path="passwordConfirm"></form:errors>
                                  </div>
            </spring:bind>
                  </div>
                </div>
                <button type="submit" class="btn btn-primary btn-user btn-block">
                  Register Account
                </button>
              </form:form>
              <hr>
              <div class="text-center">
                <a class="small" href="${contextPath}/login">Already have an account? Login!</a>
              </div>
            </div>
          </div>
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
  </body>
</html>