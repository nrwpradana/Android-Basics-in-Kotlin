import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source

/**
 * If we have a piece of code that we might use for multiple tests,
 * we can create an open class and have our test classes inherit from it.
 * */
open class BaseTest {

    val mockWebServer = MockWebServer()

    /**
     * The function takes a file from our test resources and turns it into a fake API response.
     * */
    fun enqueue(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val buffer = inputStream.source().buffer()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(buffer.readString(Charsets.UTF_8))
        )
    }
}