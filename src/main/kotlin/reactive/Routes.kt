package reactive

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router
import reactive.handler.BoardHandler

@Configuration
class Routes {

    @Bean
    fun router(boardHandler: BoardHandler) =
            router {
                "/board".nest {
                    GET("/", boardHandler::list)
                    POST("/add", boardHandler::add)
                    POST("/edit", boardHandler::edit)
                    GET("/view", boardHandler::view)
                }
            }
}