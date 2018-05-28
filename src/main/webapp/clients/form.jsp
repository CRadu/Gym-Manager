<%@page import="gym.model.Client" %>
<%@page import="java.util.List" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.lang.String" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Gym Client Management</title>
    <link rel="shortcut icon" type="image/x-icon" href="favicon.png"/>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.css" rel="stylesheet"/>

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker3.standalone.min.css"/>

</head>
<body>

<style>
    .jumbotron {
        background-color: #f4511e; /* Orange */
        color: #ffffff;
    }

</style>
<div class="jumbotron text-center">

    <h1>GYM Manager</h1>
    <p>Keep it organized</p>

</div>
<div>
    <%
        Client c = (Client) request.getAttribute("client");
        List<String> errors = (List<String>) request.getAttribute("errors");
        boolean isAdd = (c == null || (c != null && c.getId() == null));
    %>

    <h2 style="text-align: center;"><%= isAdd ? "Add" : "Update"%> Client</h2>

</div>
<div style="width:800px; margin:0 auto;">

    <form role="form" action="clients" method="post">
        <input type="hidden" name="action" value="<%= isAdd ? "insert" : "update"%>">
        <%
            if (c != null) {
        %>
        <input type="hidden" name="id" value="<%= c.getId()%>">
        <%
            }
        %>

        <div class="form-group">

            <label class="control-label">Name</label>
            <input type="text" class="form-control" id="name" name="name" value="<%= c == null ? "" : c.getName()%>">
        </div>
        <div class="form-group">
            <label>Gender</label>
            <select class="form-control" id="gender" name="gender" value="<%= c == null ? "" : c.getGender().toString()%>">
                <option>MALE</option>
                <option>FEMALE</option>
            </select>
        </div>
        <div class="form-group">
            <label>Pass Type</label>
            <select class="form-control" id="pass" name="pass">
                <option>FITNESS</option>
                <option>AEROBIC</option>
                <option>FULL_ACCESS</option>
            </select>
        </div>
        <div class="form-group">
            <label>Phone Number</label>
            <input type="text" class="form-control" id="phone" name="phone"
                   value="<%= c == null ? "" : c.getPhoneNumber()%>">
        </div>
        <div>
            <label class="control-label">Validation Expire Date</label>
            <script
                    src="https://code.jquery.com/jquery-2.2.4.min.js"
                    integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="
                    crossorigin="anonymous"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/js/bootstrap-datepicker.min.js"></script>

            <div class="form-group">
                <div class="input-group date" data-provide="datepicker">
                    <input type="text" class="form-control" name="validationExpireDate"
                           value="<%= c == null || c.getValidationExpireDate() == null ? "" : new SimpleDateFormat("MM/dd/yyyy").format(c.getValidationExpireDate())%>">
                    <div class="input-group-addon">
                        <span class="glyphicon glyphicon-th"></span>
                    </div>

                </div>
            </div>
        </div>
        <%
            if (errors != null && errors.size() > 0) {
                out.print("<div text-align:center text-color:white style=\"background-color:red\">");
                for (String error : errors) {
                    out.print(error + "<br>");
                }
                out.print("</div>");
            }
        %>

        <input type="submit" class="btn btn-default" value="Submit">
        <input type="button" class="btn btn-default" value="Cancel" onclick="window.location.href='clients';">

    </form>

</div>
<br>


</body>
</html>