import org.watchit.git.Git

class Project {
	String repoUrl
	String repoDir
	String lastLogId
	public void updateLogs(){
	}
	static constraints = {
		repoUrl()
		repoDir(nullable:true)
		lastLogId(nullable:true)
	}
}
