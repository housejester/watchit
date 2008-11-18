import org.watchit.domain.*

class ProjectAnalyzerService{	
	def analyzerFactoryMap
	
	def analyzeProject(projectId){
		def project = Project.findById(projectId)
		def remainingAnalyzerNames = analyzerFactoryMap*.key
		lookupLatestReports(project).each{ previousReport ->
			remainingAnalyzerNames.remove(previousReport.name)
			analyzeLogsForAnalyzerName( previousReport.name)
		}

		remainingAnalyzerNames.each{ analyzerName -> 
			analyzeLogsForAnalyzerName(analyzerName, project, null)
		}
	}
	
	def analyzeLogsForAnalyzerName(analyzerName, project, previousReport){
		project.logsAfterCommitId(previousReport?.commitLog?.logId).each{ commitLog -> 
			def analyzer = newAnalyzerByName(analyzerName)
			analyzer.commitLog = commitLog
			analyzer.project = project
			analyzer.previousReport = previousReport
			previousReport?.latest = false
			analyzer.latest = true
			analyzer.analyze()
			analyzer.save()
			previousReport = analyzer
		}
	}

	def newAnalyzerByName = { name ->
		def analyzer = analyzerFactoryMap[name]( name )
		analyzer?.name = name
		return analyzer
	}
	
	def lookupLatestReports = { project ->
		return ProjectAnalyzer.findByProjectAndLatest(project, true)
	}
}