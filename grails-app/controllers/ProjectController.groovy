import org.watchit.domain.*

class ProjectController {
	def scaffold = Project
	def projectService

	def updateLogs = {
		projectService.updateLogs(params.id)
		redirect(uri:"/project/${params.id}")
	}
}
