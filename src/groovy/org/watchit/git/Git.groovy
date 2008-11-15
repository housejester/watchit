package org.watchit.git

import org.watchit.domain.*

class Git {
	String repoDir
	def tempFileNameSource

	public Git(){
	}
	public Git(String repoDir){
		this.repoDir = repoDir
	}
	
    def exists() {
		try{
			"git --version".execute()
		}catch(Exception ex){
			return false
		}
		return true
    }
	
	def checkout(repoUrl){
		def fileName = clone(repoUrl)
		return new GitProject( repoUrl:repoUrl, repoDir:fileName)
	}

	def clone(url){
		def fileName = tempFileNameSource.nextFileName()
		clone(url, fileName)
		return fileName
	}
	
    def clone(url, dir){
		if( url.indexOf("git://") != 0 ){
			throw new InvalidGitUrlException(url);
		}
		def proc = "git clone ${url} ${dir}".execute()
		proc.waitFor()
		if( proc.exitValue() != 0){
			throw new GitCloneException( proc.err.text )
		}
    }

	def update(){
		def proc = "git --git-dir=${repoDir}/.git/ pull".execute()
		proc.waitFor()
	}
	
	def log(sinceCommitId){
		if(!sinceCommitId){
			sinceCommitId = ""
		}
		def proc = "git --git-dir=${repoDir}/.git/ log --pretty=format:%H ${sinceCommitId}".execute()
		proc.waitFor()
		def logs = []
		proc.text.split("\n").each{ logs.add(new CommitLog(logId:it)) }
		return logs
	}
	
}
