package gym.model;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import gym.persistence.ClientManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Controller servlet for:
 * - adding new client
 * - editing client data
 * - deleting client
 * - searching for clients
 * - sorting and ordering clients in table
 * - generating clients report
 */
@WebServlet("/clients")
public class ClientControllerServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(ClientControllerServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        String sortBy = req.getParameter("by");
        String order = req.getParameter("order");
        String search = req.getParameter("search");
        if (logger.isDebugEnabled()) {
            logger.debug("Processing action: " + action);
        }

        if (action != null) {

            if ("new".equals(action)) {
                getServletContext().getRequestDispatcher("/clients/form.jsp").forward(req, resp);
            } else if ("download".equals(action)) {
                List<Client> clients = ClientManager.selectAll(sortBy, order, search);
                OutputStream out = resp.getOutputStream();
                export(out, clients);
                out.flush();
            } else {
                Long id = Long.valueOf(req.getParameter("id"));
                if ("delete".equals(action)) {
                    ClientManager.remove(id);
                    resp.sendRedirect("clients");
                } else if ("edit".equals(action)) {
                    req.setAttribute("client", ClientManager.getClient(id));
                    getServletContext().getRequestDispatcher("/clients/form.jsp").forward(req, resp);
                }
            }
        } else {

            req.setAttribute("clients", ClientManager.selectAll(sortBy, order, search));
            getServletContext().getRequestDispatcher("/clients/home.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DoPost started");
        String action = request.getParameter("action");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String phoneNo = request.getParameter("phone");
        String pass = request.getParameter("pass");
        List<String> errors = new ArrayList<>();
        Date date = null;
        java.sql.Date sqlDate = null;
        try {
            date = new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("validationExpireDate"));
        } catch (ParseException e) {
            logger.error("Unable to parse data out of " + request.getParameter("validationExpireDate"), e);
        }
        // validations for fields input

        if (name == null || name.equals("") || name.length() < 4 || !name.matches("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$")) {
            errors.add("Name not valid!");
        }
        if (phoneNo.length() < 10 || phoneNo.length() > 10 || !phoneNo.matches("[0-9]+$")) {
            errors.add("Phone number is not valid!");
        }

        if (date == null) {
            errors.add("Validation expire date error!");
        } else {
            sqlDate = new java.sql.Date(date.getTime());
        }
        System.out.println("name " + name + ", gender " + gender + ", phone " + phoneNo + ", validation expire date " + sqlDate + ", pass" + pass);

        Client c = new Client(null, name, Client.Gender.valueOf(gender), phoneNo, sqlDate, Client.SubscriptionType.valueOf(pass), null);
        if ("update".equals(action)) {
            c.setId(Long.valueOf(request.getParameter("id")));
        }

        request.setAttribute("errors", errors);

        if (errors.size() > 0) {
            request.setAttribute("client", c);
            getServletContext().getRequestDispatcher("/clients/form.jsp").forward(request, response);
            return;
        }

        if ("insert".equals(action)) {
            ClientManager.add(c);
        } else if ("update".equals(action)) {
            ClientManager.update(c);
        }
        response.sendRedirect("clients");
        response.setContentType("text/html");
    }

    private final void export(OutputStream out, List<Client> clientsToExport) {


        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();
            document.add(new Paragraph("Gym Clients Report\n\nCreated on: " + new Date() + "\n\n\n"));
            PdfPTable table = new PdfPTable(3); // 3 columns.
            table.setWidthPercentage(100); //Width 100%
            table.setSpacingBefore(10f); //Space before table
            table.setSpacingAfter(10f); //Space after table

            //Set Column widths
            float[] columnWidths = {1f, 1f, 1f};
            table.setWidths(columnWidths);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Name"));
            cell1.setPaddingLeft(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell2 = new PdfPCell(new Paragraph("Phone"));
            cell2.setPaddingLeft(10);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell3 = new PdfPCell(new Paragraph("Registered On"));
            cell3.setPaddingLeft(10);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.completeRow();


            for (Client client : clientsToExport) {
                table.addCell(new PdfPCell(new Paragraph(client.getName())));
                table.addCell(new PdfPCell(new Paragraph(client.getPhoneNumber())));
                table.addCell(new PdfPCell(new Paragraph(client.getRegisteredOn().toString())));
                table.completeRow();
            }

            document.add(table);

            document.addAuthor("Gym Manager");
            document.addCreationDate();
            document.addCreator("App");
            document.addTitle("Gym Clients");

            document.close();
            writer.close();
        } catch (DocumentException e) {
            logger.error("Unable to create pdf export document.", e);
        }
    }


}



