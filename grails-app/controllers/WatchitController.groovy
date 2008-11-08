import java.io.File
import org.watchit.git.*

class WatchitController {
	def git

    def index = { 
		git.exists() ? render( view : "index" ) : render( view : "gitNotFound" ) 
	}
	
	def watch = {
		def f = File.createTempFile("watchit-project-", ".git")
		f.delete()
		try{	
			def cloneOut = git.clone( params.name, f.absolutePath )
			render( view:"watch" )
		}catch( GitCloneException ex ){
			render( view:"projectNotFound", model:[ error : ex.getMessage() ] )
		}
	}
}
