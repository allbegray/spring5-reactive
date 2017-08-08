package reactive.model

import java.time.LocalDateTime
import java.util.*

data class Board(val id: UUID, var title: String, var content: String, val regDate: LocalDateTime = LocalDateTime.now(), val updateDate: LocalDateTime? = null)