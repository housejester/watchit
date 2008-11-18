package org.watchit

import org.watchit.domain.*

class StaticAnalyzerSource{
	public static Map getAnalyzerFactoryMap(){
		return [
			"Test Analyzer" : { new ProjectAnalyzer() }
		];
	}
}