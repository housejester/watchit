import org.watchit.git.*
import org.watchit.domain.*

class ProjectServiceUpdateLogsTests extends GroovyTestCase {
	static TEST_ID = 202
	
	def projectService
	def project
	def idUsed = 0
	
	void setUp(){
		projectService = new ProjectService()
		projectService.analyzerService = [ analyze : {proj->}]

		project = new Project()
		project.metaClass.updateLogs = {->false}
		Project.metaClass.static.findById = { id -> idUsed = id; return project; }
	}
	void tearDown(){
		def remove = GroovySystem.metaClassRegistry.&removeMetaClass
		remove Project
		remove GitProject
	}
	
    void testShouldCallLookupProjectById(){
		projectService.updateLogs(TEST_ID)
		assertEquals( TEST_ID, idUsed)
    }

    void testShouldCallUpdateLogsOnReturnedProject(){
		def updateCalled = false;
		project.metaClass.updateLogs = {->updateCalled = true}

		projectService.updateLogs(TEST_ID)
		assertTrue( updateCalled )
    }

	void testShouldCallAnalyzerServiceWithReturnedProjectWhenUpdateFoundLogs(){
		def projectPassedToAnalyze = null
		
		project.metaClass.updateLogs = {-> true }
		projectService.analyzerService = [ analyze : {proj->projectPassedToAnalyze=proj}]
	
		projectService.updateLogs(TEST_ID)
		assertSame( project, projectPassedToAnalyze )
	}

	void testShouldNotCallAnalyzerServiceWhenUpdateFoundNoNewLogs(){
		project.metaClass.updateLogs = {-> false}
		projectService.analyzerService = [ analyze : {proj -> fail "Should not call analyze when no new logs found" }]
	
		projectService.updateLogs(TEST_ID)
	}

}
