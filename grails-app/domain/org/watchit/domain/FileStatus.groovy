package org.watchit.domain

class FileStatus {
	static belongsTo = CommitLog
	
	CommitLog commitLog
	String filePath
	String status
}
