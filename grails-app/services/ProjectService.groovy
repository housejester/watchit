class ProjectService {
    boolean transactional = true

	def git
	def analyzerService

	def watch(repoUrl){
		return Project.findByRepoUrl(repoUrl) ?: createProject(repoUrl)
	}

	def createProject(repoUrl){
		def project = git.checkout( repoUrl ) 
		project.save()
		return project
	}
	
	def updateLogs(projectId){
		def project = Project.findById(projectId)
		if(project.updateLogs()){
			analyzerService.analyze(project)
		}
	}
}
