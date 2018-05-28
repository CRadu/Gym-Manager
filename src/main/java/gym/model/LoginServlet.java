package gym.model;

import gym.persistence.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Login Servlet - validating user name and password and redirecting to clients page.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("logout") != null) {
            request.getSession().removeAttribute("user");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else if (request.getSession().getAttribute("user") != null) {
            response.sendRedirect("clients");
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Map<String, String> messages = new HashMap<>();

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            messages.put("username", "Please enter both username and password.");
        }

        if (messages.isEmpty()) {
            User user = UserManager.getUser(username, password);
            if (user != null) {
                request.getSession().setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/clients");
                return;
            } else {
                messages.put("login", "Unknown login, please try again.");
            }
        }

        request.setAttribute("messages", messages);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

}