package org.watchit

import org.watchit.domain.*
import org.watchit.analyzers.*

class StaticAnalyzerSource{
	public static Map getAnalyzerFactoryMap(){
		return [
			"File Count" : { new FileCounter() },
			"Groovy File Count" : { new FileCounter("\\.groovy\$") }
		];
	}
}