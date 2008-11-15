import org.watchit.git.Git

class Project {
	static hasMany = [logs:CommitLog]

	String repoUrl
	String repoDir
	String lastLogId

	public boolean updateLogs(){
		def scm = getScm(repoDir)
		scm.update()
		def logs = scm.log(lastLogId)
		logs.each{ this.addToLogs(it) }
		if( !logs.isEmpty()){
			lastLogId = logs[-1].logId
		}
		return !logs.isEmpty();
	}
	public Git getScm(repoDir){
		return null;
	}

	static constraints = {
		repoUrl()
		repoDir(nullable:true)
		lastLogId(nullable:true)
	}
}
