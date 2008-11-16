import org.watchit.domain.*
import org.watchit.*

class StaticAnalyzerServiceTests extends GroovyTestCase {
	def service
	def project
	def bookMarksToReturn
	
	void setUp(){
		bookMarksToReturn = [:]
		service = new StaticAnalyzerService()
		project = new GitProject(id:101, logs:[new CommitLog(logId:"1"), new CommitLog(logId:"2"), new CommitLog(logId:"3")])

		service.addLogAnalyzer("emptyAnalyzer", [ analyzeLog : { log -> }])

		ProjectAnalyzerBookMark.metaClass.static.findByProjectAndAnalyzerKey = { proj, key -> bookMarksToReturn[key] }
		ProjectAnalyzerBookMark.metaClass.save = { -> }
	}
	void tearDown(){
		def remove = GroovySystem.metaClassRegistry.&removeMetaClass
		remove ProjectAnalyzerBookMark
	}

	void testShouldPassAllLogsToNewAnalyzers(){
		def logsAnalyzed = []
		service.addLogAnalyzer("testAnalyzer", [ analyzeLog : { log -> logsAnalyzed.add(log) }])

		service.analyze(project)
		
		assertEquals(project.logs, logsAnalyzed)
	}
	
	void testShouldCreateAnalyzerBookMarkForNewAnalyzer(){
		def bookMarks = []
		ProjectAnalyzerBookMark.metaClass.save = { -> bookMarks.add(delegate) }
		service.analyze(project)
		assertEquals(1, bookMarks.size())
		assertEquals(project, bookMarks[0].project)
	}
	
	void testShouldLookupBookMarksForAnalyzers(){
		def bookMarks = []
		ProjectAnalyzerBookMark.metaClass.save = { -> bookMarks.add(delegate) }

		bookMarksToReturn["emptyAnalyzer"] = new ProjectAnalyzerBookMark(id:202)
		service.analyze(project)
		
		assertSame(bookMarksToReturn["emptyAnalyzer"], bookMarks[0])
		assertEquals( project.logs[-1].logId, bookMarks[0].lastLogIdAnalyzed)
	}

	void testShouldNotReAnalyzeLogs(){
		def logsAnalyzed = []
		service.addLogAnalyzer("testAnalyzer", [ analyzeLog : { log -> logsAnalyzed.add(log) }])
		
		bookMarksToReturn["testAnalyzer"] = new ProjectAnalyzerBookMark(id:202, lastLogIdAnalyzed:project.logs[0].logId)
		
		service.analyze(project)
		assertEquals(project.logs[1..-1], logsAnalyzed)
	}
}
