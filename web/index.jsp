<%-- 
    Document   : index
    Created on : 2015.06.09., 6:48:43
    Author     : szabon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOAPui Assist WebService</title>
    </head>
    <body>
        <h1>SOAPui Assist WebService</h1>
        <table border="1" cellpadding="5" bgcolor="#b0ffb0">
            <tr>
                <td>
                    <a href="local_file_display?file=c:\proba.txt" >
                        Local textfile lekerese
                    </a>
                </td>
            </tr>
            <tr>
                <td>
                    <a href="local_file_display_from_line?file=c:\proba.txt&fromline=5" >
                        Local textfile lekerese adott sortol
                    </a>
                </td>
            </tr>
            <tr>
                <td>
                    <a href="local_file_display_from_line_to_line?file=c:\proba.txt&fromline=5&toline=10" >
                        Local textfile lekerese adott sortol adott sorig
                    </a>
                </td>
            </tr>
            <tr>
                <td>
                    <a href="os_command_run?command=dir">
                        Command futtatas
                    </a>
                </td>
            </tr>            
        </table> 
    </body>
</html>
