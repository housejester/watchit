package org.watchit.git
class LogFormatBuilder {
	static String LOG_START = ":LOG_START:"
	static String LOG_DELIM = ":LOG_DELIM:"

	StringBuilder format
	def numParts = 0

	public LogFormatBuilder(){
		format = new StringBuilder(LOG_START)
	}
	def addFullHash(){
		return add("H")
	}
	def addCommitterName(){
		return add("cn")
	}
	def addCommitTime(){
		return add("ct")
	}
	def addSubject(){
		return add("s")
	}
	def addBody(){
		return add("b")
	}
	def add(text){
		format << "%" << text << LOG_DELIM
		++numParts
		return this;
	}
	def parse(logOutput, callback){
		logOutput.trim().split(LOG_START).reverse().each { log -> 
			if( log ){
				callback(log.split(LOG_DELIM, numParts+1))
			}
		}
	}
	public String toString(){
		return format.toString();
	}
}
