/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appsnan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author szabon
 */
public class os_command_run extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");

        String command = "";

        if (request.getQueryString() != null) {
            command = request.getParameter("command");
        }

        System.out.println("\ncommand received:" + command);

        //nem lehet siman meghivni a Runtime.exec(command) -ot
        //mert az a szokozt tartalmazo opciokat az exec mogott allo StringTokenizer a szokoznel szettori
        //nem veszi figyelembe a dupla idezojelet es a escape-el szokozt sem
        //ezert sajat tokenizer kellett hasznalni regexp-el megoldva
        //innen van: http://stackoverflow.com/questions/3366281/tokenizing-a-string-but-ignoring-delimiters-within-quotes

        //ez a regex kifejezes keresi a tokeneket
        //ami dupla idezojek kozott van, ott nem veszi figyelembe a szokozoket delimiterkent
        String regex = "\"([^\"]*)\"|(\\S+)";

        //ebbe szamoljuk a tokeneket
        int numOfTokens = 0;

        //token osszeszamolas
        Matcher m = Pattern.compile(regex).matcher(command);
        while (m.find()) {
            if (m.group(1) != null) {
                //System.out.println("Quoted [" + m.group(1) + "]");
                numOfTokens++;
            } else {
                //System.out.println("Plain [" + m.group(2) + "]");
                numOfTokens++;
            }
        }

        //tokenszamnak megfelelo String array definialasa
        String[] commandArray = new String[numOfTokens];
        System.out.println("Number of Executable and Options (tokens) in the given command line altogether: " + numOfTokens + "\nTokens found in command:");

        numOfTokens = 0;

        //String array feltoltese a tokenekkel
        m = Pattern.compile(regex).matcher(command);
        while (m.find()) {
            if (m.group(1) != null) {
                System.out.println("Quoted [" + m.group(1) + "]");
                commandArray[numOfTokens] = m.group(1);
                numOfTokens++;
            } else {
                System.out.println("Plain [" + m.group(2) + "]");
                commandArray[numOfTokens] = m.group(2);
                numOfTokens++;
            }
        }

        PrintWriter out = response.getWriter();

        Runtime rt = Runtime.getRuntime();
        try {
//            Process pr = rt.exec(command);    //a command egy sorba nem jó mert szóközöknél széttöri a dupla idezojelben levo opciokat
            Process pr = rt.exec(commandArray); //a saját parserrel tokenizált String tömböt kell használni
            InputStream is = pr.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String outputline;

            out.println("\n--Command requested--\n");
            out.println(command);

            out.println("\n--Command request tokenized--\n");
            for (String s : commandArray) {
                out.println(s);
            }

            out.println("\n--Standard output--\n");

            while ((outputline = br.readLine()) != null) {
                out.println(outputline);
            }

            is = pr.getErrorStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            out.println("\n--Error output--\n");

            while ((outputline = br.readLine()) != null) {
                out.println(outputline);
            }
        } catch (IOException e) {
            out.println("\nError:\n" + e.toString());
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
            throws ServletException, IOException {
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
            throws ServletException, IOException {
        processRequest(request, response);
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
