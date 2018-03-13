package event.ticket.sales

class UrlMappings {

    static mappings = {
//        "/$controller/$action?/$id?(.$format)?"{
//            constraints {
//                // apply constraints here
//            }
//        }

        group "/admin", {
            group "/ticket", {
                "/index"(controller:"ticket", action: "index")
                "/"(controller:"ticket", action: "index")
                "/create"(controller:"ticket", action: "create")
                "/edit/$id"(controller:"ticket", action: "edit")
                "/edit"(controller:"ticket", action: "edit")
            }
            group "/event", {
                "/index"(controller:"event", action: "index")
                "/"(controller:"event", action: "index")
                "/create"(controller:"event", action: "create")
                "/edit/$id"(controller:"event", action: "edit")
                "/edit"(controller:"event", action: "edit")
            }
            "/dashboard"(controller:"dashboard", action: "index")
        }

        "/"(controller:"purchase", action:"index")
        "/purchase/$id"(controller:"purchase", action: "shortURL")

        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
