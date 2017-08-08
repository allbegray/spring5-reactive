package reactive.handler

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactive.model.Board
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import java.time.LocalDateTime
import java.util.*

@Component
class BoardHandler {

    val boards = hashMapOf<UUID, Board>()

    fun list(request: ServerRequest) = ok().body(boards.values.toFlux(), Board::class.java)!!

    fun add(request: ServerRequest) =
            request
                    .body(BodyExtractors.toFormData())
                    .flatMap {
                        val formData = it.toSingleValueMap()

                        val board = Board(id = UUID.randomUUID(), title = formData["title"]!!, content = formData["content"]!!)
                        boards.put(board.id, board)

                        ok().body(Mono.just(board), Board::class.java)
                    }!!

    fun edit(request: ServerRequest) =
            request
                    .body(BodyExtractors.toFormData())
                    .flatMap {
                        val formData = it.toSingleValueMap()

                        val id = UUID.fromString(formData["id"]!!)
                        val board = boards[id]!!.copy(title = formData["title"]!!, content = formData["content"]!!, updateDate = LocalDateTime.now())
                        boards.put(board.id, board)

                        ok().body(Mono.just(board), Board::class.java)
                    }!!

    fun view(request: ServerRequest) =
            request
                    .body(BodyExtractors.toFormData())
                    .flatMap {
                        val formData = it.toSingleValueMap()

                        val id = formData["id"]!!
                        val board = boards[UUID.fromString(id)]
                        val mono = Mono.justOrEmpty(board)

                        ok().body(mono, Board::class.java)
                    }!!
}