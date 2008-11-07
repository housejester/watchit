import org.watchit.git.GitCloneException

class GitServiceTests extends GroovyTestCase {
    def cloneProcReturn

    void testShouldThrowExWhenCloneHasError(){
	def git = new GitService()

	cloneProcReturn = [ 
		exitValue : { -> 128 }, 
		err : [ text : "--clone error text--" ],
		waitFor : {} 

	] 
	
	String.metaClass.execute = { -> cloneProcReturn }

	try{
		git.clone("git://foo", "/tmp")
		fail("should throw ex when proc has error")
	}catch( GitCloneException ex ){
		assertTrue( ex.getMessage().contains(cloneProcReturn.err.text))
	}
    }
}
