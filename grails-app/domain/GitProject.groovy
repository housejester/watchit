import org.watchit.git.Git

class GitProject extends Project {
	
	public void updateLogs(){
		def git = GitProject.git(repoDir)
		git.update()
		git.log(lastLogId)
	}

	public static Git git(repoDir){
		return new Git(repoDir)
	}
}
