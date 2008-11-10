import java.io.File
import org.watchit.git.*

class WatchitController {
	def git

    def index = { 
	}
	
	def watch = {
		def project = Project.findByRepoUrl(params.name)
		if( project ){
			redirect( uri:"/project/${project.id}")
			return
		}
		try{	
			def cloneOut = git.clone( params.name )
			project = new Project()
			project.repoUrl = params.name
			project.save()
			redirect( uri:"/project/${project.id}")
		}catch( GitCloneException ex ){
			render( view:"index", model:[ error : ex.getMessage() ] )
		}
	}
}
