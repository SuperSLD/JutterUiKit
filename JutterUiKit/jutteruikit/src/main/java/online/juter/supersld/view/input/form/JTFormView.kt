package online.juter.supersld.view.input.form

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.view_form.view.*
import online.juter.supersld.R

/**
 *  Конструктор для форм.
 *
 * @author Solyanoy Leonid (solyanoy.leonid@gmail.com)
 */
class JTFormView : LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defUtils: Int) : super(
        context,
        attributeSet,
        defUtils
    )

    private val mFragments : MutableList<Fragment> = mutableListOf()
    private var mPosition = 0
    private var mLastPosition = mPosition
    private var mParams: JTFormParams? = null
    private var mForm: JTForm? = null
    private var mExitListener: (()->Unit)? = null
    private var mFinishListener: ((JTForm)->Unit)? = null

    init {
        View.inflate(context, R.layout.view_form, this)
    }


    /**
     * Инициализация формы.
     * Создаются все страницы и линии по
     * указанному шаблону. Затем пользователь все заполняет.
     *
     * @param form
     * @param params набор настроек для кастомизации.
     */
    fun init(
        form: JTForm,
        childFragmentManager: FragmentManager,
        params: JTFormParams = JTFormParams()
    ) {
        this.mForm = form
        this.mParams = params

        with(vLine) {
            setColor(params.colorAccent)
            setBackgroundColor(params.colorBorder)
            setMaxProgress(form.pages.size)
            setProgress(1)
        }
        tvCounter.setTextColor(params.colorAccent)
        mFragments.clear()
        for (i in form.pages.indices) {
            val page = JTFormPageFragment.create(
                page = form.pages[i],
                position = if (i != form.pages.size - 1) i else JTFormPage.LAST_PAGE,
                bottomText = form.pages[i].buttonText,
                finishString = form.finishText
            )
            with(page) {
                onNext(this@JTFormView::next)
                onPrevious(this@JTFormView::previous)
                onFinish(this@JTFormView::finish)
                setRootFragmentManager(childFragmentManager)
                setParams(params)
            }
            mFragments.add(page)
        }
        vpFormPager.setPagingEnabled(false)
        vpFormPager.adapter = ExpanseProfitPageAdapter(childFragmentManager, mFragments)
        updateLine()
    }

    fun next() {
        if (mPosition < mFragments.size - 1)
            if (mForm!!.pages[mPosition].isValid()) {
                vpFormPager.setCurrentItem(vpFormPager.currentItem + 1, true)
            } else {
                Toast.makeText(context, "Пожалуйста заполните все обязательные поля чтобы продолжить", Toast.LENGTH_SHORT).show()
            }
        mPosition = vpFormPager.currentItem
        updateLine()
    }

    fun previous() {
        if (mPosition > 0) {
            vpFormPager.setCurrentItem(vpFormPager.currentItem - 1, true)
        } else {
            exit()
        }
        mPosition = vpFormPager.currentItem
        updateLine()
    }

    private fun exit() {
        mExitListener?.invoke()
    }

    /**
     * Нажатие на кнопку назад на первой странице,
     * или нажатие на кнопку на главную внизу страницы.
     */
    fun onExit(exit: ()->Unit) {
        mExitListener = exit
    }

    private fun finish() {
        if (this.mForm?.isValid() == true) {
            mFinishListener?.invoke(this.mForm!!)
        } else {
            Toast.makeText(context, "Пожалуйста заполните все поля перед отправкой", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Завершение заполнения формы.
     */
    fun onFinish(finish: (JTForm)->Unit) {
        mFinishListener = finish
    }

    @SuppressLint("SetTextI18n")
    private fun updateLine() {
        tvCounter.text = "${mPosition+1}/${mFragments.size}"
        vLine.addProgress(mPosition - mLastPosition)
        mLastPosition = mPosition
    }

    inner class ExpanseProfitPageAdapter(fm: FragmentManager,
                                         private val fragments: MutableList<Fragment>
    ) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragments[position].tag
        }
    }
}