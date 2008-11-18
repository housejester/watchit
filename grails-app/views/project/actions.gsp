<html>
<head>
	<meta name="layout" content="main"/>
</head>
<body>
<g:each in="${projects}">
	<p>${it.repoUrl} [<a href="${createLink(action:'updateLogs', id:it.id)}">Update Logs</a> | <a href="${createLink(action:'analyzeLogs', id:it.id)}">Analyze Logs</a>]</p>
</g:each>
</body>
</html>