import java.io.File

class WatchitController {
    	def index = { 
		try{
			def gitVersion = "git --version".execute().text
			return [ gitVersion : gitVersion ]
		}catch(Exception e){
			render( view : "gitNotFound" )
		}
	}
	
	def watch = {
		def f = File.createTempFile("watchit-project-", ".git")
		f.delete()
		System.out.println("git clone ${params.name} ${f.absolutePath}")
		def cloneOut = "git clone ${params.name} ${f.absolutePath}".execute().text
		return [cloneOutput: cloneOut]
	}
}
