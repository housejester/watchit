import org.watchit.git.*

class GitTests extends GroovyTestCase {
	def git
	def cloneProcReturn 
	def cloneExecString
	def nextFileName

	void setUp(){
		git = new Git()
		nextFileName = "/tmp/foo"
		cloneProcReturn = [ 
			exitValue : { -> 128 }, 
			text : "out",
			err : [ text : "--clone error text--" ],
			waitFor : {}
		]
		String.metaClass.execute = { -> cloneExecString = delegate; return cloneProcReturn; }
		git.tempFileNameSource = [ nextFileName : { -> nextFileName }]
	}

    void testShouldThrowExWhenCloneHasError(){
		try{
			git.clone("git://foo", "/tmp/foo")
			fail("should throw ex when proc has error")
		}catch( GitCloneException ex ){
			assertTrue( ex.getMessage().contains(cloneProcReturn.err.text))
		}
    }
	void testShouldThrowExWhenCloneCalledWithNonGitUrl(){
		try{
			git.clone("foo", "/tmp/foo")
			fail("should throw ex when clone called without git:// url")
		}catch(InvalidGitUrlException ex){
		}
	}
	
	void testShouldUseTempFileServiceForTargetDirectory(){
		cloneProcReturn.exitValue = { -> 0 }
		nextFileName = "/tmp/fileNameForTesting"
		def fname = git.clone("git://foo")
		assertEquals("git clone git://foo ${nextFileName}", cloneExecString)
		assertEquals(nextFileName, fname)
	}

	void testCheckoutShouldCreateGitProject(){
		cloneProcReturn.exitValue = { -> 0 }
		nextFileName = "/tmp/fileNameForTesting"
		def project = git.checkout("git://foo")
		assertEquals("git://foo", project.repoUrl)
		assertEquals(nextFileName, project.repoDir)
	}
}
