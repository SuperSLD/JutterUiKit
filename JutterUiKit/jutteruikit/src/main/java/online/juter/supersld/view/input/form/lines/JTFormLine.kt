package online.juter.supersld.view.input.form.lines

import android.os.Parcelable

/**
 * Дефолтный элемент формы.
 * Абстракция объединяющая элементы формы
 * заполняющие данные (например [TextInputLine]) и
 * просто что-то отображающие (например [SolidTextLine]).
 *
 * @author Solyanoy Leonid (solyanoy.leonid@gmail.com)
 */
interface JTFormLine: Parcelable {

    /**
     * Определение разметки и
     * типа вью холдера для отображения
     * линии в форме.
     */
    fun getAdapterData(): Pair<Int, Class<*>>

    /**
     * Проверка на валидность данных,
     * введенных в линии.
     */
    fun valid(): Boolean

    /**
     * Получение идентификатора линии.
     * Если идентификатор равен нулю, то
     * линия не возвращает никакого значения.
     * Например [TextLine], не имеет идентификатора,
     * так как не учавствует в заполнении формы.
     */
    fun getLineId(): String?

    /**
     * Определение типа возвращаемого
     * згачения, чтобы выполнять поиск по идентификатору,
     * через всю форму.
     */
    fun getValueType(): Class<*>?

    /**
     * Возвращение хранимого значения, для
     * поиска по идентификатору, через всю форму.
     */
    fun getLineValue(): Any?
}