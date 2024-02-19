/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.fifa.fcms;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author PATRIX
 */

public class Players extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.addHeader("Content-Type", "text/html");

        ObjectMapper objMapper = new ObjectMapper();
        System.out.println(request.getParameter("clubId"));
        String json = objMapper.writeValueAsString(Utils.getPlayers(Integer.parseInt(request.getParameter("clubId"))));
        System.out.println(json);
        PrintWriter out = response.getWriter();
        out.write(json);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        long dateOfBirth = Long.parseLong(request.getParameter("dateOfBirth"));
        String nationality = request.getParameter("nationality");
        String position = request.getParameter("position");
        float salary = Float.parseFloat(request.getParameter("salary"));
        int clubId = Integer.parseInt(request.getParameter("clubId"));
        int jerseyNo = Integer.parseInt(request.getParameter("jerseyNo"));
        long contractEndDate = Long.parseLong(request.getParameter("contractEndDate"));

        boolean success = Utils.registerPlayer(fname, lname, dateOfBirth, nationality, position, salary, clubId,
                jerseyNo, contractEndDate);
        if (success) {
            response.sendRedirect("/pages/club?clubId=" + clubId);
        } else {
            response.sendRedirect("/pages/players");

        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
