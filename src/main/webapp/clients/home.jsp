<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@page import="gym.model.Client" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Gym Client Management</title>
    <link rel="shortcut icon" type="image/x-icon" href="favicon.png">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>.table-wrapper-2 {
        display: block;
        max-height: 300px;
        overflow-y: auto;
        -ms-overflow-style: -ms-autohiding-scrollbar;
    }

    tr:nth-child(even) {
        background-color: #f2f2f2
    }
    </style>

    <script type="text/javascript">
        function runScript(e) {
            if (e.keyCode == 13) {

                var tb = document.getElementById("searchInput");
                window.location.assign(removeParam('search', window.location.href) +
                    (location.href.indexOf("?") == -1 ? "?" : "&") + "search=" + encodeURIComponent(tb.value)
                );
                return false;
            }

            return true;
        }

        function removeParam(name, _url) {
            var reg = new RegExp("((&)*" + name + "=([^&]*))", "g");
            return _url.replace(reg, '');
        }
    </script>

</head>
<body>

<style>
    .jumbotron {
        background-color: #f4511e; /* Orange */
        color: #ffffff;
    }
</style>
<div class="jumbotron text-center">

    <a style="float:right; position: absolute; top: 5px; right: 15px;" href="login?logout" style="color:black"><h4>
        Logout</h4></a>

    <h1>GYM Manager</h1>
    <p>Keep it organized</p>

</div>
</div>
</form>
</div>

<%!
    String getOrder(String currentSortBy, String currentOrder, String thisParam, Boolean forIcon) {
        String order = thisParam.equals(currentSortBy) ? currentOrder : "asc";
        return forIcon ? order : "asc".equals(order) ? "desc" : "asc";
    }

    String getSearchParam(String search) throws java.io.UnsupportedEncodingException {
        if (search != null && search.trim().length() > 0) {
            return "&search=" + java.net.URLEncoder.encode(search, "UTF-8");
        } else {
            return "";
        }
    }
%>
<%
    String sortBy = request.getParameter("by");
    String order = request.getParameter("order");

%>
<%
    String search = request.getParameter("search");
%>
<div class="container-fluid text-center">
    <br>
    <div class="row">
        <span class="glyphicon glyphicon-user"></span>
        <a href="clients?action=new" style="color:black">Add Client</a>

        &nbsp;<span class="glyphicon glyphicon-download"></span>
        <a target="_blank" href="clients?action=download<%= sortBy != null ? "&by=" + sortBy : "" %><%= order != null ? "&order=" + order : ""%><%=getSearchParam(search)%>" style="color:black">PDF Export</a>
    </div>
</div>

<!-- Search form -->
<form class="form-inline" style="margin-left: 10px;">
    <input id="searchInput" class="form-control" type="text" placeholder="Search" aria-label="Search"
           onkeypress="return runScript(event)" value="<%= search != null ? search : ""%>" autofocus>
</form>

<div class="card">
    <div class="card-body">

        <div class="table-wrapper-2">

            <!--Table-->
            <table class="table table-responsive-md">
                <thead class="mdb-color lighten-4">

                <tr style="font-weight: bold">
                    <td>ID</td>
                    <td>
                        <a href="clients?by=name&order=<%= getOrder(sortBy, order, "name", false) + getSearchParam(search) %>">Name
                            <span class="glyphicon glyphicon-chevron-<%= "asc".equals(getOrder(sortBy, order, "name", true)) ? "up" : "down" %>"></span></a>
                    </td>
                    <td>Gender</td>
                    <td>Phone</td>
                    <td>
                        <a href="clients?by=validation_expire_date&order=<%= getOrder(sortBy, order, "validation_expire_date",false) + getSearchParam(search)%>">Pass
                            Expire Date <span
                                    class="glyphicon glyphicon-chevron-<%= "asc".equals(getOrder(sortBy, order, "validation_expire_date", true)) ? "up" : "down" %>"></span></a>
                    </td>
                    <td>Pass type</td>
                    <td>
                        <a href="clients?by=registered_on&order=<%= getOrder(sortBy, order, "registered_on",false) + getSearchParam(search) %>">Registered
                            on <span
                                    class="glyphicon glyphicon-chevron-<%= "asc".equals(getOrder(sortBy, order, "registered_on", true)) ? "up" : "down" %>"></span></a>
                    </td>
                    <td>Edit</td>
                    <td>Delete</td>
                </tr>
                </thead>
                <tbody>

                <%
                    java.util.List<Client> clients = (java.util.List) request.getAttribute("clients");
                    if (clients != null) {
                        for (Client c : clients) {
                %>
                <tr>
                    <td><%=c.getId()%>
                    </td>
                    <td><%=c.getName()%>
                    </td>
                    <td><%=c.getGender()%>
                    </td>
                    <td><%=c.getPhoneNumber()%>
                    </td>
                    <td><%=c.getValidationExpireDate()%>
                    </td>
                    <td><%=c.getPass()%>
                    </td>
                    <td><%=c.getRegisteredOn()%>
                    </td>
                    <td>
                        <a href="clients?id=<%=c.getId()%>&action=edit">Edit</a>
                    </td>
                    <td>
                        <a href="clients?id=<%=c.getId()%>&action=delete">Delete</a>
                    </td>
                </tr>

                <%
                        }
                    }

                %>
                </tbody>
            </table>
            <!--Table-->

        </div>
    </div>
</div>
<br>
</body>
</html>