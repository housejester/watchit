package org.watchit.analyzers

import org.watchit.domain.*

class FileCounter extends ProjectAnalyzer{
	Integer numFiles
	
	def analyze = { -> 
		numFiles = previousReport?.numFiles ?: 0
		numFiles += commitLog.whatChanged.findAll{ it.status == "A" }.size()
		numFiles -= commitLog.whatChanged.findAll{ it.status == "D" }.size()
	}
}