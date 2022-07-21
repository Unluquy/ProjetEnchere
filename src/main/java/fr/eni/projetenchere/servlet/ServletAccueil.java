package fr.eni.projetenchere.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletAccueil", value = "/ServletAccueil")
public class ServletAccueil extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
        rd.forward(request, response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if(request.getParameter("disconnect") != null){
            session.setAttribute("pseudoUser", null);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
            rd.forward(request, response);
        }
    }

}
