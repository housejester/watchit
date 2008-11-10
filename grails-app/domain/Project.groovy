import org.watchit.git.Git

class Project {
	String repoUrl
	
	public static Project watch(String repoUrl){
		return Project.findByRepoUrl(repoUrl) ?: createProject(repoUrl)
	}

	public static Project createProject(String repoUrl){
		(new Git()).clone( repoUrl ) //REALLY feel this doesn't belong here.
		def project = new Project()
		project.repoUrl = repoUrl
		project.save()
		return project
	}
}
