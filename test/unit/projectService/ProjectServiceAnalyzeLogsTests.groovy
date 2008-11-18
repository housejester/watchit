import org.watchit.domain.*

class ProjectServiceAnalyzeLogsTests extends GroovyTestCase {
	static TEST_ID = 202
	
	def projectService
	def project
	def idUsed = 0
	
	void setUp(){
		projectService = new ProjectService()
		projectService.analyzerService = [ analyze:{proj->}]

		project = new Project()
		Project.metaClass.static.findById = { id -> idUsed = id; return project; }
	}

	void tearDown(){
		def remove = GroovySystem.metaClassRegistry.&removeMetaClass
		remove Project
	}
	
    void testShouldCallLookupProjectById(){
		projectService.analyzeLogs(TEST_ID)
		assertEquals( TEST_ID, idUsed)
    }

    void testShouldAnalyzeReturnedProject(){
		def analyzedProject;
		projectService.analyzerService = [ analyze:{proj->analyzedProject=proj}]

		projectService.analyzeLogs(TEST_ID)
		assertSame( project, analyzedProject )
    }
}


