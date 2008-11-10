<html>
<head>
	<style type="text/css">
		pre {
			border: 1px dashed black;
			background-color: #DDD;
			padding:5;
			margin:5;
			width:400;
			white-space:-pre-wrap;
			white-space:pre-wrap;
			overflow:auto;
		}
		.error {
			border: 1px solid red;
			background-color:#FFFFCC;
			padding:8;
			margin:8;
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
		<label>Project Git URL: <input type="text" name="repoUrl" /></label>
		<input type="submit" value="Watch It!" />
	</g:form>
</body>
</html>
