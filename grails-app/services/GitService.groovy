import org.watchit.git.GitCloneException

class GitService {
    boolean transactional = true

    def exists() {
	try{
		"git --version".execute()
	}catch(Exception ex){
		return false
	}
	return true
    }

    def clone(url, dir){
	def proc = "git clone ${url} ${dir}".execute()
	proc.waitFor()
	if( proc.exitValue() != 0){
		throw new GitCloneException( proc.err.text )
	}
	def out = proc.text
	System.out.println("git clone output: " + proc.exitValue() + " : "+out);
    }
}
