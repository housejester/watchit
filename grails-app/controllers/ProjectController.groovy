class ProjectController {
	def scaffold = Project
	
	def watch = {
		render "so you like ${params.project}"
	}
}
