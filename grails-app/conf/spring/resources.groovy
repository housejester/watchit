beans = {
	tempFileNameSource(org.watchit.TempFileNameSource)
    scm(org.watchit.git.Git){
		tempFileNameSource = tempFileNameSource
	}
	analyzerFactoryMap(org.watchit.StaticAnalyzerSource){ bean ->
		bean.factoryMethod = "getAnalyzerFactoryMap"
	}
}

