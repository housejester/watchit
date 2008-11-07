Could not find Project ${params.name}.  
<g:if test="${error}">
<pre>
${error}
</pre>
</g:if>
<g:else>
It doesn't look like it's a git url.
</g:else>
