import org.watchit.git.*

class ProjectServiceUpdateLogsTests extends GroovyTestCase {
	static TEST_ID = 202
	
	def projectService
	def project
	def idUsed = 0
	
	void setUp(){
		projectService = new ProjectService()
		projectService.analyzerService = [ analyze : {proj->}]

		Project.metaClass.static.findById = { id -> idUsed = id; return project; }

		project = new Project()
	}
	
    void testShouldCallLookupProjectById(){
		projectService.updateLogs(TEST_ID)
		assertEquals( TEST_ID, idUsed)
    }

    void testShouldCallUpdateOnReturnedProject(){
		def updateCalled = false;
		project.metaClass.updateLogs = {->updateCalled = true}

		projectService.updateLogs(TEST_ID)
		assertTrue( updateCalled )
    }

	void testShouldCallAnalyzerServiceWithReturnedProject(){
		def projectPassedToAnalyze = null
		
		projectService.analyzerService = [ analyze : {proj->projectPassedToAnalyze=proj}]
	
		projectService.updateLogs(TEST_ID)
		assertSame( project, projectPassedToAnalyze )
	}

}
