import org.watchit.git.*
import org.watchit.*

class GitLogTests extends GroovyTestCase {
	def testStartTime
	def git
	def projDir
	def filesToCommit
	
	void setUp(){
		filesToCommit  = [ 
			[name:"file1", message:"added file1"],
			[name:"file2", message:"added file2"],
			[name:"file3", message:"added file3"],
		]
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
		filesToCommit[2].message = "added file3\n\nmore body here"
		git.log().each{ 
			assertNotNull(it.subject)
			assertNotNull(it.author)
			assertNotNull(it.commitDate)
			assertTrue(it.message.startsWith(it.subject))

			assertFalse(it.whatChanged.isEmpty())
			assertEquals(1, it.whatChanged.size())
			assertTrue((filesToCommit*.name).containsAll(it.whatChanged*.filePath))
			assertTrue(["A"].containsAll(it.whatChanged*.status))

			assertTrue("Date looks too early: test started ${testStartTime.time}, commit said ${it.commitDate.time}", 
				it.commitDate.after(testStartTime) || it.commitDate.equals(testStartTime))
			assertTrue("Date looks too late: test started ${testStartTime}, commit said ${it.commitDate}", 
				it.commitDate.before(new Date()) || it.commitDate.equals(new Date()))
			if(it.logId == "file3"){
				assertEquals("added file 3\n\nmore body here", it.message)
			}
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
