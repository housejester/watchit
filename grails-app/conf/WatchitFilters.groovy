import org.watchit.git.Git

class WatchitFilters {
   def filters = {
       gitInstalledCheck(controller:'*', action:'*') {
           before = {
				if( servletContext.getAttribute("WATCHIT_FILTER.GIT_INSTALLED") == null){
					if( !(new Git()).exists()){
						render(view:'/gitNotFound')
						return false;
					}
					servletContext.setAttribute("WATCHIT_FILTER.GIT_INSTALLED", true)
				}
           }
} } }