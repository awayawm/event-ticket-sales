package event.ticket.sales

class UrlMappings {

    static mappings = {

        "/login/auth"(controller:"login", action:"auth")
        "/logout"(controller:"logout", action:"index")


        group "/sale", {
            "/status/$id"(controller:"sale", action: "status")
        }

        group "/admin", {
            group "/sale", {
                "/index"(controller:"sale", action: "index")
                "/"(controller:"sale", action: "index")
            }
            group "/report", {
                "/index"(controller:"report", action: "index")
                "/"(controller:"report", action: "index")
            }

            group "/ticket", {
                "/index"(controller:"ticket", action: "index")
                "/"(controller:"ticket", action: "index")
                "/create"(controller:"ticket", action: "create")
                "/edit/$id"(controller:"ticket", action: "edit")
                "/edit"(controller:"ticket", action: "edit")
                "/delete/$id"(controller:"ticket", action: "delete")
            }

            group "/event", {
                "/index"(controller:"event", action: "index")
                "/"(controller:"event", action: "index")
                "/create"(controller:"event", action: "create")
                "/edit/$id"(controller:"event", action: "edit")
                "/edit"(controller:"event", action: "edit")
                "/delete/$id"(controller:"event", action: "delete")
            }

            "/dashboard"(controller:"dashboard", action: "index")
        }

        group "/purchase", {
            "/processPayment"(controller:"purchase", action: "processPayment")
            "/getClientToken"(controller:"purchase", action: "getClientToken")
            "/confirmation"(controller:"purchase", action: "confirmation")
            "/$id"(controller:"purchase", action: "shortURL")
        }

        "/"(controller:"purchase", action:"index")


        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
