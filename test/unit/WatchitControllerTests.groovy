import org.watchit.git.*

class WatchitControllerTests extends GroovyTestCase {
    def git
    def renderArgs
    def controller
    def cloneUrl 
    def params 
	def repoUrlForFind
	def projectToReturn
	def redirectArgs
	def idForNewProject

    void setUp() {
		git = [ 
			exists : { -> true },
			clone : { url -> cloneUrl = url; [ text : "cloneOut" ] }
		]
		params = [ name:"git://fakegiturl"]    
		renderArgs = [ : ]
        cloneUrl = null
		WatchitController.metaClass.params = params 
		WatchitController.metaClass.render = { args -> renderArgs = args }
		WatchitController.metaClass.redirect = { args -> redirectArgs = args }
		controller = new WatchitController()
		controller.git = git
		
		projectToReturn = null
		Project.metaClass.static.findByRepoUrl = { url -> repoUrlForFind = url; return projectToReturn;}
		idForNewProject = 101
		Project.metaClass.save = {-> delegate.id=idForNewProject}
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
    }

    void testWatchActionShouldRenderErrorPageIfNotGitUrl(){
		controller.git.clone = {url->
			throw new GitCloneException("foo not found")
		}
		controller.watch()
		assertEquals( "index", renderArgs.view )       
		assertEquals( "foo not found", renderArgs.model.error )
    }

    void testWatchActionShouldRenderErrorPageIfCloneFails(){
		controller.git.clone = {url->
			throw new InvalidGitUrlException()
		}
		controller.watch()
		assertEquals( "index", renderArgs.view )       
		assertEquals( (new InvalidGitUrlException()).message, renderArgs.model.error)       
    }

	void testWatchActionShouldRedirectToProjectPageWhenProjectAlreadyBeingWatched(){
		Project p = new Project()
		p.id = 202
		p.repoUrl = 'git://fakegiturl'
		projectToReturn = p
		
		controller.watch()
		assertEquals( "/project/202", redirectArgs.uri )
		assertNull(cloneUrl)
	}

    void testWatchActionShouldRedirectToNewProject(){
		controller.watch()
		assertEquals( "/project/101", redirectArgs.uri )
    }

}
