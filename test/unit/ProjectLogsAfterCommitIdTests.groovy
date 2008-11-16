import org.watchit.domain.*

class ProjectLogsAfterCommitIdTests extends GroovyTestCase {
	def project
	void setUp(){
		project = new GitProject()
		project.logs = [
			new CommitLog(logId:"1"),
			new CommitLog(logId:"2"),
			new CommitLog(logId:"3")
		]
	}
	
	void testShouldReturnAllLogsWhenGivenNull(){
		//i know, i know, internal apis with nulls is paranoia...we can talk about it.
		assertEquals(project.logs, project.logsAfterCommitId(null))
	}
	void testShouldReturnCorrectLogsWhenGivenLogId(){
		assertEquals(project.logs[1..-1], project.logsAfterCommitId("1"))
	}
	void testShouldReturnNoLogsWhenGivenLastLogId(){
		assertTrue(project.logsAfterCommitId("3").isEmpty())
	}
	void testShouldReturnAllLogsWhenGivenInvalidLogId(){
		assertEquals(project.logs, project.logsAfterCommitId("300"))
	}
}