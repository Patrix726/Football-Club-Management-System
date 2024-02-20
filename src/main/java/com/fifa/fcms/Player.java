/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.fifa.fcms;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author PATRIX
 */

public class Player extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.addHeader("Content-Type", "text/html");

        ObjectMapper objMapper = new ObjectMapper();
        System.out.println(request.getParameter("playerId"));
        String json = objMapper
                .writeValueAsString(Utils.getPlayer(Integer.parseInt(request.getParameter("playerId"))));
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
        HttpSession session = request.getSession();
        Cookie cookies[] = request.getCookies();
        boolean valid = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName() == "username") {
                    valid = true;
                }
            }

        }
        if (session.getAttribute("username") != null || valid) {
            if (request.getParameter("action") == null) {
                processPost(request, response);
            } else {
                processDelete(request, response);
            }
        } else {
            response.sendRedirect("/pages/login");
        }

    }

    protected void processPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int playerId = Integer.parseInt(request.getParameter("playerId"));
        int clubId = Integer.parseInt(request.getParameter("clubId"));
        int salary = Integer.parseInt(request.getParameter("salary"));
        int jerseyNo = Integer.parseInt(request.getParameter("jerseyNo"));
        long contractEndDate = Long.parseLong(request.getParameter("contractEndDate"));

        boolean success = Utils.transferPlayer(playerId, clubId, salary, jerseyNo, contractEndDate);
        if (success) {
            response.sendRedirect("/pages/club?clubId=" + clubId);
        } else {
            response.sendRedirect("/pages/player?playerId=" + playerId);

        }
    }

    protected void processDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        int playerId = Integer.parseInt(request.getParameter("playerId"));
        boolean success = Utils.deletePlayer(playerId);

        if (success) {
            String redirectUrl = "/pages/players";
            response.sendRedirect(redirectUrl);
        } else {
            String redirectUrl = "/pages/player?playerId=" + playerId;
            response.sendRedirect(redirectUrl);
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
