package mohsen.soltanian.cleanarchitecture.core.data.core

import io.mockk.MockKAnnotations
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner

@RunWith(BlockJUnit4ClassRunner::class)
open class DataUnitTest {

    open fun onSetUpTest() {}

    open fun onStopTest() {}

    @Before
    fun onSetup() {
        MockKAnnotations.init(this)
        onSetUpTest()
    }

    @After
    fun onTearDown() {
        onStopTest()
    }
}