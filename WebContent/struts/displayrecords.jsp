<%@ taglib uri="/struts-tags" prefix="s" %>

<h3>All Records:</h3>
<table border="1">
<s:iterator  value="list">
<fieldset>
<tr>
<td><s:property value="name"/></td>
<td><s:property value="password"/></td>
<td><s:property value="email"/></td></tr>
</fieldset>
</table>
</s:iterator>