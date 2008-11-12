import org.watchit.git.Git

class Project {
	String repoUrl
	String repoDir
	Integer logCount
	static constraints = {
		repoUrl()
		repoDir(nullable:true)
		logCount(nullable:true)
	}
}
