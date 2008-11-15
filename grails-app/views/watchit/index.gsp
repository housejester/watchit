<html>
<head>
	<style type="text/css">
		pre {
			border: 1px dashed black;
			background-color: #DDD;
			padding:5px;
			margin:5px;
			width:400px;
			white-space:-pre-wrap;
			white-space:pre-wrap;
			overflow:auto;
		}
		.error {
			border: 1px solid red;
			background-color:#FFFFCC;
			padding:8px;
			margin:8px;
		}
		#repoUrl{
			width:350px;
		}
	</style>
</head>
<body>
	<g:if test="${error}">
	    <div class="error">
			Could not watch Project ${params.name}.  
			<pre>${error.encodeAsHTML()}</pre>
		</div>
	</g:if>
	<g:form name="watchitForm" url="[controller:'watchit',action:'watch']">
		<label>Project Git URL: <input id="repoUrl" type="text" name="repoUrl" /></label>
		<input type="submit" value="Watch It!" />
	</g:form>
</body>
</html>
