<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
<h2>Chec in the item: <c:out value="${inventoryItem.name}"/></h2>
<br/>
<form name="removeForm" action="remove.do" method="post">
  <input type="hidden" name="id" value=<c:out value="${inventoryItem.id}" escapeXml="false"/> />
  <input type="hidden" name="version" value=<c:out value="${inventoryItem.version}" escapeXml="false"/> />
  Amount: <input type="text" name="count" />
  <input type="submit" value="Submit" />
</form>
</body>
</html>