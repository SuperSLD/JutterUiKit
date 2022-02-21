package online.juter.supersld.view.input.form

import android.annotation.SuppressLint
import android.content.Context
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
 * Конструктор для форм.
 *
 * В него передается форма [JTForm] и отображается
 * давай пользователю заполнить огромное количество полей
 * в удобном для него виде.
 *
 * Для базовой кастомизации формы используется объект
 * [JTFormParams] с базовыми цветами и стилями. Далее для
 * кастомизации каждого элемента можно его переопределить,
 * заменив ссылку на холдер и разметку. Также можно
 * реализовывать свои поля формы, но их ViewHolder обязательно
 * должен наследлваться от [JTFormBaseHolder].
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
    private var mToastListener: ((String)->Unit)? = null

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
        vpFormPager.adapter = JTFormPageAdapter(childFragmentManager, mFragments)
        updateLine()
    }

    /**
     * Переход к следующей странице
     * формы, в случае если все поля на текущей
     * странице заполнены верно.
     */
    fun next() {
        if (mPosition < mFragments.size - 1)
            if (mForm!!.pages[mPosition].isValid()) {
                vpFormPager.setCurrentItem(vpFormPager.currentItem + 1, true)
            } else {
                if (mToastListener == null) {
                    Toast.makeText(
                        context,
                        "Пожалуйста заполните все обязательные поля чтобы продолжить",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    mToastListener?.invoke("Пожалуйста заполните все обязательные поля чтобы продолжить")
                }
            }
        mPosition = vpFormPager.currentItem
        updateLine()
    }

    /**
     * Переход к предыдущей странице формы или
     * к выходу из формы (если выбрана первая страница)
     */
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
            if (mToastListener == null) {
                Toast.makeText(context, "Пожалуйста заполните все поля перед отправкой", Toast.LENGTH_SHORT).show()
            } else {
                mToastListener?.invoke("Пожалуйста заполните все поля перед отправкой")
            }
        }
    }

    /**
     * Калбэк для определения завершения формы.
     */
    fun onFinish(finish: (JTForm)->Unit) {
        mFinishListener = finish
    }

    /**
     * Переопределение отображения тостов.
     * Если его не вызывать, то будут показываться
     * дефолтные тосты анжроида.
     */
    fun onToast(listener: (String)->Unit) {
        mToastListener = listener
    }

    @SuppressLint("SetTextI18n")
    private fun updateLine() {
        tvCounter.text = "${mPosition+1}/${mFragments.size}"
        vLine.addProgress(mPosition - mLastPosition)
        mLastPosition = mPosition
    }

    inner class JTFormPageAdapter(fm: FragmentManager,
                                  private val fragments: MutableList<Fragment>
    ) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment = fragments[position]
        override fun getCount(): Int = fragments.size
        override fun getPageTitle(position: Int): CharSequence? = fragments[position].tag
    }
}