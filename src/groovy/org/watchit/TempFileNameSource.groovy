package org.watchit

class TempFileNameSource{
	def nextFileName(){
		def f = File.createTempFile("watchit-project-", ".git")
		f.delete()
		return f.absolutePath
	}
}