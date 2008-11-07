import java.io.File
import org.watchit.git.GitCloneException

class WatchitController {
	def gitService

    	def index = { 
		gitService.exists() ? render( view : "index" ) : render( view : "gitNotFound" ) 
	}
	
	def watch = {
		if( params.name.indexOf("git://") != 0 ){
			render( view : "projectNotFound" )
			return
		}
		def f = File.createTempFile("watchit-project-", ".git")
		f.delete()
		try{	
			def cloneOut = gitService.clone( params.name, f.absolutePath )
			render( view:"watch" )
		}catch( GitCloneException ex ){
			render( view:"projectNotFound", model:[ error : ex.getMessage() ] )
		}
	}
}
