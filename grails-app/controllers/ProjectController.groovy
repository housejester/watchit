import org.watchit.domain.*

class ProjectController {
	def scaffold = Project
	def projectService
	def projectAnalyzerService

	def updateLogs = {
		projectService.updateLogs(params.id)
		redirect(uri:"/project/${params.id}")
	}
	
	def analyze = {
		projectAnalyzerService.analyzeProject(params.id)
		redirect(uri:"/projectAnalyzer")
	}

	def actions = {
		return [projects:Project.findAll()]
	}
}
