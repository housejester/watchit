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
	</style>
</head>
<body>
	Could not watch Project ${params.name}.  
	<pre>${error?.encodeAsHTML()}</pre>
</body>
</html>