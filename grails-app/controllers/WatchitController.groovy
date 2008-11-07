import java.io.File

class WatchitController {
	def gitService

    	def index = { 
		gitService.exists() ? render( view : "index" ) : render( view : "gitNotFound" ) 
	}
	
	def watch = {
		def f = File.createTempFile("watchit-project-", ".git")
		f.delete()
		def cloneOut = gitService.clone( params.name, f.absolutePath )
		return [cloneOutput: cloneOut]
	}
}
