package online.juter.supersld.view.input.form

import android.os.Parcelable
import android.renderscript.Sampler
import android.renderscript.Type
import kotlinx.android.parcel.Parcelize

/**
 * Форма на основе которой создается
 * поэтапный набор экранов, для заполнения данных.
 *
 * Такой подход к заполнению данных упрощяет это действие
 * для пользователя и делает процес забиваение информации
 * более наглядным.
 *
 * Форма состоит из страниц [JTFormPage] (которые потом будут отдельными
 * экранами) и полей [JTFormLine] (в которых реализуется валидация и
 * заполнение данных)
 */
@Parcelize
data class JTForm(
    val name: String? = null,
    val finishText: String = "Завершить",
    val pages: MutableList<JTFormPage>
): Parcelable {

    /**
     * Проверяем все поля формы на каждой
     * странице на верностьб заполнения.
     * Каждая линия формы сама определяет
     * правильно она заполнена или нет.
     *
     * @return true если форма полностью заполнена правильно.
     */
    fun isValid(): Boolean {
        var isValid = true
        pages.forEach { page ->
            if (!page.isValid()) isValid = false
        }
        return isValid
    }

    /**
     * Поиск строкового значения по
     * идентификатору среди всех полей формы.
     *
     * @param id идентификатор линии формы.
     * @return строка найденная по идентификатору.
     */
    fun findString(id: String) = foundValueByType<String>(id)

    /**
     * Поиск числового значения по
     * идентификатору среди всех полей формы.
     *
     * @param id идентификатор линии формы.
     * @return чисто найденнае по идентификатору.
     */
    fun findInt(id: String) = foundValueByType<Int>(id)

    /**
     * Поиск логического значения по
     * идентификатору среди всех полей формы.
     *
     * @param id идентификатор линии формы.
     * @return логическое значение найденное по идентификатору.
     */
    fun findBool(id: String) = foundValueByType<Boolean>(id)

    /**
     * Поиск значения в форме по указанному
     * возвращаемому типу данных.
     *
     * @param id идентификатор линии формы.
     * @return значение найденное по идентификатору.
     */
    inline fun <reified T> foundValueByType(id: String) : T?  {
        val resultClass = T::class.java
        pages.forEach { page ->
            page.lines.forEach { line ->
                if (
                    line.getLineId() != null &&
                    line.getLineId() == id &&
                    line.getValueType() == resultClass
                ) {
                    return line.getLineValue() as T
                }
            }
        }
        return null
    }
}