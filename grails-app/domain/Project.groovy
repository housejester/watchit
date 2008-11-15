import org.watchit.git.Git

class Project {
	static hasMany = [logs:CommitLog]

	String repoUrl
	String repoDir
	String lastLogId

	public boolean updateLogs(){
	}

	static constraints = {
		repoUrl()
		repoDir(nullable:true)
		lastLogId(nullable:true)
	}
}
