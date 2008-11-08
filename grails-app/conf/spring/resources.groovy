beans = {
	tempFileNameSource(org.watchit.TempFileNameSource)
    git(org.watchit.git.Git){
		tempFileNameSource = tempFileNameSource
	}
}