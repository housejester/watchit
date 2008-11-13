class ProjectService {
    boolean transactional = true

	def git

	def watch(repoUrl){
		return Project.findByRepoUrl(repoUrl) ?: createProject(repoUrl)
	}

	def createProject(repoUrl){
		def project = git.checkout( repoUrl ) 
		project.save()
		return project
	}
}
