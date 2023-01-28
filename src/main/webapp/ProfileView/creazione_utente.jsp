<%--
  Created by IntelliJ IDEA.
  User: tommasosorrentino
  Date: 28/01/23
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Crea utente</title>
  <link rel="stylesheet" href="required_checkbox.css">
</head>
<body>


<p>${error_message}</p>

  <form name= "form" action="${pageContext.request.contextPath}/UserCreateServlet" method="post">
    <p>Crea utente</p>
    <hr>

    <label for="username"><b>Username</b></label>
    <input type="text" placeholder="Campo obbligatorio" name="username" id="username" required>
    <br>
    <p class="password"></p>
    <label for="password"><b>Password</b></label>
    <input type="password"  placeholder="Campo obbligatorio" name="password" id="password" required>
    <br>

    <div>
      <label for="r1"><b>USER MANAGER</b></label>
      <input type="checkbox" name="roles" id="r1"  value= "USER_MANAGER">
      <label for="r2"><b>ORDER MANAGER</b></label>
      <input type="checkbox" name="roles" id="r2"  value= "ORDER_MANAGER">
      <label for="r2"><b>CATALOG MANAGER</b></label>
      <input type="checkbox" name="roles" id= "r3" value= "CATALOG_MANAGER">
    </div>
    <br>
    <output id='req'>Scegli almeno una checkbox.</output>

    <input type="submit" id="checkBtn">
  </form>


</body>

<script type="text/javascript">

  let form = document.forms['form']
  form.onsubmit = validate;
  function validate(e) {
    e.preventDefault();
    let ui = this.elements;
    let chx = Array.from(ui.roles);
    for (let c of chx) {
      if (c.checked) {
        this.submit();
      }
    }
    ui.req.style.display = 'block';
  }

</script>
</html>
