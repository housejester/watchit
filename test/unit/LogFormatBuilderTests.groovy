import org.watchit.git.*

class FormatBuilderTests extends GroovyTestCase {
	void testShouldParseSingleLog(){
		def logStart = LogFormatBuilder.LOG_START
		def logDelim = LogFormatBuilder.LOG_DELIM
		def testLog = """
			${logStart}hash1${logDelim}
		"""
		def format = new LogFormatBuilder()
		format.addFullHash()
		
		format.parse(testLog, {parts-> assertEquals("hash1", parts[0])})
	}
	void testShouldParseEmptyLastPartsAsEmptyStrings(){
		def logStart = LogFormatBuilder.LOG_START
		def logDelim = LogFormatBuilder.LOG_DELIM
		def testLog = """
			${logStart}hash1${logDelim}${logDelim}
		"""
		def format = new LogFormatBuilder()
		format.addFullHash()
		format.addBody()
		
		format.parse(testLog, {parts-> assertEquals("", parts[1])})
	}
}