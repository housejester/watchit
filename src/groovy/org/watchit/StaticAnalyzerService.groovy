package org.watchit
import org.watchit.domain.*

class StaticAnalyzerService{
	def analyzers = []
	
	public void analyze(Project project){
		analyzers.each{ entry ->
			def key = entry.key
			def analyzer = entry.analyzer
			def bookMark = lookupOrCreateBookMark(project, key)
			project.logsAfterCommitId(bookMark.lastLogIdAnalyzed).each{ log -> 
				analyzer.analyzeLog( log )
			}
			bookMark.lastLogIdAnalyzed = project.logs[-1].logId
			bookMark.save()
		}
	}

	def lookupOrCreateBookMark = { project, key ->
		return ProjectAnalyzerBookMark.findByProjectAndAnalyzerKey(project, key) ?:
			new ProjectAnalyzerBookMark(project:project, analyzerKey:key)
	}

	def addLogAnalyzer = { key, analyzer -> analyzers.add( [key:key, analyzer:analyzer])}
}