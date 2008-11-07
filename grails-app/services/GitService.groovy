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
	"git clone ${url} ${dir}".execute().text
    }
}
