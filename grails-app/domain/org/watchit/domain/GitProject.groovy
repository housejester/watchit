package org.watchit.domain

import org.watchit.git.Git

class GitProject extends Project {
	public Git getScm(repoDir){
		return new Git(repoDir)
	}
}
