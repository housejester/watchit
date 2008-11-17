package org.watchit.domain

class CommitLog {
	static hasMany = [whatChanged:FileStatus]

	Project project
	String logId
	String subject
	String author
	String message
	Date commitDate

	static constraints = {
		logId()
		subject(nullable:true)
		author()
		commitDate()
		message(nullable:true)
		project()
		
	}
}
