package org.watchit.domain

class CommitLog {
	Project project
	String logId
	String subject
	String author
	String message
	Date commitDate
	def someMethod = {->}
	static constraints = {
		logId()
		subject(nullable:true)
		author()
		commitDate()
		message(nullable:true)
		project()
		
	}
}
