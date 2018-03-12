<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
<h2>All items:</h2>
<table border="1">
    <tr>
     <td align="left">Inventory Item Name</td>
    </tr>
  <c:forEach items="${inventoryItems}" var="entry">
    <tr>
     <td align="left"><a href="details.do?id=${entry.id}">
       <c:out value="${entry.name}" escapeXml="false"/></a> 
     </td>
    </tr>
  </c:forEach>
</table>

<br/>
<table>
    <tr>
     <td align="left">
       <a href="add.do">Add</a> 
     </td>
    </tr>
</table>
</body>
</html>