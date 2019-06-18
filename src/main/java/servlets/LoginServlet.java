package servlets;

import dao.blueprint.IAuthUserDAO;
import models.AuthUser;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Stateless
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    IAuthUserDAO authUserDAO;

    public LoginServlet() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");

        AuthUser authUser = new AuthUser();

        authUser.setUserName(userName);
        authUser.setPassword(password);

        try {
            String userValidate = authUserDAO.getUserRole(authUser);

            if (userValidate != null) {
                HttpSession session = request.getSession();
                switch (userValidate) {
                    case "USER":
                        System.out.println("User's Home");

                        session.setMaxInactiveInterval(10 * 60);
                        session.setAttribute("User", userName);
                        request.setAttribute("userName", userName);

                        request.getRequestDispatcher("/JSP/User.jsp").forward(request, response);
                        break;
                    case "ADMIN":
                        System.out.println("Admin's Home");

                        session.setAttribute("Admin", userName); //setting session attribute
                        request.setAttribute("userName", userName);
                        request.setAttribute("users", authUserDAO.getAll());

                        request.getRequestDispatcher("/JSP/Admin.jsp").forward(request, response);
                        break;
                }
            } else {
                System.out.println("No AuthUser found with username: " + userName + " and given password");
                request.setAttribute("errMessage", "No AuthUser found with username: " + userName + " and given password");

                request.getRequestDispatcher("/JSP/Login.jsp").forward(request, response);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    } //End of doPost()
}
