package org.watchit.git

class InvalidGitUrlException extends GitCloneException{
	InvalidGitUrlException(url){
		super("' doesn't look like it's a git url.")
	}
}