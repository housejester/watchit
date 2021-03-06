import org.watchit.git.*
import org.watchit.domain.*

class GitProjectTests extends GroovyTestCase {
	def git
	def updateCalled = false
	def logCalledWith = "fake log id"
	def logsToReturn = []
	def delegateGitInstance

	void setUp(){
		def meta = new ExpandoMetaClass(Git, true)
		meta.update = {-> updateCalled = true; delegateGitInstance = delegate }
		meta.log = {sinceLogId-> logCalledWith = sinceLogId; return logsToReturn; }
		meta.initialize()
	}
	void tearDown(){
		def remove = GroovySystem.metaClassRegistry.&removeMetaClass
		remove Git
		remove Project
		remove GitProject
	}

	void testUpdateShouldCreateGitInstance(){
		//this was WAY harder to test than it should have been.
		def project = new GitProject(repoDir:"/tmp/foo")
		project.updateLogs()
		assertEquals("/tmp/foo", delegateGitInstance.repoDir)
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
