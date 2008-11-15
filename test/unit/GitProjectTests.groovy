import org.watchit.git.*

class GitProjectTests extends GroovyTestCase {
	def git
	def dirPassedToGit 
	def updateCalled = false
	def logCalledWith = "fake log id"
	def logsToReturn = []

	void setUp(){
		GitProject.metaClass.static.git = {repoDir -> 
			dirPassedToGit = repoDir 
			git = new Git(repoDir)
			return git
		}
		
		Git.metaClass.update = {-> updateCalled = true }
		Git.metaClass.log = {sinceLogId-> logCalledWith = sinceLogId; return logsToReturn; }
	}

	void testUpdateShouldCreateGitInstance(){
		def project = new GitProject(repoDir:"/tmp/foo")
		project.updateLogs()
		assertEquals("/tmp/foo", dirPassedToGit)
	}

	void testUpdateShouldCallUpdateOnGitInstance(){
		def project = new GitProject(repoDir:"/tmp/foo")
		project.updateLogs()
		assertTrue( updateCalled )
	}
	
	void testUpdateShouldCallLogWithNullLogIdWhenNoLastLogIdSet(){
		def project = new GitProject(repoDir:"/tmp/foo")
		project.updateLogs()
		assertNull( logCalledWith )
	}

	void testUpdateShouldCallLogWithLogIdWhenLastLogIdSet(){
		def project = new GitProject(repoDir:"/tmp/foo", lastLogId:"someLogId")
		project.updateLogs()
		assertEquals("someLogId", logCalledWith )
	}
	
	void testShouldReturnedFalseWhenNoNewLogsFound(){
		def project = new GitProject(repoDir:"/tmp/foo", lastLogId:"someLogId")
		
		assertFalse(project.updateLogs() )
	}
	void testShouldReturnedTrueWhenNewLogsFound(){
		def project = new GitProject(repoDir:"/tmp/foo", lastLogId:"someLogId")
		logsToReturn = [ new CommitLog() ]
		
		assertTrue(project.updateLogs() )
	}
}
