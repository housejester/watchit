import org.watchit.domain.*

class ProjectService {
    boolean transactional = true

	def scm
	def analyzerService

	def watch(repoUrl){
		return Project.findByRepoUrl(repoUrl) ?: createProject(repoUrl)
	}

	def createProject(repoUrl){
		def project = scm.checkout( repoUrl ) 
		project.save()
		return project
	}
	
	def updateLogs(projectId){
		Project.findById(projectId).updateLogs()
	}
	
	def analyzeLogs(projectId){
		analyzerService.analyze(Project.findById(projectId))
	}
}
