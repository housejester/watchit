package org.watchit.git

class InvalidGitUrlException extends GitCloneException{
	InvalidGitUrlException(url){
		super("'${url}' doesn't look like it's a git url.".toString())
	}
}