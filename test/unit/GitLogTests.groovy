import org.watchit.git.*
import org.watchit.*

class GitLogTests extends GroovyTestCase {
	def git
	def projDir
	def filesToCommit = [ 
		[name:"file1", message:"added file1"],
		[name:"file2", message:"added file2"],
		[name:"file3", message:"added file3"],
	]
	
	void setUp(){
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
		assertEquals(filesToCommit.size(), git.log(null).size())
    }
	void testShouldHaveSubjectInCommitLog(){
		git.log(null).each{ assertNotNull(it.subject)}
	}
    void testShouldReturnLogsOldestToNewest(){
		def logs = git.log(null)
		def expectedOrder = filesToCommit.collect{it.message}
		def actualOrder = logs.collect{it.subject}
		assertEquals( expectedOrder, actualOrder)
    }
    void testShouldReturnLogsSinceProvidedCommit(){
		def allLogs = git.log(null)
		def allButFirstLogs = git.log(allLogs[0].logId)
		assertEquals( allLogs.size()-1, allButFirstLogs.size())
    }
}
