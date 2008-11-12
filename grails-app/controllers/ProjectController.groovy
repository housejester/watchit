class ProjectController {
	def scaffold = Project

	def refreshLogCount = {
		def project = Project.findById(params.id)
		project.logCount = "git --git-dir=${project.repoDir}/.git/ log --pretty=format:%H".execute().text.split("\n").length
		redirect(uri:"/project/${project.id}")
	}
}
