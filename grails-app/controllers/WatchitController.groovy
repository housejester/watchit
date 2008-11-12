import org.watchit.git.*

class WatchitController {
	def projectService

    def index = { 
	}
	
	def watch = {
		try{	
			def project = projectService.watch(params.repoUrl)
			redirect( uri:"/project/${project.id}")
		}catch(ex){
			//should this handle specific exceptions only?
			render( view:"index", model:[ error : ex.getMessage() ] )
		}
	}
	
}
