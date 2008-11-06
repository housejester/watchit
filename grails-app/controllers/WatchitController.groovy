class WatchitController {
    	def index = { 
		try{
			def gitVersion = "git --version".execute().text
			return [ gitVersion : gitVersion ]
		}catch(Exception e){
			render( view : "gitNotFound" )
		}
	}
}
