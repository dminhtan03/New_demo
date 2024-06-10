<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="stylelist.css">
    </head>
    <body>
        <h1>List Product</h1>
        <form action='list?action=add' method='post'>
            <table>
                <tr>
                    <td>Product's ID</td>
                    <td><input type="text" name="id" required></td>
                </tr>
                <tr>
                    <td>Product's Name</td>
                    <td><input type="text" name="name" required></td>
                </tr>
                <tr>
                    <td>Provider</td>
                    <td><input type="text" name="provider" required></td>
                </tr>
                <tr>
                    <td>Unit's Price</td>
                    <td><input type="text" name="price" required></td>
                </tr>
                <tr>
                    <td colspan='2' style='text-align: right'>
                        <button type="submit">Insert</button>
                        <button type="reset">Reset</button>
                    </td>
                </tr>
            </table>
        </form>

        <p class='error'>${requestScope.err}</p>

        <a href="list?action=list">Show Product List</a>

    </body>
</html>
