package io.keikai.tutorial.web;

import io.keikai.tutorial.Configuration;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * accept "server" parameter e.g. http://localhost:8080?server=10.1.1.1:8888
 */
public class BaseServlet extends HttpServlet {
    protected String keikaiServerAddress = Configuration.DEFAULT_KEIKAI_SERVER;
    protected File defaultFileFolder;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        defaultFileFolder = new File(getServletContext().getRealPath(Configuration.getDefaultFileFolder()));
//        Configuration.enableSocketIOLog();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        determineServerAddress(req);
        avoidResponseCache(response);
    }

    private void avoidResponseCache(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.
    }

    /**
     * determine Keikai server address according to the query string
     * @param request
     */
    protected void determineServerAddress(ServletRequest request) {
        String serverAddress = request.getParameter("server");
        if (serverAddress != null){
            keikaiServerAddress = "http://" + serverAddress;
        }
    }
}
