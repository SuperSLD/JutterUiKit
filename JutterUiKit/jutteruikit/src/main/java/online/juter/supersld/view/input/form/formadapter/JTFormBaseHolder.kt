package online.juter.supersld.view.input.form.formadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import online.juter.supersld.view.input.form.JTFormParams
import online.juter.supersld.view.input.form.lines.JTFormLine

/**
 * Базовый холдер от которого
 * будут наследоваться все поля формы.
 *
 * @author Solyanoy Leonid (solyanoy.leonid@gmail.com)
 */
abstract class JTFormBaseHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var mParams: JTFormParams? = null

    /**
     * Связывание UI модели данных
     * с холдером.
     */
    abstract fun bind(line: JTFormLine)

    /**
     * Установка параметров формы
     * для корректного отображения холдера.
     */
    fun setParams(params: JTFormParams) {
        this.mParams = params
    }

    /**
     * Получение параметров по которым
     * создается визуальная часть формы.
     */
    protected fun getParams() = mParams!!
}