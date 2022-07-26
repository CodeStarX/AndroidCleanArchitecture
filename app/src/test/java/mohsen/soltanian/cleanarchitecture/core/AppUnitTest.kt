package mohsen.soltanian.cleanarchitecture.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner

@RunWith(BlockJUnit4ClassRunner::class)
open class AppUnitTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

//    @get:Rule
//    var testCoroutineRule = TestCoroutineRule()

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