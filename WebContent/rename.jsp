<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Add a new inventory item </h2>
<br/>
<form name="changeNameForm" action="changeName.do" method="post">
  <input type="hidden" name="id" value=<c:out value="${inventoryItem.id}" escapeXml="false"/> />
  <input type="hidden" name="version" value=<c:out value="${inventoryItem.version}" escapeXml="false"/> />
  New Inventory Item Name: <input type="text" name="newName" />
  <input type="submit" value="Submit" />
</form>