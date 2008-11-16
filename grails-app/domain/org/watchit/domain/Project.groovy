package org.watchit.domain

import org.watchit.git.Git

class Project {
	static hasMany = [logs:CommitLog]

	String repoUrl
	String repoDir
	String lastLogId
	List logs

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
	def logsAfterCommitId = { commitId -> 
		if( !commitId){
			return logs
		}
		//brute force...want to issue query here
		def recentLogs = []
		for(int i = logs.size()-1; i>=0; i--){
			println "${logs[i].logId} == ${commitId}? ${logs[i].logId == commitId}"
			if( logs[i].logId == commitId){
				return recentLogs.reverse()
			}
			recentLogs.add(logs[i])
		}
		return logs
	}

	static constraints = {
		repoUrl()
		repoDir(nullable:true)
		lastLogId(nullable:true)
	}
}
