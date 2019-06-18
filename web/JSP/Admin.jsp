<%@ page import="java.util.List" %>
<%@ page import="models.AuthUser" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Admin Page</title>
    </head>
    <% //In case, if Admin session is not set, redirect to Login page
    if((request.getSession(false).getAttribute("Admin")== null) )
    {
    %>
    <jsp:forward page="/JSP/Login.jsp"></jsp:forward>
    <%} %>
    <body>
        <center><h2>Admin's Home</h2></center>
        Welcome <%=request.getAttribute("userName") %><br><br><br>
        <table align="center">
        <% for (AuthUser user:(List<AuthUser>)request.getAttribute("users")) {%>
            <tr>
                <td>
                    <label><%=user.getUserName()%></label>
                    <form name="form" action="http://localhost:8080/AuthenticationApp-1.0/RemoveAuthUserServlet" method="post">
                        <input type="submit" value="Remove"/>
                        <input type="hidden" name="authusername" value="<%=user.getUserName()%>">
                    </form>
                </td>
            </tr>
        <%}%>
        </table>
        <div style="text-align: right"><a href="<%=request.getContextPath()%>/LogoutServlet">Logout</a></div>
    </body>
</html>
