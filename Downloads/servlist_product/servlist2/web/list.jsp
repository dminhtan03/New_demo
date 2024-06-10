<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="stylelist.css">
    </head>
    <body>
        <h2>List Product</h2>
        <table class='product_table'>
            <tr>
                <th>Product's ID</th>
                <th>Product's Name</th>
                <th>Provider</th>
                <th>Unit's Price</th>
                <th colspan='2'><a href="list?action=home">Insert New</a></th> 
            </tr>

            <c:forEach var="product" items="${sessionScope.data}">
                <c:if test="${sessionScope.action eq 'edit' && param.id eq product.id}">
                    <form action='list?action=update&id=${product.id}' method='post'>
                        <tr>
                            <td><input type='text' name='new_id' value='${product.id}'></td>
                            <td><input type='text' name='new_name' value='${product.name}'></td>
                            <td><input type='text' name='new_provider' value='${product.provider}'></td>
                            <td><input type='text' name='new_price' value='${product.price}'></td>
                            <td colspan='2'><button type='submit'>Update</button></td>
                        </tr>
                    </form>
                </c:if>

                <c:if test="${sessionScope.action ne 'edit' || param.id ne product.id}">
                    <tr>
                        <td>${product.id}</td>
                        <td>${product.name}</td>
                        <td>${product.provider}</td>
                        <td>${product.price}</td>
                        <td><a href='list?action=edit&id=${product.id}'>Edit</a></td>
                        <td><a href='list?action=delete&id=${product.id}'>Delete</a></td>
                    </tr>
                </c:if>
            </c:forEach>  
        </table>

        <p class='error'>${requestScope.err}</p>
        
    </body>
</html>
