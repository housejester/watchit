class ProjectControllerTests extends GroovyTestCase {

    void testWatchShouldTryToResolveProject() {
	ProjectController.metaClass.getParams = { -> [project:'Foo'] }
	ProjectController.metaClass.render = { text -> assertEquals "so you like Foo", text }
	(new ProjectController()).watch()
    }
}
