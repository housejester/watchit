import java.io.File
import org.watchit.git.*

class WatchitController {
	def git

    def index = { 
		git.exists() ? render( view : "index" ) : render( view : "gitNotFound" ) 
	}
	
	def watch = {
		try{	
			def cloneOut = git.clone( params.name )
			render( view:"watch" )
		}catch( GitCloneException ex ){
			render( view:"index", model:[ error : ex.getMessage() ] )
		}
	}
}
