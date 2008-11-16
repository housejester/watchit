import org.watchit.git.*
import org.watchit.*

class GitLogTests extends GroovyTestCase {
	def testStartTime
	def git
	def projDir
	def filesToCommit = [ 
		[name:"file1", message:"added file1"],
		[name:"file2", message:"added file2"],
		[name:"file3", message:"added file3"],
	]
	
	void setUp(){
		Calendar cal = Calendar.getInstance()
		cal.set(Calendar.MILLISECOND, 0)
		testStartTime = cal.time

		projDir = (new TempFileNameSource()).nextFileName()
		(new File(projDir)).mkdir()

		initNewProject(projDir)
		filesToCommit.each{ addFileAndCommit(it.name, it.message) }

		git = new Git(projDir)
	}

	void tearDown(){
		def delCommand = "rm -fr ${projDir}"
		if( delCommand.indexOf("rm -fr /tmp") == 0){
			delCommand.execute().waitFor()
		}
	}

	private void initNewProject(dir){
		[ "sh" , "-c" , "cd ${dir} && git init" ].execute().waitFor()
	}
	private void addFileAndCommit(fileName, commitMessage){
		(new File("${projDir}/${fileName}")).createNewFile()
		[ "sh" , "-c" , "cd ${projDir} && git add ${fileName} && git commit -a -m '${commitMessage}'" ].execute().waitFor()
	}

    void testShouldHaveCorrectLogCount(){
		assertEquals(filesToCommit.size(), git.log().size())
    }
    void testShouldFindAllLogsWhenNullPassed(){
		assertEquals(filesToCommit.size(), git.log(null).size())
    }
	void testShouldHaveAllFiledsPopulatedInCommitLog(){
		git.log().each{ 
			assertNotNull(it.subject)
			assertNotNull(it.author)
			assertNotNull(it.commitDate)
			assertTrue("Date looks too early: test started ${testStartTime.time}, commit said ${it.commitDate.time}", 
				it.commitDate.after(testStartTime) || it.commitDate.equals(testStartTime))
			assertTrue("Date looks too late: test started ${testStartTime}, commit said ${it.commitDate}", 
				it.commitDate.before(new Date()) || it.commitDate.equals(new Date()))
		}
	}
    void testShouldReturnLogsOldestToNewest(){
		def logs = git.log()
		def expectedOrder = filesToCommit.collect{it.message}
		def actualOrder = logs.collect{it.subject}
		assertEquals( expectedOrder, actualOrder)
    }
    void testShouldReturnLogsSinceProvidedCommit(){
		def allLogs = git.log()
		def allButFirstLogs = git.log(allLogs[0].logId)
		assertEquals( allLogs.size()-1, allButFirstLogs.size())
    }
}
