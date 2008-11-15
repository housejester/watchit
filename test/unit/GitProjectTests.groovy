import org.watchit.git.*

class GitProjectTests extends GroovyTestCase {
	def git
	def dirPassedToGit 
	def updateCalled = false
	def logCalledWith = "fake log id"
	def logsToReturn = []

	void setUp(){
		GitProject.metaClass.getScm = {repoDir -> 
			dirPassedToGit = repoDir 
			git = new Git(repoDir)
			return git
		}
		
		Git.metaClass.update = {-> updateCalled = true }
		Git.metaClass.log = {sinceLogId-> logCalledWith = sinceLogId; return logsToReturn; }
	}

	void testUpdateShouldCreateGitInstance(){
/*		Can't figure out how to test this.  Dammit.
		
		def project = new GitProject(repoDir:"/tmp/foo")
		project.metaClass.getScm = {repoDir -> 
			dirPassedToGit = repoDir 
			git = new Git(repoDir)
			return git
		}
		project.updateLogs()
		assertEquals("/tmp/foo", dirPassedToGit)
*/
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
		project.metaClass.addToLogs = { l -> }
		logsToReturn = [ new CommitLog() ]
		
		assertTrue(project.updateLogs() )
	}
	void testShouldAddSingleReturnedLogToLogsList(){
		def project = new GitProject(repoDir:"/tmp/foo", lastLogId:"someLogId")
		def addedLog = null
		project.metaClass.addToLogs = { l -> addedLog = l }
		logsToReturn = [ new CommitLog() ]
		
		project.updateLogs()
		
		assertSame(logsToReturn[0], addedLog)
	}
	void testShouldAddMultipleReturnedLogsToLogsListInOrderReceived(){
		def project = new GitProject(repoDir:"/tmp/foo", lastLogId:"someLogId")
		def addedLogs = []
		project.metaClass.addToLogs = { l -> addedLogs.add(l) }
		logsToReturn = [ new CommitLog(), new CommitLog(), new CommitLog() ]
		
		project.updateLogs()
		
		assertSame(logsToReturn[0], addedLogs[0])
		assertSame(logsToReturn[1], addedLogs[1])
		assertSame(logsToReturn[2], addedLogs[2])
	}
	void testShouldSetLastLogIdToIdOfLastLogReceived(){
		def project = new GitProject(repoDir:"/tmp/foo", lastLogId:"someLogId")
		def addedLogs = []
		project.metaClass.addToLogs = { l -> addedLogs.add(l) }
		logsToReturn = [ new CommitLog(logId:"1"), new CommitLog(logId:"2"), new CommitLog(logId:"3") ]
		
		project.updateLogs()
		assertEquals("3", project.lastLogId)
	}
}