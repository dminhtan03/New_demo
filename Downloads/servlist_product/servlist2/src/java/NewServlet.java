
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Product;
import exceptions.DuplicateIdException;
import jakarta.servlet.http.HttpSession;
import java.util.Iterator;

/**
 *
 * @author ASUS
 */
@WebServlet(urlPatterns = {"/list"})
public class NewServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        session.setAttribute("action", action);

        if (action.equals("add")) {
            handleAdd(request, response);
        }

        if (action.equals("delete")) {
            handleDelete(request, response);
        }

        if (action.equals("edit")) {
            handleEdit(request, response);
        }

        if (action.equals("home")) {
            handleHome(request, response);
        }

        if (action.equals("list")) {
            handleList(request, response);
        }

        if (action.equals("update")) {
            handleUpdate(request, response);
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

    private void handleAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String provider = request.getParameter("provider");
        String priceStr = request.getParameter("price");

        request.setAttribute("err", "");
        HttpSession session = request.getSession();
        List<Product> products = (List<Product>) session.getAttribute("data");
        if (products == null) {
            products = new ArrayList<>();
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);

            for (Product p : products) {
                if (p.getId().equals(id)) {
                    throw new DuplicateIdException("The input ID is used!");
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("err", "Price is not double!");
            session.setAttribute("data", products);
            request.getRequestDispatcher("home.jsp").forward(request, response);
            return;
        } catch (DuplicateIdException e) {
            request.setAttribute("err", e.getMessage());
            session.setAttribute("data", products);
            request.getRequestDispatcher("home.jsp").forward(request, response);
            return;
        }

        Product newProduct = new Product(id, name, provider, price);
        products.add(newProduct);
        session.setAttribute("data", products);
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("err", "");
        HttpSession session = request.getSession();
        List<Product> products = (List<Product>) session.getAttribute("data");
        String id = request.getParameter("id");
        Iterator<Product> iterator = products.iterator();

        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getId().equals(id)) {
                iterator.remove();
                break;
            }
        }

        session.setAttribute("data", products);
        request.getRequestDispatcher("list.jsp").forward(request, response);
    }

    private void handleEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("err", "");
        request.getRequestDispatcher("list.jsp").forward(request, response);
    }

    private void handleHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("err", "");
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    private void handleList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("err", "");
        request.getRequestDispatcher("list.jsp").forward(request, response);
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("err", "");
        String id = request.getParameter("new_id");
        String name = request.getParameter("new_name");
        String provider = request.getParameter("new_provider");
        String priceStr = request.getParameter("new_price");

        String curID = request.getParameter("id");

        HttpSession session = request.getSession();
        List<Product> products = (List<Product>) session.getAttribute("data");
        if (products == null) {
            products = new ArrayList<>();
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);

            for (Product p : products) {
                if (p.getId().equals(id) && !p.getId().equals(curID)) {
                    throw new DuplicateIdException("The input ID is used!");
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("err", "Price is not double!");
            session.setAttribute("data", products);
            session.setAttribute("action", "edit");
            request.getRequestDispatcher("list.jsp").forward(request, response);
            return;
        } catch (DuplicateIdException e) {
            request.setAttribute("err", e.getMessage());
            session.setAttribute("data", products);
            session.setAttribute("action", "edit");
            request.getRequestDispatcher("list.jsp").forward(request, response);
            return;
        }

        for (Product p : products) {
            if (p.getId().equals(curID)) {
                p.setId(id);
                p.setName(name);
                p.setPrice(price);
                p.setProvider(provider);
            }
        }

        session.setAttribute("data", products);
        request.getRequestDispatcher("list.jsp").forward(request, response);
    }
}
