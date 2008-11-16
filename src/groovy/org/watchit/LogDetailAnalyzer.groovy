package org.watchit

class LogDetailAnalyzer{
	def analyzeLog = { log -> log.message = "message brought to you by...monkeys"; println log.message; }
}