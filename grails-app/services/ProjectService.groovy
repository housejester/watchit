class ProjectService {
    boolean transactional = true

	def git

	def watch(repoUrl){
		return Project.findByRepoUrl(repoUrl) ?: createProject(repoUrl)
	}

	def createProject(repoUrl){
		def repoDir = git.clone( repoUrl ) //REALLY feel this doesn't belong here.
		def project = new Project(repoUrl:repoUrl,repoDir:repoDir)
		project.save()
		return project
	}
}
