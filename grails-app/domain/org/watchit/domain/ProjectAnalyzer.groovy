package org.watchit.domain

class ProjectAnalyzer {
	static belongsTo = [Project, CommitLog]
	String name
	Project project
	CommitLog commitLog
	ProjectAnalyzer previousReport
	Boolean latest = false
	
	def analyze = { -> println "Analyzing ${commitLog.logId}"}

	static constraints = {
		name()
		commitLog()
		previousReport(nullable:true)
	}
}
