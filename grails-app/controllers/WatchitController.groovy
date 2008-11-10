import java.io.File
import org.watchit.git.*

class WatchitController {
	def git

    def index = { 
	}
	
	def watch = {
		def project = Project.findByRepoUrl(params.repoUrl)
		if( project ){
			redirect( uri:"/project/${project.id}")
			return
		}
		try{	
			def cloneOut = git.clone( params.repoUrl )
			project = new Project()
			project.repoUrl = params.repoUrl
			project.save()
			redirect( uri:"/project/${project.id}")
		}catch( GitCloneException ex ){
			render( view:"index", model:[ error : ex.getMessage() ] )
		}
	}
}
