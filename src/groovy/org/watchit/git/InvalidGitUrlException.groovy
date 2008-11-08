package org.watchit.git

class InvalidGitUrlException extends GitCloneException{
	InvalidGitUrlException(url){
		super("'${url}' does not look like a git url.".toString())
	}
}