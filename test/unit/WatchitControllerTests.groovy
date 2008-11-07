class WatchitControllerTests extends GroovyTestCase {
    def git
    def renderArgs
    def controller
    def cloneUrl 
    def cloneDir	
    def params 

    void setUp() {
	git = [ 
		exists : { -> true },
		clone : { url,dir -> cloneUrl = url; cloneDir = dir; [ text : "cloneOut" ] }
	]
	params = [ name:"git://fakegiturl"]    
	renderArgs = [ : ]
        cloneUrl = null
        cloneDir = null
	WatchitController.metaClass.params = params 
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
	controller.watch()
	assertEquals( params.name, cloneUrl )
	assertNotNull( cloneDir )
	assertEquals( "watch", renderArgs.view )
    }

    void testWatchActionShouldRenderErrorPageIfNotGitUrl(){
	params.name = "a name that doesn't start with git://"
	controller.watch()
 	assertNull( cloneDir )
	assertNull( cloneUrl )
	assertEquals( "projectNotFound", renderArgs.view )       
    }
}
