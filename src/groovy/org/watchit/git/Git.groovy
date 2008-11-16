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
	
	def log(sinceCommitId=""){
		if(sinceCommitId){
			sinceCommitId = "${sinceCommitId}.."
		}else{
			sinceCommitId = ""
		}
		def logFormat = logFormat.addFullHash().addCommitterName().addCommitTime().addSubject()
		def logCommand = "git --git-dir=${repoDir}/.git/ log --pretty=format:${logFormat} ${sinceCommitId}"
		def logs = []
		logFormat.parse( logCommand.execute().text, { parts ->
			logs.add(new CommitLog(
				logId:parts[0], 
				author:parts[1], 
				commitDate:new Date(Long.parseLong(parts[2]) * 1000), 
				subject:parts[3]
			)) 
		})
		return logs
	}
	
	def getLogFormat(){
		return new LogFormatBuilder()
	}
}
