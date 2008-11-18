package org.watchit

import org.watchit.domain.*
import org.watchit.analyzers.*

class StaticAnalyzerSource{
	public static Map getAnalyzerFactoryMap(){
		return [
			"Test Analyzer" : { new ProjectAnalyzer() }, 
			"File Count" : { new FileCounter() }
		];
	}
}