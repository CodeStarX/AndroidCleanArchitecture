package mohsen.soltanian.cleanarchitecture.core.data.models.response

import io.mockk.impl.annotations.MockK
import mohsen.soltanian.cleanarchitecture.core.data.core.ModelTesting
import mohsen.soltanian.cleanarchitecture.core.data.core.ModelUnitTest
import mohsen.soltanian.cleanarchitecture.core.data.extention.empty
import org.amshove.kluent.`should equal`
import org.junit.Test

@ModelTesting(
    clazz = Movie::class,
    modelFields = ["adult", "backdrop_path", "genre_ids", "id", "original_language", "original_title",
        "overview", "popularity", "poster_path", "release_date", "title", "video", "vote_average", "vote_count"])
class MovieModelTest : ModelUnitTest() {

    @MockK(relaxed = true)
    lateinit var movie: Movie

    @Test
    fun createResponse() {
       movie.movieId `should equal` String.empty()
    }

}