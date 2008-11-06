class WatchitControllerTests extends GroovyTestCase {

    void testWhenGitInstalledIndexShouldHavGitVersionInModel() {
	def fakeGitVersionOutput = 'fake git version'

	String.metaClass.execute = { -> [ text : fakeGitVersionOutput ] } 
	def model = (new WatchitController()).index()
	assertEquals(fakeGitVersionOutput, model.gitVersion)
    }
    
    void testWhenGitNotInstalledIndexShouldRenderError(){
	String.metaClass.execute = { -> throw new IOException("git: not found") }
	def argsPassedToRender =  null

	WatchitController.metaClass.render = { args -> argsPassedToRender = args }

	(new WatchitController()).index()
	assertEquals( "gitNotFound", argsPassedToRender.view ) 
    }
}
