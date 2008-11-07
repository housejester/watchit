class WatchitControllerTests extends GroovyTestCase {
    def git
    def renderArgs
    def controller

    void setUp() {
	git = [ 
		exists : { -> true }
	]
	WatchitController.metaClass.params = [ name:"nameParamValue"]
	WatchitController.metaClass.render = { args -> renderArgs = args }
	controller = new WatchitController()
	controller.gitService = git
    }

    void testWhenGitInstalledIndexShouldRenderIndexView() {
	String.metaClass.execute = { -> [ text : "fake version"] } 
	
	controller.index()
	assertEquals( "index", renderArgs.view )
    }
    
    void testWhenGitNotInstalledIndexShouldRenderError(){
	controller.gitService = [
                exists : { -> false }
        ]

	controller.index()
	assertEquals( "gitNotFound", renderArgs.view ) 
    }

    void testWatchActionShouldAttemptToCloneWhenGivenGitUrl(){
	def cloneUrl 
	def cloneDir	
	controller.gitService = [ 
		clone : { url,dir -> cloneUrl = url; cloneDir = dir; [ text : "cloneOut" ] }
	]
	
	controller.watch()
	assertEquals( "nameParamValue", cloneUrl )
	assertNotNull( cloneDir )
    }
}
