package mohsen.soltanian.cleanarchitecture.ui.fragments.details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mohsen.soltanian.cleanarchitecture.BR
import mohsen.soltanian.cleanarchitecture.R
import mohsen.soltanian.cleanarchitecture.base.mvi.BaseMviFragment
import mohsen.soltanian.cleanarchitecture.databinding.FragmentMovieDetailsBinding
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.annotation.FragmentAttribute
import mohsen.soltanian.cleanarchitecture.libraries.framework.extensions.gone
import mohsen.soltanian.cleanarchitecture.libraries.framework.extensions.toast
import mohsen.soltanian.cleanarchitecture.libraries.framework.extensions.visible
import mohsen.soltanian.cleanarchitecture.ui.fragments.details.adapter.CastsAdapter
import mohsen.soltanian.cleanarchitecture.ui.fragments.details.contract.MovieDetailsContract
import mohsen.soltanian.cleanarchitecture.utils.Genres

@SuppressLint("NonConstantResourceId")
@FragmentAttribute(layoutId = R.layout.fragment_movie_details)
@AndroidEntryPoint
class MovieDetailsFragment : BaseMviFragment<FragmentMovieDetailsBinding,
        MovieDetailsContract.State, MovieDetailsViewModel>() {

    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()
    private val navArgs: MovieDetailsFragmentArgs by navArgs()
    private val castsAdapter = CastsAdapter().apply {
        clickListener = { cast ->
            toast(
                "name is: ${cast.name}\n" +
                        "character name is: ${cast.character}"
            )
        }
    }

    override fun fragmentStart() {
        super.fragmentStart()
        me?.supportActionBar?.title = "Movie Details"
    }

    override val viewModel: MovieDetailsViewModel
        get() = movieDetailsViewModel

    override fun bindingVariables(): HashMap<Int, Any> {
        val hashMap: HashMap<Int, Any> = hashMapOf()
        hashMap[BR.click] = ClickProxy()
        hashMap[BR.detailsModel] = navArgs.model
        hashMap[BR.viewModel] = viewModel
        hashMap[BR.castAdapter] = castsAdapter
        hashMap[BR.rvLayoutManager] = LinearLayoutManager(
            requireCompatActivity().applicationContext,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        return hashMap
    }

    override fun onViewReady(bundle: Bundle?) {
        viewModel.onTriggerEvent(MovieDetailsContract.Event.fetchMovieCast(movieId = navArgs.model.movieId.toString()))
        getBinging()?.counterFab?.count = navArgs.model.movieVote!!?.toInt()

        var genresStr = ""
        navArgs.model.genreIds?.forEach { item ->
            genresStr += "  ${Genres.realGenres[item]}"
        }
        if (genresStr.isEmpty()) {
            getBinging()?.lGenres?.gone()
        } else {
            getBinging()?.lGenres?.visible()
            getBinging()?.genres?.apply {
                text = genresStr
            }
        }
    }

    inner class ClickProxy {
        fun fabClicked() {
            toast("movie vote is: ${navArgs.model.movieVote}")
        }
    }
}