import org.watchit.git.*
import org.watchit.domain.*

class WatchitControllerTests extends GroovyTestCase {
    def controller
    def params 
    def renderArgs
	def redirectArgs

    void setUp() {
		params = [ repoUrl:"git://fakegiturl"]    
		renderArgs = [ : ]

		WatchitController.metaClass.params = params 
		WatchitController.metaClass.render = { args -> renderArgs = args }
		WatchitController.metaClass.redirect = { args -> redirectArgs = args }

		controller = new WatchitController()
		controller.projectService = new ProjectService()
    }
	void tearDown(){
		def remove = GroovySystem.metaClassRegistry.&removeMetaClass
		remove ProjectService
		remove WatchitController
	}

    void testWatchActionShouldCallToProjectService(){
		def watchRepoUrl = null
		params.repoUrl = "git://foo"
		ProjectService.metaClass.watch = { repoUrl -> watchRepoUrl = repoUrl; new Project(id:202) }
		controller.watch()
		assertEquals( params.repoUrl, watchRepoUrl )
    }

    void testWatchActionShouldRenderErrorPageOnError(){
		ProjectService.metaClass.watch = { repoUrl -> throw new Exception("foo not found") }
		controller.watch()
		assertEquals( "index", renderArgs.view )       
		assertEquals( "foo not found", renderArgs.model.error )
    }

	void testWatchActionShouldRedirectToProjectDetailPage(){
		ProjectService.metaClass.watch = { repoUrl -> new Project(id:202) }
		controller.watch()
		assertEquals( "/project/202", redirectArgs.uri )
	}
}
