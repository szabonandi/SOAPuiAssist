/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appsnan;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author szabon
 */
public class local_file_display_new_lines extends HttpServlet
  {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
      {
        response.setContentType("text/text;charset=UTF-8");

        String filename = "";

        if (request.getQueryString() != null)
          {
            filename = request.getParameter("file");
          }

        PrintWriter out = response.getWriter();

        Integer lastLinePos = 0;

        HashMap<String, Integer> fileLinePosMap = (HashMap<String, Integer>) request.getServletContext().getAttribute("fileLinePosMap");

        if (fileLinePosMap == null)
          {
            lastLinePos = 0;
            fileLinePosMap = new HashMap<>();
          }
        else
          {
            if (fileLinePosMap.containsKey(filename))
              {
                lastLinePos = fileLinePosMap.get(filename);
              }
          }

        BufferedReader bufferedReader;

        try
          {
            FileReader fr = new FileReader(filename);
            bufferedReader = new BufferedReader(fr);
          } catch (FileNotFoundException e)
          {
            out.println("ERROR: file not found: " + filename);
            return;
          }

        String line = "";
        Integer actualLinePos = 0;

        while (actualLinePos < lastLinePos)
          {
            line = bufferedReader.readLine();
            if (line == null)
              {
                out.println("No new lines in: " + filename);
                return;
              }
            actualLinePos++;
          }

        out.println("New lines in: " + filename + "\n");

        while (true)
          {
            out.println(line);
            actualLinePos++;
            line = bufferedReader.readLine();
            if (line == null)
              {
                fileLinePosMap.put(filename, actualLinePos);
                request.getServletContext().setAttribute("fileLinePosMap", fileLinePosMap);
                return;
              }
          }
      }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
      {
        processRequest(request, response);
      }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
      {
        processRequest(request, response);
      }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
      {
        return "Short description";
      }// </editor-fold>

  }
