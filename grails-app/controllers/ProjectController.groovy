import org.watchit.domain.*

class ProjectController {
	def scaffold = Project
	def projectService

	def updateLogs = {
		projectService.updateLogs(params.id)
		redirect(uri:"/project/${params.id}")
	}
	
	def analyzeLogs = {
		projectService.analyzeLogs(params.id)
		redirect(uri:"/project/${params.id}")
	}

	def actions = {
		return [projects:Project.findAll()]
	}
}
