beans = {
	tempFileNameSource(org.watchit.TempFileNameSource)
    git(org.watchit.git.Git){
		tempFileNameSource = tempFileNameSource
	}
	analyzerService(org.watchit.AnalyzerMonkeys){ bean ->
		bean.initMethod = 'loadMonkeys' 
	}
}