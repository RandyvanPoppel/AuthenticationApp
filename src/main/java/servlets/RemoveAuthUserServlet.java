package servlets;

import dao.blueprint.IAuthUserDAO;
import models.AuthUser;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Stateless
public class RemoveAuthUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    IAuthUserDAO authUserDAO;

    public RemoveAuthUserServlet() {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String adminString = (String) request.getSession(false).getAttribute("Admin");
        if (adminString != null)
        {
            String authUserName = request.getParameter("authusername");
            AuthUser authUserToDelete = authUserDAO.findAuthUserByUsername(authUserName);
            authUserDAO.removeAuthUser(authUserToDelete);
            request.setAttribute("Admin", adminString);
            request.setAttribute("users", authUserDAO.getAll());
            try {
                request.getRequestDispatcher("/JSP/Login.jsp").forward(request, response);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
    } //End of doPost()
}
