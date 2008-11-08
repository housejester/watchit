import org.watchit.git.*

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
		controller.git = git
    }

    void testWhenGitInstalledIndexShouldRenderIndexView() {
		controller.index()
		assertEquals( "index", renderArgs.view )
    }
    
    void testWhenGitNotInstalledIndexShouldRenderError(){
		controller.git = [
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
		controller.git.clone = {url,dir->
			throw new GitCloneException("foo not found")
		}
		controller.watch()
		assertEquals( "projectNotFound", renderArgs.view )       
    }

    void testWatchActionShouldRenderErrorPageIfCloneFails(){
		controller.git.clone = {url,dir->
			throw new InvalidGitUrlException()
		}
		controller.watch()
		assertEquals( "projectNotFound", renderArgs.view )       
		assertEquals( (new InvalidGitUrlException()).message, renderArgs.model.error)       
    }
   
}
