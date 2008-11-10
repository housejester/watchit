class UrlMappings {
	static mappings = {
		"/" {
			controller = "watchit"
			action = "index"
		}
		
		"/$controller/$id"{
			action = [POST:"update", GET:"show", PUT:"save", DELETE:"delete"]
		}
		"/$controller/$action?/$id?"{
		}
		
	  
		"500"(view:'/error')

	}
}
