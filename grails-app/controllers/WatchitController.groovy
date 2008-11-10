import java.io.File
import org.watchit.git.*

class WatchitController {
	def git

    def index = { 
	}
	
	def watch = {
		try{	
			def project = Project.watch(params.repoUrl)
			redirect( uri:"/project/${project.id}")
		}catch(GitCloneException ex){
			render( view:"index", model:[ error : ex.getMessage() ] )
		}
	}
	
}
