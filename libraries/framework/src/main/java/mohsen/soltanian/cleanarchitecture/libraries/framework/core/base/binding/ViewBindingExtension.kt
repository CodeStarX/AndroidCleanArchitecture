package mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.binding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.adapter.BasicPagingRecyclerAdapter
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.adapter.BasicRecyclerAdapter
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.annotation.Layout
import java.lang.reflect.ParameterizedType

inline fun <reified V : ViewDataBinding> BasicRecyclerAdapter<*, *>.getDataBinding(
    layoutInflater: LayoutInflater,container: ViewGroup): V? {
    return if(this.javaClass.getAnnotation(Layout::class.java) == null) {
        throw Exception("layout id is Null")
        null
    }else {
        this.javaClass.getAnnotation(Layout::class.java)
            ?.let { layoutRes -> DataBindingUtil.inflate(layoutInflater, layoutRes.value,container,false) }

    }
}

inline fun <reified V : ViewDataBinding> BasicPagingRecyclerAdapter<*, *>.getDataBinding(
    layoutInflater: LayoutInflater,container: ViewGroup): V? {
    return if(this.javaClass.getAnnotation(Layout::class.java) == null) {
        throw Exception("layout id is Null")
        null
    }else {
        this.javaClass.getAnnotation(Layout::class.java)
            ?.let { layoutRes -> DataBindingUtil.inflate(layoutInflater, layoutRes.value,container,false) }

    }
}
