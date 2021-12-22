package online.juter.supersld.view.input.form
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.view_form_fragment.*
import online.juter.supersld.R
import online.juter.supersld.common.setMargins
import online.juter.supersld.common.toPx
import online.juter.supersld.view.input.form.formadapter.JTFormPageAdapter

/**
 * Отображение старницы формы.
 */
class JTFormPageFragment : Fragment() {

    private var mNext: (()->Unit)? = null
    private var mPrevious: (()->Unit)? = null
    private var mFinish: (()->Unit)? = null
    private var mParams: JTFormParams? = null
    private val mAdapter by lazy { JTFormPageAdapter(mParams!!) }
    private var mRootFragmentManager: FragmentManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_form_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showPage()
    }

    private fun showPage() {
        val position = arguments?.getInt(ARG_POSITION)!!
        val page = arguments?.getParcelable<JTFormPage>(ARG_PAGE)!!
        mAdapter.addAll(page.lines)
        with(rvRecycler) {
            adapter = this@JTFormPageFragment.mAdapter
            layoutManager = LinearLayoutManager(context)
        }
        val btnNext = AppCompatButton(ContextThemeWrapper(context, mParams!!.buttonSolidStyle), null, mParams!!.buttonSolidStyle)
        val btnPrevious = AppCompatButton(ContextThemeWrapper(context, mParams!!.buttonEmptyStyle), null, mParams!!.buttonEmptyStyle)

        val bottomText = page.buttonText
        val finishText = arguments?.getString(ARG_FINISH_TEXT) ?: "Завершить"
        btnNext.text = bottomText
        btnPrevious.text = "Назад"
        btnPrevious.visibility = if (position == 0) View.GONE else View.VISIBLE
        btnPrevious.setOnClickListener {
            mPrevious?.let { it1 -> it1() }
        }
        btnNext.setMargins(0, 0, 0, 16.toPx.toInt())
        btnPrevious.setMargins(0, 0, 0, 16.toPx.toInt())

        vgPageContent.addView(btnNext)
        vgPageContent.addView(btnPrevious)

        if (position == JTFormPage.LAST_PAGE) {
            btnNext.text = finishText
            btnNext.setOnClickListener {
                mFinish?.let { it1 -> it1() }
            }
        } else {
            btnNext.setOnClickListener {
                mNext?.let { it1 -> it1() }
            }
        }
    }

    fun onNext(next: ()->Unit) {
        this.mNext = next
    }

    fun onPrevious(previous: ()->Unit) {
        this.mPrevious = previous
    }

    fun onFinish(finish: ()->Unit) {
        this.mFinish = finish
    }

    fun setRootFragmentManager(fragmentManager: FragmentManager) {
        this.mRootFragmentManager = fragmentManager
    }

    fun setParams(params: JTFormParams) {
        this.mParams = params
    }

    companion object {
        const val ARG_PAGE = "arg_page"
        const val ARG_POSITION = "arg_position"
        const val ARG_BOTTOM_TEXT = "arg_bottom_text"
        const val ARG_FINISH_TEXT = "arg_finish_text"

        fun create(
            page: JTFormPage,
            position: Int,
            bottomText: String?,
            finishString: String?
        ): JTFormPageFragment {
            val fragment = JTFormPageFragment()
            val args = Bundle()
            args.putParcelable(ARG_PAGE, page)
            args.putInt(ARG_POSITION, position)
            args.putString(ARG_BOTTOM_TEXT, bottomText)
            args.putString(ARG_FINISH_TEXT, finishString)
            fragment.arguments = args
            return fragment
        }
    }
}