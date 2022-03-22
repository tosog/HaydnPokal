package at.schnedl.ues.hp;

import java.io.IOException;
import java.net.ConnectException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Servlet implementation class Display
 */
@WebServlet("/display.json")
public class Display extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Display() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String url = request.getParameter("url");
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			WebReader wr = new WebReader(url);
			wr.readWeb();
					
			gson.toJson(wr.getCategories(), response.getWriter());
		}
		catch (ConnectException e) {
			gson.toJson(e.getMessage(), response.getWriter());
		}
		
		
	}

}
