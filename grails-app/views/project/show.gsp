

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show Project</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Project List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Project</g:link></span>
        </div>
        <div class="body">
            <h1>Show Project</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:projectInstance, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Repo Url:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:projectInstance, field:'repoUrl')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Repo Dir:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:projectInstance, field:'repoDir')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Log Count:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:projectInstance, field:'logCount')} 
								<g:link action="refreshLogCount" params="[id:projectInstance.id]">Refresh</g:link>
							</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${projectInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
