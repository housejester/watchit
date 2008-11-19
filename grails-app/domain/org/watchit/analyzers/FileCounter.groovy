package org.watchit.analyzers

import org.watchit.domain.*

class FileCounter extends ProjectAnalyzer{
	Integer numFiles
	String filePattern
	public FileCounter(){
		this(".")
	}
	public FileCounter(pattern){
		this.filePattern = pattern
	}
	
	def analyze = { -> 
		numFiles = previousReport?.numFiles ?: 0
		numFiles += commitLog.whatChanged.findAll{ it.filePath =~ filePattern && it.status == "A" }.size()
		numFiles -= commitLog.whatChanged.findAll{ it.filePath =~ filePattern && it.status == "D" }.size()
	}
	
	static constraints = { 
		name()
		numFiles()
		filePattern()
		previousReport(nullable:true)
	}
}