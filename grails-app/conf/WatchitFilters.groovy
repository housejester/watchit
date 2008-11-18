import org.watchit.git.Git

class WatchitFilters {
	static final String GIT_INSTALLED = "WATCHIT_FILTER.GIT_INSTALLED"
   def filters = {
       gitInstalledCheck(controller:'*', action:'*') {
           before = {
				if( servletContext.getAttribute(GIT_INSTALLED) == null){
					if(!(new Git()).exists()){
						render(view:'/gitNotFound')
						return false;
					}
					servletContext.setAttribute(GIT_INSTALLED, true)
				}
           }
} } }