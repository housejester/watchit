<html>
<head>
	<meta name="layout" content="main"/>
</head>
<body>
	<p>Reports:
		<a href="${createLink(controller:'projectAnalyzer')}">Report List</a> |
		<a href="${createLink(controller:'fileCounter')}">File Count Report</a> 
	</p>
<g:each in="${projects}">
	<p>${it.repoUrl} [
		<a href="${createLink(action:'updateLogs', id:it.id)}">Update Logs</a> | 
		<a href="${createLink(action:'analyze', id:it.id)}">Analyze</a> 
	]</p>
</g:each>
</body>
</html>