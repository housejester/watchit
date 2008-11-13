import org.watchit.git.*

class ProjectServiceTests extends GroovyTestCase {
    def git
    def cloneUrl 
	def repoUrlForFind
	def projectToReturn
	def idForNewProject
	def projectService

    void setUp() {
		git = [ 
			checkout : { url -> cloneUrl = url; new GitProject(id: idForNewProject) }
		]
        cloneUrl = null
		
		projectService = new ProjectService()
		projectService.git = git;
		
		projectToReturn = null
		Project.metaClass.static.findByRepoUrl = { url -> repoUrlForFind = url; return projectToReturn;}
		idForNewProject = 101
		GitProject.metaClass.save = {-> delegate.id=idForNewProject}
    }

    void testWatchShouldAttemptToCloneWhenGivenGitUrl(){
		idForNewProject = 303
		Project proj = projectService.watch("git://foo")
		assertEquals( "git://foo", cloneUrl )
		assertEquals( idForNewProject, proj.id)
    }

    void testShouldPassGitCloneExceptionsUpToCaller(){
		git.checkout = {url->
			throw new GitCloneException("some clone exception string")
		}
		try{
			projectService.watch("git://foo")
			fail('should pass GitCloneException on up.')
		}catch(GitCloneException e){
			//pass
		}
    }

    void testShouldPassInvalidGitUrlExceptionsUpToCaller(){
		git.checkout = {url->
			throw new InvalidGitUrlException()
		}
		try{
			projectService.watch()
			fail('shoule pass InvalidGitUrlException on up.')
		}catch(InvalidGitUrlException e){
			//pass
		}
    }

	void testShouldReturnExistingProjectWhenFound(){
		Project p = new Project()
		p.id = 202
		projectToReturn = p
		
		assertSame( projectToReturn, projectService.watch("git://foo"))
		assertNull( cloneUrl )
	}

}
