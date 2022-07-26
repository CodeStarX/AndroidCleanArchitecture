package mohsen.soltanian.cleanarchitecture.core.data.services

import io.mockk.verify
import kotlinx.coroutines.test.runTest
import mohsen.soltanian.cleanarchitecture.core.data.core.ServicesUnitTest
import mohsen.soltanian.cleanarchitecture.core.data.core.ServiceUnitTesting
import mohsen.soltanian.cleanarchitecture.core.data.models.response.CastsResponse
import mohsen.soltanian.cleanarchitecture.core.data.services.RemoteApi.Companion.API_KEY
import org.amshove.kluent.`should be instance of`
import org.junit.Test

@ServiceUnitTesting(baseUrl = "https://api.themoviedb.org/3/", clazz = RemoteApi::class)
internal class RemoteApiTest: ServicesUnitTest() {

    @Test
    fun `run test by live service`() = runTest {
        val response = serviceBuilder(isLive = true).getCasts("550",API_KEY)
        response `should be instance of` CastsResponse::class.java
    }

    @Test
    fun `run test by mock service`() = runTest{
        enqueueResponse("mock/get-casts.json")
        serviceBuilder(isLive = false).getCasts("550",API_KEY)
        mockWebServerInstance.takeRequest()
        verify(exactly = 1) {  mockWebServerInstance.takeRequest()  }
    }

}