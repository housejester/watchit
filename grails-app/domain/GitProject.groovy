import org.watchit.git.Git

class GitProject extends Project {
	
	public boolean updateLogs(){
		def git = GitProject.git(repoDir)
		git.update()
		def logs = git.log(lastLogId)
		return !logs.isEmpty();
	}

	public static Git git(repoDir){
		return new Git(repoDir)
	}
}
