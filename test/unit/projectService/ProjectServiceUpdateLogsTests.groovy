import org.watchit.domain.*

class ProjectServiceUpdateLogsTests extends GroovyTestCase {
	static TEST_ID = 202
	
	def projectService
	def project
	def idUsed = 0
	
	void setUp(){
		projectService = new ProjectService()

		project = new Project()
		project.metaClass.updateLogs = {->false}
		Project.metaClass.static.findById = { id -> idUsed = id; return project; }
	}

	void tearDown(){
		def remove = GroovySystem.metaClassRegistry.&removeMetaClass
		remove Project
	}
	
    void testShouldCallLookupProjectById(){
		projectService.updateLogs(TEST_ID)
		assertEquals( TEST_ID, idUsed)
    }

    void testShouldCallUpdateLogsOnReturnedProject(){
		def updatedProject;
		project.metaClass.updateLogs = {-> updatedProject = delegate}

		projectService.updateLogs(TEST_ID)
		assertSame( project, updatedProject )
    }
}
