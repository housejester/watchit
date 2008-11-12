class ProjectService {
    boolean transactional = true

	def git

	def watch(repoUrl){
		return Project.findByRepoUrl(repoUrl) ?: createProject(repoUrl)
	}

	def createProject(repoUrl){
		git.clone( repoUrl ) //REALLY feel this doesn't belong here.
		def project = new Project()
		project.repoUrl = repoUrl
		project.save()
		return project
	}
}
