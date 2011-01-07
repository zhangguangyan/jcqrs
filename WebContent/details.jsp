<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Item details:</h2>
<br/>
<table>
<tr><td>Id:</td><td><c:out value="${inventoryItem.id}" escapeXml="false"/></td></tr>
<tr><td>Name:</td><td><c:out value="${inventoryItem.name}" escapeXml="false"/></td></tr>
<tr><td>Count:</td><td><c:out value="${inventoryItem.currentCount}" escapeXml="false"/></td></tr>
</table>
<br /><br />
<table>
<tr>
<td><a href="changeNamePage.do?id=${inventoryItem.id}">Rename</a></td>

<td><a href="javascript:submit()">Deactivate</a></td>

<td><a href="checkInPage.do?id=${inventoryItem.id}">Check In</a></td>

<td><a href="removePage.do?id=${inventoryItem.id}&version=${inventoryItem.version}">Remove</a></td>
</tr>
</table>
<form name="deactivateForm" action="deactivate.do" method="post">
<input type="hidden" name="id" value="${inventoryItem.id}"/>
<input type="hidden" name="version" value="${inventoryItem.version}"/>
</form>
<script language="JavaScript" type="text/javascript">
<!--
function submit(){
  document.deactivateForm.submit() ;
}
-->
</script>